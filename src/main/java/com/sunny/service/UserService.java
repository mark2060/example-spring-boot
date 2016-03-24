package com.sunny.service;

import com.google.common.collect.Lists;
import com.sunny.dao.UserDao;
import com.sunny.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private GuavaCacheManager guavaCacheManager;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    /**
     * 发送邮件
     */
    public void sendMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("maxiaoshuai2588@126.com");
        message.setSubject("this is a mail from spring boot");
        message.setText("hello world");
        javaMailSender.send(message);
    }

    /**
     * 查询菜单，use guava cache
     *
     * @return 结果
     */
    public List<String> selectMenu() {
        Cache cache = guavaCacheManager.getCache("guavaCache");
        List<String> menuList = cache.get("menu", List.class);
        if (menuList == null) {
            System.out.println("select menu from database");
            menuList = Lists.newArrayList("新闻", "娱乐", "科技", "游戏");
            cache.put("menu", menuList);
        } else {
            System.out.println("select menu from cache");
        }
        return menuList;
    }

    /**
     * 查询用户，use spring cache annotation
     *
     * @param id id
     * @return 结果
     */
    @Cacheable(value = "guavaCache", key = "'_key' + #id")
    public UserModel selectById(Long id) {
        UserModel result = new UserModel();

        result.setId(id);
        result.setUsername("tom");
        result.setPassword("jack");

        Long count = userDao.selectCount();
        System.out.println("count is " + count);
        return result;
    }

}
