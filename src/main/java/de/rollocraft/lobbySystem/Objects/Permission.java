package de.rollocraft.lobbySystem.Objects;

import java.util.List;

public class Permission {
    private String rank;
    private String prefix;
    private List<String> permissions;
    private List<String> players;

    public Permission(String rank, String prefix, List<String> permissions, List<String> players) {
        this.rank = rank;
        this.prefix = prefix;
        this.permissions = permissions;
        this.players = players;
    }

    public String getRank() {
        return rank;
    }

    public String getPrefix() {
        return prefix;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public List<String> getPlayers() {
        return players;
    }
}
