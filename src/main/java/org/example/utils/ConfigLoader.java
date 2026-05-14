package org.example.utils;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Utility class for loading and accessing configuration properties from the
 * application's config.properties file.
 * <p>
 * This class provides static methods to retrieve configuration values such as
 * API keys, browser types, and base URLs. All configuration is loaded from
 * {@code src/main/resources/config.properties}.
 * </p>
 *
 * @author Andrei Stoica
 * @version 1.0
 */
public class ConfigLoader {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private ConfigLoader() {
    }

    /**
     * Retrieves the API key from the secrets.properties file.
     *
     * @return the API key value, or null if not found
     */
    public static String getApiKey() {
        return getSecret("api.key");
    }
    /**
     * Retrieves the browser type from the configuration file.
     *
     * @return the browser type (e.g., "chrome", "firefox", "edge"), or null if not found
     */
    public static String getBrowser() {
        return getConfig("browser");
    }
    /**
     * Retrieves the base URL from the configuration file.
     *
     * @return the base URL of the application under test, or null if not found
     */
    public static String getBaseUrl() {
        return getConfig("baseUrl");
    }
    /**
     * Retrieves a configuration value by its key from the config.properties file.
     * <p>
     * This method loads the properties file from {@code src/main/resources/config.properties}
     * and returns the value associated with the specified key.
     * </p>
     *
     * @param key the configuration property key to retrieve
     * @return the configuration value, or null if the key is not found or an error occurs
     */
    public static String getConfig(String key) {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }

    /**
     * Retrieves a secret value by its key from the secrets.properties file.
     * <p>
     * This method loads the properties file from {@code src/main/resources/secrets.properties}
     * and returns the value associated with the specified key. This file should contain
     * sensitive information like API keys and should be excluded from version control.
     * </p>
     *
     * @param key the secret property key to retrieve
     * @return the secret value, or null if the key is not found or an error occurs
     */
    public static String getSecret(String key) {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/secrets.properties")) {
            properties.load(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }

}
