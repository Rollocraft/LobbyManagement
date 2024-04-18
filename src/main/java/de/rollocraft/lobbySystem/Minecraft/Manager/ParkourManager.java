package de.rollocraft.lobbySystem.Minecraft.Manager;

import de.rollocraft.lobbySystem.Main;
import de.rollocraft.lobbySystem.Minecraft.Database.Sql.Tabels.ParkourSqlManager;
import de.rollocraft.lobbySystem.Minecraft.Objects.Parkour;
import de.rollocraft.lobbySystem.Minecraft.Utils.Items;
import de.rollocraft.lobbySystem.Minecraft.Utils.Maps.BuildMap;
import de.rollocraft.lobbySystem.Minecraft.Utils.Maps.ParkourMap;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.sql.SQLException;
import java.util.List;

public class ParkourManager {
    private ParkourSqlManager parkourSqlManager;
    public ParkourManager(ParkourSqlManager parkourSqlManager) {
        this.parkourSqlManager = parkourSqlManager;
    }

    public void saveParkour(Parkour parkour) {
        try {
            parkourSqlManager.saveParkour(parkour);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean parkourExists(String name) {
        try {
            return parkourSqlManager.getParkour(name) != null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void startParkour(Player player, String parkourName) {
        World world =player.getLocation().getWorld();
        Parkour parkour = getParkourObjectByName(parkourName);
        if (world != parkour.getStart().getWorld()) {
            player.sendMessage("You are in the wrong world!");
        }
        player.sendMessage("Teleporting to the parkour start location...");
        player.getInventory().clear();
        player.getInventory().setItem(8, getLeaveItem());
        player.teleport(parkour.getStart());
        player.setFlying(false);
        ParkourMap.getInstance().addParkour(player, parkour);
    }

    public void stopParkour(Player player) {
        ParkourMap.getInstance().removeParkour(player);
        player.sendMessage("Parkour stopped");
        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        player.getInventory().clear();
        player.getInventory().setItem(4, Items.ServerManagerItem());
        player.getInventory().setItem(5, Items.PlayerHeadItem(player));
        player.getInventory().setItem(3, Items.GraphlinHook());
    }

    //check system verbessern

    public void checkpoint(Player player) {
        if (ParkourMap.getInstance().isInParkour(player)) {
            Parkour parkour = ParkourMap.getInstance().getParkour(player);
            int checkpoint = ParkourMap.getInstance().getCheckpoint(player);
            Location playerLocation = player.getLocation();
            double x = Math.round(playerLocation.getX()) + 0.5;
            double y = Math.round(playerLocation.getY()) + 0.5;
            double z = Math.round(playerLocation.getZ()) + 0.5;
            playerLocation.setX(x);
            playerLocation.setY(y);
            playerLocation.setZ(z);
            Location checkpointLocation = getCheckPointLocation(parkour, checkpoint);
            Location playerLocationWihtoutYaw = new Location(playerLocation.getWorld(), playerLocation.getX(), playerLocation.getY(), playerLocation.getZ());
            Location checkpointLocationWihtoutYaw = new Location(checkpointLocation.getWorld(), checkpointLocation.getX(), checkpointLocation.getY(), checkpointLocation.getZ());
            Bukkit.getLogger().info(playerLocationWihtoutYaw.toString() + " Checkpoint:" + checkpointLocationWihtoutYaw.toString());
            if (playerLocationWihtoutYaw.equals(checkpointLocationWihtoutYaw)) {
                ParkourMap.getInstance().setCheckpoint(player, checkpoint + 1);
                player.sendMessage("Checkpoint");
                player.sendMessage("Checkpoint " + (checkpoint + 1) + " reached");
            }
        }
    }

    private Location getCheckPointLocation(Parkour parkour, int checkpoint) {
        List<Location> checkpoints = parkour.getCheckpoints();
        if (checkpoint >= 0 && checkpoint <= checkpoints.size()) {
            return checkpoints.get(checkpoint);
        }
        Bukkit.getLogger().warning("Invalid checkpoint number");
        return null; // return null if the checkpoint number is invalid
    }

    public void finishParkour(Player player) {
        player.sendMessage("You finished the parkour in " + ParkourMap.getInstance().getTime(player).toString());
        stopParkour(player);
    }
    public boolean doesParkourExist(String parkourName) {
        try {
            return parkourSqlManager.getParkour(parkourName) != null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ItemStack getLeaveItem() {
        ItemStack item = new ItemStack(Material.RED_CONCRETE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Leave Parkour");
        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "unique"), PersistentDataType.STRING, "leaveParkour");

        item.setItemMeta(meta);
        return item;
    }
    private Parkour getParkourObjectByName(String parkourName) {
        try {
            Parkour parkour = parkourSqlManager.getParkour(parkourName);
            return parkour;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
