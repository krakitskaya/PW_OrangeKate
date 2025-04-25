package com.kate.project.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static final Properties properties = new Properties();

    static {
        try (FileInputStream input = new FileInputStream("src/test/resources/config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + e.getMessage(), e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public static String getBrowser() {
        return properties.getProperty("browser", "chromium"); // Default to Chromium
    }
}
