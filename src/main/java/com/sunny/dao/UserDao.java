package com.sunny.dao;

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
public class UserDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public Long selectCount(){
        return sqlSessionTemplate.selectOne("com.sunny.mapper.UserMapper.selectTotalCount");
    }

    public Long selectCount2(){
        return jdbcTemplate.queryForObject("select count(1) from t_user", Long.class);
    }
}
