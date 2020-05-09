package com.banmingi.communityplus.smscenter.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * @auther 半命i 2020/5/9
 * @description
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SMSListener {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String username;

    @StreamListener(Sink.INPUT)
    public void sendCheckCode(Map<String,String> msg) {
        String accountId = msg.get("accountId");
        String checkCode = msg.get("checkCode");
        log.info("账号：{}",accountId);
        log.info("验证码：{}",checkCode);
        try {
            //发邮件
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            // 第二个参数true表示使用HTML语言来编写邮件
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(username);//收件人邮件地址(上面获取到的，也可以直接填写,string类型)
            helper.setTo(accountId); //收件人邮件地址
            helper.setSubject("注册验证码"); //邮件主题

            //使用模板thymeleaf
            //Context是导这个包import org.thymeleaf.context.Context;
            Context context = new Context();
            //设置传入模板的页面的参数 参数名为:checkCode 参数随便写就行
            context.setVariable("checkCode", checkCode);
            //emailTemplate是你要发送的模板我这里用的是Thymeleaf
            String emailContent = templateEngine.process("checkCode", context);
            //邮件正文
            helper.setText(emailContent, true);
            //发送
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error("验证码发送失败：{}",e.getMessage());
        }
    }

}
