package de.rollocraft.pridetuvelobby.Manager;

import de.rollocraft.pridetuvelobby.Database.Tables.TimerDatabaseManager;
import de.rollocraft.pridetuvelobby.Objects.Time;
import de.rollocraft.pridetuvelobby.Threads.Timer;
import de.rollocraft.pridetuvelobby.Utils.Maps.PlayerJoinTimeMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TimeManager{
    private  Timer timer;
    private  Map<Player, Time> playerTimes = new HashMap<>();
    private  TimerDatabaseManager timerDatabaseManager;
    public TimeManager(TimerDatabaseManager timerDatabaseManager, Timer timer) {
        this.timerDatabaseManager = timerDatabaseManager;
        this.timer = timer;
    }
    public Time getTime(Player player) {
        try {
            PlayerJoinTimeMap sharedMap = PlayerJoinTimeMap.getInstance();

            Time joinedTime = sharedMap.getTimeForPlayer(player);
            Time oldtime = timerDatabaseManager.getTime(player);
            Time newtime = timer.getTime();

            Time subtractedTime = newtime.subtract(joinedTime);
            Time finalTime = subtractedTime.add(oldtime);

            return finalTime;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
