package de.rollocraft.lobbySystem.Minecraft.Manager;

import de.rollocraft.lobbySystem.Minecraft.Utils.ConfigManager;
import de.rollocraft.lobbySystem.Minecraft.Utils.Maps.ParkourMap;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.*;
import org.bukkit.ChatColor;

public class ScoreboardManager implements Listener {
    private  TimeManager timeManager;
    private XpManager xpManager;
    private PermissionManager permissionManager;
    private ConfigManager configManager;
    public ScoreboardManager(TimeManager timeManager, XpManager xpManager, PermissionManager permissionManager, ConfigManager configManager) {
        this.xpManager = xpManager;
        this.timeManager = timeManager;
        this.permissionManager = permissionManager;
        this.configManager = configManager;
    }

    public void updateScoreboard(Player player, Scoreboard board) {
        player.setScoreboard(board);
    }

    public Scoreboard MainScoreboard(Player player){
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        String time =  timeManager.getTime(player).toString();
        String prefix = permissionManager.getPlayerPrefix(player);

        String title = configManager.get().getString("scoreboard.title");
        String domain = configManager.get().getString("scoreboard.domain");


        Objective objective = board.registerNewObjective(title, "dummy");
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
        objective.getScore(domain).setScore(0);

        return board;
    }

    public Scoreboard ParkourScoreboard(Player player){
        ParkourMap parkourMap = ParkourMap.getInstance();
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        String time =  parkourMap.getTime(player).toString();
        int checkpoint = parkourMap.getCheckpoint(player);

        String title = "Parkour";
        String domain = configManager.get().getString("scoreboard.domain");
        String parkourname = parkourMap.getParkour(player).getName();


        Objective objective = board.registerNewObjective(title, "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.getScore(ChatColor.BLUE + "").setScore(7);
        objective.getScore(ChatColor.AQUA + "Parkour").setScore(6);
        objective.getScore(ChatColor.WHITE + "  - " +  parkourname).setScore(5);
        objective.getScore(ChatColor.RED + "").setScore(4);
        objective.getScore(ChatColor.GOLD + "Checkpoint").setScore(3);
        objective.getScore(ChatColor.WHITE + "  - " + checkpoint).setScore(2);
        objective.getScore(ChatColor.GREEN + "").setScore(1);
        objective.getScore(domain).setScore(0);

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.WHITE + time));

        return board;
    }
}