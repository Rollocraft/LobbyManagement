package de.rollocraft.lobbySystem.Minecraft.Utils.Maps;

import de.rollocraft.lobbySystem.Minecraft.Objects.Parkour;
import de.rollocraft.lobbySystem.Minecraft.Objects.ParkourPlayer;
import de.rollocraft.lobbySystem.Minecraft.Objects.Time;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ParkourMap {
    private static ParkourMap instance;
    private final Map<Player, ParkourPlayer> parkourPlayers;

    private ParkourMap() {
        parkourPlayers = new HashMap<>();
    }

    public static synchronized ParkourMap getInstance() {
        if (instance == null) {
            instance = new ParkourMap();
        }
        return instance;
    }

    public void addParkour(Player player, Parkour parkour) {
        parkourPlayers.put(player, new ParkourPlayer(parkour, new Time(0, 0, 0,0), 0));
    }
    public void removeParkour(Player player) {
        parkourPlayers.remove(player);
    }
    public boolean isInParkour(Player player) {
        return parkourPlayers.containsKey(player);
    }
    public Set<Player> getAllPlayers() {
        return parkourPlayers.keySet();
    }
    public Time getTime(Player player) {
        return parkourPlayers.get(player).getTime();
    }
    public void updateTime(Player player, Time time) {
        parkourPlayers.get(player).updateTime(time);
    }
    public int getCheckpoint(Player player) {
        return parkourPlayers.get(player).getCheckpoint();
    }
    public int getResetHigh(Player player) {
        return parkourPlayers.get(player).getResetHigh();
    }
    public Parkour getParkour(Player player) {
        return parkourPlayers.get(player).getParkour();
    }
    public void setCheckpoint(Player player, int checkpoint) {
        parkourPlayers.get(player).setCheckpoint(checkpoint);
    }
}
