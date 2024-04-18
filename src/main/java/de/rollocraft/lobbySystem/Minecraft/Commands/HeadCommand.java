package de.rollocraft.lobbySystem.Minecraft.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class HeadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                String targetPlayerName = args[0];
                OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(targetPlayerName);
                if (targetPlayer != null) {
                    ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                    skullMeta.setOwningPlayer(targetPlayer);
                    skull.setItemMeta(skullMeta);
                    player.getInventory().addItem(skull);
                    player.sendMessage("You received the head of " + targetPlayerName);
                } else {
                    player.sendMessage("Player not found");
                }
            } else {
                player.sendMessage("Please specify a player");
            }
        }
        return true;
    }
}