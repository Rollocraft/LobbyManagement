package de.rollocraft.pridetuvelobby.Commands;

import de.rollocraft.pridetuvelobby.Database.DatabaseMain;
import de.rollocraft.pridetuvelobby.Threads.Timer;
import de.rollocraft.pridetuvelobby.Utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class StatusCommand implements CommandExecutor, TabCompleter {
    private Timer timer;
    private DatabaseMain databaseMain;
    public StatusCommand(Timer timer, DatabaseMain databaseMain) {
        this.timer = timer;
        this.databaseMain = databaseMain;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("threads")) {
                boolean isRunning = timer.isRunning();
                Message.returnMessage(sender ,"Thread Timer: #### " + (isRunning ? "an" : "aus"));
            } else if (args[0].equalsIgnoreCase("database")) {
                boolean isConnected = databaseMain.isConnected();
               Message.returnMessage(sender, "Database: #### " + (isConnected ? "verbunden" : "nicht verbunden"));
            } else {
                Message.returnMessage(sender,"Unbekannter Befehl.");
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("threads");
            completions.add("database");
        }
        return completions;
    }
}
