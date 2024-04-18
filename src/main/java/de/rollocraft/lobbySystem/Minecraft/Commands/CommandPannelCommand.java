package de.rollocraft.lobbySystem.Minecraft.Commands;

import de.rollocraft.lobbySystem.Minecraft.Utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CommandPannelCommand implements CommandExecutor {

    private final ConfigManager configManager;

    public CommandPannelCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by a player.");
            return true;
        }

        Player player = (Player) sender;
        FileConfiguration config = configManager.get();

        if (config.isConfigurationSection("commandpannel.examplepannel")) {
            String filler = config.getString("commandpannel.examplepannel.filler");
            List<String> slots = config.getStringList("commandpannel.examplepannel.slot");

            Inventory commandPanelInventory = Bukkit.createInventory(null, 9, "Command Panel");

            for (String slot : slots) {
                int slotIndex = Integer.parseInt(slot);
                ItemStack item = new ItemStack(Material.valueOf(filler));
                commandPanelInventory.setItem(slotIndex, item);
            }

            player.openInventory(commandPanelInventory);
        }

        return true;
    }
}