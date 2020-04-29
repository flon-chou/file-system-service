package com.cj.qc.service.impl;

import com.cj.qc.fastmybatismapper.ExchangeFileMapper;
import com.cj.qc.fastmybatismapper.WordsResultMapper;
import com.cj.qc.model.ExchangeFile;
import com.cj.qc.model.WordsResult;
import com.cj.qc.ruleengine.buffer.SourceBuffer;
import com.cj.qc.service.ExchangeFileService;
import com.cj.qc.tool.JsonTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenzhaowen
 * @description 转换音频文件实现类
 * @date 2019/4/10 17:35
 */
@Service
public class ExchangeFileServiceImpl implements ExchangeFileService {
  @Resource
  private WordsResultMapper wordsResultMapper;
  @Resource
  private ExchangeFileMapper exchangeFileMapper;

  @Override
  public void exchangeFile(String fileName) {
    List<WordsResult> wordsResults=wordsResultMapper.listByColumn("file_name", fileName);
    String source="";
    List<String> sourceList=new ArrayList<>();
    List<String> roleList=new ArrayList<>();
    List<String> timeList=new ArrayList<>();
    for (WordsResult r:wordsResults) {
      String c=r.getContent();
      for (int i = 0; i < c.length(); i++) {
        String charStr=String.valueOf(c.charAt(i));
        source+=charStr;
        sourceList.add(charStr);
        roleList.add(r.getRole());
        timeList.add(r.getBeginTime());
      }
    }
    ExchangeFile exchangeFile=new ExchangeFile();
    exchangeFile.setFileName(fileName);
    exchangeFile.setSource(source);
    exchangeFile.setSourceArr(JsonTool.listToJson(sourceList));
    exchangeFile.setRoleArr(JsonTool.listToJson(roleList));
    exchangeFile.setTimeArr(JsonTool.listToJson(timeList));
    exchangeFile.createOperation();
    exchangeFileMapper.save(exchangeFile);
  }

  @Override
  public ExchangeFile getExchangeFileIfNotExistWithInsert(String fileName) {
    //获取转换音频文件词结果
    ExchangeFile exchangeFile=exchangeFileMapper.getByColumn("file_name", fileName);
    //若音频转写词结果不存在，则转写，并插入数据
    if(null==exchangeFile){
      exchangeFile(fileName);
      exchangeFile=exchangeFileMapper.getByColumn("file_name", fileName);
    }
    return exchangeFile;
  }

  @Override
  public SourceBuffer exchangeFileToSourceBuffer(ExchangeFile exchangeFile) {
    //通过exchangeFile获取sourceBuffer
    List<String> sourceList= JsonTool.jsonToList(exchangeFile.getSourceArr(),String.class);
    List<String> roleList=JsonTool.jsonToList(exchangeFile.getRoleArr(),String.class);
    List<String> timeList=JsonTool.jsonToList(exchangeFile.getTimeArr(),String.class);
    return new SourceBuffer(exchangeFile.getSource(),sourceList,timeList,roleList);
  }
}
