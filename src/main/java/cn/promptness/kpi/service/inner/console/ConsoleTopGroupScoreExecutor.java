package cn.promptness.kpi.service.inner.console;

import cn.promptness.kpi.dao.KpiGroupFlowMapper;
import cn.promptness.kpi.dao.KpiGroupMapper;
import cn.promptness.kpi.domain.pojo.KpiConsole;
import cn.promptness.kpi.domain.pojo.KpiGroup;
import cn.promptness.kpi.domain.pojo.KpiGroupFlow;
import cn.promptness.kpi.domain.vo.console.ConsoleRsp;
import cn.promptness.kpi.service.inner.group.BaseGroupHandler;
import cn.promptness.kpi.support.common.enums.ConsoleTypeEnum;
import cn.promptness.kpi.support.common.enums.GroupCategoryEnum;
import cn.promptness.kpi.support.common.enums.GroupFlowTypeEnum;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author linhuid
 * @date 2019/7/2 8:43
 * @since v1.0.0
 */
@Service
public class ConsoleTopGroupScoreExecutor extends AbstractConsoleExecutor {


    @Resource
    private KpiGroupFlowMapper kpiGroupFlowMapper;
    @Resource
    private KpiGroupMapper kpiGroupMapper;
    @Resource
    private BaseGroupHandler groupHandler;


    @Override
    List<ConsoleRsp.ConsoleVO> doConsole(List<KpiConsole> kpiConsoleList, Map<Integer, KpiGroup> kpiGroupMap) {


        List<ConsoleRsp.ConsoleVO> consoleVoList = Lists.newArrayList();
        for (KpiConsole kpiConsole : kpiConsoleList) {

            // 大组
            KpiGroup topKpiGroup = kpiGroupMap.get(kpiConsole.getGroupId());

            //审批链
            KpiGroupFlow kpiGroupFlow = kpiGroupFlowMapper.getGroupFlowById(kpiConsole.getFlowId());

            ConsoleRsp.ConsoleVO consoleVO = buildConsoleVO(kpiConsole, topKpiGroup, kpiGroupFlow);
            consoleVoList.add(consoleVO);
        }

        return consoleVoList;
    }

    @Override
    Object getTableData(KpiConsole kpiConsole) {

        KpiGroup topKpiGroup = kpiGroupMapper.getGroupById(kpiConsole.getGroupId());
        GroupCategoryEnum groupCategoryEnum = GroupCategoryEnum.getInstance(topKpiGroup.getCategory());

        return groupHandler.buildTableGroupRsp(groupCategoryEnum, kpiConsole, topKpiGroup);

    }

    private ConsoleRsp.ConsoleVO buildConsoleVO(KpiConsole kpiConsole, KpiGroup topKpiGroup, KpiGroupFlow kpiGroupFlow) {
        ConsoleRsp.ConsoleVO consoleVo = super.buildConsole(kpiConsole);
        consoleVo.setType(ConsoleTypeEnum.GROUP_SCORE.getValue());
        consoleVo.setNode(ConsoleTypeEnum.GROUP_SCORE.getName());

        consoleVo.setGroupType(GroupCategoryEnum.getInstance(topKpiGroup.getCategory()).getValue());
        consoleVo.setGroupName(topKpiGroup.getName());
        consoleVo.setAssessType(GroupFlowTypeEnum.getInstance(kpiGroupFlow.getType()).getValue());
        return consoleVo;
    }

    @Override
    ConsoleTypeEnum getConsoleType() {
        return ConsoleTypeEnum.GROUP_SCORE;
    }
}
