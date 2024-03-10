package de.rollocraft.pridetuvelobby;

/*
    *  PrideTuveLobby
    * Version: 0.0.1
    * Author: Rollocraft
    * 09.03.2024
 */

import de.rollocraft.pridetuvelobby.Commands.ConnectToCommand;
import de.rollocraft.pridetuvelobby.Commands.StatusCommand;
import de.rollocraft.pridetuvelobby.Commands.HubCommand;
import de.rollocraft.pridetuvelobby.Database.DatabaseMain;
import de.rollocraft.pridetuvelobby.Database.Tables.TimerDatabaseManager;
import de.rollocraft.pridetuvelobby.Listener.HubProtection.*;
import de.rollocraft.pridetuvelobby.Listener.PlayerChatListener;
import de.rollocraft.pridetuvelobby.Listener.PlayerInteractListener;
import de.rollocraft.pridetuvelobby.Listener.PlayerJoinListener;
import de.rollocraft.pridetuvelobby.Listener.PlayerQuitListener;
import de.rollocraft.pridetuvelobby.Manager.InventoryManager;
import de.rollocraft.pridetuvelobby.Manager.ScoreboardManager;
import de.rollocraft.pridetuvelobby.Manager.TablistManager;
import de.rollocraft.pridetuvelobby.Manager.TimeManager;
import de.rollocraft.pridetuvelobby.Threads.Update;
import de.rollocraft.pridetuvelobby.Threads.Timer;
import de.rollocraft.pridetuvelobby.Utils.BungeeCord.Bungee;
import org.bukkit.Bukkit;
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
        Bukkit.getLogger().info("PrideTuveLobby is starting...");

        //Assingments
        Timer timer = new Timer();
        databaseMain = new DatabaseMain();
        tablistManager = new TablistManager();

        timerDatabaseManager = new TimerDatabaseManager(databaseMain.getConnection());
        try {
            timerDatabaseManager.createTimerTableIfNotExists();
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to connect to database! Is the database running?");
        }

        timeManager = new TimeManager(timerDatabaseManager, timer);
        inventoryManager = new InventoryManager();
        scoreboardManager = new ScoreboardManager(timeManager);
        update = new Update(scoreboardManager);
        StatusCommand statusCommand = new StatusCommand(timer, databaseMain, update);
        HubCommand hubcommand = new HubCommand();
        ConnectToCommand connectToCommand = new ConnectToCommand();

        timer.start();
        update.start();
        try {
            databaseMain.connectToDatabase();
        } catch (Exception e) {
            Bukkit.getLogger().severe("Failed to connect to database! Is the database running?");
        }

        getServer().getPluginManager().registerEvents(new PlayerQuitListener(timerDatabaseManager, timeManager), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(tablistManager, scoreboardManager, timeManager), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(inventoryManager), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new DamageListener(), this);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new HungerListener(), this);
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
        getServer().getPluginManager().registerEvents(new WeatherListener(), this);


        this.getCommand("status").setExecutor(statusCommand);
        this.getCommand("status").setTabCompleter(statusCommand);

        this.getCommand("hub").setExecutor(hubcommand);

        this.getCommand("sendTo").setExecutor(connectToCommand);
        this.getCommand("sendTo").setTabCompleter(connectToCommand);

        Bukkit.getLogger().info("PrideTuveLobby has been enabled!");

    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("PrideTuveLobby is stopping...");

        databaseMain.disconnect();

        Bukkit.getLogger().info("PrideTuveLobby has been disabled!");
    }

    public static Main getInstance() {
        return instance;
    }
}
