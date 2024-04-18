package de.rollocraft.lobbySystem.Minecraft.Listener.HubProtection;

import de.rollocraft.lobbySystem.Main;
import de.rollocraft.lobbySystem.Minecraft.Utils.Items;
import de.rollocraft.lobbySystem.Minecraft.Utils.Maps.PlayerDuelMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;


public class PlayerDeathListener implements Listener {


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = (Player) event.getEntity();

        PlayerDuelMap duelMap = PlayerDuelMap.getInstance();
        Player opponent = duelMap.getDuelOpponent(player);
        if (duelMap.isPlayerInDuel(player)) {
            if (event.getEntity() == player) {
                player.sendTitle(ChatColor.GOLD + opponent.getName() + " won the Fight", "", 10, 70, 20);
                opponent.sendTitle(ChatColor.GOLD + opponent.getName() + " won the Fight", "", 10, 70, 20);
                opponent.getLocation().getWorld().strikeLightningEffect(opponent.getLocation());
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> player.spigot().respawn(), 1L);
                event.getDrops().clear();
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> finishDuel(player, opponent), 100L);
            } else if (event.getEntity() == opponent) {
                player.sendTitle(ChatColor.GOLD + player.getName() + " won the Fight", "", 10, 70, 20);
                opponent.sendTitle(ChatColor.GOLD + player.getName() + " won the Fight", "", 10, 70, 20);
                player.getLocation().getWorld().strikeLightningEffect(player.getLocation());
                event.getDrops().clear();
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> player.spigot().respawn(), 1L);
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> finishDuel(player, opponent), 100L);
            }
        }else {
            event.setCancelled(true);
        }
    }

    public void finishDuel(Player player, Player opponent) {
        World world = Bukkit.getWorld("world");
        player.getInventory().clear();
        opponent.getInventory().clear();
        player.teleport(world.getSpawnLocation());
        opponent.teleport(world.getSpawnLocation());
        player.setHealth(20);
        opponent.setHealth(20);

        player.getInventory().setItem(4, Items.ServerManagerItem());
        player.getInventory().setItem(5, Items.PlayerHeadItem(player));
        player.getInventory().setItem(3, Items.GraphlinHook());

        opponent.getInventory().setItem(4, Items.ServerManagerItem());
        opponent.getInventory().setItem(5, Items.PlayerHeadItem(opponent));
        opponent.getInventory().setItem(3, Items.GraphlinHook());

        PlayerDuelMap.getInstance().removeDuel(player, opponent);
    }
}