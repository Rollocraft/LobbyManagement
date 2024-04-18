package de.rollocraft.lobbySystem.Minecraft.Database.Mysql.Tables;

import org.bukkit.entity.Player;

import java.sql.*;

public class XpDatabaseManager {
    private final Connection connection;

    public XpDatabaseManager(Connection connection) {
        this.connection = connection;
    }

    public void createXpTableIfNotExists() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS xp (id INTEGER PRIMARY KEY AUTO_INCREMENT, player VARCHAR(255) NOT NULL, xp INTEGER NOT NULL)");
        }
    }

    public void createPlayerEntryIfNotExists(Player player) throws SQLException {
        String playerName = player.getName();
        String query = "INSERT INTO xp (player, xp) SELECT ?, ? WHERE NOT EXISTS(SELECT 1 FROM xp WHERE player = ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, playerName);
            stmt.setInt(2, 1); // Standart xp = 1
            stmt.setString(3, playerName);
            stmt.executeUpdate();
        }
    }

    public void addXp(Player player, int xpToAdd) throws SQLException {
        String playerName = player.getName();
        String query = "UPDATE xp SET xp = xp + ? WHERE player = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, xpToAdd);
            statement.setString(2, playerName);
            statement.executeUpdate();
        }
    }

    public int getXp(Player player) throws SQLException {
        String playerName = player.getName();
        String query = "SELECT xp FROM xp WHERE player = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, playerName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("xp");
            } else {
                return 0;
            }
        }
    }
}