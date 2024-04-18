package de.rollocraft.lobbySystem.Minecraft.Listener;

import de.rollocraft.lobbySystem.Main;
import de.rollocraft.lobbySystem.Minecraft.Manager.InventoryManager;
import de.rollocraft.lobbySystem.Minecraft.Manager.ParkourManager;
import de.rollocraft.lobbySystem.Minecraft.Utils.Items;
import de.rollocraft.lobbySystem.Minecraft.Utils.Maps.BuildMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import static org.bukkit.Bukkit.getServer;

public class PlayerInteractListener implements Listener {
    private InventoryManager inventoryManager;
    private ParkourManager parkourManager;

    public PlayerInteractListener(InventoryManager inventoryManager, ParkourManager parkourManager) {
        this.inventoryManager = inventoryManager;
        this.parkourManager = parkourManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        Block block = event.getClickedBlock();
        if (item == null || block == null) {
            return;
        }

        if (block.getType() == Material.HEAVY_WEIGHTED_PRESSURE_PLATE) {
            parkourManager.checkpoint(player);
        }
        if (block.getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
            parkourManager.finishParkour(player);
        }



        if (item.isSimilar(Items.ServerManagerItem())) {
            inventoryManager.openInventory(player, InventoryManager.InventoryType.SERVER_SELECTOR);
        }
        if (item.isSimilar(Items.PlayerHeadItem(player))) {
            inventoryManager.openInventory(player, InventoryManager.InventoryType.STATS);
            event.setCancelled(true);
        }


        if (block.getType() == Material.PLAYER_WALL_HEAD) {
            Skull skull = (Skull) block.getState();
            if (skull.hasOwner()) {
                String ownerName = skull.getOwningPlayer().getName();
                event.getPlayer().sendMessage("You clicked on the head of " + ownerName);
                // Add logik
            }
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }

        if ("leaveParkour".equals(item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "unique"), PersistentDataType.STRING))) {
            parkourManager.stopParkour(player);
        }

        if (!event.getPlayer().hasPermission("lobbySystem.hubprotection.interact") || !BuildMap.getInstance().canBuild(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

}
