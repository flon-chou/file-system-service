package com.cj.qc.ruleengine.resolver;

import com.cj.qc.model.MachineCheckDetail;
import com.cj.qc.model.MachineCheckDetailInfo;
import com.cj.qc.ruleengine.buffer.*;
import com.cj.qc.ruleengine.config.RuleEnum;
import com.cj.qc.ruleengine.separator.RuleSeparator;
import com.cj.qc.ruleengine.util.RuleUtil;
import com.cj.qc.tool.StringTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @author chenzhaowen
 * @description 包含解析器
 * @date 2019/3/21 15:48
 */
public class ContainResolver {

  private final static String OPERATOR = RuleEnum.CONTAIN.getOperator();
  private final static int OPERATOR_NUM = 1;
  /**
   * 计算入口
   * @param dataQueue
   * @param sourceBuffer
   * @param num
   * @param range 间距
   * @return
   * @throws Exception
   */
  public static CommonExpressionBuffer execute(Queue<ExpressionBuffer> dataQueue, SourceBuffer sourceBuffer, int num, int range) throws Exception{
    /**
     * 判断条件：
     * 1.{w1,w2,w3}
     * 2.{w1/w2/w3}
     * 3.dataQueue中数据类型是RuleEnum.BoolResult还是RuleEnum.Data
     */
    //根据指定数量取栈中数据
    List<ExpressionBuffer> queueExpressionBuffers= RuleSeparator.pollDataQueue(OPERATOR_NUM,OPERATOR,dataQueue);

    //保存源表达式和源后缀表达式
    String sourceExpression=queueExpressionBuffers.get(0).getSourceExpression();
    String sourcePostfixExpression=queueExpressionBuffers.get(0).getSourcePostfixExpression();

    //栈先进后出，所以需倒转赋值
    ExpressionBuffer rightExpressionBuffer=queueExpressionBuffers.get(0);
    //计算一个操作数
    ResolverResBuffer rightResolverResBuffer=RuleSeparator.getResolverResBufferByAndOrNo(rightExpressionBuffer,sourceBuffer,OPERATOR,range);

    //计算次数，修改返回bool值
    if(rightExpressionBuffer.getNumber()!=null){
      rightResolverResBuffer.setFinalBoolean(
              rightResolverResBuffer.getMachineCheckDetailInfos().size()>=rightExpressionBuffer.getNumber()?true:false);
    }

    //返回信息
    //1.计算boolean结果
    Boolean boolRes=rightResolverResBuffer.getFinalBoolean();
    //添加结果
    List<MachineCheckDetailInfo> infos=new ArrayList<>();
    infos=rightResolverResBuffer.getMachineCheckDetailInfos();

    ExpressionBuffer expressionBuffer=new ExpressionBuffer();
    expressionBuffer.setRuleEnum(RuleEnum.BOOL_RESULT);
    expressionBuffer.setBoolRes(boolRes);
    expressionBuffer.setSourceExpression(sourceExpression);
    expressionBuffer.setSourcePostfixExpression(sourcePostfixExpression);
    expressionBuffer.setValue(boolRes.toString());

    MachineCheckDetail machineCheckDetail=new MachineCheckDetail();
    machineCheckDetail.setSourceExpression(sourceExpression);
    machineCheckDetail.setSourcePostfixExpression(sourcePostfixExpression);
    machineCheckDetail.setCurrentExpression(queueExpressionBuffers.get(0).getValue());
    machineCheckDetail.setOperator(OPERATOR);
    machineCheckDetail.setSort(num);
    machineCheckDetail.setBoolRes(boolRes);
    machineCheckDetail.setInfos(infos);

    return new CommonExpressionBuffer(expressionBuffer,machineCheckDetail);
  }

  /**
   *  <列表>完成源匹配字符串通过pattern分割字符串后，返回匹配的位置信息列表
   * @param splitRegex and/or...
   * @param matchStr w1 and w2
   * @param source 源匹配字符串
   * @return
   */
  public static List<ContainBuffer> resolveBySplit(String splitRegex, String matchStr, String source){
    //1.分割字符串，去重，去空字符
    List<String> splitList= StringTool.splitStringByTrimAndDuplicateRemoveAndheadTrimRemove(splitRegex, matchStr);
    //2.匹配字符串是否包含指定字符串，并返回位置信息
    return resolve(splitList, source);
  }

  /**
   * <列表>匹配字符串是否包含多个指定字符串，并返回位置信息
   * @param matchContentList
   * @param source 源匹配字符串
   * @return RuleContainer
   */
  public static List<ContainBuffer> resolve(List<String> matchContentList, String source){
    List<ContainBuffer> containBuffers=new ArrayList<>();
    matchContentList.forEach(matchContent->{
      containBuffers.add(resolve(matchContent, source));
    });
    return containBuffers;
  }

  /**
   * [弃之]匹配字符串是否包含指定字符串，并返回位置信息，特殊字符存在问题
   * @param pattern
   * @param source
   * @return RuleContainer
   */
//  @Deprecated
//  public static RuleContainer containStringEvaluate(String pattern,String source){
//    boolean isValid=false;
//    List<Location> locations=new ArrayList<>();
//    //执行匹配
//    Pattern p = Pattern.compile(pattern);
//    Matcher m = p.matcher(source);
//    while(m.find()){
//      if(!isValid){
//        isValid=true;
//      }
//      locations.add(new Location(m.start(),m.end()));
//    }
//    return new RuleContainer(pattern,isValid,locations,locations.size());
//  }

  /**
   * 匹配字符串是否包含指定字符串，并返回位置信息
   * @param matchContent
   * @param source 源匹配字符串
   * @return ContainBuffer
   */
  public static ContainBuffer resolve(String matchContent, String source){
    List<LocationBuffer> locationBuffers=new ArrayList<>();
    //执行匹配
    RuleUtil.locationEvaluate(locationBuffers, matchContent, source,0,0);
    return new ContainBuffer(matchContent,!locationBuffers.isEmpty(),locationBuffers,locationBuffers.size());
  }
}
