package com.sunny.configuration;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 数据源配置
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-08-03
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration implements EnvironmentAware {

    private static Logger logger = LoggerFactory.getLogger(DataSourceConfiguration.class);

    private RelaxedPropertyResolver propertyResolver;

    private Environment environment;

    @Bean(name = "dataSource", destroyMethod = "close")
    public DataSource dataSource() {
        logger.debug("configure datasource,current active profiles are {}", environment.getActiveProfiles());

        String driverClassName = propertyResolver.getRequiredProperty("driverClassName");
        String url = propertyResolver.getRequiredProperty("url");
        String username = propertyResolver.getRequiredProperty("username");
        String password = propertyResolver.getRequiredProperty("password");
        Boolean testOnBorrow = propertyResolver.getRequiredProperty("testOnBorrow", Boolean.class);
        Boolean testWhileIdle = propertyResolver.getRequiredProperty("testWhileIdle", Boolean.class);
        Long timeBetweenEvictionRunsMillis = propertyResolver.getRequiredProperty("timeBetweenEvictionRunsMillis", Long.class);

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

        return dataSource;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        this.propertyResolver = new RelaxedPropertyResolver(environment, "datasource.");
    }

}
