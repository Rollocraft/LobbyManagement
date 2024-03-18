package de.rollocraft.lobbySystem.Utils.Maps;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.Map;

public class PlayerPermissionsMap {
    private static PlayerPermissionsMap instance;
    private final Map<Player, PermissionAttachment> playerPermissions;

    private PlayerPermissionsMap() {
        playerPermissions = new HashMap<>();
    }

    public static synchronized PlayerPermissionsMap getInstance() {
        if (instance == null) {
            instance = new PlayerPermissionsMap();
        }
        return instance;
    }
    public void removePlayer(Player player) {
        playerPermissions.remove(player);
    }

    public PermissionAttachment getPlayerPermissions(Player player) {
        return playerPermissions.get(player);
    }
    public void setPlayerPermissions(Player player, PermissionAttachment permissionAttachment) {
        playerPermissions.put(player, permissionAttachment);
    }
}
