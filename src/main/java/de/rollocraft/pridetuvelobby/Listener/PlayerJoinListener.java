package de.rollocraft.pridetuvelobby.Listener;

import de.rollocraft.pridetuvelobby.Database.Tables.XpDatabaseManager;
import de.rollocraft.pridetuvelobby.Manager.ScoreboardManager;
import de.rollocraft.pridetuvelobby.Manager.TablistManager;
import de.rollocraft.pridetuvelobby.Manager.TimeManager;
import de.rollocraft.pridetuvelobby.Manager.XpManager;
import de.rollocraft.pridetuvelobby.Threads.Timer;
import de.rollocraft.pridetuvelobby.Utils.Items;
import de.rollocraft.pridetuvelobby.Utils.Maps.PlayerJoinTimeMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class PlayerJoinListener implements Listener {
    private final TablistManager tablistManager;
    private ScoreboardManager scoreboardManager;
    private TimeManager timeManager;
    private XpDatabaseManager xpDatabaseManager;
    public PlayerJoinListener(TablistManager tablistManager, ScoreboardManager scoreboardManager, TimeManager timeManager, XpDatabaseManager xpDatabaseManager) {
        this.xpDatabaseManager = xpDatabaseManager;
        this.timeManager = timeManager;
        this.tablistManager = tablistManager;
        this.scoreboardManager = scoreboardManager;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        tablistManager.setTabList();
        scoreboardManager.updateScoreboard(player);

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
