package de.rollocraft.lobbySystem.Minecraft.Manager.Setup;

import de.rollocraft.lobbySystem.Minecraft.Database.Sql.Tabels.MapSqlManager;
import de.rollocraft.lobbySystem.Minecraft.Utils.Enum.SetupMapState;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class SetupPvpMapManager {
    private Map<Player, SetupMapState> setupStates;
    private Map<Player, Map<String, Object>> setupValues;
    private MapSqlManager mapSqlManager;

    public SetupPvpMapManager(MapSqlManager mapSqlManager) {
        this.mapSqlManager = mapSqlManager;
        this.setupStates = new HashMap<>();
        this.setupValues = new HashMap<>();
    }

    public void startSetup(Player player) {
        setupStates.put(player, SetupMapState.CONFIRMATION);
        player.sendMessage("Are you sure you want to start the setup? Please write Yes/No");
    }

    public SetupMapState getSetupState(Player player) {
        return setupStates.get(player);
    }

    public void setSetupState(Player player, SetupMapState state) {
        setupStates.put(player, state);
    }

    public void handleConfirmation(Player player, String message) {
        if (message.equalsIgnoreCase("yes")) {
            setSetupState(player, SetupMapState.MAP_NAME);
            player.sendMessage("Please type in the map name");
        } else if (message.equalsIgnoreCase("no")) {
            setSetupState(player, null);
            player.sendMessage("Setup cancelled");
        } else {
            player.sendMessage("Please write Yes/No");
        }
    }

    public void handleMapName(Player player, String message) {
        if (mapSqlManager.mapExists(message)) {
            player.sendMessage("Map already exists");
            player.sendMessage("Setup cancelled");
        } else {
            getSetupValues(player).put("MapName", message);
            setSetupState(player, SetupMapState.MAP_DESCRIPTION);
            player.sendMessage("Please type in the map description");
        }
    }

    public void handleMapDescription(Player player, String message) {
        getSetupValues(player).put("MapDescription", message);
        setSetupState(player, SetupMapState.SPAWNPOINT1);
        player.sendMessage("Please go to the spawnpoint of player1 and type Spawnpoint1");
    }

    public void handleSpawnpoint1(Player player, String message) {
        if (message.equalsIgnoreCase("Spawnpoint1")) {
            getSetupValues(player).put("Spawnpoint1", player.getLocation());
            setSetupState(player, SetupMapState.SPAWNPOINT2);
            player.sendMessage("Please go to the spawnpoint of player2 and type Spawnpoint2");
        } else {
            player.sendMessage("Please type Spawnpoint1");
        }
    }

    public void handleSpawnpoint2(Player player, String message) {
        if (message.equalsIgnoreCase("Spawnpoint2")) {
            getSetupValues(player).put("Spawnpoint2", player.getLocation());
            setSetupState(player, SetupMapState.SPECTATOR);
            player.sendMessage("Please go to the spectator spawnpoint and type SpawnpointSpectator");
        } else {
            player.sendMessage("Please type Spawnpoint2");
        }
    }

    public void handleSpectator(Player player, String message) {
        if (message.equalsIgnoreCase("SpawnpointSpectator")) {
            getSetupValues(player).put("Spectator", player.getLocation());
            setSetupState(player, SetupMapState.CONFIRM_SETUP);
            player.sendMessage(formatSetupValues(player) + "Are you sure everything is correct? Please write Yes/No");
        } else {
            player.sendMessage("Please type SpawnpointSpectator");
        }
    }

    public void confirmSetup(Player player, String message) {
        if (message.equalsIgnoreCase("yes")) {
            mapSqlManager.createMap(
                    (String) getSetupValues(player).get("MapName"),
                    (String) getSetupValues(player).get("MapDescription"),
                    player.getWorld().getName(),
                    (Location) getSetupValues(player).get("Spawnpoint1"),
                    (Location) getSetupValues(player).get("Spawnpoint2"),
                    (Location) getSetupValues(player).get("Spectator")
            );
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

    private String formatSetupValues(Player player) {
        Map<String, Object> values = getSetupValues(player);
        StringBuilder sb = new StringBuilder();
        sb.append("Map Name: ").append(values.get("MapName")).append("\n");
        sb.append("Map Description: ").append(values.get("MapDescription")).append("\n");
        sb.append("Spawnpoint1: ").append(formatLocation((Location) values.get("Spawnpoint1"))).append("\n");
        sb.append("Spawnpoint2: ").append(formatLocation((Location) values.get("Spawnpoint2"))).append("\n");
        sb.append("Spectator: ").append(formatLocation((Location) values.get("Spectator"))).append("\n");
        return sb.toString();
    }

    private String formatLocation(Location location) {
        return Math.round(location.getX()) + ", " + Math.round(location.getY()) + ", " + Math.round(location.getZ());
    }
}