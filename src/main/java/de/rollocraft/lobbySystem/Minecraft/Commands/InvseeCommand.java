package de.rollocraft.lobbySystem.Minecraft.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvseeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("lobbySystem.command.invsee")) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        player.openInventory(target.getInventory());
                    } else {
                        player.sendMessage("Player not found.");
                    }
                } else {
                    player.sendMessage("Usage: /invsee <player>");
                }
            } else {
                player.sendMessage("You do not have permission to use this command.");
            }
        } else {
            sender.sendMessage("Only players can use this command.");
        }
        return true;
    }
}