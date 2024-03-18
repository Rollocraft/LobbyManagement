package de.rollocraft.lobbySystem.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class GrapplingHookListener implements Listener {
    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        Player player = event.getPlayer();
        ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
        if (event.getState() == PlayerFishEvent.State.REEL_IN || event.getState() == PlayerFishEvent.State.IN_GROUND) {
            if (itemMeta.getDisplayName().equals("Grappling Hook")) {
                Vector direction = player.getLocation().getDirection();
                direction.multiply(2.0D);

                player.setVelocity(direction);
            }
        }
    }
}