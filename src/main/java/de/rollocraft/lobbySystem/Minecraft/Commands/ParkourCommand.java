package de.rollocraft.lobbySystem.Minecraft.Commands;

import de.rollocraft.lobbySystem.Minecraft.Manager.ParkourManager;
import de.rollocraft.lobbySystem.Minecraft.Objects.Parkour;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ParkourCommand implements CommandExecutor {
    private final ParkourManager parkourManager;

    public ParkourCommand(ParkourManager parkourManager) {
        this.parkourManager = parkourManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 2) {
            player.sendMessage("Usage: /parkour start <name>");
            return true;
        }

        String action = args[0];
        String name = args[1];

        if ("start".equalsIgnoreCase(action)) {

            if (!parkourManager.doesParkourExist(name)) {
                player.sendMessage("Parkour with name " + name + " does not exist.");
                return true;
            } else {
                player.sendMessage("Starting parkour " + name);
                parkourManager.startParkour(player, name);
            }
        }
        return true;
    }
}