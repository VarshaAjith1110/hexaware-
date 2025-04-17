package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Make sure your db.properties file is in resources/ folder
            String connStr = DBPropertyUtil.getConnectionString("resources/db.properties");
            conn = DriverManager.getConnection(connStr, "root", "Var$ha1110"); // change username/password
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
