package cn.promptness.kpi.domain.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class KpiProjectTime implements Serializable {
    private static final long serialVersionUID = -4213766342782771608L;
    /**
     * ID
     */
    private Integer id;

    /**
     * 标记（英文）
     */
    private String sign;

    /**
     * 备注（中文）
     */
    private String remark;

    /**
     * 设置时间
     */
    private Date settingTime;

    /**
     * 类型（0普通 1预设 2实际）
     */
    private Integer type;

    /**
     * 对应节点（自评self 组长leader 总监director 总经理manager 总裁ceo 面谈interview 结果result）
     */
    private String process;

    /**
     * 考核方案ID
     */
    private Integer projectId;


}