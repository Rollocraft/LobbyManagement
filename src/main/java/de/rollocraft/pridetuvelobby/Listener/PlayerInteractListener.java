package de.rollocraft.pridetuvelobby.Listener;

import de.rollocraft.pridetuvelobby.Manager.InventoryManager;
import de.rollocraft.pridetuvelobby.Utils.Items;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

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
            inventoryManager.openInventory(player, inventoryManager.createInventory());
        }
    }
}
