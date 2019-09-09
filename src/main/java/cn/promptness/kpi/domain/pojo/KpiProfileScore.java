package cn.promptness.kpi.domain.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class KpiProfileScore implements Serializable {
    private static final long serialVersionUID = 8391420585888676220L;
    /**
     * ID
     */
    private Integer id;

    /**
     * 审批人员工ID
     */
    private String userId;


    /**
     * 为了连表查询加的字段
     */
    private String userName;

    /**
     * 审批人职级标签
     */
    private String job;

    /**
     * 点评
     */
    private String comment;

    /**
     * 评分
     */
    private Double score;

    /**
     * 分类(0总评 1明细分评)
     */
    private Integer category;

    /**
     * 考核表(profile_id)/明细ID(profile_point_id)
     */
    private Integer relationId;

    /**
     * 流程ID
     */
    private Integer flowId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;
}