package cn.promptness.kpi.service.inner.group;

import cn.promptness.kpi.support.common.Constants;
import cn.promptness.kpi.support.common.enums.GroupCategoryEnum;
import cn.promptness.kpi.domain.pojo.KpiConsole;
import cn.promptness.kpi.domain.pojo.KpiGroup;
import cn.promptness.kpi.domain.vo.table.TableGroupRsp;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author linhuid
 * @date 2019/7/2 18:45
 * @since v1.0.0
 */
public abstract class AbstractGroupExecutor extends AbstractTableGroupBuilder{

    @Autowired
    private BaseGroupHandler groupHandler;
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private RuntimeService runtimeService;

    /**
     * 开启考核流程(只针对于大组)
     *
     * @param kpiGroupList 考核大组
     * @return 处理结果
     * @author linhuid
     * @date 2019/7/2 19:11
     * @since v1.0.0
     */
    abstract boolean startProcess(List<KpiGroup> kpiGroupList);


    /**
     * 审批提交结果
     *
     * @param kpiConsole 审批卡片
     * @param kpiGroup   考核组
     * @author linhuid
     * @date 2019/7/4 16:21
     * @since v1.0.0
     */
    abstract void commitTask(KpiConsole kpiConsole, KpiGroup kpiGroup);


    /**
     * 构建考核列表数据
     *
     * @param kpiConsole 审批卡片
     * @param kpiGroup 卡片对应的考核组
     * @return 考核列表
     * @author linhuid
     * @date   2019/7/4 19:25
     * @since  v1.0.0
     */
    abstract TableGroupRsp buildTableGroupRsp(KpiConsole kpiConsole, KpiGroup kpiGroup);

    /**
     * 考核组类型
     *
     * @return 考核组类型
     * @author linhuid
     * @date 2019/7/2 19:11
     * @since v1.0.0
     */
    abstract GroupCategoryEnum getGroupCategory();


    public String createProcessInstance(Map<String, Object> variables) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(Constants.PROCESS_DEFINITION_KEY).latestVersion().singleResult();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), variables);
        return processInstance.getId();
    }


    @PostConstruct
    public void register() {
        groupHandler.register(getGroupCategory(), this);
    }

}
