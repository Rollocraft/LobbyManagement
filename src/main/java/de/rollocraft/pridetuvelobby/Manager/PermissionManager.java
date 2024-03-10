package de.rollocraft.pridetuvelobby.Manager;

import de.rollocraft.pridetuvelobby.Database.Tables.PermissionDatabaseManager;
import de.rollocraft.pridetuvelobby.Objects.Permission;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class PermissionManager {
    private final PermissionDatabaseManager permissionDatabaseManager;

    public PermissionManager(PermissionDatabaseManager permissionDatabaseManager) {
        this.permissionDatabaseManager = permissionDatabaseManager;
    }

    public List<String> getPermission(Player player) {
        try {
            List<Permission> permissions = permissionDatabaseManager.loadPermissions();
            for (Permission permission : permissions) {
                if (permission.getPlayers().contains(player.getName())) {
                    return permission.getPermissions();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
