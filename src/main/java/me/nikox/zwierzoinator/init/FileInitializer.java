package me.nikox.zwierzoinator.init;

import me.nikox.zwierzoinator.util.PropertiesWrapper;

import javax.naming.ConfigurationException;
import java.io.FileNotFoundException;
import java.util.Properties;

public class FileInitializer {

    private static Properties config;
    private static Properties messages;

    public static void load() throws ConfigurationException {
        initializeConfig();
        initializeMessages();
    }

    public static void initializeConfig() throws ConfigurationException {
        PropertiesWrapper wrapper = new PropertiesWrapper("config.properties");
        wrapper.create();
        config = wrapper.load();
        if (config == null) {
            throw new ConfigurationException("Nieprawidlowy plik konfiguracji!");
        }
        if (config.getProperty("bootstrap.token") == null) {
            throw new ConfigurationException("Token bota nie zostal ustawiony!");
        }
    }

    public static void initializeMessages() {
        PropertiesWrapper wrapper = new PropertiesWrapper("messages.properties");
        wrapper.create();
        messages = wrapper.load();
    }

    public static Properties getConfig() {
        return config;
    }

    public static Properties getMessages() {
        return messages;
    }

}
