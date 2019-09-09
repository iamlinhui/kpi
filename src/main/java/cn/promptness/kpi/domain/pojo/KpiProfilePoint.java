package cn.promptness.kpi.domain.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class KpiProfilePoint implements Serializable {
    private static final long serialVersionUID = 4829866191121857387L;
    /**
     * ID
     */
    private Integer id;

    /**
     * 指标类型(0 KPI  1 GS)
     */
    private Integer type;

    /**
     * 权重
     */
    private Integer power;

    /**
     * 数据来源
     */
    private String source;

    /**
     * 完成情况自述
     */
    private String statement;

    /**
     * 自评评分
     */
    private Double score;

    /**
     * 目标制定能否被编辑（1能 0不能）
     */
    private Boolean canEdit;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 考核表ID
     */
    private Integer profileId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 关键绩效指标
     */
    private String target;

    /**
     * 目标值及说明
     */
    private String explain;
}