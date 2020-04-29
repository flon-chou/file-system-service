package com.cj.qc.service;

import com.cj.qc.model.TaskVoiceRule;
import com.cj.qc.model.bo.TaskBO;
import com.cj.qc.model.dto.TaskScopeCombDTO;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author chenzhaowen
 * @description 任务引擎接口类
 * @date 2019/4/18 15:15
 */
public interface TaskEngineService {

  /**
   * 生成每日自动质检任务
   */
  void generateAutoTaskForDay();

  /**
   * 插入每日自动质检任务队列
   * @param beforeDate 当前时间前一天日期
   * @return
   */
  void insertTaskQueueForDay(Timestamp beforeDate);

  /**
   * 执行每日自动质检任务
   * @param beforeDate
   */
  void executeAutoTaskForDay(String beforeDate);

  /**
   * 执行单个手动质检任务
   * @param taskId
   */
  void executeSingleHandTask(Long taskId);

  /**
   * 执行单个自动质检任务
   * @param taskId
   */
  void executeSingleAutoTask(Long taskId, String beforeDate);

  /**
   * 获取task详细信息，如任务、任务筛选范围、语音质检任务规则、模型、模型对应规则列表
   * @param taskId
   * @return
   */
  TaskBO getTaskBo(Long taskId);

  /**
   * 根据任务筛选范围及语音质检任务规则范围获取音频文件名集合
   * @param taskScopeCombDTO
   * @return
   */
  List<String> listFileNamesByTaskScopeCombAndTaskVoiceRule(TaskScopeCombDTO taskScopeCombDTO, TaskVoiceRule taskVoiceRule);
}
