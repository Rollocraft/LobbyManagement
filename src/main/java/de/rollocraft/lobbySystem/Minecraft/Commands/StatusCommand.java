package de.rollocraft.lobbySystem.Minecraft.Commands;

import de.rollocraft.lobbySystem.Minecraft.Database.Mysql.DatabaseMain;
import de.rollocraft.lobbySystem.Minecraft.Database.Sql.SqlMain;
import de.rollocraft.lobbySystem.Main;
import de.rollocraft.lobbySystem.Minecraft.Threads.Update;
import de.rollocraft.lobbySystem.Minecraft.Threads.Timer;
import de.rollocraft.lobbySystem.Minecraft.Utils.Message;
import org.bukkit.Server;
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
        if (sender != null && sender.hasPermission("lobbySystem.command.status")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("threads")) {

                    Message.returnMessage(sender, "#####################");
                    Message.returnMessage(sender, "# Thread Timer: " + (isLastMessageOlderThanTwoSeconds(timer.getLastTime()) ? "an       #" : "aus  #"));
                    Message.returnMessage(sender, "# Thread Update: " + (isLastMessageOlderThanTwoSeconds(update.getLastTime()) ? "an     #" : "aus #"));
                    Message.returnMessage(sender, "#####################");
                } else if (args[0].equalsIgnoreCase("database")) {
                    boolean isMySqlConnected = databaseMain.isConnected();
                    boolean isSqlConnected = sqlMain.isConnected();
                    Message.returnMessage(sender, "Mysql: #### " + (isMySqlConnected ? "verbunden" : "nicht verbunden"));
                    Message.returnMessage(sender, "Sql: #### " + (isSqlConnected ? "verbunden" : "nicht verbunden"));
                } else if (args[0].equalsIgnoreCase("ram")) {
                    Message.returnMessage(sender, "Ram: " + Runtime.getRuntime().freeMemory() / 1024 / 1024 + "MB / " + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "MB");
                } else if (args[0].equalsIgnoreCase("tps")) {
                    Message.returnMessage(sender, "Tps: " + Main.getInstance().getServer().getTPS());
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
            completions.add("ram");
        }
        return completions;
    }
    private boolean isLastMessageOlderThanTwoSeconds(long getLastMessageTime) {
        long timeSinceLastMessage = System.currentTimeMillis() - getLastMessageTime;
        return timeSinceLastMessage > 2000; // 2 sec wegen irgendwelchen delays oder sonstigem
    }
}
