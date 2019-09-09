package cn.promptness.kpi.dao;

import cn.promptness.kpi.domain.pojo.KpiProfile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KpiProfileMapper {

    List<Integer> listProjectIdByUserId(String userId);

    KpiProfile getProfileById(Integer profileId);

    List<String> listUserIdByProjectId(Integer projectId);

    Integer insertSelective(KpiProfile kpiProfile);

    KpiProfile getProfileByUserIdAndProjectId(@Param("userId") String userId, @Param("projectId") Integer projectId);

    List<KpiProfile> listProfileByUserIdAndProjectId(@Param("userIdList") List<String> userIdList, @Param("projectId") Integer projectId);

}