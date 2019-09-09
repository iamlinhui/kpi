package cn.promptness.kpi.domain.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class KpiGroupFlow implements Serializable {

    private static final long serialVersionUID = -4179049759200787824L;
    /**
     * ID
     */
    private Integer id;

    /**
     * 审批者ID
     */
    private String userId;

    /**
     * 审批者名称
     */
    private String userName;

    /**
     * 审批类型(1评明细/0评总分)
     */
    private Integer type;

    /**
     * 职级标签（如组长leader）
     */
    private String job;

    /**
     * 审批级别(第一/二/三级)
     */
    private Integer level;

    /**
     * 是否评分约束(1强制分布评分 0非强制分布评分)
     */
    private Integer isLimit;

    /**
     * 考核组ID
     */
    private Integer groupId;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改者
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 是否对上游(前面的审批人)隐藏
     */
    private Boolean isHide;


    private Integer projectId;


    private Boolean isFinished;


}