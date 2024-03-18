package de.rollocraft.lobbySystem.Threads;

import de.rollocraft.lobbySystem.Objects.Time;

public class Timer extends Thread{
    private int time = 0;
    private boolean running = true;

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000); // Pause für eine Sekunde
                time++; // Erhöhe die Variable um 1
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }
    }

    // Eigentlich unwichtig aber trotzdem mal da
    public void stopThread() {
        running = false;
    }
    public boolean isRunning() {
        return running;
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
}

