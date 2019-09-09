package cn.promptness.kpi.dao;

import cn.promptness.kpi.domain.pojo.KpiProfileScore;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KpiProfileScoreMapper {

    List<KpiProfileScore> listByFlowLevelAndProfileId(@Param("groupId") Integer groupId, @Param("groupFlowLevel") Integer groupFlowLevel, @Param("profileId") Integer profileId, @Param("category") Integer category);

    List<KpiProfileScore> listByProfileId(@Param("groupId") Integer groupId, @Param("profileId") Integer profileId, @Param("category") Integer category);

    List<Double> getCurrentProfileScore(@Param("groupFlowId") Integer groupFlowId, @Param("profileScoreType") Integer profileScoreType);

    List<KpiProfileScore> listByFlowIdAndScoreType(@Param("flowId") Integer flowId, @Param("category") Integer category);
}