package de.rollocraft.lobbySystem.Minecraft.Manager;

import de.rollocraft.lobbySystem.Minecraft.Database.Sql.Tabels.BlockParticelSqlManager;
import de.rollocraft.lobbySystem.Minecraft.Objects.Effects;
import org.bukkit.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockParticleManager {
    private BlockParticelSqlManager blockParticelSqlManager;

    public BlockParticleManager(BlockParticelSqlManager blockParticelSqlManager) {
        this.blockParticelSqlManager = blockParticelSqlManager;
    }

    public void createBlockParticle(Effects effect) {
        spawnParticle(effect);
        blockParticelSqlManager.addEffect(effect);
    }

    public void spawnParticle(Effects effect) {
        Particle particle = effect.getType();
        Location location = effect.getLocation();
        World world = location.getWorld();
        if (particle.equals(Particle.REDSTONE)) {
            Color color = getColorByName(effect.getColor());
            Particle.DustOptions dustOptions = new Particle.DustOptions(color, 3); // Increase size to 3
            world.spawnParticle(particle, location, effect.getIntensity(), dustOptions);
        } else {
            world.spawnParticle(particle, location, effect.getIntensity());
        }
    }

    public List<Effects> getAllEffects() {
        return blockParticelSqlManager.getAllEffects();
    }

    public void deleteBlockParticle(Location location) {

        List<Effects> allEffects = blockParticelSqlManager.getAllEffects();
        for (Effects effect : allEffects) {
            double distanceSquared = effect.getLocation().distanceSquared(location);
            if (distanceSquared <= 4) {
                blockParticelSqlManager.deleteEffect(effect.getLocation());
            }
        }
    }

    private Color getColorByName(String name) {
        return getColorMap().get(name.toUpperCase());
    }

    private Map<String, Color> getColorMap() {
        Map<String, Color> colorMap = new HashMap<>();
        colorMap.put("RED", Color.RED);
        colorMap.put("BLUE", Color.BLUE);
        colorMap.put("GREEN", Color.GREEN);
        colorMap.put("YELLOW", Color.YELLOW);
        colorMap.put("AQUA", Color.AQUA);
        colorMap.put("BLACK", Color.BLACK);
        colorMap.put("FUCHSIA", Color.FUCHSIA);
        colorMap.put("GRAY", Color.GRAY);
        colorMap.put("LIME", Color.LIME);
        colorMap.put("MAROON", Color.MAROON);
        colorMap.put("NAVY", Color.NAVY);
        colorMap.put("OLIVE", Color.OLIVE);
        colorMap.put("ORANGE", Color.ORANGE);
        colorMap.put("PURPLE", Color.PURPLE);
        colorMap.put("SILVER", Color.SILVER);
        colorMap.put("TEAL", Color.TEAL);
        colorMap.put("WHITE", Color.WHITE);

        return colorMap;
    }
}