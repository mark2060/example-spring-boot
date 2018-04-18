package com.sunny.mapper;

import com.sunny.model.UserModel;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * UserDao
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-08-04
 */
@Repository
public class UserMapper {

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public Long insert(UserModel model) {
        sqlSessionTemplate.insert("com.sunny.mapper.UserMapper.insert", model);
        return model.getId();
    }

    public UserModel selectById(UserModel model) {
        return sqlSessionTemplate.selectOne("com.sunny.mapper.UserMapper.select", model);
    }

    public Long selectCount() {
        return jdbcTemplate.queryForObject("select count(1) from t_user", Long.class);
    }

}
