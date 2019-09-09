package cn.promptness.kpi.support.common.enums;

import cn.promptness.kpi.support.exception.BizExceptionEnum;
import lombok.Getter;

@Getter
public enum GroupFlowTypeEnum implements BaseEnum {

    /**
     * 评总分
     */
    TOTAL("评总分", 0),

    /**
     * 评明细
     */
    DETAIL("评明细", 1);

    private String name;

    private Integer value;

    GroupFlowTypeEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static GroupFlowTypeEnum getInstance(Integer value) {
        for (GroupFlowTypeEnum e : GroupFlowTypeEnum.values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        throw BizExceptionEnum.SERVER_ERROR.pageException();
    }
}
