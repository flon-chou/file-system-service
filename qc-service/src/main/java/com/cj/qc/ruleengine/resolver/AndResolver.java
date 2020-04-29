package com.cj.qc.ruleengine.resolver;

import com.cj.qc.model.MachineCheckDetail;
import com.cj.qc.model.MachineCheckDetailInfo;
import com.cj.qc.ruleengine.buffer.*;
import com.cj.qc.ruleengine.config.RuleEnum;
import com.cj.qc.ruleengine.separator.RuleSeparator;
import com.cj.qc.tool.StringTool;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

/**
 * @author chenzhaowen
 * @description and解析器
 * @date 2019/3/12 0:26
 */
@ApiModel(description = "and解析器")
public class AndResolver {

  private final static String OPERATOR = RuleEnum.AND.getOperator();
  //不可修改
  private final static int OPERATOR_NUM = 2;

  /**
   * 计算入口
   * @param dataQueue
   * @param sourceBuffer
   * @param num
   * @return
   * @throws Exception
   */
  public static CommonExpressionBuffer execute(Queue<ExpressionBuffer> dataQueue, SourceBuffer sourceBuffer,int num, int range) throws Exception{
    /**
     * 判断条件：
     * 1.{w1,w2,w3}
     * 2.{w1/w2/w3}
     * 3.dataQueue中数据类型是RuleEnum.BoolResult还是RuleEnum.Data
     */
    //根据指定数量取栈中数据
    List<ExpressionBuffer> queueExpressionBuffers= RuleSeparator.pollDataQueue(OPERATOR_NUM,OPERATOR,dataQueue);

    //保存源表达式和源后缀表达式
    String sourceExpression=RuleSeparator.getSourceExpression(queueExpressionBuffers,OPERATOR);
    String sourcePostfixExpression=RuleSeparator.getSourcePostfixExpression(queueExpressionBuffers,OPERATOR);

    //栈先进后出，所以需倒转赋值
    ExpressionBuffer leftExpressionBuffer=queueExpressionBuffers.get(1);
    ExpressionBuffer rightExpressionBuffer=queueExpressionBuffers.get(0);
    //计算两个操作数
    ResolverResBuffer leftResolverResBuffer=RuleSeparator.getResolverResBufferByAndOrNo(leftExpressionBuffer,sourceBuffer,OPERATOR,range);
    ResolverResBuffer rightResolverResBuffer=RuleSeparator.getResolverResBufferByAndOrNo(rightExpressionBuffer,sourceBuffer,OPERATOR,range);

    //计算次数，修改返回bool值
    if(leftExpressionBuffer.getNumber()!=null){
      leftResolverResBuffer.setFinalBoolean(
              leftResolverResBuffer.getMachineCheckDetailInfos().size()>=leftExpressionBuffer.getNumber()?true:false);
    }
    if(rightExpressionBuffer.getNumber()!=null){
      rightResolverResBuffer.setFinalBoolean(
              rightResolverResBuffer.getMachineCheckDetailInfos().size()>=rightExpressionBuffer.getNumber()?true:false);
    }

    //返回信息
    //1.计算boolean结果
    Boolean boolRes=resolve(
            new ArrayList<Boolean>(
                    Arrays.asList(leftResolverResBuffer.getFinalBoolean(),rightResolverResBuffer.getFinalBoolean())
            ));
    //添加结果
    List<MachineCheckDetailInfo> infos=new ArrayList<>();
    infos.addAll(leftResolverResBuffer.getMachineCheckDetailInfos());
    infos.addAll(rightResolverResBuffer.getMachineCheckDetailInfos());

    ExpressionBuffer expressionBuffer=new ExpressionBuffer();
    expressionBuffer.setRuleEnum(RuleEnum.BOOL_RESULT);
    expressionBuffer.setBoolRes(boolRes);
    expressionBuffer.setSourceExpression(sourceExpression);
    expressionBuffer.setSourcePostfixExpression(sourcePostfixExpression);
    expressionBuffer.setValue(boolRes.toString());

    MachineCheckDetail machineCheckDetail=new MachineCheckDetail();
    machineCheckDetail.setSourceExpression(sourceExpression);
    machineCheckDetail.setSourcePostfixExpression(sourcePostfixExpression);
    machineCheckDetail.setCurrentExpression(queueExpressionBuffers.get(1).getValue()+" "+OPERATOR+" "+queueExpressionBuffers.get(0).getValue());
    machineCheckDetail.setOperator(OPERATOR);
    machineCheckDetail.setSort(num);
    machineCheckDetail.setBoolRes(boolRes);
    machineCheckDetail.setInfos(infos);

    return new CommonExpressionBuffer(expressionBuffer,machineCheckDetail);
  }

  /**
   * 用于如 w1 and w2 and w3 ，满足只含有一个运算符连接其他多个字符串，w？只能是字符串，不能是其他运算结果的布尔值
   * @param matchStr w1 and w2 and w3
   * @param source
   * @return
   */
  public static SimpleContainBuffer simpleResolveObject(String matchStr, String source){
    matchStr=matchStr.trim();
    List<ContainBuffer> containBuffers=ContainResolver.resolveBySplit(OPERATOR,matchStr,source);
    Boolean boolRes=resolve(ContainBuffer.toBoolArray(containBuffers));
    return new SimpleContainBuffer(OPERATOR,matchStr,containBuffers,boolRes);
  }

  /**
   * 用于如 w1 and w2 and w3 ，满足只含有一个运算符连接其他多个字符串，w？只能是字符串，不能是其他运算结果的布尔值
   * @param matchStr w1 and w2 and w3
   * @param source
   * @return
   */
  public static boolean simpleResolveBool(String matchStr, String source){
    matchStr=matchStr.trim();
    List<ContainBuffer> containBuffers=ContainResolver.resolveBySplit(OPERATOR,matchStr,source);
    return resolve(ContainBuffer.toBoolArray(containBuffers));
  }

  /**
   * 用于如 w1 and w2 and w3 ，满足只含有一个运算符连接其他多个字符串，w？只能是字符串，不能是其他运算结果的布尔值
   * @param matchContentList w1 w2 w3 的集合
   * @param source
   * @return
   */
  public static SimpleContainBuffer resolveObject(List<String> matchContentList, String source){
    List<ContainBuffer> containBuffers=ContainResolver.resolve(matchContentList,source);
    Boolean boolRes=resolve(ContainBuffer.toBoolArray(containBuffers));
    String matchStr= StringTool.splitJointString(matchContentList, " "+OPERATOR+" ");
    return new SimpleContainBuffer(OPERATOR,matchStr,containBuffers,boolRes);
  }

  /**
   * 用于如 w1 and w2 and w3 ，满足只含有一个运算符连接其他多个字符串，w？只能是字符串，不能是其他运算结果的布尔值
   * @param matchContentList w1 w2 w3 的集合
   * @param source
   * @return
   */
  public static boolean resolveBool(List<String> matchContentList, String source){
    return resolveObject(matchContentList, source).getBoolRes();
  }

  /**
   * and核心算法：计算Boolean数组中若存在假，则假；全部为真，则真
   * @param array
   * @return
   */
  public static boolean resolve(ArrayList<Boolean> array){
    for (int i = 0; i < array.size(); i++) {
      if(!array.get(i)){
        return false;
      }
    }
    return true;
  }

  /**
   * and核心算法：计算Boolean数组中若存在假，则假；全部为真，则真
   * @param array
   * @return
   */
  public static boolean resolveInteger(List<Integer> array){
    for (int i = 0; i < array.size(); i++) {
      if(array.get(i)==0){
        return false;
      }
    }
    return true;
  }

}
