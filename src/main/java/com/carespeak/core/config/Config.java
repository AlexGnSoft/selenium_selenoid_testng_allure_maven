package com.carespeak.core.config;

import java.util.Properties;

public class Config {

    private final Properties properties;

    public Config(Properties properties) {
        this.properties = properties;
    }


    public String get(String key) {
        return properties.getProperty(key);
    }

}
