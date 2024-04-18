package de.rollocraft.lobbySystem.Minecraft.Commands;

import de.rollocraft.lobbySystem.Minecraft.Utils.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SocialCommand implements CommandExecutor {
    private ConfigManager configManager;

    public SocialCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                String subcommand = args[0];
                String url = configManager.get().getString("socialcommand." + subcommand);
                if (url != null) {
                    player.sendMessage(subcommand + ": " + url);
                } else {
                    player.sendMessage("No URL found for " + subcommand);
                }
            } else {
                player.sendMessage("Please specify a subcommand");
            }
        }
        return true;
    }
}
