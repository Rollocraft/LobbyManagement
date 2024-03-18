package de.rollocraft.lobbySystem.Database.Mysql.Tables;

import de.rollocraft.lobbySystem.Objects.Permission;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermissionDatabaseManager {
    private final Connection connection;

    public PermissionDatabaseManager(Connection connection) {
        this.connection = connection;
    }

    public void createPermissionsTableIfNotExists() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS Permissions (RankName VARCHAR(255), Prefix VARCHAR(255), Permission VARCHAR(255), Player VARCHAR(255), PRIMARY KEY (RankName, Permission, Player))");
        }
    }

    public void savePermission(Permission permission) throws SQLException {
        String query = "INSERT INTO Permissions (RankName, Prefix, Permission, Player) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE Prefix = ?, Permission = ?, Player = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, permission.getRank());
            stmt.setString(2, permission.getPrefix());
            stmt.setString(3, String.join(",", permission.getPermissions()));
            stmt.setString(4, String.join(",", permission.getPlayers()));
            stmt.setString(5, permission.getPrefix());
            stmt.setString(6, String.join(",", permission.getPermissions()));
            stmt.setString(7, String.join(",", permission.getPlayers()));
            stmt.executeUpdate();
        }
    }

    public List<Permission> loadPermissions() throws SQLException {
        List<Permission> permissions = new ArrayList<>();
        String query = "SELECT * FROM Permissions";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String rank = rs.getString("RankName");
                String prefix = rs.getString("Prefix");
                List<String> permissionList = Arrays.asList(rs.getString("Permission").split(","));
                List<String> players = Arrays.asList(rs.getString("Player").split(","));
                permissions.add(new Permission(rank, prefix, permissionList, players));
            }
        }
        return permissions;
    }

    public Permission loadPermission(String rank) throws SQLException {
        Permission permission = null;
        String query = "SELECT * FROM Permissions WHERE RankName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, rank);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String prefix = rs.getString("Prefix");
                List<String> permissions = Arrays.asList(rs.getString("Permission").split(","));
                List<String> players = Arrays.asList(rs.getString("Player").split(","));
                permission = new Permission(rank, prefix, permissions, players);
            }
        }
        return permission;
    }

    public void deletePermission(String rank) throws SQLException {
        String query = "DELETE FROM Permissions WHERE RankName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, rank);
            stmt.executeUpdate();
        }
    }
}
