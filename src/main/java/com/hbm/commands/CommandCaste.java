package com.hbm.commands;

import com.hbm.saveddata.caste.Caste;
import com.hbm.saveddata.caste.CasteManager;
import com.hbm.saveddata.caste.PlayerData;
import com.hbm.saveddata.caste.Role;
import java.util.UUID;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class CommandCaste extends CommandBase {
    private CasteManager casteManager;

    public CommandCaste(CasteManager casteManager) {
        this.casteManager = casteManager;
    }

    @Override
    public String getCommandName() {
        return "ntmcaste";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/ntmcaste <subcommand> [arguments]";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayer)) {
            sender.addChatMessage(new ChatComponentText("This command can only be used by a player."));
            return;
        }

        EntityPlayer player = (EntityPlayer) sender;
        UUID playerUUID = player.getUniqueID();
        Caste playerCaste = null;
        PlayerData playerData = null;

        if (args.length < 1) {
            sender.addChatMessage(new ChatComponentText("Invalid usage. Use /ntmcaste <subcommand> [arguments]"));
            return;
        }

        String subcommand = args[0];

        switch (subcommand) {
            case "create":
                if (args.length < 2) {
                    sender.addChatMessage(new ChatComponentText("Invalid usage. Use /ntmcaste create <caste name>"));
                    return;
                }

                String casteName = args[1];

                // Check if the player is already in a caste
                for (Caste tempCaste : casteManager.getCastes()) {
                    for (PlayerData playerDataTemp : tempCaste.getMembers()) {
                        if (playerDataTemp.getPlayerUUID().equals(playerUUID)) {
                            sender.addChatMessage(new ChatComponentText("You are already in a caste."));
                            return;
                        }
                    }
                }

                // Check if a caste with the given name already exists
                for (Caste tempCaste : casteManager.getCastes()) {
                    if (tempCaste.getName().equals(casteName)) {
                        sender.addChatMessage(new ChatComponentText("A caste with this name already exists."));
                        return;
                    }
                }

                // Create the new caste and add the player as the leader
                Caste newCaste = new Caste(casteName);
                newCaste.addMember(new PlayerData(playerUUID, Role.LEADER, newCaste.getId()));
                casteManager.getCastes().add(newCaste);

                sender.addChatMessage(new ChatComponentText("Caste '" + casteName + "' created successfully."));
                break;

            case "leave":
                // Find the player's caste and player data
                for (Caste tempCaste : casteManager.getCastes()) {
                    for (PlayerData member : tempCaste.getMembers()) {
                        if (member.getPlayerUUID().equals(playerUUID)) {
                            playerCaste = tempCaste;
                            playerData = member;
                            break;
                        }
                    }
                    if (playerCaste != null) {
                        break;
                    }
                }

                if (playerCaste == null) {
                    sender.addChatMessage(new ChatComponentText("You are not in a caste."));
                    return;
                }

                // Remove the player from the caste
                playerCaste.getMembers().remove(playerData);

                // If the caste is now empty, delete it
                if (playerCaste.getMembers().isEmpty()) {
                    casteManager.getCastes().remove(playerCaste);
                }

                sender.addChatMessage(new ChatComponentText("You have left the caste '" + playerCaste.getName() + "'."));
                break;

            case "delete":
                // Find the player's caste and player data
                for (Caste tempCaste : casteManager.getCastes()) {
                    for (PlayerData member : tempCaste.getMembers()) {
                        if (member.getPlayerUUID().equals(playerUUID)) {
                            playerCaste = tempCaste;
                            playerData = member;
                            break;
                        }
                    }
                    if (playerCaste != null) {
                        break;
                    }
                }

                if (playerCaste == null) {
                    sender.addChatMessage(new ChatComponentText("You are not in a caste."));
                    return;
                }

                // Check if the player is the leader of the caste
                if (playerData.getRole() != Role.LEADER) {
                    sender.addChatMessage(new ChatComponentText("Only the leader can delete the caste."));
                    return;
                }

                // Delete the caste
                casteManager.getCastes().remove(playerCaste);

                sender.addChatMessage(new ChatComponentText("You have deleted the caste '" + playerCaste.getName() + "'."));
                break;

            case "invite":
                if (args.length < 2) {
                    sender.addChatMessage(new ChatComponentText("Invalid usage. Use /ntmcaste invite <player name>"));
                    return;
                }

                String invitedPlayerName = args[1];
                // Find the player's caste and player data
                for (Caste tempCaste : casteManager.getCastes()) {
                    for (PlayerData member : tempCaste.getMembers()) {
                        if (member.getPlayerUUID().equals(playerUUID)) {
                            playerCaste = tempCaste;
                            playerData = member;
                            break;
                        }
                    }
                    if (playerCaste != null) {
                        break;
                    }
                }

                if (playerCaste == null) {
                    sender.addChatMessage(new ChatComponentText("You are not in a caste."));
                    return;
                }

                // Check if the invited player is already in a caste
                for (Caste tempCaste : casteManager.getCastes()) {
                    for (PlayerData member : tempCaste.getMembers()) {
                        if (member.getPlayerUUID().toString().equals(invitedPlayerName)) {
                            sender.addChatMessage(new ChatComponentText("The player is already in a caste."));
                            return;
                        }
                    }
                }

                // Invite the player to the caste
                playerCaste.getInvitedPlayers().add(invitedPlayerName);

                sender.addChatMessage(new ChatComponentText("You have invited '" + invitedPlayerName + "' to your caste."));
                break;

            case "accept":
                if (args.length < 2) {
                    sender.addChatMessage(new ChatComponentText("Invalid usage. Use /ntmcaste accept <caste name>"));
                    return;
                }

                casteName = args[1];
                playerCaste = null;

                // Find the caste with the given name
                for (Caste tempCaste : casteManager.getCastes()) {
                    if (tempCaste.getName().equals(casteName)) {
                        playerCaste = tempCaste;
                        break;
                    }
                }

                if (playerCaste == null) {
                    sender.addChatMessage(new ChatComponentText("The caste does not exist."));
                    return;
                }

                // Check if the player is invited to the caste
                if (!playerCaste.getInvitedPlayers().contains(playerUUID.toString())) {
                    sender.addChatMessage(new ChatComponentText("You are not invited to this caste."));
                    return;
                }

                // Add the player to the caste
                playerCaste.getMembers().add(new PlayerData(playerUUID, Role.MEMBER, playerCaste.getId()));
                playerCaste.getInvitedPlayers().remove(playerUUID.toString());

                sender.addChatMessage(new ChatComponentText("You have joined the caste '" + playerCaste.getName() + "'."));
                break;

            case "promote":
                if (args.length < 2) {
                    sender.addChatMessage(new ChatComponentText("Invalid usage. Use /ntmcaste promote <player name>"));
                    return;
                }

                String promotedPlayerName = args[1];

                // Find the player's caste and player data
                for (Caste tempCaste : casteManager.getCastes()) {
                    for (PlayerData member : tempCaste.getMembers()) {
                        if (member.getPlayerUUID().equals(playerUUID)) {
                            playerCaste = tempCaste;
                            playerData = member;
                            break;
                        }
                    }
                    if (playerCaste != null) {
                        break;
                    }
                }

                if (playerCaste == null) {
                    sender.addChatMessage(new ChatComponentText("You are not in a caste."));
                    return;
                }

                // Check if the player is the leader of the caste
                if (playerData.getRole() != Role.LEADER) {
                    sender.addChatMessage(new ChatComponentText("Only the leader can promote members."));
                    return;
                }

                // Find the member to be promoted
                PlayerData promotedPlayerData = null;
                for (PlayerData member : playerCaste.getMembers()) {
                    if (member.getPlayerUUID().toString().equals(promotedPlayerName)) {
                        promotedPlayerData = member;
                        break;
                    }
                }

                if (promotedPlayerData == null) {
                    sender.addChatMessage(new ChatComponentText("The player is not a member of your caste."));
                    return;
                }

                // Promote the member
                if (promotedPlayerData.getRole() == Role.MEMBER) {
                    promotedPlayerData.setRole(Role.MANAGER);
                    sender.addChatMessage(new ChatComponentText("You have promoted '" + promotedPlayerName + "' to Manager."));
                } else if (promotedPlayerData.getRole() == Role.MANAGER) {
                    sender.addChatMessage(new ChatComponentText("The player is already a Manager."));
                }
                break;

            case "demote":
                if (args.length < 2) {
                    sender.addChatMessage(new ChatComponentText("Invalid usage. Use /ntmcaste demote <player name>"));
                    return;
                }

                String demotedPlayerName = args[1];

                // Find the player's caste and player data
                for (Caste tempCaste : casteManager.getCastes()) {
                    for (PlayerData member : tempCaste.getMembers()) {
                        if (member.getPlayerUUID().equals(playerUUID)) {
                            playerCaste = tempCaste;
                            playerData = member;
                            break;
                        }
                    }
                    if (playerCaste != null) {
                        break;
                    }
                }

                if (playerCaste == null) {
                    sender.addChatMessage(new ChatComponentText("You are not in a caste."));
                    return;
                }

                // Check if the player is the leader of the caste
                if (playerData.getRole() != Role.LEADER) {
                    sender.addChatMessage(new ChatComponentText("Only the leader can demote managers."));
                    return;
                }

                // Find the manager  to be demoted
                PlayerData demotedPlayerData = null;
                for (PlayerData member : playerCaste.getMembers()) {
                    if (member.getPlayerUUID().toString().equals(demotedPlayerName)) {
                        demotedPlayerData = member;
                        break;
                    }
                }

                if (demotedPlayerData == null) {
                    sender.addChatMessage(new ChatComponentText("The player is not a member of your caste."));
                    return;
                }

                // Demote the manager
                if (demotedPlayerData.getRole() == Role.MANAGER) {
                    demotedPlayerData.setRole(Role.MEMBER);
                    sender.addChatMessage(new ChatComponentText("You have demoted '" + demotedPlayerName + "' to Member."));
                } else if (demotedPlayerData.getRole() == Role.MEMBER) {
                    sender.addChatMessage(new ChatComponentText("The player is already a Member."));
                }
                break;
            case "kick":
                if (args.length < 2) {
                    sender.addChatMessage(new ChatComponentText("Invalid usage. Use /ntmcaste kick <player name>"));
                    return;
                }

                String kickedPlayerName = args[1];

                // Find the player's caste and player data
                for (Caste tempCaste : casteManager.getCastes()) {
                    for (PlayerData member : tempCaste.getMembers()) {
                        if (member.getPlayerUUID().equals(playerUUID)) {
                            playerCaste = tempCaste;
                            playerData = member;
                            break;
                        }
                    }
                    if (playerCaste != null) {
                        break;
                    }
                }

                if (playerCaste == null) {
                    sender.addChatMessage(new ChatComponentText("You are not in a caste."));
                    return;
                }

                // Check if the player is the leader of the caste
                if (playerData.getRole() != Role.LEADER) {
                    sender.addChatMessage(new ChatComponentText("Only the leader can kick members."));
                    return;
                }

                // Find the member to be kicked
                PlayerData kickedPlayerData = null;
                for (PlayerData member : playerCaste.getMembers()) {
                    if (member.getPlayerUUID().toString().equals(kickedPlayerName)) {
                        kickedPlayerData = member;
                        break;
                    }
                }

                if (kickedPlayerData == null) {
                    sender.addChatMessage(new ChatComponentText("The player is not a member of your caste."));
                    return;
                }

                // Kick the member
                playerCaste.getMembers().remove(kickedPlayerData);

                sender.addChatMessage(new ChatComponentText("You have kicked '" + kickedPlayerName + "' from your caste."));
                break;

            case "list":
                if (args.length < 2) {
                    sender.addChatMessage(new ChatComponentText("Invalid usage. Use /ntmcaste list <caste name>"));
                    return;
                }

                casteName = args[1];
                Caste listedCaste = null;

                // Find the caste with the given name
                for (Caste tempCaste : casteManager.getCastes()) {
                    if (tempCaste.getName().equals(casteName)) {
                        listedCaste = tempCaste;
                        break;
                    }
                }

                if (listedCaste == null) {
                    sender.addChatMessage(new ChatComponentText("The caste does not exist."));
                    return;
                }

                // List the members of the caste in hierarchical order
                StringBuilder casteMembers = new StringBuilder("Members of caste '" + casteName + "':\n");
                for (Role role : Role.values()) {
                    for (PlayerData member : listedCaste.getMembers()) {
                        if (member.getRole() == role) {
                            casteMembers.append("- ").append(member.getPlayerUUID().toString()).append(" (").append(role.name()).append(")\n");
                        }
                    }
                }

                sender.addChatMessage(new ChatComponentText(casteMembers.toString()));
                break;

            default:
                sender.addChatMessage(new ChatComponentText("Invalid subcommand. Use /ntmcaste <subcommand> [arguments]"));
                break;
        }
    }
}