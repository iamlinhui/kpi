package cn.promptness.kpi.service.inner.console;

import cn.promptness.kpi.domain.pojo.KpiConsole;
import cn.promptness.kpi.domain.pojo.KpiGroup;
import cn.promptness.kpi.domain.vo.console.ConsoleRsp;
import cn.promptness.kpi.support.common.enums.ConsoleTypeEnum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author linhuid
 * @date 2019/7/2 8:43
 * @since v1.0.0
 */
@Service
public class ConsoleHandleAppealExecutor extends AbstractConsoleExecutor {



    @Override
    List<ConsoleRsp.ConsoleVO> doConsole(List<KpiConsole> kpiConsoleList, Map<Integer, KpiGroup> kpiGroupMap) {
        return null;
    }

    @Override
    Object getTableData(KpiConsole kpiConsole) {
        return null;
    }

    @Override
    ConsoleTypeEnum getConsoleType() {
        return ConsoleTypeEnum.HANDLE_APPEAL;
    }
}
