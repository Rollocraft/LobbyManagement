package de.rollocraft.lobbySystem.Commands;

import de.rollocraft.lobbySystem.Utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender != null && sender.hasPermission("lobbySystem.command.hub")) {
            if (args[0].equalsIgnoreCase("hub")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    //CHnage to velocity
                } else {
                    Message.returnMessage(sender, "Du bist kein Spieler!");
                }
            }
        } else {
            Message.returnMessage(sender, "Du hast nciht alle Argumente angegeben. /send <Spieler> <Server>");
        }
        return true;
    }
}

