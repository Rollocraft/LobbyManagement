package de.rollocraft.lobbySystem.Minecraft.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("lobbySystem.command.fly")) {
                boolean canFly = player.getAllowFlight();
                player.setAllowFlight(!canFly);
                player.sendMessage("Fly mode " + (!canFly ? "enabled" : "disabled"));
            } else {
                player.sendMessage("You do not have permission to use this command");
            }
        }
        return true;
    }
}