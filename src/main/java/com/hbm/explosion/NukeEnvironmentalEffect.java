package com.hbm.explosion;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class NukeEnvironmentalEffect {
	
	static Random rand = new Random();
	
	/**
	 * Area of effect radiation effect. j > 0 for jagged edges of the spherical area. Args: world, x, y, z, radius, outer radius with random chance.
	 */
	@Deprecated //does not use scorched uranium, implementation is garbage anyway
	public static void applyStandardAOE(World world, int x, int y, int z, int r, int j) {

		int r2 = r * r;
		int r22 = r2 / 2;
		for (int xx = -r; xx < r; xx++) {
			int X = xx + x;
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + y;
				int YY = XX + yy * yy;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + z;
					int ZZ = YY + zz * zz;
					if (ZZ < r22 + rand.nextInt(j)) {
						applyStandardEffect(world, X, Y, Z);
					}
				}
			}
		}
	}
	
	public static void applyStandardEffect(World world, int x, int y, int z) {
		int chance = 100;
		Block b = null;
		int meta = 0;
		
		Block in = world.getBlock(x, y, z);
		int inMeta = world.getBlockMetadata(x, y, z);
		
		if(in == Blocks.air)
			return;
		
		//Task done by fallout effect entity.
		/*if(in == Blocks.grass) {
			b = ModBlocks.waste_earth;
			
		} else */
		
		if(in == Blocks.sand) {
			
			if(inMeta == 1)
				b = ModBlocks.waste_trinitite_red;
			else
				b = ModBlocks.waste_trinitite;
			
			chance = 20;
			
		} else if(in == Blocks.mycelium) {
			b = ModBlocks.waste_mycelium;
			
		} else if(in == Blocks.log || in == Blocks.log2) {
			b = ModBlocks.waste_log;
			
		} else if(in == Blocks.planks) {
			b = ModBlocks.waste_planks;
			
		} else if(in == Blocks.mossy_cobblestone) {
			b = ModBlocks.ore_oil;
			chance = 50;
			
		} else if(in == Blocks.coal_ore) {
			b = Blocks.diamond_ore;
			chance = 10;
		} else if(in == ModBlocks.ore_uranium) {
			b = ModBlocks.ore_schrabidium;
			chance = 10;
			
		} else if(in == ModBlocks.ore_nether_uranium) {
			b = ModBlocks.ore_nether_schrabidium;
			chance = 10;
			
		} else if(in == ModBlocks.ore_nether_plutonium) {
			b = ModBlocks.ore_nether_schrabidium;
			chance = 25;
			
		} else if(in == Blocks.brown_mushroom_block && inMeta == 10) {
			b = ModBlocks.waste_planks;
			
		} else if(in == Blocks.red_mushroom_block && inMeta == 10) {
			b = ModBlocks.waste_planks;
			
		} else if(in == Blocks.end_stone) {
			b = ModBlocks.ore_tikite;
			chance = 1;
			
		} else if(in == Blocks.clay) {
			b = Blocks.hardened_clay;
		} else if(in.getMaterial().getCanBurn()) {
			b = Blocks.fire;
			chance = 100;
		}
		
		if(b != null && rand.nextInt(1000) < chance)
			world.setBlock(x, y, z, b, meta, 2);
	}

}
