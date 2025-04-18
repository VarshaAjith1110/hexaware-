package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {

    public static String getConnectionString(String fileName) {
        Properties props = new Properties();
        String connectionString = null;

        try (FileInputStream fis = new FileInputStream(fileName)) {
            props.load(fis);
            String host = props.getProperty("host");
            String port = props.getProperty("port");
            String database = props.getProperty("database");
            String user = props.getProperty("user");
            String password = props.getProperty("password");

            connectionString = "jdbc:mysql://" + host + ":" + port + "/" + database +
                               "?user=" + user + "&password=" + password;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connectionString;
    }
}
