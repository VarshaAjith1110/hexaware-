package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            String connectionString = DBPropertyUtil.getConnectionString("resources/db.properties");
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
