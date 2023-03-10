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

    public static Properties properties;

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
            throw new RuntimeException("Properties file not found at " + propertyFilePath);
        }
    }

    /**
     * Returns variable value from properties file, depending on variable name
     *
     * @param variableName - any variable name from the properties file
     */
    public static String getVariableValue(String variableName) {
        String variable = properties.getProperty(variableName);
        if(variable != null) return variable;
        else throw new RuntimeException("variable is not specified in the properties file");
    }

}
