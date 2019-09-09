package cn.promptness.kpi.dao;

import cn.promptness.kpi.domain.pojo.KpiGroupFlow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KpiGroupFlowMapper {

    List<Integer> listProjectIdByUserId(String userId);

    KpiGroupFlow getGroupFlowById(Integer flowId);

    KpiGroupFlow getFirstFlowerByGroupId(Integer groupId);

    Integer checkFlowIsLast(@Param("groupId") Integer groupId, @Param("groupFlowLevel") Integer groupFlowLevel);

    KpiGroupFlow getNextFlowerByGroupId(@Param("groupId") Integer groupId, @Param("groupFlowLevel") Integer groupFlowLevel);

    Integer updateFinish(@Param("flowId") Integer flowId, @Param("isFinished") Boolean isFinished);
}