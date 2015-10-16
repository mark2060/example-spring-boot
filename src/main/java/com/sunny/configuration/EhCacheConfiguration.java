package com.sunny.configuration;

import net.sf.ehcache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * 缓存配置
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-08-04
 */
@Configuration
@EnableCaching(proxyTargetClass = true)
public class EhCacheConfiguration {

    @Bean(name = "ehCache")
    public CacheManager getEhCacheManagerFactory() {
        EhCacheManagerFactoryBean ehCacheManagerFactory = new EhCacheManagerFactoryBean();

        //load resource
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:ehcache.xml");
        ehCacheManagerFactory.setConfigLocation(resource);

        return ehCacheManagerFactory.getObject();
    }


    @Bean(name = "ehCacheManager")
    public EhCacheCacheManager getEhCacheCacheManager(CacheManager cacheManager) {
        EhCacheCacheManager ehCacheManager = new EhCacheCacheManager();
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;
    }

}
