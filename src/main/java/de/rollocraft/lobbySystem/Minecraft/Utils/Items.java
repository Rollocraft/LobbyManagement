package de.rollocraft.lobbySystem.Minecraft.Utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class Items {

    public static ItemStack ServerManagerItem() {
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta meta = compass.getItemMeta();

        meta.setDisplayName("Server Manager");
        List<String> lore = new ArrayList<>();
        lore.add("Right-click to open server manager");
        meta.setLore(lore);

        compass.setItemMeta(meta);
        return compass;
    }

    public static ItemStack PlayerHeadItem(Player player) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();

        meta.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
        meta.setDisplayName(player.getName() + " stats");
        List<String> lore = new ArrayList<>();
        lore.add("Right-click to your stats");
        meta.setLore(lore);

        head.setItemMeta(meta);
        return head;
    }


    public static ItemStack GraphlinHook() {
        ItemStack hook = new ItemStack(Material.FISHING_ROD);
        ItemMeta meta = hook.getItemMeta();

        meta.displayName(Component.text("Grappling Hook"));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Throw to graple"));
        meta.lore(lore);

        hook.setItemMeta(meta);
        return hook;
    }
}
