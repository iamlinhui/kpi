package cn.promptness.kpi.support.common.enums;

import cn.promptness.kpi.support.exception.BizExceptionEnum;
import lombok.Getter;

@Getter
public enum ProfilePointTypeEnum implements BaseEnum {

	/**
	 * KPI指标
	 */
	TARGET_DRAFT("KPI", 0),

	/**
	 * GS指标
	 */
	TARGET_FINISH("GS", 1);

	private String name;

	private Integer value;

	ProfilePointTypeEnum(String name, Integer value) {
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
