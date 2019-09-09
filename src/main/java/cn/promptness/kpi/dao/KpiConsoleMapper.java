package cn.promptness.kpi.dao;

import cn.promptness.kpi.domain.pojo.KpiConsole;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface KpiConsoleMapper {

    int insertSelective(KpiConsole record);

    List<KpiConsole> listConsole(@Param("userId") String userId, @Param("projectId") Integer projectId);

    KpiConsole getConsoleByIdAndUserId(@Param("consoleId") Integer consoleId, @Param("userId") String userId);

    int updateFinishTime(@Param("flowId") Integer flowId, @Param("isFinished") Boolean isFinished, @Param("finishTime") Date finishTime);

    Integer updateHideByProjectId(@Param("projectId") Integer projectId, @Param("isHide") Boolean isHide);
}