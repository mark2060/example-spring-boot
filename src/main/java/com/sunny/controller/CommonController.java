package com.sunny.controller;

import com.sunny.model.UserModel;
import com.sunny.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * UserController
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-08-04
 */
@Api(value = "交警业务接口类", tags = "交警6合1业务接口", description = "主要任务和交警的专网通信")
@RestController
public class CommonController {

    @Resource
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
    @ApiOperation(value = "检查用户账号接口", notes = "检查该用户是否在6合1系统中存在", produces = "application/json")
    @RequestMapping(value = "/guavaCache", method = RequestMethod.GET)
    public UserModel guavaCache(Long userId) {
        return userService.guavaCache(userId);
    }

    /**
     * 缓存使用
     */
    @ApiOperation(value = "检查用户账号接口2", notes = "检查该用户是否在6合1系统中存在", produces = "application/json")
    @RequestMapping(value = "/guavaCache2", method = RequestMethod.GET)
    public UserModel guavaCache2(UserModel userModel) {
        return userService.guavaCache(userModel.getId());
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

    @RequestMapping(value = "/transactional", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserModel mybatis(UserModel userModel) {
        Long id = userService.insert(userModel);

        UserModel result = new UserModel();
        result.setId(id);
        return result;
    }

}
