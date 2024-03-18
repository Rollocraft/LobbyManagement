package de.rollocraft.lobbySystem.Listener;

import de.rollocraft.lobbySystem.Database.Mysql.Tables.XpDatabaseManager;
import de.rollocraft.lobbySystem.Manager.*;
import de.rollocraft.lobbySystem.Utils.Items;
import de.rollocraft.lobbySystem.Utils.Maps.PlayerJoinTimeMap;
import de.rollocraft.lobbySystem.Utils.Maps.PlayerSecretKeyMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;

public class PlayerJoinListener implements Listener {
    private final TablistManager tablistManager;
    private ScoreboardManager scoreboardManager;
    private TimeManager timeManager;
    private XpDatabaseManager xpDatabaseManager;
    private WorldManager worldManager;
    public PlayerJoinListener(TablistManager tablistManager, ScoreboardManager scoreboardManager, TimeManager timeManager, XpDatabaseManager xpDatabaseManager, WorldManager worldManager) {
        this.xpDatabaseManager = xpDatabaseManager;
        this.timeManager = timeManager;
        this.tablistManager = tablistManager;
        this.scoreboardManager = scoreboardManager;
        this.worldManager = worldManager;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.joinMessage(null);
        Player player = event.getPlayer();
        tablistManager.setTabList();
        scoreboardManager.updateScoreboard(player);

        if (player.hasPermission("pridetuve.staff.manage")) {
            Map<String, String> nonExistentWorlds = worldManager.checkWorlds();
            for (Map.Entry<String, String> entry : nonExistentWorlds.entrySet()) {
                String worldName = entry.getKey();
                String mapName = entry.getValue();
                player.sendMessage("Die Welt " + ChatColor.RED + worldName + ChatColor.WHITE + " bzw. deshalb auch die Map " + ChatColor.RED + mapName + ChatColor.WHITE + " existiert nicht.");
            }
        }

        try {
            xpDatabaseManager.createPlayerEntryIfNotExists(player);
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to create player entry for " + player.getName());
        }

        PlayerJoinTimeMap sharedMap = PlayerJoinTimeMap.getInstance();
        sharedMap.setTimeForPlayer(player, timeManager.getCurrentTime());

        PlayerSecretKeyMap secretKeyMap = PlayerSecretKeyMap.getInstance();
        try {
            SecretKey secretKey = PlayerSecretKeyMap.generateSecretKey();
            secretKeyMap.setSecretKeyForPlayer(player, secretKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        player.getInventory().setItem(4, Items.ServerManagerItem());
        player.getInventory().setItem(5, Items.PlayerHeadItem(player));
        player.getInventory().setItem(3, Items.GraphlinHook());
    }

}
