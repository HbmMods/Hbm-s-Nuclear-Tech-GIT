package com.hbm.commands;

import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.util.ChatBuilder;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandReloadRecipes extends CommandBase {

	@Override
	public String getCommandName() {
		return "ntmreload";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/ntmreload";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		try {
			SerializableRecipe.initialize();
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Reload complete :)"));
		} catch(Exception ex) {
			sender.addChatMessage(ChatBuilder.start("----------------------------------").color(EnumChatFormatting.GRAY).flush());
			sender.addChatMessage(ChatBuilder.start("An error has occoured during loading, consult the log for details.").color(EnumChatFormatting.RED).flush());
			sender.addChatMessage(ChatBuilder.start(ex.getLocalizedMessage()).color(EnumChatFormatting.RED).flush());
			sender.addChatMessage(ChatBuilder.start(ex.getStackTrace()[0].toString()).color(EnumChatFormatting.RED).flush());
			sender.addChatMessage(ChatBuilder.start("----------------------------------").color(EnumChatFormatting.GRAY).flush());
			throw ex;
		}
	}
}
