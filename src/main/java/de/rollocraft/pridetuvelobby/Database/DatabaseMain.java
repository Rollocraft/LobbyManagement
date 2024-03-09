package de.rollocraft.pridetuvelobby.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseMain {
    private Connection connection;
    private String databaseUrl = "";
    private String user = "";
    private String password = "";

    public void connectToDatabase() {
        try {
            connection = DriverManager.getConnection(databaseUrl, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
