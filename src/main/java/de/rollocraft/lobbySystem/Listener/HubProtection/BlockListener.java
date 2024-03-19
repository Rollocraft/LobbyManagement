package de.rollocraft.lobbySystem.Listener.HubProtection;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.getPlayer().hasPermission("lobbySystem.hubprotection.breakBlock")){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!event.getPlayer().hasPermission("lobbySystem.hubprotection.placeBlock")){
            event.setCancelled(true);
        }
    }
}