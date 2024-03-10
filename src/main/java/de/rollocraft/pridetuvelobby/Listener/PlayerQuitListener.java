package de.rollocraft.pridetuvelobby.Listener;

import de.rollocraft.pridetuvelobby.Database.Tables.TimerDatabaseManager;
import de.rollocraft.pridetuvelobby.Manager.TimeManager;
import de.rollocraft.pridetuvelobby.Objects.Time;
import de.rollocraft.pridetuvelobby.Threads.Timer;
import de.rollocraft.pridetuvelobby.Utils.Maps.PlayerJoinTimeMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class PlayerQuitListener implements Listener {
    private TimerDatabaseManager timerDatabaseManager;
    private TimeManager timeManager;

    public PlayerQuitListener(TimerDatabaseManager timerDatabaseManager, TimeManager timeManager) {
        this.timeManager = timeManager;
        this.timerDatabaseManager = timerDatabaseManager;
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) throws SQLException {
        event.setQuitMessage(null);
        Player player = event.getPlayer();

        Time currentTime = timeManager.getNewTime(player); // Die Zeit die der Spieler auf dem Server war
        timerDatabaseManager.saveToDatabase(player, currentTime);

        PlayerJoinTimeMap sharedMap = PlayerJoinTimeMap.getInstance();
        sharedMap.removePlayer(player);
    }
}
