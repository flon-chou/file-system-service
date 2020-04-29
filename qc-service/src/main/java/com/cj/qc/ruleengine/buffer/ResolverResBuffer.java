package com.cj.qc.ruleengine.buffer;

import com.cj.qc.model.MachineCheckDetailInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author chenzhaowen
 * @description 解析结果缓存类
 * @date 2019/3/26 19:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "解析结果缓存类")
public class ResolverResBuffer {
  @ApiModelProperty("规则解析结果详情信息类集合")
  List<MachineCheckDetailInfo> machineCheckDetailInfos;
  @ApiModelProperty("每个匹配内容的boolean结果存储列表")
  Boolean finalBoolean;
}
