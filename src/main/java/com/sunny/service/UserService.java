package com.sunny.service;

import com.sunny.dao.UserDao;
import com.sunny.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * UserService
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-08-04
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    public UserModel selectById(Integer id) {
        UserModel result = new UserModel();

        result.setId(1);
        result.setUsername("tom");
        result.setPassword("jack");

        Long count = userDao.selectCount();
        System.out.println("count is " + count);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("executor run");
            }
        });

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("maxiaoshuai2588@126.com");
        message.setText("您好 by spring boot");
        javaMailSender.send(message);

        return result;
    }

}
