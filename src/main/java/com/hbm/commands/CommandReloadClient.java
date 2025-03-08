package com.hbm.commands;

import java.util.HashMap;

import com.hbm.config.ClientConfig;
import com.hbm.config.RunningConfig.ConfigWrapper;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;

public class CommandReloadClient extends CommandReloadConfig {

	public static void register() {
		if(FMLLaunchHandler.side() != Side.CLIENT) return;
		ClientCommandHandler.instance.registerCommand(new CommandReloadClient());
	}

	@Override
	public String getCommandName() {
		return "ntmclient";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/ntmclient help";
	}
	
	@Override public void help(ICommandSender sender, String[] args) {
		if(args.length >= 2) {
			String command = args[1];
			if("help".equals(command)) sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Shows usage for /ntmclient subcommands."));
			if("list".equals(command)) sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Shows all client variable names and values."));
			if("reload".equals(command)) sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Reads client variables from the config file."));
			if("get".equals(command)) sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Shows value for the specified variable name."));
			if("set".equals(command)) sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Sets a variable's value and saves it to the config file."));
		} else {
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "/ntmclient " + EnumChatFormatting.GOLD + "help " + EnumChatFormatting.RED + "<command>"));
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "/ntmclient " + EnumChatFormatting.GOLD + "list"));
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "/ntmclient " + EnumChatFormatting.GOLD + "reload"));
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "/ntmclient " + EnumChatFormatting.GOLD + "get " + EnumChatFormatting.RED + "<name>"));
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "/ntmclient " + EnumChatFormatting.GOLD + "set " + EnumChatFormatting.RED + "<name> <value>"));
		}
	}
	
	@Override public HashMap<String, ConfigWrapper> getConfigMap() { return ClientConfig.configMap; }
	@Override public void refresh() { ClientConfig.refresh(); }
	@Override public void reload() { ClientConfig.reload(); }
	@Override public String getTitle() { return "CLIENT VARIABLES:"; }
}
