package de.rollocraft.pridetuvelobby.Utils.BungeeCord;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Bungee {
    public static void connectToServer(Player player, String serverName, boolean Message) {
        if (player == null || serverName == null || serverName.isEmpty()) {
            return;
        }

        ProxiedPlayer proxiedPlayer = (ProxiedPlayer) player;
        ServerInfo target = ProxyServer.getInstance().getServerInfo(serverName);
        if (Message) {
            proxiedPlayer.sendMessage(ChatColor.GREEN + "Sending you to " + serverName + "...");
        }
        proxiedPlayer.connect(target);
    }
}
