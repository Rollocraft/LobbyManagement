package de.rollocraft.pridetuvelobby.Manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class TablistManager {
    public void setTabList() {

        String header = " " + "\n" + "test" + "\n" + " " + "\n" + "123" + "\n" + "Lol";
        String footer = " " + "\n" + "test" + "\n" + " " + "\n" + "123" + "\n" + "Lol";

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setPlayerListHeader(header);
            player.setPlayerListFooter(footer);
        }
    }
}

