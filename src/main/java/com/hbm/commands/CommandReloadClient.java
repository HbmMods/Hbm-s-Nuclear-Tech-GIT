package com.hbm.commands;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.hbm.config.ClientConfig;
import com.hbm.config.ClientConfig.ConfigWrapper;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;

public class CommandReloadClient extends CommandBase {

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

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return sender instanceof EntityPlayer;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		
		if(args.length < 1) throw new CommandException(getCommandUsage(sender));
		
		String operator = args[0];
		
		if("help".equals(operator)) {
			
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
			return;
		}
		
		if("list".equals(operator)) {
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "CLIENT VARIABLES:"));
			for(Entry<String, ConfigWrapper> line : ClientConfig.configMap.entrySet()) {
				sender.addChatMessage(new ChatComponentText("  " + EnumChatFormatting.GOLD + line.getKey() + ": " + EnumChatFormatting.YELLOW + line.getValue().value));
			}
			return;
		}
		
		if("reload".equals(operator)) {
			ClientConfig.reload();
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Variables loaded from config file."));
			return;
		}

		if(args.length < 2) throw new CommandException(getCommandUsage(sender));
		
		String key = args[1];
		
		if("get".equals(operator)) {
			ConfigWrapper wrapper = ClientConfig.configMap.get(key);
			if(wrapper == null) throw new CommandException("Key does not exist.");
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + key + ": " + EnumChatFormatting.YELLOW + wrapper.value));
			return;
		}

		if(args.length < 3) throw new CommandException(getCommandUsage(sender));
		
		String value = args[2];
		
		if("set".equals(operator)) {
			ConfigWrapper wrapper = ClientConfig.configMap.get(key);
			if(wrapper == null) throw new CommandException("Key does not exist.");
			
			try {
				wrapper.update(value);
				ClientConfig.refresh();
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
			return getListOfStringsFromIterableMatchingLastWord(args, ClientConfig.configMap.keySet().stream().map(String::valueOf).collect(Collectors.toList()));
		}
		return Collections.emptyList();
	}
}
