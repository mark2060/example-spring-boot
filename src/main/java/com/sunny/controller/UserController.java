package com.sunny.controller;

import com.sunny.model.UserModel;
import com.sunny.plugin.mail.MailPlugin;
import com.sunny.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * UserController
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-08-04
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommonsMultipartResolver commonsMultipartResolver;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private MailPlugin mailPlugin;

    @RequestMapping(value = "/user/select", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserModel select(Integer id) {

        return userService.selectById(id);
    }

}
