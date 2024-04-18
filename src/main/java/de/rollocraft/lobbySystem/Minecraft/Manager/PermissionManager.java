package de.rollocraft.lobbySystem.Minecraft.Manager;

import org.bukkit.entity.Player;

import net.luckperms.api.query.QueryOptions;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;

public class PermissionManager {
    public String getPlayerPrefix(Player player) {
        LuckPerms api = LuckPermsProvider.get();
        User user = api.getUserManager().getUser(player.getUniqueId());
        if (user != null) {
            CachedMetaData metaData = user.getCachedData().getMetaData(QueryOptions.defaultContextualOptions());
            if (metaData.getPrefix() == null) {
                return "Spieler";
            }
            return metaData.getPrefix();
        }
        return null;
    }
}
