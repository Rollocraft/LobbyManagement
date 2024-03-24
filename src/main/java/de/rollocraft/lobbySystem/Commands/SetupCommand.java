package de.rollocraft.lobbySystem.Commands;


import de.rollocraft.lobbySystem.Manager.SetupPvpKitManager;
import de.rollocraft.lobbySystem.Manager.SetupPvpMapManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetupCommand implements CommandExecutor, TabCompleter {
    private SetupPvpMapManager setupMapManager;
    private SetupPvpKitManager setupKitManager;

    public SetupCommand(SetupPvpMapManager setupMapManager, SetupPvpKitManager setupKitManager) {
        this.setupMapManager = setupMapManager;
        this.setupKitManager = setupKitManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        //Npc

        if (args.length == 1 && args[0].equalsIgnoreCase("npc")) {
            player.sendMessage("Please specify a sub-command: npc create/remove");
            return true;
        }

        if (args[1].equalsIgnoreCase("create")) {
            //npcManager.createNpc(player.getLocation());
            player.sendMessage(ChatColor.GREEN + "NPC erstellt.");
        } else if (args[1].equalsIgnoreCase("remove")) {
            //npcManager.removeNpc(player.getLocation());
            player.sendMessage(ChatColor.RED + "NPC entfernt.");
        }

        //PVP

        if (args.length == 1 && args[0].equalsIgnoreCase("pvp")) {
            player.sendMessage("Please specify a sub-command: pvp kit/map");
            return true;
        }

        if (args[1].equalsIgnoreCase("kit")) {
            setupKitManager.startSetup(player);
        } else if (args[1].equalsIgnoreCase("map")) {
            setupMapManager.startSetup(player);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("pvp", "npc");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("pvp")) {
            return Arrays.asList("kit", "map");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("npc")) {
            return Arrays.asList("create", "remove");
        }

        return Collections.emptyList();
    }
}
