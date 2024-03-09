package de.rollocraft.pridetuvelobby.Commands;

import de.rollocraft.pridetuvelobby.Database.DatabaseMain;
import de.rollocraft.pridetuvelobby.Threads.Update;
import de.rollocraft.pridetuvelobby.Threads.Timer;
import de.rollocraft.pridetuvelobby.Utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class StatusCommand implements CommandExecutor, TabCompleter {
    private Timer timer;
    private DatabaseMain databaseMain;
    private Update update;

    public StatusCommand(Timer timer, DatabaseMain databaseMain, Update update) {
        this.timer = timer;
        this.databaseMain = databaseMain;
        this.update = update;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("threads")) {
                boolean isTimerRunning = timer.isRunning();
                boolean isUpdateRunning = update.isRunning();
                Message.returnMessage(sender, "#####################");
                Message.returnMessage(sender, "# Thread Timer: " + (isTimerRunning ? "an    #" : "aus  #"));
                Message.returnMessage(sender, "# Thread Update: " + (isUpdateRunning ? "an  #" : "aus #"));
                Message.returnMessage(sender, "#####################");
            } else if (args[0].equalsIgnoreCase("database")) {
                boolean isConnected = databaseMain.isConnected();
                Message.returnMessage(sender, "Database: #### " + (isConnected ? "verbunden" : "nicht verbunden"));
            } else if (args[0].equalsIgnoreCase("tps")) {
                Message.returnMessage(sender, "Tps: !Todo!" );
            } else if (args[0].equalsIgnoreCase("ram")) {
                Message.returnMessage(sender, ": " + Runtime.getRuntime().freeMemory() / 1024 / 1024 + "MB / " + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "MB");
            } else {
                Message.returnMessage(sender, "Unbekannter Befehl.");
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
            completions.add("tps");
            completions.add("ram");
        }
        return completions;
    }
}
