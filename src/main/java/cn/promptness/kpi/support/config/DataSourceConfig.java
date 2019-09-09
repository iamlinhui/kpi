package cn.promptness.kpi.support.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author linhuid
 * @date 2019/7/1 12:28
 * @since v1.0.0
 */
@Slf4j
@Configuration
public class DataSourceConfig {


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.workflow")
    public DataSource workflowDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid.kpi")
    public DataSource kpiDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

}
