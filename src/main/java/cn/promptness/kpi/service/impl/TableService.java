package cn.promptness.kpi.service.impl;

import cn.promptness.kpi.dao.KpiConsoleMapper;
import cn.promptness.kpi.service.inner.console.BaseConsoleHandler;
import cn.promptness.kpi.support.common.enums.ConsoleTypeEnum;
import cn.promptness.kpi.support.common.utils.AssertUtil;
import cn.promptness.kpi.domain.pojo.KpiConsole;
import cn.promptness.kpi.domain.vo.hr.LoginUserRsp;
import cn.promptness.kpi.support.exception.BizExceptionEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author linhuid
 * @date 2019/7/2 14:23
 * @since v1.0.0
 */
@Service
public class TableService {


    @Resource
    private BaseConsoleHandler consoleHandler;
    @Resource
    private KpiConsoleMapper kpiConsoleMapper;


    /**
     * 获取卡片对应的列表数据
     *
     * @param consoleId 卡片id
     * @param user      当前登录用户信息
     * @return 列表数据
     * @author linhuid
     * @date 2019/7/2 14:37
     * @since v1.0.0
     */
    public Object getTableData(Integer consoleId, LoginUserRsp user) {

        String userId = user.getUsername();

        KpiConsole kpiConsole = kpiConsoleMapper.getConsoleByIdAndUserId(consoleId, userId);

        AssertUtil.notNull(kpiConsole, BizExceptionEnum.SERVER_ERROR);

        ConsoleTypeEnum consoleTypeEnum = ConsoleTypeEnum.getInstance(kpiConsole.getNode());

        return consoleHandler.getTableData(consoleTypeEnum, kpiConsole);
    }
}
