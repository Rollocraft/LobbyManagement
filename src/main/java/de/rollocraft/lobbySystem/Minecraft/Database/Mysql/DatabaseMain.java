package de.rollocraft.lobbySystem.Minecraft.Database.Mysql;

import de.rollocraft.lobbySystem.Minecraft.Utils.ConfigManager;
import de.rollocraft.lobbySystem.Minecraft.Utils.Maps.BuildMap;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseMain {
    private Connection connection;
    private ConfigManager configManager;

    public DatabaseMain(ConfigManager configManager) {
        this.configManager = configManager;
        connectToDatabase();
    }

    public void connectToDatabase() {
        try {
            if (connection == null || connection.isClosed()) {
                String host = configManager.get().getString("database.host");
                String user = configManager.get().getString("database.user");
                String password = configManager.get().getString("database.password");
                Bukkit.getLogger().info("Connecting to database...");

                connection = DriverManager.getConnection(host, user, password);
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
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}