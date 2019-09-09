package cn.promptness.kpi.support.common.enums;

import cn.promptness.kpi.support.exception.BizExceptionEnum;
import lombok.Getter;

@Getter
public enum GroupCategoryEnum implements BaseEnum {

    /**
     * 独立考核组
     */
    INDEPENDENT("独立考核组", 0),

    /**
     * 非独立考核组
     */
    DEPENDENT("非独立考核组", 1),


    /**
     * 非独立考核分组
     */
    DEPENDENT_SUB("非独立考核组分组", 2);

    private String name;

    private Integer value;

    GroupCategoryEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static GroupCategoryEnum getInstance(Integer value) {
        for (GroupCategoryEnum e : GroupCategoryEnum.values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        throw BizExceptionEnum.SERVER_ERROR.pageException();
    }
}
