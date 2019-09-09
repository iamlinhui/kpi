package cn.promptness.kpi.dao;

import cn.promptness.kpi.domain.pojo.KpiGroupMember;

import java.util.List;

public interface KpiGroupMemberMapper {

    List<String> listUserIdByGroupId(Integer groupId);

    List<KpiGroupMember> listByGroupId(Integer groupId);

}