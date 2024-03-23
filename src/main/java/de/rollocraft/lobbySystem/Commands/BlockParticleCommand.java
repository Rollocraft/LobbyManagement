package de.rollocraft.lobbySystem.Commands;

import de.rollocraft.lobbySystem.Manager.BlockParticleManager;
import de.rollocraft.lobbySystem.Objects.Effects;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockParticleCommand implements CommandExecutor, TabCompleter {
    private BlockParticleManager blockParticleManager;

    public BlockParticleCommand(BlockParticleManager blockParticleManager) {
        this.blockParticleManager = blockParticleManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location location = player.getLocation();
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("create")) {
                    String particleName = args[1].toUpperCase();
                    Particle particle;
                    int intensity = Integer.parseInt(args[2]);
                    try {
                        particle = Particle.valueOf(particleName);
                    } catch (IllegalArgumentException e) {
                        player.sendMessage("Invalid particle type!");
                        return true;
                    }

                    Effects effect;
                    if (args.length == 4) {
                        String color = args[3];
                        effect = new Effects(location, color, particle, intensity);
                    } else {
                        effect = new Effects(location, null, particle, intensity);
                    }

                    blockParticleManager.createBlockParticle(effect);
                    sender.sendMessage(ChatColor.GREEN + "Created particle at your location!");
                } else if (args[0].equalsIgnoreCase("delete")) {
                    Bukkit.getLogger().info("Calling deleteBlockParticle"); // Added log output
                    blockParticleManager.deleteBlockParticle(location);
                    sender.sendMessage(ChatColor.BLUE + "Deleted all particles in a 2 block radius!");
                }
            } else {
                player.sendMessage("Too few arguments!");
            }

        }
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return getAllOptions();
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("create")) {
            return getAllParticleNames();
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("create")) {
            return intensity();
        }
        if (args.length == 4 && args[0].equalsIgnoreCase("create") && args[1].equalsIgnoreCase("REDSTONE")) {
            return createColorList();
        }
        return null;
    }

    private List<String> intensity() {
        List<String> intensity = new ArrayList<>();
        intensity.add("<intensity>");
        return intensity;
    }

    private List<String> getAllParticleNames() {
        List<String> particleNames = new ArrayList<>();
        for (Particle particle : Particle.values()) {
            particleNames.add(particle.name());
        }
        return particleNames;
    }

    private List<String> createColorList() {
        Map<String, Color> colorMap = createColorMap();
        return new ArrayList<>(colorMap.keySet());
    }

    private Map<String, Color> createColorMap() {
        Map<String, Color> colorMap = new HashMap<>();
        colorMap.put("RED", Color.RED);
        colorMap.put("BLUE", Color.BLUE);
        colorMap.put("GREEN", Color.GREEN);
        colorMap.put("YELLOW", Color.YELLOW);
        colorMap.put("AQUA", Color.AQUA);
        colorMap.put("BLACK", Color.BLACK);
        colorMap.put("FUCHSIA", Color.FUCHSIA);
        colorMap.put("GRAY", Color.GRAY);
        colorMap.put("LIME", Color.LIME);
        colorMap.put("MAROON", Color.MAROON);
        colorMap.put("NAVY", Color.NAVY);
        colorMap.put("OLIVE", Color.OLIVE);
        colorMap.put("ORANGE", Color.ORANGE);
        colorMap.put("PURPLE", Color.PURPLE);
        colorMap.put("SILVER", Color.SILVER);
        colorMap.put("TEAL", Color.TEAL);
        colorMap.put("WHITE", Color.WHITE);

        return colorMap;
    }

    private List<String> getAllOptions() {
        List<String> dustOptions = new ArrayList<>();
        dustOptions.add("create");
        dustOptions.add("delete");
        return dustOptions;
    }
}
