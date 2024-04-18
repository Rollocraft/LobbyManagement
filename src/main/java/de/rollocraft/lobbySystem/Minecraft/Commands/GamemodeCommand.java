package de.rollocraft.lobbySystem.Minecraft.Commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                String mode = args[0];
                switch (mode) {
                    case "1":
                    case "c":
                    case "creative":
                        if (!player.hasPermission("lobbySystem.command.gamemode.creative")) {
                            player.sendMessage("You do not have permission to use this game mode");
                            break;
                        }
                        player.setGameMode(GameMode.CREATIVE);
                        player.sendMessage("Game mode set to Creative");
                        break;
                    case "2":
                    case "s":
                    case "survival":
                        if (!player.hasPermission("lobbySystem.command.gamemode.survival")) {
                            player.sendMessage("You do not have permission to use this game mode");
                            break;
                        }
                        player.setGameMode(GameMode.SURVIVAL);
                        player.sendMessage("Game mode set to Survival");
                        break;
                    case "3":
                    case "spec":
                    case "spectator":
                        if (!player.hasPermission("lobbySystem.command.gamemode.spectator")) {
                            player.sendMessage("You do not have permission to use this game mode");
                            break;
                        }
                        player.setGameMode(GameMode.SPECTATOR);
                        player.sendMessage("Game mode set to Spectator");
                        break;
                    case "4":
                    case "a":
                    case "adventure":
                        if (!player.hasPermission("lobbySystem.command.gamemode.adventure")) {
                            player.sendMessage("You do not have permission to use this game mode");
                            break;
                        }
                        player.setGameMode(GameMode.ADVENTURE);
                        player.sendMessage("Game mode set to Adventure");
                        break;
                    default:
                        player.sendMessage("Invalid game mode");
                        break;
                }
            } else {
                player.sendMessage("Please specify a game mode");
            }
        }
        return true;
    }
}