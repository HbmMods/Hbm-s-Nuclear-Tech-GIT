package com.hbm.commands;

import com.hbm.uninos.GenNode;
import com.hbm.uninos.UniNodespace;
import com.hbm.util.ChatBuilder;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandReapNetworks extends CommandBase {

	@Override
	public String getCommandName() {
		return "ntmreapnetworks";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/ntmreapnetworks";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {

		try {
			
			UniNodespace.activeNodeNets.forEach((net) -> {
				net.links.forEach((link) -> { ((GenNode)link).expired = true; });
				net.links.clear();
				net.providerEntries.clear();
				net.receiverEntries.clear();
			});
			UniNodespace.activeNodeNets.clear();
			UniNodespace.worlds.clear();

			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Nodespace cleared :)"));
			
		} catch(Exception ex) {
			sender.addChatMessage(ChatBuilder.start("----------------------------------").color(EnumChatFormatting.GRAY).flush());
			sender.addChatMessage(ChatBuilder.start("An error has occoured during network reap, consult the log for details.").color(EnumChatFormatting.RED).flush());
			sender.addChatMessage(ChatBuilder.start(ex.getLocalizedMessage()).color(EnumChatFormatting.RED).flush());
			sender.addChatMessage(ChatBuilder.start(ex.getStackTrace()[0].toString()).color(EnumChatFormatting.RED).flush());
			sender.addChatMessage(ChatBuilder.start("----------------------------------").color(EnumChatFormatting.GRAY).flush());
			throw ex;
		}
	}
}
