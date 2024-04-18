package de.rollocraft.lobbySystem.Minecraft.Database.Sql.Tabels;

import de.rollocraft.lobbySystem.Minecraft.Objects.Effects;
import org.bukkit.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlockParticelSqlManager {
    private Connection conn;

    public BlockParticelSqlManager(Connection conn) {
        this.conn = conn;
    }

    public void createTableIfNotExist() {
        String sql = "CREATE TABLE IF NOT EXISTS effects ("
                + "	id integer PRIMARY KEY,"
                + "	coords text NOT NULL,"
                + "	color text,"
                + "	type text NOT NULL,"
                + " intensity integer NOT NULL,"
                + " name text"
                + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }
    }

    public void addEffect(Effects effect) {
        String coords = effect.getLocation().getX() + ";" + effect.getLocation().getY() + ";" + effect.getLocation().getZ() + ";" + effect.getLocation().getWorld().getName();
        String sql = "INSERT INTO effects(coords, color, type, intensity) VALUES(?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, coords);
            String color = effect.getColor();
            if (color != null) {
                pstmt.setString(2, color.toString());
            } else {
                pstmt.setString(2, null);
            }
            pstmt.setString(3, effect.getType().name());
            pstmt.setInt(4, effect.getIntensity());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }
    }

    public void deleteEffect(Location location) {
        String coords = location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getWorld().getName();
        String sql = "DELETE FROM effects WHERE coords = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, coords);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }
    }

    public List<Effects> getAllEffects() {
        String sql = "SELECT coords, color, type, intensity FROM effects";
        List<Effects> effects = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String[] coords = rs.getString("coords").split(";");
                World world = Bukkit.getWorld(coords[3]);
                double x = Double.parseDouble(coords[0]);
                double y = Double.parseDouble(coords[1]);
                double z = Double.parseDouble(coords[2]);
                Location location = new Location(world, x, y, z);
                String color = rs.getString("color");
                Particle type = Particle.valueOf(rs.getString("type"));
                int intensity = rs.getInt("intensity");
                effects.add(new Effects(location, color, type, intensity));
            }
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }
        return effects;
    }
}