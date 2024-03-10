package de.rollocraft.pridetuvelobby.Threads;

import de.rollocraft.pridetuvelobby.Manager.ScoreboardManager;
import de.rollocraft.pridetuvelobby.Manager.TablistManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import de.rollocraft.pridetuvelobby.Main; // Stellen Sie sicher, dass Sie Ihre Hauptklasse importieren

public class Update extends Thread{
    private int time = 0;
    private boolean running = true;
    private final ScoreboardManager scoreboardManager;
    private TablistManager tablistManager;
    public Update(ScoreboardManager scoreboardManager, TablistManager tablistManager) {
        this.scoreboardManager = scoreboardManager;
        this.tablistManager = tablistManager;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000); // Pause für eine Sekunde
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                        scoreboardManager.updateScoreboard(player);
                        tablistManager.setTabList();
                    });
                }

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