package de.rollocraft.pridetuvelobby;

/*
    *  PrideTuveLobby
    * Version: 0.0.1
    * Author: Rollocraft
    * 09.03.2024
 */

import de.rollocraft.pridetuvelobby.Commands.StatusCommand;
import de.rollocraft.pridetuvelobby.Database.DatabaseMain;
import de.rollocraft.pridetuvelobby.Database.Tables.TimerDatabaseManager;
import de.rollocraft.pridetuvelobby.Listener.HubProtection.*;
import de.rollocraft.pridetuvelobby.Listener.PlayerInteractListener;
import de.rollocraft.pridetuvelobby.Listener.PlayerJoinListener;
import de.rollocraft.pridetuvelobby.Manager.InventoryManager;
import de.rollocraft.pridetuvelobby.Manager.ScoreboardManager;
import de.rollocraft.pridetuvelobby.Manager.TablistManager;
import de.rollocraft.pridetuvelobby.Manager.TimeManager;
import de.rollocraft.pridetuvelobby.Threads.Update;
import de.rollocraft.pridetuvelobby.Threads.Timer;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Main extends JavaPlugin {
    private static Main instance;
    private DatabaseMain databaseMain;
    private TablistManager tablistManager;
    private ScoreboardManager scoreboardManager;
    private TimerDatabaseManager timerDatabaseManager;
    private TimeManager timeManager;
    private Update update;
    private InventoryManager inventoryManager;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        //Assingments
        Timer timer = new Timer();
        databaseMain = new DatabaseMain();
        tablistManager = new TablistManager();

        timerDatabaseManager = new TimerDatabaseManager(databaseMain.getConnection());
        try {
            timerDatabaseManager.createTimerTableIfNotExists();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        timeManager = new TimeManager(timerDatabaseManager, timer);
        inventoryManager = new InventoryManager();
        scoreboardManager = new ScoreboardManager(timeManager);
        update = new Update(scoreboardManager);
        StatusCommand statusCommand = new StatusCommand(timer, databaseMain, update);

        //...
        timer.start();
        update.start();
        databaseMain.connectToDatabase();

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(tablistManager, timer, scoreboardManager), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(inventoryManager), this);
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new DamageListener(), this);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new HungerListener(), this);
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
        getServer().getPluginManager().registerEvents(new WeatherListener(), this);

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
