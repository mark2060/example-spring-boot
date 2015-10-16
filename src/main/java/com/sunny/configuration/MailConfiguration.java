package com.sunny.configuration;

import com.sunny.plugin.mail.MailPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * 邮件发送配置
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-08-04
 */
@Configuration
public class MailConfiguration implements EnvironmentAware {

    private static Logger logger = LoggerFactory.getLogger(MailConfiguration.class);

    private RelaxedPropertyResolver propertyResolver;

    private Environment environment;

    /**
     * 创建发送邮件插件
     *
     * @return 结果
     */
    @Bean(name = "mailPlugin")
    public MailPlugin mail(JavaMailSenderImpl mailSender) {
        MailPlugin mail = new MailPlugin();
        mail.setMailSender(mailSender);
        return mail;
    }

    @Bean(name = "mailSender")
    public JavaMailSenderImpl javaMailSenderImpl() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(propertyResolver.getRequiredProperty("host"));
        mailSender.setPort(propertyResolver.getRequiredProperty("port", Integer.class));
        mailSender.setProtocol(propertyResolver.getProperty("protocol"));
        mailSender.setDefaultEncoding(propertyResolver.getProperty("defaultEncoding"));
        mailSender.setUsername(propertyResolver.getProperty("username"));
        mailSender.setPassword(propertyResolver.getProperty("password"));

        //set property
        Properties javaMailProperties = new Properties();
        javaMailProperties.setProperty("mail.smtp.from", propertyResolver.getRequiredProperty("from"));
        javaMailProperties.setProperty("mail.smtp.auth", "true");
        javaMailProperties.setProperty("mail.smtp.timeout", "20000");
        javaMailProperties.setProperty("mail.smtp.port", "465");
        javaMailProperties.setProperty("mail.smtp.socketFactory.port", "465");
        javaMailProperties.setProperty("mail.smtp.socketFactory.fallback", "false");
        javaMailProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        this.propertyResolver = new RelaxedPropertyResolver(environment, "mail.");
    }

}
