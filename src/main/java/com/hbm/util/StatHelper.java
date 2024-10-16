package com.hbm.util;

import java.util.Iterator;
import java.util.Map;

import com.hbm.interfaces.NotableComments;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatCrafting;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentTranslation;

@NotableComments
public class StatHelper {
	
	/*
	 * God is dead and we are pissing on his grave
	 */
	public static Map publicReferenceToOneshotStatListPleaseAllPointAndLaugh;
	
	/**
	 * This is probably the worst fucking way of doing this, but it's the only one I could think of that works.
	 * In short: stats are hilariously broken.
	 * Minecraft registers them at the earliest point during startup right after blocks and items are registered.
	 * The problem in that? Item remapping. Modded items probably don't even exist at that point and even if they did,
	 * the system would break because modded items have dynamic IDs and the stats register fixed IDs instead of item instances.
	 * What did forge do to solve this issue? Well nothing, of course! The injected bits comment on that in vanilla's stat
	 * registering code, but instead of fixing anything it just slaps a fat "TODO" onto it. Wow! Really helpful!
	 * 
	 * So what do we do? Every time the world starts and we know the IDs are now correct, we smack that fucker up nice and good.
	 * All ID-bound stats get deep-cleaned out of this mess and registered 1:1 again. Is this terrible and prone to breaking with
	 * mods that do their own stat handling? Hard to say, but the possibility is there.
	 */
	public static void resetStatShitFuck() {
		
		publicReferenceToOneshotStatListPleaseAllPointAndLaugh = ReflectionHelper.getPrivateValue(StatList.class, null, "field_75942_a", "oneShotStats");
		
		for(int i = 0; i < StatList.objectCraftStats.length; i++) StatList.objectCraftStats[i] = null;
		for(int i = 0; i < StatList.mineBlockStatArray.length; i++) StatList.mineBlockStatArray[i] = null;
		for(int i = 0; i < StatList.objectUseStats.length; i++) StatList.objectUseStats[i] = null;
		for(int i = 0; i < StatList.objectBreakStats.length; i++) StatList.objectBreakStats[i] = null;
		StatList.objectMineStats.clear();
		StatList.itemStats.clear();
		
		try {
			initCraftItemStats();
			initBlockMineStats();
			initItemUseStats();
			initItemBreakStats();
		} catch(Throwable ex) { } // just to be sure
	}
	
	/**
	 * For reasons beyond human comprehension, this bit originally only registered items that are the result
	 * of an IRecipe instead of just all items outright like the item usage stats. The logical consequence of this is:
	 * 1) The code has to iterate over thousands of recipes to get recipe results which is no more performant than just going over
	 *    32k potential items, most of which are going to be null anyway
	 * 2) The system just will never work with items that don't have crafting table recipes
	 */
	private static void initCraftItemStats() {
		Iterator iterator = Item.itemRegistry.iterator();
		while(iterator.hasNext()) {
			Item item = (Item) iterator.next();

			if(item != null) {
				int i = Item.getIdFromItem(item);
				try {
					StatList.objectCraftStats[i] = registerStat(new StatCrafting("stat.craftItem." + i, new ChatComponentTranslation("stat.craftItem", new Object[] { (new ItemStack(item)).func_151000_E() }), item));
				} catch(Throwable ex) { }
			}
		}

		replaceAllSimilarBlocks(StatList.objectCraftStats);
	}

	private static void initBlockMineStats() {
		Iterator iterator = Block.blockRegistry.iterator();

		while(iterator.hasNext()) {
			Block block = (Block) iterator.next();

			if(Item.getItemFromBlock(block) != null) {
				int i = Block.getIdFromBlock(block);
				try {
					if(block.getEnableStats()) {
						StatList.mineBlockStatArray[i] = registerStat(new StatCrafting("stat.mineBlock." + i, new ChatComponentTranslation("stat.mineBlock", new Object[] { (new ItemStack(block)).func_151000_E() }), Item.getItemFromBlock(block)));
						StatList.objectMineStats.add((StatCrafting) StatList.mineBlockStatArray[i]);
					}
				} catch(Throwable ex) { }
			}
		}

		replaceAllSimilarBlocks(StatList.mineBlockStatArray);
	}

	private static void initItemUseStats() {
		Iterator iterator = Item.itemRegistry.iterator();

		while(iterator.hasNext()) {
			Item item = (Item) iterator.next();

			if(item != null) {
				int i = Item.getIdFromItem(item);
				try {
					StatList.objectUseStats[i] = registerStat(new StatCrafting("stat.useItem." + i, new ChatComponentTranslation("stat.useItem", new Object[] { (new ItemStack(item)).func_151000_E() }), item));
					if(!(item instanceof ItemBlock)) {
						StatList.itemStats.add((StatCrafting) StatList.objectUseStats[i]);
					}
				} catch(Throwable ex) { }
			}
		}

		replaceAllSimilarBlocks(StatList.objectUseStats);
	}

	private static void initItemBreakStats() {
		Iterator iterator = Item.itemRegistry.iterator();

		while(iterator.hasNext()) {
			Item item = (Item) iterator.next();

			if(item != null) {
				int i = Item.getIdFromItem(item);
				try {
					if(item.isDamageable()) {
						StatList.objectBreakStats[i] = registerStat(new StatCrafting("stat.breakItem." + i, new ChatComponentTranslation("stat.breakItem", new Object[] { (new ItemStack(item)).func_151000_E() }), item));
					}
				} catch(Throwable ex) { }
			}
		}

		replaceAllSimilarBlocks(StatList.objectBreakStats);
	}

	private static void replaceAllSimilarBlocks(StatBase[] stats) {
		func_151180_a(stats, Blocks.water, Blocks.flowing_water);
		func_151180_a(stats, Blocks.lava, Blocks.flowing_lava);
		func_151180_a(stats, Blocks.lit_pumpkin, Blocks.pumpkin);
		func_151180_a(stats, Blocks.lit_furnace, Blocks.furnace);
		func_151180_a(stats, Blocks.lit_redstone_ore, Blocks.redstone_ore);
		func_151180_a(stats, Blocks.powered_repeater, Blocks.unpowered_repeater);
		func_151180_a(stats, Blocks.powered_comparator, Blocks.unpowered_comparator);
		func_151180_a(stats, Blocks.redstone_torch, Blocks.unlit_redstone_torch);
		func_151180_a(stats, Blocks.lit_redstone_lamp, Blocks.redstone_lamp);
		func_151180_a(stats, Blocks.red_mushroom, Blocks.brown_mushroom);
		func_151180_a(stats, Blocks.double_stone_slab, Blocks.stone_slab);
		func_151180_a(stats, Blocks.double_wooden_slab, Blocks.wooden_slab);
		func_151180_a(stats, Blocks.grass, Blocks.dirt);
		func_151180_a(stats, Blocks.farmland, Blocks.dirt);
	}

	private static void func_151180_a(StatBase[] stats, Block block, Block similarBlock) {
		int i = Block.getIdFromBlock(block);
		int j = Block.getIdFromBlock(similarBlock);

		if(stats[i] != null && stats[j] == null) {
			stats[j] = stats[i];
		} else {
			StatList.allStats.remove(stats[i]);
			StatList.objectMineStats.remove(stats[i]);
			StatList.generalStats.remove(stats[i]);
			stats[i] = stats[j];
		}
	}
	
	private static StatBase registerStat(StatBase stat) {
		if(publicReferenceToOneshotStatListPleaseAllPointAndLaugh.containsKey(stat.statId)) {
			publicReferenceToOneshotStatListPleaseAllPointAndLaugh.remove(stat.statId);
		}
		
		StatList.allStats.add(stat);
		publicReferenceToOneshotStatListPleaseAllPointAndLaugh.put(stat.statId, stat);
		return stat;
	}
}
