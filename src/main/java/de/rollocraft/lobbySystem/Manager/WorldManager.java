package de.rollocraft.lobbySystem.Manager;

import de.rollocraft.lobbySystem.Database.Sql.Tabels.MapSqlManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class WorldManager {
    private MapSqlManager mapSqlManager;

    public WorldManager(MapSqlManager mapSqlManager) {
        this.mapSqlManager = mapSqlManager;
    }

    public boolean deleteWorld(String worldName) {
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            Bukkit.unloadWorld(world, true);
            Bukkit.getWorld(worldName).getWorldFolder().delete();
            return true;
        }
        return false;
    }

    public List<String> listAllWorlds() {
        File serverDirectory = Bukkit.getServer().getWorldContainer();
        File[] worldDirectories = serverDirectory.listFiles(File::isDirectory);

        List<String> excludedWorlds = Arrays.asList("config", "cache", "logs", "plugins", "versions", "libraries");

        if (worldDirectories != null) {
            for (File worldDirectory : worldDirectories) {
                String worldName = worldDirectory.getName();
                if (excludedWorlds.contains(worldName)) {
                    continue;
                }
                World world = Bukkit.getWorld(worldName);
                if (world == null) {
                    Bukkit.createWorld(new WorldCreator(worldName));
                }
            }
        }
        return Arrays.stream(worldDirectories).map(File::getName).filter(name -> !excludedWorlds.contains(name)).collect(Collectors.toList());
    }

    public Map<String, String> checkWorlds() {
        Map<String, String> nonExistentWorlds = new HashMap<>();
        for (String mapName : mapSqlManager.getAllMaps()) {
            String worldName = mapSqlManager.getWorldName(mapName);
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                nonExistentWorlds.put(worldName, mapName);
            }
        }
        return nonExistentWorlds;
    }

    public boolean mapExist(String mapName) {
        if (mapName != null) {
            String worldName = mapSqlManager.getWorldName(mapName);
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                return false;
            }
            return true;
        }
        return false;
    }
}