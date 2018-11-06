package eChallan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String CONN = "jdbc:mysql://localhost/echallan";

    public static Connection getConnection() throws SQLException{

        return DriverManager.getConnection(CONN,USERNAME,PASSWORD);
    }
}