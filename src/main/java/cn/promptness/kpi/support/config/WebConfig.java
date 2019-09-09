package cn.promptness.kpi.support.config;

import cn.promptness.kpi.domain.vo.HttpResult;
import cn.promptness.kpi.support.exception.BusinessException;
import cn.promptness.kpi.support.exception.BusinessExceptionResolver;
import cn.promptness.kpi.support.interceptor.SessionBondingInterceptor;
import cn.promptness.kpi.support.interceptor.UserBondingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Web Config
 *
 * @author Lynn
 */
@Configuration
@RestControllerAdvice
public class WebConfig extends WebMvcConfigurerAdapter {

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(Throwable.class)
    public HttpResult error(Throwable e) {
        e.printStackTrace();
        if (e instanceof BusinessException) {
            throw (BusinessException) e;
        }
        return HttpResult.getErrorHttpResult(e.getMessage());
    }

    @Bean
    public SessionBondingInterceptor sessionBondingInterceptor() {
        return new SessionBondingInterceptor();
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(new BusinessExceptionResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionBondingInterceptor())
                .addPathPatterns("/project/**")
                .addPathPatterns("/profile/**")
                .addPathPatterns("/group/**")
                .addPathPatterns("/examine/**")
                .addPathPatterns("/activity/**")
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/activity/deploy/**")
                .excludePathPatterns("/static/**");

        registry.addInterceptor(new UserBondingInterceptor())
                .addPathPatterns("/project/**")
                .addPathPatterns("/profile/**")
                .addPathPatterns("/group/**")
                .addPathPatterns("/examine/**")
                .addPathPatterns("/activity/**")
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/activity/deploy/**")
                .excludePathPatterns("/static/**");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        builder.indentOutput(Boolean.TRUE).dateFormat(dateFormat);
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));

        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

}
