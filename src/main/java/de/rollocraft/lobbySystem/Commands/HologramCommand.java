package de.rollocraft.lobbySystem.Commands;

import de.rollocraft.lobbySystem.Manager.HologramManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HologramCommand implements CommandExecutor, TabCompleter {
    private HologramManager hologramManager;

    public HologramCommand(HologramManager hologramManager) {
        this.hologramManager = hologramManager;
    }

   @Override
public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (command.getName().equalsIgnoreCase("hologram")) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("remove")) {
                    int result = hologramManager.removeHologram(player.getLocation());
                    switch (result) {
                        case 0:
                            player.sendMessage(ChatColor.GREEN + "Hologram removed");
                            break;
                        case 1:
                            player.sendMessage(ChatColor.RED + "Hologram not found");
                            break;
                        case 2:
                            player.sendMessage(ChatColor.RED +"Some error occurred, this shouldn't happen. Please report it to the Devs!");
                            break;
                    }
                } else if (args[0].equalsIgnoreCase("create")) {
                    if (args.length > 1) {
                        String[] hologramText = Arrays.copyOfRange(args, 1, args.length);
                        hologramManager.createHologram(((Player) sender).getLocation(), String.join(" ", hologramText).split("\\|"));
                        player.sendMessage("Hologram created");
                    } else {
                        player.sendMessage("Usage: /hologram create <text>");
                        return false;
                    }
                } else if (args[0].equalsIgnoreCase("list")) {
                    player.sendMessage("Holograms:");
                } else {
                    player.sendMessage("Usage: /hologram <create|remove|list> [text]");
                    return false;
                }
                return true;
            } else {
                player.sendMessage("Usage: /hologram <create|remove|list> [text]");
                return false;
            }
        } else {
            sender.sendMessage("Only players can use this command");
            return false;
        }
    }
    return false;
}

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("hologram")) {
            if (args.length == 1) {
                List<String> options = new ArrayList<>();
                if ("create".startsWith(args[0].toLowerCase())) {
                    options.add("create");
                }
                if ("remove".startsWith(args[0].toLowerCase())) {
                    options.add("remove");
                }
                if ("list".startsWith(args[0].toLowerCase())) {
                    options.add("list");
                }
                return options;
            }
        }
        return null;
    }
}