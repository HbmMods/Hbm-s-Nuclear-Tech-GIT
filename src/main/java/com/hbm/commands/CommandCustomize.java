package com.hbm.commands;

import com.hbm.items.ICustomizable;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class CommandCustomize extends CommandBase {

	@Override
	public String getCommandName() {
		return "ntmcustomize";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/ntmcustomize";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(!(sender instanceof EntityPlayer)) {
			sender.addChatMessage(new ChatComponentText("Customization is only available to players!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			return;
		}
		
		EntityPlayer player = (EntityPlayer) sender;
		
		if(player.getHeldItem() == null || !(player.getHeldItem().getItem() instanceof ICustomizable)) {
			sender.addChatMessage(new ChatComponentText("You have to hold a customizable item to use this command!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			return;
		}
		
		ICustomizable item = (ICustomizable) player.getHeldItem().getItem();
		item.customize(player, player.getHeldItem(), args);
	}
}
