package com.hbm.util;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockLoot.TileEntityLoot;
import com.hbm.crafting.handlers.MKUCraftingHandler;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.itempool.ItemPool;
import com.hbm.itempool.ItemPoolsPile;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBookLore;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class LootGenerator {

	public static final String LOOT_BOOKLET = "LOOT_BOOKLET";
	public static final String LOOT_CAPNUKE = "LOOT_CAPNUKE";
	public static final String LOOT_MEDICINE = "LOOT_MEDICINE";
	public static final String LOOT_CAPSTASH = "LOOT_CAPSTASH";
	public static final String LOOT_MAKESHIFT_GUN = "LOOT_MAKESHIFT_GUN";
	public static final String LOOT_NUKE_STORAGE = "LOOT_NUKE_STORAGE";
	public static final String LOOT_BONES = "LOOT_BONES";
	public static final String LOOT_GLYPHID_HIVE = "LOOT_GLYPHID_HIVE";
	public static final String LOOT_METEOR = "LOOT_METEOR";

	public static void applyLoot(World world, int x, int y, int z, String name) {
		switch(name) {
			case LOOT_BOOKLET: lootBooklet(world, x, y, z);
			case LOOT_CAPNUKE: lootCapNuke(world, x, y, z);
			case LOOT_MEDICINE: lootMedicine(world, x, y, z);
			case LOOT_CAPSTASH: lootCapStash(world, x, y, z);
			case LOOT_MAKESHIFT_GUN: lootMakeshiftGun(world, x, y, z);
			case LOOT_NUKE_STORAGE: lootNukeStorage(world, x, y, z);
			case LOOT_BONES: lootBones(world, x, y, z);
			case LOOT_GLYPHID_HIVE: lootGlyphidHive(world, x, y, z);
			case LOOT_METEOR: lootBookMeteor(world, x, y, z);
			default: lootBones(world, x, y, z); break;
		}
	}

	public static String[] getLootNames() {
		return new String[] {
			LOOT_BOOKLET,
			LOOT_CAPNUKE,
			LOOT_MEDICINE,
			LOOT_CAPSTASH,
			LOOT_MAKESHIFT_GUN,
			LOOT_NUKE_STORAGE,
			LOOT_BONES,
			LOOT_GLYPHID_HIVE,
			LOOT_METEOR,
		};
	}

	public static void setBlock(World world, int x, int y, int z) {
		world.setBlock(x, y, z, ModBlocks.deco_loot);
	}

	public static void addItemWithDeviation(TileEntityLoot loot, Random rand, ItemStack stack, double  x, double y, double z) {
		loot.addItem(stack, x + rand.nextGaussian() * 0.02, y, z + rand.nextGaussian() * 0.02);
	}

	public static void lootBooklet(World world, int x, int y, int z) {

		TileEntityLoot loot = (TileEntityLoot) world.getTileEntity(x, y, z);

		if(loot != null && loot.items.isEmpty()) {
			loot.addItem(ItemBookLore.createBook("beacon", 12, 0x404040, 0xD637B3), 0, 0, 0);;
		}
	}

	public static void lootCapNuke(World world, int x, int y, int z) {

		TileEntityLoot loot = (TileEntityLoot) world.getTileEntity(x, y, z);

		if(loot != null && loot.items.isEmpty()) {

			if(world.rand.nextInt(5) == 0)
				loot.addItem(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.NUKE_STANDARD), -0.25, 0, -0.125);
			else
				loot.addItem(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.ROCKET_HEAT), -0.25, 0, -0.25);

			for(int i = 0; i < 4; i++) addItemWithDeviation(loot, world.rand, new ItemStack(ModItems.cap_nuka, 2), 0.125, i * 0.03125, 0.25);
			for(int i = 0; i < 2; i++) addItemWithDeviation(loot, world.rand, new ItemStack(ModItems.syringe_metal_stimpak, 1), -0.25, i * 0.03125, 0.25);
			for(int i = 0; i < 6; i++) addItemWithDeviation(loot, world.rand, new ItemStack(ModItems.cap_nuka, 2), 0.125, i * 0.03125, -0.25);
		}
	}

	public static void lootMedicine(World world, int x, int y, int z) {

		TileEntityLoot loot = (TileEntityLoot) world.getTileEntity(x, y, z);

		if(loot != null && loot.items.isEmpty()) {

			for(int i = 0; i < 4; i++) addItemWithDeviation(loot, world.rand, ItemPool.getStack(ItemPoolsPile.POOL_PILE_MED_SYRINGE, world.rand), 0.125, i * 0.03125, 0.25);
			addItemWithDeviation(loot, world.rand, ItemPool.getStack(ItemPoolsPile.POOL_PILE_MED_PILLS, world.rand), -0.25, 0, -0.125);
		}
	}

	public static void lootCapStash(World world, int x, int y, int z) {

		TileEntityLoot loot = (TileEntityLoot) world.getTileEntity(x, y, z);

		if(loot != null && loot.items.isEmpty()) {

			for(int i = -1; i <= 1; i++) {
				for(int j = -1; j <= 1; j++) {

					int count = world.rand.nextInt(5) + 3;
					for(int k = 0; k < count; k++) {
						addItemWithDeviation(loot, world.rand, ItemPool.getStack(ItemPoolsPile.POOL_PILE_CAPS, world.rand), i * 0.3125, k * 0.03125, j * 0.3125);
					}
				}
			}
		}
	}

	public static void lootMakeshiftGun(World world, int x, int y, int z) {

		TileEntityLoot loot = (TileEntityLoot) world.getTileEntity(x, y, z);

		if(loot != null && loot.items.isEmpty()) {

			boolean r = world.rand.nextBoolean();
			if(r) addItemWithDeviation(loot, world.rand, ItemPool.getStack(ItemPoolsPile.POOL_PILE_MAKESHIFT_GUN, world.rand), 0.125, 0.025, 0.25);

			if(!r || world.rand.nextBoolean()) addItemWithDeviation(loot, world.rand, ItemPool.getStack(ItemPoolsPile.POOL_PILE_MAKESHIFT_WRENCH, world.rand), -0.25, 0, -0.28125);

			int count = world.rand.nextInt(2) + 1;
			for(int i = 0; i < count; i++) addItemWithDeviation(loot, world.rand, ItemPool.getStack(ItemPoolsPile.POOL_PILE_MAKESHIFT_PLATES, world.rand), -0.25, i * 0.03125, 0.3125);

			count = world.rand.nextInt(2) + 2;
			for(int i = 0; i < count; i++) addItemWithDeviation(loot, world.rand, ItemPool.getStack(ItemPoolsPile.POOL_PILE_MAKESHIFT_WIRE, world.rand), 0.25, i * 0.03125, 0.1875);
		}
	}

	public static void lootNukeStorage(World world, int x, int y, int z) {

		TileEntityLoot loot = (TileEntityLoot) world.getTileEntity(x, y, z);

		if(loot != null && loot.items.isEmpty()) {

			for(int i = 0; i < 4; i++) {
				for(int j = 0; j < 4; j++) {

					if(world.rand.nextBoolean()) {
						loot.addItem(ItemPool.getStack(ItemPoolsPile.POOL_PILE_NUKE_STORAGE, world.rand), -0.375 + i * 0.25, 0, -0.375 + j * 0.25);
					}
				}
			}
		}
	}

	public static void lootBones(World world, int x, int y, int z) {

		TileEntityLoot loot = (TileEntityLoot) world.getTileEntity(x, y, z);

		if(loot != null && loot.items.isEmpty()) {

			int limit = world.rand.nextInt(3) + 3;
			for(int i = 0; i < limit; i++) {
				addItemWithDeviation(loot, world.rand, ItemPool.getStack(ItemPool.getPool(ItemPoolsPile.POOL_PILE_BONES), world.rand), world.rand.nextDouble() - 0.5, i * 0.03125, world.rand.nextDouble() - 0.5);
			}
		}
	}

	public static void lootGlyphidHive(World world, int x, int y, int z) {

		TileEntityLoot loot = (TileEntityLoot) world.getTileEntity(x, y, z);

		if(loot != null && loot.items.isEmpty()) {

			int limit = world.rand.nextInt(3) + 3;
			for(int i = 0; i < limit; i++) {
				addItemWithDeviation(loot, world.rand, ItemPool.getStack(ItemPool.getPool(ItemPoolsPile.POOL_PILE_HIVE), world.rand), world.rand.nextDouble() - 0.5, i * 0.03125, world.rand.nextDouble() - 0.5);
			}
		}
	}

	public static void lootBookMeteor(World world, int x, int y, int z) {

		TileEntityLoot loot = (TileEntityLoot) world.getTileEntity(x, y, z);

		if(loot != null && loot.items.isEmpty()) {
			Item mkuItem = MKUCraftingHandler.getMKUItem(world);
			ItemStack mkuBook = MKUCraftingHandler.generateBook(world, mkuItem);

			addItemWithDeviation(loot, world.rand, new ItemStack(mkuItem), 0, 0, 0.25);
			addItemWithDeviation(loot, world.rand, mkuBook, 0, 0, -0.25);
		}
	}

	public static void lootBookLore(World world, int x, int y, int z, ItemStack book) {

		TileEntityLoot loot = (TileEntityLoot) world.getTileEntity(x, y, z);

		if(loot != null && loot.items.isEmpty()) {
			addItemWithDeviation(loot, world.rand, book, 0, 0, -0.25);

			int count = world.rand.nextInt(3) + 2;
			for(int k = 0; k < count; k++) addItemWithDeviation(loot, world.rand, new ItemStack(Items.book), -0.25, k * 0.03125, 0.25);

			count = world.rand.nextInt(2) + 1;
			for(int k = 0; k < count; k++) addItemWithDeviation(loot, world.rand, new ItemStack(Items.paper), 0.25, k * 0.03125, 0.125);
		}
	}

}