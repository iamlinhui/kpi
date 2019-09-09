package cn.promptness.kpi.service.impl;

import cn.promptness.kpi.dao.KpiProfileMapper;
import cn.promptness.kpi.dao.KpiProjectTimeMapper;
import cn.promptness.kpi.domain.pojo.KpiConsole;
import cn.promptness.kpi.domain.pojo.KpiProfile;
import cn.promptness.kpi.domain.pojo.KpiProjectTime;
import cn.promptness.kpi.domain.vo.project.AddMemberReq;
import cn.promptness.kpi.service.inner.ProjectInnerService;
import cn.promptness.kpi.support.common.enums.ConsoleTypeEnum;
import cn.promptness.kpi.support.common.enums.ProcessJobEnum;
import cn.promptness.kpi.support.common.enums.ProjectTimeTypeEnum;
import cn.promptness.kpi.support.common.utils.AssertUtil;
import cn.promptness.kpi.support.exception.BizExceptionEnum;
import com.google.common.collect.Sets;
import cn.promptness.kpi.service.workflow.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author linhuid
 * @date 2019/7/1 21:47
 * @since v1.0.0
 */
@Service
public class ProjectService {


    @Resource
    private ActivityService activityService;
    @Resource
    private KpiProfileMapper kpiProfileMapper;
    @Resource
    private KpiProjectTimeMapper kpiProjectTimeMapper;
    @Resource
    private ProjectInnerService projectInnerService;

    public void confirmProject(Integer projectId) {
        // 1.目标制定卡片 设置为显示出来
        // 2.project 从  1未确认 -> 2目标制定中
        // 3.增加实际完成时间卡位
        List<KpiProjectTime> kpiProjectTimeList = kpiProjectTimeMapper.listTimeByProjectIdAndType(projectId, ProjectTimeTypeEnum.PRE.getValue());
        projectInnerService.confirmProject(projectId, kpiProjectTimeList);

        //TODO 发送目标制定通知
    }


    /**
     * 分配人员后开始考核方案
     *
     * @param projectId 考核方案
     * @author linhuid
     * @date 2019/7/2 21:53
     * @since v1.0.0
     */
    public void startProject(Integer projectId) {

        // TODO 校验每个组的审批至少有一个审批人

        activityService.startProject(projectId);
    }


    /**
     * 增加考核人员名单
     *
     * @param addMemberReq 人员信息
     * @author linhuid
     * @date 2019/7/2 22:33
     * @since v1.0.0
     */
    public void addMember(AddMemberReq addMemberReq) {

        Integer projectId = addMemberReq.getProjectId();

        List<String> userIdList = addMemberReq.getUserIdList();
        List<String> existUserIdList = kpiProfileMapper.listUserIdByProjectId(projectId);

        // 是否存在已经入库的
        Set<String> intersection = Sets.intersection(Sets.newHashSet(userIdList), Sets.newHashSet(existUserIdList));
        AssertUtil.isTrue(intersection.isEmpty(), BizExceptionEnum.SERVER_ERROR);

        KpiProjectTime kpiProjectTime = kpiProjectTimeMapper.getTime(projectId, ProjectTimeTypeEnum.PRE, ProcessJobEnum.SELF);

        for (String userId : userIdList) {

            KpiProfile kpiProfile = buildKpiProfile(projectId, userId);
            KpiConsole kpiConsole = buildKpiConsole(projectId, kpiProjectTime, userId);

            projectInnerService.saveProfileAndConsole(kpiProfile, kpiConsole);
        }

    }

    private KpiConsole buildKpiConsole(Integer projectId, KpiProjectTime kpiProjectTime, String userId) {
        KpiConsole kpiConsole = new KpiConsole();
        kpiConsole.setProjectId(projectId);
        kpiConsole.setNode(ConsoleTypeEnum.MAKE_AIMS.getValue());
        kpiConsole.setUserId(userId);
        kpiConsole.setFinishCount(0);
        kpiConsole.setTotalCount(1);
        kpiConsole.setCreateTime(new Date());
        kpiConsole.setDeadLine(kpiProjectTime.getSettingTime());
        kpiConsole.setIsHide(Boolean.TRUE);
        return kpiConsole;
    }

    private KpiProfile buildKpiProfile(Integer projectId, String userId) {
        KpiProfile kpiProfile = new KpiProfile();
        kpiProfile.setUserId(userId);
        kpiProfile.setProjectId(projectId);
        kpiProfile.setCreateTime(new Date());
        return kpiProfile;
    }
}
