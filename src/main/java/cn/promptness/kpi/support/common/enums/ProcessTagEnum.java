package cn.promptness.kpi.support.common.enums;

import lombok.Getter;

/**
 * @author Lynn
 * @date 2018/9/4 9:03
 */
@Getter
public enum ProcessTagEnum implements BaseEnum {

    /**
     *
     */
    FILL_PERFORMANCE_TARGETS("绩效目标填写"),

    SELF_EVALUATION("员工自评"),

    SUB_GROUP_SCORE("分组评分"),

    GROUP_SCORE("考核组评分"),

    INTERVIEW("结果确认"),

    APPEAL("绩效结果申诉处理");

    /**
     * 任务名称
     */
    private String name;

    private String value;

    ProcessTagEnum(String name) {
        this.name = name;
    }


    public static String getNameListString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ProcessTagEnum e : ProcessTagEnum.values()) {
            stringBuilder.append(",").append("'").append(e.getName()).append("'");
        }
        return stringBuilder.substring(1);
    }
}
