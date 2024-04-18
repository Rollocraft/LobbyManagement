package de.rollocraft.lobbySystem.Minecraft.Commands;

import de.rollocraft.lobbySystem.Minecraft.Manager.WorldManager;
import de.rollocraft.lobbySystem.Minecraft.Threads.MapCreation;
import de.rollocraft.lobbySystem.Minecraft.Threads.WorldCreation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WorldCommand implements CommandExecutor, TabCompleter {
    private WorldManager worldManager;
    public WorldCommand(WorldManager worldManager) {
        this.worldManager = worldManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("Please specify a sub-command: create, delete, list");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "create":
                if (args.length < 2) {
                    player.sendMessage("Please specify what to create: world, map");
                    return true;
                }
                if (args.length < 3) {
                    player.sendMessage("Please specify a name for the creation.");
                    return true;
                }
                switch (args[1].toLowerCase()) {
                    case "world":
                        WorldCreation worldCreation = new WorldCreation(player, args[2]);
                        worldCreation.start();
                        break;
                    case "map":
                        MapCreation mapCreation = new MapCreation(player, args[2]);
                        mapCreation.start();
                        break;
                    default:
                        player.sendMessage("Unknown creation type. Please use: world, map");
                        break;
                }
                break;
            case "delete":
                if(worldManager.deleteWorld(args[1])){
                    player.sendMessage("World deleted successfully!");
                } else {
                    player.sendMessage("World found but could not be deleted. Please try again.");
                }
                break;
            case "list":
                worldManager.listAllWorlds().forEach(player::sendMessage);
                break;
            default:
                player.sendMessage("Unknown sub-command. Please use: create, delete, list");
                break;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> subCommands = new ArrayList<>();
        if (args.length == 1) {
            subCommands.add("create");
            subCommands.add("delete");
            subCommands.add("list");
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("create")) {
                subCommands.add("world");
                subCommands.add("map");
            } else if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("delete")) {
                subCommands.addAll(worldManager.listAllWorlds());
            }
        }
        return subCommands;
    }
}