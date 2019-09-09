package cn.promptness.kpi.service.inner.flow;

import cn.promptness.kpi.support.common.enums.ConsoleTypeEnum;
import cn.promptness.kpi.domain.pojo.KpiConsole;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author linhuid
 * @date 2019/7/4 15:39
 * @since v1.0.0
 */
public abstract class AbstractGroupFlowExecutor {


    @Resource
    private BaseGroupFlowHandler groupFlowHandler;


    abstract void commitTask(KpiConsole kpiConsole);


    /**
     * 仅限分组/大组
     *
     * @author linhuid
     * @date 2019/7/4 15:58
     * @since v1.0.0
     */
    abstract ConsoleTypeEnum getConsoleType();


    @PostConstruct
    public void register() {
        groupFlowHandler.register(getConsoleType(), this);
    }

}
