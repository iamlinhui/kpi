package cn.promptness.kpi.controller.admin;

import cn.promptness.kpi.domain.vo.HttpResult;
import cn.promptness.kpi.domain.vo.project.AddMemberReq;
import cn.promptness.kpi.service.impl.ProjectService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 考核方案配置接口
 *
 * @author linhuid
 * @date 2019/7/2 14:49
 * @since v1.0.0
 */
@RestController
@RequestMapping(value = "/project", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProjectController {

    @Resource
    private ProjectService projectService;


    /**
     * 确认提交考核方案(指定好人员之后点击此按钮开启目标制定)
     *
     * @param projectId 考核方案id
     * @author linhuid
     * @date 2019/7/2 16:06
     * @since v1.0.0
     */
    @PostMapping("/confirm")
    public HttpResult confirm(@RequestParam Integer projectId) {
        projectService.confirmProject(projectId);
        return HttpResult.getSuccessHttpResult();
    }


    /**
     * 分配人员后开始考核方案
     *
     * @param projectId 考核方案id
     * @author linhuid
     * @date 2019/7/2 16:06
     * @since v1.0.0
     */
    @PostMapping("/start")
    public HttpResult start(@RequestParam Integer projectId) {
        projectService.startProject(projectId);
        return HttpResult.getSuccessHttpResult();
    }


    /**
     * 增加考核人员名单
     *
     * @param addMemberReq 人员信息
     * @author linhuid
     * @date 2019/7/2 22:33
     * @since v1.0.0
     */
    @PostMapping("/add")
    public HttpResult add(@RequestBody AddMemberReq addMemberReq) {
        projectService.addMember(addMemberReq);
        return HttpResult.getSuccessHttpResult();
    }


}
