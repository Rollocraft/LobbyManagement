package de.rollocraft.pridetuvelobby.Listener.HubProtection;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;


public class ItemListener implements Listener {

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent event) {
        if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(true);
        }
    }
}
