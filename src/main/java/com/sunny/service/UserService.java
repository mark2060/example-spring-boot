package com.sunny.service;

import com.sunny.constant.Constant;
import com.sunny.dao.UserDao;
import com.sunny.mapper.UserMapper;
import com.sunny.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

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
    private UserMapper userMapper;

    @Autowired
    private UserDao userDao;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private GuavaCacheManager guavaCacheManager;

    @Autowired
    private RedisTemplate redisTemplate;

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

    public UserModel guavaCache(Long userId) {
        Cache cache = guavaCacheManager.getCache("guavaCache");
        String cacheKey = "application_user_" + userId;
        UserModel userModel = cache.get(cacheKey, UserModel.class);
        if (userModel == null) {
            System.out.println("select user from database");
            userModel = new UserModel();
            userModel.setId(userId);
            userModel.setUsername("jobs");
            cache.put(cacheKey, userModel);
        } else {
            System.out.println("select user from cache");
        }
        return userModel;
    }

    /**
     * 此处result为什么为null呢？
     *
     * @param userId   用户id
     * @param useCache 是否使用缓存
     * @return 结果
     */
    @Cacheable(value = "guavaCache", key = "'application_user_' + #userId", condition = "#useCache and #result == null")
    public UserModel cacheAnnotation(Long userId, Boolean useCache) {
        UserModel userModel = new UserModel();
        userModel.setId(userId);
        userModel.setUsername("jobs");
        System.out.println("select from database.");
        return userModel;
    }

    /**
     * 查询用户，use spring cache annotation
     *
     * @param userId 用户ID
     * @return 结果
     */
    public UserModel selectUser(Long userId) {
        return userDao.selectUserById(userId);
    }

    public UserModel selectUserFromRedis(Long userId) {
        String cacheKey = "application_user_" + userId;

        ValueOperations<String, UserModel> operations = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(cacheKey)) {
            redisTemplate.expire(cacheKey,Constant.ONE_HOUR_MILLISECONDS, TimeUnit.MILLISECONDS);
            return operations.get(cacheKey);
        } else {
            UserModel userModel = userDao.selectUserById(userId);
            operations.set(cacheKey, userModel, Constant.ONE_HOUR_MILLISECONDS, TimeUnit.MILLISECONDS);
            return userModel;
        }
    }

    /**
     * 使用mybatis
     */
    public UserModel selectUserById(Long id) {
        UserModel model = new UserModel();
        model.setId(id);
        return userMapper.selectById(model);
    }

}
