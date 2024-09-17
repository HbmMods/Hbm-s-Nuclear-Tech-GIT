package com.hbm.saveddata.caste;

import net.minecraft.nbt.NBTTagCompound;
import java.util.UUID;

public class PlayerData {
    private UUID playerUUID;
    private Role role;
    private UUID casteId;
	private String playerName;

    public PlayerData(UUID playerUUID, Role role, UUID casteId, String playerName) {
        this.playerUUID = playerUUID;
        this.role = role;
        this.casteId = casteId;
		this.playerName = playerName;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UUID getCasteId() {
        return casteId;
    }

    public void setCasteId(UUID casteId) {
        this.casteId = casteId;
    }

	public String getPlayerName() {
		return playerName;
	}


	public void readFromNBT(NBTTagCompound nbt) {
		this.playerUUID = UUID.fromString(nbt.getString("playerUUID"));
		this.role = Role.valueOf(nbt.getString("role"));
		this.casteId = UUID.fromString(nbt.getString("casteId"));
		this.playerName = nbt.getString("playerName");
	}

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("playerUUID", this.playerUUID.toString());
		nbt.setString("role", this.role.name());
		nbt.setString("casteId", this.casteId.toString());
		nbt.setString("playerName", this.playerName);
	}
}
