package de.rollocraft.lobbySystem.Minecraft.Threads;

import de.rollocraft.lobbySystem.Minecraft.Objects.Time;
import de.rollocraft.lobbySystem.Minecraft.Utils.Maps.ParkourMap;
import org.bukkit.entity.Player;

public class Timer extends Thread {
    private int time = 0;
    private long lasttime = 0;
    private boolean running = true;

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000); // Pause für eine Sekunde
                time++;
                ParkourMap parkourMap = ParkourMap.getInstance();
                for (Player player : parkourMap.getAllPlayers()) {
                    Time oldTime = parkourMap.getTime(player);
                    Time newTime = new  Time(0, 0, 0, 1);
                    Time finalTime = oldTime.add(newTime);
                    parkourMap.updateTime(player, finalTime);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lasttime = System.currentTimeMillis();
        }
    }

    public void stopThread() {
        running = false;
    }

    public synchronized Time getTime() {
        int totalSeconds = time;
        int days = totalSeconds / (24 * 60 * 60);
        totalSeconds %= 24 * 60 * 60;
        int hours = totalSeconds / (60 * 60);
        totalSeconds %= 60 * 60;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return new Time(days, hours, minutes, seconds);
    }

    public long getLastTime() {
        return lasttime;
    }

}




