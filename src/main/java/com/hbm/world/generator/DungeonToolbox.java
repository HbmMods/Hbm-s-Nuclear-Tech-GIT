package com.hbm.world.generator;

import java.util.List;
import java.util.Random;

import com.hbm.inventory.RecipesCommon.MetaBlock;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class DungeonToolbox {
	
	public static void generateBox(World world, int x, int y, int z, int sx, int sy, int sz, List<MetaBlock> blocks) {
		
		if(blocks.isEmpty())
			return;
		
		for(int i = x; i < x + sx; i++) {
			for(int j = y; j < y + sy; j++) {
				for(int k = z; k < z + sz; k++) {
					MetaBlock b = getRandom(blocks, world.rand);
					world.setBlock(i, j, k, b.block, b.meta, 2);
				}
			}
		}
	}
	
	public static void generateBox(World world, int x, int y, int z, int sx, int sy, int sz, Block block) {
		generateBox(world, x, y, z, sx, sy, sz, new MetaBlock(block));
	}

	//i know it's copy paste, but it's a better strat than using a wrapper and generating single-entry lists for no good reason
	public static void generateBox(World world, int x, int y, int z, int sx, int sy, int sz, MetaBlock block) {
		
		for(int i = x; i < x + sx; i++) {
			
			for(int j = y; j < y + sy; j++) {
				
				for(int k = z; k < z + sz; k++) {
					
					world.setBlock(i, j, k, block.block, block.meta, 2);
				}
			}
		}
	}
	
	//now with vectors to provide handy rotations
	public static void generateBox(World world, int x, int y, int z, Vec3 size, List<MetaBlock> blocks) {
		
		generateBox(world, x, y, z, (int)size.xCoord, (int)size.yCoord, (int)size.zCoord, blocks);
	}
	
	public static <T> T getRandom(List<T> list, Random rand) {
		
		if(list.isEmpty())
			return null;

		return list.get(rand.nextInt(list.size()));
	}
	
	public static void generateOre(World world, Random rand, int chunkX, int chunkZ, int veinCount, int amount, int minHeight, int variance, Block ore) {
		generateOre(world, rand, chunkX, chunkZ, veinCount, amount, minHeight, variance, ore, 0, Blocks.stone);
	}
	
	public static void generateOre(World world, Random rand, int chunkX, int chunkZ, int veinCount, int amount, int minHeight, int variance, Block ore, int meta) {
		generateOre(world, rand, chunkX, chunkZ, veinCount, amount, minHeight, variance, ore, meta, Blocks.stone);
	}
	
	public static void generateOre(World world, Random rand, int chunkX, int chunkZ, int veinCount, int amount, int minHeight, int variance, Block ore, Block target) {
		generateOre(world, rand, chunkX, chunkZ, veinCount, amount, minHeight, variance, ore, 0, target);
	}
	
	public static void generateOre(World world, Random rand, int chunkX, int chunkZ, int veinCount, int amount, int minHeight, int variance, Block ore, int meta, Block target) {
		
		for(int i = 0; i < veinCount; i++) {
			
			int x = chunkX + rand.nextInt(16);
			int y = minHeight + (variance > 0 ? rand.nextInt(variance) : 0);
			int z = chunkZ + rand.nextInt(16);
	
			(new WorldGenMinable(ore, meta, amount, target)).generate(world, rand, x, y, z);
		}
	}

	private static WorldGenFlowers genFlowers = new WorldGenFlowers(null);
	public static void generateFlowers(World world, Random rand, int chunkX, int chunkZ, Block flower, int meta) {
		int x = chunkX + rand.nextInt(16);
		int z = chunkZ + rand.nextInt(16);
		int y = world.getHeightValue(x, z);
		genFlowers.func_150550_a(flower, meta);
		genFlowers.generate(world, rand, x, y, z);
	}
}
