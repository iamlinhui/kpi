package cn.promptness.kpi.support.common.enums;

import cn.promptness.kpi.support.exception.BizExceptionEnum;
import lombok.Getter;

@Getter
public enum GroupTypeEnum implements BaseEnum {

    /**
     * 限制KPI占比/自评+打分
     */
    LIMIT_ALL("自评+打分，限制KPI占比", 1),

    /**
     * 不限KPI占比/上级直接打分
     */
    UN_LIMIT_HALF("上级直接打分，不限KPI占比", 2),

    /**
     * 自评+打分
     */
    UN_LIMIT_ALL("自评+打分", 3);

    private String name;

    private Integer value;

    GroupTypeEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static GroupTypeEnum getInstance(Integer value) {
        for (GroupTypeEnum e : GroupTypeEnum.values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        throw BizExceptionEnum.SERVER_ERROR.pageException();
    }
}
