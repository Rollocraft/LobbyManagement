package de.rollocraft.lobbySystem.Manager;

import de.rollocraft.lobbySystem.Database.Sql.Tabels.HologramSqlManager;
import de.rollocraft.lobbySystem.Objects.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class HologramManager {
    private HologramSqlManager hologramSqlManager;

    public HologramManager(HologramSqlManager hologramSqlManager) {
        this.hologramSqlManager = hologramSqlManager;
    }

    public void createHologram(Location location, String... lines) {
        String groupname;
        do {
            groupname = generateRandomGroupname();
        } while (hologramSqlManager.groupExists(groupname));

        for (String line : lines) {
            Hologram hologram = new Hologram(line);
            hologram.setupArmorStand(location);
            hologramSqlManager.addHologram(location, line, groupname);
            location.subtract(0, 0.25, 0);
        }
    }

    public int removeHologram(Location location) {
        for (Entity entity : location.getWorld().getEntities()) {
            if (entity instanceof ArmorStand) {
                ArmorStand armorStand = (ArmorStand) entity;
                if (armorStand.getLocation().distanceSquared(location) <= 4) { // 2 block radius
                    Bukkit.getLogger().info("ArmorStand found, visibility: " + armorStand.isVisible());
                    String text = armorStand.getCustomName();
                    String hologramText = hologramSqlManager.getHologramText(entity.getLocation());
                    if (hologramText != null && hologramText.equals(text)) {
                        String groupname = hologramSqlManager.getHologramGroup(entity.getLocation());
                        List<Location> groupLocations = hologramSqlManager.getHologramsInGroup(groupname);
                        for (Location groupLocation : groupLocations) {
                            Optional<Entity> removeEntityOptional = location.getWorld().getNearbyEntities(groupLocation, 1, 1, 1).stream()
                                    .filter(e -> e instanceof ArmorStand)
                                    .findFirst();
                            if (removeEntityOptional.isPresent()) {
                                Entity removeEntity = removeEntityOptional.get();
                                removeEntity.remove();
                                hologramSqlManager.deleteHologram(groupLocation);
                            } else {
                                Bukkit.getLogger().warning("Weird Error, entity should be found but wasn't");
                                return 2; // Some error occurred
                            }
                        }
                        return 0; // Success
                    }
                    return 1; // Hologram not found
                }
                return 1; // Hologram not found
            }
        }
        return 2; // Some error occurred
    }

    private String generateRandomGroupname() {
        Random random = new Random();
        StringBuilder groupname = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {
            char randomChar = (char) ('a' + random.nextInt(26)); // Generiert einen zufälligen Buchstaben von a bis z
            groupname.append(randomChar);
        }

        return groupname.toString();
    }
}