package cn.promptness.kpi.domain.vo.console;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 考核卡片请求参数
 *
 * @author linhuid
 * @date 2019/7/1 19:32
 * @since v1.0.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsoleRsp {


    /**
     * 方案名称
     */
    private String projectName;

    /**
     * 考核方案
     */
    private Integer projectId;


    private List<ConsoleVO> dataList;


    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ConsoleVO {

        /**
         * console_id
         */
        private Integer consoleId;

        /**
         * 卡片类型
         */
        private Integer type;

        /**
         * 当前节点
         */
        private String node;

        private Date deadline;

        private Date finishTime;

        /**
         * 是否已结束，0.未结束 1.已结束
         */
        private Boolean isEnd;


        /**
         * 审批类型（0评总分 1评明细）
         */
        private Integer assessType;


        private String interviewName;


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
         *
         */
        private Integer groupId;


        /**
         * 备注
         */
        private String remark;


        /**
         * 已完成数
         */
        private Integer finishNum;


        private Date createTime;


        /**
         * 考核结果
         */
        private String result;

    }


}
