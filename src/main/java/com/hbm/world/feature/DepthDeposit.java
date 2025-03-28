package com.hbm.world.feature;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class DepthDeposit {

	public static void generateConditionOverworld(World world, int x, int yMin, int yDev, int z, int size, double fill, Block block, Random rand, int chance) {

		if(rand.nextInt(chance) == 0)
			generate(world, x + rand.nextInt(16) + 8, yMin + rand.nextInt(yDev), z + rand.nextInt(16) + 8, size, fill, block, rand, Blocks.stone, ModBlocks.stone_depth);
	}

	public static void generateConditionNether(World world, int x, int yMin, int yDev, int z, int size, double fill, Block block, Random rand, int chance) {

		if(rand.nextInt(chance) == 0)
			generate(world, x + rand.nextInt(16) + 8, yMin + rand.nextInt(yDev), z + rand.nextInt(16) + 8, size, fill, block, rand, Blocks.netherrack, ModBlocks.stone_depth_nether);
	}

	public static void generateCondition(World world, int x, int yMin, int yDev, int z, int size, double fill, Block block, Random rand, int chance, Block genTarget, Block filler) {

		if(rand.nextInt(chance) == 0)
			generate(world, x + rand.nextInt(16) + 8, yMin + rand.nextInt(yDev), z + rand.nextInt(16) + 8, size, fill, block, rand, genTarget, filler);
	}

	public static void generate(World world, int x, int y, int z, int size, double fill, Block block, Random rand, Block genTarget, Block filler) {

		for(int i = x - size; i <= x + size; i++) {
			for(int j = y - size; j <= y + size; j++) {
				for(int k = z - size; k <= z + size; k++) {

					if(j < 1 || j > 126)
						continue;

					double len = Vec3.createVectorHelper(x - i, y - j, z - k).lengthVector();
					Block target = world.getBlock(i, j, k);

					if(target.isReplaceableOreGen(world, i, j, k, genTarget) || target.isReplaceableOreGen(world, i, j, k, Blocks.bedrock)) { //yes you've heard right, bedrock

						if(len + rand.nextInt(2) < size * fill) {
							world.setBlock(i, j, k, block);

						} else  if(len + rand.nextInt(2) <= size) {
							world.setBlock(i, j, k, filler);
						}
					}
				}
			}
		}
	}
}
