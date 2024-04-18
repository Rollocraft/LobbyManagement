package de.rollocraft.lobbySystem.Minecraft.Database.Sql;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlMain {
    private JavaPlugin plugin;
    private Connection connection;

    public SqlMain(JavaPlugin plugin) {
        this.plugin = plugin;
        connectToDatabase();
    }

    public void connectToDatabase() {
        try {
            if (connection == null || connection.isClosed()) {
                String path = plugin.getDataFolder().getAbsolutePath() + "/sql";
                File directory = new File(path);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                String url = "jdbc:sqlite:" + path + "/database.db";
                connection = DriverManager.getConnection(url);
                if (connection != null) {
                    Bukkit.getLogger().info("Connected to the database");
                }
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