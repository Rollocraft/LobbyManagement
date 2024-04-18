package de.rollocraft.lobbySystem.Minecraft.Manager.Setup;

import de.rollocraft.lobbySystem.Minecraft.Database.Sql.Tabels.KitSqlManager;
import de.rollocraft.lobbySystem.Minecraft.Utils.Base64Util;
import de.rollocraft.lobbySystem.Minecraft.Utils.Enum.SetupKitState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SetupPvpKitManager {
    private Map<Player, SetupKitState> setupStates = new HashMap<>();
    private Map<Player, Map<String, Object>> setupValues = new HashMap<>();
    private KitSqlManager kitSqlManager;

    public SetupPvpKitManager(KitSqlManager kitSqlManager) {
        this.kitSqlManager = kitSqlManager;
    }

    public void startSetup(Player player) {
        setupStates.put(player, SetupKitState.CONFIRMATION);
        player.sendMessage("Are you sure you want to start the setup? Please write Yes/No");
    }

    public void handleConfirmation(Player player, String message) {
        if (message.equalsIgnoreCase("yes")) {
            setSetupState(player, SetupKitState.KIT_NAME);
            player.sendMessage("Please type in the kit name");
        } else if (message.equalsIgnoreCase("no")) {
            setSetupState(player, null);
            player.sendMessage("Setup cancelled");
        } else {
            player.sendMessage("Please write Yes/No");
        }
    }

    public void handleKitName(Player player, String message) {
        getSetupValues(player).put("KitName", message);
        setSetupState(player, SetupKitState.KIT_DESCRIPTION);
        player.sendMessage("Please type in the kit description");
    }

    public void handleKitDescription(Player player, String message) {
        getSetupValues(player).put("KitDescription", message);
        setSetupState(player, SetupKitState.CONFIRM_SETUP);
        player.sendMessage(formatSetupValues(player) + "Are you sure everything is correct? Please write Yes/No");
    }

    public void confirmSetup(Player player, String message) {
        if (message.equalsIgnoreCase("yes")) {
            try {
                ItemStack[] items = player.getInventory().getContents();
                String data = Base64Util.encode(Arrays.asList(items));

                //Speichern des Kits in die Sql
                kitSqlManager.addKit((String) getSetupValues(player).get("KitName"), (String) getSetupValues(player).get("KitDescription"), data);

            } catch (Exception e) {
                e.printStackTrace();
            }

            setSetupState(player, null);
            player.sendMessage("Setup completed");
        } else if (message.equalsIgnoreCase("no")) {
            setSetupState(player, null);
            player.sendMessage("Setup cancelled");
        } else {
            player.sendMessage("Please write Yes/No");
        }
    }

    private void setSetupState(Player player, SetupKitState state) {
        setupStates.put(player, state);
    }

    private Map<String, Object> getSetupValues(Player player) {
        return setupValues.computeIfAbsent(player, k -> new HashMap<>());
    }

    public SetupKitState getSetupState(Player player) {
        return setupStates.get(player);
    }

    private String formatSetupValues(Player player) {
        Map<String, Object> values = getSetupValues(player);
        StringBuilder sb = new StringBuilder();
        sb.append("Kit Name: ").append(values.get("KitName")).append("\n");
        sb.append("Kit Description: ").append(values.get("KitDescription")).append("\n");
        return sb.toString();
    }
}
