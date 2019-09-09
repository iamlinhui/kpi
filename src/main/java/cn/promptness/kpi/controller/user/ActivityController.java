package cn.promptness.kpi.controller.user;

import cn.promptness.kpi.domain.vo.hr.LoginUserRsp;
import cn.promptness.kpi.service.impl.GroupFlowService;
import cn.promptness.kpi.service.impl.ProfileService;
import cn.promptness.kpi.support.common.utils.BindUtils;
import cn.promptness.kpi.domain.vo.HttpResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 审批相关
 *
 * @author linhuid
 * @date 2019/7/2 14:47
 * @since v1.0.0
 */
@RestController
@RequestMapping(value = "/activity")
public class ActivityController {


    @Resource
    private GroupFlowService groupFlowService;
    @Resource
    private ProfileService profileService;

    /**
     * 审批提交数据到下一步
     *
     * @param consoleId 卡片id
     * @author linhuid
     * @date 2019/7/4 15:04
     * @since v1.0.0
     */
    @PostMapping(value = "/commit", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public HttpResult commitTask(@RequestParam("consoleId") Integer consoleId) {

        LoginUserRsp user = BindUtils.getUser();
        groupFlowService.commitTask(user, consoleId);

        return HttpResult.getSuccessHttpResult();
    }

    @PostMapping(value = "/confirm", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public HttpResult confirmProfile(@RequestParam("consoleId") Integer consoleId) {

        LoginUserRsp user = BindUtils.getUser();
        profileService.confirmProfile(user, consoleId);

        return HttpResult.getSuccessHttpResult();
    }

    @PostMapping(value = "/state", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public HttpResult state(@RequestParam("consoleId") Integer consoleId, @RequestParam("reason") String reason) {
        LoginUserRsp user = BindUtils.getUser();
        profileService.appealProfile(user, consoleId,reason);

        return HttpResult.getSuccessHttpResult();
    }

    @PostMapping(value = "/revoke", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public HttpResult revokeProfile(@RequestParam("consoleId") Integer consoleId) {

        LoginUserRsp user = BindUtils.getUser();
        profileService.cancelAppeal(user, consoleId);

        return HttpResult.getSuccessHttpResult();
    }

}
