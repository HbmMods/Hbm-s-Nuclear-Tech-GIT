package com.hbm.commands;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.hbm.config.RunningConfig.ConfigWrapper;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public abstract class CommandReloadConfig extends CommandBase {

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return sender instanceof EntityPlayer;
	}
	
	public abstract void help(ICommandSender sender, String[] args);
	public abstract HashMap<String, ConfigWrapper> getConfigMap();
	public abstract void refresh();
	public abstract void reload();
	public abstract String getTitle();

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		
		if(args.length < 1) throw new CommandException(getCommandUsage(sender));
		
		String operator = args[0];
		
		if("help".equals(operator)) {
			help(sender, args);
			return;
		}
		
		if("list".equals(operator)) {
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + getTitle()));
			for(Entry<String, ConfigWrapper> line : getConfigMap().entrySet()) {
				sender.addChatMessage(new ChatComponentText("  " + EnumChatFormatting.GOLD + line.getKey() + ": " + EnumChatFormatting.YELLOW + line.getValue().value));
			}
			return;
		}
		
		if("reload".equals(operator)) {
			reload();
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Variables loaded from config file."));
			return;
		}

		if(args.length < 2) throw new CommandException(getCommandUsage(sender));
		
		String key = args[1];
		
		if("get".equals(operator)) {
			ConfigWrapper wrapper = getConfigMap().get(key);
			if(wrapper == null) throw new CommandException("Key does not exist.");
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + key + ": " + EnumChatFormatting.YELLOW + wrapper.value));
			return;
		}

		if(args.length < 3) throw new CommandException(getCommandUsage(sender));
		
		String value = args[2];
		
		if("set".equals(operator)) {
			ConfigWrapper wrapper = getConfigMap().get(key);
			if(wrapper == null) throw new CommandException("Key does not exist.");
			
			try {
				wrapper.update(value);
				refresh();
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Value updated."));
			} catch(Exception ex) {
				throw new CommandException("Error parsing type for " + wrapper.value.getClass().getSimpleName() + ": " + ex.getLocalizedMessage());
			}
			
			return;
		}
		
		throw new CommandException(getCommandUsage(sender));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if(!(sender instanceof EntityPlayer)) return Collections.emptyList();
		if(args.length < 1) return Collections.emptyList();
		if(args.length == 1) return getListOfStringsMatchingLastWord(args, "list", "reload", "get", "set");
		String operator = args[0];
		if(args.length == 2 && ("get".equals(operator) || "set".equals(operator))) {
			return getListOfStringsFromIterableMatchingLastWord(args, getConfigMap().keySet().stream().map(String::valueOf).collect(Collectors.toList()));
		}
		return Collections.emptyList();
	}
}
