package com.hbm.saveddata.caste;

import java.util.*;

public class Caste {
    private String name;
    private final UUID id;
    private final List<PlayerData> members;
    private List<String> invitedPlayers;

    public Caste(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
        this.members = new ArrayList<>();
        this.invitedPlayers = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public List<PlayerData> getMembers() {
        return members;
    }

    public void addMember(PlayerData playerData) {
        this.members.add(playerData);
    }

    public void removeMember(UUID playerUUID) {
        this.members.removeIf(member -> member.getPlayerUUID().equals(playerUUID));
    }

    public List<String> getInvitedPlayers() {
        return this.invitedPlayers;
    }
}