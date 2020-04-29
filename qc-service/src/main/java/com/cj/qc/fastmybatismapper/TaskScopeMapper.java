package com.cj.qc.fastmybatismapper;

import com.cj.qc.model.TaskScope;
import com.cj.qc.model.dto.TaskScopeCombDTO;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author chenzhaowen
 * @description 质检任务范围类mapper
 * @date 2019/4/3 14:45
 */
public interface TaskScopeMapper extends CrudMapper<TaskScope, Long> {

  /**
   * 获取质检任务范围组合：与字典表组合获取相应名称
   * @param taskScopeId
   * @return
   */
  TaskScopeCombDTO getTaskScopeCombByTaskScopeId(@Param("taskScopeId") Long taskScopeId);

}
