package com.cj.qc.ruleengine.resolver;

import com.cj.qc.model.MachineCheckDetail;
import com.cj.qc.model.MachineCheckDetailInfo;
import com.cj.qc.ruleengine.buffer.*;
import com.cj.qc.ruleengine.config.RuleEnum;
import com.cj.qc.ruleengine.separator.RuleSeparator;
import com.cj.qc.tool.StringTool;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * @author chenzhaowen
 * @description ex解析器
 * @date 2019/3/12 0:26
 */
@ApiModel(description = "ex解析器")
public class ExceptResolver {

  private final static String OPERATOR = RuleEnum.EX.getOperator();
  private final static int OPERATOR_NUM = 2;

  /**
   * 计算入口
   * @param dataQueue
   * @param sourceBuffer
   * @param num
   * @return
   * @throws Exception
   */
  public static CommonExpressionBuffer execute(Queue<ExpressionBuffer> dataQueue, SourceBuffer sourceBuffer, int num) throws Exception{
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

    //当前计算内容
    String currentContent=leftExpressionBuffer.getValue()+" "+OPERATOR+" "+rightExpressionBuffer.getValue();
    //计算两个操作数
    ResolverResBuffer leftResolverResBuffer=RuleSeparator.getResolverResBufferByExcept(leftExpressionBuffer,sourceBuffer,OPERATOR,currentContent);
    ResolverResBuffer rightResolverResBuffer=RuleSeparator.getResolverResBufferByExcept(rightExpressionBuffer,sourceBuffer,OPERATOR,currentContent);

    //返回信息
    //1.通过两个操作数结果对比，分离出在左边操作数中不包含右边的左边操作数
    leftResolverResBuffer.getMachineCheckDetailInfos()
            .removeIf(x-> rightResolverResBuffer.getMachineCheckDetailInfos()
                    .stream().anyMatch((y->x.getBaseOpenLocation()>=y.getBaseOpenLocation()
                    && x.getBaseCloseLocation()<=y.getBaseCloseLocation())));
    //弃之
//    Boolean boolRes=resolve(leftResolverResBuffer.getFinalBoolean(),rightResolverResBuffer.getFinalBoolean());

    //2.计算次数，修改返回bool值
    Boolean boolRes=null;

    if(leftExpressionBuffer.getNumber()!=null){
      boolRes=leftResolverResBuffer.getMachineCheckDetailInfos().size()>=leftExpressionBuffer.getNumber()?true:false;
    }else{
      boolRes=leftResolverResBuffer.getMachineCheckDetailInfos().size()>0?true:false;
    }
    //添加结果
    List<MachineCheckDetailInfo> infos=new ArrayList<>();
    infos.addAll(leftResolverResBuffer.getMachineCheckDetailInfos());
    //except的内容不加入结果，根据需求可修改是否加入
//    infos.addAll(rightResolverResBuffer.getMachineCheckDetailInfos());

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
   * 如“W1 ex W1W2”：要求出现“W1”但“W1W2”的词组合除外
   * @param matchStr W1 ex W1W2
   * @param source 源字符串
   * @return
   */
  public static ExceptBuffer simpleResolveObject(String matchStr, String source) throws Exception{
    matchStr=matchStr.trim();
    //1.分割字符串，去重，去空字符
    List<String> splitList= StringTool.splitStringByTrimAndDuplicateRemoveAndheadTrimRemove(OPERATOR, matchStr);
    if(splitList.size()!=2){
      throw new Exception(OPERATOR+"格式错误！左右匹配内容不能为空！");
    }
    return resolveObject(splitList.get(0), splitList.get(1), source);
  }

  /**
   * 如“W1 ex W1W2”：要求出现“W1”但“W1W2”的词组合除外
   * @param matchStr W1 ex W1W2
   * @param source 源字符串
   * @return
   */
  public static boolean simpleResolveBool(String matchStr, String source) throws Exception{
    return simpleResolveObject(matchStr, source).isValid();
  }

  /**
   * 如“W1 ex W1W2”：要求出现“W1”但“W1W2”的词组合除外
   * @param leftMatchStr w1
   * @param rightMatchStr w2
   * @param source 源字符串
   * @return
   */
  public static boolean resolveBool(String leftMatchStr, String rightMatchStr, String source){
    ExceptBuffer exceptBuffer=resolveObject(leftMatchStr, rightMatchStr, source);
    return exceptBuffer.isValid();
  }

  /**
   * 如“W1 ex W1W2”：要求出现“W1”但“W1W2”的词组合除外
   * @param leftMatchStr w1
   * @param rightMatchStr w2
   * @param source 源字符串
   * @return
   */
  public static ExceptBuffer resolveObject(String leftMatchStr, String rightMatchStr, String source){
    ContainBuffer leftContainBuffer = ContainResolver.resolve(leftMatchStr, source);
    ContainBuffer rightContainBuffer = ContainResolver.resolve(rightMatchStr, source);
    boolean isValid=leftContainBuffer.isValid() && !rightContainBuffer.isValid();
    return new ExceptBuffer(
            leftMatchStr+" "+OPERATOR+" "+rightMatchStr,
            isValid,
            leftContainBuffer,
            rightContainBuffer,
            isValid?leftContainBuffer.getCount():0
    );
  }

  /**
   * 核心算法：如“W1 ex W1W2”：要求出现“W1”但“W1W2”的词组合除外
   * @param leftBool
   * @param rightBool
   * @return
   */
  public static boolean resolve(boolean leftBool, boolean rightBool){
    if (leftBool && !rightBool){
      return true;
    }else {
      return false;
    }
  }
}
