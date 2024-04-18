package de.rollocraft.lobbySystem.Minecraft.Objects;

import org.bukkit.Location;
import java.util.List;

public class Parkour {
    private String name;
    private String description;
    private Location start;
    private List<Location> checkpoints;
    private Location end;
    private int resetHigh;

    public Parkour(String name, String description, Location start, List<Location> checkpoints, Location end, int resetHigh) {
        this.name = name;
        this.description = description;
        this.start = start;
        this.checkpoints = checkpoints;
        this.end = end;
        this.resetHigh = resetHigh;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Location getStart() {
        return start;
    }

    public List<Location> getCheckpoints() {
        return checkpoints;
    }

    public Location getEnd() {
        return end;
    }

    public int getResetHigh() {
        return resetHigh;
    }
    public Location getCheckpointLocation(int checkpointNumber) {
        if (checkpointNumber < 0 || checkpointNumber >= checkpoints.size()) {
            throw new IllegalArgumentException("Invalid checkpoint number");
        }
        return checkpoints.get(checkpointNumber);
    }

    public boolean isCheckpoint(Location location) {
        return this.checkpoints.contains(location);
    }
}
