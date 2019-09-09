package cn.promptness.kpi.support.listener;


import cn.promptness.kpi.dao.KpiProfileScoreMapper;
import cn.promptness.kpi.support.listener.event.UpdateProfileEvent;
import cn.promptness.kpi.domain.pojo.KpiConsole;
import cn.promptness.kpi.domain.pojo.KpiProfileScore;
import cn.promptness.kpi.support.common.enums.ProfileScoreTypeEnum;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class UpdateProfileEventListener {


    @Resource
    private KpiProfileScoreMapper kpiProfileScoreMapper;


    @EventListener
    public void updateProfile(UpdateProfileEvent updateProfileEvent){

        KpiConsole kpiConsole = (KpiConsole) updateProfileEvent.getSource();

        // 结束的流程
        Integer flowId = kpiConsole.getFlowId();

        List<KpiProfileScore> kpiProfileScoreList = kpiProfileScoreMapper.listByFlowIdAndScoreType(flowId, ProfileScoreTypeEnum.GENERAL_COMMENT.getValue());



    }

}
