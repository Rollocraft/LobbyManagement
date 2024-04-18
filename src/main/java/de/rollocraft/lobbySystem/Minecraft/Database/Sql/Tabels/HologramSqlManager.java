package de.rollocraft.lobbySystem.Minecraft.Database.Sql.Tabels;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HologramSqlManager {
    private Connection conn;

    public HologramSqlManager(Connection conn) {
        this.conn = conn;
    }

    public void createTableIfNotExist() {
        String sql = "CREATE TABLE IF NOT EXISTS holograms ("
                + "	id integer PRIMARY KEY,"
                + "	coords text NOT NULL,"
                + "	text text NOT NULL,"
                + "	gruppe text NOT NULL,"
                + " name text"
                + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }
    }

    public void addHologram(Location location, String text, String gruppe) {
    String coords = location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getWorld().getName();
    String sql = "INSERT INTO holograms(coords, text, gruppe) VALUES(?,?,?)";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, coords);
        pstmt.setString(2, text);
        pstmt.setString(3, gruppe); // Fügen Sie den Wert für "gruppe" hinzu
        pstmt.executeUpdate();
    } catch (SQLException e) {
        Bukkit.getLogger().info(e.getMessage());
    }
}

    public void deleteHologram(Location location) {
        String coords = location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getWorld().getName();
        String sql = "DELETE FROM holograms WHERE coords = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, coords);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }
    }

    public String getHologramText(Location location) {
        String coords = location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getWorld().getName();
        String sql = "SELECT text FROM holograms WHERE coords = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, coords);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("text");
            }
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }

        return null;
    }

    public boolean groupExists(String gruppe) {
        String sql = "SELECT 1 FROM holograms WHERE gruppe = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, gruppe);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true; // Gruppe existiert
            }
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }

        return false; // Gruppe existiert nicht
    }

    public List<Location> getHologramsInGroup(String groupname) {
        List<Location> locations = new ArrayList<>();
        String sql = "SELECT coords FROM holograms WHERE gruppe = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, groupname);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String[] coords = rs.getString("coords").split(";");
                Location location = new Location(Bukkit.getWorld(coords[3]), Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2]));
                locations.add(location);
            }
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }

        return locations;
    }

    public String getHologramGroup(Location location) {
        String coords = location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getWorld().getName();
        String sql = "SELECT gruppe FROM holograms WHERE coords = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, coords);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("gruppe");
            }
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }

        return null;
    }
}