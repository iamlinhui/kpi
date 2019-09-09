package cn.promptness.kpi.domain.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class KpiProject implements Serializable {
    private static final long serialVersionUID = 6650065980360936114L;
    /**
     * ID
     */
    private Integer id;

    /**
     * 考核方案名称
     */
    private String name;

    /**
     * 考核周期开始时间
     */
    private Date startTime;

    /**
     * 考核周期结束时间
     */
    private Date endTime;

    /**
     * 催交时间方案(提前天数 逗号分隔)
     */
    private String remindTime;

    /**
     * 考核员工数
     */
    private Integer userCount;

    /**
     * 备注
     */
    private String remark;

    /**
     * -9已删除 0草稿 1未确认 2目标制定中 3目标制定结束 4分配考核组中 5分配人员结束 6系统分配流程中 7系统分配流程完成 8考核中   9已归档
     */
    private Integer status;

    /**
     * 是否启用
     */
    private Boolean isOpen;

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