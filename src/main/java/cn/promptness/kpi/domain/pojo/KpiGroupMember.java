package cn.promptness.kpi.domain.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class KpiGroupMember implements Serializable {
    private static final long serialVersionUID = -7062199341304638022L;
    /**
     * ID
     */
    private Integer id;

    /**
     * 员工ID
     */
    private String userId;

    /**
     * 员工名称
     */
    private String userName;

    /**
     * 归属部门ID
     */
    private String officeId;

    /**
     * 归属部门名称
     */
    private String officeName;

    /**
     * 岗位名称
     */
    private String jobName;

    /**
     * 审批经理ID
     */
    private String leaderId;

    /**
     * 审批经理名称
     */
    private String leaderName;

    /**
     * 入职日期
     */
    private Date companyDate;

    /**
     * 是否在职
     */
    private Boolean isService;

    /**
     * 入职满3个月
     */
    private Boolean isServicePredict;

    /**
     * 是否薪资转正
     */
    private Boolean isSalaryPredict;

    /**
     * 考核组ID
     */
    private Integer groupId;

    /**
     * 考核表ID
     */
    private Integer profileId;
}