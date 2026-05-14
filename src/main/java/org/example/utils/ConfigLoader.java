package org.example.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigLoader {

    private ConfigLoader() {
    }

    public static String getApiKey() {
        return getConfig("api.key");
    }
    public static String getBrowser() {
        return getConfig("browser");
    }
    public static String getBaseUrl() {
        return getConfig("baseUrl");
    }
    public static String getConfig(String key) {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }

}
