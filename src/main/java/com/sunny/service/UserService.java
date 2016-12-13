package com.sunny.service;

import com.google.common.collect.Lists;
import com.sunny.dao.UserDao;
import com.sunny.mapper.UserMapper;
import com.sunny.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.SystemPropertyUtils;

import javax.annotation.Resource;
import java.io.Serializable;
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

    public List<String> guavaCache() {
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
     * 此处result为什么为null呢？
     * @param userId
     * @param useCache
     * @return
     */
    @Cacheable(value = "guavaCache", key = "'application_menu_list_key_' + #userId", condition = "#useCache and #result == null")
    public List<String> cacheAnnotation(Long userId,Boolean useCache) {
        List<String> menuList = Lists.newArrayList("新闻", "娱乐", "科技", "游戏");
        System.out.println("select menu from database.");
        return menuList;
    }

    /**
     * 查询用户，use spring cache annotation
     *
     * @param id id
     * @return 结果
     */
    @Cacheable(value = "guavaCache", key = "'_key' + #id")
    public UserModel selectUserFromCache(Long id) {
        return userDao.selectUserById(id);
    }

    public void testRedis(){
        System.out.println(get("abc"));

        set("abc","efg");

        System.out.println(get("abc"));
    }

    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }
    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * use mybatis annotation
     *
     * @param id id
     * @return 结果
     */
    public UserModel selectUserById(Long id) {
        UserModel model = new UserModel();
        model.setId(id);
        return userMapper.selectById(model);
    }

}
