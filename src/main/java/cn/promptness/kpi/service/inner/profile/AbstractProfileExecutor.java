package cn.promptness.kpi.service.inner.profile;

import cn.promptness.kpi.support.common.enums.ConsoleTypeEnum;
import cn.promptness.kpi.domain.pojo.KpiConsole;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

public abstract class AbstractProfileExecutor {

    @Resource
    private BaseProfileHandler profileHandler;


    abstract void confirm(KpiConsole kpiConsole);

    /**
     * 仅限结果确认/申述
     *
     * @author linhuid
     * @date 2019/7/4 15:58
     * @since v1.0.0
     */
    abstract ConsoleTypeEnum getConsoleType();


    @PostConstruct
    public void register() {
        profileHandler.register(getConsoleType(), this);
    }

    abstract void appeal(KpiConsole kpiConsole, String reason);
}
