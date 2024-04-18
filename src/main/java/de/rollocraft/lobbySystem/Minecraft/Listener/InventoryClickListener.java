package de.rollocraft.lobbySystem.Minecraft.Listener;

import de.rollocraft.lobbySystem.Minecraft.Manager.DuelManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;


public class InventoryClickListener implements Listener {
    private DuelManager duelManager;

    public InventoryClickListener(DuelManager duelManager) {
        this.duelManager = duelManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals(player.getName() + " stats")) {
            if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Close")) {
                event.setCancelled(true);
                player.closeInventory();
            } else {
                event.setCancelled(true);
            }
        }

        if (event.getView().getTitle().equals("Server Manager")) {
            event.setCancelled(true);
        }


        if (event.getView().getTitle().equals("Kit Selector")) {
            if (event.getCurrentItem() != null) {
                String selectedKit = event.getCurrentItem().getItemMeta().getDisplayName();
                duelManager.setKit(selectedKit);
                player.closeInventory();
                duelManager.mapSelection(player);
                event.setCancelled(true);
            }
        }

        if (event.getView().getTitle().equals("Map Selector")) {
            if (event.getCurrentItem() != null) {
                String selectedMap = event.getCurrentItem().getItemMeta().getDisplayName();
                duelManager.setMap(selectedMap);
                player.closeInventory();
                event.setCancelled(true);
            }
        }

        if (!player.hasPermission("lobbySystem.hubprotection.inventoryInteract")) {
            event.setCancelled(true);
        }
    }
}