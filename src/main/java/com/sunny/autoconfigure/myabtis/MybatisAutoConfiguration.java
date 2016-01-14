package com.sunny.autoconfigure.myabtis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@Conditional(MybatisAutoConfiguration.MybatisCondition.class)
@EnableConfigurationProperties(MybatisProperties.class)
//@ConditionalOnClass(value = {EnableTransactionManagement.class})
//@ConditionalOnBean(DataSource.class)
//@MapperScan(basePackages = {"com.sunny.**.dao"})
public class MybatisAutoConfiguration {

    private static Logger logger = LoggerFactory.getLogger(MybatisAutoConfiguration.class);

    @Autowired
    private MybatisProperties properties;

    @Autowired
    private DataSource dataSource;

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory() {
        try {
            SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
            sessionFactory.setDataSource(dataSource);
            sessionFactory.setTypeAliasesPackage(properties.getTypeAliasesPackage());
            sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(properties.getMapperLocations()));
            sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(properties.getConfigLocation()));
            return sessionFactory.getObject();
        } catch (Exception e) {
            logger.error("could not configure mybatis session factory", e);
            return null;
        }
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(SqlSessionFactory.class)
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @ConditionalOnMissingBean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    static class MybatisCondition extends AnyNestedCondition {

        MybatisCondition() {
            super(ConfigurationPhase.PARSE_CONFIGURATION);
        }

        @ConditionalOnProperty(prefix = "spring.mybatis", name = "start", havingValue = "true")
        static class StartProperty {
        }

    }

}
