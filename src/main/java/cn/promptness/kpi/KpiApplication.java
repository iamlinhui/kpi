package cn.promptness.kpi;

import cn.promptness.kpi.support.common.annotation.EnableHttpClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author linhuid
 * @date 2019/7/1 10:48
 * @since v1.0.0
 */
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(basePackages = "cn.promptness.kpi.dao")
@EnableHttpClient
public class KpiApplication {

    public static void main(String[] args) {
        SpringApplication.run(KpiApplication.class, args);
    }

}
