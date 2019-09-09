package cn.promptness.kpi.dao;

import cn.promptness.kpi.domain.pojo.KpiProfilePoint;

import java.util.List;

public interface KpiProfilePointMapper {

    List<KpiProfilePoint> listProfilePointByProfileId(Integer profileId);
}