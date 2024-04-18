package de.rollocraft.lobbySystem.Minecraft.Listener;

import de.rollocraft.lobbySystem.Minecraft.Manager.Setup.SetupParkourManager;
import de.rollocraft.lobbySystem.Minecraft.Manager.Setup.SetupPvpKitManager;
import de.rollocraft.lobbySystem.Minecraft.Manager.Setup.SetupPvpMapManager;
import de.rollocraft.lobbySystem.Minecraft.Manager.XpManager;
import de.rollocraft.lobbySystem.Minecraft.Utils.Enum.SetupParkourState;
import de.rollocraft.lobbySystem.Minecraft.Utils.Enum.SetupKitState;
import de.rollocraft.lobbySystem.Minecraft.Utils.Enum.SetupMapState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerChatEvent;

import static de.rollocraft.lobbySystem.Minecraft.Utils.Enum.SetupParkourState.*;
import static de.rollocraft.lobbySystem.Minecraft.Utils.Enum.SetupKitState.KIT_NAME;

public class PlayerChatListener implements Listener {
    private XpManager xpManager;
    private SetupPvpMapManager setupMapManager;
    private SetupPvpKitManager setupKitManager;
    private SetupParkourManager setupParkourManager;

    public PlayerChatListener(XpManager xpManager, SetupPvpMapManager setupMapManager, SetupPvpKitManager setupKitManager, SetupParkourManager setupParkourManager) {
        this.xpManager = xpManager;
        this.setupMapManager = setupMapManager;
        this.setupKitManager = setupKitManager;
        this.setupParkourManager = setupParkourManager;
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String format = "%1$s: %2$s";
        SetupMapState state = setupMapManager.getSetupState(player);
        SetupKitState kitState = setupKitManager.getSetupState(player);
        SetupParkourState parkourState = setupParkourManager.getSetupState(player);
        event.setFormat(format);

        if (state != null) {
            event.setCancelled(true);
            switch (state) {
                case CONFIRMATION:
                    setupMapManager.handleConfirmation(player, event.getMessage());
                    break;
                case MAP_NAME:
                    setupMapManager.handleMapName(player, event.getMessage());
                    break;
                case MAP_DESCRIPTION:
                    setupMapManager.handleMapDescription(player, event.getMessage());
                    break;
                case SPAWNPOINT1:
                    setupMapManager.handleSpawnpoint1(player, event.getMessage());
                    break;
                case SPAWNPOINT2:
                    setupMapManager.handleSpawnpoint2(player, event.getMessage());
                    break;
                case SPECTATOR:
                    setupMapManager.handleSpectator(player, event.getMessage());
                    break;
                case CONFIRM_SETUP:
                    setupMapManager.confirmSetup(player, event.getMessage());
                    break;
            }
        } else if (kitState != null) {
            event.setCancelled(true);
            switch (kitState) {
                case CONFIRMATION:
                    setupKitManager.handleConfirmation(player, event.getMessage());
                    break;
                case KIT_NAME:
                    setupKitManager.handleKitName(player, event.getMessage());
                    break;
                case KIT_DESCRIPTION:
                    setupKitManager.handleKitDescription(player, event.getMessage());
                    break;
                case CONFIRM_SETUP:
                    setupKitManager.confirmSetup(player, event.getMessage());
                    break;
            }
        }else if (setupParkourManager != null) {
            event.setCancelled(true);
            switch (parkourState) {
                case CONFIRMATION:
                    setupParkourManager.handleConfirmation(player, event.getMessage());
                    break;
                case PARKOUR_NAME:
                    setupParkourManager.handleParkourName(player, event.getMessage());
                    break;
                case PARKOUR_DESCRIPTION:
                    setupParkourManager.handleParkourDescription(player, event.getMessage());
                    break;
                case START:
                    setupParkourManager.handleStart(player, event.getMessage());
                    break;
                case CHECKPOINT:
                    setupParkourManager.handleCheckpoint(player, event.getMessage());
                    break;
                case END:
                    setupParkourManager.handleEnd(player, event.getMessage());
                    break;
                case RESET_HIGH:
                    setupParkourManager.handleResetHigh(player, event.getMessage());
                    break;
                case CONFIRM_SETUP:
                    setupParkourManager.confirmSetup(player, event.getMessage());
                    break;
            }
        } else {
            xpManager.addXp(player, 5);
        }
    }
}
