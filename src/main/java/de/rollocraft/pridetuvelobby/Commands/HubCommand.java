package de.rollocraft.pridetuvelobby.Commands;

import de.rollocraft.pridetuvelobby.Utils.BungeeCord.Bungee;
import de.rollocraft.pridetuvelobby.Utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender != null && sender.hasPermission("pridetuvelobby.hub")) {
            if (args[0].equalsIgnoreCase("hub")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    Bungee.connectToServer(player, "hub", true);
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

