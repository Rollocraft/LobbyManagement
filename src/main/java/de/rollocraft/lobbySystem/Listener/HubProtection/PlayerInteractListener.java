package de.rollocraft.lobbySystem.Listener.HubProtection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onEntity(PlayerInteractEvent event) {
        if (!event.getPlayer().hasPermission("lobbySystem.hubprotection.interact")) {
            event.setCancelled(true);
        }
    }
}
