package com.sunny.service;

import com.sunny.model.UserModel;
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

    public UserModel selectById(Integer id) {
        UserModel result = new UserModel();

        result.setId(1);
        result.setUsername("tom");
        result.setPassword("jack");

        return result;
    }

}
