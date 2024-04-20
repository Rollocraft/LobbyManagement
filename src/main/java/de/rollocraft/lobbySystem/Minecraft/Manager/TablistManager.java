package de.rollocraft.lobbySystem.Minecraft.Manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TablistManager {
    private PermissionManager permissionManager;

    public TablistManager(PermissionManager permissionManager) {
        this.permissionManager = permissionManager;
    }

    public void setTabList() {
    Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

    for (Player player : Bukkit.getOnlinePlayers()) {
        String prefix = permissionManager.getPlayerPrefix(player);

        // Skip player if prefix is null
        if (prefix == null) {
            continue;
        }

        String displayName = "[" + prefix + "] " + player.getName();
        player.setDisplayName(displayName);
        player.setPlayerListName(displayName);

        // Registriere ein Team um das Prefix anzuzeigen
        Team team = scoreboard.getTeam(prefix);
        if (team == null) {
            team = scoreboard.registerNewTeam(prefix);
        }
        team.addEntry(player.getName());
        team.setPrefix("[" + prefix + "] ");

    }
}
}
