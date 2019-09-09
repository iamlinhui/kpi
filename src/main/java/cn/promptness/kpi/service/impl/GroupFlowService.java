package cn.promptness.kpi.service.impl;

import cn.promptness.kpi.dao.KpiConsoleMapper;
import cn.promptness.kpi.service.inner.flow.BaseGroupFlowHandler;
import cn.promptness.kpi.support.common.enums.ConsoleTypeEnum;
import cn.promptness.kpi.support.common.utils.AssertUtil;
import cn.promptness.kpi.domain.pojo.KpiConsole;
import cn.promptness.kpi.domain.vo.hr.LoginUserRsp;
import cn.promptness.kpi.support.exception.BizExceptionEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 审批链相关
 *
 * @author linhuid
 * @date 2019/7/4 15:10
 * @since v1.0.0
 */
@Service
public class GroupFlowService {

    @Resource
    private KpiConsoleMapper kpiConsoleMapper;
    @Resource
    private BaseGroupFlowHandler groupFlowHandler;

    public void commitTask(LoginUserRsp user, Integer consoleId) {

        KpiConsole kpiConsole = kpiConsoleMapper.getConsoleByIdAndUserId(consoleId, user.getUsername());
        AssertUtil.notNull(kpiConsole, BizExceptionEnum.SERVER_ERROR);

        ConsoleTypeEnum consoleTypeEnum = ConsoleTypeEnum.getInstance(kpiConsole.getNode());
        groupFlowHandler.commitTask(consoleTypeEnum, kpiConsole);
    }
}
