package cn.promptness.kpi.dao;

import cn.promptness.kpi.domain.pojo.KpiProjectTime;
import cn.promptness.kpi.support.common.enums.ProcessJobEnum;
import cn.promptness.kpi.support.common.enums.ProjectTimeTypeEnum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KpiProjectTimeMapper {

    KpiProjectTime getTime(@Param("projectId") Integer projectId, @Param("projectTimeTypeEnum") ProjectTimeTypeEnum projectTimeTypeEnum, @Param("processJobEnum") ProcessJobEnum processJobEnum);

    int insertBatch(@Param("kpiProjectTimeList") List<KpiProjectTime> kpiProjectTimeList);

    List<KpiProjectTime> listTimeByProjectIdAndType(@Param("projectId") Integer projectId, @Param("projectTimeType") Integer projectTimeType);
}