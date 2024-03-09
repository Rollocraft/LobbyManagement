package de.rollocraft.pridetuvelobby.Manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.*;
import org.bukkit.ChatColor;

public class ScoreboardManager implements Listener {
    private  TimeManager timeManager;
    public ScoreboardManager(TimeManager timeManager) {
        this.timeManager = timeManager;
    }

    public void updateScoreboard(Player p) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        String time =  timeManager.getTime(p).toString();


        Objective objective = board.registerNewObjective("Pridetuve", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.getScore(ChatColor.RED + "").setScore(10);
        objective.getScore(ChatColor.GOLD + "Rank").setScore(9);
        objective.getScore(ChatColor.GREEN + "  -> Soon  ").setScore(8);
        objective.getScore(ChatColor.BLUE + "").setScore(7);
        objective.getScore(ChatColor.BLUE + "Playtime").setScore(6);
        objective.getScore(ChatColor.WHITE + "  - " +  time).setScore(5);
        objective.getScore(ChatColor.RED + "").setScore(4);
        objective.getScore(ChatColor.GOLD + "Level").setScore(3);
        objective.getScore(ChatColor.GREEN + "  -> Soon  ").setScore(2);
        objective.getScore(ChatColor.GREEN + "").setScore(1);
        objective.getScore(ChatColor.GOLD + "pridetuve.de").setScore(0);

        p.setScoreboard(board);


    }
}