package com.hbm.commands;

import com.hbm.saveddata.CasteSavedData;
import com.hbm.saveddata.caste.Caste;
import com.hbm.saveddata.caste.PlayerData;
import com.hbm.saveddata.caste.Role;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraft.server.MinecraftServer;

import java.util.UUID;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ArrayList;

public class CommandCaste extends CommandBase {

	private CasteSavedData casteSavedData;

	public CommandCaste() {
		// We will initialize casteSavedData in the processCommand method
	}

	@Override
	public String getCommandName() {
		return "ntmcaste";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/ntmcaste help";
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
		String playerName = player.getDisplayName();

		// Initialize casteSavedData
		World world = player.getEntityWorld();
		this.casteSavedData = CasteSavedData.getData(world);

		if (args.length < 1) {
			sender.addChatMessage(new ChatComponentText("Invalid usage. Use /ntmcaste <subcommand> [arguments]"));
			return;
		}

		String subcommand = args[0];

        switch (subcommand) {

			case "help":
				String[] helpMessages = new String[]{
					"§aNTM Castes are like guilds, they offer basic association and management features.",
					"§aAvailable subcommands for /ntmcaste:",
					"§a- create <caste name>: Creates a new caste with the given name.",
					"§a- leave: Leaves your current caste.",
					"§a- delete: Deletes your current caste. Only the leader can do this.",
					"§a- invite <player name>: Invites a player to your caste. Only the leader can do this.",
					"§a- accept <caste name>: Accepts an invitation to join a caste.",
					"§a- promote <player name>: Promotes a member to manager. Only the leader can do this.",
					"§a- demote <player name>: Demotes a manager to member. Only the leader can do this.",
					"§a- kick <player name>: Kicks a member from the caste. Only the leader can do this.",
					"§a- list <caste name>: Lists all members of a caste."
				};
				for (String helpMessage : helpMessages) {
					sender.addChatMessage(new ChatComponentText(helpMessage));
				}
				break;

            case "create":
                if (args.length < 2) {
                    sender.addChatMessage(new ChatComponentText("Invalid usage. Use /ntmcaste create <caste name>"));
                    return;
                }

                String casteName = args[1];

                // Check if the player is already in a caste
                for (Caste tempCaste : casteSavedData.getCastes()) {
                    for (PlayerData playerDataTemp : tempCaste.getMembers()) {
                        if (playerDataTemp.getPlayerUUID().equals(playerUUID)) {
                            sender.addChatMessage(new ChatComponentText("You are already in a caste."));
                            return;
                        }
                    }
                }

                // Check if a caste with the given name already exists
                for (Caste tempCaste : casteSavedData.getCastes()) {
                    if (tempCaste.getName().equals(casteName)) {
                        sender.addChatMessage(new ChatComponentText("A caste with this name already exists."));
                        return;
                    }
                }

                // Create the new caste and add the player as the leader
                Caste newCaste = new Caste(casteName);
                newCaste.addMember(new PlayerData(playerUUID, Role.LEADER, newCaste.getId(),playerName));
                casteSavedData.getCastes().add(newCaste);

                sender.addChatMessage(new ChatComponentText("Caste '" + casteName + "' created successfully."));
				this.casteSavedData.markDirty();
                break;

            case "leave":
                // Find the player's caste and player data
                for (Caste tempCaste : casteSavedData.getCastes()) {
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
                    casteSavedData.getCastes().remove(playerCaste);
                }

                sender.addChatMessage(new ChatComponentText("You have left the caste '" + playerCaste.getName() + "'."));
				this.casteSavedData.markDirty();
                break;

            case "delete":
                // Find the player's caste and player data
                for (Caste tempCaste : casteSavedData.getCastes()) {
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
                casteSavedData.getCastes().remove(playerCaste);

                sender.addChatMessage(new ChatComponentText("You have deleted the caste '" + playerCaste.getName() + "'."));
				this.casteSavedData.markDirty();
                break;

			case "invite":
				if (args.length < 2) {
					sender.addChatMessage(new ChatComponentText("Invalid usage. Use /ntmcaste invite <player name>"));
					return;
				}

				String invitedPlayerName = args[1];

				// Find the player's caste and player data
				for (Caste tempCaste : casteSavedData.getCastes()) {
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
				for (Caste tempCaste : casteSavedData.getCastes()) {
					for (PlayerData member : tempCaste.getMembers()) {
						if (member.getPlayerName().equals(invitedPlayerName)) {
							sender.addChatMessage(new ChatComponentText("The player is already in a caste."));
							return;
						}
					}
				}

				// Check if the player is already invited
				if (playerCaste.getInvitedPlayers().contains(invitedPlayerName)) {
					sender.addChatMessage(new ChatComponentText("The player is already invited to your caste."));
					return;
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
                for (Caste tempCaste : casteSavedData.getCastes()) {
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
                if (!playerCaste.getInvitedPlayers().contains(playerName)) {
                    sender.addChatMessage(new ChatComponentText("You are not invited to this caste."));
                    return;
                }

                // Add the player to the caste
                playerCaste.getMembers().add(new PlayerData(playerUUID, Role.MEMBER, playerCaste.getId(),playerName));
                playerCaste.getInvitedPlayers().remove(playerName);

                sender.addChatMessage(new ChatComponentText("You have joined the caste '" + playerCaste.getName() + "'."));
				this.casteSavedData.markDirty();
                break;

            case "promote":
                if (args.length < 2) {
                    sender.addChatMessage(new ChatComponentText("Invalid usage. Use /ntmcaste promote <player name>"));
                    return;
                }

                String promotedPlayerName = args[1];

                // Find the player's caste and player data
                for (Caste tempCaste : casteSavedData.getCastes()) {
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
                    if (member.getPlayerName().equals(promotedPlayerName)) {
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
				this.casteSavedData.markDirty();
                break;

            case "demote":
                if (args.length < 2) {
                    sender.addChatMessage(new ChatComponentText("Invalid usage. Use /ntmcaste demote <player name>"));
                    return;
                }

                String demotedPlayerName = args[1];

                // Find the player's caste and player data
                for (Caste tempCaste : casteSavedData.getCastes()) {
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
                    if (member.getPlayerName().equals(demotedPlayerName)) {
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
				this.casteSavedData.markDirty();
                break;
            case "kick":
                if (args.length < 2) {
                    sender.addChatMessage(new ChatComponentText("Invalid usage. Use /ntmcaste kick <player name>"));
                    return;
                }

                String kickedPlayerName = args[1];

                // Find the player's caste and player data
                for (Caste tempCaste : casteSavedData.getCastes()) {
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
                    if (member.getPlayerName().toString().equals(kickedPlayerName)) {
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
				this.casteSavedData.markDirty();
                break;

			case "list":
				if (args.length < 2) {
					sender.addChatMessage(new ChatComponentText("Invalid usage. Use /ntmcaste list <caste name>"));
					return;
				}

				casteName = args[1];
				Caste listedCaste = null;

				// Find the caste with the given name
				for (Caste tempCaste : casteSavedData.getCastes()) {
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
				List<String> casteMembers = new ArrayList<>();
				casteMembers.add("Members of Caste '" + casteName + "':");
				for (Role role : Role.values()) {
					for (PlayerData member : listedCaste.getMembers()) {
						if (member.getRole() == role) {
							String tempPlayerName = member.getPlayerName();
							casteMembers.add(String.format(Locale.US, "- §a%s (§b%s§a)", tempPlayerName, role.name()));
						}
					}
				}

				for (String member : casteMembers) {
					sender.addChatMessage(new ChatComponentText(member));
				}
				break;
        }
    }

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (!(sender instanceof EntityPlayer)) {
			return Collections.emptyList();
		}

		if (args.length < 1) {
			return getListOfStringsMatchingLastWord(args, "help","create", "leave", "delete", "invite", "accept", "promote", "demote", "kick", "list");
		}

		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "help","create", "leave", "delete", "invite", "accept", "promote", "demote", "kick", "list");
		}

		if (args.length == 2) {
			switch (args[0]) {
				case "invite":
				case "promote":
				case "demote":
				case "kick":
					return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
			}
		}

		return Collections.emptyList();
	}
}
