package cn.promptness.kpi.domain.pojo;

import java.util.Date;

public class KpiProjectNotice {
    /**
     * 编号
     */
    private Integer id;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 提前天数
     */
    private Integer remindDay;

    /**
     * 备注
     */
    private String remark;

    /**
     * 考核方案时间点编号
     */
    private Integer projectTimeId;

}