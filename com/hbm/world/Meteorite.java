package com.hbm.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class Meteorite {
	
	public void generate(World world, Random rand, int x, int y, int z) {
		
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
		//0 - Molten
		//1 - Cobble
		//2 - Broken
		//3 - Mix
		int hull = rand.nextInt(4);
		
		//0 - Cobble
		//1 - Broken
		//2 - Mix
		int outerPadding = 0;
		
		if(hull == 2)
			outerPadding = 1 + rand.nextInt(2);
		else if(hull == 3)
			outerPadding = 2;
		
		//0 - Broken
		//1 - Stone
		//2 - Netherrack
		int innerPadding = rand.nextInt(hull == 0 ? 3 : 2);
		
		//0 - Meteor
		//1 - Treasure
		//2 - Ore
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
			ipL.add(new ItemStack(Blocks.stone));
			break;
		case 2:
			ipL.add(new ItemStack(Blocks.netherrack));
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
		//0 - Molten
		//1 - Cobble
		//2 - Broken
		//3 - Mix
		int hull = rand.nextInt(4);
		
		//0 - Cobble
		//1 - Broken
		//2 - Mix
		int outerPadding = 0;
		
		if(hull == 2)
			outerPadding = 1 + rand.nextInt(2);
		else if(hull == 3)
			outerPadding = 2;
		
		//0 - Broken
		//1 - Stone
		//2 - Netherrack
		int innerPadding = rand.nextInt(hull == 0 ? 3 : 2);
		
		//0 - Meteor
		//1 - Treasure
		//2 - Ore
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
			ipL.add(new ItemStack(Blocks.stone));
			break;
		case 2:
			ipL.add(new ItemStack(Blocks.netherrack));
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
		//0 - Molten
		//1 - Cobble
		//2 - Broken
		//3 - Mix
		int hull = rand.nextInt(4);
		
		//0 - Meteor
		//1 - Treasure
		//2 - Ore
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
		world.setBlock(x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}

	public void genL1(World world, Random rand, int x, int y, int z, List<ItemStack> hull, List<ItemStack> op, List<ItemStack> ip, List<ItemStack> core) {
		generateSphere7x7(world, rand, x, y, z, hull);
		generateStar5x5(world, rand, x, y, z, op);
		generateStar3x3(world, rand, x, y, z, ip);
		ItemStack stack = core.get(rand.nextInt(core.size()));
		world.setBlock(x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}

	public void genL2(World world, Random rand, int x, int y, int z, List<ItemStack> hull, List<ItemStack> op, List<ItemStack> ip, List<ItemStack> core) {
		generateSphere7x7(world, rand, x, y, z, hull);
		generateSphere5x5(world, rand, x, y, z, op);
		generateStar3x3(world, rand, x, y, z, ip);
		ItemStack stack = core.get(rand.nextInt(core.size()));
		world.setBlock(x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}

	public void genL3(World world, Random rand, int x, int y, int z, List<ItemStack> hull, List<ItemStack> op, List<ItemStack> ip, List<ItemStack> core) {
		generateSphere7x7(world, rand, x, y, z, hull);
		generateSphere5x5(world, rand, x, y, z, op);
		generateBox(world, rand, x, y, z, ip);
		ItemStack stack = core.get(rand.nextInt(core.size()));
		world.setBlock(x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}

	public void genL4(World world, Random rand, int x, int y, int z, List<ItemStack> hull, List<ItemStack> op, List<ItemStack> ip, List<ItemStack> core) {
		generateSphere7x7(world, rand, x, y, z, hull);
		generateSphere5x5(world, rand, x, y, z, op);
		generateBox(world, rand, x, y, z, ip);
		generateStar3x3(world, rand, x, y, z, this.getRandomOre(rand));
		ItemStack stack = core.get(rand.nextInt(core.size()));
		world.setBlock(x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}

	public void genL5(World world, Random rand, int x, int y, int z, List<ItemStack> hull, List<ItemStack> op, List<ItemStack> ip, List<ItemStack> core) {
		generateSphere7x7(world, rand, x, y, z, hull);
		generateSphere5x5(world, rand, x, y, z, op);
		generateStar5x5(world, rand, x, y, z, ip);
		generateStar3x3(world, rand, x, y, z, this.getRandomOre(rand));
		ItemStack stack = core.get(rand.nextInt(core.size()));
		world.setBlock(x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}

	public void genM1(World world, Random rand, int x, int y, int z, List<ItemStack> hull, List<ItemStack> op, List<ItemStack> ip, List<ItemStack> core) {
		generateSphere5x5(world, rand, x, y, z, hull);
		ItemStack stack = core.get(rand.nextInt(core.size()));
		world.setBlock(x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}
	
	public void genM2(World world, Random rand, int x, int y, int z, List<ItemStack> hull, List<ItemStack> op, List<ItemStack> ip, List<ItemStack> core) {
		generateSphere5x5(world, rand, x, y, z, hull);
		generateStar3x3(world, rand, x, y, z, op);
		ItemStack stack = core.get(rand.nextInt(core.size()));
		world.setBlock(x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}
	
	public void genM3(World world, Random rand, int x, int y, int z, List<ItemStack> hull, List<ItemStack> op, List<ItemStack> ip, List<ItemStack> core) {
		generateSphere5x5(world, rand, x, y, z, hull);
		generateBox(world, rand, x, y, z, op);
		ItemStack stack = core.get(rand.nextInt(core.size()));
		world.setBlock(x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}
	
	public void genM4(World world, Random rand, int x, int y, int z, List<ItemStack> hull, List<ItemStack> op, List<ItemStack> ip, List<ItemStack> core) {
		generateSphere5x5(world, rand, x, y, z, hull);
		generateBox(world, rand, x, y, z, op);
		generateStar3x3(world, rand, x, y, z, ip);
		ItemStack stack = core.get(rand.nextInt(core.size()));
		world.setBlock(x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}
	
	public void genM5(World world, Random rand, int x, int y, int z, List<ItemStack> hull, List<ItemStack> op, List<ItemStack> ip, List<ItemStack> core) {
		generateSphere5x5(world, rand, x, y, z, hull);
		generateBox(world, rand, x, y, z, ip);
		ItemStack stack = core.get(rand.nextInt(core.size()));
		world.setBlock(x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}
	
	public void genM6(World world, Random rand, int x, int y, int z, List<ItemStack> hull, List<ItemStack> op, List<ItemStack> ip, List<ItemStack> core) {
		generateSphere5x5(world, rand, x, y, z, hull);
		generateBox(world, rand, x, y, z, ip);
		generateStar3x3(world, rand, x, y, z, this.getRandomOre(rand));
		ItemStack stack = core.get(rand.nextInt(core.size()));
		world.setBlock(x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}
	
	public void generateSphere7x7(World world, Random rand, int x, int y, int z, List<ItemStack> set) {
		for(int a = -3; a < 4; a++)
			for(int b = -1; b < 2; b++)
				for(int c = -1; c < 2; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					world.setBlock(x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
		for(int a = -1; a < 2; a++)
			for(int b = -3; b < 4; b++)
				for(int c = -1; c < 2; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					world.setBlock(x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
		for(int a = -1; a < 2; a++)
			for(int b = -1; b < 2; b++)
				for(int c = -3; c < 4; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					world.setBlock(x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
		
		for(int a = -2; a < 3; a++)
			for(int b = -2; b < 3; b++)
				for(int c = -1; c < 2; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					world.setBlock(x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
		for(int a = -1; a < 2; a++)
			for(int b = -2; b < 3; b++)
				for(int c = -2; c < 3; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					world.setBlock(x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
		for(int a = -2; a < 3; a++)
			for(int b = -1; b < 2; b++)
				for(int c = -2; c < 3; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					world.setBlock(x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
	}
	
	public void generateSphere5x5(World world, Random rand, int x, int y, int z, List<ItemStack> set) {
		for(int a = -2; a < 3; a++)
			for(int b = -1; b < 2; b++)
				for(int c = -1; c < 2; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					world.setBlock(x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
		for(int a = -1; a < 2; a++)
			for(int b = -2; b < 3; b++)
				for(int c = -1; c < 2; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					world.setBlock(x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
		for(int a = -1; a < 2; a++)
			for(int b = -1; b < 2; b++)
				for(int c = -2; c < 3; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					world.setBlock(x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
	}

	public void generateBox(World world, Random rand, int x, int y, int z, List<ItemStack> set) {
		for(int a = -1; a < 2; a++)
			for(int b = -1; b < 2; b++)
				for(int c = -1; c < 2; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					world.setBlock(x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}
	}
	
	public void generateStar5x5(World world, Random rand, int x, int y, int z, List<ItemStack> set) {
		for(int a = -1; a < 2; a++)
			for(int b = -1; b < 2; b++)
				for(int c = -1; c < 2; c++) {
					ItemStack stack = set.get(rand.nextInt(set.size()));
					world.setBlock(x + a, y + b, z + c, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
				}

		ItemStack stack = set.get(rand.nextInt(set.size()));
		world.setBlock(x + 2, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
		stack = set.get(rand.nextInt(set.size()));
		world.setBlock(x - 2, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
		stack = set.get(rand.nextInt(set.size()));
		world.setBlock(x, y + 2, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
		stack = set.get(rand.nextInt(set.size()));
		world.setBlock(x, y - 2, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
		stack = set.get(rand.nextInt(set.size()));
		world.setBlock(x, y, z + 2, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
		stack = set.get(rand.nextInt(set.size()));
		world.setBlock(x, y, z - 2, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}
	
	public void generateStar3x3(World world, Random rand, int x, int y, int z, List<ItemStack> set) {

		ItemStack stack = set.get(rand.nextInt(set.size()));
		world.setBlock(x, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
		stack = set.get(rand.nextInt(set.size()));
		world.setBlock(x + 1, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
		stack = set.get(rand.nextInt(set.size()));
		world.setBlock(x - 1, y, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
		stack = set.get(rand.nextInt(set.size()));
		world.setBlock(x, y + 1, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
		stack = set.get(rand.nextInt(set.size()));
		world.setBlock(x, y - 1, z, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
		stack = set.get(rand.nextInt(set.size()));
		world.setBlock(x, y, z + 1, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
		stack = set.get(rand.nextInt(set.size()));
		world.setBlock(x, y, z - 1, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 2);
	}
	
	public List<ItemStack> getRandomOre(Random rand) {
		List<ItemStack> ores = new ArrayList<ItemStack>();
		
		String[] names = OreDictionary.getOreNames();
		
		for(int i = 0; i < names.length; i++) {
			if(names[i].length() > 3 && names[i].substring(0, 3).equals("ore")) {
				
				List<ItemStack> ota = OreDictionary.getOres(names[i]);
				for(int j = 0; j < ota.size(); j++) {
					ItemStack stack = ota.get(j);
					if(Block.getBlockFromItem(stack.getItem()) != null)
						ores.add(stack.copy());
				}
			}
		}
		
		if(rand.nextInt(5) == 3) {
			List<ItemStack> list = new ArrayList<ItemStack>();
			list.add(new ItemStack(ModBlocks.ore_rare, 1).copy());
			return list;
		}
		
		if(ores.isEmpty()) {
			List<ItemStack> list = new ArrayList<ItemStack>();
			list.add(new ItemStack(Blocks.iron_ore, 1).copy());
			return list;
		} else {
			return ores;
		}
	}

}
