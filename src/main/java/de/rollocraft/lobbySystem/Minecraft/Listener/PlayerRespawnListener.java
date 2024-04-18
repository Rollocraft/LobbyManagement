package de.rollocraft.lobbySystem.Minecraft.Listener;

import de.rollocraft.lobbySystem.Minecraft.Utils.Items;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        player.getInventory().setItem(4, Items.ServerManagerItem());
        player.getInventory().setItem(5, Items.PlayerHeadItem(player));
        player.getInventory().setItem(3, Items.GraphlinHook());
    }
}