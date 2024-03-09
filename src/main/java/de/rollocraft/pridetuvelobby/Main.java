package de.rollocraft.pridetuvelobby;

/*
    *  PrideTuveLobby
    * Version: 0.0.1
    * Author: Rollocraft
    * 09.03.2024
 */

import de.rollocraft.pridetuvelobby.Threads.Timer;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        //Assingments
        Timer timer = new Timer();

        //...
        timer.start();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getInstance() {
        return instance;
    }
}
