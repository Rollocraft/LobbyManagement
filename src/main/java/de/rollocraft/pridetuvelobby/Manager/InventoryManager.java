package de.rollocraft.pridetuvelobby.Manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryManager {
    public Inventory createServerSelectorInventory() {
        Inventory inv = Bukkit.createInventory(null, 36, "Server Manager");

        ItemStack item1 = new ItemStack(Material.GRASS_BLOCK);
        inv.setItem(3, item1);

        return inv;
    }

    public Inventory createStatsInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 36, player.getName() + " stats");

        ItemStack item1 = new ItemStack(Material.GRASS_BLOCK);
        inv.setItem(3, item1);

        return inv;
    }

    public void openInventory(Player player, Inventory inv) {
        player.openInventory(inv);
    }
}