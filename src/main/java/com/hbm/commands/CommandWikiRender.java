package com.hbm.commands;

import java.util.ArrayList;
import java.util.Locale;
import java.util.List;
import java.util.Arrays;

import com.hbm.inventory.gui.GUIScreenWikiRender;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemDepletedFuel;
import com.hbm.items.machine.ItemFluidDuct;
import com.hbm.items.machine.ItemRBMKPellet;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.command.WrongUsageException;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.init.Blocks;

// horribly written command so you can render more than fucking guns without having to decompile the JAR
public class CommandWikiRender extends CommandBase {

	public static void register() {
		if(FMLLaunchHandler.side() != Side.CLIENT) return;
		ClientCommandHandler.instance.registerCommand(new CommandWikiRender());
	}
	
	@Override
	public String getCommandName() {
		return "ntmwikirender";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return String.format(Locale.US, "%s/%s <type> %s- Render screenshots of a selected item type (e.g ItemGunBaseNT). Intended for developers and wiki editors only.", EnumChatFormatting.GREEN,
				getCommandName(), EnumChatFormatting.LIGHT_PURPLE);
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(!(sender instanceof EntityPlayer)) {
			throw new PlayerNotFoundException();
		}

		if(args.length == 0) {
			throw new WrongUsageException(getCommandUsage(sender), new Object[0]);
		}

		MainRegistry.logger.info("Taking a screenshot of " + args[0]);

		List<Item> ignoredItems = Arrays.asList(ModItems.achievement_icon, Items.spawn_egg, Item.getItemFromBlock(Blocks.mob_spawner));

		List<Class<? extends Item>> collapsedClasses = Arrays.asList(ItemRBMKPellet.class, ItemDepletedFuel.class, ItemFluidDuct.class);

		String prefix = args[0];
		int slotScale = 16;
		boolean ignoreNonNTM = true;

		List<ItemStack> stacks = new ArrayList<ItemStack>();
		for(Object reg : Item.itemRegistry) {
			Item item = (Item) reg;
			if(ignoreNonNTM && !Item.itemRegistry.getNameForObject(item).startsWith("hbm:"))
				continue;
			if(ignoredItems.contains(item))
				continue;
			if(!item.getClass().getSimpleName().equalsIgnoreCase(args[0])
					&& (net.minecraft.block.Block.getBlockFromItem(item) == null || !net.minecraft.block.Block.getBlockFromItem(item).getClass().getSimpleName().equalsIgnoreCase(args[0])))
				continue;
			if(collapsedClasses.contains(item.getClass())) {
				stacks.add(new ItemStack(item));
			} else {
				item.getSubItems(item, null, stacks);
			}
		}

		Minecraft.getMinecraft().thePlayer.closeScreen();
		FMLCommonHandler.instance().showGuiScreen(new GUIScreenWikiRender(stacks.toArray(new ItemStack[0]), prefix, "wiki-block-renders-256", slotScale));
	}
}