package com.carespeak.core.config;

import com.carespeak.core.logger.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Reads properties from property file
 */
public class PropertyFileReader implements IConfigReader {

    public Properties properties;

    @Override
    public Config readConfig(String url) {
        Logger.info("Read config by URL '" + url + "'..");
        Properties props = new Properties();
        try {
            ClassLoader loader = PropertyFileReader.class.getClassLoader();
            try (InputStream is = loader.getResourceAsStream(url)) {
                props.load(is);
            }
        } catch (IOException e) {
            Logger.error("Failed to read property file", e);
            throw new RuntimeException(e);
        }
        Logger.info("Property file read successfully");
        mergePropsWithArguments(props);
        return new Config(props);
    }

    /**
     * Merge properties from command line arguments with provided properties object
     *
     * @param properties - merged properties object
     */
    private void mergePropsWithArguments(Properties properties) {
        List<String> keys = new ArrayList<>();
        keys.addAll(properties.stringPropertyNames());
        for (String key : keys) {
            properties.put(key, System.getProperty(key, properties.getProperty(key)));
        }
    }

    public PropertyFileReader(){
        BufferedReader reader;
        String propertyFilePath = "src/main/resources/env/demo.properties";
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
        }
    }

    public String getEnvValue() {
        String app_env = properties.getProperty("app_env");
        if(app_env != null) return app_env;
        else throw new RuntimeException("app_env is not specified in the config.properties file");
    }


}
