package de.rollocraft.lobbySystem.Minecraft.Listener.HubProtection;

import de.rollocraft.lobbySystem.Minecraft.Objects.Parkour;
import de.rollocraft.lobbySystem.Minecraft.Utils.Maps.ParkourMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        ParkourMap parkourMap = ParkourMap.getInstance();
        if (!parkourMap.isInParkour(player)) {
            return;
        }
        if (player.getLocation().getY() < parkourMap.getResetHigh(player)) {
            int Checkpoint = parkourMap.getCheckpoint(player);
            Parkour parkour = parkourMap.getParkour(player);
            parkour.getCheckpointLocation(Checkpoint);
            player.teleport(parkour.getCheckpointLocation(Checkpoint));

        }
    }
}
