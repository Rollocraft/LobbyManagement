package de.rollocraft.pridetuvelobby.Listener.HubProtection;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class EntityListener implements Listener {

    @EventHandler
    public void onEntity(PlayerInteractEvent event) {
        if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
            event.setCancelled(true);
        }
    }
}
