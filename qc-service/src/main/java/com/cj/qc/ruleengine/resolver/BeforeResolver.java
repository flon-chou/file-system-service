package com.cj.qc.ruleengine.resolver;

import com.cj.qc.model.MachineCheckDetail;
import com.cj.qc.model.MachineCheckDetailInfo;
import com.cj.qc.ruleengine.buffer.*;
import com.cj.qc.ruleengine.config.BorderEnum;
import com.cj.qc.ruleengine.config.RuleEnum;
import com.cj.qc.ruleengine.config.RuleType;
import com.cj.qc.ruleengine.separator.RuleSeparator;
import com.cj.qc.ruleengine.util.RuleUtil;
import com.cj.qc.tool.StringTool;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @author chenzhaowen
 * @description bf解析器
 * @date 2019/3/12 0:26
 */
@ApiModel(description = "bf解析器")
public class BeforeResolver {

  private final static String OPERATOR = RuleEnum.BF.getOperator();
  private final static int RANGE = RuleType.BF_RANGE;
  private final static int OPERATOR_NUM = 2;

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
    ExpressionBuffer leftExpressionBuffer=queueExpressionBuffers.get(1);
    ExpressionBuffer rightExpressionBuffer=queueExpressionBuffers.get(0);
    //计算两个操作数
    ResolverResBuffer resolverResBuffer=getResolverResBufferByBefore(leftExpressionBuffer,rightExpressionBuffer,sourceBuffer,OPERATOR,range);

    //计算次数，修改返回bool值
    if(rightExpressionBuffer.getNumber()!=null){
      resolverResBuffer.setFinalBoolean(
              resolverResBuffer.getMachineCheckDetailInfos().size()>=rightExpressionBuffer.getNumber()?true:false);
    }

    //返回信息
    //1.计算boolean结果
    Boolean boolRes=resolverResBuffer.getFinalBoolean();
    //添加结果
    List<MachineCheckDetailInfo> infos=resolverResBuffer.getMachineCheckDetailInfos();

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
   * 计算bf左右两个操作数其中一个的匹配结果如w1 {w1/A-w2/w3}其中一种的结果
   * @param leftExpressionBuffer
   * @param rightExpressionBuffer
   * @param sourceBuffer
   * @param operator
   * @param range 间距
   * @return
   * @throws Exception
   */
  public static ResolverResBuffer getResolverResBufferByBefore(ExpressionBuffer leftExpressionBuffer,ExpressionBuffer rightExpressionBuffer,
                                                               SourceBuffer sourceBuffer,
                                                               String operator, int range) throws Exception{
    /**
     * 将如左边匹配内容w与右边匹配内容转换成左右对应内容格式的BorderRoleBuffer，
     * 如{w1/w2}与{w3/A-w4}转成 w1与w2;w1与w3;w2与w3;w2与A-w4;
     */
    List<BorderRoleBuffer> borderRoleBuffers=RuleSeparator.exchangeToBorderRoleBufferByBeforeNear(
            leftExpressionBuffer,rightExpressionBuffer,operator);

    //当前计算内容
    String currentContent=leftExpressionBuffer.getValue()+" "+operator+" "+rightExpressionBuffer.getValue();

    return resolveByBorderRoleBuffer(borderRoleBuffers,sourceBuffer,range,currentContent);
  }

  /**
   * 根据bf两边拆解成一一对应的borderRoleBuffers，遍历根据bf两边拆解成一一对应的borderRoleBuffers获取结果
   * @param borderRoleBuffers
   * @param sourceBuffer
   * @param range 间距
   * @return
   */
  public static ResolverResBuffer resolveByBorderRoleBuffer(List<BorderRoleBuffer> borderRoleBuffers,
                                                            SourceBuffer sourceBuffer, int range,String currentContent){
    List<MachineCheckDetailInfo> machineCheckDetailInfos=new ArrayList<>();

    for (BorderRoleBuffer borderRoleBuffer:borderRoleBuffers) {
      BorderBuffer borderBuffer=resolveObject(
              borderRoleBuffer.getLeftRoleBuffer().getChildRegex(),
              borderRoleBuffer.getRightRoleBuffer().getChildRegex(),
              sourceBuffer.getSource(),range);
      if(borderBuffer.isValid()){
        List<BorderLocationBuffer> borderLocationBuffers = borderBuffer.getBorderLocationBuffers();
        //遍历计算时间、角色等信息
        machineCheckDetailInfos.addAll(
                RuleSeparator.getTimeAndRoleOnBorderLocationBuffersAndBorderRoleBuffer(
                        borderLocationBuffers,borderRoleBuffer,sourceBuffer,borderBuffer.getMatchingContent(),currentContent)
        );
      }
    }

    return new ResolverResBuffer(machineCheckDetailInfos,machineCheckDetailInfos.size()>0?true:false);
  }

  /**
   * 如 w1 bf w2 ，判断是否“w1”在“w2”的前面
   * @param matchStr w1 bf w2
   * @param source 源字符串
   * @param range 间距
   * @return
   */
  public static BorderBuffer simpleResolveObject(String matchStr, String source, int range) throws Exception{
    matchStr=matchStr.trim();
    //1.分割字符串，去重，去空字符
    List<String> splitList= StringTool.splitStringByTrimAndDuplicateRemoveAndheadTrimRemove(OPERATOR, matchStr);
    if(splitList.size()!=2){
      throw new Exception(OPERATOR+"格式错误！左右匹配内容不能为空！");
    }
    return resolveObject(splitList.get(0), splitList.get(1), source, range);
  }

  /**
   * 如 w1 bf w2 ，判断是否“w1”在“w2”的前面
   * @param matchStr w1 bf w2
   * @param source 源字符串
   * @param range 间距
   * @return
   */
  public static boolean simpleResolveBool(String matchStr, String source, int range) throws Exception{
    return simpleResolveObject(matchStr, source,range).isValid();
  }

  /**
   * 如 w1 bf w2 ，判断是否“w1”在“w2”的前面
   * @param leftMatchStr w1
   * @param rightMatchStr w2
   * @param source 源字符串
   * @param range 间距
   * @return
   */
  public static boolean resolveBool(String leftMatchStr, String rightMatchStr, String source, int range){
    BorderBuffer borderBuffer=resolveObject(leftMatchStr, rightMatchStr, source,range);
    return borderBuffer.isValid();
  }

  /**
   * 如 w1 bf w2 ，判断是否“w1”在“w2”的前面
   * @param leftMatchStr w1
   * @param rightMatchStr w2
   * @param source 源字符串
   * @param range 间距
   * @return
   */
  public static BorderBuffer resolveObject(String leftMatchStr, String rightMatchStr, String source, int range){
    List<BorderLocationBuffer> borderLocationBuffers=RuleUtil.borderEvaluate(leftMatchStr, rightMatchStr, source, BorderEnum.BEFORE, range);
    return new BorderBuffer(
            leftMatchStr+" "+OPERATOR+" "+rightMatchStr,
            borderLocationBuffers.size()>0?true:false,
            borderLocationBuffers,
            borderLocationBuffers.size()
            );
  }

}
