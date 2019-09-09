package cn.promptness.kpi.support.common.enums;

import cn.promptness.kpi.support.exception.BizExceptionEnum;
import lombok.Getter;

/**
 * 方案时间节点标记
 *
 * @author Jaward
 * @date 2018-09-11 11:38:06
 * @since 1.2.0
 */
@Getter
public enum ProjectTimeSignEnum implements BaseEnum {

    /**
     * explain
     */
    SELF_START_TIME("自评开始时间", "selfStartTime"),
    SELF_PRE_TIME("预设自评完成时间", "selfPreTime"),
    LEADER_PRE_TIME("预设组长审批完成时间", "leaderPreTime"),
    MANAGER_PRE_TIME("预设总经理/副总经理审批完成时间", "managerPreTime"),
    DIRECTOR_PRE_TIME("预设总监审批完成时间", "directorPreTime"),
    VICE_CEO_PRE_TIME("预设副总裁审批完成时间", "viceCeoPreTime"),
    CEO_PRE_TIME("预设总裁审批完成时间", "ceoPreTime"),
    INTERVIEW_PRE_TIME("预设绩效面谈完成时间", "interviewPreTime"),
    RESULT_PRE_TIME("预设考核结果确认完成时间", "resultPreTime"),
    ;

    private String name;

    private String value;

    ProjectTimeSignEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static ProjectTimeSignEnum getInstance(String value) {
        for (ProjectTimeSignEnum e : ProjectTimeSignEnum.values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        throw BizExceptionEnum.SERVER_ERROR.pageException();
    }
}
