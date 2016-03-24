package com.sunny.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Test
 *
 * @author sunny
 * @version 1.0.0
 * @since 2016-03-24
 */
@Component
public class Mail {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("maxiaoshuai2588@126.com");
        message.setSubject("this is a mail from spring boot");
        message.setText("hello world");
        javaMailSender.send(message);
    }

}
