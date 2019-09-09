package cn.promptness.kpi.support.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import cn.promptness.kpi.support.exception.BizExceptionEnum;
import lombok.Getter;

import java.util.Map;

/**
 * @author Lynn
 * @date 2018/9/6 11:10
 */
@Getter
public enum ConsoleTypeEnum implements BaseEnum {

    /**
     * 说明 1绩效目标填写;2员工自评;3分组评分;4大组评分;5绩效面谈;6确认结果;7申述中;8处理申述
     */
    MAKE_AIMS       ("目标填写",  1,  ProcessTagEnum.FILL_PERFORMANCE_TARGETS),
    SELF            ("员工自评",  2,  ProcessTagEnum.SELF_EVALUATION),
    SUB_GROUP_SCORE ("分组评分",  3,  ProcessTagEnum.SUB_GROUP_SCORE),
    GROUP_SCORE     ("大组评分",  4,  ProcessTagEnum.GROUP_SCORE),
    INTERVIEW       ("绩效面谈",  5,  ProcessTagEnum.INTERVIEW),
    CONFIRM         ("结果确认",  6,  ProcessTagEnum.INTERVIEW),
    APPEAL          ("结果申诉",  7,  ProcessTagEnum.INTERVIEW),
    HANDLE_APPEAL   ("申诉处理",  8,  ProcessTagEnum.APPEAL);

    String name;
    Integer value;
    ProcessTagEnum processTagEnum;

    ConsoleTypeEnum(String name, Integer value, ProcessTagEnum processTagEnum) {
        this.name = name;
        this.value = value;
        this.processTagEnum = processTagEnum;
    }

    public static ConsoleTypeEnum getInstance(Integer code) {

        for (ConsoleTypeEnum consoleTypeEnum : ConsoleTypeEnum.values()) {
            if (consoleTypeEnum.getValue().equals(code)) {
                return consoleTypeEnum;
            }
        }
        throw BizExceptionEnum.SERVER_ERROR.pageException();
    }


    @JsonValue
    @Override
    public Map<String, Object> toMap() {
        return ImmutableMap.<String, Object>builder()
                .put("name", getName())
                .put("value", getValue())
                .put("processTag", getProcessTagEnum())
                .build();
    }
}