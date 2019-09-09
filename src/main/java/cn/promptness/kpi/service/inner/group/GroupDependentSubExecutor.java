package cn.promptness.kpi.service.inner.group;

import cn.promptness.kpi.dao.*;
import cn.promptness.kpi.domain.pojo.*;
import cn.promptness.kpi.support.common.Constants;
import cn.promptness.kpi.support.common.enums.*;
import com.google.common.collect.Lists;
import cn.promptness.kpi.domain.to.ProcessTO;
import cn.promptness.kpi.domain.vo.table.TableGroupRsp;
import cn.promptness.kpi.service.rpc.UserService;
import cn.promptness.kpi.service.workflow.ActivityService;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 非独立考核组分组
 *
 * @author linhuid
 * @date 2019/7/2 18:52
 * @since v1.0.0
 */
@Service
public class GroupDependentSubExecutor extends AbstractGroupExecutor {


    @Resource
    private KpiGroupMapper kpiGroupMapper;
    @Resource
    private KpiGroupFlowMapper kpiGroupFlowMapper;
    @Resource
    private KpiProjectMapper kpiProjectMapper;
    @Resource
    private KpiProfileMapper kpiProfileMapper;
    @Resource
    private KpiGroupMemberMapper kpiGroupMemberMapper;
    @Resource
    private KpiProfileScoreMapper kpiProfileScoreMapper;
    @Resource
    private KpiProjectTimeMapper kpiProjectTimeMapper;
    @Resource
    private UserService userService;
    @Resource
    private ActivityService activityService;


    @Override
    boolean startProcess(List<KpiGroup> kpiGroupList) {
        return true;
    }

    @Override
    void commitTask(KpiConsole kpiConsole, KpiGroup subKpiGroup) {

        KpiGroup topKpiGroup = kpiGroupMapper.getGroupById(subKpiGroup.getParentId());
        KpiGroupFlow subKpiGroupFlow = kpiGroupFlowMapper.getGroupFlowById(kpiConsole.getFlowId());

        List<Task> taskList = activityService.listTask(topKpiGroup.getProcessInstanceId(), Constants.SUB_TASK_DEFINITION_KEY, kpiConsole.getFlowId(), subKpiGroup.getId());

        for (Task task : taskList) {

            List<KpiConsole> kpiConsoleList = Lists.newArrayList();
            ProcessTO process = buildProcess(kpiConsole, subKpiGroup, topKpiGroup, subKpiGroupFlow, kpiConsoleList);

            Map<String, Object> variables = process.getVariables();
            activityService.commitTaskAndSaveConsole(task, variables, kpiConsoleList);
        }

    }

    private ProcessTO buildProcess(KpiConsole kpiConsole, KpiGroup subKpiGroup, KpiGroup topKpiGroup, KpiGroupFlow subKpiGroupFlow, List<KpiConsole> kpiConsoleList) {
        ProcessTO process = new ProcessTO();

        boolean subProcessIsLast = activityService.checkSubProcessIsLast(topKpiGroup.getProcessInstanceId(), Constants.SUB_PROCESS_ACTIVITY_ID);
        boolean subTaskIsLast = activityService.checkTaskIsLast(topKpiGroup.getProcessInstanceId(), Constants.SUB_TASK_DEFINITION_KEY, subKpiGroup.getId());
        boolean subFlowIsLast = kpiGroupFlowMapper.checkFlowIsLast(subKpiGroup.getId(), subKpiGroupFlow.getLevel()) == 0;


        // 准备合并后需要的数据
        if (subProcessIsLast && subTaskIsLast && subFlowIsLast) {
            nextTopTask(kpiConsole, topKpiGroup, kpiConsoleList, process);
        }
        // 准备下个子任务
        else if (subTaskIsLast && !subFlowIsLast) {
            nextSubTask(kpiConsole, subKpiGroup, subKpiGroupFlow, kpiConsoleList, process);
        }
        // 常规提交
        else {
            process.setFinish(Boolean.FALSE);
        }
        return process;
    }

    private void nextSubTask(KpiConsole kpiConsole, KpiGroup subKpiGroup, KpiGroupFlow subKpiGroupFlow, List<KpiConsole> kpiConsoleList, ProcessTO process) {
        KpiGroupFlow nextKpiGroupFlow = kpiGroupFlowMapper.getNextFlowerByGroupId(subKpiGroup.getId(), subKpiGroupFlow.getLevel());
        KpiProjectTime kpiProjectTime = kpiProjectTimeMapper.getTime(kpiConsole.getProjectId(), ProjectTimeTypeEnum.PRE, ProcessJobEnum.getInstance(nextKpiGroupFlow.getJob()));

        List<String> subGroupUserList = kpiGroupMemberMapper.listUserIdByGroupId(subKpiGroup.getId());


        process.setAssignee(nextKpiGroupFlow.getId().toString());
        process.setDeadLine(kpiProjectTime.getSettingTime());
        process.setSubGroupUserList(subGroupUserList);
        process.setFinish(false);


        KpiConsole nextKpiConsole = new KpiConsole();
        nextKpiConsole.setProjectId(kpiConsole.getProjectId());
        nextKpiConsole.setFlowId(nextKpiGroupFlow.getId());
        nextKpiConsole.setGroupId(subKpiGroup.getId());
        nextKpiConsole.setNode(ConsoleTypeEnum.SUB_GROUP_SCORE.getValue());
        nextKpiConsole.setUserId(nextKpiGroupFlow.getUserId());
        nextKpiConsole.setTotalCount(subGroupUserList.size());
        nextKpiConsole.setCreateTime(new Date());
        nextKpiConsole.setDeadLine(kpiProjectTime.getSettingTime());
        nextKpiConsole.setIsHide(Boolean.FALSE);
        nextKpiConsole.setIsFinished(Boolean.FALSE);

        // 下一个卡片数据
        kpiConsoleList.add(nextKpiConsole);


        // TODO 通知下一个审批人
    }

    private void nextTopTask(KpiConsole kpiConsole, KpiGroup topKpiGroup, List<KpiConsole> kpiConsoleList, ProcessTO process) {
        KpiGroupFlow topKpiGroupFlow = kpiGroupFlowMapper.getFirstFlowerByGroupId(topKpiGroup.getId());
        KpiProjectTime kpiProjectTime = kpiProjectTimeMapper.getTime(kpiConsole.getProjectId(), ProjectTimeTypeEnum.PRE, ProcessJobEnum.getInstance(topKpiGroupFlow.getJob()));


        List<String> userIdList = Lists.newArrayList();
        List<Integer> subGroupIdList = kpiGroupMapper.listSubGroupIdByParentId(topKpiGroup.getId());
        for (Integer subGroupId : subGroupIdList) {
            userIdList.addAll(kpiGroupMemberMapper.listUserIdByGroupId(subGroupId));
        }

        process.setAssignee(topKpiGroupFlow.getId().toString());
        process.setDeadLine(kpiProjectTime.getSettingTime());
        process.setGroup(topKpiGroup.getId().toString());
        process.setUserList(userIdList);
        process.setFinish(Boolean.TRUE);


        KpiConsole nextKpiConsole = new KpiConsole();
        nextKpiConsole.setProjectId(kpiConsole.getProjectId());
        nextKpiConsole.setFlowId(topKpiGroupFlow.getId());
        nextKpiConsole.setGroupId(topKpiGroup.getId());
        nextKpiConsole.setNode(ConsoleTypeEnum.GROUP_SCORE.getValue());
        nextKpiConsole.setUserId(topKpiGroupFlow.getUserId());
        nextKpiConsole.setTotalCount(subGroupIdList.size());
        nextKpiConsole.setCreateTime(new Date());
        nextKpiConsole.setDeadLine(kpiProjectTime.getSettingTime());
        nextKpiConsole.setIsHide(Boolean.FALSE);
        nextKpiConsole.setIsFinished(Boolean.FALSE);


        // 下一个卡片数据
        kpiConsoleList.add(nextKpiConsole);


        // TODO 通知下一个审批人
    }


    @Override
    TableGroupRsp buildTableGroupRsp(KpiConsole kpiConsole, KpiGroup subKpiGroup) {

        // 2019-02-18 绩效评分时，绩效审批人可查看员工前一季度考评结果
        boolean approvalRole = userService.checkApprovalRole(kpiConsole.getUserId(), RoleTypeEnum.PERFORMANCE_APPROVER);

        KpiProject kpiProject = kpiProjectMapper.getProjectById(kpiConsole.getProjectId());
        KpiGroupFlow kpiGroupFlow = kpiGroupFlowMapper.getGroupFlowById(kpiConsole.getFlowId());

        // 分组所对应的所有考核人
        List<KpiGroupMember> kpiGroupMemberList = kpiGroupMemberMapper.listByGroupId(kpiConsole.getGroupId());

        // 大组
        KpiGroup topKpiGroup = kpiGroupMapper.getGroupById(subKpiGroup.getParentId());

        List<TableGroupRsp.GroupUser> groupUserList = buildGroupUserList(kpiConsole, subKpiGroup, approvalRole, kpiGroupFlow, kpiGroupMemberList);

        // 构建分组列表的数据集合
        return super.buildTableSubGroupRsp(kpiConsole, kpiProject, kpiGroupFlow, kpiGroupMemberList, subKpiGroup, topKpiGroup, groupUserList);
    }

    private List<TableGroupRsp.GroupUser> buildGroupUserList(KpiConsole kpiConsole, KpiGroup subKpiGroup, boolean approvalRole, KpiGroupFlow kpiGroupFlow, List<KpiGroupMember> kpiGroupMemberList) {
        List<TableGroupRsp.GroupUser> groupUserList = Lists.newArrayList();
        for (KpiGroupMember kpiGroupMember : kpiGroupMemberList) {

            // 当前人的考核成绩单
            KpiProfile kpiProfile = kpiProfileMapper.getProfileById(kpiGroupMember.getProfileId());
            // 当前审批人 和 上游审批人的 总评数据
            List<KpiProfileScore> kpiProfileScoreList = kpiProfileScoreMapper.listByFlowLevelAndProfileId(subKpiGroup.getId(), kpiGroupFlow.getLevel(), kpiGroupMember.getProfileId(), ProfileScoreTypeEnum.GENERAL_COMMENT.getValue());
            List<TableGroupRsp.GroupScore> groupScoreList = buildGroupScoreList(kpiConsole, subKpiGroup, kpiProfileScoreList);

            // 构建考核人的数据
            TableGroupRsp.GroupUser groupUser = super.baseBuildGroupUser(approvalRole, kpiGroupMember, kpiProfile, groupScoreList);
            groupUserList.add(groupUser);
        }
        return groupUserList;
    }

    private List<TableGroupRsp.GroupScore> buildGroupScoreList(KpiConsole kpiConsole, KpiGroup subKpiGroup, List<KpiProfileScore> kpiProfileScoreList) {
        // 构建评分数据集合
        List<TableGroupRsp.GroupScore> groupScoreList = Lists.newArrayList();
        for (int index = 0; index < kpiProfileScoreList.size(); index++) {
            TableGroupRsp.GroupScore groupScore = super.buildSubGroupScore(kpiConsole, subKpiGroup.getId(), kpiProfileScoreList, index);
            groupScoreList.add(groupScore);
        }
        return groupScoreList;
    }


    @Override
    GroupCategoryEnum getGroupCategory() {
        return GroupCategoryEnum.DEPENDENT_SUB;
    }


}
