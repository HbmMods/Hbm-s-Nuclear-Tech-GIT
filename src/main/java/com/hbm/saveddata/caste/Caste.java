package com.hbm.saveddata.caste;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Caste {
	private UUID id;
	private String name;
	private List<PlayerData> members;
	private List<String> invitees;

	public Caste(String name) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.members = new ArrayList<PlayerData>();
		this.invitees = new ArrayList<String>();
	}

	public Caste() {
		this.members = new ArrayList<>();
		this.invitees = new ArrayList<>();
	}

	public UUID getId() {
		return id;
	}

	public List<PlayerData> getMembers() {
		return this.members;
	}

	public String getName() {
		return name;
	}

	public void addMember(PlayerData playerData) {
		this.members.add(playerData);
	}

	public List<String> getInvitedPlayers() {
		return this.invitees;
	}



	public void readFromNBT(NBTTagCompound nbt) {
		this.id = UUID.fromString(nbt.getString("id"));
		this.name = nbt.getString("name");

		NBTTagList memberList = nbt.getTagList("members", 10);
		for (int i = 0; i < memberList.tagCount(); i++) {
			NBTTagCompound memberNBT = memberList.getCompoundTagAt(i);
			UUID playerUUID = UUID.fromString(memberNBT.getString("playerUUID"));
			Role role = Role.valueOf(memberNBT.getString("role"));
			UUID casteId = UUID.fromString(memberNBT.getString("casteId"));
			String playerName = memberNBT.getString("playerName");
			PlayerData playerData = new PlayerData(playerUUID, role, casteId, playerName);
			this.members.add(playerData);
		}

		NBTTagList inviteeList = nbt.getTagList("invitees", 8);
		for (int i = 0; i < inviteeList.tagCount(); i++) {
			this.invitees.add(inviteeList.getStringTagAt(i));
		}
	}

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("id", this.id.toString());
		nbt.setString("name", this.name);

		NBTTagList memberList = new NBTTagList();
		for (PlayerData member : this.members) {
			NBTTagCompound memberNBT = new NBTTagCompound();
			member.writeToNBT(memberNBT);
			memberList.appendTag(memberNBT);
		}
		nbt.setTag("members", memberList);

		NBTTagList inviteeList = new NBTTagList();
		for (String invitee : this.invitees) {
			NBTTagCompound inviteeNBT = new NBTTagCompound();
			inviteeNBT.setString("invitee", invitee);
			// Add more data to inviteeNBT here if needed
			inviteeList.appendTag(inviteeNBT);
		}
		nbt.setTag("invitees", inviteeList);
	}
}
