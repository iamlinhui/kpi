package cn.promptness.kpi.support.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author linhuid
 * @date 2019/7/1 21:27
 * @since v1.0.0
 */
@ConfigurationProperties(prefix = "hr")
@Component
@Data
public class HrProperties {


    /**
     * 首页地址
     */
    private String dashboard;

    private String officeListApi;
    private String userListApi;
    private String userAuthApi;
    private String userInfoApi;
    private String userRoleApi;

    private List<String> hrManager;


}
