package de.rollocraft.pridetuvelobby;

/*
    *  PrideTuveLobby
    * Version: 0.0.1
    * Author: Rollocraft
    * 09.03.2024
 */

import de.rollocraft.pridetuvelobby.Commands.StatusCommand;
import de.rollocraft.pridetuvelobby.Database.DatabaseMain;
import de.rollocraft.pridetuvelobby.Threads.Timer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main instance;
    private DatabaseMain databaseMain;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        //Assingments
        Timer timer = new Timer();
        databaseMain = new DatabaseMain();
        StatusCommand statusCommand = new StatusCommand(timer, databaseMain);

        //...
        timer.start();
        databaseMain.connectToDatabase();

        this.getCommand("status").setExecutor(statusCommand);
        this.getCommand("status").setTabCompleter(statusCommand);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getInstance() {
        return instance;
    }
}
