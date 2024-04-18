package de.rollocraft.lobbySystem.Minecraft.Objects;

import org.bukkit.entity.Player;

public class Duel {
    private Player target;
    private Player player;
    private String map;
    private String kit;

    public Duel(Player player, Player target, String map, String kit) {
        this.player = player;
        this.target = target;
        this.map = map;
        this.kit = kit;

    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getKit() {
        return kit;
    }

    public void setKit(String kit) {
        this.kit = kit;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getTarget() {
        return target;
    }

    public void setTarget(Player target) {
        this.target = target;
    }
    @Override
    public String toString() {
        return "Duel{" +
                "target=" + target.getName() +
                ", player=" + player.getName() +
                ", map='" + map + '\'' +
                ", kit='" + kit + '\'' +
                '}';
    }
}