package de.rollocraft.lobbySystem.Listener;

import de.rollocraft.lobbySystem.Manager.InventoryManager;
import de.rollocraft.lobbySystem.Utils.Items;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Bukkit.getServer;

public class PlayerInteractListener implements Listener {
    private InventoryManager inventoryManager;

    public PlayerInteractListener(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.isSimilar(Items.ServerManagerItem())) {
            inventoryManager.openInventory(player, InventoryManager.InventoryType.SERVER_SELECTOR);
        }
        if (item.isSimilar(Items.PlayerHeadItem(player))) {
            inventoryManager.openInventory(player, InventoryManager.InventoryType.STATS);
            event.setCancelled(true);
        }
    }

}
