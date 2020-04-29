package com.cj.qc.ruleengine.config;

/**
 * @author chenzhaowen
 * @description 规则运算符枚举类
 * @date 2019/3/20 20:08
 */
public enum RuleEnum {

  /**
   * 特别说明：同级最近出现的逻辑运算符需优先级不同，举反例：w3 bf w4 ne w5 此为错误
   */

  /**
   * {w1/w2/w3}数据类型
   */
  DATA_SLASH(RuleType.VIRGULE,600),
  /**
   * {w1,w2,w3}数据类型
   */
  DATA_COMMA(RuleType.COMMA,500),
  /**
   * 计算后布尔结果数据
   */
  BOOL_RESULT(null,400),
  /**
   * 数据
   */
  DATA(null,300),
  /**
   * 左圆括号
   */
  OPEN_PAREN(RuleType.OPEN_PAREN,200),
  /**
   * 右圆括号
   */
  CLOSE_PAREN(RuleType.CLOSE_PAREN,200),
  /**
   * 非
   */
  NO(RuleType.NO,190),
  /**
   * BF 表示之前（before）关系，，例如“W1 bf W2”：要求“W1”在“W2”的前面
   */
  BF(RuleType.BF,150),
  /**
   * ne 表示附近（near）关系，例如“W1 ne W2”：要求“W1”在“W2”的附近，近似于“(W1 bf W2) or (W2 after W1)”
   */
  NE(RuleType.NE,150),
  /**
   * ex 表示除外（except）关系，例如“W1 ex W1W2”：要求出现“W1”但“W1W2”的词组合除外
   */
  EX(RuleType.EX,150),

  /**
   * 且
   */
  AND(RuleType.AND,100),
  /**
   * 或
   */
  OR(RuleType.OR,0),
  /**
   * 坐席A
   */
  A(RuleType.A_,-10),
  /**
   * 坐席B
   */
  B(RuleType.B_,-10),
  /**
   * 全部角色
   */
  AB(RuleType.AB,-10),

  /**
   * 包含
   */
  CONTAIN(RuleType.CONTAIN,-20);



  private String operator;
  /**
   * 优先级
   */
  private Integer degree;

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public Integer getDegree() {
    return degree;
  }

  public void setDegree(Integer degree) {
    this.degree = degree;
  }

  RuleEnum(String operator, Integer degree) {
    this.operator = operator;
    this.degree = degree;
  }}
