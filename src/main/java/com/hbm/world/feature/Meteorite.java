package com.hbm.world.feature;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.BlockEnums.EnumMeteorType;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.WorldConfig;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

@Spaghetti("why")
public class Meteorite {
	
	public static boolean safeMode = false;

	public void generate(World world, Random rand, int x, int y, int z, boolean safe, boolean allowSpecials, boolean damagingImpact) {
		safeMode = safe;
		
		if(replacables.isEmpty()) {
			generateReplacables();
		}

		if(damagingImpact) {
			List<Entity> list = (List<Entity>) world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(x - 7.5, y - 7.5, z - 7.5, x + 7.5, y + 7.5, z + 7.5));
	
			for(Entity e : list) {
				e.attackEntityFrom(ModDamageSource.meteorite, 1000);
			}
		}

		if(WorldConfig.enableSpecialMeteors && allowSpecials)
			switch(rand.nextInt(300)) {
			case 0:
				// Meteor-only tiny meteorite
				List<ItemStack> list0 = new ArrayList<ItemStack>();
				list0.add(new ItemStack(ModBlocks.block_meteor));
				generateBox(world, rand, x, y, z, list0);
				return;
			case 1:
				// Large ore-only meteorite
				List<ItemStack> list1 = new ArrayList<ItemStack>();
				list1.addAll(this.getRandomOre(rand));
				int i = list1.size();
				for(int j = 0; j < i; j++)
					list1.add(new ItemStack(ModBlocks.block_meteor_broken));
				generateSphere7x7(world, rand, x, y, z, list1);
				return;
			case 2:
				// Medium ore-only meteorite
				List<ItemStack> list2 = new ArrayList<ItemStack>();
				list2.addAll(this.getRandomOre(rand));
				int k = list2.size() / 2;
				for(int j = 0; j < k; j++)
					list2.add(new ItemStack(ModBlocks.block_meteor_broken));
				generateSphere5x5(world, rand, x, y, z, list2);
				return;
			case 3:
				// Small pure ore meteorite
				List<ItemStack> list3 = new ArrayList<ItemStack>();
				list3.addAll(this.getRandomOre(rand));
				generateBox(world, rand, x, y, z, list3);
				return;
			case 4:
				// Bamboozle
				world.createExplosion(null, x + 0.5, y + 0.5, z + 0.5, 15F, !safe);
				ExplosionLarge.spawnRubble(world, x, y, z, 25);
				return;
			case 5:
				// Large treasure-only meteorite
				List<ItemStack> list4 = new ArrayList<ItemStack>();
				list4.add(new ItemStack(ModBlocks.block_meteor_treasure));
				list4.add(new ItemStack(ModBlocks.block_meteor_broken));
				generateSphere7x7(world, rand, x, y, z, list4);
				return;
			case 6:
				// Medium treasure-only meteorite
				List<ItemStack> list5 = new ArrayList<ItemStack>();
				list5.add(new ItemStack(ModBlocks.block_meteor_treasure));
				list5.add(new ItemStack(ModBlocks.block_meteor_treasure));
				list5.add(new ItemStack(ModBlocks.block_meteor_broken));
				generateSphere5x5(world, rand, x, y, z, list5);
				return;
			case 7:
				// Small pure treasure meteorite
				List<ItemStack> list6 = new ArrayList<ItemStack>();
				list6.add(new ItemStack(ModBlocks.block_meteor_treasure));
				generateBox(world, rand, x, y, z, list6);
				return;
			case 8:
				// Large nuclear meteorite
				List<ItemStack> list7 = new ArrayList<ItemStack>();
				list7.add(new ItemStack(ModBlocks.block_meteor_treasure));
				List<ItemStack> list8 = new ArrayList<ItemStack>();
				list8.add(new ItemStack(ModBlocks.toxic_block));
				generateSphere7x7(world, rand, x, y, z, list7);
				generateSphere5x5(world, rand, x, y, z, list8);
				return;
			case 9:
				// Giant ore meteorite
				List<ItemStack> list9 = new ArrayList<ItemStack>();
				list9.add(new ItemStack(ModBlocks.block_meteor_broken));
				generateSphere9x9(world, rand, x, y, z, list9);
				generateSphere7x7(world, rand, x, y, z, this.getRandomOre(rand));
				return;
			case 10:
				// Tainted Meteorite
				List<ItemStack> list10 = new ArrayList<ItemStack>();
				list10.add(new ItemStack(ModBlocks.block_meteor_broken));
				generateSphere5x5(world, rand, x, y, z, list10);
				setBlock(world, x, y, z, ModBlocks.taint, 9, 2);
				return;
			case 12:
				// Star Blaster
				world.createExplosion(null, x + 0.5, y + 0.5, z + 0.5, 10F, !safe);
				ItemStack stack = new ItemStack(ModItems.gun_b92);
				stack.setStackDisplayName("§9Star Blaster§r");
				EntityItem blaster = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, stack);
				world.spawnEntityInWorld(blaster);
				return;
			}

		switch(rand.nextInt(3)) {
		case 0:
			generateLarge(world, rand, x, y, z);
			break;
		case 1:
			generateMedium(world, rand, x, y, z);
			break;
		case 2:
			generateSmall(world, rand, x, y, z);
			break;
		}
	}

	public void generateLarge(World world, Random rand, int x, int y, int z) {
		// 0 - Molten
		// 1 - Cobble
		// 2 - Broken
		// 3 - Mix
		int hull = rand.nextInt(4);

		// 0 - Cobble
		// 1 - Broken
		// 2 - Mix
		int outerPadding = 0;

		if(hull == 2)
			outerPadding = 1 + rand.nextInt(2);
		else if(hull == 3)
			outerPadding = 2;

		// 0 - Broken
		// 1 - Stone
		// 2 - Netherrack
		int innerPadding = rand.nextInt(hull == 0 ? 3 : 2);

		// 0 - Meteor
		// 1 - Treasure
		// 2 - Ore
		int core = rand.nextInt(2);
		if(innerPadding > 0)
			core = 2;

		List<ItemStack> hullL = new ArrayList<ItemStack>();
		switch(hull) {
		case 0:
			hullL.add(new ItemStack(ModBlocks.block_meteor_molten));
			break;
		case 1:
			hullL.add(new ItemStack(ModBlocks.block_meteor_cobble));
			break;
		case 2:
			for(int i = 0; i < 99; i++)
				hullL.add(new ItemStack(ModBlocks.block_meteor_broken));
			hullL.add(new ItemStack(ModBlocks.block_meteor_treasure));
			break;
		case 3:
			hullL.add(new ItemStack(ModBlocks.block_meteor_molten));
			hullL.add(new ItemStack(ModBlocks.block_meteor_broken));
			break;
		}

		List<ItemStack> opL = new ArrayList<ItemStack>();
		switch(outerPadding) {
		case 0:
			opL.add(new ItemStack(ModBlocks.block_meteor_cobble));
			break;
		case 1:
			for(int i = 0; i < 99; i++)
				opL.add(new ItemStack(ModBlocks.block_meteor_broken));
			opL.add(new ItemStack(ModBlocks.block_meteor_treasure));
			break;
		case 2:
			opL.add(new ItemStack(ModBlocks.block_meteor_cobble));
			opL.add(new ItemStack(ModBlocks.block_meteor_broken));
			break;
		}

		List<ItemStack> ipL = new ArrayList<ItemStack>();
		switch(innerPadding) {
		case 0:
			for(int i = 0; i < 99; i++)
				ipL.add(new ItemStack(ModBlocks.block_meteor_broken));
			ipL.add(new ItemStack(ModBlocks.block_meteor_treasure));
			break;
		case 1:
			ipL.add(new ItemStack(ModBlocks.block_meteor_broken));
			break;
		case 2:
			ipL.add(new ItemStack(ModBlocks.block_meteor_cobble));
			break;
		}

		List<ItemStack> coreL = new ArrayList<ItemStack>();
		switch(core) {
		case 0:
			coreL.add(new ItemStack(ModBlocks.block_meteor));
			break;
		case 1:
			coreL.add(new ItemStack(ModBlocks.block_meteor_treasure));
			break;
		case 2:
			coreL.addAll(this.getRandomOre(rand));
			break;
		}

		switch(rand.nextInt(5)) {
		case 0:
			genL1(world, rand, x, y, z, hullL, opL, ipL, coreL);
			break;
		case 1:
			genL2(world, rand, x, y, z, hullL, opL, ipL, coreL);
			break;
		case 2:
			genL3(world, rand, x, y, z, hullL, opL, ipL, coreL);
			break;
		case 3:
			genL4(world, rand, x, y, z, hullL, opL, ipL, coreL);
			break;
		case 4:
			genL5(world, rand, x, y, z, hullL, opL, ipL, coreL);
			break;
		}
	}

	public void generateMedium(World world, Random rand, int x, int y, int z) {
		// 0 - Molten
		// 1 - Cobble
		// 2 - Broken
		// 3 - Mix
		int hull = rand.nextInt(4);

		// 0 - Cobble
		// 1 - Broken
		// 2 - Mix
		int outerPadding = 0;

		if(hull == 2)
			outerPadding = 1 + rand.nextInt(2);
		else if(hull == 3)
			outerPadding = 2;

		// 0 - Broken
		// 1 - Stone
		// 2 - Netherrack
		int innerPadding = rand.nextInt(hull == 0 ? 3 : 2);

		// 0 - Meteor
		// 1 - Treasure
		// 2 - Ore
		int core = rand.nextInt(2);
		if(innerPadding > 0)
			core = 2;

		List<ItemStack> hullL = new ArrayList<ItemStack>();
		switch(hull) {
		case 0:
			hullL.add(new ItemStack(ModBlocks.block_meteor_molten));
			break;
		case 1:
			hullL.add(new ItemStack(ModBlocks.block_meteor_cobble));
			break;
		case 2:
			for(int i = 0; i < 99; i++)
				hullL.add(new ItemStack(ModBlocks.block_meteor_broken));
			hullL.add(new ItemStack(ModBlocks.block_meteor_treasure));
			break;
		case 3:
			hullL.add(new ItemStack(ModBlocks.block_meteor_molten));
			hullL.add(new ItemStack(ModBlocks.block_meteor_broken));
			break;
		}

		List<ItemStack> opL = new ArrayList<ItemStack>();
		switch(outerPadding) {
		case 0:
			opL.add(new ItemStack(ModBlocks.block_meteor_cobble));
			break;
		case 1:
			for(int i = 0; i < 99; i++)
				opL.add(new ItemStack(ModBlocks.block_meteor_broken));
			opL.add(new ItemStack(ModBlocks.block_meteor_treasure));
			break;
		case 2:
			opL.add(new ItemStack(ModBlocks.block_meteor_cobble));
			opL.add(new ItemStack(ModBlocks.block_meteor_broken));
			break;
		}

		List<ItemStack> ipL = new ArrayList<ItemStack>();
		switch(innerPadding) {
		case 0:
			for(int i = 0; i < 99; i++)
				ipL.add(new ItemStack(ModBlocks.block_meteor_broken));
			ipL.add(new ItemStack(ModBlocks.block_meteor_treasure));
			break;
		case 1:
			ipL.add(new ItemStack(ModBlocks.block_meteor_broken));
			break;
		case 2:
			ipL.add(new ItemStack(ModBlocks.block_meteor_cobble));
			break;
		}

		List<ItemStack> coreL = new ArrayList<ItemStack>();
		switch(core) {
		case 0:
			coreL.add(new ItemStack(ModBlocks.block_meteor));
			break;
		case 1:
			coreL.add(new ItemStack(ModBlocks.block_meteor_treasure));
			break;
		case 2:
			coreL.addAll(this.getRandomOre(rand));
			break;
		}

		List<ItemStack> sCore = new ArrayList<ItemStack>();
		switch(core) {
		case 0:
			sCore.add(new ItemStack(ModBlocks.block_meteor));
			break;
		case 1:
			sCore.add(new ItemStack(ModBlocks.block_meteor_treasure));
			break;
		case 2:
			sCore.add(new ItemStack(ModBlocks.block_meteor_treasure));
			sCore.add(new ItemStack(ModBlocks.block_meteor));
			break;
		}

		switch(rand.nextInt(6)) {
		case 0:
			genM1(world, rand, x, y, z, hullL, opL, ipL, sCore);
			break;
		case 1:
			genM2(world, rand, x, y, z, hullL, opL, ipL, coreL);
			break;
		case 2:
			genM3(world, rand, x, y, z, hullL, opL, ipL, coreL);
			break;
		case 3:
			genM4(world, rand, x, y, z, hullL, opL, ipL, coreL);
			break;
		case 4:
			genM5(world, rand, x, y, z, hullL, opL, ipL, coreL);
			break;
		case 5:
			genM6(world, rand, x, y, z, hullL, opL, ipL, coreL);
			break;
		}
	}

	public void generateSmall(World world, Random rand, int x, int y, int z) {
		// 0 - Molten
		// 1 - Cobble
		// 2 - Broken
		// 3 - Mix
		int hull = rand.nextInt(4);

		// 0 - Meteor
		// 1 - Treasure
		// 2 - Ore
		int core = rand.nextInt(3);

		List<ItemStack> hullL = new ArrayList<ItemStack>();
		switch(hull) {
		case 0:
			hullL.add(new ItemStack(ModBlocks.block_meteor_molten));
			break;
		case 1:
			hullL.add(new ItemStack(ModBlocks.block_meteor_cobble));
			break;
		case 2:
			for(int i = 0; i < 99; i++)
				hullL.add(new ItemStack(ModBlocks.block_meteor_broken));
			hullL.add(new ItemStack(ModBlocks.block_meteor_treasure));
			break;
		case 3:
			hullL.add(new ItemStack(ModBlocks.block_meteor_molten));
			hullL.add(new ItemStack(ModBlocks.block_meteor_broken));
			break;
		}

		List<ItemStack> sCore = new ArrayList<ItemStack>();
		switch(core) {
		case 0:
			sCore.add(new ItemStack(ModBlocks.block_meteor));
			break;
		case 1:
			sCore.add(new ItemStack(ModBlocks.block_meteor_treasure));
			break;
		case 2:
			sCore.add(new ItemStack(ModBlocks.block_meteor_treasure));
			sCore.add(new ItemStack(ModBlocks.block_meteor));
			break;
		}

		generateBox(world, rand, x, y, z, hullL);
		ItemStack stack = sCore.get(rand.nextInt(sCore.size()));
		setBlock(world, x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}

	public void genL1(World world, Random rand, int x, int y, int z, List<ItemStack> hull, List<ItemStack> op, List<ItemStack> ip, List<ItemStack> core) {
		generateSphere7x7(world, rand, x, y, z, hull);
		generateStar5x5(world, rand, x, y, z, op);
		generateStar3x3(world, rand, x, y, z, ip);
		ItemStack stack = core.get(rand.nextInt(core.size()));
		setBlock(world, x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}

	public void genL2(World world, Random rand, int x, int y, int z, List<ItemStack> hull, List<ItemStack> op, List<ItemStack> ip, List<ItemStack> core) {
		generateSphere7x7(world, rand, x, y, z, hull);
		generateSphere5x5(world, rand, x, y, z, op);
		generateStar3x3(world, rand, x, y, z, ip);
		ItemStack stack = core.get(rand.nextInt(core.size()));
		setBlock(world, x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}

	public void genL3(World world, Random rand, int x, int y, int z, List<ItemStack> hull, List<ItemStack> op, List<ItemStack> ip, List<ItemStack> core) {
		generateSphere7x7(world, rand, x, y, z, hull);
		generateSphere5x5(world, rand, x, y, z, op);
		generateBox(world, rand, x, y, z, ip);
		ItemStack stack = core.get(rand.nextInt(core.size()));
		setBlock(world, x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}

	public void genL4(World world, Random rand, int x, int y, int z, List<ItemStack> hull, List<ItemStack> op, List<ItemStack> ip, List<ItemStack> core) {
		generateSphere7x7(world, rand, x, y, z, hull);
		generateSphere5x5(world, rand, x, y, z, op);
		generateBox(world, rand, x, y, z, ip);
		generateStar3x3(world, rand, x, y, z, this.getRandomOre(rand));
		ItemStack stack = core.get(rand.nextInt(core.size()));
		setBlock(world, x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}

	public void genL5(World world, Random rand, int x, int y, int z, List<ItemStack> hull, List<ItemStack> op, List<ItemStack> ip, List<ItemStack> core) {
		generateSphere7x7(world, rand, x, y, z, hull);
		generateSphere5x5(world, rand, x, y, z, op);
		generateStar5x5(world, rand, x, y, z, ip);
		generateStar3x3(world, rand, x, y, z, this.getRandomOre(rand));
		ItemStack stack = core.get(rand.nextInt(core.size()));
		setBlock(world, x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}

	public void genM1(World world, Random rand, int x, int y, int z, List<ItemStack> hull, List<ItemStack> op, List<ItemStack> ip, List<ItemStack> core) {
		generateSphere5x5(world, rand, x, y, z, hull);
		ItemStack stack = core.get(rand.nextInt(core.size()));
		setBlock(world, x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}

	public void genM2(World world, Random rand, int x, int y, int z, List<ItemStack> hull, List<ItemStack> op, List<ItemStack> ip, List<ItemStack> core) {
		generateSphere5x5(world, rand, x, y, z, hull);
		generateStar3x3(world, rand, x, y, z, op);
		ItemStack stack = core.get(rand.nextInt(core.size()));
		setBlock(world, x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}

	public void genM3(World world, Random rand, int x, int y, int z, List<ItemStack> hull, List<ItemStack> op, List<ItemStack> ip, List<ItemStack> core) {
		generateSphere5x5(world, rand, x, y, z, hull);
		generateBox(world, rand, x, y, z, op);
		ItemStack stack = core.get(rand.nextInt(core.size()));
		setBlock(world, x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}

	public void genM4(World world, Random rand, int x, int y, int z, List<ItemStack> hull, List<ItemStack> op, List<ItemStack> ip, List<ItemStack> core) {
		generateSphere5x5(world, rand, x, y, z, hull);
		generateBox(world, rand, x, y, z, op);
		generateStar3x3(world, rand, x, y, z, ip);
		ItemStack stack = core.get(rand.nextInt(core.size()));
		setBlock(world, x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}

	public void genM5(World world, Random rand, int x, int y, int z, List<ItemStack> hull, List<ItemStack> op, List<ItemStack> ip, List<ItemStack> core) {
		generateSphere5x5(world, rand, x, y, z, hull);
		generateBox(world, rand, x, y, z, ip);
		ItemStack stack = core.get(rand.nextInt(core.size()));
		setBlock(world, x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}

	public void genM6(World world, Random rand, int x, int y, int z, List<ItemStack> hull, List<ItemStack> op, List<ItemStack> ip, List<ItemStack> core) {
		generateSphere5x5(world, rand, x, y, z, hull);
		generateBox(world, rand, x, y, z, ip);
		generateStar3x3(world, rand, x, y, z, this.getRandomOre(rand));
		ItemStack stack = core.get(rand.nextInt(core.size()));
		setBlock(world, x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}

	public void generateSphere7x7(World world, Random rand, int x, int y, int z, List<ItemStack> set) {
		for(int a = -3; a < 4; a++)
			for(int b = -1; b < 2; b++)
				for(int c = -1; c < 2; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					setBlock(world, x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
		for(int a = -1; a < 2; a++)
			for(int b = -3; b < 4; b++)
				for(int c = -1; c < 2; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					setBlock(world, x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
		for(int a = -1; a < 2; a++)
			for(int b = -1; b < 2; b++)
				for(int c = -3; c < 4; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					setBlock(world, x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}

		for(int a = -2; a < 3; a++)
			for(int b = -2; b < 3; b++)
				for(int c = -1; c < 2; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					setBlock(world, x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
		for(int a = -1; a < 2; a++)
			for(int b = -2; b < 3; b++)
				for(int c = -2; c < 3; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					setBlock(world, x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
		for(int a = -2; a < 3; a++)
			for(int b = -1; b < 2; b++)
				for(int c = -2; c < 3; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					setBlock(world, x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
	}

	public void generateSphere5x5(World world, Random rand, int x, int y, int z, List<ItemStack> set) {
		for(int a = -2; a < 3; a++)
			for(int b = -1; b < 2; b++)
				for(int c = -1; c < 2; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					setBlock(world, x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
		for(int a = -1; a < 2; a++)
			for(int b = -2; b < 3; b++)
				for(int c = -1; c < 2; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					setBlock(world, x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
		for(int a = -1; a < 2; a++)
			for(int b = -1; b < 2; b++)
				for(int c = -2; c < 3; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					setBlock(world, x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
	}

	public void generateSphere9x9(World world, Random rand, int x, int y, int z, List<ItemStack> set) {
		for(int a = -4; a < 5; a++)
			for(int b = -1; b < 2; b++)
				for(int c = -1; c < 2; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					setBlock(world, x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
		for(int a = -1; a < 2; a++)
			for(int b = -4; b < 5; b++)
				for(int c = -1; c < 2; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					setBlock(world, x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
		for(int a = -1; a < 2; a++)
			for(int b = -1; b < 2; b++)
				for(int c = -4; c < 5; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					setBlock(world, x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}

		for(int a = -1; a < 2; a++)
			for(int b = -3; b < 4; b++)
				for(int c = -3; c < 4; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					setBlock(world, x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
		for(int a = -3; a < 4; a++)
			for(int b = -1; b < 2; b++)
				for(int c = -3; c < 4; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					setBlock(world, x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
		for(int a = -3; a < 4; a++)
			for(int b = -3; b < 4; b++)
				for(int c = -1; c < 2; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					setBlock(world, x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}

		for(int a = -3; a < 4; a++)
			for(int b = -2; b < 3; b++)
				for(int c = -2; c < 3; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					setBlock(world, x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
		for(int a = -2; a < 3; a++)
			for(int b = -3; b < 4; b++)
				for(int c = -2; c < 3; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					setBlock(world, x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
		for(int a = -2; a < 3; a++)
			for(int b = -2; b < 3; b++)
				for(int c = -3; c < 4; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					setBlock(world, x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
	}

	public void generateBox(World world, Random rand, int x, int y, int z, List<ItemStack> set) {
		for(int a = -1; a < 2; a++)
			for(int b = -1; b < 2; b++)
				for(int c = -1; c < 2; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					setBlock(world, x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
	}

	public void generateStar5x5(World world, Random rand, int x, int y, int z, List<ItemStack> set) {
		for(int a = -1; a < 2; a++)
			for(int b = -1; b < 2; b++)
				for(int c = -1; c < 2; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					setBlock(world, x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}

		ItemStack stack = set.get(rand.nextInt(set.size()));
		setBlock(world, x + 2, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
		stack = set.get(rand.nextInt(set.size()));
		setBlock(world, x - 2, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
		stack = set.get(rand.nextInt(set.size()));
		setBlock(world, x, y + 2, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
		stack = set.get(rand.nextInt(set.size()));
		setBlock(world, x, y - 2, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
		stack = set.get(rand.nextInt(set.size()));
		setBlock(world, x, y, z + 2, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
		stack = set.get(rand.nextInt(set.size()));
		setBlock(world, x, y, z - 2, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}

	public void generateStar3x3(World world, Random rand, int x, int y, int z, List<ItemStack> set) {

		ItemStack stack = set.get(rand.nextInt(set.size()));
		setBlock(world, x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
		stack = set.get(rand.nextInt(set.size()));
		setBlock(world, x + 1, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
		stack = set.get(rand.nextInt(set.size()));
		setBlock(world, x - 1, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
		stack = set.get(rand.nextInt(set.size()));
		setBlock(world, x, y + 1, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
		stack = set.get(rand.nextInt(set.size()));
		setBlock(world, x, y - 1, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
		stack = set.get(rand.nextInt(set.size()));
		setBlock(world, x, y, z + 1, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
		stack = set.get(rand.nextInt(set.size()));
		setBlock(world, x, y, z - 1, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}

	public List<ItemStack> getRandomOre(Random rand) {
		List<ItemStack> ores = new ArrayList<ItemStack>();
		for(EnumMeteorType num : EnumMeteorType.values()) ores.add(DictFrame.fromOne(ModBlocks.ore_meteor, num));
		return ores;
	}
	
	private void setBlock(World world, int x, int y, int z, Block b, int meta, int flag) {
		Block target = world.getBlock(x, y, z);
		
		if(safeMode) {
			if(!target.isReplaceable(world, x, y, z) && !replacables.contains(target)) return; 
		}
		
		float hardness = target.getBlockHardness(world, x, y, z);
		if(hardness != -1 && hardness < 10_000)
			world.setBlock(x, y, z, b, meta, flag);
	}
	
	public static HashSet<Block> replacables = new HashSet();
	
	public static void generateReplacables() {
		replacables.add(ModBlocks.block_meteor);
		replacables.add(ModBlocks.block_meteor_broken);
		replacables.add(ModBlocks.block_meteor_cobble);
		replacables.add(ModBlocks.block_meteor_molten);
		replacables.add(ModBlocks.block_meteor_treasure);
		replacables.add(ModBlocks.ore_meteor);
	}
}
