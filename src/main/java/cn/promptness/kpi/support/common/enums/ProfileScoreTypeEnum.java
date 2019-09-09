package cn.promptness.kpi.support.common.enums;

import cn.promptness.kpi.support.exception.BizExceptionEnum;
import lombok.Getter;

@Getter
public enum ProfileScoreTypeEnum implements BaseEnum {

    /**
     * 分类(0总评 1明细分评)
     */
    GENERAL_COMMENT("总评", 0),

    /**
     * GS指标
     */
    SUBDIVISION_COMMENT("明细分评", 1);

    private String name;

    private Integer value;

    ProfileScoreTypeEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static ProfilePointTypeEnum getInstance(Integer value) {

        for (ProfilePointTypeEnum e : ProfilePointTypeEnum.values()) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        throw BizExceptionEnum.SERVER_ERROR.pageException();
    }
}
