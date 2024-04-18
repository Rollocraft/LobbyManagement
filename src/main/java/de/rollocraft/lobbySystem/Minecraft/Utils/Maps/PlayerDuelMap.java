package de.rollocraft.lobbySystem.Minecraft.Utils.Maps;

import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;

public class PlayerDuelMap {
    private static PlayerDuelMap instance;
    private final Map<Player, Player> duelingPlayers;

    private PlayerDuelMap() {
        duelingPlayers = new HashMap<>();
    }

    public static synchronized PlayerDuelMap getInstance() {
        if (instance == null) {
            instance = new PlayerDuelMap();
        }
        return instance;
    }

    public void addDuel(Player player1, Player player2) {
        duelingPlayers.put(player1, player2);
        duelingPlayers.put(player2, player1);
    }

    public void removeDuel(Player player1, Player player2) {
        duelingPlayers.remove(player1);
        duelingPlayers.remove(player2);
    }

    public boolean isPlayerInDuel(Player player) {
        return duelingPlayers.containsKey(player);
    }

    public Player getDuelOpponent(Player player) {
        return duelingPlayers.get(player);
    }
}

