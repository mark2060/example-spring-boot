package com.sunny.autoconfigure.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池配置
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-08-03
 */
@Configuration
@Conditional(ExecutorAutoConfiguration.ExecutorCondition.class)
@EnableConfigurationProperties(ExecutorProperties.class)
public class ExecutorAutoConfiguration {

    private static Logger logger = LoggerFactory.getLogger(ExecutorAutoConfiguration.class);

    @Autowired
    private ExecutorProperties properties;

    @Bean(name = "executor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        try {
            ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
            threadPoolTaskExecutor.setCorePoolSize(properties.getCorePoolSize());
            threadPoolTaskExecutor.setMaxPoolSize(properties.getMaxPoolSize());
            threadPoolTaskExecutor.setKeepAliveSeconds(properties.getKeepAliveSeconds());
            return threadPoolTaskExecutor;
        } catch (Exception e) {
            logger.error("could not configure thread pool");
            return null;
        }
    }

    static class ExecutorCondition extends AnyNestedCondition {

        ExecutorCondition() {
            super(ConfigurationPhase.PARSE_CONFIGURATION);
        }

        @ConditionalOnProperty(prefix = "spring.executor", name = "start",havingValue = "true")
        static class StartProperty {
        }

    }

}
