package com.sunny.mapper;

import com.sunny.model.UserModel;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * UserDao
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-08-04
 */
@Repository
public class UserMapper {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public UserModel selectById(UserModel model){
        return sqlSessionTemplate.selectOne("com.sunny.mapper.UserDao.select",model);
    }

    public Long selectCount(){
        return jdbcTemplate.queryForObject("select count(1) from t_user", Long.class);
    }

}
