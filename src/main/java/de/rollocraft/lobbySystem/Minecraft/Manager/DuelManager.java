package de.rollocraft.lobbySystem.Minecraft.Manager;

import de.rollocraft.lobbySystem.Minecraft.Database.Sql.Tabels.KitSqlManager;
import de.rollocraft.lobbySystem.Minecraft.Database.Sql.Tabels.MapSqlManager;
import de.rollocraft.lobbySystem.Minecraft.Objects.Duel;
import de.rollocraft.lobbySystem.Minecraft.Utils.Base64Util;
import de.rollocraft.lobbySystem.Minecraft.Utils.Maps.PlayerDuelMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DuelManager {
    private InventoryManager inventoryManager;
    private Duel duel;
    private MapSqlManager mapSqlManager;

    private enum State {KIT_SELECTION, MAP_SELECTION, READY}
    private KitSqlManager kitSqlManager;

    private State state;

    public DuelManager(InventoryManager inventoryManager, MapSqlManager mapSqlManager, KitSqlManager kitSqlManager) {
        this.kitSqlManager = kitSqlManager;
        this.mapSqlManager = mapSqlManager;
        this.inventoryManager = inventoryManager;
    }

    public void acceptDuel(Player player, Player target) {
        duel = new Duel(player, target, null, null);
        inventoryManager.openInventory(player, InventoryManager.InventoryType.KIT_SELECTOR);
    }

    public void declineDuel(Player player, Player target) {
        player.sendMessage(ChatColor.RED + "Your Duel to " + target.getName() + " got declined!");
        target.sendMessage(ChatColor.RED + "You declined the Duel");
    }

    public void setMap(String map) {
        duel.setMap(map);
        fight();
    }

    public void setKit(String kit) {
        duel.setKit(kit);
    }

    public String debug() {
        return duel.toString();
    }

    public void mapSelection(Player player) {
        inventoryManager.openInventory(player, InventoryManager.InventoryType.MAP_SELECTOR);
    }

    public void mapSelected(String map) {
        if (state == State.MAP_SELECTION) {
            duel.setMap(map);
            state = State.READY;
        }
    }


    public Duel getCurrentDuel() {
        return duel;
    }

    public void fight() {
        PlayerDuelMap sharedMap = PlayerDuelMap.getInstance();
        String map = duel.getMap();

        String world = mapSqlManager.getWorldName(map);

        Location spawnplayer = mapSqlManager.getSpawnLocation1(map, world);
        Location spawntarget = mapSqlManager.getSpawnLocation2(map, world);

        Player player = duel.getPlayer();
        Player target = duel.getTarget();

        player.setGameMode(GameMode.SURVIVAL);
        target.setGameMode(GameMode.SURVIVAL);

        sharedMap.addDuel(player, target);

        player.teleport(spawnplayer);
        target.teleport(spawntarget);

        player.getInventory().clear();
        target.getInventory().clear();

        // Laden des Kits aus der Datenbank
        String encodedKit = kitSqlManager.getKit(duel.getKit());

        try {
            // Dekodieren des Kits und Setzen der Items in das Inventar des Spielers
            List<ItemStack> items = Base64Util.decode(encodedKit);
            ItemStack[] itemArray = items.toArray(new ItemStack[0]);
            player.getInventory().setContents(itemArray);
            target.getInventory().setContents(itemArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}