package de.rollocraft.pridetuvelobby.Listener.HubProtection;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerListener implements Listener {

    @EventHandler
    public void onGetHunger(FoodLevelChangeEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) return;
        event.setCancelled(true);
    }
}
