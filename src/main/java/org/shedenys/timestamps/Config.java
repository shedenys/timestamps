package org.shedenys.timestamps;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
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

    public static boolean isDevelopment() {
        return Boolean.parseBoolean(props.getProperty("development", "false"));
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}

