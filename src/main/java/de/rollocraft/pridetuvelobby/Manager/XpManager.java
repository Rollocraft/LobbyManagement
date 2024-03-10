package de.rollocraft.pridetuvelobby.Manager;

import de.rollocraft.pridetuvelobby.Database.Tables.XpDatabaseManager;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class XpManager {
    private XpDatabaseManager xpDatabaseManager;

    public XpManager(XpDatabaseManager xpDatabaseManager) {
        this.xpDatabaseManager = xpDatabaseManager;
    }

    public int getXp(Player player) {
        try {
            int xp = xpDatabaseManager.getXp(player);
            return xp;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addXp(Player player, int xp) {
        try {
            xpDatabaseManager.addXp(player, xp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getLvl(Player player) {
        try {
            int xp = xpDatabaseManager.getXp(player);
            return calculateLevel(xp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getRemainingXpForNextLevel(Player player) {
        try {
            int xp = xpDatabaseManager.getXp(player);
            int currentLevel = calculateLevel(xp);
            int nextLevelXp = xpForLevel(currentLevel + 1);
            return nextLevelXp - xp;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int xpForLevel(int level) {
    double requiredXp = 1; // Start XP

    for (int i = 1; i < level; i++) {
        requiredXp *= 1.4; // Increase required XP by 40%
    }

    return (int) Math.round(requiredXp);
}

    public int calculateLevel(int xp) {
    int level = 0;
    double requiredXp = 1; // Start XP

    while (requiredXp <= xp) {
        level++;
        requiredXp *= 1.4; // Increase required XP by 40%
    }

    return level;
}
}