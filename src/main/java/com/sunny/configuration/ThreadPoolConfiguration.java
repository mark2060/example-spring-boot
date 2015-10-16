package com.sunny.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池配置
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-08-03
 */
@Configuration
public class ThreadPoolConfiguration implements EnvironmentAware {

    private static Logger logger = LoggerFactory.getLogger(ThreadPoolConfiguration.class);

    private RelaxedPropertyResolver propertyResolver;

    private Environment environment;

    @Bean(name = "threadPoolTaskExecutor")
    @ConditionalOnMissingBean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        try {
            logger.debug("configure thread pool,current active profiles are {}", environment.getActiveProfiles());

            Integer corePoolSize = propertyResolver.getRequiredProperty("corePoolSize", Integer.class);
            Integer maxPoolSize = propertyResolver.getRequiredProperty("maxPoolSize", Integer.class);
            Integer keepAliveSeconds = propertyResolver.getRequiredProperty("keepAliveSeconds", Integer.class);

            ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
            threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
            threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
            threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
            return threadPoolTaskExecutor;
        } catch (Exception e) {
            logger.error("could not configure thread pool");
            return null;
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        this.propertyResolver = new RelaxedPropertyResolver(environment, "pool.");
    }

}
