package cn.promptness.kpi.support.common.annotation;

import cn.promptness.kpi.support.config.HttpClientConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : Lynn
 * @date : 2019-04-18 21:08
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(HttpClientConfig.class)
public @interface EnableHttpClient {

}