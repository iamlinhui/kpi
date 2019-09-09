package cn.promptness.kpi.service.inner.console;

import cn.promptness.kpi.dao.KpiProfileMapper;
import cn.promptness.kpi.support.common.enums.ConsoleTypeEnum;
import com.google.common.collect.Lists;
import cn.promptness.kpi.domain.pojo.KpiConsole;
import cn.promptness.kpi.domain.pojo.KpiGroup;
import cn.promptness.kpi.domain.pojo.KpiProfile;
import cn.promptness.kpi.domain.vo.console.ConsoleRsp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author linhuid
 * @date 2019/7/2 8:43
 * @since v1.0.0
 */
@Service
public class ConsoleConfirmExecutor extends AbstractConsoleExecutor {

    @Resource
    private KpiProfileMapper kpiProfileMapper;

    @Override
    List<ConsoleRsp.ConsoleVO> doConsole(List<KpiConsole> kpiConsoleList,Map<Integer, KpiGroup> kpiGroupMap) {

        List<ConsoleRsp.ConsoleVO> consoleVoList = Lists.newArrayList();
        for (KpiConsole kpiConsole : kpiConsoleList) {
            KpiGroup kpiGroup = kpiGroupMap.get(kpiConsole.getGroupId());
            KpiProfile kpiProfile = kpiProfileMapper.getProfileById(kpiConsole.getProfileId());
            ConsoleRsp.ConsoleVO consoleVO = buildConsoleVO(kpiConsole, kpiGroup ,kpiProfile);
            consoleVoList.add(consoleVO);
        }

        return consoleVoList;
    }

    @Override
    Object getTableData(KpiConsole kpiConsole) {
        return null;
    }

    private ConsoleRsp.ConsoleVO buildConsoleVO(KpiConsole kpiConsole, KpiGroup kpiGroup, KpiProfile kpiProfile) {
        ConsoleRsp.ConsoleVO consoleVO = super.buildConsole(kpiConsole);
        consoleVO.setType(ConsoleTypeEnum.CONFIRM.getValue());
        consoleVO.setNode(ConsoleTypeEnum.CONFIRM.getName());
        consoleVO.setInterviewName(kpiGroup.getInterviewerName());
        consoleVO.setResult(kpiProfile.getFinalResult());
        return consoleVO;
    }

    @Override
    ConsoleTypeEnum getConsoleType() {
        return ConsoleTypeEnum.CONFIRM;
    }
}
