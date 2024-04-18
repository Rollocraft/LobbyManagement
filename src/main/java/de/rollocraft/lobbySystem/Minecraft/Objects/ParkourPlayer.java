package de.rollocraft.lobbySystem.Minecraft.Objects;

public class ParkourPlayer {
    private Parkour parkour;
    private Time time;
    private int checkpoint;
    public ParkourPlayer(Parkour parkour, Time time, int checkpoint) {
        this.parkour = parkour;
        this.time = time;
        this.checkpoint = checkpoint;
    }
    public Parkour getParkour() {
        return parkour;
    }
    public Time getTime() {
        return time;
    }
    public int getCheckpoint() {
        return checkpoint;
    }
    public void setCheckpoint(int checkpoint) {
        this.checkpoint = checkpoint;
    }
    public int getResetHigh() {
        return parkour.getResetHigh();
    }
    public void updateTime(Time time) {
        this.time = time;
    }
}
