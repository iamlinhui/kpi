package cn.promptness.kpi.domain.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 首页工作台卡片
 *
 * @author linhuid
 * @date 2019/7/1 18:41
 * @since v1.0.0
 */
@Data
public class KpiConsole implements Serializable {

    private static final long serialVersionUID = -2670513941764725817L;

    private Integer id;

    private Integer profileId;

    private Integer projectId;

    private Integer flowId;

    private Integer groupId;

    /**
     * 1绩效目标填写;2员工自评;3分组评分;4大组评分;5绩效面谈;6确认结果;7申述中;8处理申述
     */
    private Integer node;

    private String userId;

    private Integer finishCount;

    private Integer totalCount;

    private Date createTime;

    private Date deadLine;

    private Date finishTime;

    private String remark;

    private Boolean isHide;

    private Boolean isFinished;


}