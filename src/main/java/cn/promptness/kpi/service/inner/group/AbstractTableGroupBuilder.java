package cn.promptness.kpi.service.inner.group;

import cn.promptness.kpi.dao.KpiProfileScoreMapper;
import cn.promptness.kpi.domain.pojo.*;
import cn.promptness.kpi.support.common.enums.GroupCategoryEnum;
import cn.promptness.kpi.support.common.enums.ProfileScoreTypeEnum;
import cn.promptness.kpi.support.common.utils.KpiUtils;
import cn.promptness.kpi.domain.vo.table.TableGroupRsp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author linhuid
 * @date 2019/7/5 9:11
 * @since v1.0.0
 */
public abstract class AbstractTableGroupBuilder {

    @Resource
    private KpiProfileScoreMapper kpiProfileScoreMapper;

    TableGroupRsp.GroupScore buildTopGroupScore(KpiConsole kpiConsole, KpiProfileScore kpiProfileScore) {
        TableGroupRsp.GroupScore groupScore = new TableGroupRsp.GroupScore();

        groupScore.setFlowId(kpiProfileScore.getFlowId());
        groupScore.setUserId(kpiProfileScore.getUserId());
        groupScore.setAssess(kpiProfileScore.getComment());
        groupScore.setScore(kpiProfileScore.getScore());
        groupScore.setName(kpiProfileScore.getUserName());
        groupScore.setCurrent(Objects.equals(kpiConsole.getFlowId(), kpiProfileScore.getFlowId()));
        groupScore.setCategory(ProfileScoreTypeEnum.getInstance(kpiProfileScore.getCategory()).getValue());
        groupScore.setGroupId(kpiConsole.getGroupId());
        groupScore.setFinishTime(kpiProfileScore.getModifyTime());
        groupScore.setIsSubGroup(Boolean.FALSE);
        groupScore.setIsSubFlowLast(Boolean.FALSE);
        return groupScore;
    }

    TableGroupRsp.GroupScore buildSubGroupScore(KpiConsole kpiConsole, Integer subKpiGroupId, List<KpiProfileScore> kpiProfileScoreList, int index) {
        TableGroupRsp.GroupScore groupScore = new TableGroupRsp.GroupScore();

        KpiProfileScore kpiProfileScore = kpiProfileScoreList.get(index);

        groupScore.setFlowId(kpiProfileScore.getFlowId());
        groupScore.setUserId(kpiProfileScore.getUserId());
        groupScore.setName(kpiProfileScore.getUserName());
        groupScore.setAssess(kpiProfileScore.getComment());
        groupScore.setScore(kpiProfileScore.getScore());
        groupScore.setCurrent(Objects.equals(kpiConsole.getFlowId(), kpiProfileScore.getFlowId()));
        groupScore.setCategory(ProfileScoreTypeEnum.getInstance(kpiProfileScore.getCategory()).getValue());
        groupScore.setGroupId(subKpiGroupId);
        groupScore.setFinishTime(kpiProfileScore.getModifyTime());
        groupScore.setIsSubGroup(Boolean.TRUE);
        groupScore.setIsSubFlowLast(kpiProfileScoreList.size() - index == 1);
        return groupScore;
    }

    TableGroupRsp buildTableSubGroupRsp(KpiConsole kpiConsole, KpiProject kpiProject, KpiGroupFlow kpiGroupFlow, List<KpiGroupMember> kpiGroupMemberList, KpiGroup subKpiGroup, KpiGroup topKpiGroup, List<TableGroupRsp.GroupUser> groupUserList) {
        // 当前的分布数据
        List<Double> scoreList = kpiProfileScoreMapper.getCurrentProfileScore(kpiGroupFlow.getId(),ProfileScoreTypeEnum.GENERAL_COMMENT.getValue());

        TableGroupRsp tableGroupRsp = new TableGroupRsp();
        tableGroupRsp.setFlowId(kpiConsole.getFlowId());
        tableGroupRsp.setProjectName(kpiProject.getName());
        tableGroupRsp.setProjectId(kpiProject.getId());
        tableGroupRsp.setGroupName(topKpiGroup.getName());
        tableGroupRsp.setSubGroupName(subKpiGroup.getName());
        tableGroupRsp.setGroupType(GroupCategoryEnum.getInstance(subKpiGroup.getCategory()).getValue());
        tableGroupRsp.setFinishNum(kpiConsole.getFinishCount());
        tableGroupRsp.setDistributed(KpiUtils.getDistributionByTotal(kpiGroupMemberList.size()));
        tableGroupRsp.setCurrentDistributed(KpiUtils.getDistributionByScoreList(scoreList));
        tableGroupRsp.setGroupId(subKpiGroup.getId());
        tableGroupRsp.setScoreType(kpiGroupFlow.getType());
        tableGroupRsp.setLimitType(kpiGroupFlow.getIsLimit());

        tableGroupRsp.setDataList(groupUserList.stream().sorted(Comparator.comparing(TableGroupRsp.GroupUser::getRankingScore)).collect(Collectors.toList()));
        return tableGroupRsp;
    }

    TableGroupRsp.GroupUser baseBuildGroupUser(boolean approvalRole, KpiGroupMember kpiGroupMember, KpiProfile kpiProfile, List<TableGroupRsp.GroupScore> groupScoreList) {
        TableGroupRsp.GroupUser groupUser = new TableGroupRsp.GroupUser();
        groupUser.setUserId(kpiGroupMember.getUserId());
        groupUser.setName(kpiGroupMember.getUserName());
        groupUser.setDepartment(kpiGroupMember.getOfficeName());
        groupUser.setPost(kpiGroupMember.getJobName());
        groupUser.setScore(kpiProfile.getSelfScore());

        if (CollectionUtils.isEmpty(groupScoreList)) {
            groupUser.setRankingScore(Optional.ofNullable(kpiProfile.getSelfScore()).orElse(0.0));
        } else {
            groupUser.setRankingScore(Optional.ofNullable(groupScoreList.get(groupScoreList.size() - 1).getScore()).orElse(0.0));
        }
        //TODO  绩效审批人可查看员工前一季度考评结果
        groupUser.setPreviousQuarterResult(StringUtils.EMPTY);
        groupUser.setDataList(groupScoreList);
        return groupUser;
    }

    TableGroupRsp buildTableTopGroup(KpiConsole kpiConsole, KpiProject kpiProject, KpiGroupFlow kpiGroupFlow, KpiGroup topKpiGroup, List<TableGroupRsp.GroupUser> groupUserList) {
        // 当前的分布数据
        List<Double> scoreList = kpiProfileScoreMapper.getCurrentProfileScore(kpiGroupFlow.getId(), ProfileScoreTypeEnum.GENERAL_COMMENT.getValue());
        TableGroupRsp tableGroupRsp = new TableGroupRsp();
        tableGroupRsp.setFlowId(kpiConsole.getFlowId());
        tableGroupRsp.setProjectName(kpiProject.getName());
        tableGroupRsp.setGroupName(topKpiGroup.getName());
        tableGroupRsp.setProjectId(kpiProject.getId());
        tableGroupRsp.setGroupType(GroupCategoryEnum.getInstance(topKpiGroup.getCategory()).getValue());
        tableGroupRsp.setFinishNum(kpiConsole.getFinishCount());
        tableGroupRsp.setDistributed(KpiUtils.getDistributionByTotal(groupUserList.size()));
        tableGroupRsp.setCurrentDistributed(KpiUtils.getDistributionByScoreList(scoreList));
        tableGroupRsp.setScoreType(kpiGroupFlow.getType());
        tableGroupRsp.setGroupId(kpiConsole.getGroupId());
        tableGroupRsp.setLimitType(kpiGroupFlow.getIsLimit());
        tableGroupRsp.setDataList(groupUserList.stream().sorted(Comparator.comparing(TableGroupRsp.GroupUser::getRankingScore)).collect(Collectors.toList()));
        return tableGroupRsp;
    }

}
