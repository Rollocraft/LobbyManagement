package de.rollocraft.lobbySystem.Minecraft.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("lobbySystem.command.vanish")) {
                if (player.isInvisible()) {
                    player.setInvisible(false);
                    player.sendMessage("You are now visible");
                } else {
                    player.setInvisible(true);
                    player.sendMessage("You are now invisible");
                }
            } else {
                player.sendMessage("You do not have permission to use this command");
            }
        }
        return true;
    }
}