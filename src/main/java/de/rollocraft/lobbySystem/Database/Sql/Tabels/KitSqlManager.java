package de.rollocraft.lobbySystem.Database.Sql.Tabels;

import org.bukkit.Bukkit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KitSqlManager {
    private Connection conn;
    public KitSqlManager(Connection conn) {
        this.conn = conn;
    }

    public void createTableIfNotExist() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS kits (\n"
                + " id integer PRIMARY KEY AUTOINCREMENT,\n"
                + " KitName text NOT NULL,\n"
                + " KitDescription text,\n"
                + " Base64String text NOT NULL\n"
                + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {
            Bukkit.getLogger().info(e.getMessage());
        }
    }

    public void addKit(String kitName, String kitDescription, String base64String) {
        String sql = "INSERT INTO kits(KitName, KitDescription, Base64String) VALUES(?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kitName);
            pstmt.setString(2, kitDescription);
            pstmt.setString(3, base64String);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }
    }

    public void deleteKit(String kitName) {
        String sql = "DELETE FROM kits WHERE KitName = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kitName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }
    }

    public String getKit(String kitName) {
        String sql = "SELECT Base64String FROM kits WHERE KitName = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kitName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("Base64String");
            }
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }

        return null;
    }
    public List<String> getAllKits() {
        List<String> kits = new ArrayList<>();
        String sql = "SELECT KitName FROM kits";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                kits.add(resultSet.getString("KitName"));
            }
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }
        return kits;
    }

    public String getKitDescription(String kitName) {
        String description = "";
        String sql = "SELECT KitDescription FROM kits WHERE KitName = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kitName);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                description = resultSet.getString("KitDescription");
            }
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }
        return description;
    }
}
