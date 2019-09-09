package cn.promptness.kpi.domain.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 考核组 (大组 / 小组)
 *
 * @author linhuid
 * @date 2019/7/1 18:44
 * @since v1.0.0
 */
@Data
public class KpiGroup implements Serializable {

    private static final long serialVersionUID = -2682328217912033207L;
    /**
     * ID
     */
    private Integer id;

    /**
     * 上一级ID
     */
    private Integer parentId;

    /**
     * 流程实例ID
     */
    private Long processInstanceId;

    /**
     * 考核组名称
     */
    private String name;

    /**
     * 考核方式(1限制KPI占比/自评+打分 2不限KPI占比/上级直接打分 3自评+打分)
     */
    private Integer type;

    /**
     * 类型(0独立 1非独立)
     */
    private Integer category;

    /**
     * KPI占比
     */
    private Integer kpiPercent;

    /**
     * KPI+GS条数上限
     */
    private Integer pointCeil;

    /**
     * KPI+GS条数下限
     */
    private Integer pointFloor;

    /**
     * 绩效面谈人ID
     */
    private String interviewerId;

    /**
     * 绩效面谈人名称
     */
    private String interviewerName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 部门ID
     */
    private String officeId;

    /**
     * 部门名称
     */
    private String officeName;

    /**
     * 考核方案ID
     */
    private Integer projectId;

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

}