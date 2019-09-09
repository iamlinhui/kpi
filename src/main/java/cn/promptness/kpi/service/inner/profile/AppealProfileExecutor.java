package cn.promptness.kpi.service.inner.profile;

import cn.promptness.kpi.support.common.enums.ConsoleTypeEnum;
import cn.promptness.kpi.domain.pojo.KpiConsole;
import org.springframework.stereotype.Service;

@Service
public class AppealProfileExecutor extends AbstractProfileExecutor {


    @Override
    void confirm(KpiConsole kpiConsole) {
        //        kpiProfileMapper 更新状态  和  设置确认时间

//        kpiConsoleMapper  更新卡片完成数量  和 卡片已完成

//        kpiProfileAppealMapper  更新状态  已确认
    }

    @Override
    ConsoleTypeEnum getConsoleType() {
        return ConsoleTypeEnum.APPEAL;
    }

    @Override
    void appeal(KpiConsole kpiConsole, String reason) {

    }
}
