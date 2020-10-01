package me.nikox.zwierzoinator.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesWrapper {

    private String propertiesFile;
    private String path;
    private Properties properties;


    /**
     * Constructor dla pliku properties ze ścieżką ../plik.properties
     *
     * @param propertiesFile - Nazwa pliku properties
     */
    public PropertiesWrapper(String propertiesFile) {
        this.propertiesFile = propertiesFile;
        this.path = System.getProperty("user.dir");
        this.path = this.path + "/" + propertiesFile;
    }

    /**
     * Constructor dla pliku properties, ścieżka pliku to ścieżka obiektu File.
     *
     * @param propertiesFile - Obiekt File
     */
    public PropertiesWrapper(File propertiesFile) {
        this.propertiesFile = propertiesFile.getName();
        this.path = propertiesFile.getAbsolutePath();
    }

    /**
     * Tworzenie pustego pliku Properties.
     */
    public Properties create() {
        try {
            File file = new File(this.path);
            if(!file.exists()){
                file.createNewFile();
            }
            this.properties = new Properties();
            return this.properties;
        } catch (IOException io) {
            System.out.println("Wystąpił błąd podczas zapisywania pliku!\n\n" + io.getMessage());
            return null;
        }
    }

    /**
     * @return String zawierający ścieżkę pliku
     */
    public String getPath() {
        return this.path;
    }

    /**
     * @return Obiekt Properties
     */
    public Properties getProperties() {
        return this.properties;
    }

    /**
     * Ładowanie pliku.
     */
    public Properties load() {
        Properties properties = new Properties();
        try {
            FileInputStream ip = new FileInputStream(this.path);
            properties.load(ip);
            this.properties = properties;
            ip.close();
            return this.properties;
        } catch (IOException ne) {
            System.out.println("Nie odnaleziono pliku!\n Sciezka pliku: " + path);
            return null;
        }
    }

    /**
     * @return Obiekt File
     */
    public File getFile() {
        return new File(this.path);
    }

    /**
     * Zapisywanie pliku properties.
     */
    public void save() {
        try {
            FileOutputStream out = new FileOutputStream(this.path);
            this.properties.store(out, null);
            out.close();
        } catch (IOException io) {
            System.out.println("Wystąpił błąd podczas zapisywania pliku!\n\n" + io.getMessage());
        }
    }

    /**
     * Ustawianie konkretnej wartości.
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        try {
            FileOutputStream out = new FileOutputStream(this.path);
            this.properties.setProperty(key, value);
            this.properties.store(out, null);
            out.close();
        } catch (IOException io) {
            System.out.println("Wystąpił błąd podczas zapisywania pliku!\n\n" + io.getMessage());
        }
    }
}
