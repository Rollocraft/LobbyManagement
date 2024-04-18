package de.rollocraft.lobbySystem.Minecraft.Utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Message {
    public static String PREFIX = "[LobbySystem] ";

    public static void sendMessage(Player player, String message) {
        player.sendMessage(PREFIX + message);
    }
    public static void returnMessage(CommandSender player, String message) {
        player.sendMessage(PREFIX + message);
    }
}
