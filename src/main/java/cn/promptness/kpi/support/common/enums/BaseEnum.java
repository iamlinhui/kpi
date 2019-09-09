package cn.promptness.kpi.support.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 枚举接口类，方便页面上显示相应枚举常量的名称
 *
 * @author Jaward
 * @date 2018-08-27 09:39:41
 * @since 1.2.0
 */
public interface BaseEnum {

	/**
	 * 得到枚举显示的名称
	 */
	String getName();

	/**
	 * 得到枚举对应的数值(一般用于数据库对应)
	 */
	Object getValue();

	@JsonValue
	default Map<String, Object> toMap() {
		ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
		if (StringUtils.isNotEmpty(getName())) {
			builder.put("name", getName());
		}
		if (null != getValue()) {
			builder.put("value", getValue());
		}
		return builder.build();
	}
}
