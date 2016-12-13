package com.sunny.controller;

import com.sunny.model.UserModel;
import com.sunny.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
     *
     * @return 结果
     */
    @RequestMapping(value = "/guavaCache", method = RequestMethod.GET)
    public List<String> guavaCache() {
        return userService.guavaCache();
    }

    /**
     * 使用注解缓存
     *
     * @return 结果
     */
    @RequestMapping(value = "/cacheAnnotation", method = RequestMethod.GET)
    public List<String> cacheAnnotation(Long userId) {
        return userService.cacheAnnotation(userId, true);
    }

    @RequestMapping(value = "/cacheAnnotationf", method = RequestMethod.GET)
    public List<String> cacheAnnotationf(Long userId) {
        return userService.cacheAnnotation(userId, false);
    }

    /**
     * 查询用户
     * @return 结果
     */
    @RequestMapping(value = "/redis", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void redis() {
        userService.testRedis();
    }

    /**
     * 查询用户
     *
     * @param id id
     * @return 结果
     */
    @RequestMapping(value = "/selectUserFromCache", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserModel selectUserFromCache(Long id) {
        return userService.selectUserFromCache(id);
    }

    /**
     * 查询用户数，use mybatis annotation
     *
     * @return 数量
     */
    @RequestMapping(value = "/selectUserById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserModel selectUserById(Long id) {
        return userService.selectUserById(id);
    }

}
