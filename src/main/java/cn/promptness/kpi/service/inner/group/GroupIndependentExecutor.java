package cn.promptness.kpi.service.inner.group;

import cn.promptness.kpi.dao.*;
import cn.promptness.kpi.domain.pojo.*;
import cn.promptness.kpi.support.common.Constants;
import cn.promptness.kpi.support.common.enums.*;
import cn.promptness.kpi.support.listener.event.UpdateProfileEvent;
import com.google.common.collect.Lists;
import cn.promptness.kpi.domain.to.ProcessTO;
import cn.promptness.kpi.domain.vo.table.TableGroupRsp;
import cn.promptness.kpi.service.inner.GroupInnerService;
import cn.promptness.kpi.service.rpc.UserService;
import cn.promptness.kpi.service.workflow.ActivityService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 独立考核组大组
 *
 * @author linhuid
 * @date 2019/7/2 18:52
 * @since v1.0.0
 */
@Service
public class GroupIndependentExecutor extends AbstractGroupExecutor {

    @Resource
    private KpiGroupFlowMapper kpiGroupFlowMapper;
    @Resource
    private KpiGroupMemberMapper kpiGroupMemberMapper;
    @Resource
    private KpiProjectTimeMapper kpiProjectTimeMapper;
    @Resource
    private KpiProjectMapper kpiProjectMapper;
    @Resource
    private KpiProfileMapper kpiProfileMapper;
    @Resource
    private KpiProfileScoreMapper kpiProfileScoreMapper;
    @Resource
    private UserService userService;
    @Resource
    private GroupInnerService groupInnerService;
    @Resource
    private ActivityService activityService;
    @Resource
    private ApplicationEventPublisher publisher;

    @Override
    boolean startProcess(List<KpiGroup> kpiGroupList) {

        for (KpiGroup kpiGroup : kpiGroupList) {

            KpiGroupFlow kpiGroupFlow = kpiGroupFlowMapper.getFirstFlowerByGroupId(kpiGroup.getId());
            List<String> userIdList = kpiGroupMemberMapper.listUserIdByGroupId(kpiGroup.getId());
            KpiProjectTime kpiProjectTime = kpiProjectTimeMapper.getTime(kpiGroup.getProjectId(), ProjectTimeTypeEnum.PRE, ProcessJobEnum.getInstance(kpiGroupFlow.getJob()));

            Map<String, Object> variables = buildVariables(kpiGroupFlow, kpiProjectTime, userIdList);

            // 生成卡片数据
            KpiConsole kpiConsole = buildConsole(kpiGroup, kpiGroupFlow, userIdList, kpiProjectTime);

            groupInnerService.saveProcess(this, variables, kpiGroup, kpiConsole);
        }


        return true;
    }

    @Override
    void commitTask(KpiConsole kpiConsole, KpiGroup topKpiGroup) {

        KpiGroupFlow topKpiGroupFlow = kpiGroupFlowMapper.getGroupFlowById(kpiConsole.getFlowId());

        List<Task> taskList = activityService.listTask(topKpiGroup.getProcessInstanceId(), Constants.TOP_TASK_DEFINITION_KEY, kpiConsole.getFlowId(), topKpiGroup.getId());

        for (Task task : taskList) {

            List<KpiConsole> kpiConsoleList = Lists.newArrayList();
            ProcessTO process = buildProcess(kpiConsole, topKpiGroup, topKpiGroupFlow, kpiConsoleList);

            Map<String, Object> variables = process.getVariables();

            activityService.commitTaskAndSaveConsole(task, variables, kpiConsoleList);
        }
    }

    private ProcessTO buildProcess(KpiConsole kpiConsole, KpiGroup topKpiGroup, KpiGroupFlow topKpiGroupFlow, List<KpiConsole> kpiConsoleList) {

        boolean topTaskIsLast = activityService.checkTaskIsLast(topKpiGroup.getProcessInstanceId(), Constants.TOP_TASK_DEFINITION_KEY, topKpiGroup.getId());
        boolean topFlowIsLast = kpiGroupFlowMapper.checkFlowIsLast(topKpiGroup.getId(), topKpiGroupFlow.getLevel()) == 0;


        ProcessTO process = new ProcessTO();

        // 考核流程进入绩效确认
        if (topFlowIsLast && topTaskIsLast) {

            List<KpiGroupMember> kpiGroupMemberList = kpiGroupMemberMapper.listByGroupId(topKpiGroup.getId());

            List<String> userIdList = kpiGroupMemberList.stream().map(KpiGroupMember::getUserId).collect(Collectors.toList());

            process.setGroup(topKpiGroup.getId().toString());
            process.setUserList(userIdList);
            process.setFinish(Boolean.TRUE);


            //  绩效确认卡片
            for (KpiGroupMember kpiGroupMember : kpiGroupMemberList) {
                KpiConsole profileKpiConsole = new KpiConsole();
                profileKpiConsole.setProfileId(kpiGroupMember.getProfileId());
                profileKpiConsole.setProjectId(kpiConsole.getProjectId());
                profileKpiConsole.setGroupId(kpiGroupMember.getGroupId());
                profileKpiConsole.setNode(ConsoleTypeEnum.CONFIRM.getValue());
                profileKpiConsole.setUserId(kpiGroupMember.getUserId());
                profileKpiConsole.setFinishCount(0);
                profileKpiConsole.setTotalCount(1);
                profileKpiConsole.setCreateTime(new Date());
                profileKpiConsole.setIsHide(Boolean.FALSE);
                profileKpiConsole.setIsFinished(Boolean.FALSE);

                kpiConsoleList.add(profileKpiConsole);
            }


            //   构建绩效面谈卡片
            KpiConsole interviewKpiConsole = new KpiConsole();
            interviewKpiConsole.setProjectId(kpiConsole.getProjectId());
            interviewKpiConsole.setGroupId(topKpiGroup.getId());
            interviewKpiConsole.setNode(ConsoleTypeEnum.INTERVIEW.getValue());
            interviewKpiConsole.setUserId(topKpiGroup.getInterviewerId());
            interviewKpiConsole.setFinishCount(0);
            interviewKpiConsole.setTotalCount(kpiGroupMemberList.size());
            interviewKpiConsole.setCreateTime(new Date());
            interviewKpiConsole.setIsHide(Boolean.FALSE);
            interviewKpiConsole.setIsFinished(Boolean.FALSE);

            kpiConsoleList.add(interviewKpiConsole);


            //TODO 通知考核人结果出来了


            //  更新成绩单
            publisher.publishEvent(new UpdateProfileEvent(kpiConsole));


        }
        // 下一个考核组审批
        else if (!topFlowIsLast && topTaskIsLast) {

            KpiGroupFlow nextKpiGroupFlow = kpiGroupFlowMapper.getNextFlowerByGroupId(topKpiGroup.getId(), topKpiGroupFlow.getLevel());
            KpiProjectTime kpiProjectTime = kpiProjectTimeMapper.getTime(kpiConsole.getProjectId(), ProjectTimeTypeEnum.PRE, ProcessJobEnum.getInstance(nextKpiGroupFlow.getJob()));

            List<String> userIdList = kpiGroupMemberMapper.listUserIdByGroupId(topKpiGroup.getId());

            process.setAssignee(nextKpiGroupFlow.getId().toString());
            process.setDeadLine(kpiProjectTime.getSettingTime());
            process.setGroup(topKpiGroup.getId().toString());
            process.setUserList(userIdList);
            process.setFinish(Boolean.FALSE);


            //  构建考核组审批卡片
            KpiConsole flowKpiConsole = new KpiConsole();
            flowKpiConsole.setProjectId(kpiConsole.getProjectId());
            flowKpiConsole.setFlowId(nextKpiGroupFlow.getId());
            flowKpiConsole.setGroupId(nextKpiGroupFlow.getGroupId());
            flowKpiConsole.setNode(ConsoleTypeEnum.GROUP_SCORE.getValue());
            flowKpiConsole.setUserId(nextKpiGroupFlow.getUserId());
            flowKpiConsole.setFinishCount(0);
            flowKpiConsole.setTotalCount(userIdList.size());
            flowKpiConsole.setCreateTime(new Date());
            flowKpiConsole.setIsHide(Boolean.FALSE);
            flowKpiConsole.setIsFinished(Boolean.FALSE);

            kpiConsoleList.add(flowKpiConsole);

            //TODO 通知下一个审批人

        }
        // 常规情况
        else {
            process.setFinish(Boolean.FALSE);
        }
        return process;
    }

    @Override
    TableGroupRsp buildTableGroupRsp(KpiConsole kpiConsole, KpiGroup topKpiGroup) {

        // 2019-02-18 绩效评分时，绩效审批人可查看员工前一季度考评结果
        boolean approvalRole = userService.checkApprovalRole(kpiConsole.getUserId(), RoleTypeEnum.PERFORMANCE_APPROVER);

        KpiProject kpiProject = kpiProjectMapper.getProjectById(kpiConsole.getProjectId());
        KpiGroupFlow kpiGroupFlow = kpiGroupFlowMapper.getGroupFlowById(kpiConsole.getFlowId());

        List<TableGroupRsp.GroupUser> groupUserList = buildGroupUserList(kpiConsole, approvalRole, kpiGroupFlow, topKpiGroup);

        return super.buildTableTopGroup(kpiConsole, kpiProject, kpiGroupFlow, topKpiGroup, groupUserList);

    }


    private List<TableGroupRsp.GroupUser> buildGroupUserList(KpiConsole kpiConsole, boolean approvalRole, KpiGroupFlow kpiGroupFlow, KpiGroup topKpiGroup) {

        List<TableGroupRsp.GroupUser> groupUserList = Lists.newArrayList();

        // 独立考核组大组
        List<KpiGroupMember> kpiGroupMemberList = kpiGroupMemberMapper.listByGroupId(kpiConsole.getGroupId());
        for (KpiGroupMember kpiGroupMember : kpiGroupMemberList) {
            // 当前人的考核成绩单
            KpiProfile kpiProfile = kpiProfileMapper.getProfileById(kpiGroupMember.getProfileId());
            // 当前审批人 所在大组的 包括 上游审批人的 总评数据
            List<KpiProfileScore> kpiProfileScoreList = kpiProfileScoreMapper.listByFlowLevelAndProfileId(topKpiGroup.getId(), kpiGroupFlow.getLevel(), kpiGroupMember.getProfileId(), ProfileScoreTypeEnum.GENERAL_COMMENT.getValue());
            // 构建审批数据
            List<TableGroupRsp.GroupScore> groupScoreList = buildGroupScoreList(kpiConsole, kpiProfileScoreList);
            // 构建考核人数据
            TableGroupRsp.GroupUser groupUser = super.baseBuildGroupUser(approvalRole, kpiGroupMember, kpiProfile, groupScoreList);
            groupUserList.add(groupUser);
        }

        return groupUserList;
    }

    private List<TableGroupRsp.GroupScore> buildGroupScoreList(KpiConsole kpiConsole, List<KpiProfileScore> kpiProfileScoreList) {
        List<TableGroupRsp.GroupScore> groupScoreList = Lists.newArrayList();
        for (KpiProfileScore kpiProfileScore : kpiProfileScoreList) {
            TableGroupRsp.GroupScore groupScore = super.buildTopGroupScore(kpiConsole, kpiProfileScore);
            groupScoreList.add(groupScore);
        }
        return groupScoreList;
    }


    private KpiConsole buildConsole(KpiGroup kpiGroup, KpiGroupFlow kpiGroupFlow, List<String> userIdList, KpiProjectTime kpiProjectTime) {

        KpiConsole kpiConsole = new KpiConsole();
        kpiConsole.setProjectId(kpiGroup.getProjectId());
        kpiConsole.setFlowId(kpiGroupFlow.getId());
        kpiConsole.setGroupId(kpiGroup.getId());
        kpiConsole.setNode(ConsoleTypeEnum.GROUP_SCORE.getValue());
        kpiConsole.setUserId(kpiGroupFlow.getUserId());
        kpiConsole.setTotalCount(userIdList.size());
        kpiConsole.setCreateTime(new Date());
        kpiConsole.setDeadLine(kpiProjectTime.getSettingTime());
        kpiConsole.setRemark(StringUtils.EMPTY);
        kpiConsole.setIsHide(Boolean.FALSE);
        kpiConsole.setIsFinished(Boolean.FALSE);

        return kpiConsole;
    }


    private Map<String, Object> buildVariables(KpiGroupFlow kpiGroupFlow, KpiProjectTime kpiProjectTime, List<String> userIdList) {

        ProcessTO processTo = new ProcessTO();
        processTo.setAssignee(kpiGroupFlow.getId().toString());
        processTo.setGroup(kpiGroupFlow.getGroupId().toString());
        processTo.setDeadLine(kpiProjectTime.getSettingTime());
        processTo.setUserList(userIdList);
        processTo.setIndependent(Boolean.TRUE);

        return processTo.getVariables();

    }

    @Override
    GroupCategoryEnum getGroupCategory() {
        return GroupCategoryEnum.INDEPENDENT;
    }


}
