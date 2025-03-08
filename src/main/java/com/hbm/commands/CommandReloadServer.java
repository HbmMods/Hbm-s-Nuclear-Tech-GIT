package com.hbm.commands;

import java.util.HashMap;

import com.hbm.config.RunningConfig.ConfigWrapper;
import com.hbm.config.ServerConfig;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandReloadServer extends CommandReloadConfig {

	@Override
	public String getCommandName() {
		return "ntmserver";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/ntmserver help";
	}
	
	@Override public void help(ICommandSender sender, String[] args) {
		if(args.length >= 2) {
			String command = args[1];
			if("help".equals(command)) sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Shows usage for /ntmserver subcommands."));
			if("list".equals(command)) sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Shows all server variable names and values."));
			if("reload".equals(command)) sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Reads server variables from the config file."));
			if("get".equals(command)) sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Shows value for the specified variable name."));
			if("set".equals(command)) sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Sets a variable's value and saves it to the config file."));
		} else {
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "/ntmserver " + EnumChatFormatting.GOLD + "help " + EnumChatFormatting.RED + "<command>"));
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "/ntmserver " + EnumChatFormatting.GOLD + "list"));
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "/ntmserver " + EnumChatFormatting.GOLD + "reload"));
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "/ntmserver " + EnumChatFormatting.GOLD + "get " + EnumChatFormatting.RED + "<name>"));
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "/ntmserver " + EnumChatFormatting.GOLD + "set " + EnumChatFormatting.RED + "<name> <value>"));
		}
	}
	
	@Override public HashMap<String, ConfigWrapper> getConfigMap() { return ServerConfig.configMap; }
	@Override public void refresh() { ServerConfig.refresh(); }
	@Override public void reload() { ServerConfig.reload(); }
	@Override public String getTitle() { return "SERVER VARIABLES:"; }
}
