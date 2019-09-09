package cn.promptness.kpi.service.inner.group;

import cn.promptness.kpi.domain.pojo.KpiConsole;
import cn.promptness.kpi.domain.pojo.KpiGroup;
import cn.promptness.kpi.domain.vo.table.TableGroupRsp;
import cn.promptness.kpi.support.common.enums.GroupCategoryEnum;
import cn.promptness.kpi.support.common.utils.AssertUtil;
import cn.promptness.kpi.support.exception.BizExceptionEnum;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author linhuid
 * @date 2019/7/2 18:45
 * @since v1.0.0
 */
@Service
public class BaseGroupHandler {

    private Map<GroupCategoryEnum, AbstractGroupExecutor> executors = Maps.newHashMap();

    void register(GroupCategoryEnum groupCategoryEnum, AbstractGroupExecutor abstractGroupExecutor) {
        AbstractGroupExecutor consoleExecutor = executors.put(groupCategoryEnum, abstractGroupExecutor);
        AssertUtil.isNull(consoleExecutor, BizExceptionEnum.SERVER_ERROR);
    }

    public void start(GroupCategoryEnum groupCategoryEnum, List<KpiGroup> kpiGroupList) {
        AbstractGroupExecutor groupExecutor = executors.get(groupCategoryEnum);
        AssertUtil.notNull(groupExecutor, BizExceptionEnum.SERVER_ERROR);
        boolean result = groupExecutor.startProcess(kpiGroupList);
        AssertUtil.isTrue(result, BizExceptionEnum.SERVER_ERROR);
    }

    public void commitTask(GroupCategoryEnum groupCategoryEnum, KpiConsole kpiConsole, KpiGroup kpiGroup) {

        AbstractGroupExecutor groupExecutor = executors.get(groupCategoryEnum);
        AssertUtil.notNull(groupExecutor, BizExceptionEnum.SERVER_ERROR);
        groupExecutor.commitTask(kpiConsole,kpiGroup);
    }


    public TableGroupRsp buildTableGroupRsp(GroupCategoryEnum groupCategoryEnum, KpiConsole kpiConsole, KpiGroup kpiGroup) {

        AbstractGroupExecutor groupExecutor = executors.get(groupCategoryEnum);
        AssertUtil.notNull(groupExecutor, BizExceptionEnum.SERVER_ERROR);
        return groupExecutor.buildTableGroupRsp(kpiConsole,kpiGroup);
    }
}
