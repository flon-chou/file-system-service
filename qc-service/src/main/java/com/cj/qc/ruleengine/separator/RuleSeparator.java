package com.cj.qc.ruleengine.separator;

import com.cj.qc.model.MachineCheck;
import com.cj.qc.model.MachineCheckDetail;
import com.cj.qc.model.MachineCheckDetailInfo;
import com.cj.qc.model.dto.RuleCombDTO;
import com.cj.qc.ruleengine.buffer.*;
import com.cj.qc.ruleengine.config.RuleEnum;
import com.cj.qc.ruleengine.config.RuleType;
import com.cj.qc.ruleengine.resolver.*;
import com.cj.qc.tool.JsonTool;
import com.cj.qc.tool.StringTool;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author chenzhaowen
 * @description 规则分离器
 * @date 2019/3/25 10:52
 */
public class RuleSeparator {

  private final static int RANGE = RuleType.RANGE;

  //正则表达式中转义字符需要添加\\，调用 StringTool.escapeExprSpecialWord方法
  private final static String regEx="("+ StringTool.escapeExprSpecialWord(RuleEnum.OPEN_PAREN.getOperator())
          +"|"+StringTool.escapeExprSpecialWord(RuleEnum.CLOSE_PAREN.getOperator())
          +"|"+StringTool.escapeExprSpecialWord(RuleEnum.NO.getOperator())
          +"|"+StringTool.escapeExprSpecialWord(RuleEnum.BF.getOperator())
          +"|"+StringTool.escapeExprSpecialWord(RuleEnum.NE.getOperator())
          +"|"+StringTool.escapeExprSpecialWord(RuleEnum.EX.getOperator())
          +"|"+StringTool.escapeExprSpecialWord(RuleEnum.AND.getOperator())
          +"|"+StringTool.escapeExprSpecialWord(RuleEnum.OR.getOperator())
          +")";

  /**
   * 分离表达式
   * @param expression 需要拆分的表达式，如 (w1 and w2) or (w3 bf w4) and w5
   * @return
   */
  public static List<ExpressionBuffer> separateExpressionObject(String expression)throws Exception{

    //去除所有空格
    expression = expression.replaceAll(" ", "");
    //正则表达式：规则运算符
    Pattern p = Pattern.compile(regEx);
    Matcher m = p.matcher(expression);

    //按照规则运算符分割表达式
    String[] array=expression.split(regEx);

    List<ExpressionBuffer>  resList=new ArrayList<>();

    /*将运算符添加到列表中对应位置*/
    if(array.length > 0)
    {
      int count = 0;
      while(count < array.length)
      {
        boolean flag=m.find();
        if(flag)
        {
          NumberBuffer numberBuffer=getNumberBufferSpiltBybracket(array[count]);
          resList.add(
                  new ExpressionBuffer(
                          RuleEnum.DATA,numberBuffer.getValue(),numberBuffer.getValue(),numberBuffer.getValue(),numberBuffer.getCount()
                  )
          );

          RuleEnum ruleEnum=null;
          switch(m.group())
          {
            case RuleType.OPEN_PAREN :
              ruleEnum= RuleEnum.OPEN_PAREN;
              break;
            case RuleType.CLOSE_PAREN :
              ruleEnum= RuleEnum.CLOSE_PAREN;
              break;
            case RuleType.NO :
              ruleEnum= RuleEnum.NO;
              break;
            case RuleType.BF :
              ruleEnum= RuleEnum.BF;
              break;
            case RuleType.NE :
              ruleEnum= RuleEnum.NE;
              break;
            case RuleType.EX :
              ruleEnum= RuleEnum.EX;
              break;
            case RuleType.AND :
              ruleEnum= RuleEnum.AND;
              break;
            case RuleType.OR :
              ruleEnum= RuleEnum.OR;
              break;
            default:
              break;
          }

          resList.add(new ExpressionBuffer(ruleEnum, m.group()));
        }
        //补全最后一次循环若没有find到匹配的条件时，补上array[array.length-1]
        if(count==array.length-1 && !flag){
          NumberBuffer numberBuffer=getNumberBufferSpiltBybracket(array[count]);
          resList.add(
                  new ExpressionBuffer(
                          RuleEnum.DATA,numberBuffer.getValue(),numberBuffer.getValue(),numberBuffer.getValue(),numberBuffer.getCount()
                  )
          );
        }
        count++;
      }
    }
    //去除空字符串
    List<ExpressionBuffer> finalResList=new ArrayList<>();
    for (ExpressionBuffer expressionBuffer : resList) {
      if (!StringUtils.isBlank(expressionBuffer.getValue())) {
        finalResList.add(expressionBuffer);
      }
    }
    return finalResList;
  }

  public static NumberBuffer getNumberBufferSpiltBybracket(String originalValue) throws Exception{
    //判断是否有[]操作符，计算次数
    int beginNum=originalValue.indexOf("[");
    if(beginNum<0){
      return new NumberBuffer(originalValue,null);
    }else{
      int endNum=originalValue.indexOf("]");
      if(endNum<0){
        throw new Exception("[规则解析]次数格式'[]'错误");
      }
      String value=originalValue.substring(0, beginNum);
      Integer number=Integer.parseInt(originalValue.substring(beginNum+1, endNum));
      return new NumberBuffer(value,number);
    }
  }
  /**
   * 分离表达式
   * @param expression 需要拆分的表达式，如 (w1 and w2) or (w3 bf w4) and w5
   * @return
   */
  public static List<String> separateExpression(String expression){

    //去除所有空格
    expression = expression.replaceAll(" ", "");
    //正则表达式：规则运算符
    Pattern p = Pattern.compile(regEx);
    Matcher m = p.matcher(expression);

    //按照规则运算符分割表达式
    String[] array=expression.split(regEx);

    List<String>  resList=new ArrayList<>();

    /*将运算符添加到列表中对应位置*/
    if(array.length > 0)
    {
      int count = 0;
      while(count < array.length)
      {
        boolean flag=m.find();
        if(flag)
        {
          resList.add(array[count]);
          resList.add(m.group());
        }
        //补全最后一次循环若没有find到匹配的条件时，补上array[array.length-1]
        if(count==array.length-1 && !flag){
          resList.add(array[count]);
        }
        count++;
      }
    }

    //去除空字符串
    List<String> finalResList=new ArrayList<>();
    for (String str : resList) {
      if (!StringUtils.isBlank(str)) {
        finalResList.add(str);
      }
    }
    return finalResList;
  }

  /**
   *  分离表达式列表转换为后缀表达式列表
   * @return
   */
  public static List<ExpressionBuffer> exchangeToPostfixExpression(List<ExpressionBuffer> expressionBufferList)throws Exception{
    //保存后缀表达式列表
    List<ExpressionBuffer> outputList=new ArrayList<>();
    //保存操作符的栈
    Queue<ExpressionBuffer> operatorQueue=Collections.asLifoQueue(new LinkedList<>());
    /**
     * 转换为后缀表达式步骤：
     * （1）从左向右依次取得数据ch。
     *
     * （2）如果ch是操作数，直接输出。
     *
     * （3）如果ch是运算符（含左右括号），则：
     *       a：如果ch = '('，放入堆栈。
     *       b：如果ch = ')'，依次输出堆栈中的运算符，直到遇到'('为止。
     *       c：如果ch不是')'或者'('，那么就和堆栈顶点位置的运算符top做优先级比较。
     *           1：如果ch优先级比top高，那么将ch放入堆栈。
     *           2：如果ch优先级低于或者等于top，那么输出top，然后将ch放入堆栈。
     */
    for (ExpressionBuffer expressionBuffer:expressionBufferList) {
      if(expressionBuffer.getRuleEnum()==RuleEnum.DATA){
        outputList.add(expressionBuffer);
      }else if(expressionBuffer.getRuleEnum()==RuleEnum.OPEN_PAREN){
        operatorQueue.offer(expressionBuffer);
      }else if(expressionBuffer.getRuleEnum()==RuleEnum.CLOSE_PAREN){
        while(true){
          ExpressionBuffer buffer=operatorQueue.poll();
          if(buffer.getRuleEnum()==RuleEnum.OPEN_PAREN){
            break;
          }
          outputList.add(buffer);
        }
      }else {
        //运算符优先级比较
        ExpressionBuffer buffer=operatorQueue.peek();
        if(buffer==null ||
                buffer.getRuleEnum()==RuleEnum.OPEN_PAREN ||
                buffer.getRuleEnum()!=RuleEnum.OPEN_PAREN  && expressionBuffer.getRuleEnum().getDegree()>buffer.getRuleEnum().getDegree()){
          //1：如果ch优先级比top高，那么将ch放入堆栈。
          operatorQueue.offer(expressionBuffer);
        }else{
          // 2：如果ch优先级低于或者等于top，那么输出top，然后将ch放入堆栈。
          ExpressionBuffer buffer2=operatorQueue.poll();
          outputList.add(buffer2);
          operatorQueue.offer(expressionBuffer);
        }
      }
    }
    //最后将栈中剩余的运算符依次弹出并压入outputList
    while(true){
      ExpressionBuffer buffer=operatorQueue.poll();
      if(buffer==null){
        break;
      }
      outputList.add(buffer);
    }
    return outputList;
  }

  /**
   * 计算后缀表达式
   * @param expressionBufferList
   * @param sourceBuffer
   * @param range 间距
   * @return
   * @throws Exception
   */
  public static MachineCheck calculatePostfixExpression(List<ExpressionBuffer> expressionBufferList,
                                                        SourceBuffer sourceBuffer, int range) throws Exception{
    MachineCheck machineCheck=new MachineCheck();

    int num=1;
    List<MachineCheckDetail> details=new ArrayList<>();

    if(expressionBufferList.size()==0){
      throw new Exception("[规则解析]规则不能为空");
    }else if(expressionBufferList.size()==1){
      if(expressionBufferList.get(0).getRuleEnum()!=RuleEnum.DATA){
        throw new Exception("[规则解析]规则格式错误");
      }
      //保存操作数的栈
      Queue<ExpressionBuffer> dataQueue1=Collections.asLifoQueue(new LinkedList<>());
      dataQueue1.offer(expressionBufferList.get(0));
      CommonExpressionBuffer commonExpressionBuffer=ContainResolver.execute(dataQueue1, sourceBuffer, num,range);
      details.add(commonExpressionBuffer.getMachineCheckDetail());
      machineCheck=new MachineCheck(commonExpressionBuffer.getExpressionBuffer().getBoolRes(),details);
    }else{
      //保存操作数的栈
      Queue<ExpressionBuffer> dataQueue=Collections.asLifoQueue(new LinkedList<>());
      /**
       *  后缀表达式计算步骤：
       * （1）从左向右扫描表达式，一个取出一个数据data
       * （2）如果data是操作数，就压入堆栈
       * （3）如果data是操作符，就从堆栈中弹出此操作符需要用到的数据的个数，进行运算，然后把结果压入堆栈
       * （4）如果数据处理完毕，堆栈中最后剩余的数据就是最终结果。
       */
      for (ExpressionBuffer expressionBuffer:expressionBufferList) {
        if(expressionBuffer.getRuleEnum()==RuleEnum.DATA){
          dataQueue.offer(expressionBuffer);
        }else{
          CommonExpressionBuffer commonExpressionBuffer=new CommonExpressionBuffer();
          switch(expressionBuffer.getRuleEnum().getOperator())
          {
            case RuleType.NO :
              commonExpressionBuffer= NoResolver.execute(dataQueue, sourceBuffer, num,range);
              break;
            case RuleType.BF :
              commonExpressionBuffer=BeforeResolver.execute(dataQueue, sourceBuffer, num, range);
              break;
            case RuleType.NE :
              commonExpressionBuffer=NearResolver.execute(dataQueue, sourceBuffer, num, range);
              break;
            case RuleType.EX :
              commonExpressionBuffer= ExceptResolver.execute(dataQueue, sourceBuffer, num);
              break;
            case RuleType.AND :
              commonExpressionBuffer = AndResolver.execute(dataQueue, sourceBuffer, num,range);
              break;
            case RuleType.OR :
              commonExpressionBuffer= OrResolver.execute(dataQueue, sourceBuffer, num,range);
              break;
            default:
              break;
          }

          //将结果推进栈
          dataQueue.offer(commonExpressionBuffer.getExpressionBuffer());
          //保存结果信息
          details.add(commonExpressionBuffer.getMachineCheckDetail());
          //计数+1
          num++;

        }
      }

      //判断是否栈中最后剩余的数据就是最终结果
      ExpressionBuffer resBuffer=dataQueue.poll();
      if(dataQueue.poll()!=null || resBuffer.getRuleEnum()!=RuleEnum.BOOL_RESULT){
        throw new Exception("[规则解析]异常报错");
      }

      machineCheck=new MachineCheck(resBuffer.getBoolRes(),details);
    }

    return machineCheck;
  }

  /**
   * 规则表达式计算总入口
   * @param ruleCombDTO 规则组合类：规则与模型中规则设置参数组合
   * @param sourceBuffer 转换成指定类型的源匹配文本
   * @return
   * @throws Exception
   */
  public static MachineCheck execute(RuleCombDTO ruleCombDTO, SourceBuffer sourceBuffer)throws Exception{
    //获取规则表达式（中缀表达式）
    String ruleRegex=ruleCombDTO.getRuleLogic();
    //间距默认为10，若有设置取设置的值
    int range=ruleCombDTO.getRange()==null?10:ruleCombDTO.getRange();
    List<ExpressionBuffer> separateExpressionBuffers=separateExpressionObject(ruleRegex);
    List<ExpressionBuffer> postfixExpressionBuffers=exchangeToPostfixExpression(separateExpressionBuffers);
    //todo:删除打印
//    String result="";
//    for (ExpressionBuffer buffer:postfixExpressionBuffers) {
//      result+=buffer.getValue()+" ";
//    }
//    System.out.println(result);

    MachineCheck machineCheck=calculatePostfixExpression(postfixExpressionBuffers,sourceBuffer,range);
    return machineCheck;
  }

  /**
   * 获取一定出栈数量下的操作数栈的栈中数据
   * @param pollNum 出栈数量
   * @param operatorException 报错信息的操作符
   * @param dataQueue 操作数栈
   * @return
   * @throws Exception
   */
  public static List<ExpressionBuffer> pollDataQueue(int pollNum, String operatorException,Queue<ExpressionBuffer> dataQueue)throws Exception{
    List<ExpressionBuffer> queueExpressionBuffers=new ArrayList<>();
    for (int i = 0; i < pollNum; i++) {
      if(dataQueue.peek()==null){
        throw new Exception(operatorException+" 取栈异常！");
      }
      queueExpressionBuffers.add(dataQueue.poll());
    }
    return queueExpressionBuffers;
  }

  /**
   * 获取源表达式
   * @param dataQueueExpressionBuffers 操作数栈
   * @param operator 操作符
   * @return
   * @throws Exception
   */
  public static String getSourceExpression(List<ExpressionBuffer> dataQueueExpressionBuffers,String operator)throws Exception{

    if(dataQueueExpressionBuffers.size()==1){
      //根据是否有操作符判断是否加括号
      boolean flag=separateExpression(dataQueueExpressionBuffers.get(0).getSourceExpression()).size()>1?true:false;
      return operator+(flag?"(":"")+dataQueueExpressionBuffers.get(0).getSourceExpression()+(flag?")":"");
    }else if(dataQueueExpressionBuffers.size()==2){
      boolean flag1=separateExpression(dataQueueExpressionBuffers.get(1).getSourceExpression()).size()>1?true:false;
      boolean flag0=separateExpression(dataQueueExpressionBuffers.get(0).getSourceExpression()).size()>1?true:false;
      return (flag1?"(":"")+dataQueueExpressionBuffers.get(1).getSourceExpression()+(flag1?") ":" ")+
              operator+(flag0?" (":" ")+dataQueueExpressionBuffers.get(0).getSourceExpression()+(flag0?")":"");
    }else{
      throw new Exception("操作符："+operator+" ，获取源表达式异常！");
    }
  }

  /**
   * 获取源后缀表达式
   * @param dataQueueExpressionBuffers 操作数栈
   * @param operator 操作符
   * @return
   * @throws Exception
   */
  public static String getSourcePostfixExpression(List<ExpressionBuffer> dataQueueExpressionBuffers,String operator)throws Exception{
    if(dataQueueExpressionBuffers.size()==1){
      return dataQueueExpressionBuffers.get(0).getSourcePostfixExpression()+" "+operator;
    }else if(dataQueueExpressionBuffers.size()==2){
      return dataQueueExpressionBuffers.get(1).getSourcePostfixExpression()+" "+
              dataQueueExpressionBuffers.get(0).getSourcePostfixExpression()+" "+operator;
    }else{
      throw new Exception("操作符："+operator+" ，获取源后缀表达式异常！");
    }
  }

  /**
   * 对ExpressionBuffer中ruleEnum属性为DATA进一步分类转换
   * @param expressionBuffer
   * @return
   * @throws Exception
   */
  public static ExpressionBuffer exchangeExpressionBufferOnDataOfRuleEnum(ExpressionBuffer expressionBuffer) throws Exception{
    if(expressionBuffer.getRuleEnum()==RuleEnum.BOOL_RESULT){
      return expressionBuffer;
    }
    if(expressionBuffer.getRuleEnum()==RuleEnum.DATA){
      String s=expressionBuffer.getValue();
      //判断字符串左右开始位置是否是{}
      if(s.indexOf("{")==0 && s.indexOf("}")==(s.length()-1)){
        if(s.contains("/") && !s.contains(",")){
          expressionBuffer.setRuleEnum(RuleEnum.DATA_SLASH);
          return expressionBuffer;
        }else if(!s.contains("/") && s.contains(",")){
          expressionBuffer.setRuleEnum(RuleEnum.DATA_COMMA);
          return expressionBuffer;
        }else{
          throw new Exception("{}内格式错误");
        }
      }else{
        //说明是除{w1,w2,w3}和{w1/w2/w3}外的DATA类型
        return expressionBuffer;
      }
    }else {
      return expressionBuffer;
    }
  }

  /**
   * 转换bf或be的两边数据为一一对应的BorderRoleBuffer列表
   * @param leftExpressionBuffer
   * @param rightExpressionBuffer
   * @param operator
   * @return
   * @throws Exception
   */
  public static List<BorderRoleBuffer> exchangeToBorderRoleBufferByBeforeNear (
          ExpressionBuffer leftExpressionBuffer,ExpressionBuffer rightExpressionBuffer,String operator)throws Exception{
    //数据转换
    ExpressionBuffer leftExExpressionBuffer=RuleSeparator.exchangeExpressionBufferOnDataOfRuleEnum(leftExpressionBuffer);
    ExpressionBuffer rightExExpressionBuffer=RuleSeparator.exchangeExpressionBufferOnDataOfRuleEnum(rightExpressionBuffer);
    //转换成RoleBuffer列表
    List<RoleBuffer> leftRoleBuffers = exchangeToRoleBufferByBeforeNear(leftExExpressionBuffer, operator);
    List<RoleBuffer> rightRoleBuffers = exchangeToRoleBufferByBeforeNear(rightExExpressionBuffer, operator);

    /**
     * 将如左边匹配内容w与右边匹配内容转换成左右对应内容格式的BorderRoleBuffer，
     * 如{w1/w2}与{w3/A-w4}转成 w1与w2;w1与w3;w2与w3;w2与A-w4;
     */
    List<BorderRoleBuffer> borderRoleBuffers=new ArrayList<>();
    for (RoleBuffer leftRoleBuffer:leftRoleBuffers) {
      for (RoleBuffer rightRoleBuffer:rightRoleBuffers) {
        BorderRoleBuffer borderRoleBuffer=new BorderRoleBuffer(leftRoleBuffer,rightRoleBuffer);
        borderRoleBuffers.add(borderRoleBuffer);
      }
    }
    return borderRoleBuffers;
  }

  /**
   * 计算and、or左右两个操作数其中一个的匹配结果或no的一个匹配结果如w1 {w1/A-w2/w3} {w1,A-w2,w3} true其中一种的结果
   * @param expressionBuffer
   * @param sourceBuffer
   * @param operator
   * @param range 间距
   * @return
   * @throws Exception
   */
  public static ResolverResBuffer getResolverResBufferByAndOrNo(ExpressionBuffer expressionBuffer,
                                                                SourceBuffer sourceBuffer,
                                                                String operator, int range) throws Exception{
    //数据转换
    ExpressionBuffer exExpressionBuffer=RuleSeparator.exchangeExpressionBufferOnDataOfRuleEnum(expressionBuffer);

    if(exExpressionBuffer.getRuleEnum()==RuleEnum.BOOL_RESULT){
      //规则解析结果详情类集合
      List<MachineCheckDetailInfo> machineCheckDetailInfos=new ArrayList<>();
      return new ResolverResBuffer(machineCheckDetailInfos,exExpressionBuffer.getBoolRes());
    }else if(exExpressionBuffer.getRuleEnum()==RuleEnum.DATA){
      List<RoleBuffer> roleBuffers=RuleSeparator.exchangeToRoleBuffer(
              new ArrayList<String>(Arrays.asList(exExpressionBuffer.getValue())));
      return getBySimpleDataOrSlashData(roleBuffers,sourceBuffer,exExpressionBuffer.getValue());
    }else if(exExpressionBuffer.getRuleEnum()==RuleEnum.DATA_SLASH){
      //分离如{A-w1/B-w2/w3}，并转换为roleBuffers
      List<RoleBuffer> roleBuffers=RuleSeparator.exchangeToRoleBufferByBrace(
              exExpressionBuffer.getValue(),RuleEnum.DATA_SLASH.getOperator());
      return getBySimpleDataOrSlashData(roleBuffers,sourceBuffer,exExpressionBuffer.getValue());
    }else if(exExpressionBuffer.getRuleEnum()==RuleEnum.DATA_COMMA){
      return CommaResolver.getByCommaData(exExpressionBuffer.getValue(),sourceBuffer,range,exExpressionBuffer.getValue());
    }else{
      throw new Exception(operator+"解析器无法解析规则枚举为运算符的数据");
    }
  }

  /**
   * 计算Except左右两个操作数其中一个的匹配结果如w1 {w1/A-w2/w3}其中一种的结果
   * @param expressionBuffer
   * @param sourceBuffer
   * @param operator
   * @return
   * @throws Exception
   */
  public static ResolverResBuffer getResolverResBufferByExcept(ExpressionBuffer expressionBuffer,
                                                                SourceBuffer sourceBuffer,
                                                                String operator,String currentContent) throws Exception{
    //数据转换
    ExpressionBuffer exExpressionBuffer=RuleSeparator.exchangeExpressionBufferOnDataOfRuleEnum(expressionBuffer);
    if(exExpressionBuffer.getRuleEnum()==RuleEnum.DATA){
      List<RoleBuffer> roleBuffers=RuleSeparator.exchangeToRoleBuffer(
              new ArrayList<String>(Arrays.asList(exExpressionBuffer.getValue())));
      return getBySimpleDataOrSlashData(roleBuffers,sourceBuffer,currentContent);
    }else if(exExpressionBuffer.getRuleEnum()==RuleEnum.DATA_SLASH){
      //分离如{A-w1/B-w2/w3}，并转换为roleBuffers
      List<RoleBuffer> roleBuffers=RuleSeparator.exchangeToRoleBufferByBrace(
              exExpressionBuffer.getValue(),RuleEnum.DATA_SLASH.getOperator());
      return getBySimpleDataOrSlashData(roleBuffers,sourceBuffer,currentContent);
    }else{
      throw new Exception(operator+"解析器无法解析规则枚举为运算符的数据");
    }
  }

  /**
   * 获取RuleEnum.DATA如w1 或 RuleEnum.DATA_SLASH如{w1/A-w2/w3}的匹配结果
   * @param roleBuffers
   * @param sourceBuffer
   * @return
   */
  public static ResolverResBuffer getBySimpleDataOrSlashData( List<RoleBuffer> roleBuffers,SourceBuffer sourceBuffer,String currentContent){
    //规则解析结果详情类集合
    List<MachineCheckDetailInfo> machineCheckDetailInfos=new ArrayList<>();
    //每个匹配内容的boolean结果存储列表
    Boolean finalBoolean=false;
    //提取w1 w2 w3
    List<String> childs=roleBuffers.stream().map(RoleBuffer::getChildRegex).collect(Collectors.toList());
    //计算源文件字符串是否包含childs
    SimpleContainBuffer simpleContainBuffer= OrResolver.resolveObject(childs,sourceBuffer.getSource());

    if(simpleContainBuffer.getBoolRes()){
      //获取isValid是true的结果集
      List<ContainBuffer> containBuffers=ContainBuffer.removeFalse(simpleContainBuffer.getContainBuffers());
      //遍历计算时间、角色等信息
      machineCheckDetailInfos=
              RuleSeparator.getTimeAndRoleOnContainBuffersAndRoleBuffers(containBuffers,roleBuffers,sourceBuffer,currentContent);
      //添加运算结果boolean值
      finalBoolean=machineCheckDetailInfos.size()>0?true:false;
    }

    return new ResolverResBuffer(machineCheckDetailInfos,finalBoolean);
  }

  /**
   * 将bf或ne两边的数据如 w1 或{w1/A-w2/w3}参数转换成带角色信息的RoleBuffer集合
   * @param exExpressionBuffer
   * @param operator
   * @return
   * @throws Exception
   */
  public static List<RoleBuffer> exchangeToRoleBufferByBeforeNear(ExpressionBuffer exExpressionBuffer,String operator) throws Exception{
    if(exExpressionBuffer.getRuleEnum()==RuleEnum.DATA){
      return RuleSeparator.exchangeToRoleBuffer(
              new ArrayList<String>(Arrays.asList(exExpressionBuffer.getValue())));
    }else if(exExpressionBuffer.getRuleEnum()==RuleEnum.DATA_SLASH){
      //分离如{A-w1/B-w2/w3}，并转换为roleBuffers
      return RuleSeparator.exchangeToRoleBufferByBrace(
              exExpressionBuffer.getValue(),RuleEnum.DATA_SLASH.getOperator());
    }else{
      throw new Exception(operator+"解析器无法解析规则枚举为运算符的数据");
    }
  }

  /**
   * 将这些如 A-w1，B-w2,w3组成的sourceStrList参数转换成带角色信息的RoleBuffer集合
   * @param sourceStrList
   * @return
   * @throws Exception
   */
  public static List<RoleBuffer> exchangeToRoleBuffer(List<String> sourceStrList) throws Exception{
    List<RoleBuffer> roleBuffers=new ArrayList<>();
    for (String s:sourceStrList) {
      ContainBuffer containBufferA = ContainResolver.resolve(RuleEnum.A.getOperator(), s);
      ContainBuffer containBufferB = ContainResolver.resolve(RuleEnum.B.getOperator(), s);
      if((containBufferA.isValid() && containBufferA.getLocationBuffers().get(0).getStart()==0)||
              (containBufferB.isValid() && containBufferB.getLocationBuffers().get(0).getStart()==0)){
        RoleBuffer roleBuffer=new RoleBuffer(containBufferA.isValid()?RuleEnum.A:RuleEnum.B,s,s.substring(2));
        roleBuffers.add(roleBuffer);
      }else{
        RoleBuffer roleBuffer=new RoleBuffer(RuleEnum.AB,s,s);
        roleBuffers.add(roleBuffer);
      }
    }
    return roleBuffers;
  }

  /**
   * 将这些如 {A-w1，B-w2,w3} 或 {A-w1/B-w2/w3}由大括号组成的sourceStr参数转换成带角色信息的RoleBuffer集合
   * @param sourceStr {A-w1，B-w2,w3} 或 {A-w1/B-w2/w3}
   * @param regEx , /这些分割符
   * @return
   * @throws Exception
   */
  public static List<RoleBuffer> exchangeToRoleBufferByBrace(String sourceStr,String regEx) throws Exception{
    //分离如{A-w1/B-w2/w3}，并转换为roleBuffers
    String toValue=sourceStr.substring(1,sourceStr.length()-1);
    List<String> valueSpiltList=StringTool.splitString(regEx,toValue);
    return  exchangeToRoleBuffer(valueSpiltList);
  }

  public static HashMap<String,RoleBuffer> toMapOfRoleBuffers(List<RoleBuffer> roleBuffers){
    HashMap<String,RoleBuffer> m=new HashMap<>();
    for (RoleBuffer roleBuffer:roleBuffers) {
      m.put(roleBuffer.getChildRegex(), roleBuffer);
    }
    return m;
  }

  /**
   * 根据位置和角色判断源文件该位置是否是指定角色
   * @param role
   * @param locationBuffer
   * @param sourceBuffer
   * @return
   */
  public static boolean isExistByRoleAndLocation(String role,LocationBuffer locationBuffer,SourceBuffer sourceBuffer){
    boolean flag=true;
    if(RuleType.A.equals(role) || RuleType.B.equals(role)){
      for (int i = locationBuffer.getStart(); i < locationBuffer.getEnd(); i++) {
        if(!role.equals(sourceBuffer.getRoleList().get(i))){
          flag=false;
          break;
        }
      }
    }
    return flag;
  }

  /**
   * 遍历计算时间、角色等信息<bf、ne>
   * @param borderLocationBuffers
   * @param borderRoleBuffer
   * @param sourceBuffer
   * @return
   */
  public static List<MachineCheckDetailInfo> getTimeAndRoleOnBorderLocationBuffersAndBorderRoleBuffer(
          List<BorderLocationBuffer> borderLocationBuffers, BorderRoleBuffer borderRoleBuffer, SourceBuffer sourceBuffer,String matchStr,String currentContent){

    List<MachineCheckDetailInfo> machineCheckDetailInfos=new ArrayList<>();

    //左边角色
    RoleBuffer leftRoleBuffer = borderRoleBuffer.getLeftRoleBuffer();
    String leftRole=(leftRoleBuffer.getRuleEnum()==RuleEnum.AB)?RuleType.AB:((leftRoleBuffer.getRuleEnum()==RuleEnum.A)?RuleType.A:RuleType.B);
    //右边角色
    RoleBuffer rightRoleBuffer = borderRoleBuffer.getRightRoleBuffer();
    String rightRole=(rightRoleBuffer.getRuleEnum()==RuleEnum.AB)?RuleType.AB:((rightRoleBuffer.getRuleEnum()==RuleEnum.A)?RuleType.A:RuleType.B);

    //遍历计算时间、角色等信息
    for (BorderLocationBuffer borderLocationBuffer:borderLocationBuffers) {
      //左边位置
      LocationBuffer leftLocationBuffer = borderLocationBuffer.getLeftBorder();
      boolean leftFlag=isExistByRoleAndLocation(leftRole,leftLocationBuffer,sourceBuffer);
      //右边位置
      LocationBuffer rightLocationBuffer = borderLocationBuffer.getRightBorder();
      boolean rightFlag=isExistByRoleAndLocation(rightRole,rightLocationBuffer,sourceBuffer);

      //保存结果信息
      if(leftFlag && rightFlag){
        MachineCheckDetailInfo machineCheckDetailInfo=new MachineCheckDetailInfo();
        machineCheckDetailInfo.setLeftContent(leftRoleBuffer.getChildRegex());
        machineCheckDetailInfo.setLeftOpenLocation(leftLocationBuffer.getStart());
        machineCheckDetailInfo.setLeftCloseLocation(leftLocationBuffer.getEnd()-1);
        machineCheckDetailInfo.setLeftOpenTime(sourceBuffer.getTimeList().get(leftLocationBuffer.getStart()));
        machineCheckDetailInfo.setLeftCloseTime(sourceBuffer.getTimeList().get(leftLocationBuffer.getEnd()-1));
        machineCheckDetailInfo.setLeftRole(RuleType.AB.equals(leftRole)?null:leftRole);

        machineCheckDetailInfo.setRightContent(rightRoleBuffer.getChildRegex());
        machineCheckDetailInfo.setRightOpenLocation(rightLocationBuffer.getStart());
        machineCheckDetailInfo.setRightCloseLocation(rightLocationBuffer.getEnd()-1);
        machineCheckDetailInfo.setRightOpenTime(sourceBuffer.getTimeList().get(rightLocationBuffer.getStart()));
        machineCheckDetailInfo.setRightCloseTime(sourceBuffer.getTimeList().get(rightLocationBuffer.getEnd()-1));
        machineCheckDetailInfo.setRightRole(RuleType.AB.equals(rightRole)?null:rightRole);

        machineCheckDetailInfo.setFullExpression(matchStr);
        machineCheckDetailInfo.setFullContent(sourceBuffer.getSource().substring(leftLocationBuffer.getStart(), rightLocationBuffer.getEnd()));
        machineCheckDetailInfo.setFullOpenLocation(leftLocationBuffer.getStart());
        machineCheckDetailInfo.setFullCloseLocation(rightLocationBuffer.getEnd()-1);
        machineCheckDetailInfo.setFullOpenTime(sourceBuffer.getTimeList().get(leftLocationBuffer.getStart()));
        machineCheckDetailInfo.setFullCloseTime(sourceBuffer.getTimeList().get(rightLocationBuffer.getEnd()-1));

        machineCheckDetailInfo.setSeriesType(0);
        //添加当前计算内容
        machineCheckDetailInfo.setCurrentContent(currentContent);

        machineCheckDetailInfos.add(machineCheckDetailInfo);
      }
    }
    return machineCheckDetailInfos;
  }

  /**
   * 遍历计算时间、角色等信息
   * @param containBuffers
   * @param roleBuffers
   * @param sourceBuffer
   * @return
   */
  public static List<MachineCheckDetailInfo> getTimeAndRoleOnContainBuffersAndRoleBuffers(
          List<ContainBuffer> containBuffers, List<RoleBuffer> roleBuffers, SourceBuffer sourceBuffer,String currentContent){

    List<MachineCheckDetailInfo> machineCheckDetailInfos=new ArrayList<>();

    HashMap<String,RoleBuffer> roleBuffersHashMap=RuleSeparator.toMapOfRoleBuffers(roleBuffers);
    //遍历计算时间、角色等信息
    for (ContainBuffer containBuffer:containBuffers) {
      RoleBuffer roleBuffer=roleBuffersHashMap.get(containBuffer.getMatchingContent());

      String role=(roleBuffer.getRuleEnum()==RuleEnum.AB)?RuleType.AB:((roleBuffer.getRuleEnum()==RuleEnum.A)?RuleType.A:RuleType.B);

      if(roleBuffer.getRuleEnum()==RuleEnum.AB){
        //保存规则解析结果详情
        for (LocationBuffer locationBuffer:containBuffer.getLocationBuffers()) {
          MachineCheckDetailInfo machineCheckDetailInfo=new MachineCheckDetailInfo();
          machineCheckDetailInfo.setBaseContent(containBuffer.getMatchingContent());
          machineCheckDetailInfo.setBaseOpenLocation(locationBuffer.getStart());
          machineCheckDetailInfo.setBaseCloseLocation(locationBuffer.getEnd()-1);
          machineCheckDetailInfo.setBaseOpenTime(sourceBuffer.getTimeList().get(locationBuffer.getStart()));
          machineCheckDetailInfo.setBaseCloseTime(sourceBuffer.getTimeList().get(locationBuffer.getEnd()-1));
          machineCheckDetailInfo.setSeriesType(0);
          //添加当前计算内容
          machineCheckDetailInfo.setCurrentContent(currentContent);
          machineCheckDetailInfos.add(machineCheckDetailInfo);


        }
      }else {
        //保存规则解析结果详情
        for (LocationBuffer locationBuffer:containBuffer.getLocationBuffers()) {
          boolean flag=true;
          for (int i = locationBuffer.getStart(); i < locationBuffer.getEnd(); i++) {
            if(!role.equals(sourceBuffer.getRoleList().get(i))){
              flag=false;
              break;
            }
          }
          if(flag){
            MachineCheckDetailInfo machineCheckDetailInfo=new MachineCheckDetailInfo();
            machineCheckDetailInfo.setBaseContent(containBuffer.getMatchingContent());
            machineCheckDetailInfo.setBaseOpenLocation(locationBuffer.getStart());
            machineCheckDetailInfo.setBaseCloseLocation(locationBuffer.getEnd()-1);
            machineCheckDetailInfo.setBaseOpenTime(sourceBuffer.getTimeList().get(locationBuffer.getStart()));
            machineCheckDetailInfo.setBaseCloseTime(sourceBuffer.getTimeList().get(locationBuffer.getEnd()-1));
            machineCheckDetailInfo.setBaseRole(role);
            machineCheckDetailInfo.setSeriesType(0);
            //添加当前计算内容
            machineCheckDetailInfo.setCurrentContent(currentContent);
            machineCheckDetailInfos.add(machineCheckDetailInfo);
          }
        }
      }
    }
    return machineCheckDetailInfos;
  }

  /**
   * 规则解析结果转换json
   * @param machineCheck
   * @return
   */
  public static RuleResJsonBuffer exchangeToRuleResJson(MachineCheck machineCheck){
    RuleResJsonBuffer ruleResJsonBuffer=new RuleResJsonBuffer();
    ruleResJsonBuffer.setIsPass(machineCheck.getBoolRes()?1:0);

    List<RuleResBuffer> ruleResBuffers=new ArrayList<>();
    HashMap<String,List<RuleResTimeBuffer>> map=new HashMap<>();

    List<MachineCheckDetail> details = machineCheck.getDetails();
    for (MachineCheckDetail detail:details) {

      List<MachineCheckDetailInfo> infos=detail.getInfos();

      if(RuleType.NO.equals(detail.getOperator()) || RuleType.AND.equals(detail.getOperator())
              || RuleType.OR.equals(detail.getOperator()) || RuleType.CONTAIN.equals(detail.getOperator())){
        if(infos.size()>0){
          if(infos.get(0).getSeriesType()==0){
            //包含关系
            for (MachineCheckDetailInfo info:infos) {
              exchangeToRuleResCommon(map,info);
            }
          }else{
            //{w1,,w2,w3}顺序串联关系
            exchangeToRuleResComma(map,infos);
          }
        }
      }

      if(RuleType.BF.equals(detail.getOperator()) || RuleType.NE.equals(detail.getOperator())){
        if(infos.size()>0){
          //包含关系
          for (MachineCheckDetailInfo info:infos) {
            exchangeToRuleResBorder(map,info);
          }
        }
      }

      if(RuleType.EX.equals(detail.getOperator())){
        if(infos.size()>0){
          for (MachineCheckDetailInfo info:infos) {
            exchangeToRuleResCommon(map,info);
          }
        }
      }
    }
    for(Map.Entry<String,List<RuleResTimeBuffer>> entry : map.entrySet()){
      ruleResBuffers.add(new RuleResBuffer(entry.getKey(),entry.getValue()));
    }

    return new RuleResJsonBuffer(machineCheck.getBoolRes()?1:0,JsonTool.listToJson(ruleResBuffers));
  }

  /**
   * 转换包含关系的数据
   * @param info
   * @return
   */
  public static void exchangeToRuleResCommon(HashMap<String,List<RuleResTimeBuffer>> map,MachineCheckDetailInfo info){
    boolean contains = map.containsKey(info.getCurrentContent());
    if(contains){
      map.get(info.getCurrentContent()).add(new RuleResTimeBuffer(info.getBaseOpenTime(),info.getBaseCloseTime()));
    }else{
      List<RuleResTimeBuffer> ruleResTimeBuffers=new ArrayList<>();
      ruleResTimeBuffers.add(new RuleResTimeBuffer(info.getBaseOpenTime(),info.getBaseCloseTime()));
      map.put(info.getCurrentContent(),ruleResTimeBuffers);
    }
  }

  /**
   * 转换{w1,,w2,w3}顺序串联关系的数据
   * @param infos
   * @return
   */
  public static void exchangeToRuleResComma(HashMap<String,List<RuleResTimeBuffer>> map,List<MachineCheckDetailInfo> infos){
    boolean contains = map.containsKey(infos.get(0).getCurrentContent());
    if(contains){
      map.get(infos.get(0).getCurrentContent()).add(
              new RuleResTimeBuffer(infos.get(0).getBaseOpenTime(),infos.get(infos.size()-1).getBaseCloseTime()));
    }else{
      List<RuleResTimeBuffer> ruleResTimeBuffers=new ArrayList<>();
      ruleResTimeBuffers.add(new RuleResTimeBuffer(infos.get(0).getBaseOpenTime(),infos.get(infos.size()-1).getBaseCloseTime()));
      map.put(infos.get(0).getCurrentContent(),ruleResTimeBuffers);
    }
  }

  /**
   * 转换bf、ne边界关系的数据
   * @param info
   * @return
   */
  public static void exchangeToRuleResBorder(HashMap<String,List<RuleResTimeBuffer>> map,MachineCheckDetailInfo info){
    boolean contains = map.containsKey(info.getCurrentContent());
    if(contains){
      map.get(info.getCurrentContent()).add(new RuleResTimeBuffer(info.getFullOpenTime(),info.getFullCloseTime()));
    }else{
      List<RuleResTimeBuffer> ruleResTimeBuffers=new ArrayList<>();
      ruleResTimeBuffers.add(new RuleResTimeBuffer(info.getFullOpenTime(),info.getFullCloseTime()));
      map.put(info.getCurrentContent(),ruleResTimeBuffers);
    }
  }

  public static void main(String[] args) throws Exception{
    //todo:测试后删除
//    String test="(w1 and w2) or (w3 bf w4) and w5 or no(w6 ne{w7/w8/w9})";
//    List<String> res=separateExpression(test);
//    List<ExpressionBuffer> res2=separateExpressionObject(test);
//    List<ExpressionBuffer> expressionBuffers=exchangeToPostfixExpression(res2);
//    String result="";
//    for (ExpressionBuffer buffer:expressionBuffers) {
//      result+=buffer.getValue()+" ";
//    }
//    System.out.println(result);
//
//    String b="{w1,w2,w3}";
//    int aa=b.length();
//    int i=b.indexOf("}");
//
//    List<String> cc=new ArrayList<>();
//    cc.add("A-w1");
//    cc.add("B-w2");
//    cc.add("w1");
//    List<RoleBuffer> roleBuffers=exchangeToRoleBuffer(cc);
//
//    String value="{w1,w2,w3}";
//    String toValue=value.substring(1,value.length()-1);
//
//    List<String> r=new ArrayList<>();
//    r.add("A-w1");
//    r.add("B-w2");
//    r.add("w1");
//    List<String> r1=r;
//    r=new ArrayList<>();
//    System.out.println(r1.size());
//    r.add("w45");
//    System.out.println(r.size());

//    getNumberBufferSpiltBybracket("A-今[3]");

//    String regex="B-今天 and B-日子 or ";
//    String regex="(A-今 or 天) and {好,日,B-今} and {是/很} and no{很大/好人} and {好日/好} ex 好日子 or 是 bf {A-个/B-好}";
    String regex="A-今 or 天 or {A-是/很} or {好,日,B-今} or 是 bf A-个 or A-今天 ex 今天很";
//    String regex="今天很二";
    SourceBuffer sourceBuffer=new SourceBuffer();
    String source="今天是个好日子今天很好";
    String role="AAAAAAABBBB";
    String time="1,2,3,4,5,6,7,8,9,10,11";
    List<String> sourceList=Arrays.asList(source.split(""));
    List<String> roleList=Arrays.asList(role.split(""));
    List<String> timeList=Arrays.asList(time.split(","));
    sourceBuffer.setSource(source);
    sourceBuffer.setSourceList(sourceList);
    sourceBuffer.setRoleList(roleList);
    sourceBuffer.setTimeList(timeList);
    RuleCombDTO ruleCombDTO=new RuleCombDTO();
    ruleCombDTO.setRange(10);
    ruleCombDTO.setRuleLogic(regex);
    ruleCombDTO.setScore(50);
    MachineCheck machineCheck=execute(ruleCombDTO,sourceBuffer);
    RuleResJsonBuffer ruleResJsonBuffer=exchangeToRuleResJson(machineCheck);
    System.out.println(1);


  }
}
