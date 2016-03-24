package com.sunny.controller;

import com.sunny.mail.Mail;
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

    @Autowired
    private Mail mail;

    @RequestMapping(value = "/mail", method = RequestMethod.GET)
    public void mail() {
        mail.sendMail();
    }


    @RequestMapping(value = "/selectUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserModel select(Integer id) {

        return userService.selectById(id);
    }

}
