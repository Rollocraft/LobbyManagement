package de.rollocraft.lobbySystem.Database.Sql.Tabels;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MapSqlManager {
    private Connection conn;

    public MapSqlManager(Connection conn) {
        this.conn = conn;
    }

    public void createTableIfNotExist() {
        String sql = "CREATE TABLE IF NOT EXISTS Maps ("
                + "	MapName TEXT NOT NULL,"
                + "	MapDescription TEXT,"
                + "	WorldName TEXT NOT NULL,"
                + "	Spawnlocation1 TEXT NOT NULL,"
                + "	Spawnlocation2 TEXT NOT NULL,"
                + "	SpectatorSpawn TEXT NOT NULL"
                + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }
    }

    public void createMap(String mapName, String mapDescription, String worldName, Location spawnLocation1, Location spawnLocation2, Location spectatorSpawn) {
        String sql = "INSERT INTO Maps(MapName, MapDescription, WorldName, Spawnlocation1, Spawnlocation2, SpectatorSpawn) VALUES(?,?,?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mapName);
            pstmt.setString(2, mapDescription);
            pstmt.setString(3, worldName);
            pstmt.setString(4, locationToString(spawnLocation1));
            pstmt.setString(5, locationToString(spawnLocation2));
            pstmt.setString(6, locationToString(spectatorSpawn));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }
    }

    private String locationToString(Location location) {
        return location.getX() + "," + location.getY() + "," + location.getZ();
    }

    private Location stringToLocation(String str, World world) {
        String[] parts = str.split(",");
        double x = Double.parseDouble(parts[0]);
        double y = Double.parseDouble(parts[1]);
        double z = Double.parseDouble(parts[2]);
        return new Location(world, x, y, z);
    }
    public boolean mapExists(String mapName) {
        String sql = "SELECT COUNT(*) FROM Maps WHERE MapName = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mapName);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }

        return false;
    }

    public void deleteMap(String mapName) {
        String sql = "DELETE FROM Maps WHERE MapName = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mapName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }
    }

    public List<String> getAllMaps() {
        List<String> maps = new ArrayList<>();
        String sql = "SELECT MapName FROM Maps";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                maps.add(rs.getString("MapName"));
            }
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }

        return maps;
    }

    public String getMapDescription(String mapName) {
        String sql = "SELECT MapDescription FROM Maps WHERE MapName = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mapName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("MapDescription");
            }
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }

        return null;
    }
    public String getWorldName(String mapName) {
        String sql = "SELECT WorldName FROM Maps WHERE MapName = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mapName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("WorldName");
            }
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }

        return null;
    }
    public Location getSpawnLocation1(String mapName, String worldName) {
        World world = Bukkit.getWorld(worldName);
        String sql = "SELECT Spawnlocation1 FROM Maps WHERE MapName = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mapName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String locationString = rs.getString("Spawnlocation1");
                Location returnLocation = stringToLocation(locationString, world);
                return returnLocation;

            }
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }
        return null;
    }

    public Location getSpawnLocation2(String mapName, String worldName) {
        World world = Bukkit.getWorld(worldName);
        String sql = "SELECT Spawnlocation2 FROM Maps WHERE MapName = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mapName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String locationString = rs.getString("Spawnlocation2");
                Location returnLocation = stringToLocation(locationString, world);
                return returnLocation;

            }
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }
        return null;
    }
}
