package cn.promptness.kpi.support.common.enums;

import cn.promptness.kpi.support.exception.BizExceptionEnum;
import lombok.Getter;

@Getter
public enum ProjectTimeTypeEnum implements BaseEnum {

    /**
     * 普通时间节点
     */
    NORMAL("普通时间节点", 0),

    /**
     * 预设时间节点
     */
    PRE("预设时间节点", 1),

    /**
     * 实际时间节点
     */
    FINISH("实际时间节点", 2);

    private String name;

    private Integer value;

    ProjectTimeTypeEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static ProjectTimeTypeEnum getInstance(Integer value) {
        for (ProjectTimeTypeEnum e : ProjectTimeTypeEnum.values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        throw BizExceptionEnum.SERVER_ERROR.pageException();
    }
}
