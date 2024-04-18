package de.rollocraft.lobbySystem.Minecraft.Commands;

import de.rollocraft.lobbySystem.Minecraft.Manager.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeleportCommand implements CommandExecutor, TabCompleter {
    private WorldManager worldManager;

    public TeleportCommand(WorldManager worldManager) {
        this.worldManager = worldManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("teleport")) {
            if (args.length == 0) {
                sender.sendMessage("Available worlds: " + String.join(", ", worldManager.listAllWorlds()));
                return true;
            } else if (args.length < 2) {
                sender.sendMessage("You must specify both a player and a world.");
                return false;
            } else {
                Player targetPlayer = Bukkit.getPlayer(args[0]);
                if (targetPlayer != null) {
                    World targetWorld = Bukkit.getWorld(args[1]);
                    if (targetWorld != null) {
                        targetPlayer.teleport(targetWorld.getSpawnLocation());
                        sender.sendMessage(ChatColor.BLUE + "Teleported " + targetPlayer.getName() + " to " + targetWorld.getName() + "!");
                    } else {
                        sender.sendMessage("World not found");
                    }
                } else {
                    sender.sendMessage("Player not found");
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 1) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                suggestions.add(player.getName());
            }
        } else if (args.length == 2) {
            suggestions.addAll(worldManager.listAllWorlds());
        }
        return suggestions;
    }
}
