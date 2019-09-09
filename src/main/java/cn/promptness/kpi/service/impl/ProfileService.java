package cn.promptness.kpi.service.impl;

import cn.promptness.kpi.dao.KpiConsoleMapper;
import cn.promptness.kpi.service.inner.profile.BaseProfileHandler;
import cn.promptness.kpi.support.common.enums.ConsoleTypeEnum;
import cn.promptness.kpi.support.common.utils.AssertUtil;
import cn.promptness.kpi.domain.pojo.KpiConsole;
import cn.promptness.kpi.domain.vo.hr.LoginUserRsp;
import cn.promptness.kpi.support.exception.BizExceptionEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class ProfileService {

    @Resource
    private KpiConsoleMapper kpiConsoleMapper;
    @Resource
    private BaseProfileHandler profileHandler;

    /**
     * 确认结果
     *
     * @param user      当前登陆用户
     * @param consoleId 确认卡片id
     * @author linhuid
     * @date 2019-07-06 22:25
     * @since v1.0.0
     */
    public void confirmProfile(LoginUserRsp user, Integer consoleId) {

        KpiConsole kpiConsole = kpiConsoleMapper.getConsoleByIdAndUserId(consoleId, user.getUsername());
        AssertUtil.notNull(kpiConsole, BizExceptionEnum.SERVER_ERROR);

        ConsoleTypeEnum consoleTypeEnum = ConsoleTypeEnum.getInstance(kpiConsole.getNode());
        profileHandler.confirm(consoleTypeEnum, kpiConsole);
    }

    /**
     * 申述结果
     *
     * @param user      当前登陆用户
     * @param consoleId 确认卡片id
     * @param reason    申述理由
     * @author linhuid
     * @date 2019-07-06 22:25
     * @since v1.0.0
     */
    public void appealProfile(LoginUserRsp user, Integer consoleId, String reason) {
        KpiConsole kpiConsole = kpiConsoleMapper.getConsoleByIdAndUserId(consoleId, user.getUsername());
        AssertUtil.notNull(kpiConsole, BizExceptionEnum.SERVER_ERROR);

        ConsoleTypeEnum consoleTypeEnum = ConsoleTypeEnum.getInstance(kpiConsole.getNode());
        profileHandler.appeal(consoleTypeEnum, kpiConsole, reason);
    }

    public void cancelAppeal(LoginUserRsp user, Integer consoleId) {
        KpiConsole kpiConsole = kpiConsoleMapper.getConsoleByIdAndUserId(consoleId, user.getUsername());
        AssertUtil.notNull(kpiConsole, BizExceptionEnum.SERVER_ERROR);

        ConsoleTypeEnum consoleTypeEnum = ConsoleTypeEnum.getInstance(kpiConsole.getNode());
        AssertUtil.isTrue(Objects.equals(consoleTypeEnum, ConsoleTypeEnum.APPEAL), BizExceptionEnum.SERVER_ERROR);

        // 将申述卡片变回绩效确认卡片
    }
}
