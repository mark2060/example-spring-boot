package com.sunny.dao;

import com.sunny.model.UserModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * UserMapper
 *
 * @author sunny
 * @version 1.0.0
 * @since 2016-03-24
 */
public interface UserMapper {

    @Select("select id,username,password,birthday,create_time as createTime from t_user where id = #{id}")
    public UserModel selectUserById(@Param("id") Long id);

}
