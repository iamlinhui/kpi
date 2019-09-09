package cn.promptness.kpi.domain.vo.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author linhuid
 * @date 2019/7/2 23:49
 * @since v1.0.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableGroupRsp {


    private Integer flowId;


    /**
     * 方案名称
     */
    private String projectName;

    private Integer projectId;

    /**
     * 考核组名称
     */
    private String groupName;

    /**
     * 分组名称
     */
    private String subGroupName;

    /**
     * 当前考核组类型
     * "独立考核组", 0
     * "非独立考核组", 1
     */
    private Integer groupType;


    /**
     * 已完成数
     */
    private Integer finishNum;

    /**
     * 要求分数分布，{A:3， B:2}
     */
    private Map distributed;

    /**
     * 当前分布，{A:2, B:1}
     */
    private Map currentDistributed;


    private Integer groupId;


    /**
     * 审批类型(1评明细/0评总分)
     */
    private Integer scoreType;

    /**
     * 是否评分约束(1强制分布评分 0非强制分布评分)
     *
     */
    private Integer limitType;


    private List<GroupUser> dataList;


    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GroupUser {

        private String userId;

        private String name;

        /**
         * 部门
         */
        private String department;

        /**
         * 职位
         */
        private String post;

        /**
         * 自评分数
         */
        private Double score;


        /**
         * 排名
         */
        @JsonIgnore
        private Double rankingScore;


        /**
         * 上一季度的考核
         */
        private String previousQuarterResult;


        private List<GroupScore> dataList;


    }


    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GroupScore {

        private Integer flowId;

        /**
         * 评分人userId
         */
        private String userId;
        /**
         * 评分人姓名，刘俊
         */
        private String name;
        /**
         * 总评价，很出色的完成XXX工作
         */
        private String assess;
        /**
         * 总分数
         */
        private Double score;
        /**
         * 评价人是否是当前评分人，1是，0不是
         */
        private Boolean current;

        /**
         * 0总评 1明细总评
         */
        private Integer category;


        private Integer groupId;

        /**
         * 完成时间
         */
        private Date finishTime;

        /**
         * 1分组 0大组
         */
        private Boolean isSubGroup;

        /**
         * 是否是分组最后一个审批人 0 不是 , 1 是的
         */
        private Boolean isSubFlowLast;

    }


}
