package cn.promptness.kpi.domain.vo.table;

import lombok.Data;

import java.util.List;

/**
 * 目标制定
 *
 * @author linhuid
 * @date 2019/7/2 23:04
 * @since v1.0.0
 */
@Data
public class TableMakeAimsRsp {
    /**
     * 编号
     */
    private Integer profileId;

    /**
     * 用户ID
     */
    private String userId;


    private List<KpiProfilePointVO> dataList;


    @Data
    public static class KpiProfilePointVO {


        private Integer pointId;


        /**
         * 指标类型（0 KPI  1 GS）
         */
        private Integer type;

        /**
         * 关键绩效指标
         */
        private String target;

        /**
         * 权重
         */
        private Integer power;

        /**
         * 目标值及说明
         */
        private String explain;

        /**
         * 数据来源
         */
        private String source;

        /**
         * 是否可以编辑（目标制定阶段）
         */
        private Boolean canEdit;

    }
}

