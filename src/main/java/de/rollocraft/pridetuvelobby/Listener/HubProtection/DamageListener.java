package de.rollocraft.pridetuvelobby.Listener.HubProtection;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onGetHunger(EntityDamageEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) return;
        event.setCancelled(true);
    }
}
