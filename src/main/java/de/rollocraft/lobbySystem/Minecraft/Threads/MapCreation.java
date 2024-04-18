package de.rollocraft.lobbySystem.Minecraft.Threads;

import de.rollocraft.lobbySystem.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

import java.io.IOException;
import java.util.Random;

public class MapCreation extends Thread {
    private final Player player;
    private final String worldName;

    public MapCreation(Player player, String worldName) {
        this.player = player;
        this.worldName = worldName;
    }

    @Override
    public void run() {
        try {
            // Start a separate process to create the world
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "MapCreator.jar", worldName);
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
                    creator.generator(new ChunkGenerator() {
                        @Override
                        public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
                            ChunkData chunk = createChunkData(world);
                            if (x == 0 && z == 0) {
                                chunk.setBlock(0, 20, 0, Material.BEDROCK);
                            }
                            return chunk;
                        }
                    });
                    World world = creator.createWorld();
                    world.setSpawnLocation(new Location(world, 0, 20, 0));
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