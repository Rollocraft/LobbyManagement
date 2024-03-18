package de.rollocraft.lobbySystem.Manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.*;
import org.bukkit.ChatColor;

public class ScoreboardManager implements Listener {
    private  TimeManager timeManager;
    private XpManager xpManager;
    private PermissionManager permissionManager;
    public ScoreboardManager(TimeManager timeManager, XpManager xpManager, PermissionManager permissionManager) {
        this.xpManager = xpManager;
        this.timeManager = timeManager;
        this.permissionManager = permissionManager;
    }

    public void updateScoreboard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        String time =  timeManager.getTime(player).toString();
        String prefix = permissionManager.getPlayerPrefix(player);


        Objective objective = board.registerNewObjective("Test", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.getScore(ChatColor.RED + "").setScore(10);
        objective.getScore(ChatColor.GOLD + "Rank").setScore(9);
        objective.getScore(ChatColor.WHITE + "  - " + prefix).setScore(8);
        objective.getScore(ChatColor.BLUE + "").setScore(7);
        objective.getScore(ChatColor.AQUA + "Playtime").setScore(6);
        objective.getScore(ChatColor.WHITE + "  - " +  time).setScore(5);
        objective.getScore(ChatColor.RED + "").setScore(4);
        objective.getScore(ChatColor.GOLD + "Level").setScore(3);
        objective.getScore(ChatColor.WHITE + "  - " + xpManager.getLvl(player)).setScore(2);
        objective.getScore(ChatColor.GREEN + "").setScore(1);
        objective.getScore(ChatColor.GOLD + "test.de").setScore(0);

        player.setScoreboard(board);
    }
}