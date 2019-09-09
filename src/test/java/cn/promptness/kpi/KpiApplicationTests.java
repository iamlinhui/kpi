package cn.promptness.kpi;

import cn.promptness.kpi.service.rpc.SendMailRpc;
import cn.promptness.kpi.support.config.properties.EmailProperties;
import cn.promptness.kpi.support.config.properties.HrProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KpiApplicationTests {

    @Autowired
    private SpringTemplateEngine springTemplateEngine;
    @Autowired
    private SendMailRpc mailSender;

    @Test
    public void contextLoads() {

        Context context = new Context();
        context.setVariable("userName", "李彦宏");
        context.setVariable("message", "....");
        context.setVariable("content", "葵花妈妈开课了");
        context.setVariable("dashboard", "https://baidu.com");

        String process = springTemplateEngine.process("email", context);

        mailSender.sendMail("","开课了",process);

    }

    @Autowired
    private EmailProperties emailProperties;
    @Autowired
    private HrProperties hrProperties;

    @Test
    public void resource() {

        System.out.println(hrProperties);
    }

}
