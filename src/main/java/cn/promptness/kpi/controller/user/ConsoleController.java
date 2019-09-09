package cn.promptness.kpi.controller.user;

import cn.promptness.kpi.domain.vo.HttpResult;
import cn.promptness.kpi.domain.vo.console.ConsoleProjectRsp;
import cn.promptness.kpi.domain.vo.console.ConsoleReq;
import cn.promptness.kpi.domain.vo.console.ConsoleRsp;
import cn.promptness.kpi.domain.vo.hr.LoginUserRsp;
import cn.promptness.kpi.service.impl.ConsoleService;
import cn.promptness.kpi.support.common.utils.BindUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 首页卡片控制器
 *
 * @author linhuid
 * @date 2019/7/1 19:24
 * @since v1.0.0
 */
@RestController
@RequestMapping(value = "/examine")
public class ConsoleController {

    @Resource
    private ConsoleService consoleService;


    /**
     * 季度考核列表
     *
     * @return 季度考核列表
     * @author linhuid
     * @date 2019/7/1 21:17
     * @since v1.0.0
     */
    @PostMapping(value = "/project", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public HttpResult<List<ConsoleProjectRsp>> getProjectList() {
        LoginUserRsp user = BindUtils.getUser();
        List<ConsoleProjectRsp> consoleProjectRsp = consoleService.listProjectList(user.getUsername());
        return HttpResult.getSuccessHttpResult(consoleProjectRsp);
    }


    /**
     * 工作台查看自己当前需要审批的任务
     *
     * @param consoleReq 请求参数
     * @return 卡片
     * @author linhuid
     * @date 2019/7/1 21:06
     * @since v1.0.0
     */
    @PostMapping(value = "/console", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public HttpResult<ConsoleRsp> getConsoleList(@RequestBody @Validated ConsoleReq consoleReq)  {
        LoginUserRsp user = BindUtils.getUser();
        ConsoleRsp consoleRsp = consoleService.listConsoleCard(user.getUsername(), consoleReq.getProjectId());
        return HttpResult.getSuccessHttpResult(consoleRsp);
    }
}

