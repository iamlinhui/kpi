package cn.promptness.kpi.domain.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 每季度考核单
 *
 * @author linhuid
 * @date 2019/7/1 18:57
 * @since v1.0.0
 */
@Data
public class KpiProfile implements Serializable {

    private static final long serialVersionUID = 467123452339522651L;
    /**
     * ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 自评分数
     */
    private Double selfScore;

    /**
     * 自评结果(A/B/C/D/E)
     */
    private String selfResult;

    /**
     * 考核分数
     */
    private Double examineScore;

    /**
     * 考核结果(A/B/C/D/E)
     */
    private String examineResult;

    /**
     * 最终分数
     */
    private Double finalScore;

    /**
     * 最终结果(A/B/C/D/E)
     */
    private String finalResult;

    /**
     * 状态(0初始化;1目标制定草稿;2目标制定完成;3自评草稿;4自评完成;5考核中;6结果待确认;7申诉中;8申述待确认;9已确认结果)
     */
    private Integer states;

    /**
     * 是否已经面谈(0 没有/1已经面谈)
     */
    private Boolean interview;

    /**
     * 考核方案ID
     */
    private Integer projectId;

    /**
     * 目标制定时间
     */
    private Date targetTime;

    /**
     * 自评时间
     */
    private Date selfTime;

    /**
     * 确认时间
     */
    private Date confirmTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 自评综述(800字)
     */
    private String selfSummary;

}