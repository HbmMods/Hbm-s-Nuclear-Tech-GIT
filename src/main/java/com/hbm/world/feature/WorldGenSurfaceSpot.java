package com.hbm.world.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenSurfaceSpot extends WorldGenerator {
	
	private Block block;
	private int radius;
	private float chance;

	public WorldGenSurfaceSpot(Block block, int radius, float chance) {
		this.block = block;
		this.radius = radius;
		this.chance = chance;
	}

	public boolean generate(World world, Random rand, int x, int y, int z) {
		int r = rand.nextInt(this.radius - 2) + 2;
		byte depth = 2;

		for(int iX = x - r; iX <= x + r; ++iX) {
			for(int iZ = z - r; iZ <= z + r; ++iZ) {
				int k1 = iX - x;
				int l1 = iZ - z;

				if(k1 * k1 + l1 * l1 <= r * r) {
					for(int iY = y - depth; iY <= y + depth; ++iY) {
						Block block = world.getBlock(iX, iY, iZ);

						if(block == Blocks.dirt || block == Blocks.grass) {
							if(rand.nextFloat() < this.chance) world.setBlock(iX, iY, iZ, this.block, 0, 2);
						}
					}
				}
			}
		}

		return true;
	}
}
