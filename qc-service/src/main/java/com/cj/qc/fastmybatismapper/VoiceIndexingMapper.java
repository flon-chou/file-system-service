package com.cj.qc.fastmybatismapper;

import com.cj.qc.model.VoiceIndexing;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author chenzhaowen
 * @description 业务索引类mapper
 * @date 2019/4/3 14:45
 */
public interface VoiceIndexingMapper extends CrudMapper<VoiceIndexing, Long> {

  /**
   * 根据条件获取音频文件名称列表
   * @param recordStartTime
   * @param recordEndTime
   * @param typeOfServiceName
   * @param policyNos
   * @param callTypeName
   * @param callDurationMin
   * @param callDurationMax
   * @param callNumbers
   * @param tsrGroups
   * @param tsrNos
   * @param audioNames
   * @param longSilent
   * @param abnormalSpeed
   * @param seatEmtion
   * @return
   */
  List<String> queryByCondition(@Param("recordStartTime") Timestamp recordStartTime,
                                @Param("recordEndTime") Timestamp recordEndTime,
                                @Param("typeOfServiceName") String typeOfServiceName,
                                @Param("policyNos") List<String> policyNos,
                                @Param("callTypeName") String callTypeName,
                                @Param("callDurationMin") Integer callDurationMin,
                                @Param("callDurationMax") Integer callDurationMax,
                                @Param("callNumbers") List<String> callNumbers,
                                @Param("tsrGroups") List<String> tsrGroups,
                                @Param("tsrNos") List<String> tsrNos,
                                @Param("audioNames") List<String> audioNames,
                                @Param("longSilent") Integer longSilent,
                                @Param("abnormalSpeed") Integer abnormalSpeed,
                                @Param("seatEmtion") String seatEmtion);
}
