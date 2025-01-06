package com.hbm.commands;

import com.hbm.items.ISatChip;
import com.hbm.items.ModItems;
import com.hbm.saveddata.SatelliteSavedData;
import com.hbm.saveddata.satellites.Satellite;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class CommandSatellites extends CommandBase {
	@Override
	public String getCommandName() {
		return "ntmsatellites";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return String.format(Locale.US, "%s/%s orbit %s- Launch the held satellite.\n" + "%s/%s descend <frequency> %s- Deletes satellite by frequency.\n" + "%s/%s list %s- Lists all active satellites.", EnumChatFormatting.GREEN, getCommandName(), EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.GREEN, getCommandName(), EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.GREEN, getCommandName(), EnumChatFormatting.LIGHT_PURPLE);
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(!(sender instanceof EntityPlayer)) {
			sender.addChatMessage(new ChatComponentTranslation("commands.satellite.should_be_run_as_player").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			return;
		}
		switch(args[0]) {
		case "orbit":
			EntityPlayer player = getCommandSenderAsPlayer(sender);
			if(player.getHeldItem().getItem() instanceof ISatChip && player.getHeldItem().getItem() != ModItems.sat_chip) {
				Satellite.orbit(player.worldObj, Satellite.getIDFromItem(player.getHeldItem().getItem()), ISatChip.getFreqS(player.getHeldItem()), player.posX, player.posY, player.posZ);
				player.getHeldItem().stackSize -= 1;
				sender.addChatMessage(new ChatComponentTranslation("commands.satellite.satellite_orbited").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));
			} else {
				sender.addChatMessage(new ChatComponentTranslation("commands.satellite.not_a_satellite").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			}
			break;
		case "descend":
			int freq = parseInt(sender, args[1]);
			SatelliteSavedData data = SatelliteSavedData.getData(sender.getEntityWorld());
			if(data.sats.containsKey(freq)) {
				data.sats.remove(freq);
				data.markDirty();
				sender.addChatMessage(new ChatComponentTranslation("commands.satellite.satellite_descended").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));
			} else {
				sender.addChatMessage(new ChatComponentTranslation("commands.satellite.no_satellite").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			}
			break;
		case "list":
			data = SatelliteSavedData.getData(sender.getEntityWorld());
			if(data.sats.isEmpty()) {
				ChatComponentTranslation message = new ChatComponentTranslation("commands.satellite.no_active_satellites");
				message.getChatStyle().setColor(EnumChatFormatting.RED);
				sender.addChatMessage(message);
			} else {
				data.sats.forEach((listFreq, sat) -> {
					String messageText = String.valueOf(listFreq) + " - " + sat.getClass().getSimpleName();
					ChatComponentText message = new ChatComponentText(messageText);
					message.getChatStyle().setColor(EnumChatFormatting.GREEN);
					sender.addChatMessage(message);
				});
			}
			break;

		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if(!(sender instanceof EntityPlayer)) {
			return Collections.emptyList();
		}
		if(args.length < 1) {
			return Collections.emptyList();
		}
		if(args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "orbit", "descend", "list");
		}
		if(args[0].equals("descend")) {
			return getListOfStringsFromIterableMatchingLastWord(args, SatelliteSavedData.getData(sender.getEntityWorld()).sats.keySet().stream().map(String::valueOf).collect(Collectors.toList()));
		}
		return Collections.emptyList();
	}
}
