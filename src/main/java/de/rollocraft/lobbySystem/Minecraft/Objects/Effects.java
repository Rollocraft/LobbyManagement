package de.rollocraft.lobbySystem.Minecraft.Objects;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.checkerframework.checker.units.qual.C;

public class Effects {
    private Location location;
    private Particle type;
    private String color;
    private int intensity;

    public Effects(Location location, String color, Particle type, int intensity) {
        this.location = location;
        this.color = color;
        this.type = type;
        this.intensity = intensity;
    }

    public Location getLocation() {
        return location;
    }

    public String getColor() {
        return color;
    }

    public Particle getType() {
        return type;
    }

    public int getIntensity() {
        return intensity;
    }
}