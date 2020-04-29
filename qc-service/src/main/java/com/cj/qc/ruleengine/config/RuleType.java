package com.cj.qc.ruleengine.config;

/**
 * @author chenzhaowen
 * @description 规则运算符类型
 * @date 2019/3/21 16:37
 */
public class RuleType {
  /**
   * (、) 用于表达式的组合
   */
  public static final String OPEN_PAREN="(";
  public static final String CLOSE_PAREN=")";
  public static final String OPEN_BRACKET="[";
  public static final String CLOSE_BRACKET="]";
  public static final String OPEN__BRACE="{";
  public static final String CLOSE_BRACE="}";
  /**
   *  ,用于多个关键词顺序关系的串联，必须位于()之内，至少两个关键词，例如“(W1,W2,W3)”：W1后面有W2,W2后面有W3 
   */
  public static final String COMMA=",";
  /**
   * /用于多个同义词的串联，至少两个关键词，例如“(W1/W2/W3)”：W1、W2、W3是同义词
   */
  public static final String VIRGULE="/";

  /**
   * NO 表示取反，不存在满足表达式的情况
   */
  public static final String NO="no";
  /**
   * BF 表示之前（before）关系，，例如“W1 bf W2”：要求“W1”在“W2”的前面
   */
  public static final String BF="bf";
  /**
   * AF 表示之前（after）关系，，例如“W1 af W2”：要求“W1”在“W2”的后面
   */
  public static final String AF="af";
  /**
   * NE 表示附近（near）关系，例如“W1 ne W2”：要求“W1”在“W2”的附近，近似于“(W1 bf W2) or (W2 after W1)”
   */
  public static final String NE="ne";
  /**
   * EX 表示除外（except）关系，例如“W1 ex W1W2”：要求出现“W1”但“W1W2”的词组合除外
   */
  public static final String EX="ex";
  /**
   * AND 表示并且关系，例如“W1 and W2”：要求“W1”、“W2”都要满足
   */
  public static final String AND="and";
  /**
   * OR 表示或者关系，例如“W1 or W2”：要求“W1”、“W2”满足一个即可
   */
  public static final String OR="or";
  /**
   *  contain表示包含关系，例如“W1”或“{w1,,w2,w3}”或“{w1/w2/w3}”
   */
  public static final String CONTAIN="contain";

  /**
   *  A-w1 or B-w2 意思是坐席话术中存在关键词w1 或者 客户话术中存在关键词w2 时表达式为真，结果命中
   */
  public static final String A="A";
  public static final String B="B";
  public static final String ROLE_SYMBOL="-";
  public static final String A_=A+ROLE_SYMBOL;
  public static final String B_=B+ROLE_SYMBOL;
  public static final String AB=A+B;

  /**
   * BF AF NE 连接的两个关键词距离小于配置的距离
   */
  public static final int BF_RANGE=10;
  public static final int AF_RANGE=10;
  public static final int NE_RANGE=10;
  public static final int RANGE=10;


}
