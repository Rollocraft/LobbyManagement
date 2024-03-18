package de.rollocraft.lobbySystem.Utils.Maps;

import org.bukkit.entity.Player;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class PlayerSecretKeyMap {
    private static PlayerSecretKeyMap instance;
    private final Map<Player, SecretKey> playerSecretKeys;

    private PlayerSecretKeyMap() {
        playerSecretKeys = new HashMap<>();
    }

    public static synchronized PlayerSecretKeyMap getInstance() {
        if (instance == null) {
            instance = new PlayerSecretKeyMap();
        }
        return instance;
    }

    public void removePlayer(Player player) {
        playerSecretKeys.remove(player);
    }

    public SecretKey getSecretKeyForPlayer(Player player) {
        return playerSecretKeys.get(player);
    }

    public void setSecretKeyForPlayer(Player player, SecretKey secretKey) {
        playerSecretKeys.put(player, secretKey);
    }

    public static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        return keyGen.generateKey();
    }
}