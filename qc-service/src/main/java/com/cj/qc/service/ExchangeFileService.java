package com.cj.qc.service;

import com.cj.qc.model.ExchangeFile;
import com.cj.qc.ruleengine.buffer.SourceBuffer;

/**
 * @author chenzhaowen
 * @description 转换音频文件接口类
 * @date 2019/3/21 10:05
 */
public interface ExchangeFileService {
  /**
   * words_result表中的fileName对应音频文件转换为指定格式，保存至ExchangeFile表
   * @param fileName
   */
  void exchangeFile(String fileName);

  /**
   * 获取音频转写结果的转换类
   * @param fileName
   * @return
   */
  ExchangeFile getExchangeFileIfNotExistWithInsert(String fileName);

  /**
   * exchangeFile转换为SourceBuffer
   * @param exchangeFile
   * @return
   */
  SourceBuffer exchangeFileToSourceBuffer(ExchangeFile exchangeFile);
}
