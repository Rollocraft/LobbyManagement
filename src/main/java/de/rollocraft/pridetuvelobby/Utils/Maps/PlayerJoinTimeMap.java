package de.rollocraft.pridetuvelobby.Utils.Maps;

import de.rollocraft.pridetuvelobby.Objects.Time;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerJoinTimeMap {
    private static PlayerJoinTimeMap instance;
    private final Map<Player, Time> playerTimes;

    private PlayerJoinTimeMap() {
        playerTimes = new HashMap<>();
    }

    public static synchronized PlayerJoinTimeMap getInstance() {
        if (instance == null) {
            instance = new PlayerJoinTimeMap();
        }
        return instance;
    }
    public void removePlayer(Player player) {
        playerTimes.remove(player);
    }

    public Time getTimeForPlayer(Player player) {
        return playerTimes.get(player);
    }
    public void setTimeForPlayer(Player player, Time time) {
        playerTimes.put(player, time);
    }
}