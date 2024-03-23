package de.rollocraft.lobbySystem.Threads;

import de.rollocraft.lobbySystem.Manager.BlockParticleManager;
import de.rollocraft.lobbySystem.Manager.ScoreboardManager;
import de.rollocraft.lobbySystem.Manager.TablistManager;
import de.rollocraft.lobbySystem.Manager.XpManager;
import de.rollocraft.lobbySystem.Objects.Effects;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import de.rollocraft.lobbySystem.Main; // Stellen Sie sicher, dass Sie Ihre Hauptklasse importieren

public class Update extends Thread {
    private long lasttime = 0;
    private boolean running = true;
    private final ScoreboardManager scoreboardManager;
    private TablistManager tablistManager;
    private XpManager xpManager;
    private BlockParticleManager blockParticleManager;

    public Update(ScoreboardManager scoreboardManager, TablistManager tablistManager, XpManager xpManager, BlockParticleManager blockParticleManager) {
        this.scoreboardManager = scoreboardManager;
        this.tablistManager = tablistManager;
        this.xpManager = xpManager;
        this.blockParticleManager = blockParticleManager;
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

                        int lvl = xpManager.getLvl(player);
                        player.setLevel(lvl);
                        int remainingXp = xpManager.getRemainingXpForNextLevel(player);
                        int xpForNextLevel = xpManager.xpForLevel(lvl + 1);
                        float progress = (float) remainingXp / xpForNextLevel;
                        player.setExp(progress);
                    });
                }
                for (Effects effects : blockParticleManager.getAllEffects()) {
                    blockParticleManager.spawnParticle(effects);
                }

                lasttime = System.currentTimeMillis();


            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }
    }

    public void stopThread() {
        running = false;
    }

    public long getLastTime() {
        return lasttime;
    }
}