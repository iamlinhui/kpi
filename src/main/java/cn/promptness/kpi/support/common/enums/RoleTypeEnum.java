package cn.promptness.kpi.support.common.enums;

import lombok.Getter;

/**
 * 角色枚举
 *
 * @author linhuid
 * @date 2019/7/1 21:20
 * @since v1.0.0
 */
@Getter
public enum RoleTypeEnum {

    /**
     *
     */
    BEFORE_ENTRY                       ("待入职员工"),
    IT_MAINTENANCE                     ("IT运维"),
    COMPANY_ADMINISTRATOR              ("公司管理员"),
    TRAINING_ADMINISTRATOR             ("培训管理员"),
    SYSTEM_ADMINISTRATOR               ("系统管理员"),
    SALARY_ADMINISTRATOR               ("薪酬管理员"),
    LEAVE_ADMINISTRATOR                ("请假审批人"),
    DEPARTMENT_ADMINISTRA              ("部门管理员"),
    HR                                 ("HR"),
    ORDINARY_USER                      ("普通用户"),
    PERFORMANCE_APPROVER               ("绩效审批人"),
    PERFORMANCE_MANAGER                ("绩效管理员");

    private String name;

    RoleTypeEnum(String name) {
        this.name = name;
    }
}
