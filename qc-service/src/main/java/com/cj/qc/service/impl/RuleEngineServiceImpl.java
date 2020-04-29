package com.cj.qc.service.impl;

import com.cj.qc.fastmybatismapper.*;
import com.cj.qc.model.*;
import com.cj.qc.model.dto.RuleCombDTO;
import com.cj.qc.ruleengine.buffer.*;
import com.cj.qc.ruleengine.resolver.*;
import com.cj.qc.ruleengine.separator.RuleSeparator;
import com.cj.qc.service.ExchangeFileService;
import com.cj.qc.service.RuleEngineService;
import com.cj.qc.tool.DateTool;
import com.cj.qc.tool.JsonTool;
import com.gitee.fastmybatis.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.cj.qc.ruleengine.separator.RuleSeparator.exchangeToRuleResJson;
import static com.cj.qc.ruleengine.separator.RuleSeparator.execute;

/**
 * @author chenzhaowen
 * @description TODO
 * @date 2019/3/21 10:05
 */
@Service
public class RuleEngineServiceImpl implements RuleEngineService {

  @Resource
  private ExchangeFileService exchangeFileService;
  @Resource
  private ModelMapper modelMapper;
  @Resource
  private MachineResultMapper machineResultMapper;
  @Resource
  private MachineResultRuleMapper machineResultRuleMapper;
  @Resource
  private TaskMapper taskMapper;
  @Resource
  private TaskQueueAutoMapper taskQueueAutoMapper;
  @Resource
  private TaskQueueFileMapper taskQueueFileMapper;
  @Resource
  private TaskQueueFileHandMapper taskQueueFileHandMapper;
  @Resource
  private VoiceIndexingMapper voiceIndexingMapper;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void resolverVoiceByFileNameAndModel(String fileName, Model model, Long taskId,String beforeDate) {
    //获取转换音频文件词结果
    ExchangeFile exchangeFile = exchangeFileService.getExchangeFileIfNotExistWithInsert(fileName);
    //通过exchangeFile获取sourceBuffer
    SourceBuffer sourceBuffer = exchangeFileService.exchangeFileToSourceBuffer(exchangeFile);
    //通过model获取规则列表
    List<RuleCombDTO> ruleCombDTOS = modelMapper.getRuleCombByModelId(model.getId());

    try {

      //根据模型包含的规则集合解析每条语音文件结果
      resolverAndInsertRuleResult(ruleCombDTOS, sourceBuffer, model, fileName, taskId);

      //执行成功，修改任务文件队列执行状态及检查是否该任务全部完成，若全部完成，修改任务队列的该任务执行状态
      HashMap<String,Object> fileMap=new HashMap<>();
      fileMap.put("status",1);
      fileMap.put("last_modified", DateTool.nowTime());
      taskQueueFileMapper.updateByMap(fileMap,
              new Query().eq("task_id", taskId).eq("file_name",fileName).eq("day", DateTool.strToSqlDate(beforeDate,"yyyy-MM-dd")));
      long count= taskQueueFileMapper
              .getCount(new Query().eq("task_id", taskId)
                      .eq("status",0).eq("day", DateTool.strToSqlDate(beforeDate,"yyyy-MM-dd"))
                      .notEq("file_name", fileName));
      if(count==0){
        HashMap<String,Object> autoMap=new HashMap<>();
        autoMap.put("status",1);
        autoMap.put("last_modified", DateTool.nowTime());
        taskQueueAutoMapper.updateByMap(autoMap,
                new Query().eq("task_auto_id", taskId).eq("day", DateTool.strToSqlDate(beforeDate,"yyyy-MM-dd")));
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("[自动质检任务音频文件解析失败]：日期="+beforeDate+"，文件名="+fileName+"，任务ID="+taskId);
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void resolverVoiceByFileNameAndModelForHand(String fileName, Model model, Long taskId) {
    //获取转换音频文件词结果
    ExchangeFile exchangeFile = exchangeFileService.getExchangeFileIfNotExistWithInsert(fileName);
    //通过exchangeFile获取sourceBuffer
    SourceBuffer sourceBuffer = exchangeFileService.exchangeFileToSourceBuffer(exchangeFile);
    //通过model获取规则列表
    List<RuleCombDTO> ruleCombDTOS = modelMapper.getRuleCombByModelId(model.getId());

    try {

      //根据模型包含的规则集合解析每条语音文件结果
      resolverAndInsertRuleResult(ruleCombDTOS, sourceBuffer, model, fileName, taskId);

      //执行成功，修改任务文件队列执行状态及检查是否该任务全部完成，若全部完成，修改任务队列的该任务执行状态
      HashMap<String,Object> fileMap=new HashMap<>();
      fileMap.put("status",1);
      fileMap.put("last_modified", DateTool.nowTime());
      taskQueueFileHandMapper.updateByMap(fileMap,
              new Query().eq("task_id", taskId).eq("file_name",fileName));
      long count= taskQueueFileHandMapper
              .getCount(new Query().eq("task_id", taskId)
                      .eq("status",0)
                      .notEq("file_name", fileName));
      if(count==0){
        //查询并更新task表中执行中的手动质检任务状态为已执行
        HashMap<String,Object> map=new HashMap<>();
        map.put("status",2);
        map.put("last_modified", DateTool.nowTime());
        taskMapper.updateByMap(map,new Query().eq("id", taskId));
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("[手动质检任务音频文件解析失败]：文件名="+fileName+"，任务ID="+taskId);
    }
  }

  @Override
  public void resolverAndInsertRuleResult(List<RuleCombDTO> ruleCombDTOS, SourceBuffer sourceBuffer, Model model,
                                          String fileName, Long taskId) throws Exception{
    List<MachineResultRule> machineResultRules=new ArrayList<>();

    Integer score=null;
    for (RuleCombDTO ruleCombDTO:ruleCombDTOS) {
      //规则结果入库
      MachineCheck machineCheck=RuleSeparator.execute(ruleCombDTO,sourceBuffer);
      RuleResJsonBuffer ruleResJsonBuffer = RuleSeparator.exchangeToRuleResJson(machineCheck);
      MachineResultRule machineResultRule = new MachineResultRule();
      machineResultRule.setEnable(1);
      machineResultRule.setIsPass(ruleResJsonBuffer.getIsPass());
      machineResultRule.setQcRuleId(ruleCombDTO.getId());
      machineResultRule.setRuleResult(ruleResJsonBuffer.getResult());
      Integer ruleScore=null;
      if(null!=ruleCombDTO.getScore()){
        ruleScore = ruleResJsonBuffer.getIsPass()==1?ruleCombDTO.getScore():0;
        //计算总得分
        if(null==score){
          score=0;
        }
        score+=ruleScore;
      }
      machineResultRule.setScore(ruleScore);
      machineResultRules.add(machineResultRule);
    }

    //计算isPass
    int isPass=0;
    List<Integer> isPasses=machineResultRules.stream().map(MachineResultRule::getIsPass).collect(Collectors.toList());
    if("and".equals(model.getOperator())){
      isPass=AndResolver.resolveInteger(isPasses)?1:0;
    }
    if("or".equals(model.getOperator())){
      isPass=OrResolver.resolveInteger(isPasses)?1:0;
    }

    List<RuleResBuffer> ruleResBuffersFinal = new ArrayList<>();
    for (MachineResultRule machineResultRule:machineResultRules) {
      List<RuleResBuffer> ruleResBuffers= JsonTool.jsonToList(machineResultRule.getRuleResult(),RuleResBuffer.class);
      ruleResBuffersFinal.addAll(ruleResBuffers);
    }
    VoiceIndexing voiceIndexing=voiceIndexingMapper.getByColumn("file_name", fileName);
    MachineResult machineResult = new MachineResult();
    machineResult.setEnable(1);
    machineResult.setQcModelId(model.getId());
    machineResult.setAudioName(fileName);
    machineResult.setPolicyNo(voiceIndexing.getPolicyNo());
    machineResult.setScore(score);
    machineResult.setTaskId(taskId);
    machineResult.setIsPass(isPass);
    machineResult.setResult(JsonTool.listToJson(ruleResBuffersFinal));

    machineResultMapper.save(machineResult);
    for (MachineResultRule machineResultRule:machineResultRules) {
      machineResultRule.setQcResultId(machineResult.getId());
    }
    machineResultRuleMapper.saveBatch(machineResultRules);
  }


  public static void main(String[] args) throws Exception{
    String source = "Ljava buddy, [][]{}{} javaa【】【】**U got work hard and put 我 to 我 java, once U learned the heart of java, I can guarantee that U win//.";
    String formula_And="java and U and 我";
    String formula_Or="jjj or : or 你";
    String formula_no="no bbb";

    SimpleContainBuffer andContainer=AndResolver.simpleResolveObject(formula_And, source);
    SimpleContainBuffer orContainer=OrResolver.simpleResolveObject(formula_Or, source);
    SimpleContainBuffer noContainer= NoResolver.simpleResolveObject(formula_no, source);
    BorderBuffer borderBuffer=BeforeResolver.resolveObject("java", "[]", source,10);
    BorderBuffer borderBuffer1=BeforeResolver.simpleResolveObject("java bf []",source,10);
    BorderBuffer nearBuffer= NearResolver.resolveObject("java", "[]", source,10);
    BorderBuffer nearBuffer1=NearResolver.simpleResolveObject("java ne []",source,10);
    ExceptBuffer exceptBuffer=ExceptResolver.simpleResolveObject("java ex javaa", source);
    System.out.println(orContainer.getOperator());
//    Connection con =
//            DriverManager.getConnection(url , username , password ) ;
  }

}
