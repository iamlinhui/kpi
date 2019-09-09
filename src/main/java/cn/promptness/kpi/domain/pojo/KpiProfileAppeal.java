package cn.promptness.kpi.domain.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class KpiProfileAppeal implements Serializable {

    private static final long serialVersionUID = 6344278294332831122L;

    private Long id;

    /**
     * 申述人
     */
    private String userId;

    /**
     * 季度考核id
     */
    private Long projectId;

    /**
     * 成绩表id
     */
    private Long profileId;

    /**
     * 当前申述次数
     */
    private Integer currentCount;

    /**
     * 申述前的成绩
     */
    private Double beforeState;

    /**
     * 申述后的成绩
     */
    private Double afterState;

    /**
     * 申述的状态(1：未处理，2：已处理，3：已撤销)
     */
    private Integer status;

    /**
     * 员工是否确认(0未确认/1已确认)
     */
    private Boolean isConfirm;

    /**
     * 处理人
     */
    private String handleUserId;

    /**
     * 处理结果类型(0驳回/1修改)
     */
    private Boolean resultType;

    /**
     * 申述时间
     */
    private Date stateTime;

    /**
     * 处理时间
     */
    private Date handleTime;

    /**
     * 处理留言
     */
    private String handleReason;

    /**
     * 申述理由
     */
    private String stateReason;

    /**
     * 处理人姓名
     */
    private String handleUserName;

}