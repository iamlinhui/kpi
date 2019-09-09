package cn.promptness.kpi.support.common.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum ProcessJobEnum implements BaseEnum {

	/**
	 * 自评
	 */
	SELF("自评", "self"),

	/**
	 * 组长
	 */
	LEADER("组长", "leader"),

	/**
	 * 总经理/副总经理
	 */
	MANAGER("总经理/副总经理", "manager"),

	/**
	 * 总监
	 */
	DIRECTOR("总监", "director"),

	/**
	 * 副总裁
	 */
	VICE_CEO("副总裁", "vice-ceo"),

	/**
	 * 总裁
	 */
	CEO("总裁", "ceo"),

	/**
	 * 面谈
	 */
	INTERVIEW("面谈", "interview"),

	/**
	 * 公布结果
	 */
	RESULT("公布结果", "result");

	private String name;

	private String value;

	ProcessJobEnum(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public static ProcessJobEnum getInstance(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		for (ProcessJobEnum e : ProcessJobEnum.values()) {
			if (e.getValue().equals(value)) {
				return e;
			}
		}
		return null;
	}
}
