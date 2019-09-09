package cn.promptness.kpi.service.inner.console;

import cn.promptness.kpi.dao.KpiProfilePointMapper;
import cn.promptness.kpi.domain.pojo.KpiConsole;
import cn.promptness.kpi.domain.pojo.KpiGroup;
import cn.promptness.kpi.domain.pojo.KpiProfilePoint;
import cn.promptness.kpi.domain.vo.console.ConsoleRsp;
import cn.promptness.kpi.domain.vo.table.TableMakeAimsRsp;
import cn.promptness.kpi.support.common.enums.ConsoleTypeEnum;
import cn.promptness.kpi.support.common.enums.ProfilePointTypeEnum;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 目标制定
 *
 * @author linhuid
 * @date 2019/7/2 8:43
 * @since v1.0.0
 */
@Service
public class ConsoleMakeAimsExecutor extends AbstractConsoleExecutor {


    @Resource
    private KpiProfilePointMapper kpiProfilePointMapper;


    @Override
    List<ConsoleRsp.ConsoleVO> doConsole(List<KpiConsole> kpiConsoleList, Map<Integer, KpiGroup> kpiGroupMap) {

        List<ConsoleRsp.ConsoleVO> consoleVoList = Lists.newArrayList();
        for (KpiConsole kpiConsole : kpiConsoleList) {
            ConsoleRsp.ConsoleVO consoleVO = buildConsoleVO(kpiConsole);
            consoleVoList.add(consoleVO);
        }

        return consoleVoList;
    }

    @Override
    Object getTableData(KpiConsole kpiConsole) {

        List<KpiProfilePoint> kpiProfilePointList = kpiProfilePointMapper.listProfilePointByProfileId(kpiConsole.getProfileId());

        return buildTableMakeAimsRsp(kpiConsole, kpiProfilePointList);
    }

    private TableMakeAimsRsp buildTableMakeAimsRsp(KpiConsole kpiConsole, List<KpiProfilePoint> kpiProfilePointList) {

        List<TableMakeAimsRsp.KpiProfilePointVO> dataList = Lists.newArrayList();
        for (KpiProfilePoint kpiProfilePoint : kpiProfilePointList) {
            TableMakeAimsRsp.KpiProfilePointVO kpiProfilePointVO = buildKpiProfilePointVO(kpiProfilePoint);
            dataList.add(kpiProfilePointVO);
        }

        TableMakeAimsRsp tableMakeAimsRsp = new TableMakeAimsRsp();
        tableMakeAimsRsp.setProfileId(kpiConsole.getProfileId());
        tableMakeAimsRsp.setUserId(kpiConsole.getUserId());
        tableMakeAimsRsp.setDataList(dataList);

        return tableMakeAimsRsp;
    }

    private TableMakeAimsRsp.KpiProfilePointVO buildKpiProfilePointVO(KpiProfilePoint kpiProfilePoint) {
        TableMakeAimsRsp.KpiProfilePointVO kpiProfilePointVO = new TableMakeAimsRsp.KpiProfilePointVO();
        kpiProfilePointVO.setPointId(kpiProfilePoint.getId());
        kpiProfilePointVO.setType(ProfilePointTypeEnum.getInstance(kpiProfilePoint.getType()).getValue());
        kpiProfilePointVO.setTarget(kpiProfilePoint.getTarget());
        kpiProfilePointVO.setPower(kpiProfilePoint.getPower());
        kpiProfilePointVO.setExplain(kpiProfilePoint.getExplain());
        kpiProfilePointVO.setSource(kpiProfilePoint.getSource());
        kpiProfilePointVO.setCanEdit(kpiProfilePoint.getCanEdit());
        return kpiProfilePointVO;
    }

    private ConsoleRsp.ConsoleVO buildConsoleVO(KpiConsole kpiConsole) {
        ConsoleRsp.ConsoleVO consoleVO = super.buildConsole(kpiConsole);
        consoleVO.setType(ConsoleTypeEnum.MAKE_AIMS.getValue());
        consoleVO.setNode(ConsoleTypeEnum.MAKE_AIMS.getName());
        return consoleVO;
    }

    @Override
    ConsoleTypeEnum getConsoleType() {
        return ConsoleTypeEnum.MAKE_AIMS;
    }
}
