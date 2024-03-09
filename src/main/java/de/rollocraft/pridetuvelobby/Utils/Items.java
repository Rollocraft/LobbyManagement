package de.rollocraft.pridetuvelobby.Utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
}
