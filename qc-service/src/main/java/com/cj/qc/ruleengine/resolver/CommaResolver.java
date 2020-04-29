package com.cj.qc.ruleengine.resolver;

import com.cj.qc.model.MachineCheckDetailInfo;
import com.cj.qc.ruleengine.buffer.*;
import com.cj.qc.ruleengine.config.RuleEnum;
import com.cj.qc.ruleengine.config.RuleType;
import com.cj.qc.ruleengine.separator.RuleSeparator;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenzhaowen
 * @description 逗号（comma）解析器
 * @date 2019/3/27 10:12
 */
@ApiModel(description = "逗号（comma）解析器，解析如{w1,w2,w3...}")
public class CommaResolver {
  private final static String OPERATOR = RuleEnum.DATA_COMMA.getOperator();

  public static ResolverResBuffer getByCommaData( String matchStr,SourceBuffer sourceBuffer, int range,
                                                  String currentContent)throws Exception{

    //规则解析结果详情类集合
    List<MachineCheckDetailInfo> machineCheckDetailInfos=new ArrayList<>();
    //每个匹配内容的boolean结果存储列表
    Boolean finalBoolean=false;

    //分离如{A-w1,B-w2,w3}，并转换为roleBuffers
    List<RoleBuffer> roleBuffers=RuleSeparator.exchangeToRoleBufferByBrace(
            matchStr,OPERATOR);
    //提取w1 w2 w3
    List<String> childs=roleBuffers.stream().map(RoleBuffer::getChildRegex).collect(Collectors.toList());

    //计算源文件字符串是否包含childs
    CommaListBuffer commaListBuffer=resolveListObject(childs,sourceBuffer.getSource(),range);

    if(commaListBuffer.isValid()){
      //遍历计算时间、角色等信息
      machineCheckDetailInfos=
              getTimeAndRoleOnCommaListBufferAndRoleBuffers(commaListBuffer,roleBuffers,sourceBuffer,matchStr,currentContent);
      finalBoolean=machineCheckDetailInfos.size()>0?true:false;
    }

    return new ResolverResBuffer(machineCheckDetailInfos,finalBoolean);
  }

  /**
   * 遍历计算时间、角色等信息
   * @param commaListBuffer
   * @param roleBuffers
   * @param sourceBuffer
   * @return
   */
  public static List<MachineCheckDetailInfo> getTimeAndRoleOnCommaListBufferAndRoleBuffers(
          CommaListBuffer commaListBuffer, List<RoleBuffer> roleBuffers, SourceBuffer sourceBuffer,String matchStr,String currentContent){
    List<MachineCheckDetailInfo> machineCheckDetailInfos=new ArrayList<>();

    HashMap<String,RoleBuffer> roleBuffersHashMap=RuleSeparator.toMapOfRoleBuffers(roleBuffers);

    //遍历计算时间、角色等信息
    int seriesSortFirst=1;
    for (MultiCommaLocationBuffer multiCommaLocationBuffer:commaListBuffer.getMultiCommaLocationBuffers()) {
      List<CommaLocationBuffer> commaLocationBuffers = multiCommaLocationBuffer.getCommaLocationBuffers();

      //判断commaLocationBuffer中的如w1 w2 w3 w4是否满足各自的角色要求，必须都满足才算成功的一条信息
      boolean finalFlag=true;

      for (CommaLocationBuffer commaLocationBuffer:commaLocationBuffers) {
        RoleBuffer roleBuffer=roleBuffersHashMap.get(commaLocationBuffer.getMatchingContent());

        String role=(roleBuffer.getRuleEnum()==RuleEnum.AB)?RuleType.AB:((roleBuffer.getRuleEnum()==RuleEnum.A)?RuleType.A:RuleType.B);

        if(RuleType.A.equals(role) || RuleType.B.equals(role)){
          for (int i = commaLocationBuffer.getStart(); i < commaLocationBuffer.getEnd(); i++) {
            if(!role.equals(sourceBuffer.getRoleList().get(i))){
              finalFlag=false;
              break;
            }
          }
        }

        if(!finalFlag){
          break;
        }
      }

      if(finalFlag){
        //保存结果信息
        for (int i = 0; i < commaLocationBuffers.size(); i++) {
          //获取角色
          RoleBuffer roleBuffer=roleBuffersHashMap.get(commaLocationBuffers.get(i).getMatchingContent());
          String role=(roleBuffer.getRuleEnum()==RuleEnum.AB)?RuleType.AB:((roleBuffer.getRuleEnum()==RuleEnum.A)?RuleType.A:RuleType.B);

          MachineCheckDetailInfo info=new MachineCheckDetailInfo();
          //顺序串联内容
          info.setSeriesType(1);
          info.setSeriesContent(matchStr);
          info.setSeriesSortFirst(seriesSortFirst);
          info.setSeriesSortSecond(i+1);
          //基础内容
          info.setBaseRole(RuleType.AB.equals(role)?null:role);
          info.setBaseContent(commaLocationBuffers.get(i).getMatchingContent());
          info.setBaseOpenLocation(commaLocationBuffers.get(i).getStart());
          info.setBaseCloseLocation(commaLocationBuffers.get(i).getEnd()-1);
          info.setBaseOpenTime(sourceBuffer.getTimeList().get(commaLocationBuffers.get(i).getStart()));
          info.setBaseCloseTime(sourceBuffer.getTimeList().get(commaLocationBuffers.get(i).getEnd()-1));
          //添加当前计算内容
          info.setCurrentContent(currentContent);
          machineCheckDetailInfos.add(info);
        }

        seriesSortFirst++;
      }
    }
    return machineCheckDetailInfos;
  }

  /**
   * 解析如{w1,w2,w3...} 并获取位置信息
   * @param matchStrList w1 w2 w3 ...
   * @param source
   * @param range 间距
   * @return
   * @throws Exception
   */
  public static CommaListBuffer resolveListObject(List<String> matchStrList, String source, int range)throws Exception{
    //判断matchStrList长度至少2
    if(matchStrList.size()<2){
      throw new Exception("多个关键词顺序关系的串联,如{w1,w2,w3}至少两个关键词");
    }

    CommaListBuffer commaListBuffer=new CommaListBuffer();

    List<BorderBuffer> borderBuffers=new ArrayList<>();
    //取两个记为 w1 和 w2 ，调用w1 bf w2方法
    for (int i = 0; i < matchStrList.size()-1; i++) {
      BorderBuffer borderBuffer = BeforeResolver.resolveObject(matchStrList.get(i),matchStrList.get(i+1),source,range);
      if(!borderBuffer.isValid()){
        return new CommaListBuffer(matchStrList,false);
      }
      borderBuffers.add(borderBuffer);
    }

    //找出位置对应满足多个关键词顺序关系的串联的一组结果
    List<List<BorderLocationBuffer>> list=getSeries(borderBuffers,matchStrList);

    //把list中的List<BorderLocationBuffer>类似链表形式存储转换为指定格式，即两数组（w1，w2）与（w2,w3）转换为w1 w2 w3
    List<MultiCommaLocationBuffer> multiCommaLocationBuffers=new ArrayList<>();
    for (List<BorderLocationBuffer> borderLocationBuffers:list) {
      MultiCommaLocationBuffer multiCommaLocationBuffer = new MultiCommaLocationBuffer();
      List<CommaLocationBuffer> commaLocationBuffers = new ArrayList<>();
      List<LocationBuffer> locationBuffers =
              borderLocationBuffers.stream().map(BorderLocationBuffer::getRightBorder).collect(Collectors.toList());
      locationBuffers.add(0, borderLocationBuffers.get(0).getLeftBorder());
      for (int i = 0; i < locationBuffers.size(); i++) {
        CommaLocationBuffer commaLocationBuffer =
                new CommaLocationBuffer(matchStrList.get(i), locationBuffers.get(i).getStart(), locationBuffers.get(i).getEnd());
        commaLocationBuffers.add(commaLocationBuffer);
      }
      int begin = commaLocationBuffers.get(0).getStart();
      int end = commaLocationBuffers.get(commaLocationBuffers.size() - 1).getEnd();
      multiCommaLocationBuffer.setMatchingContent(source.substring(begin, end));
      multiCommaLocationBuffer.setStart(begin);
      multiCommaLocationBuffer.setEnd(end);
      multiCommaLocationBuffer.setCommaLocationBuffers(commaLocationBuffers);
      multiCommaLocationBuffers.add(multiCommaLocationBuffer);
    }

    return new CommaListBuffer(matchStrList,true,multiCommaLocationBuffers,multiCommaLocationBuffers.size());
  }


  /**
   * 找出位置对应满足多个关键词顺序关系的串联的一组结果
   * @param borderBuffers
   * @param matchStrList
   * @return
   */
  public static List<List<BorderLocationBuffer>> getSeries(List<BorderBuffer> borderBuffers,
                                                           List<String> matchStrList)throws Exception{
    List<List<BorderLocationBuffer>> list=new ArrayList<>();
    if(borderBuffers.size()==0){
      throw new Exception("{}内顺序关系串联至少两个关键词");
    }else if(borderBuffers.size()==1){
      for (BorderLocationBuffer borderLocationBuffer:borderBuffers.get(0).getBorderLocationBuffers()) {
        List<BorderLocationBuffer> borderLocationBuffers=new ArrayList<>();
        borderLocationBuffers.add(borderLocationBuffer);
        list.add(borderLocationBuffers);
      }
    }else{
      getSeriesbyRecursionForThree(list,borderBuffers,matchStrList,0);
    }
    return list;
  }

  /**
   * 递归方法找出位置对应满足多个关键词顺序关系的串联的一组结果,只支持{w1,w2,w3}三个以上
   * @param list
   * @param borderBuffers
   * @param matchStrList
   * @param num
   */
  public static void getSeriesbyRecursionForThree(List<List<BorderLocationBuffer>> list, List<BorderBuffer> borderBuffers,
                                          List<String> matchStrList, int num){
    //若 w1 w2 w3 w4组合成 w1 bf w2;w2 bf w3;w3 bf w4
    if((num+1)== borderBuffers.size()){
      return;
    }
    if(num==0){
      //第一次需要保存w1 bf w2与w2 bf w3中匹配的内容
      List<BorderLocationBuffer> leftBorderLocationBuffers = borderBuffers.get(num).getBorderLocationBuffers();
      List<BorderLocationBuffer> rightBorderLocationBuffers = borderBuffers.get(num+1).getBorderLocationBuffers();
      for (BorderLocationBuffer leftBorderLocationBuffer:leftBorderLocationBuffers) {
        for (BorderLocationBuffer rightBorderLocationBuffer:rightBorderLocationBuffers) {
          //判断w1的
          if(leftBorderLocationBuffer.getRightBorder().getStart().equals(rightBorderLocationBuffer.getLeftBorder().getStart()) &&
                  leftBorderLocationBuffer.getRightBorder().getEnd().equals(rightBorderLocationBuffer.getLeftBorder().getEnd())){
            List<BorderLocationBuffer> borderLocationBuffers=new ArrayList<>();
            borderLocationBuffers.add(leftBorderLocationBuffer);
            borderLocationBuffers.add(rightBorderLocationBuffer);
            list.add(borderLocationBuffers);
          }
        }
      }
    }else{
      List<List<BorderLocationBuffer>> tempList=list;
      list=new ArrayList<>();

      for (int i = 0; i < tempList.size(); i++) {
        List<BorderLocationBuffer> borderLocationBuffers=tempList.get(i);
        BorderLocationBuffer beforeBorderLocationBuffer=borderLocationBuffers.get(borderLocationBuffers.size()-1);
        List<BorderLocationBuffer> rightBorderLocationBuffers = borderBuffers.get(num+1).getBorderLocationBuffers();
        for (BorderLocationBuffer rightBorderLocationBuffer:rightBorderLocationBuffers) {
          if(beforeBorderLocationBuffer.getRightBorder().getStart().equals(rightBorderLocationBuffer.getLeftBorder().getStart()) &&
                  beforeBorderLocationBuffer.getRightBorder().getEnd().equals(rightBorderLocationBuffer.getLeftBorder().getEnd())){
            borderLocationBuffers.add(rightBorderLocationBuffer);
            list.add(borderLocationBuffers);
          }
        }
      }
    }

    num++;
    getSeriesbyRecursionForThree(list,borderBuffers,matchStrList,num);
  }

  public static void main(String[] args)throws Exception {
    //todo:测试后删除
//    RoleBuffer roleBuffer=new RoleBuffer();
//    roleBuffer.setRuleEnum(RuleEnum.B);
//    String role=(roleBuffer.getRuleEnum()==RuleEnum.AB)?RuleType.AB:((roleBuffer.getRuleEnum()==RuleEnum.A)?RuleType.A:RuleType.B);
//    String test="(w1w1 and w2) or (w3 w3 w4) and w5 or no(w6 ne{w7/w8/w9})";
//    String cStr="{A-w1,A-w2,w3,w4}";
//    SourceBuffer sourceBuffer=new SourceBuffer();
//    sourceBuffer.setSource(test);
//    getByCommaData(cStr,sourceBuffer,10,cStr);
//    List<String> cc=new ArrayList<>();
//    cc.add("w1");
//    cc.add("w2");
//    cc.add("w3");
//    cc.add("w4");
//    resolveListObject(cc,test,10);
  }
}
