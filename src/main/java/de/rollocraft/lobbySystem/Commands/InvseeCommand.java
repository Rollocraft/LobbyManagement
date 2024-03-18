package de.rollocraft.lobbySystem.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InvseeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("inv")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        Inventory targetInventory = Bukkit.createInventory(player, target.getInventory().getSize(), "Invsee - " + target.getName());
                        targetInventory.setContents(target.getInventory().getContents());
                        player.openInventory(targetInventory);
                        return true;
                    } else {
                        player.sendMessage("Player not found");
                        return false;
                    }
                } else {
                    player.sendMessage("Usage: /inv <player>");
                    return false;
                }
            } else {
                sender.sendMessage("Only players can use this command");
                return false;
            }
        }
        return false;
    }
}