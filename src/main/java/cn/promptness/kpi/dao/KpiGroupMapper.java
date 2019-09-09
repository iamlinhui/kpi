package cn.promptness.kpi.dao;

import cn.promptness.kpi.domain.pojo.KpiGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KpiGroupMapper {

    List<Integer> listProjectIdByUserId(String userId);

    List<KpiGroup> listGroupById(@Param("groupIdList") List<Integer> groupIdList);

    int saveProcessInstanceId(@Param("groupId") Integer groupId, @Param("processInstanceId") String processInstanceId);

    List<Integer> listSubGroupIdByParentId(Integer parentId);

    List<KpiGroup> listTopGroupByProjectId(Integer projectId);

    List<KpiGroup> listSubGroupByProjectId(Integer projectId);

    List<String> listProcessInstanceIdList(Integer projectId);

    KpiGroup getGroupById(Integer groupId);
}