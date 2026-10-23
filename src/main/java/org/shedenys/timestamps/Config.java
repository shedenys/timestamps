package org.shedenys.timestamps;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.TimeZone;

/**
 * The Config class is responsible for loading and managing application configuration
 * properties from a file named "config.properties" located in the classpath.
 * It provides utility methods to access specific properties and check the development
 * mode.
 */
public class Config {
    /**
     * A static {@link Properties} instance responsible for storing application
     * configuration properties loaded from the "config.properties" file.
     */
    private static final Properties props = new Properties();

    static {
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties")) {
            if (null != input) {
                props.load(input);
            } else {
                throw new RuntimeException("config.properties not found in classpath");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    /**
     * Determines if the application is running in development mode based on the
     * "development" property from the configuration file.
     */
    public static boolean isDevelopment() {
        return Boolean.parseBoolean(props.getProperty("development", "false"));
    }

    /**
     * Retrieves the value of a property corresponding to the specified key from the configuration.
     *
     * @param key the name of the property to retrieve
     * @return the value of the specified property, or null if the property is not found
     */
    public static String get(String key) {
        return props.getProperty(key);
    }

    /**
     * Retrieves the default time zone for JPEG files based on the "default.jpeg.timezone" property from the configuration.
     *
     * @return the default time zone, or UTC if the property is not found or invalid
     */
    public static TimeZone getDefaultJpegTimeZone() {
        return TimeZone.getTimeZone(get("default.jpeg.timezone"));
    }
}

