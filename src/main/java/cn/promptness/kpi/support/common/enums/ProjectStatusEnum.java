package cn.promptness.kpi.support.common.enums;

import cn.promptness.kpi.support.exception.BizExceptionEnum;
import lombok.Getter;

@Getter
public enum ProjectStatusEnum implements BaseEnum {

    /**
     * -9已删除 0草稿 1未确认 2目标制定中 3目标制定结束 4分配考核组中 5分配人员结束 6系统分配流程中 7系统分配流程完成 8考核中  9已归档
     */
    DELETED             ("已删除",       -9),
    DRAFT               ("草稿",          0),
    INIT                ("未确认",        1),
    TARGET_DRAFT        ("目标制定中",     2),
    TARGET_FINISH       ("目标制定结束",   3),
    GROUP_DISTRIBUTING  ("分配考核组中",   4),
    GROUP_DISTRIBUTED   ("分配人员结束",   5),
    AUTO_DISTRIBUTING   ("系统分配流程中", 6),
    AUTO_DISTRIBUTED    ("系统分配流程完成",7),
    EXAMINING           ("考核中",         8),
    ARCHIVE             ("已归档",         9);

    private String name;

    private Integer value;

    ProjectStatusEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static ProjectStatusEnum getInstance(Integer value) {
        for (ProjectStatusEnum e : ProjectStatusEnum.values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        throw BizExceptionEnum.SERVER_ERROR.pageException();
    }
}
