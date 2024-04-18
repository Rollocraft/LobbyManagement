package de.rollocraft.lobbySystem.Minecraft.Utils.Maps;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BuildMap {
    private static BuildMap instance;
    private final List<Player> buildingPlayers;

    private BuildMap() {
        buildingPlayers = new ArrayList<>();
    }

    public static synchronized BuildMap getInstance() {
        if (instance == null) {
            instance = new BuildMap();
        }
        return instance;
    }

    public void addToBuild(Player player) {
        buildingPlayers.add(player);
    }

    public void removeFromBuild(Player player) {
        buildingPlayers.remove(player);
    }

    public boolean canBuild(Player player) {
        return buildingPlayers.contains(player);
    }

}
