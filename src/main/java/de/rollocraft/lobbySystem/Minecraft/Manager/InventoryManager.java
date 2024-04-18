package de.rollocraft.lobbySystem.Minecraft.Manager;

import de.rollocraft.lobbySystem.Minecraft.Database.Sql.Tabels.KitSqlManager;
import de.rollocraft.lobbySystem.Minecraft.Database.Sql.Tabels.MapSqlManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventoryManager {
    private TimeManager timeManager;
    private XpManager xpManager;
    private PermissionManager permissionManager;
    private MapSqlManager mapSqlManager;
    private KitSqlManager kitSqlManager;
    private WorldManager worldManager;
    public InventoryManager(TimeManager timeManager, XpManager xpManager, PermissionManager permissionManager, MapSqlManager mapSqlManager, KitSqlManager kitSqlManager, WorldManager worldManager) {
        this.kitSqlManager = kitSqlManager;
        this.mapSqlManager = mapSqlManager;
        this.xpManager = xpManager;
        this.timeManager = timeManager;
        this.permissionManager = permissionManager;
        this.worldManager = worldManager;
    }

    public void openInventory(Player player, InventoryType type) {
        Inventory inv;
        switch (type) {
            case SERVER_SELECTOR:
                inv = createServerSelectorInventory();
                break;
            case KIT_SELECTOR:
                inv = openKitInventory();
                break;
            case MAP_SELECTOR:
                inv = openMapInventory();
                break;
            case STATS:
                inv = createStatsInventory(player);
                break;
            default:
                throw new IllegalArgumentException("Unknown inventory type: " + type);
        }
        player.openInventory(inv);
    }

    public enum InventoryType {
        SERVER_SELECTOR {
            @Override
            public Inventory createInventory(InventoryManager manager) {
                return manager.createServerSelectorInventory();
            }
        },
        KIT_SELECTOR {
            @Override
            public Inventory createInventory(InventoryManager manager) {
                return manager.openKitInventory();
            }
        },
        MAP_SELECTOR {
            @Override
            public Inventory createInventory(InventoryManager manager) {
                return manager.openMapInventory();
            }
        },
        STATS {
            @Override
            public Inventory createInventory(InventoryManager manager) {
                throw new UnsupportedOperationException("STATS inventory type requires a player-specific inventory.");
            }

            @Override
            public Inventory createInventory(InventoryManager manager, Player player) {
                return manager.createStatsInventory(player);
            }
        };

        public abstract Inventory createInventory(InventoryManager manager);
        public Inventory createInventory(InventoryManager manager, Player player) {
            throw new UnsupportedOperationException("This inventory type does not support player-specific inventories.");
        }
    }

    /*

    Inventory Creation/Open Functions

     */

    private Inventory createServerSelectorInventory() {
        Inventory inv = Bukkit.createInventory(null, 36, "Server Manager");

        ItemStack item1 = new ItemStack(Material.GRASS_BLOCK);
        inv.setItem(3, item1);

        return inv;
    }
    private Inventory openKitInventory() {
        Inventory inv = Bukkit.createInventory(null, 9, "Kit Selector");
        List<String> allKits = kitSqlManager.getAllKits();
        if (allKits.isEmpty()) {
            ItemStack item = new ItemStack(Material.BARRIER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("Keine Kits");
            item.setItemMeta(meta);
            inv.addItem(item);
        } else {
            for (String kitName : allKits) {
                String mapDescription = kitSqlManager.getKitDescription(kitName);
                ItemStack item = createMapItem(kitName, mapDescription);
                inv.addItem(item);
            }
        }
        return inv;
    }

    private Inventory openMapInventory() {
        Inventory inv = Bukkit.createInventory(null, 9, "Map Selector");

        List<String> allMaps = mapSqlManager.getAllMaps();
        if (allMaps.isEmpty()) {
            ItemStack item = new ItemStack(Material.BARRIER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("Keine Karten");
            item.setItemMeta(meta);
            inv.addItem(item);
        } else {
            for (String mapName : allMaps) {
                String mapDescription = mapSqlManager.getMapDescription(mapName);
                ItemStack item = createMapItem(mapName, mapDescription);
                if (worldManager.mapExist(mapName)) {
                    inv.addItem(item);
                }
            }
        }

        return inv;
    }

    private Inventory createStatsInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 45, player.getName() + " stats");

        ItemStack playtime = createItem(Material.CLOCK, ChatColor.WHITE + "Playtime:", timeManager.getTime(player).toString() + ChatColor.RED +"!outdated! -> look Scoreboard");
        ItemStack Level = createItemWithBar(Material.EMERALD, ChatColor.WHITE + "Level:", player);
        ItemStack Close = createItem(Material.BARRIER, ChatColor.RED + "Close", "Close the inventory");
        ItemStack Rank = createItem(Material.GOLD_INGOT, ChatColor.WHITE + "Rank:", permissionManager.getPlayerPrefix(player));

        inv.setItem(21, playtime);
        inv.setItem(22, Level);
        inv.setItem(23, Rank);
        inv.setItem(44, Close);



        return inv;
    }

    /*
    Item Creation Functions
     */


    private ItemStack createItem(Material material, String name, String lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        List<String> loreList = new ArrayList<>();
        loreList.add(lore);
        meta.setLore(loreList);

        item.setItemMeta(meta);

        return item;
    }
    private ItemStack createItemWithBar(Material material, String name, Player player) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        int totalXpForNextLevel = xpManager.xpForLevel(xpManager.getLvl(player) + 1);
        int remainingXpForNextLevel = xpManager.getRemainingXpForNextLevel(player);
        int completedXp = totalXpForNextLevel - remainingXpForNextLevel;

        int totalChars = 6;
        int completedChars = (int) ((double) completedXp / totalXpForNextLevel * totalChars);
        int remainingChars = totalChars - completedChars;

        String progressBar = ChatColor.BLUE + String.join("", Collections.nCopies(completedChars, "-"))
                + ChatColor.WHITE + String.join("", Collections.nCopies(remainingChars, "-"));

        List<String> loreList = new ArrayList<>();
        loreList.add(progressBar);
        loreList.add(ChatColor.WHITE+ "" + remainingXpForNextLevel + " Xp Remaining!");
        meta.setLore(loreList);

        item.setItemMeta(meta);

        return item;
    }

    private ItemStack createMapItem(String mapName, String mapDescription) {
        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(mapName);

        List<String> loreList = new ArrayList<>();
        loreList.add(ChatColor.WHITE + mapDescription);
        meta.setLore(loreList);

        item.setItemMeta(meta);

        return item;
    }
}