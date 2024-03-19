package de.rollocraft.lobbySystem;

/*
    * Author: Rollocraft
 */

import de.rollocraft.lobbySystem.Commands.*;
import de.rollocraft.lobbySystem.Commands.DuelCommand;
import de.rollocraft.lobbySystem.Database.Mysql.DatabaseMain;
import de.rollocraft.lobbySystem.Database.Mysql.Tables.PermissionDatabaseManager;
import de.rollocraft.lobbySystem.Database.Mysql.Tables.TimerDatabaseManager;
import de.rollocraft.lobbySystem.Database.Mysql.Tables.XpDatabaseManager;
import de.rollocraft.lobbySystem.Database.Sql.SqlMain;
import de.rollocraft.lobbySystem.Database.Sql.Tabels.HologramSqlManager;
import de.rollocraft.lobbySystem.Database.Sql.Tabels.KitSqlManager;
import de.rollocraft.lobbySystem.Database.Sql.Tabels.MapSqlManager;
import de.rollocraft.lobbySystem.Listener.GrapplingHookListener;
import de.rollocraft.lobbySystem.Listener.*;
import de.rollocraft.lobbySystem.Listener.HubProtection.*;
import de.rollocraft.lobbySystem.Listener.HubProtection.PlayerInteractListener;
import de.rollocraft.lobbySystem.Manager.*;
import de.rollocraft.lobbySystem.Threads.Update;
import de.rollocraft.lobbySystem.Threads.Timer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

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
    private HologramManager hologramManager;
    private SqlMain sqlMain;
    private HologramSqlManager hologramSqlManager;
    private DuelManager duelManager;
    private WorldManager worldManager;
    private SetupPvpMapManager setupPvpMapManager;
    private MapSqlManager mapSqlManager;
    private SetupPvpKitManager setupPvpKitManager;
    private KitSqlManager kitSqlManager;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("LobbySystem is starting...");
        instance = this;
        Bukkit.getLogger().info("Loading all Worlds! This may take a few seconds...");
        loadAllWorlds();
        Bukkit.getLogger().info("All Worlds loaded!, Hard work done :)!");

        //Assingments
        Timer timer = new Timer();

        databaseMain = new DatabaseMain();
        sqlMain = new SqlMain(this);


        /*
            Database Connection
        */

        timerDatabaseManager = new TimerDatabaseManager(databaseMain.getConnection());
        xpDatabaseManager = new XpDatabaseManager(databaseMain.getConnection());
        permissionDatabaseManager = new PermissionDatabaseManager(databaseMain.getConnection());

        mapSqlManager = new MapSqlManager(sqlMain.getConnection());
        hologramSqlManager = new HologramSqlManager(sqlMain.getConnection());
        kitSqlManager = new KitSqlManager(sqlMain.getConnection());

        try {
            timerDatabaseManager.createTimerTableIfNotExists();
            xpDatabaseManager.createXpTableIfNotExists();
            permissionDatabaseManager.createPermissionsTableIfNotExists();

            hologramSqlManager.createTableIfNotExist();
            mapSqlManager.createTableIfNotExist();
            kitSqlManager.createTableIfNotExist();
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to connect to database! Is the database running?");
        }

        /*
            Managers
        */

        timeManager = new TimeManager(timerDatabaseManager, timer);
        xpManager = new XpManager(xpDatabaseManager);
        permissionManager = new PermissionManager();
        hologramManager = new HologramManager(hologramSqlManager);
        worldManager = new WorldManager(mapSqlManager);
        setupPvpMapManager = new SetupPvpMapManager(mapSqlManager);
        setupPvpKitManager = new SetupPvpKitManager(kitSqlManager);

        tablistManager = new TablistManager(permissionManager);
        scoreboardManager = new ScoreboardManager(timeManager, xpManager, permissionManager);
        inventoryManager = new InventoryManager(timeManager,xpManager,permissionManager,mapSqlManager,kitSqlManager, worldManager);
        duelManager = new DuelManager(inventoryManager, mapSqlManager, kitSqlManager);

        Update update = new Update(scoreboardManager, tablistManager);

        timer.start();
        update.start();

        /*
            Registering Listeners
        */

        getServer().getPluginManager().registerEvents(new PlayerQuitListener(timerDatabaseManager, timeManager, permissionManager), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(tablistManager, scoreboardManager, timeManager, xpDatabaseManager, worldManager), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(duelManager), this);
        getServer().getPluginManager().registerEvents(new de.rollocraft.lobbySystem.Listener.PlayerInteractListener(inventoryManager), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(xpManager,setupPvpMapManager,setupPvpKitManager), this);
        getServer().getPluginManager().registerEvents(new PlayerEntityInteractListener(inventoryManager), this);
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new DamageListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new HungerListener(), this);
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
        getServer().getPluginManager().registerEvents(new WeatherListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDeathListener(), this);
        getServer().getPluginManager().registerEvents(new GrapplingHookListener(), this);


        /*
            Registering Commands
        */

        StatusCommand statusCommand = new StatusCommand(timer, databaseMain, update, sqlMain);
        HubCommand hubcommand = new HubCommand();
        InvseeCommand invseeCommand = new InvseeCommand();
        HologramCommand hologramCommand = new HologramCommand(hologramManager);
        DuelCommand duelCommand = new DuelCommand(duelManager);
        TeleportCommand teleportCommand = new TeleportCommand(worldManager);
        WorldCommand worldCommand = new WorldCommand(worldManager);
        SetupCommand setupCommand = new SetupCommand(setupPvpMapManager, setupPvpKitManager);

        this.getCommand("status").setExecutor(statusCommand);
        this.getCommand("status").setTabCompleter(statusCommand);

        this.getCommand("hub").setExecutor(hubcommand);

        this.getCommand("invsee").setExecutor(invseeCommand);

        this.getCommand("hologram").setExecutor(hologramCommand);
        this.getCommand("hologram").setTabCompleter(hologramCommand);

        this.getCommand("duel").setExecutor(duelCommand);
        this.getCommand("duel").setTabCompleter(duelCommand);

        this.getCommand("teleport").setExecutor(teleportCommand);
        this.getCommand("teleport").setTabCompleter(teleportCommand);

        this.getCommand("world").setExecutor(worldCommand);
        this.getCommand("world").setTabCompleter(worldCommand);

        this.getCommand("setup").setExecutor(setupCommand);

        Bukkit.getLogger().info("LobbySystem has been enabled!");

    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("LobbySystem is stopping...");

        databaseMain.disconnect();
        sqlMain.disconnect();

        Bukkit.getLogger().info("LobbySystem has been disabled!");
    }

    public static Main getInstance() {
        return instance;
    }
    private void loadAllWorlds() {
        File serverDirectory = this.getServer().getWorldContainer();
        File[] worldDirectories = serverDirectory.listFiles(File::isDirectory);

        List<String> excludedWorlds = Arrays.asList("config", "cache", "logs", "plugins", "versions", "libraries");

        if (worldDirectories != null) {
            for (File worldDirectory : worldDirectories) {
                String worldName = worldDirectory.getName();
                if (excludedWorlds.contains(worldName)) {
                    continue;
                }
                World world = Bukkit.getWorld(worldName);
                if (world == null) {
                    Bukkit.createWorld(new WorldCreator(worldName));
                }
            }
        }
    }

}
