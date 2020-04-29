package com.cj.qc.fastmybatismapper;

import com.cj.qc.model.TaskQueueAuto;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author chenzhaowen
 * @description 自动质检任务队列类mapper
 * @date 2019/4/3 14:45
 */
public interface TaskQueueAutoMapper extends CrudMapper<TaskQueueAuto, Long> {

  /**
   * 查询指定日期的统计数
   * @param beforeDate
   * @return
   */
  @Select("SELECT COUNT(id) FROM cj_qc_task_queue_auto WHERE DATE_FORMAT(`day`, '%Y-%m-%d') = DATE_FORMAT(#{beforeDate},'%Y-%m-%d')")
  long countByDay(@Param("beforeDate")Timestamp beforeDate);

  /**
   * 查询自定日期下自动质检任务未完成的任务ID数组
   * @param beforeDate
   * @return
   */
  @Select("SELECT task_auto_id FROM cj_qc_task_queue_auto WHERE DATE_FORMAT(`day`, '%Y-%m-%d') = #{beforeDate} AND status=0")
  List<Long> listByDay(@Param("beforeDate")String beforeDate);

}
