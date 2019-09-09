package cn.promptness.kpi.service.inner.console;

import cn.promptness.kpi.support.common.enums.ConsoleTypeEnum;
import cn.promptness.kpi.support.common.utils.AssertUtil;
import com.google.common.collect.Maps;
import cn.promptness.kpi.domain.pojo.KpiConsole;
import cn.promptness.kpi.domain.pojo.KpiGroup;
import cn.promptness.kpi.domain.vo.console.ConsoleRsp;
import cn.promptness.kpi.support.exception.BizExceptionEnum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 卡片处理器
 *
 * @author linhuid
 * @date 2019/7/2 8:47
 * @since v1.0.0
 */
@Service
public class BaseConsoleHandler {

    private Map<ConsoleTypeEnum, AbstractConsoleExecutor> executors = Maps.newHashMap();

    void register(ConsoleTypeEnum consoleType, AbstractConsoleExecutor abstractConsoleExecutor) {
        AbstractConsoleExecutor consoleExecutor = executors.put(consoleType, abstractConsoleExecutor);
        AssertUtil.isNull(consoleExecutor, BizExceptionEnum.SERVER_ERROR);
    }

    public List<ConsoleRsp.ConsoleVO> getConsoleList(ConsoleTypeEnum consoleTypeEnum, List<KpiConsole> kpiConsoleList, Map<Integer, KpiGroup> kpiGroupMap) {
        AbstractConsoleExecutor consoleExecutor = executors.get(consoleTypeEnum);
        AssertUtil.notNull(consoleExecutor, BizExceptionEnum.SERVER_ERROR);
        return consoleExecutor.doConsole(kpiConsoleList, kpiGroupMap);
    }

    public Object getTableData(ConsoleTypeEnum consoleTypeEnum, KpiConsole kpiConsoleList) {
        AbstractConsoleExecutor consoleExecutor = executors.get(consoleTypeEnum);
        AssertUtil.notNull(consoleExecutor, BizExceptionEnum.SERVER_ERROR);
        return consoleExecutor.getTableData(kpiConsoleList);
    }

}
