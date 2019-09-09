package cn.promptness.kpi.service.inner;

import cn.promptness.kpi.dao.KpiConsoleMapper;
import cn.promptness.kpi.dao.KpiProfileMapper;
import cn.promptness.kpi.dao.KpiProjectMapper;
import cn.promptness.kpi.dao.KpiProjectTimeMapper;
import cn.promptness.kpi.support.common.enums.ProjectStatusEnum;
import cn.promptness.kpi.support.common.enums.ProjectTimeTypeEnum;
import cn.promptness.kpi.support.common.utils.AssertUtil;
import cn.promptness.kpi.domain.pojo.KpiConsole;
import cn.promptness.kpi.domain.pojo.KpiProfile;
import cn.promptness.kpi.domain.pojo.KpiProjectTime;
import cn.promptness.kpi.support.exception.BizExceptionEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linhuid
 * @date 2019/7/2 22:15
 * @since v1.0.0
 */
@Service
public class ProjectInnerService {


    @Resource
    private KpiProfileMapper kpiProfileMapper;
    @Resource
    private KpiProjectMapper kpiProjectMapper;
    @Resource
    private KpiProjectTimeMapper kpiProjectTimeMapper;
    @Resource
    private KpiConsoleMapper kpiConsoleMapper;


    @Transactional(rollbackFor = Exception.class)
    public void saveProfileAndConsole(KpiProfile kpiProfile, KpiConsole kpiConsole) {

        kpiProfileMapper.insertSelective(kpiProfile);

        kpiConsole.setProfileId(kpiProfile.getId());
        kpiConsoleMapper.insertSelective(kpiConsole);
    }

    /**
     * 1.目标制定卡片 设置为显示出来
     * 2.project 从  1未确认 -> 2目标制定中
     * 3.增加实际完成时间卡位
     *
     * @param projectId          考核方案
     * @param kpiProjectTimeList 预设值时间
     * @author linhuid
     * @date 2019/7/2 22:36
     * @since v1.0.0
     */
    @Transactional(rollbackFor = Exception.class)
    public void confirmProject(Integer projectId, List<KpiProjectTime> kpiProjectTimeList) {

        int success = kpiProjectMapper.updateState(projectId, ProjectStatusEnum.INIT.getValue(), ProjectStatusEnum.TARGET_DRAFT.getValue());
        AssertUtil.isTrue(success == 1, BizExceptionEnum.SERVER_ERROR);

        kpiConsoleMapper.updateHideByProjectId(projectId, Boolean.FALSE);

        for (KpiProjectTime kpiProjectTime : kpiProjectTimeList) {
            kpiProjectTime.setId(null);
            kpiProjectTime.setSign(StringUtils.replace(kpiProjectTime.getSign(), "Pre", "Finish"));
            kpiProjectTime.setRemark(StringUtils.replace(kpiProjectTime.getRemark(), "预设", "实际").replace("开始", "完成"));
            kpiProjectTime.setSettingTime(null);
            kpiProjectTime.setType(ProjectTimeTypeEnum.FINISH.getValue());
        }
        kpiProjectTimeMapper.insertBatch(kpiProjectTimeList);
    }
}
