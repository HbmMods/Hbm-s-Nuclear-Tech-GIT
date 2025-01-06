package com.hbm.util;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.mob.EntityCreeperNuclear;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import java.util.ArrayList;
import java.util.List;

public class CompatNER {

	/*
	 * INIT
	 */

	public static void init() {
		sendRegisterOre(new ItemStack(ModBlocks.ore_alexandrite), false, 0xff00ff, new ItemStack(ModItems.gem_alexandrite));
		sendRegisterMob(EntityCreeperNuclear.class, "-1", encodeDrops(
				new DropItem(new ItemStack(Blocks.tnt), 0, 2),
				new DropItem(new ItemStack(ModItems.coin_creeper), 1, 1, 0.33F)));
	}

	/*
	 * REGISTERS
	 */

	public static void sendRegisterOre(ItemStack ore, boolean silk, int color, ItemStack... drops) {
		NBTTagCompound data = new NBTTagCompound();
		data.setTag(stack, ore.writeToNBT(new NBTTagCompound()));
		data.setBoolean(silkTouch, silk);
		data.setInteger(colour, color);
		data.setTag(addDrops, encodeStacks(drops));
		int[] distribution = new int[256];
		for(int i = 0; i < 256; i++) distribution[i] = 100;
		data.setIntArray("distribution", distribution);

		NBTTagCompound res = new NBTTagCompound();
		NBTTagCompound block = new NBTTagCompound();
		block.setTag("stack", new ItemStack(Blocks.stone).writeToNBT(new NBTTagCompound()));
		res.setTag("block", block);
		data.setTag(restriction, res);

		FMLInterModComms.sendMessage(notEnoughResources, registerOre, data);
	}

	public static void sendRegisterMob(Class clazz, String light, NBTTagList drops) {
		NBTTagCompound data = new NBTTagCompound();
		data.setString(name, clazz.getName());
		data.setString(lightLevel, light);
		data.setTag(addDrops, drops);
		MainRegistry.logger.info("Sending " + registerMob + " to " + notEnoughResources);
		FMLInterModComms.sendMessage(notEnoughResources, registerMob, data);
	}

	/*
	 * ENCODERS
	 */

	public static String encodeLightLevel(int level, boolean below) {
		return level + ":" + (below ? "b" : "a");
	}

	public static NBTTagList encodeDrops(DropItem... stacks) {
		NBTTagList list = new NBTTagList();
		for(DropItem stack : stacks) list.appendTag(stack.writeToNBT());
		return list;
	}

	public static NBTTagList encodeStacks(ItemStack... stacks) {
		NBTTagList list = new NBTTagList();
		for(ItemStack stack : stacks) list.appendTag(stack.writeToNBT(new NBTTagCompound()));
		return list;
	}

	/*
	 * DROP SYSTEM
	 */

	public static class DropItem {
		public ItemStack drop;
		public int min = 1;
		public int max = 1;
		public float chance = 1F;
		List<String> conditionals = new ArrayList();

		public DropItem(ItemStack stack) { this(stack, 1, 1, 1F); }
		public DropItem(ItemStack stack, int min, int max) { this(stack, min, max, 1F); }
		public DropItem(ItemStack stack, int min, int max, float chance) {
			this.drop = stack;
			this.min = min;
			this.max = max;
			this.chance = chance;
		}

		public NBTTagCompound writeToNBT() {
			NBTTagCompound compound = new NBTTagCompound();
			compound.setTag("stack", this.drop.writeToNBT(new NBTTagCompound()));
			compound.setInteger("min", this.min);
			compound.setInteger("max", this.max);
			compound.setFloat("chance", this.chance);
			NBTTagList conditionals = new NBTTagList();
			for(String condition : this.conditionals) conditionals.appendTag(new NBTTagString(condition));
			compound.setTag("conditionals", conditionals);
			return compound;
		}
	}

	/*
	 * CONSTANTS
	 */

	public static final String notEnoughResources = "neresources";
	public static final String registerDungeon = "registerDungeon";
	public static final String registerMob = "registerMob";
	public static final String registerOre = "registerOre";
	public static final String registerPlant = "registerPlant";
	public static final String addToRegistry = "add";
	public static final String modifyMob = "modifyMob";
	public static final String modifyOre = "modifyOre";
	public static final String modifyPlant = "modifyPlant";
	public static final String removeMob = "removeMob";
	public static final String removeOre = "removeOre";
	public static final String removePlant = "removePlant";
	public static final String distribution = "distribution";
	public static final String bestHeight = "bestHeight";
	public static final String stack = "stack";
	public static final String name = "name";
	public static final String lightLevel = "lightLevel";
	public static final String conditionals = "conditionals";
	public static final String colour = "colour";
	public static final String itemList = "itemList";
	public static final String chance = "chance";
	public static final String min = "min";
	public static final String max = "max";
	public static final String priority = "priority";
	public static final String addPriority = "addPriority";
	public static final String removePriority = "removePriority";
	public static final String addDrops = "addDrops";
	public static final String removeDrops = "removeDrops";
	public static final String silkTouch = "silkTouch";
	public static final String wither = "wither";
	public static final String strict = "strict";
	public static final String biomeArray = "biomeArray";
	public static final String type = "type";
	public static final String restriction = "restriction";
	public static final String blockRestriction = "block";
	public static final String dimensionRestriction = "dimension";
	public static final String biomeRestriction = "biome";

	public static final String conditional_rareDrop = "ner.rareDrop.text";
	public static final String conditional_silkTouch = "ner.ore.silkTouch";
	public static final String conditional_equipmentDrop = "ner.equipmentDrop.text";
	public static final String conditional_burning = "ner.burning.text";
	public static final String conditional_notBurning = "ner.notBurning.text";
	public static final String conditional_playerKill = "ner.playerKill.text";
	public static final String conditional_notPlayerKill = "ner.notPlayerKill.text";
	public static final String conditional_aboveLooting = "ner.aboveLooting.text";
	public static final String conditional_belowLooting = "ner.belowLooting.text";
	public static final String conditional_killedBy = "ner.killedBy.text";
	public static final String conditional_notKilledBy = "ner.notKilledBy.text";
}
