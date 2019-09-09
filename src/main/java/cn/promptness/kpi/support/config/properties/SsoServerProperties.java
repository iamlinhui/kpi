package cn.promptness.kpi.support.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * yjs-sso-server配置信息
 *
 * @author pengruib
 * @date 2018/9/25
 * @since 1.0.1
 */
@Component
@Data
@ConfigurationProperties(prefix = "sso.server")
public class SsoServerProperties {

    private String schema = "http";

    private String host;

    private int port = 80;

}
