package com.sunny.controller;

import com.sunny.model.UserModel;
import com.sunny.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 * UserController
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-08-04
 */
@RestController
public class CommonController {

    @Autowired
    private UserService userService;

    /**
     * 发送邮件
     */
    @RequestMapping(value = "/mailExample", method = RequestMethod.GET)
    public void mailExample() {
        userService.sendMail();
    }

    /**
     * 缓存使用
     */
    @RequestMapping(value = "/guavaCache", method = RequestMethod.GET)
    public UserModel guavaCache(Long userId) {
        return userService.guavaCache(userId);
    }

    /**
     * 使用注解缓存
     */
    @RequestMapping(value = "/cacheAnnotation", method = RequestMethod.GET)
    public UserModel cacheAnnotation(Long userId) {
        return userService.cacheAnnotation(userId, true);
    }

    /**
     * 使用注解缓存，使用开关控制缓存是否生效
     */
    @RequestMapping(value = "/cacheAnnotationWithSwitch", method = RequestMethod.GET)
    public UserModel cacheAnnotationWithSwitch(Long userId) {
        return userService.cacheAnnotation(userId, false);
    }

    /**
     * 使用redis缓存数据
     */
    @RequestMapping(value = "/redis", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserModel redis(Long userId) {
       return userService.selectUserFromRedis(userId);
    }

    /**
     * 使用mybatis注解
     *
     * @param userId 用户id
     * @return 结果
     */
    @RequestMapping(value = "/dao", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserModel dao(Long userId) {
        return userService.selectUser(userId);
    }

    /**
     * 使用mybatis mapper
     */
    @RequestMapping(value = "/mybatis", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserModel mybatis(Long userId) {
        return userService.selectUserById(userId);
    }

}
