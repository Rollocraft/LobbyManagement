package de.rollocraft.pridetuvelobby.Commands;

import de.rollocraft.pridetuvelobby.Database.DatabaseMain;
import de.rollocraft.pridetuvelobby.Threads.Timer;
import de.rollocraft.pridetuvelobby.Threads.Update;
import de.rollocraft.pridetuvelobby.Utils.BungeeCord.Bungee;
import de.rollocraft.pridetuvelobby.Utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConnectToCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender != null && sender.hasPermission("pridetuvelobby.sendto")) {
            if (args.length > 2) {
                if (args[0].equalsIgnoreCase("send")) {
                    Player player = Bukkit.getPlayer(args[1]);
                    if (player != null) {
                        Bungee.connectToServer(player, args[2], true);
                    } else {
                        Message.returnMessage(sender, "Spieler nicht gefunden.");
                    }
                } else {
                    Message.returnMessage(sender, "Unbekannter Befehl.");
                }
            } else {
                Message.returnMessage(sender, "Du hast nciht alle Argumente angegeben. /send <Spieler> <Server>");
            }
            return true;
        }
        Message.returnMessage(sender, "Du hast keine Berechtigung!");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("send");
        } else if (args.length == 2) {
            // Add player names to completions
            for (Player player : Bukkit.getOnlinePlayers()) {
                completions.add(player.getName());
            }
        } else if (args.length == 3) {
            // Add server names to completions
            // Replace this with your actual server names
            completions.addAll(Arrays.asList("server1", "server2", "server3"));
        }
        return completions;
    }
}
