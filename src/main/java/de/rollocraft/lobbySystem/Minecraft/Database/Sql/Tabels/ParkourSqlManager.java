package de.rollocraft.lobbySystem.Minecraft.Database.Sql.Tabels;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.rollocraft.lobbySystem.Minecraft.Objects.Parkour;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class ParkourSqlManager {
    private Connection connection;

    public ParkourSqlManager(Connection conn) {
        this.connection = conn;
    }

    public void createTableIfNotExists() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS parkours (" +
                "name VARCHAR(255), " +
                "description VARCHAR(255), " +
                "start VARCHAR(255), " +
                "checkpoints VARCHAR(255), " +
                "end VARCHAR(255), " +
                "resetHigh int," +
                "PRIMARY KEY (name)" +
                ")";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
    }

    public void saveParkour(Parkour parkour) throws SQLException {
        String sql = "INSERT INTO parkours (name, description, start, checkpoints, end, resetHigh) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, parkour.getName());
        statement.setString(2, parkour.getDescription());
        statement.setString(3, locationToString(parkour.getStart()));
        statement.setString(4, locationsToString(parkour.getCheckpoints()));
        statement.setString(5, locationToString(parkour.getEnd()));
        statement.setInt(6, parkour.getResetHigh()); // Save the resetHigh property
        statement.executeUpdate();
    }

    public Parkour getParkour(String name) throws SQLException {
        String sql = "SELECT * FROM parkours WHERE name = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String description = resultSet.getString("description");
            Location start = stringToLocation(resultSet.getString("start"));
            List<Location> checkpoints = stringToLocations(resultSet.getString("checkpoints"));
            Location end = stringToLocation(resultSet.getString("end"));
            int resetHigh = resultSet.getInt("resetHigh"); // Retrieve the resetHigh property
            return new Parkour(name, description, start, checkpoints, end, resetHigh);
        }

        return null;
    }

    private String locationToString(Location location) {
        return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ();
    }


    private Location stringToLocation(String str) {
        String[] parts = str.split(",");
        World world = Bukkit.getServer().getWorld(parts[0]);
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);
        return new Location(world, x, y, z);
    }

    private String locationsToString(List<Location> locations) {
        List<String> strLocations = new ArrayList<>();
        for (Location location : locations) {
            strLocations.add(locationToString(location));
        }
        return String.join(";", strLocations);
    }

    private List<Location> stringToLocations(String str) {
        List<Location> locations = new ArrayList<>();
        String[] strLocations = str.split(";");
        for (String strLocation : strLocations) {
            locations.add(stringToLocation(strLocation));
        }
        return locations;
    }
}
