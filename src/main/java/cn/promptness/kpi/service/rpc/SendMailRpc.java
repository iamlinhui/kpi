package cn.promptness.kpi.service.rpc;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

/**
 * @author : Lynn
 * @date : 2019-05-04 01:33
 */
@Component
@Slf4j
public class SendMailRpc {

    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     *
     * @param email   收件人
     * @param subject   主题
     * @param context   内容
     * @author linhuid
     * @date 2019-05-26 19:18
     * @since v1.0.0
     */
    public void sendMail(String email, String subject, String context) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(email);
            // 启用HTML
            mimeMessageHelper.setText(context, Boolean.TRUE);
            // 发送邮件
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.warn("发送邮件至{}失败!", email);
        }

    }
}
