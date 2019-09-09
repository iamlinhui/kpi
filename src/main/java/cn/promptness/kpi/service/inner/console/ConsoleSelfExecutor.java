package cn.promptness.kpi.service.inner.console;

import cn.promptness.kpi.dao.KpiProfileMapper;
import cn.promptness.kpi.dao.KpiProfilePointMapper;
import cn.promptness.kpi.domain.pojo.KpiConsole;
import cn.promptness.kpi.domain.pojo.KpiGroup;
import cn.promptness.kpi.domain.pojo.KpiProfile;
import cn.promptness.kpi.domain.pojo.KpiProfilePoint;
import cn.promptness.kpi.domain.vo.console.ConsoleRsp;
import cn.promptness.kpi.domain.vo.table.TableSelfRsp;
import cn.promptness.kpi.support.common.enums.ConsoleTypeEnum;
import cn.promptness.kpi.support.common.enums.ProfilePointTypeEnum;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 自评
 *
 * @author linhuid
 * @date 2019/7/2 8:43
 * @since v1.0.0
 */
@Service
public class ConsoleSelfExecutor extends AbstractConsoleExecutor {


    @Resource
    private KpiProfilePointMapper kpiProfilePointMapper;
    @Resource
    private KpiProfileMapper kpiProfileMapper;

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
        KpiProfile kpiProfile = kpiProfileMapper.getProfileById(kpiConsole.getProfileId());

        return buildTableSelfRsp(kpiConsole, kpiProfilePointList, kpiProfile);
    }

    private TableSelfRsp buildTableSelfRsp(KpiConsole kpiConsole, List<KpiProfilePoint> kpiProfilePointList, KpiProfile kpiProfile) {
        List<TableSelfRsp.KpiProfilePointVO> dataList = Lists.newArrayList();
        for (KpiProfilePoint kpiProfilePoint : kpiProfilePointList) {
            TableSelfRsp.KpiProfilePointVO kpiProfilePointVO = buildKpiProfilePointVO(kpiProfilePoint);
            dataList.add(kpiProfilePointVO);
        }

        TableSelfRsp tableSelfRsp = new TableSelfRsp();
        tableSelfRsp.setProfileId(kpiConsole.getProfileId());
        tableSelfRsp.setUserId(kpiConsole.getUserId());
        tableSelfRsp.setSelfSummary(kpiProfile.getSelfSummary());

        tableSelfRsp.setDataList(dataList);
        return tableSelfRsp;
    }

    private TableSelfRsp.KpiProfilePointVO buildKpiProfilePointVO(KpiProfilePoint kpiProfilePoint) {
        TableSelfRsp.KpiProfilePointVO kpiProfilePointVO = new TableSelfRsp.KpiProfilePointVO();
        kpiProfilePointVO.setPointId(kpiProfilePoint.getId());
        kpiProfilePointVO.setType(ProfilePointTypeEnum.getInstance(kpiProfilePoint.getType()).getValue());
        kpiProfilePointVO.setPower(kpiProfilePoint.getPower());
        kpiProfilePointVO.setTarget(kpiProfilePoint.getTarget());
        kpiProfilePointVO.setExplain(kpiProfilePoint.getExplain());
        kpiProfilePointVO.setSource(kpiProfilePoint.getSource());
        kpiProfilePointVO.setStatement(kpiProfilePoint.getStatement());
        kpiProfilePointVO.setScore(kpiProfilePoint.getScore());
        return kpiProfilePointVO;
    }

    private ConsoleRsp.ConsoleVO buildConsoleVO(KpiConsole kpiConsole) {
        ConsoleRsp.ConsoleVO consoleVO = super.buildConsole(kpiConsole);
        consoleVO.setType(ConsoleTypeEnum.SELF.getValue());
        consoleVO.setNode(ConsoleTypeEnum.SELF.getName());
        return consoleVO;
    }

    @Override
    ConsoleTypeEnum getConsoleType() {
        return ConsoleTypeEnum.SELF;
    }
}
