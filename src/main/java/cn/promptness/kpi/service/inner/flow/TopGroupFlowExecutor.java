package cn.promptness.kpi.service.inner.flow;

import cn.promptness.kpi.dao.KpiGroupMapper;
import cn.promptness.kpi.support.common.enums.ConsoleTypeEnum;
import cn.promptness.kpi.support.common.enums.GroupCategoryEnum;
import cn.promptness.kpi.domain.pojo.KpiConsole;
import cn.promptness.kpi.domain.pojo.KpiGroup;
import cn.promptness.kpi.service.inner.group.BaseGroupHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author linhuid
 * @date 2019/7/4 15:41
 * @since v1.0.0
 */
@Service
public class TopGroupFlowExecutor extends AbstractGroupFlowExecutor {

    @Resource
    private KpiGroupMapper kpiGroupMapper;
    @Resource
    private BaseGroupHandler groupHandler;

    @Override
    void commitTask(KpiConsole kpiConsole) {

        KpiGroup kpiGroup = kpiGroupMapper.getGroupById(kpiConsole.getGroupId());

        GroupCategoryEnum groupCategoryEnum = GroupCategoryEnum.getInstance(kpiGroup.getCategory());

        groupHandler.commitTask(groupCategoryEnum, kpiConsole, kpiGroup);
    }

    @Override
    ConsoleTypeEnum getConsoleType() {
        return ConsoleTypeEnum.GROUP_SCORE;
    }
}
