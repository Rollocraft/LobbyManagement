package de.rollocraft.pridetuvelobby.Manager;

import de.rollocraft.pridetuvelobby.Database.Tables.TimerDatabaseManager;
import de.rollocraft.pridetuvelobby.Objects.Time;
import de.rollocraft.pridetuvelobby.Threads.Timer;
import de.rollocraft.pridetuvelobby.Utils.Maps.PlayerJoinTimeMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class TimeManager {
    private Timer timer;
    private TimerDatabaseManager timerDatabaseManager;

    public TimeManager(TimerDatabaseManager timerDatabaseManager, Timer timer) {
        this.timerDatabaseManager = timerDatabaseManager;
        this.timer = timer;
    }

    public Time getTime(Player player) {
        try {
            PlayerJoinTimeMap playerJoinTimeMap = PlayerJoinTimeMap.getInstance();
            Time playerTime = playerJoinTimeMap.getTimeForPlayer(player);
            if (playerTime == null) {
                playerTime = new Time(0, 0, 0, 0);
            }
            Time oldtime = timerDatabaseManager.getTime(player);
            Time newtime = timer.getTime();

            Time subtractedTime = newtime.subtract(playerTime);
            Time finalTime = subtractedTime.add(oldtime);

            return finalTime;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Time getNewTime(Player player) {
        PlayerJoinTimeMap playerJoinTimeMap = PlayerJoinTimeMap.getInstance();
        Time playerTime = playerJoinTimeMap.getTimeForPlayer(player);
        if (playerTime == null) {
            playerTime = new Time(0, 0, 0, 0);
        }
        Time newtime = timer.getTime();

        Time finalTime = newtime.subtract(playerTime);

        return finalTime;

    }

    public Time getCurrentTime() {
        return timer.getTime();
    }
}
