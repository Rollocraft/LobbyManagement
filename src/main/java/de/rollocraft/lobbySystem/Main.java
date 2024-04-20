package de.rollocraft.lobbySystem;

/*
    * Author: Rollocraft
 */

import de.rollocraft.lobbySystem.Minecraft.Commands.*;
import de.rollocraft.lobbySystem.Minecraft.Commands.DuelCommand;
import de.rollocraft.lobbySystem.Minecraft.Database.Mysql.DatabaseMain;
import de.rollocraft.lobbySystem.Minecraft.Database.Mysql.Tables.TimerDatabaseManager;
import de.rollocraft.lobbySystem.Minecraft.Database.Mysql.Tables.XpDatabaseManager;
import de.rollocraft.lobbySystem.Minecraft.Database.Sql.SqlMain;
import de.rollocraft.lobbySystem.Minecraft.Database.Sql.Tabels.*;
import de.rollocraft.lobbySystem.Minecraft.Listener.*;
import de.rollocraft.lobbySystem.Minecraft.Listener.HubProtection.*;
import de.rollocraft.lobbySystem.Minecraft.Manager.*;
import de.rollocraft.lobbySystem.Minecraft.Manager.Setup.SetupParkourManager;
import de.rollocraft.lobbySystem.Minecraft.Manager.Setup.SetupPvpKitManager;
import de.rollocraft.lobbySystem.Minecraft.Manager.Setup.SetupPvpMapManager;
import de.rollocraft.lobbySystem.Minecraft.Threads.Update;
import de.rollocraft.lobbySystem.Minecraft.Threads.Timer;
import de.rollocraft.lobbySystem.Minecraft.Utils.ConfigManager;

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
    private BlockParticleManager blockParticleManager;
    private BlockParticelSqlManager blockParticelSqlManager;
    private ConfigManager configManager;
    private SetupParkourManager setupParkourManager;
    private ParkourManager parkourManager;
    private ParkourSqlManager parkourSqlManager;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("LobbySystem is starting...");
        instance = this;

        configManager = new ConfigManager(this);
        configManager.setup();

        Bukkit.getLogger().info("Loading all Worlds! This may take a few seconds...");
        loadAllWorlds();
        Bukkit.getLogger().info("All Worlds loaded!, Hard work done :)!");

        //Assingments
        Timer timer = new Timer();

        databaseMain = new DatabaseMain(configManager);
        sqlMain = new SqlMain(this);


        /*
            Database Connection
        */

        timerDatabaseManager = new TimerDatabaseManager(databaseMain.getConnection());
        xpDatabaseManager = new XpDatabaseManager(databaseMain.getConnection());

        mapSqlManager = new MapSqlManager(sqlMain.getConnection());
        hologramSqlManager = new HologramSqlManager(sqlMain.getConnection());
        kitSqlManager = new KitSqlManager(sqlMain.getConnection());
        blockParticelSqlManager = new BlockParticelSqlManager(sqlMain.getConnection());
        parkourSqlManager = new ParkourSqlManager(sqlMain.getConnection());


        try {
            timerDatabaseManager.createTimerTableIfNotExists();
            xpDatabaseManager.createXpTableIfNotExists();


            hologramSqlManager.createTableIfNotExist();
            mapSqlManager.createTableIfNotExist();
            kitSqlManager.createTableIfNotExist();
            blockParticelSqlManager.createTableIfNotExist();
            parkourSqlManager.createTableIfNotExists();
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
        blockParticleManager = new BlockParticleManager(blockParticelSqlManager);
        parkourManager = new ParkourManager(parkourSqlManager);

        tablistManager = new TablistManager(permissionManager);
        scoreboardManager = new ScoreboardManager(timeManager, xpManager, permissionManager, configManager);
        inventoryManager = new InventoryManager(timeManager,xpManager,permissionManager,mapSqlManager,kitSqlManager, worldManager);
        duelManager = new DuelManager(inventoryManager, mapSqlManager, kitSqlManager);
        setupParkourManager = new SetupParkourManager(parkourManager);

        Update update = new Update(scoreboardManager, tablistManager, xpManager,blockParticleManager);

        timer.start();
        update.start();

        /*
            Registering Listeners
        */

        getServer().getPluginManager().registerEvents(new PlayerQuitListener(timerDatabaseManager, timeManager, permissionManager), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(tablistManager, scoreboardManager, timeManager, xpDatabaseManager, worldManager), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(duelManager), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(inventoryManager, parkourManager), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(xpManager,setupPvpMapManager,setupPvpKitManager, setupParkourManager), this);
        getServer().getPluginManager().registerEvents(new PlayerEntityInteractListener(inventoryManager), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(inventoryManager, parkourManager), this);
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new DamageListener(), this);
        getServer().getPluginManager().registerEvents(new HungerListener(), this);
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
        getServer().getPluginManager().registerEvents(new WeatherListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new GrapplingHookListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);


        /*
            Registering Commands
        */

        StatusCommand statusCommand = new StatusCommand(timer, databaseMain, update, sqlMain);
        InvseeCommand invseeCommand = new InvseeCommand();
        HologramCommand hologramCommand = new HologramCommand(hologramManager);
        DuelCommand duelCommand = new DuelCommand(duelManager);
        TeleportCommand teleportCommand = new TeleportCommand(worldManager);
        WorldCommand worldCommand = new WorldCommand(worldManager);
        SetupCommand setupCommand = new SetupCommand(setupPvpMapManager, setupPvpKitManager,setupParkourManager);
        BlockParticleCommand blockParticleCommand = new BlockParticleCommand(blockParticleManager);
        SocialCommand socialCommand = new SocialCommand(configManager);
        GamemodeCommand gamemodeCommand = new GamemodeCommand();
        FlyCommand flyCommand = new FlyCommand();
        VanishCommand vanishCommand = new VanishCommand();
        HeadCommand headCommand = new HeadCommand();
        BuildCommand buildCommand = new BuildCommand();
        ParkourCommand parkourCommand = new ParkourCommand(parkourManager);

        this.getCommand("status").setExecutor(statusCommand);
        this.getCommand("status").setTabCompleter(statusCommand);

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

        this.getCommand("blockparticle").setExecutor(blockParticleCommand);
        this.getCommand("blockparticle").setTabCompleter(blockParticleCommand);

        this.getCommand("social").setExecutor(socialCommand);

        this.getCommand("gamemode").setExecutor(gamemodeCommand);

        this.getCommand("fly").setExecutor(flyCommand);

        this.getCommand("vanish").setExecutor(vanishCommand);

        this.getCommand("head").setExecutor(headCommand);

        this.getCommand("build").setExecutor(buildCommand);

        this.getCommand("parkour").setExecutor(parkourCommand);



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
    public ConfigManager getConfigManager() {
        return configManager;
    }
}