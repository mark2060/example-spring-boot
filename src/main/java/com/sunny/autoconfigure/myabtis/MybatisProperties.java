package com.sunny.autoconfigure.myabtis;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ExecutorProperties
 *
 * @author sunny
 * @version 1.0.0
 * @since 2016-01-13
 */
@ConfigurationProperties(prefix = "spring.mybatis")
public class MybatisProperties {

    private String typeAliasesPackage;

    private String mapperLocations;

    private String configLocation;

    public String getTypeAliasesPackage() {
        return typeAliasesPackage;
    }

    public void setTypeAliasesPackage(String typeAliasesPackage) {
        this.typeAliasesPackage = typeAliasesPackage;
    }

    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public String getConfigLocation() {
        return configLocation;
    }

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

}
