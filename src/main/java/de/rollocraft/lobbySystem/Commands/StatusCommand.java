package de.rollocraft.lobbySystem.Commands;

import de.rollocraft.lobbySystem.Database.Mysql.DatabaseMain;
import de.rollocraft.lobbySystem.Database.Sql.SqlMain;
import de.rollocraft.lobbySystem.Threads.Update;
import de.rollocraft.lobbySystem.Threads.Timer;
import de.rollocraft.lobbySystem.Utils.Message;
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
    private SqlMain sqlMain;

    public StatusCommand(Timer timer, DatabaseMain databaseMain, Update update, SqlMain sqlMain) {
        this.sqlMain = sqlMain;
        this.timer = timer;
        this.databaseMain = databaseMain;
        this.update = update;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender != null && sender.hasPermission("pridetuvelobby.status")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("threads")) {
                    boolean isTimerRunning = timer.isRunning();
                    boolean isUpdateRunning = update.isRunning();
                    Message.returnMessage(sender, "#####################");
                    Message.returnMessage(sender, "# Thread Timer: " + (isTimerRunning ? "an       #" : "aus  #"));
                    Message.returnMessage(sender, "# Thread Update: " + (isUpdateRunning ? "an     #" : "aus #"));
                    Message.returnMessage(sender, "#####################");
                } else if (args[0].equalsIgnoreCase("database")) {
                    boolean isMySqlConnected = databaseMain.isConnected();
                    boolean isSqlConnected = sqlMain.isConnected();
                    Message.returnMessage(sender, "Mysql: #### " + (isMySqlConnected ? "verbunden" : "nicht verbunden"));
                    Message.returnMessage(sender, "Sql: #### " + (isSqlConnected ? "verbunden" : "nicht verbunden"));
                } else if (args[0].equalsIgnoreCase("tps")) {
                    Message.returnMessage(sender, "Tps: !Todo!");
                } else if (args[0].equalsIgnoreCase("ram")) {
                    Message.returnMessage(sender, ": " + Runtime.getRuntime().freeMemory() / 1024 / 1024 + "MB / " + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "MB");
                } else {
                    Message.returnMessage(sender, "Unbekannter Befehl.");
                }
            }
            return true;
        }
        Message.returnMessage(sender, "Du hast keine Berechtigung!");
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
