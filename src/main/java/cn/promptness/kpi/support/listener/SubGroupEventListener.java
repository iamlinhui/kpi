package cn.promptness.kpi.support.listener;

import cn.promptness.kpi.dao.*;
import cn.promptness.kpi.support.listener.event.SubGroupEvent;
import cn.promptness.kpi.domain.pojo.KpiConsole;
import cn.promptness.kpi.domain.pojo.KpiGroup;
import cn.promptness.kpi.domain.pojo.KpiGroupFlow;
import cn.promptness.kpi.domain.pojo.KpiProjectTime;
import cn.promptness.kpi.domain.to.ProcessTO;
import cn.promptness.kpi.service.inner.GroupInnerService;
import cn.promptness.kpi.support.common.Constants;
import cn.promptness.kpi.support.common.enums.ConsoleTypeEnum;
import cn.promptness.kpi.support.common.enums.ProcessJobEnum;
import cn.promptness.kpi.support.common.enums.ProjectStatusEnum;
import cn.promptness.kpi.support.common.enums.ProjectTimeTypeEnum;
import cn.promptness.kpi.support.common.utils.AssertUtil;
import cn.promptness.kpi.support.exception.BizExceptionEnum;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 系统自动分配小组人员
 *
 * @author linhuid
 * @date 2019/7/2 21:00
 * @since v1.0.0
 */
@Component
public class SubGroupEventListener {

    @Resource
    private KpiGroupMapper kpiGroupMapper;
    @Resource
    private KpiGroupFlowMapper kpiGroupFlowMapper;
    @Resource
    private KpiGroupMemberMapper kpiGroupMemberMapper;
    @Resource
    private KpiProjectTimeMapper kpiProjectTimeMapper;
    @Resource
    private KpiProjectMapper kpiProjectMapper;
    @Resource
    private TaskService taskService;
    @Resource
    private GroupInnerService groupInnerService;

    /**
     * 系统自动分配小组人员
     *
     * @param subGroupEvent 分配事件
     * @author linhuid
     * @date 2019/7/2 21:31
     * @since v1.0.0
     */
    @EventListener
    public void autoDistribute(SubGroupEvent subGroupEvent) {
        Integer projectId = (Integer) subGroupEvent.getSource();

        List<KpiGroup> subKpiGroupList = kpiGroupMapper.listSubGroupByProjectId(projectId);
        List<String> processInstanceIdList = kpiGroupMapper.listProcessInstanceIdList(projectId);

        for (KpiGroup kpiGroup : subKpiGroupList) {

            Task task = taskService.createTaskQuery().processInstanceIdIn(processInstanceIdList).taskDefinitionKey(Constants.AUTO_DISTRIBUTE_SUBGROUP).taskCategory(kpiGroup.getId().toString()).singleResult();

            if (Objects.isNull(task)) {
                continue;
            }

            KpiGroupFlow kpiGroupFlow = kpiGroupFlowMapper.getFirstFlowerByGroupId(kpiGroup.getId());
            List<String> userIdList = kpiGroupMemberMapper.listUserIdByGroupId(kpiGroup.getId());
            KpiProjectTime kpiProjectTime = kpiProjectTimeMapper.getTime(kpiGroup.getProjectId(), ProjectTimeTypeEnum.PRE, ProcessJobEnum.getInstance(kpiGroupFlow.getJob()));

            Map<String, Object> variables = this.buildVariables(kpiGroup, kpiGroupFlow, userIdList, kpiProjectTime);
            KpiConsole kpiConsole = this.buildKpiConsole(projectId, kpiGroup, kpiGroupFlow, userIdList, kpiProjectTime);

            groupInnerService.saveDistributionProcess(task, variables, kpiConsole);
        }

        int success = kpiProjectMapper.updateState(projectId, ProjectStatusEnum.AUTO_DISTRIBUTED.getValue(), ProjectStatusEnum.EXAMINING.getValue());
        AssertUtil.isTrue(success == 1, BizExceptionEnum.SERVER_ERROR);
    }

    private Map<String, Object> buildVariables(KpiGroup kpiGroup, KpiGroupFlow kpiGroupFlow, List<String> userIdList, KpiProjectTime kpiProjectTime) {
        ProcessTO processTo = new ProcessTO();
        processTo.setAssignee(kpiGroupFlow.getId().toString());
        processTo.setDeadLine(kpiProjectTime.getSettingTime());
        processTo.setSubGroup(kpiGroup.getId().toString());
        processTo.setSubGroupUserList(userIdList);

        return processTo.getVariables();
    }

    private KpiConsole buildKpiConsole(Integer projectId, KpiGroup kpiGroup, KpiGroupFlow kpiGroupFlow, List<String> userIdList, KpiProjectTime kpiProjectTime) {
        KpiConsole kpiConsole = new KpiConsole();
        kpiConsole.setProjectId(projectId);
        kpiConsole.setFlowId(kpiGroupFlow.getId());
        kpiConsole.setGroupId(kpiGroup.getId());
        kpiConsole.setNode(ConsoleTypeEnum.SUB_GROUP_SCORE.getValue());
        kpiConsole.setUserId(kpiGroupFlow.getUserId());
        kpiConsole.setTotalCount(userIdList.size());
        kpiConsole.setCreateTime(new Date());
        kpiConsole.setDeadLine(kpiProjectTime.getSettingTime());
        kpiConsole.setIsFinished(Boolean.FALSE);
        kpiConsole.setIsHide(Boolean.FALSE);
        return kpiConsole;
    }

}
