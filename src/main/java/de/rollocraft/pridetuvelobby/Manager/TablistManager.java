package de.rollocraft.pridetuvelobby.Manager;

import de.rollocraft.pridetuvelobby.Objects.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TablistManager {
    private final PermissionManager permissionManager;

    public TablistManager(PermissionManager permissionManager) {
        this.permissionManager = permissionManager;
    }

    public void setTabList() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        for (Player player : Bukkit.getOnlinePlayers()) {
            Permission permission = permissionManager.getPermission(player);
            String rank = permission != null ? permission.getRank() : "Spieler";

            player.setDisplayName("[" + rank + "] " + player.getName());

            Team team = scoreboard.getTeam(player.getName());
            if (team == null) {
                team = scoreboard.registerNewTeam(player.getName());
            }

            // Set the prefix of the team to the rank
            team.setPrefix("[" + rank + "] ");

            // Add the player to the team
            team.addEntry(player.getName());

            String header = " " + "\n" + "test" + "\n" + " " + "\n" + "123" + "\n" + "Lol";
            String footer = " " + "\n" + "test" + "\n" + " " + "\n" + "123" + "\n" + "Lol" + "\n" + "Rang: " + rank;

            player.setPlayerListHeader(header);
            player.setPlayerListFooter(footer);
        }
    }
}
