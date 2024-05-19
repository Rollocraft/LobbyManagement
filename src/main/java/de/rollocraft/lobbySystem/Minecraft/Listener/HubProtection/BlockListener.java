package de.rollocraft.lobbySystem.Minecraft.Listener.HubProtection;

import de.rollocraft.lobbySystem.Main;
import de.rollocraft.lobbySystem.Minecraft.Commands.BuildCommand;
import de.rollocraft.lobbySystem.Minecraft.Utils.Maps.BuildMap;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.logging.Level;

public class BlockListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Main.getDebugger().log(Level.INFO, BuildMap.getInstance().canBuild(event.getPlayer()) ? "true" : "false");
        if (!event.getPlayer().hasPermission("lobbySystem.hubprotection.breakBlock") || !BuildMap.getInstance().canBuild(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!event.getPlayer().hasPermission("lobbySystem.hubprotection.placeBlock") || !BuildMap.getInstance().canBuild(event.getPlayer())){
            event.setCancelled(true);
        }
    }
}