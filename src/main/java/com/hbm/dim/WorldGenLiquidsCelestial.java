package com.hbm.dim;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenLiquidsCelestial extends WorldGenerator {

	// Identical to WorldGenLiquids except you can specify the stone block to replace

	private final Block liquidBlock;
	private final Block targetBlock;

	public WorldGenLiquidsCelestial(Block liquidBlock, Block targetBlock) {
		this.liquidBlock = liquidBlock;
		this.targetBlock = targetBlock;
	}

	public boolean generate(World world, Random rand, int x, int y, int z) {
		if(world.getBlock(x, y + 1, z) != targetBlock) {
			return false;
		} else if(world.getBlock(x, y - 1, z) != targetBlock) {
			return false;
		} else if(world.getBlock(x, y, z).getMaterial() != Material.air && world.getBlock(x, y, z) != targetBlock) {
			return false;
		} else {
			int l = 0;

			if(world.getBlock(x - 1, y, z) == targetBlock) {
				++l;
			}

			if(world.getBlock(x + 1, y, z) == targetBlock) {
				++l;
			}

			if(world.getBlock(x, y, z - 1) == targetBlock) {
				++l;
			}

			if(world.getBlock(x, y, z + 1) == targetBlock) {
				++l;
			}

			int i1 = 0;

			if(world.isAirBlock(x - 1, y, z)) {
				++i1;
			}

			if(world.isAirBlock(x + 1, y, z)) {
				++i1;
			}

			if(world.isAirBlock(x, y, z - 1)) {
				++i1;
			}

			if(world.isAirBlock(x, y, z + 1)) {
				++i1;
			}

			if(l == 3 && i1 == 1) {
				world.setBlock(x, y, z, this.liquidBlock, 0, 2);
				world.scheduledUpdatesAreImmediate = true;
				this.liquidBlock.updateTick(world, x, y, z, rand);
				world.scheduledUpdatesAreImmediate = false;
			}

			return true;
		}
	}
}
