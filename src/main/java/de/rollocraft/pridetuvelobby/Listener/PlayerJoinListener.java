package de.rollocraft.pridetuvelobby.Listener;

import de.rollocraft.pridetuvelobby.Database.Tables.XpDatabaseManager;
import de.rollocraft.pridetuvelobby.Main;
import de.rollocraft.pridetuvelobby.Manager.*;
import de.rollocraft.pridetuvelobby.Objects.Permission;
import de.rollocraft.pridetuvelobby.Threads.Timer;
import de.rollocraft.pridetuvelobby.Utils.Items;
import de.rollocraft.pridetuvelobby.Utils.Maps.PlayerJoinTimeMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;
import java.util.List;

public class PlayerJoinListener implements Listener {
    private final TablistManager tablistManager;
    private ScoreboardManager scoreboardManager;
    private TimeManager timeManager;
    private XpDatabaseManager xpDatabaseManager;
    private PermissionManager permissionManager;
    public PlayerJoinListener(TablistManager tablistManager, ScoreboardManager scoreboardManager, TimeManager timeManager, XpDatabaseManager xpDatabaseManager, PermissionManager permissionManager) {
        this.xpDatabaseManager = xpDatabaseManager;
        this.timeManager = timeManager;
        this.tablistManager = tablistManager;
        this.scoreboardManager = scoreboardManager;
        this.permissionManager = permissionManager;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.joinMessage(null);
        Player player = event.getPlayer();
        tablistManager.setTabList();
        scoreboardManager.updateScoreboard(player);

        Permission permission = permissionManager.getPermission(player);
        if (permission != null) {
            for (String perm : permission.getPermissions()) {
                Bukkit.getLogger().info("Adding permission " + perm + " to " + player.getName());
                player.addAttachment(Main.getInstance(), perm, true);
            }
        }


        try {
            xpDatabaseManager.createPlayerEntryIfNotExists(player);
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to create player entry for " + player.getName());
        }

        PlayerJoinTimeMap sharedMap = PlayerJoinTimeMap.getInstance();
        sharedMap.setTimeForPlayer(player, timeManager.getCurrentTime());

        player.getInventory().setItem(4, Items.ServerManagerItem());
        player.getInventory().setItem(5, Items.PlayerHeadItem(player));

    }
}
