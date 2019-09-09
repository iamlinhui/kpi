package cn.promptness.kpi.support.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author linhuid
 * @date 2019/7/1 15:34
 * @since v1.0.0
 */
@ConfigurationProperties(prefix = "email.template")
@Component
@PropertySource("classpath:/email.properties")
@Data
public class EmailProperties {

    private String target;
    private String self;
    private String selfSysRemind;
    private String selfOpRemind;
    private String group;
    private String groupSysRemind;
    private String groupOpRemind;
    private String interview;
    private String interviewSysRemind;

    private String interviewOpRemind;
    private String result;
    private String resultSysRemind;
    private String resultOpRemind;
    private String appealApply;
    private String appealResult;
    private String feedback;

}
