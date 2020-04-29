package com.cj.qc.ruleengine.resolver;

import com.cj.qc.model.MachineCheckDetail;
import com.cj.qc.model.MachineCheckDetailInfo;
import com.cj.qc.ruleengine.buffer.*;
import com.cj.qc.ruleengine.config.RuleEnum;
import com.cj.qc.ruleengine.separator.RuleSeparator;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @author chenzhaowen
 * @description no解析器
 * @date 2019/3/12 0:51
 */
@ApiModel(description = "no解析器")
public class NoResolver {

  private final static String OPERATOR = RuleEnum.NO.getOperator();
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
    String sourceExpression=RuleSeparator.getSourceExpression(queueExpressionBuffers,OPERATOR);
    String sourcePostfixExpression=RuleSeparator.getSourcePostfixExpression(queueExpressionBuffers,OPERATOR);

    //栈先进后出，所以需倒转赋值
    ExpressionBuffer rightExpressionBuffer=queueExpressionBuffers.get(0);
    //计算一个操作数
    ResolverResBuffer rightResolverResBuffer=RuleSeparator.getResolverResBufferByAndOrNo(rightExpressionBuffer,sourceBuffer,OPERATOR,range);

    if(rightExpressionBuffer.getNumber()!=null){
      rightResolverResBuffer.setFinalBoolean(
              rightResolverResBuffer.getMachineCheckDetailInfos().size()>=rightExpressionBuffer.getNumber()?true:false);
    }

    //返回信息
    //1.计算boolean结果
    Boolean boolRes=resolve(rightResolverResBuffer.getFinalBoolean());
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
    machineCheckDetail.setCurrentExpression(OPERATOR+" "+queueExpressionBuffers.get(0).getValue());
    machineCheckDetail.setOperator(OPERATOR);
    machineCheckDetail.setSort(num);
    machineCheckDetail.setBoolRes(boolRes);
    machineCheckDetail.setInfos(infos);

    return new CommonExpressionBuffer(expressionBuffer,machineCheckDetail);
  }

  /**
   * 用于只有 no w1 格式，有且只有一个运算符no及一个字符串w1，no必须在w1之前
   * @param matchStr
   * @param source
   * @return
   */
  public static SimpleContainBuffer simpleResolveObject(String matchStr, String source){
    matchStr=matchStr.trim();
    List<ContainBuffer> containBuffers=ContainResolver.resolveBySplit(OPERATOR,matchStr,source);
    Boolean boolRes=resolve(containBuffers.get(0).isValid());
    return new SimpleContainBuffer(OPERATOR,matchStr,containBuffers,boolRes);
  }

  /**
   * 用于如 w1 or w2 or w3 ，满足只含有一个运算符连接其他多个字符串，w？只能是字符串，不能是其他运算结果的布尔值
   * @param matchStr
   * @param source
   * @return
   */
  public static boolean simpleResolveBool(String matchStr, String source){
    matchStr=matchStr.trim();
    List<ContainBuffer> containBuffers=ContainResolver.resolveBySplit(OPERATOR,matchStr,source);
    return resolve(containBuffers.get(0).isValid());
  }

  /**
   * no核心算法：取反
   * @param bool
   * @return
   */
  public static boolean resolve(boolean bool){
    return !bool;
  }
}
