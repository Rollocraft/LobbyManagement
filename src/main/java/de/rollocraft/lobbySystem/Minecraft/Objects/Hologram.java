package de.rollocraft.lobbySystem.Minecraft.Objects;

import de.rollocraft.lobbySystem.Minecraft.Database.Sql.Tabels.HologramSqlManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

public final class Hologram {
    private String line;

    public Hologram(String line) {
        this.line = line;
    }

    public void setupArmorStand(Location location) {
        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);

        armorStand.setGravity(false);
        armorStand.setInvulnerable(true);
        armorStand.setVisible(false);

        String coloredLine = ChatColor.translateAlternateColorCodes('&', line);

        armorStand.setCustomName(coloredLine);
        armorStand.setCustomNameVisible(true);
    }
}