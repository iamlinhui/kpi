package cn.promptness.kpi.service.inner.console;

import cn.promptness.kpi.domain.pojo.KpiConsole;
import cn.promptness.kpi.domain.pojo.KpiGroup;
import cn.promptness.kpi.domain.vo.console.ConsoleRsp;
import cn.promptness.kpi.support.common.enums.ConsoleTypeEnum;
import cn.promptness.kpi.support.common.enums.GroupCategoryEnum;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author linhuid
 * @date 2019/7/2 8:43
 * @since v1.0.0
 */
@Service
public class ConsoleInterviewExecutor extends AbstractConsoleExecutor {


    @Override
    List<ConsoleRsp.ConsoleVO> doConsole(List<KpiConsole> kpiConsoleList, Map<Integer, KpiGroup> kpiGroupMap) {

        List<ConsoleRsp.ConsoleVO> consoleVoList = Lists.newArrayList();
        for (KpiConsole kpiConsole : kpiConsoleList) {
            KpiGroup kpiGroup = kpiGroupMap.get(kpiConsole.getGroupId());
            ConsoleRsp.ConsoleVO consoleVO = buildConsoleVO(kpiConsole, kpiGroup);
            consoleVoList.add(consoleVO);
        }

        return consoleVoList;
    }

    @Override
    Object getTableData(KpiConsole kpiConsole) {





        return null;
    }

    private ConsoleRsp.ConsoleVO buildConsoleVO(KpiConsole kpiConsole, KpiGroup kpiGroup) {
        ConsoleRsp.ConsoleVO consoleVO = super.buildConsole(kpiConsole);
        consoleVO.setType(ConsoleTypeEnum.INTERVIEW.getValue());
        consoleVO.setNode(ConsoleTypeEnum.INTERVIEW.getName());

        consoleVO.setGroupName(kpiGroup.getName());
        consoleVO.setGroupType(GroupCategoryEnum.getInstance(kpiGroup.getCategory()).getValue());

        return consoleVO;
    }

    @Override
    ConsoleTypeEnum getConsoleType() {
        return ConsoleTypeEnum.INTERVIEW;
    }
}
