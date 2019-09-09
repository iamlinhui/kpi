package cn.promptness.kpi.service.inner.console;

import cn.promptness.kpi.support.common.enums.ConsoleTypeEnum;
import cn.promptness.kpi.domain.pojo.KpiConsole;
import cn.promptness.kpi.domain.pojo.KpiGroup;
import cn.promptness.kpi.domain.vo.console.ConsoleRsp;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * 卡片数据执行器
 *
 * @author linhuid
 * @date 2019/7/2 8:34
 * @since v1.0.0
 */
public abstract class AbstractConsoleExecutor {

    @Autowired
    private BaseConsoleHandler consoleHandler;


    /**
     * 填充控制台卡片数据
     *
     * @param kpiConsoleList 卡片元数据
     * @param kpiGroupMap    卡片上考核组信息
     * @return 卡片数据
     * @author linhuid
     * @date 2019/7/2 8:56
     * @since v1.0.0
     */
    abstract List<ConsoleRsp.ConsoleVO> doConsole(List<KpiConsole> kpiConsoleList, Map<Integer, KpiGroup> kpiGroupMap);


    /**
     * 根据卡片查出需要展示的列表数据
     *
     * @param kpiConsole 卡片数据
     * @return 卡片对应的列表数据
     * @author linhuid
     * @date 2019/7/2 14:29
     * @since v1.0.0
     */
    abstract Object getTableData(KpiConsole kpiConsole);


    /**
     * 卡片处理器标识
     *
     * @return 卡片类型
     * @author linhuid
     * @date 2019/7/2 8:57
     * @since v1.0.0
     */
    abstract ConsoleTypeEnum getConsoleType();


    ConsoleRsp.ConsoleVO buildConsole(KpiConsole kpiConsole) {
        ConsoleRsp.ConsoleVO consoleVO = new ConsoleRsp.ConsoleVO();
        consoleVO.setConsoleId(kpiConsole.getId());
        consoleVO.setDeadline(consoleVO.getDeadline());
        consoleVO.setFinishTime(consoleVO.getFinishTime());
        consoleVO.setIsEnd(kpiConsole.getIsFinished());
        consoleVO.setGroupId(kpiConsole.getGroupId());
        consoleVO.setRemark(kpiConsole.getRemark());
        consoleVO.setFinishNum(kpiConsole.getFinishCount());
        consoleVO.setCreateTime(kpiConsole.getCreateTime());
        return consoleVO;
    }

    @PostConstruct
    public void register() {
        consoleHandler.register(getConsoleType(), this);
    }

}
