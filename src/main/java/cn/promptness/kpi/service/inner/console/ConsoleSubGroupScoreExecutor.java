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
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author linhuid
 * @date 2019/7/2 8:43
 * @since v1.0.0
 */
@Service
public class ConsoleSubGroupScoreExecutor extends AbstractConsoleExecutor {


    @Resource
    private KpiGroupFlowMapper kpiGroupFlowMapper;
    @Resource
    private KpiGroupMapper kpiGroupMapper;
    @Resource
    private BaseGroupHandler groupHandler;


    @Override
    List<ConsoleRsp.ConsoleVO> doConsole(List<KpiConsole> kpiConsoleList, Map<Integer, KpiGroup> kpiGroupMap) {


        List<Integer> parentIdList = kpiGroupMap.values().stream().map(KpiGroup::getParentId).distinct().collect(Collectors.toList());
        List<KpiGroup> kpiGroupList = kpiGroupMapper.listGroupById(parentIdList);
        Map<Integer, KpiGroup> topKpiGroupMap = Maps.uniqueIndex(kpiGroupList, KpiGroup::getId);


        List<ConsoleRsp.ConsoleVO> consoleVoList = Lists.newArrayList();
        for (KpiConsole kpiConsole : kpiConsoleList) {

            // 分组
            KpiGroup subKpiGroup = kpiGroupMap.get(kpiConsole.getGroupId());

            //大组
            KpiGroup topKpiGroup = topKpiGroupMap.get(subKpiGroup.getParentId());

            //审批链
            KpiGroupFlow kpiGroupFlow = kpiGroupFlowMapper.getGroupFlowById(kpiConsole.getFlowId());

            ConsoleRsp.ConsoleVO consoleVO = buildConsoleVO(kpiConsole, subKpiGroup, topKpiGroup, kpiGroupFlow);
            consoleVoList.add(consoleVO);
        }

        return consoleVoList;
    }

    @Override
    Object getTableData(KpiConsole kpiConsole) {
        // 分组
        KpiGroup subKpiGroup = kpiGroupMapper.getGroupById(kpiConsole.getGroupId());
        GroupCategoryEnum groupCategoryEnum = GroupCategoryEnum.getInstance(subKpiGroup.getCategory());

        return groupHandler.buildTableGroupRsp(groupCategoryEnum, kpiConsole, subKpiGroup);
    }


    private ConsoleRsp.ConsoleVO buildConsoleVO(KpiConsole kpiConsole, KpiGroup subKpiGroup, KpiGroup topKpiGroup, KpiGroupFlow kpiGroupFlow) {

        ConsoleRsp.ConsoleVO consoleVO = super.buildConsole(kpiConsole);
        consoleVO.setType(ConsoleTypeEnum.SUB_GROUP_SCORE.getValue());
        consoleVO.setNode(ConsoleTypeEnum.SUB_GROUP_SCORE.getName());

        consoleVO.setGroupType(GroupCategoryEnum.getInstance(subKpiGroup.getCategory()).getValue());
        consoleVO.setGroupName(topKpiGroup.getName());
        consoleVO.setSubGroupName(subKpiGroup.getName());
        consoleVO.setAssessType(GroupFlowTypeEnum.getInstance(kpiGroupFlow.getType()).getValue());

        return consoleVO;
    }

    @Override
    ConsoleTypeEnum getConsoleType() {
        return ConsoleTypeEnum.SUB_GROUP_SCORE;
    }
}
