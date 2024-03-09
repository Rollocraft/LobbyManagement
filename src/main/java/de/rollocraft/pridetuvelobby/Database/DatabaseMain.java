package de.rollocraft.pridetuvelobby.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseMain {
    private Connection connection;

    public DatabaseMain() {
        connectToDatabase();
    }

    public void connectToDatabase() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lol", "code", ")1Iaio0[Z0rozcbq");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}