package cn.promptness.kpi.service.inner.profile;

import cn.promptness.kpi.support.common.enums.ConsoleTypeEnum;
import cn.promptness.kpi.support.common.utils.AssertUtil;
import com.google.common.collect.Maps;
import cn.promptness.kpi.domain.pojo.KpiConsole;
import cn.promptness.kpi.support.exception.BizExceptionEnum;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BaseProfileHandler {


    private Map<ConsoleTypeEnum, AbstractProfileExecutor> executors = Maps.newHashMap();

    void register(ConsoleTypeEnum consoleTypeEnum, AbstractProfileExecutor abstractProfileExecutor) {
        AbstractProfileExecutor profileExecutor = executors.put(consoleTypeEnum, abstractProfileExecutor);
        AssertUtil.isNull(profileExecutor, BizExceptionEnum.SERVER_ERROR);
    }

    public void confirm(ConsoleTypeEnum consoleTypeEnum, KpiConsole kpiConsole) {
        AbstractProfileExecutor profileExecutor = executors.get(consoleTypeEnum);
        AssertUtil.notNull(profileExecutor, BizExceptionEnum.SERVER_ERROR);
        profileExecutor.confirm(kpiConsole);
    }

    public void appeal(ConsoleTypeEnum consoleTypeEnum, KpiConsole kpiConsole, String reason) {
        AbstractProfileExecutor profileExecutor = executors.get(consoleTypeEnum);
        AssertUtil.notNull(profileExecutor, BizExceptionEnum.SERVER_ERROR);
        profileExecutor.appeal(kpiConsole,reason);
    }
}
