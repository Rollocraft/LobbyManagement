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
import de.rollocraft.pridetuvelobby.Database.Tables.PermissionDatabaseManager;
import de.rollocraft.pridetuvelobby.Database.Tables.TimerDatabaseManager;
import de.rollocraft.pridetuvelobby.Database.Tables.XpDatabaseManager;
import de.rollocraft.pridetuvelobby.Listener.HubProtection.*;
import de.rollocraft.pridetuvelobby.Listener.PlayerChatListener;
import de.rollocraft.pridetuvelobby.Listener.PlayerInteractListener;
import de.rollocraft.pridetuvelobby.Listener.PlayerJoinListener;
import de.rollocraft.pridetuvelobby.Listener.PlayerQuitListener;
import de.rollocraft.pridetuvelobby.Manager.*;
import de.rollocraft.pridetuvelobby.Threads.Update;
import de.rollocraft.pridetuvelobby.Threads.Timer;
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
    private InventoryManager inventoryManager;
    private XpDatabaseManager xpDatabaseManager;
    private XpManager xpManager;
    private PermissionDatabaseManager permissionDatabaseManager;
    private PermissionManager permissionManager;

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


        /*
            Database Connection
        */

        timerDatabaseManager = new TimerDatabaseManager(databaseMain.getConnection());
        xpDatabaseManager = new XpDatabaseManager(databaseMain.getConnection());
        permissionDatabaseManager = new PermissionDatabaseManager(databaseMain.getConnection());
        try {
            timerDatabaseManager.createTimerTableIfNotExists();
            xpDatabaseManager.createXpTableIfNotExists();
            permissionDatabaseManager.createPermissionsTableIfNotExists();
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to connect to database! Is the database running?");
        }

        /*
            Managers
        */

        timeManager = new TimeManager(timerDatabaseManager, timer);
        xpManager = new XpManager(xpDatabaseManager);
        permissionManager = new PermissionManager(permissionDatabaseManager);
        scoreboardManager = new ScoreboardManager(timeManager, xpManager, permissionManager);


        tablistManager = new TablistManager(permissionManager);
        inventoryManager = new InventoryManager();

        Update update = new Update(scoreboardManager, tablistManager);

        timer.start();
        update.start();

        /*
            Registering Listeners
        */

        getServer().getPluginManager().registerEvents(new PlayerQuitListener(timerDatabaseManager, timeManager), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(tablistManager, scoreboardManager, timeManager, xpDatabaseManager, permissionManager), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(inventoryManager), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(xpManager), this);
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new DamageListener(), this);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new HungerListener(), this);
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
        getServer().getPluginManager().registerEvents(new WeatherListener(), this);

        /*
            Registering Commands
        */

        StatusCommand statusCommand = new StatusCommand(timer, databaseMain, update);
        HubCommand hubcommand = new HubCommand();
        ConnectToCommand connectToCommand = new ConnectToCommand();

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
