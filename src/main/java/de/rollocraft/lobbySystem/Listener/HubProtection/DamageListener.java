package de.rollocraft.lobbySystem.Listener.HubProtection;

import de.rollocraft.lobbySystem.Utils.Maps.PlayerDuelMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onGetDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        PlayerDuelMap sharedMap = PlayerDuelMap.getInstance();
        if (sharedMap.isPlayerInDuel(player)) {
            return;
        }
        event.setCancelled(true);
    }
}
