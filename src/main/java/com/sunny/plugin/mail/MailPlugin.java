package com.sunny.plugin.mail;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * MailPlugin
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-08-04
 */
public class MailPlugin {

    private JavaMailSenderImpl mailSender;

    /**
     * send html mail
     *
     * @param to              收件人
     * @param carbonCopy      抄送
     * @param blindCarbonCopy 密送
     * @throws javax.mail.MessagingException
     */
    public void send(String[] to, String[] carbonCopy, String[] blindCarbonCopy, String subject, String text) throws MessagingException {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
        messageHelper.setTo(to);
        if (carbonCopy != null && carbonCopy.length > 0) {
            messageHelper.setCc(carbonCopy);
        }
        if (blindCarbonCopy != null && blindCarbonCopy.length > 0) {
            messageHelper.setBcc(blindCarbonCopy);
        }
        messageHelper.setSubject(subject);
        messageHelper.setText(text, true);
        mailSender.send(mailMessage);
    }

    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

}
