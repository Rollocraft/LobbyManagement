package de.rollocraft.pridetuvelobby.Listener;

import de.rollocraft.pridetuvelobby.Manager.ScoreboardManager;
import de.rollocraft.pridetuvelobby.Manager.TablistManager;
import de.rollocraft.pridetuvelobby.Manager.TimeManager;
import de.rollocraft.pridetuvelobby.Threads.Timer;
import de.rollocraft.pridetuvelobby.Utils.Items;
import de.rollocraft.pridetuvelobby.Utils.Maps.PlayerJoinTimeMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final TablistManager tablistManager;
    private ScoreboardManager scoreboardManager;
    private TimeManager timeManager;
    public PlayerJoinListener(TablistManager tablistManager, ScoreboardManager scoreboardManager, TimeManager timeManager) {
        this.timeManager = timeManager;
        this.tablistManager = tablistManager;
        this.scoreboardManager = scoreboardManager;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        tablistManager.setTabList();
        scoreboardManager.updateScoreboard(player);

        PlayerJoinTimeMap sharedMap = PlayerJoinTimeMap.getInstance();
        sharedMap.setTimeForPlayer(player, timeManager.getCurrentTime());

        player.getInventory().setItem(4, Items.ServerManagerItem());
        player.getInventory().setItem(5, Items.PlayerHeadItem(player));

    }
}
