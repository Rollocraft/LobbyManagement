package de.rollocraft.pridetuvelobby.Threads;

public class Timer extends Thread{
    private int variable = 0;
    private boolean running = true;

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000); // Pause für eine Sekunde
                variable++; // Erhöhe die Variable um 1
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
}

