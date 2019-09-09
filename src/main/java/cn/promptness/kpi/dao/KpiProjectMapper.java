package cn.promptness.kpi.dao;

import cn.promptness.kpi.domain.pojo.KpiProject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KpiProjectMapper {

    List<KpiProject> listProject(@Param("stateList") List<Integer> stateList, @Param("isOpen") Boolean isOpen);

    List<KpiProject> listProjectByIdAndStates(@Param("projectIdList") List<Integer> projectIdList, @Param("stateList") List<Integer> stateList, @Param("isOpen") Boolean isOpen);

    KpiProject getProjectById(@Param("projectId") Integer projectId);

    Integer updateState(@Param("projectId") Integer projectId, @Param("fromState") Integer fromState, @Param("toState") Integer toState);
}