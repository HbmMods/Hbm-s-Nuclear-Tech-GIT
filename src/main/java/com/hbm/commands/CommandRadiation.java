package com.hbm.commands;

import com.hbm.handler.radiation.ChunkRadiationManager;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandRadiation extends CommandBase {

	@Override
	public String getCommandName() {
		return "ntmrad";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/ntmrad <set/clear>";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		
		if(args.length == 1 && "clear".equals(args[0])) {
			ChunkRadiationManager.proxy.clearSystem(sender.getEntityWorld());
			sender.addChatMessage(new ChatComponentText("Cleared radiation data!"));
			return;
		}
		
		if(args.length == 2 && "set".equals(args[0])) {
			float amount = (float) this.parseDoubleBounded(sender, args[1], 0D, 100_000D);
			ChunkRadiationManager.proxy.setRadiation(sender.getEntityWorld(), sender.getPlayerCoordinates().posX, sender.getPlayerCoordinates().posY, sender.getPlayerCoordinates().posZ, amount);
			sender.addChatMessage(new ChatComponentText("Radiation set."));
			return;
		}

		sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + getCommandUsage(sender)));
	}

}
