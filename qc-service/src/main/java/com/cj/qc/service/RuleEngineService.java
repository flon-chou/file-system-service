package com.cj.qc.service;

import com.cj.qc.model.ExchangeFile;
import com.cj.qc.model.Model;
import com.cj.qc.model.dto.RuleCombDTO;
import com.cj.qc.ruleengine.buffer.SourceBuffer;

import java.util.List;

/**
 * @author chenzhaowen
 * @description TODO
 * @date 2019/3/21 10:05
 */
public interface RuleEngineService {

  /**
   * 根据模型解析音频，插入音频的规则识别结果数据
   * @param fileName
   * @param model
   * @param taskId
   * @param beforeDate
   */
  void resolverVoiceByFileNameAndModel(String fileName, Model model,Long taskId,String beforeDate);

  /**
   * [手动质检]根据模型解析音频，插入音频的规则识别结果数据
   * @param fileName
   * @param model
   * @param taskId
   */
  void resolverVoiceByFileNameAndModelForHand(String fileName, Model model,Long taskId);

  /**
   * 解析每条语音结果
   * @param ruleCombDTO
   * @param sourceBuffer
   */
  /**
   * 根据模型包含的规则集合解析每条语音文件结果
   * @param ruleCombDTOS
   * @param sourceBuffer
   * @param model
   * @param fileName
   * @param taskId
   */
  void resolverAndInsertRuleResult(List<RuleCombDTO> ruleCombDTOS, SourceBuffer sourceBuffer, Model model,
                                   String fileName, Long taskId) throws Exception;
}
