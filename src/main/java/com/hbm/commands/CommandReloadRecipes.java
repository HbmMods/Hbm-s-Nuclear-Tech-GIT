package com.hbm.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.hbm.config.ItemPoolConfigJSON;
import com.hbm.inventory.FluidContainerRegistry;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.util.ChatBuilder;
import com.hbm.util.DamageResistanceHandler;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandReloadRecipes extends CommandBase {

	@Override
	public String getCommandName() {
		return "ntmreload";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/ntmreload [animations [<name>|list]]";
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return sender instanceof EntityPlayer;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(args.length == 0) {
			reloadRecipes(sender);
			return;
		}

		if(isAnimSubcommand(args[0])) {
			reloadAnimations(sender, args);
			return;
		}

		sender.addChatMessage(ChatBuilder.start("Unknown subcommand. Usage: " + getCommandUsage(sender)).color(EnumChatFormatting.RED).flush());
	}

	private static boolean isAnimSubcommand(String s) {
		return "animations".equals(s) || "anims".equals(s) || "anim".equals(s);
	}

	private void reloadRecipes(ICommandSender sender) {
		try {
			FluidContainerRegistry.clearRegistry(); // we do this first so IFluidRegisterListener can go wild with the registry
			Fluids.reloadFluids();
			FluidContainerRegistry.register();
			SerializableRecipe.initialize();
			ItemPoolConfigJSON.initialize();
			DamageResistanceHandler.init();

			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Reload complete :)"));
		} catch(Exception ex) {
			sender.addChatMessage(ChatBuilder.start("----------------------------------").color(EnumChatFormatting.GRAY).flush());
			sender.addChatMessage(ChatBuilder.start("An error has occurred during loading, consult the log for details.").color(EnumChatFormatting.RED).flush());
			sender.addChatMessage(ChatBuilder.start(ex.getLocalizedMessage()).color(EnumChatFormatting.RED).flush());
			sender.addChatMessage(ChatBuilder.start(ex.getStackTrace()[0].toString()).color(EnumChatFormatting.RED).flush());
			sender.addChatMessage(ChatBuilder.start("----------------------------------").color(EnumChatFormatting.GRAY).flush());
			MainRegistry.logger.error("Recipe reload failed", ex);
		}
	}

	private static final String NOTE_ANIM_STALE =
		EnumChatFormatting.GRAY + "Note: animations currently playing may still use old data until the next animation starts.";

	private void reloadAnimations(ICommandSender sender, String[] args) {
		if(args.length < 2) {
			reloadAllAnims(sender);
			return;
		}

		String name = args[1];
		if("list".equals(name)) {
			listAnims(sender);
			return;
		}

		reloadOneAnim(sender, name);
	}

	private void reloadAllAnims(ICommandSender sender) {
		try {
			int count = ResourceManager.reloadAllAnimations();
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Reloaded " + count + " animation file(s)."));
			sender.addChatMessage(new ChatComponentText(NOTE_ANIM_STALE));
		} catch(Exception ex) {
			sendAnimError(sender, "all animations", ex);
		}
	}

	private void reloadOneAnim(ICommandSender sender, String name) {
		try {
			if(ResourceManager.reloadAnimation(name)) {
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Animation '" + name + "' reloaded successfully."));
				sender.addChatMessage(new ChatComponentText(NOTE_ANIM_STALE));
			} else {
				sender.addChatMessage(ChatBuilder.start(
					"Unknown animation name: '" + name + "'. Use '/ntmreload animations list' to see available names.")
					.color(EnumChatFormatting.RED).flush());
			}
		} catch(Exception ex) {
			sendAnimError(sender, name, ex);
		}
	}

	private void listAnims(ICommandSender sender) {
		sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Available animation names:"));
		for(String n : ResourceManager.getAnimationReloadNames()) {
			sender.addChatMessage(new ChatComponentText("  " + EnumChatFormatting.GOLD + n));
		}
	}

	private static void sendAnimError(ICommandSender sender, String target, Exception ex) {
		sender.addChatMessage(ChatBuilder.start(
			"Failed to reload " + target + ": " + ex.getLocalizedMessage())
			.color(EnumChatFormatting.RED).flush());
		MainRegistry.logger.error("Animation reload failed for {}", target, ex);
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if(!(sender instanceof EntityPlayer)) return Collections.emptyList();

		if(args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "animations");
		}

		if(args.length == 2 && isAnimSubcommand(args[0])) {
			Set<String> names = ResourceManager.getAnimationReloadNames();
			List<String> options = new ArrayList<>(names);
			options.add("list");
			return getListOfStringsFromIterableMatchingLastWord(args, options);
		}

		return Collections.emptyList();
	}
}
