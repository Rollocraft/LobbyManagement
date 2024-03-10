package de.rollocraft.pridetuvelobby.Listener;

import de.rollocraft.pridetuvelobby.Manager.XpManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerChatEvent;

public class PlayerChatListener implements Listener {
    private XpManager xpManager;
    public PlayerChatListener(XpManager xpManager) {
        this.xpManager = xpManager;
    }
        @EventHandler
        public void onPlayerChat (PlayerChatEvent event){
            Player player = event.getPlayer();
            xpManager.addXp(player, 5);

    }
}
