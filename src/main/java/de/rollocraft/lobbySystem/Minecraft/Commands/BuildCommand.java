package de.rollocraft.lobbySystem.Minecraft.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.rollocraft.lobbySystem.Minecraft.Utils.Maps.BuildMap;

public class BuildCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            boolean canBuild = BuildMap.getInstance().canBuild(player);
            if (canBuild) {
                BuildMap.getInstance().removeFromBuild(player);
                player.sendMessage("You now can't build anymore!");
            } else {
                BuildMap.getInstance().addToBuild(player);
                player.sendMessage("You now can build!");
            }
        }
        return true;
    }
}