package cn.promptness.kpi.domain.vo.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 考核方案VO
 *
 * @author Jaward
 * @date 2018-08-23 10:28:01
 * @since 1.2.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ProjectRsp {

    /**
     * 编号
     */
    private Integer id;

    /**
     * 方案名称
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
     * 催交时间方案（提前天数）
     */
    private List<Integer> remindTime;

    /**
     * 考核员工数
     */
    private Integer userCount;

    /**
     * 考核组数
     */
    private Integer groupCount;

    /**
     * 备注（选填）
     */
    private String remark;

    /**
     * 状态（0草稿 -1未开始 1考核中 2已归档）
     */
    private Integer status;

    /**
     * 是否启用
     */
    private boolean open;

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

    /**
     * 各个时间节点
     */
    private List<ProjectTimeRsp> timeList;

    /**
     * 是否设置考核组
     */
    private boolean settingGroup;


    /**
     * 考核方案时间点VO
     *
     * @author Jaward
     * @date 2018-08-23 10:28:01
     * @since 1.2.0
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class ProjectTimeRsp {

        /**
         * 编号
         */
        private Integer id;

        /**
         * 英文标记
         */
        private String sign;

        /**
         * 中文备注
         */
        private String remark;

        /**
         * 设置时间
         */
        private Date settingTime;

        /**
         * 类型
         */
        private Integer type;

        /**
         * 阶段
         */
        @JsonIgnore
        private Integer process;

        /**
         * 方案里是否有设置该角色 是则需要 不是则不需要
         */
        private Boolean isNeed = true;
    }
}
