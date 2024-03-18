package de.rollocraft.lobbySystem.Listener;

import de.rollocraft.lobbySystem.Database.Mysql.Tables.TimerDatabaseManager;
import de.rollocraft.lobbySystem.Manager.PermissionManager;
import de.rollocraft.lobbySystem.Manager.TimeManager;
import de.rollocraft.lobbySystem.Objects.Time;
import de.rollocraft.lobbySystem.Utils.Maps.PlayerJoinTimeMap;
import de.rollocraft.lobbySystem.Utils.Maps.PlayerSecretKeyMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class PlayerQuitListener implements Listener {
    private TimerDatabaseManager timerDatabaseManager;
    private TimeManager timeManager;
    private PermissionManager permissionManager;

    public PlayerQuitListener(TimerDatabaseManager timerDatabaseManager, TimeManager timeManager, PermissionManager permissionManager) {
        this.permissionManager = permissionManager;
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

        PlayerSecretKeyMap secretKeyMap = PlayerSecretKeyMap.getInstance();
        secretKeyMap.removePlayer(player);
    }
}
