package de.rollocraft.lobbySystem.Listener.HubProtection;

import de.rollocraft.lobbySystem.Main;
import de.rollocraft.lobbySystem.Utils.Maps.PlayerDuelMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;


public class EntityDeathListener implements Listener {


    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        PlayerDuelMap duelMap = PlayerDuelMap.getInstance();
        Player opponent = duelMap.getDuelOpponent(player);
        if (duelMap.isPlayerInDuel(player)) {
            if (event.getEntity() == player) {
                player.sendTitle(ChatColor.GOLD + opponent.getName() + " won the Fight", "", 10, 70, 20);
                player.spigot().respawn();
                opponent.sendTitle(ChatColor.GOLD + opponent.getName() + " won the Fight", "", 10, 70, 20);
                event.getDrops().clear();
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> finishDuel(player, opponent), 100L);
            } else if (event.getEntity() == opponent) {
                player.sendTitle(ChatColor.GOLD + player.getName() + " won the Fight", "", 10, 70, 20);
                opponent.sendTitle(ChatColor.GOLD + player.getName() + " won the Fight", "", 10, 70, 20);
                opponent.spigot().respawn();
                event.getDrops().clear();
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> finishDuel(player, opponent), 100L);
            }
        }else {
            event.setCancelled(true);
        }
    }

    public void finishDuel(Player player, Player opponent) {
        player.getInventory().clear();
        opponent.getInventory().clear();
        player.teleport(player.getWorld().getSpawnLocation());
        opponent.teleport(opponent.getWorld().getSpawnLocation());
        player.setHealth(20);
        opponent.setHealth(20);

        PlayerDuelMap.getInstance().removeDuel(player, opponent);
    }
}