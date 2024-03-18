package de.rollocraft.lobbySystem.Listener;

import de.rollocraft.lobbySystem.Manager.SetupPvpKitManager;
import de.rollocraft.lobbySystem.Manager.SetupPvpMapManager;
import de.rollocraft.lobbySystem.Manager.XpManager;
import de.rollocraft.lobbySystem.Utils.Enum.SetupKitState;
import de.rollocraft.lobbySystem.Utils.Enum.SetupMapState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerChatEvent;

public class PlayerChatListener implements Listener {
    private XpManager xpManager;
    private SetupPvpMapManager setupMapManager;
    private SetupPvpKitManager setupKitManager;

    public PlayerChatListener(XpManager xpManager, SetupPvpMapManager setupMapManager, SetupPvpKitManager setupKitManager) {
        this.xpManager = xpManager;
        this.setupMapManager = setupMapManager;
        this.setupKitManager = setupKitManager;
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        SetupMapState state = setupMapManager.getSetupState(player);
        SetupKitState kitState = setupKitManager.getSetupState(player);

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
        } else {
            xpManager.addXp(player, 5);
        }
    }
}
