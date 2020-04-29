package com.cj.qc.fastmybatismapper;

import com.cj.qc.model.Model;
import com.cj.qc.model.dto.RuleCombDTO;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenzhaowen
 * @description 模型类mapper
 * @date 2019/4/3 14:45
 */
public interface ModelMapper extends CrudMapper<Model, Long> {
  /**
   * 获取规则与模型中规则设置参数组合
   * @param modelId
   * @return
   */
  List<RuleCombDTO> getRuleCombByModelId(@Param("modelId") Long modelId);
}
