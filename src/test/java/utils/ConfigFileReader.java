package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

    private Properties properties;
    private final String propertyFilePath= "src/test/resources/configs/Configuration.properties";

    public ConfigFileReader(){
        BufferedReader reader;
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

    public String getDatabaseUsername(){
        String databaseUsername = properties.getProperty("databaseUsername");
        if(databaseUsername!= null) return databaseUsername;
        else throw new RuntimeException("databaseUsername not specified in the Configuration.properties file.");
    }

    public String getDatabasePassword(){
        String databasePassword = properties.getProperty("databasePassword");
        if(databasePassword!= null) return databasePassword;
        else throw new RuntimeException("databasePassword not specified in the Configuration.properties file.");
    }

    public String getDatabaseUrl(){
        String databaseUrl = properties.getProperty("databaseUrl");
        if(databaseUrl!= null) return databaseUrl;
        else throw new RuntimeException("databaseUrl not specified in the Configuration.properties file.");
    }
}