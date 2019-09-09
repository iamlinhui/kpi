package cn.promptness.kpi.service.impl;

import cn.promptness.kpi.dao.*;
import cn.promptness.kpi.service.inner.console.BaseConsoleHandler;
import cn.promptness.kpi.support.common.enums.ConsoleTypeEnum;
import cn.promptness.kpi.support.common.enums.ProjectStatusEnum;
import cn.promptness.kpi.support.common.enums.RoleTypeEnum;
import cn.promptness.kpi.support.common.utils.AssertUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import cn.promptness.kpi.domain.pojo.KpiConsole;
import cn.promptness.kpi.domain.pojo.KpiGroup;
import cn.promptness.kpi.domain.pojo.KpiProject;
import cn.promptness.kpi.domain.vo.console.ConsoleProjectRsp;
import cn.promptness.kpi.domain.vo.console.ConsoleRsp;
import cn.promptness.kpi.service.rpc.UserService;
import cn.promptness.kpi.support.exception.BizExceptionEnum;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author linhuid
 * @date 2019/7/1 20:58
 * @since v1.0.0
 */
@Service
public class ConsoleService {

    @Resource
    private UserService userService;
    @Resource
    private KpiProjectMapper kpiProjectMapper;
    @Resource
    private KpiProfileMapper kpiProfileMapper;
    @Resource
    private KpiGroupMapper kpiGroupMapper;
    @Resource
    private KpiGroupFlowMapper kpiGroupFlowMapper;
    @Resource
    private KpiConsoleMapper kpiConsoleMapper;
    @Resource
    private BaseConsoleHandler consoleHandler;


    /**
     * 季度考核列表
     *
     * @param userId 当前登录用户
     * @return 考核列表
     * @author linhuid
     * @date 2019/7/1 22:20
     * @since v1.0.0
     */
    public List<ConsoleProjectRsp> listProjectList(String userId) {

        List<ConsoleProjectRsp> consoleProjectRspList = Lists.newArrayList();
        List<KpiProject> kpiProjectList;
        //校验角色 (绩效审批人 和  绩效管理员 可以查看全部的考核方案)
        boolean approvalRole = userService.checkApprovalRole(userId, RoleTypeEnum.PERFORMANCE_APPROVER, RoleTypeEnum.PERFORMANCE_MANAGER);
        if (approvalRole) {
            kpiProjectList = kpiProjectMapper.listProject(Lists.newArrayList(ProjectStatusEnum.INIT.getValue(), ProjectStatusEnum.EXAMINING.getValue(), ProjectStatusEnum.ARCHIVE.getValue()), true);
        } else {

            // 考核人
            List<Integer> projectIdList = kpiProfileMapper.listProjectIdByUserId(userId);
            // 面谈人
            projectIdList.addAll(kpiGroupMapper.listProjectIdByUserId(userId));
            // 审批人
            projectIdList.addAll(kpiGroupFlowMapper.listProjectIdByUserId(userId));

            HashSet<Integer> projectIdSet = Sets.newHashSet(projectIdList);
            if (CollectionUtils.isEmpty(projectIdSet)) {
                return consoleProjectRspList;
            }

            kpiProjectList = kpiProjectMapper.listProjectByIdAndStates(Lists.newArrayList(projectIdSet), Lists.newArrayList(ProjectStatusEnum.INIT.getValue(), ProjectStatusEnum.EXAMINING.getValue(), ProjectStatusEnum.ARCHIVE.getValue()), true);
        }

        for (KpiProject kpiProject : kpiProjectList) {
            ConsoleProjectRsp consoleProjectRsp = new ConsoleProjectRsp();
            consoleProjectRsp.setProjectId(kpiProject.getId());
            consoleProjectRsp.setProjectName(kpiProject.getName());
            consoleProjectRspList.add(consoleProjectRsp);
        }

        return consoleProjectRspList;
    }

    /**
     * @param userId    当前登录用户
     * @param projectId 当前考核方案
     * @return 工作台卡片
     * @author linhuid
     * @date 2019/7/1 22:22
     * @since v1.0.0
     */
    public ConsoleRsp listConsoleCard(String userId, Integer projectId) {

        KpiProject kpiProject = kpiProjectMapper.getProjectById(projectId);
        AssertUtil.notNull(kpiProject, BizExceptionEnum.SERVER_ERROR);

        List<KpiConsole> kpiConsoleList = kpiConsoleMapper.listConsole(userId, projectId);


        List<Integer> groupIdList = kpiConsoleList.stream().map(KpiConsole::getGroupId).distinct().collect(Collectors.toList());
        List<KpiGroup> kpiGroupList = kpiGroupMapper.listGroupById(groupIdList);
        Map<Integer, KpiGroup> kpiGroupMap = Maps.uniqueIndex(kpiGroupList, KpiGroup::getId);

        // 过滤未开始考核的目标制定卡片   过滤隐藏的卡片
        Map<Integer, List<KpiConsole>> kpiConsoleMap = kpiConsoleList.stream().filter(KpiConsole::getIsHide).collect(Collectors.groupingBy(KpiConsole::getNode));

        List<ConsoleRsp.ConsoleVO> consoleList = Lists.newArrayList();
        for (Map.Entry<Integer, List<KpiConsole>> entry : kpiConsoleMap.entrySet()) {
            ConsoleTypeEnum consoleTypeEnum = ConsoleTypeEnum.getInstance(entry.getKey());
            consoleList.addAll(consoleHandler.getConsoleList(consoleTypeEnum, entry.getValue(),kpiGroupMap));
        }

        ConsoleRsp consoleRsp = new ConsoleRsp();
        consoleRsp.setProjectName(kpiProject.getName());
        consoleRsp.setProjectId(kpiProject.getId());
        consoleRsp.setDataList(consoleList);

        return consoleRsp;
    }


}
