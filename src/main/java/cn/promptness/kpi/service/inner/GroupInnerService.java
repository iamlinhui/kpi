package cn.promptness.kpi.service.inner;

import cn.promptness.kpi.dao.KpiConsoleMapper;
import cn.promptness.kpi.dao.KpiGroupMapper;
import cn.promptness.kpi.service.inner.group.AbstractGroupExecutor;
import cn.promptness.kpi.support.common.utils.AssertUtil;
import cn.promptness.kpi.domain.pojo.KpiConsole;
import cn.promptness.kpi.domain.pojo.KpiGroup;
import cn.promptness.kpi.support.exception.BizExceptionEnum;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author linhuid
 * @date 2019/7/2 20:30
 * @since v1.0.0
 */
@Service
public class GroupInnerService {

    @Resource
    private KpiGroupMapper kpiGroupMapper;
    @Resource
    private KpiConsoleMapper kpiConsoleMapper;
    @Resource
    private TaskService taskService;

    @Transactional(rollbackFor = Exception.class)
    public void saveProcess(AbstractGroupExecutor groupExecutor, Map<String, Object> variables, KpiGroup kpiGroup) {
        // 流程实例id
        String processInstanceId = groupExecutor.createProcessInstance(variables);
        int success = kpiGroupMapper.saveProcessInstanceId(kpiGroup.getId(), processInstanceId);
        AssertUtil.isTrue(success == 1, BizExceptionEnum.SERVER_ERROR);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveProcess(AbstractGroupExecutor groupExecutor, Map<String, Object> variables, KpiGroup kpiGroup, KpiConsole kpiConsole) {
        this.saveProcess(groupExecutor, variables, kpiGroup);
        int success = kpiConsoleMapper.insertSelective(kpiConsole);
        AssertUtil.isTrue(success == 1, BizExceptionEnum.SERVER_ERROR);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveDistributionProcess(Task task, Map<String, Object> variables, KpiConsole kpiConsole) {
        taskService.complete(task.getId(), variables);
        int success = kpiConsoleMapper.insertSelective(kpiConsole);
        AssertUtil.isTrue(success == 1, BizExceptionEnum.SERVER_ERROR);
    }
}
