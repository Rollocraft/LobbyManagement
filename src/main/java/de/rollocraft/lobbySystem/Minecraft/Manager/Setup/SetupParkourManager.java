package de.rollocraft.lobbySystem.Minecraft.Manager.Setup;

import de.rollocraft.lobbySystem.Minecraft.Database.Sql.Tabels.ParkourSqlManager;
import de.rollocraft.lobbySystem.Minecraft.Manager.ParkourManager;
import de.rollocraft.lobbySystem.Minecraft.Objects.Parkour;
import de.rollocraft.lobbySystem.Minecraft.Utils.Enum.SetupParkourState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetupParkourManager {
    private Map<Player, SetupParkourState> setupStates;
    private Map<Player, Map<String, Object>> setupValues;

    private ParkourManager parkourManager;

    public SetupParkourManager(ParkourManager parkourManager) {
        this.setupStates = new HashMap<>();
        this.setupValues = new HashMap<>();
        this.parkourManager = parkourManager;
    }

    public void startSetup(Player player) {
        setupStates.put(player, SetupParkourState.CONFIRMATION);
        player.sendMessage("Are you sure you want to start the setup? Please write Yes/No");
    }

    public SetupParkourState getSetupState(Player player) {
        return setupStates.get(player);
    }

    public void setSetupState(Player player, SetupParkourState state) {
        setupStates.put(player, state);
    }

    public void handleConfirmation(Player player, String message) {
        if (message.equalsIgnoreCase("yes")) {
            setSetupState(player, SetupParkourState.PARKOUR_NAME);
            player.sendMessage("Please type in the parkour name");
        } else if (message.equalsIgnoreCase("no")) {
            setSetupState(player, null);
            player.sendMessage("Setup cancelled");
        } else {
            player.sendMessage("Please write Yes/No");
        }
    }

    public void handleParkourName(Player player, String message) {
        if (parkourManager.parkourExists(message)) {
            player.sendMessage("Parkour already exists");
            player.sendMessage("Setup cancelled");
        } else {
            getSetupValues(player).put("ParkourName", message);
            setSetupState(player, SetupParkourState.PARKOUR_DESCRIPTION);
            player.sendMessage("Please type in the parkour description");
        }
    }

    public void handleParkourDescription(Player player, String message) {
        getSetupValues(player).put("ParkourDescription", message);
        setSetupState(player, SetupParkourState.START);
        player.sendMessage("Please go to the start location and type Start");
    }

    public void handleStart(Player player, String message) {
        if (message.equalsIgnoreCase("Start")) {
            getSetupValues(player).put("Start", player.getLocation());
            setSetupState(player, SetupParkourState.CHECKPOINT);
            player.sendMessage("Start location set. Please go to the first checkpoint and type Checkpoint");
        } else {
            player.sendMessage("Please type Start");
        }
    }

    public void handleCheckpoint(Player player, String message) {
        if (message.equalsIgnoreCase("Checkpoint")) {
            List<Location> checkpoints = (List<Location>) getSetupValues(player).getOrDefault("Checkpoints", new ArrayList<>());
            Location playerLocation = player.getLocation();
            Block blockUnderPlayer = playerLocation.getWorld().getBlockAt(playerLocation.getBlockX(), playerLocation.getBlockY(), playerLocation.getBlockZ());
            Bukkit.getLogger().info(blockUnderPlayer.getType().toString());
            if (blockUnderPlayer.getType() == Material.HEAVY_WEIGHTED_PRESSURE_PLATE) {
                Location blockLocation = blockUnderPlayer.getLocation();
                double x = Math.round(blockLocation.getX()) + 0.5;
                double y = Math.round(blockLocation.getY()) + 0.5;
                double z = Math.round(blockLocation.getZ()) + 0.5;
                blockLocation.setX(x);
                blockLocation.setY(y);
                blockLocation.setZ(z);
                checkpoints.add(blockLocation);
                getSetupValues(player).put("Checkpoints", checkpoints);
                player.sendMessage("Checkpoint added. Please go to the next checkpoint and type Checkpoint, or type Checkpointsend to finish adding checkpoints");
            } else {
                player.sendMessage("You are not standing on a pressure plate. Please stand on a pressure plate and type Checkpoint.");
            }
        } else if (message.equalsIgnoreCase("Checkpointsend")) {
            setSetupState(player, SetupParkourState.END);
            player.sendMessage("Please go to the end location and type End");
        } else {
            player.sendMessage("Please type Checkpoint or Checkpointsend");
        }
    }

    public void handleEnd(Player player, String message) {
        if (message.equalsIgnoreCase("End")) {
            getSetupValues(player).put("End", player.getLocation());
            setSetupState(player, SetupParkourState.RESET_HIGH);
            player.sendMessage("Please go to the High where the Player should be teleported to the last Checkpoint and type ResetHigh");
        } else {
            player.sendMessage("Please type End");
        }
    }

    public void handleResetHigh(Player player, String message) {
        if (message.equalsIgnoreCase("ResetHigh")) {
            getSetupValues(player).put("ResetHigh", (int) Math.round(player.getLocation().getY()));
            setSetupState(player, SetupParkourState.CONFIRM_SETUP);
            player.sendMessage(formatSetupValues(player) + "Are you sure everything is correct? Please write Yes/No");
        } else {
            player.sendMessage("Please type ResetHigh");
        }
    }

    public void confirmSetup(Player player, String message) {
        if (message.equalsIgnoreCase("yes")) {
            Parkour parkour = createParkour(player);
            parkourManager.saveParkour(parkour);
            setSetupState(player, null);
            player.sendMessage("Setup completed");
        } else if (message.equalsIgnoreCase("no")) {
            setSetupState(player, null);
            player.sendMessage("Setup cancelled");
        } else {
            player.sendMessage("Please write Yes/No");
        }
    }

    private Map<String, Object> getSetupValues(Player player) {
        return setupValues.computeIfAbsent(player, k -> new HashMap<>());
    }

    private Parkour createParkour(Player player) {
        Map<String, Object> values = getSetupValues(player);
        String name = (String) values.get("ParkourName");
        String description = (String) values.get("ParkourDescription");
        Location start = (Location) values.get("Start");
        List<Location> checkpoints = (List<Location>) values.get("Checkpoints");
        Location end = (Location) values.get("End");
        int resetHigh = (int) values.get("ResetHigh");
        return new Parkour(name, description, start, checkpoints, end, resetHigh);
    }

    private String formatSetupValues(Player player) {
        Map<String, Object> values = getSetupValues(player);
        StringBuilder sb = new StringBuilder();
        sb.append("Parkour Name: ").append(values.get("ParkourName")).append("\n");
        sb.append("Parkour Description: ").append(values.get("ParkourDescription")).append("\n");
        sb.append("Start: ").append(formatLocation((Location) values.get("Start"))).append("\n");
        sb.append("Checkpoints: " + "\n").append(formatLocations((List<Location>) values.get("Checkpoints"))).append("\n");
        sb.append("End: ").append(formatLocation((Location) values.get("End"))).append("\n");
        sb.append("ResetHigh: ").append(values.get("ResetHigh")).append("\n");
        return sb.toString();
    }

    private String formatLocations(List<Location> locations) {
        int i = 1;
        StringBuilder sb = new StringBuilder();
        for (Location location : locations) {
            sb.append("Checkpoint " + i + ": " + formatLocation(location)).append("\n");
            i++;
        }
        return sb.toString();
    }

    private String formatLocation(Location location) {
        return String.format("%.2f, %.2f, %.2f", location.getX(), location.getY(), location.getZ());
    }
}