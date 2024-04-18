package de.rollocraft.lobbySystem.Minecraft.Listener;

import de.rollocraft.lobbySystem.Minecraft.Manager.InventoryManager;
import de.rollocraft.lobbySystem.Minecraft.Utils.Items;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerEntityInteractListener implements Listener {
    private InventoryManager inventoryManager;

    public PlayerEntityInteractListener(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (player instanceof Player) {
            if (entity instanceof Villager) {
                inventoryManager.openInventory(player, InventoryManager.InventoryType.STATS);
            }
        }
    }

}
