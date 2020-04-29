package com.cj.qc.fastmybatismapper;

import com.cj.qc.model.Task;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author chenzhaowen
 * @description 任务类mapper
 * @date 2019/4/3 14:45
 */
public interface TaskMapper extends CrudMapper<Task, Long> {

  /**
   * 查询指定日期下自动质检任务未完成的任务ID数组
   * @param beforeDate
   * @return
   */
  @Select("SELECT " +
          " t.id " +
          "FROM " +
          " `cj_qc_task` t " +
          "WHERE " +
          " t.`enable` = 1 " +
          "AND t.task_type = 1 " +
          "AND #{beforeDate} >= t.start_date " +
          "AND ( " +
          " t.end_date IS NULL " +
          " OR #{beforeDate} <= t.end_date " +
          ")")
  List<Long> getTaskQueueByDate(@Param("beforeDate") Timestamp beforeDate);

}
