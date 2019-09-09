package cn.promptness.kpi.service.inner.flow;

import cn.promptness.kpi.support.common.enums.ConsoleTypeEnum;
import cn.promptness.kpi.support.common.utils.AssertUtil;
import com.google.common.collect.Maps;
import cn.promptness.kpi.domain.pojo.KpiConsole;
import cn.promptness.kpi.support.exception.BizExceptionEnum;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author linhuid
 * @date 2019/7/4 15:39
 * @since v1.0.0
 */
@Service
public class BaseGroupFlowHandler {


    private Map<ConsoleTypeEnum, AbstractGroupFlowExecutor> executors = Maps.newHashMap();

    void register(ConsoleTypeEnum consoleTypeEnum, AbstractGroupFlowExecutor abstractGroupFlowExecutor) {
        AbstractGroupFlowExecutor groupFlowExecutor = executors.put(consoleTypeEnum, abstractGroupFlowExecutor);
        AssertUtil.isNull(groupFlowExecutor, BizExceptionEnum.SERVER_ERROR);
    }

    public void commitTask(ConsoleTypeEnum consoleTypeEnum, KpiConsole kpiConsole) {
        AbstractGroupFlowExecutor groupFlowExecutor = executors.get(consoleTypeEnum);
        AssertUtil.notNull(groupFlowExecutor, BizExceptionEnum.SERVER_ERROR);

        groupFlowExecutor.commitTask(kpiConsole);
    }

}
