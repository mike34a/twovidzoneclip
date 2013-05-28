package com.pmk.twovidzoneclip.environment;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBAccess {

    public static final String DB_PROPERTIES_FILE_PATH = "db.properties.path";

    public static final String DB_URL = "db.url";

    public static final String DB_PORT = "db.port";

    public static final String DB_USER = "db.user";

    public static final String DB_PASSWORD = "db.password";

    private static PropertiesConfiguration propertiesConfiguration = null;

    static {
        if (System.getProperties().containsKey(DB_PROPERTIES_FILE_PATH)) {
            final String filePath = System.getProperty(DB_PROPERTIES_FILE_PATH);
            try {
                propertiesConfiguration = new PropertiesConfiguration(filePath);
            } catch (ConfigurationException e) {
                Logger.getAnonymousLogger().log(Level.CONFIG, "Impossible de trouver le fichier " + filePath);
            }
        }
    }

    public static String getDBUrl() {
        return propertiesConfiguration != null ? propertiesConfiguration.getString(DB_URL) : null;
    }

    public static String getDbUser() {
        return propertiesConfiguration != null ? propertiesConfiguration.getString(DB_USER) : null;
    }

    public static String getDbPort() {
        return propertiesConfiguration != null ? propertiesConfiguration.getString(DB_PORT) : null;
    }

    public static String getDbPassword() {
        return propertiesConfiguration != null ? propertiesConfiguration.getString(DB_PASSWORD) : "";
    }
}
