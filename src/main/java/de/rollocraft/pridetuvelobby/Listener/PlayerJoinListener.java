package de.rollocraft.pridetuvelobby.Listener;

import de.rollocraft.pridetuvelobby.Manager.ScoreboardManager;
import de.rollocraft.pridetuvelobby.Manager.TablistManager;
import de.rollocraft.pridetuvelobby.Threads.Timer;
import de.rollocraft.pridetuvelobby.Utils.Items;
import de.rollocraft.pridetuvelobby.Utils.Maps.PlayerJoinTimeMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final TablistManager tablistManager;
    private Timer timer;
    private ScoreboardManager scoreboardManager;
    public PlayerJoinListener(TablistManager tablistManager, Timer timer, ScoreboardManager scoreboardManager) {
        this.tablistManager = tablistManager;
        this.timer = timer;
        this.scoreboardManager = scoreboardManager;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        tablistManager.setTabList();
        PlayerJoinTimeMap sharedMap = PlayerJoinTimeMap.getInstance();
        sharedMap.setTimeForPlayer(player, timer.getTime());
        scoreboardManager.updateScoreboard(player);
        player.getInventory().setItem(4, Items.ServerManagerItem());

    }
}
