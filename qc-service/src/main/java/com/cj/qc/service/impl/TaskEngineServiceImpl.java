package com.cj.qc.service.impl;

import com.cj.qc.fastmybatismapper.*;
import com.cj.qc.model.*;
import com.cj.qc.model.bo.TaskBO;
import com.cj.qc.model.dto.RuleCombDTO;
import com.cj.qc.model.dto.TaskScopeCombDTO;
import com.cj.qc.service.RuleEngineService;
import com.cj.qc.service.TaskEngineService;
import com.cj.qc.tool.DateTool;
import com.cj.qc.tool.StringTool;
import com.gitee.fastmybatis.core.query.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author chenzhaowen
 * @description 任务引擎实现类
 * @date 2019/4/18 15:16
 */
@Slf4j
@Service
public class TaskEngineServiceImpl implements TaskEngineService {

  @Resource
  private RabbitTemplate rabbitTemplate;
  @Resource
  private TaskMapper taskMapper;
  @Resource
  private TaskScopeMapper taskScopeMapper;
  @Resource
  private TaskVoiceRuleMapper taskVoiceRuleMapper;
  @Resource
  private ModelMapper modelMapper;
  @Resource
  private TaskQueueAutoMapper taskQueueAutoMapper;
  @Resource
  private VoiceIndexingMapper voiceIndexingMapper;
  @Resource
  private TaskQueueFileMapper taskQueueFileMapper;
  @Resource
  private RuleEngineService ruleEngineService;
  @Resource
  private TaskQueueFileHandMapper taskQueueFileHandMapper;


  @Transactional(rollbackFor = Exception.class)
  @Override
  public void generateAutoTaskForDay(){

    //获取系统时间前一天日期
    Calendar ca = Calendar.getInstance();
    ca.setTime(new Date());
    ca.add(Calendar.DATE, -1);
    Timestamp beforeDate=DateTool.dateToTime(ca.getTime());
    String beforeDateStr=DateTool.dateToStr(beforeDate, "yyyy-MM-dd");

    //判断是否已存在此日期任务队列，防止定时任务的设置使之重复生成数据
    long count=taskQueueAutoMapper.countByDay(beforeDate);
    if(count>0){
      return;
    }
    //插入每日自动质检任务队列
    insertTaskQueueForDay(beforeDate);
    //发送消息给rabbitmq
    CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
    rabbitTemplate.convertAndSend("fanout.exchange.task.auto", null, beforeDateStr, correlationData);
  }

  @Override
  public void insertTaskQueueForDay(Timestamp beforeDate) {

    List<Long> taskIds=taskMapper.getTaskQueueByDate(beforeDate);
    List<TaskQueueAuto> taskQueueAutos=new ArrayList<>();
    for (Long id:taskIds) {
      TaskQueueAuto taskQueueAuto=new TaskQueueAuto();
      taskQueueAuto.setDay(beforeDate);
      taskQueueAuto.setTaskAutoId(id);
      taskQueueAuto.setStatus(0);
      taskQueueAuto.createOperation();
      taskQueueAutos.add(taskQueueAuto);
    }
    taskQueueAutoMapper.saveBatch(taskQueueAutos);
  }

  @Override
  public void executeAutoTaskForDay(String beforeDate) {
    //查询指定日期下自动质检任务未完成的任务ID数组
    List<Long> taskIds=taskQueueAutoMapper.listByDay(beforeDate);
    //查询并更新task表中从未执行过的自动质检任务状态为已执行
    if(taskIds.size()>0){
      HashMap<String,Object> map=new HashMap<>();
      map.put("status",2);
      map.put("last_modified", DateTool.nowTime());
      taskMapper.updateByMap(map,new Query().in("id", taskIds).eq("status", 0));
    }

    for (Long taskId:taskIds) {
      //执行单个自动质检任务
      //todo:可能优化开启多个线程,也可能单独分离出质检任务模块，发送任务的队列消息
      executeSingleAutoTask(taskId,beforeDate);
    }
  }

  @Override
  public void executeSingleHandTask(Long taskId) {
    //获取该任务筛选文件统计数
    long count=taskQueueFileHandMapper.getCount(new Query().eq("task_id", taskId));
    if(count>0){
      //查询并更新task表中执行中的手动质检任务状态为已执行
      HashMap<String,Object> map=new HashMap<>();
      map.put("status",2);
      map.put("last_modified", DateTool.nowTime());
      taskMapper.updateByMap(map,new Query().eq("id", taskId));
      return;
    }
    //获取task详细信息，如任务、任务筛选范围、语音质检任务规则、模型、模型对应规则列表
    TaskBO taskBO=getTaskBo(taskId);
    if(null==taskBO){
      return;
    }
    //根据taskBO筛选音频文件
    TaskScopeCombDTO scope = taskBO.getTaskScopeCombDTO();
    TaskVoiceRule taskVoiceRule=taskBO.getTaskVoiceRule();
    //1.根据任务筛选范围筛选
    //2.根据语音质检任务规则范围筛选
    //todo:长时静音业务确认 sum？
    List<String> fileNames=listFileNamesByTaskScopeCombAndTaskVoiceRule(scope,taskVoiceRule);

    //todo:机构领域、关键词、客户与坐席情绪（暂以总体情绪代替）、超长通话（与通话时长查询重复？）、抢插话（叠音）未加入筛选条件，后期业务确认再添加，关键词使用elasticsearch技术？

    //如果fileNames.size==0,直接修改相应执行状态，并return
    if(fileNames.size()==0){
      //查询并更新task表中执行中的手动质检任务状态为已执行
      HashMap<String,Object> map=new HashMap<>();
      map.put("status",2);
      map.put("last_modified", DateTool.nowTime());
      taskMapper.updateByMap(map,new Query().eq("id", taskId));
    }

    //插入每个任务队列包含的音频文件数组初始化，状态为未执行
    List<TaskQueueFileHand> taskQueueFileHands=new ArrayList<>();
    for (String filename:fileNames){
      TaskQueueFileHand taskQueueFileHand=new TaskQueueFileHand();
      taskQueueFileHand.setStatus(0);
      taskQueueFileHand.setFileName(filename);
      taskQueueFileHand.setTaskId(taskId);
      taskQueueFileHand.createOperation();
      taskQueueFileHands.add(taskQueueFileHand);
    }
    taskQueueFileHandMapper.saveBatch(taskQueueFileHands);

    //执行每个音频文件规则解析
    for (String filename:fileNames){
      ruleEngineService.resolverVoiceByFileNameAndModelForHand(filename, taskBO.getModel(),taskId);
    }
  }

  @Override
  public void executeSingleAutoTask(Long taskId, String beforeDate) {
    //获取task详细信息，如任务、任务筛选范围、语音质检任务规则、模型、模型对应规则列表
    TaskBO taskBO=getTaskBo(taskId);
    if(null==taskBO){
      return;
    }
    //根据taskBO筛选音频文件
    TaskScopeCombDTO scope = taskBO.getTaskScopeCombDTO();
    TaskVoiceRule taskVoiceRule=taskBO.getTaskVoiceRule();
    //1.根据任务筛选范围筛选
    //2.根据语音质检任务规则范围筛选
    //todo:长时静音业务确认 sum？
    List<String> fileNames=listFileNamesByTaskScopeCombAndTaskVoiceRule(scope,taskVoiceRule);

    //todo:机构领域、关键词、客户与坐席情绪（暂以总体情绪代替）、超长通话（与通话时长查询重复？）、抢插话（叠音）未加入筛选条件，后期业务确认再添加，关键词使用elasticsearch技术？

    //如果fileNames.size==0,直接修改相应执行状态，并return
    if(fileNames.size()==0){
      HashMap<String,Object> map=new HashMap<>();
      map.put("status",1);
      taskQueueAutoMapper.updateByMap(map,
              new Query().eq("task_auto_id", taskId).eq("day", DateTool.strToSqlDate(beforeDate,"yyyy-MM-dd")));
    }

    //插入每个任务队列包含的音频文件数组初始化，状态为未执行
    List<TaskQueueFile> taskQueueFiles=new ArrayList<>();
    for (String filename:fileNames){
      TaskQueueFile taskQueueFile=new TaskQueueFile();
      taskQueueFile.setDay(DateTool.strToSqlDate(beforeDate,"yyyy-MM-dd"));
      taskQueueFile.setStatus(0);
      taskQueueFile.setFileName(filename);
      taskQueueFile.setTaskId(taskId);
      taskQueueFile.createOperation();
      taskQueueFiles.add(taskQueueFile);
    }
    taskQueueFileMapper.saveBatch(taskQueueFiles);

    //执行每个音频文件规则解析
    for (String filename:fileNames){
      ruleEngineService.resolverVoiceByFileNameAndModel(filename, taskBO.getModel(),taskId,beforeDate);
    }
  }

  @Override
  public TaskBO getTaskBo(Long taskId) {
    Task task=taskMapper.getById(taskId);
    if(null==task){
      log.info("[自动质检任务异常]taskId:{}->[任务]数据不存在",taskId);
      return null;
    }

    TaskScopeCombDTO taskScopeCombDTO=taskScopeMapper.getTaskScopeCombByTaskScopeId(task.getQcTaskScopeId());
    if(null==taskScopeCombDTO){
      log.info("[自动质检任务异常]taskId:{}->[质检任务范围]数据不存在",taskId);
      return null;
    }

    TaskVoiceRule taskVoiceRule=taskVoiceRuleMapper.getById(task.getQcTaskVoiceId());
    if(null==taskVoiceRule){
      log.info("[自动质检任务异常]taskId:{}->[语音质检任务规则]数据不存在",taskId);
      return null;
    }
    Model model=modelMapper.getById(task.getQcModelId());
    if(null==model){
      log.info("[自动质检任务异常]taskId:{}->[业务模型]数据不存在",taskId);
      return null;
    }
    List<RuleCombDTO> ruleCombDTOS=modelMapper.getRuleCombByModelId(model.getId());
    if(ruleCombDTOS.size()==0){
      log.info("[自动质检任务异常]taskId:{}->[业务模型关联的规则]数据不存在",taskId);
      return null;
    }
    return new TaskBO(task, taskScopeCombDTO, taskVoiceRule, model, ruleCombDTOS);
  }

  @Override
  public List<String> listFileNamesByTaskScopeCombAndTaskVoiceRule(
          TaskScopeCombDTO taskScopeCombDTO, TaskVoiceRule taskVoiceRule) {
    List<String> policyNos= StringTool.splitString(",",taskScopeCombDTO.getPolicyNo());
    List<String> callNumbers=StringTool.splitString(",",taskScopeCombDTO.getCallNumbers());
    List<String> tsrGroups=StringTool.splitString(",",taskScopeCombDTO.getTsrGroups());
    List<String> tsrNos=StringTool.splitString(",",taskScopeCombDTO.getTsrNo());
    List<String> audioNames=StringTool.splitString(",",taskScopeCombDTO.getAudioNames());

    return voiceIndexingMapper.queryByCondition(
            taskScopeCombDTO.getRecordStartTime(),
            taskScopeCombDTO.getRecordEndTime(),
            taskScopeCombDTO.getTypeOfServiceName(),
            policyNos,
            taskScopeCombDTO.getCallTypeName(),
            taskScopeCombDTO.getCallDurationMin(),
            taskScopeCombDTO.getCallDurationMax(),
            callNumbers,
            tsrGroups,
            tsrNos,
            audioNames,
            taskVoiceRule.getLongSilent(),
            taskVoiceRule.getAbnormalSpeed(),
            taskVoiceRule.getSeatEmtion()
    );
  }

}
