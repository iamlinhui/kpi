package cn.promptness.kpi.service.workflow;

import cn.promptness.kpi.dao.KpiConsoleMapper;
import cn.promptness.kpi.dao.KpiGroupFlowMapper;
import cn.promptness.kpi.dao.KpiGroupMapper;
import cn.promptness.kpi.dao.KpiProjectMapper;
import cn.promptness.kpi.domain.pojo.KpiConsole;
import cn.promptness.kpi.domain.pojo.KpiGroup;
import cn.promptness.kpi.support.common.enums.GroupCategoryEnum;
import cn.promptness.kpi.support.common.enums.ProjectStatusEnum;
import cn.promptness.kpi.support.common.utils.AssertUtil;
import cn.promptness.kpi.support.exception.BizExceptionEnum;
import cn.promptness.kpi.support.listener.event.SubGroupEvent;
import cn.promptness.kpi.service.inner.group.BaseGroupHandler;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author linhuid
 * @date 2019/7/2 16:13
 * @since v1.0.0
 */
@Service
public class ActivityService {


    @Resource
    private KpiGroupMapper kpiGroupMapper;
    @Resource
    private KpiProjectMapper kpiProjectMapper;
    @Resource
    private KpiConsoleMapper kpiConsoleMapper;
    @Resource
    private KpiGroupFlowMapper kpiGroupFlowMapper;
    @Resource
    private BaseGroupHandler groupHandler;
    @Resource
    private ApplicationEventPublisher publisher;
    @Resource
    private HistoryService historyService;
    @Resource
    private TaskService taskService;

    /**
     * 开启流程中的考核
     *
     * @param projectId 考核方案
     * @author linhuid
     * @date 2019/7/2 17:32
     * @since v1.0.0
     */
    public void startProject(Integer projectId) {

        // -9已删除 0草稿 1未确认 2目标制定中 3目标制定结束 4分配考核组中 5分配人员结束 6系统分配流程中 7系统分配流程完成 8考核中  9已归档

        // 状态修改为 "系统分配流程中"
        int success = kpiProjectMapper.updateState(projectId, ProjectStatusEnum.GROUP_DISTRIBUTED.getValue(), ProjectStatusEnum.AUTO_DISTRIBUTING.getValue());
        AssertUtil.isTrue(success == 1, BizExceptionEnum.SERVER_ERROR);


        // 获取当前的 考核大组
        List<KpiGroup> kpiGroupList = kpiGroupMapper.listTopGroupByProjectId(projectId);
        AssertUtil.isFalse(kpiGroupList.isEmpty(), BizExceptionEnum.SERVER_ERROR);

        // 分成两批  分别开启流程 (独立 | 非独立)
        Map<Integer, List<KpiGroup>> kpiGroupMap = kpiGroupList.stream().filter(this::filter).collect(Collectors.groupingBy(KpiGroup::getCategory));
        for (Map.Entry<Integer, List<KpiGroup>> entry : kpiGroupMap.entrySet()) {
            GroupCategoryEnum groupCategoryEnum = GroupCategoryEnum.getInstance(entry.getKey());
            groupHandler.start(groupCategoryEnum, entry.getValue());
        }

        success = kpiProjectMapper.updateState(projectId, ProjectStatusEnum.AUTO_DISTRIBUTING.getValue(), ProjectStatusEnum.AUTO_DISTRIBUTED.getValue());
        AssertUtil.isTrue(success == 1, BizExceptionEnum.SERVER_ERROR);

        // 系统自动分配小组人员
        SubGroupEvent subGroupEvent = new SubGroupEvent(projectId);
        publisher.publishEvent(subGroupEvent);

    }


    /**
     * 过滤已经处理过的大组
     *
     * @param kpiGroup 大组
     * @return 是否处理过
     * @author linhuid
     * @date 2019/7/2 20:42
     * @since v1.0.0
     */
    private boolean filter(KpiGroup kpiGroup) {
        return !Objects.equals(kpiGroup.getProcessInstanceId(), 0L);
    }


    /**
     * 查看子流程是否最后一个
     */
    public boolean checkSubProcessIsLast(Long processInstanceId, String subProcessActivityId) {
        return historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId.toString()).activityId(subProcessActivityId).unfinished().count() == 1L;
    }

    /**
     * 查看当前并行任务是否最后一个
     *
     * @param category 子流程中的任务分组标识 _|_ 主流程-->多个子流程-->同一任务会有多个子流程去签收--->需要跟踪当前子流程下的当前并形任务
     */
    public boolean checkTaskIsLast(Long processInstanceId, String taskDefinitionKey, Integer category) {
        return historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId.toString()).taskDefinitionKey(taskDefinitionKey).taskCategory(category.toString()).unfinished().count() == 1L;
    }

    public List<Task> listTask(Long processInstanceId, String taskDefinitionKey, Integer flowId, Integer groupId) {
        return taskService.createTaskQuery()
                .processInstanceId(processInstanceId.toString())
                .taskDefinitionKey(taskDefinitionKey)
                .taskAssignee(flowId.toString())
                .taskCategory(groupId.toString())
                .list();
    }

    @Transactional(rollbackFor = Exception.class)
    public void commitTaskAndSaveConsole(Task task, Map<String, Object> variables, List<KpiConsole> kpiConsoleList) {
        taskService.complete(task.getId(), variables);
        for (KpiConsole kpiConsole : kpiConsoleList) {
            kpiConsoleMapper.insertSelective(kpiConsole);
        }

        Integer flowId = Integer.valueOf(task.getAssignee());

        // 说明有流程要结束
        if (!CollectionUtils.isEmpty(kpiConsoleList)) {
            int success = kpiConsoleMapper.updateFinishTime(flowId, Boolean.TRUE, new Date());
            AssertUtil.isTrue(success == 1, BizExceptionEnum.SERVER_ERROR);
            kpiGroupFlowMapper.updateFinish(flowId, Boolean.TRUE);
        }

    }
}
