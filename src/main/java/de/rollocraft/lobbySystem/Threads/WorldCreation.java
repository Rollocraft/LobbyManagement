package de.rollocraft.lobbySystem.Threads;


import de.rollocraft.lobbySystem.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.IOException;

public class WorldCreation extends Thread {
    private final Player player;
    private final String worldName;

    public WorldCreation(Player player, String worldName) {
        this.player = player;
        this.worldName = worldName;
    }

    @Override
    public void run() {
        try {
            // Start a separate process to create the world
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "WorldCreator.jar", worldName);
            Process process = processBuilder.start();

            // Create a new thread to wait for the process to finish
            new Thread(() -> {
                try {
                    process.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Load the world into the server
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                    WorldCreator creator = new WorldCreator(worldName);
                    World world = creator.createWorld();
                    player.sendMessage(worldName + " created successfully!");
                    player.sendMessage("teleporting to spawn...");
                    player.teleport(world.getSpawnLocation());
                });
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}