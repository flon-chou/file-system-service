package com.cj.qc.ruleengine.buffer;

import com.cj.qc.ruleengine.config.RuleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenzhaowen
 * @description 表达式缓存类
 * @date 2019/3/25 15:33
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "表达式缓存类:用于分离表达式后的数据信息保存")
public class ExpressionBuffer {
  @ApiModelProperty("分离后存储的类型，如BOOL_RESULT、DATA、OPEN_PAREN、CLOSE_PAREN、NO等")
  private RuleEnum ruleEnum;
  @ApiModelProperty("分离得到的值")
  private String value;
  @ApiModelProperty("若ruleEnum为BOOL_RESULT，说明此分离值与某个运算符已经计算，此属性表示计算后的结果")
  private Boolean boolRes;

  @ApiModelProperty("源表达式")
  private String sourceExpression;
  @ApiModelProperty("源后缀表达式")
  private String sourcePostfixExpression;

  @ApiModelProperty("次数")
  private Integer number;

  public ExpressionBuffer(RuleEnum ruleEnum, String value) {
    this.ruleEnum = ruleEnum;
    this.value = value;
  }

  public ExpressionBuffer(RuleEnum ruleEnum, String value, String sourceExpression, String sourcePostfixExpression, Integer number) {
    this.ruleEnum = ruleEnum;
    this.value = value;
    this.sourceExpression = sourceExpression;
    this.sourcePostfixExpression = sourcePostfixExpression;
    this.number = number;
  }
}
