package de.rollocraft.lobbySystem.Minecraft.Utils;

import de.rollocraft.lobbySystem.Main;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public class Debugger {

    private Main main;

    public Debugger(Main main) {
        this.main = main;
    }

    public void log(Level level, String message) {
        if (main.getConfigManager().get().getBoolean("debug")) {
            Main.getInstance().getLogger().log(level, message);
        }
    }
}
