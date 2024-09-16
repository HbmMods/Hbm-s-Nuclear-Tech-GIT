package com.hbm.saveddata.caste;

import net.minecraft.nbt.NBTTagCompound;
import java.util.UUID;

public class PlayerData {
    private final UUID playerUUID;
    private Role role;
    private UUID casteId;

    public PlayerData(UUID playerUUID, Role role, UUID casteId) {
        this.playerUUID = playerUUID;
        this.role = role;
        this.casteId = casteId;
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

    public NBTTagCompound writeToNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("Role", role.name());
        compound.setString("CasteId", casteId.toString());
        return compound;
    }

    public void readFromNBT(NBTTagCompound compound) {
        this.role = Role.valueOf(compound.getString("Role"));
        this.casteId = UUID.fromString(compound.getString("CasteId"));
    }
}