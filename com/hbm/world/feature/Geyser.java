//Schematic to java Structure by jajo_11 | inspired by "MITHION'S .SCHEMATIC TO JAVA CONVERTINGTOOL"

package com.hbm.world.feature;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class Geyser extends WorldGenerator {

	public boolean generate(World world, Random rand, int x, int y, int z) {
		int i = rand.nextInt(1);

		if (i == 0) {
			generate_r0(world, rand, x, y, z);
		}

		return true;

	}

	public boolean generate_r0(World world, Random rand, int x, int y, int z) {

		x -= 2;
		y -= 11;
		z -= 2;
		world.setBlock(x + 1, y + 5, z + 0, Blocks.stone, 0, 3);
		world.setBlock(x + 2, y + 5, z + 0, Blocks.stone, 0, 3);
		world.setBlock(x + 3, y + 5, z + 0, Blocks.stone, 0, 3);
		world.setBlock(x + 0, y + 5, z + 1, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 5, z + 1, Blocks.stone, 0, 3);
		world.setBlock(x + 2, y + 5, z + 1, Blocks.stone, 0, 3);
		world.setBlock(x + 3, y + 5, z + 1, Blocks.stone, 0, 3);
		world.setBlock(x + 4, y + 5, z + 1, Blocks.stone, 0, 3);
		world.setBlock(x + 0, y + 5, z + 2, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 5, z + 2, Blocks.stone, 0, 3);
		world.setBlock(x + 2, y + 5, z + 2, Blocks.stone, 0, 3);
		world.setBlock(x + 3, y + 5, z + 2, Blocks.stone, 0, 3);
		world.setBlock(x + 4, y + 5, z + 2, Blocks.stone, 0, 3);
		world.setBlock(x + 0, y + 5, z + 3, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 5, z + 3, Blocks.stone, 0, 3);
		world.setBlock(x + 2, y + 5, z + 3, Blocks.stone, 0, 3);
		world.setBlock(x + 3, y + 5, z + 3, Blocks.stone, 0, 3);
		world.setBlock(x + 4, y + 5, z + 3, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 5, z + 4, Blocks.stone, 0, 3);
		world.setBlock(x + 2, y + 5, z + 4, Blocks.stone, 0, 3);
		world.setBlock(x + 3, y + 5, z + 4, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 6, z + 0, Blocks.stone, 0, 3);
		world.setBlock(x + 2, y + 6, z + 0, Blocks.stone, 0, 3);
		world.setBlock(x + 3, y + 6, z + 0, Blocks.stone, 0, 3);
		world.setBlock(x + 0, y + 6, z + 1, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 6, z + 1, Blocks.stone, 0, 3);
		world.setBlock(x + 2, y + 6, z + 1, Blocks.stone, 0, 3);
		world.setBlock(x + 3, y + 6, z + 1, Blocks.stone, 0, 3);
		world.setBlock(x + 4, y + 6, z + 1, Blocks.stone, 0, 3);
		world.setBlock(x + 0, y + 6, z + 2, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 6, z + 2, Blocks.stone, 0, 3);
		world.setBlock(x + 2, y + 6, z + 2, Blocks.stone, 0, 3);
		world.setBlock(x + 3, y + 6, z + 2, Blocks.stone, 0, 3);
		world.setBlock(x + 4, y + 6, z + 2, Blocks.stone, 0, 3);
		world.setBlock(x + 0, y + 6, z + 3, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 6, z + 3, Blocks.stone, 0, 3);
		world.setBlock(x + 2, y + 6, z + 3, Blocks.stone, 0, 3);
		world.setBlock(x + 3, y + 6, z + 3, Blocks.stone, 0, 3);
		world.setBlock(x + 4, y + 6, z + 3, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 6, z + 4, Blocks.stone, 0, 3);
		world.setBlock(x + 2, y + 6, z + 4, Blocks.stone, 0, 3);
		world.setBlock(x + 3, y + 6, z + 4, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 7, z + 0, Blocks.stone, 0, 3);
		world.setBlock(x + 2, y + 7, z + 0, Blocks.stone, 0, 3);
		world.setBlock(x + 3, y + 7, z + 0, Blocks.stone, 0, 3);
		world.setBlock(x + 0, y + 7, z + 1, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 7, z + 1, Blocks.water, 0, 3);
		world.setBlock(x + 2, y + 7, z + 1, ModBlocks.block_yellowcake, 0, 3);
		world.setBlock(x + 3, y + 7, z + 1, Blocks.water, 0, 3);
		world.setBlock(x + 4, y + 7, z + 1, Blocks.stone, 0, 3);
		world.setBlock(x + 0, y + 7, z + 2, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 7, z + 2, ModBlocks.block_yellowcake, 0, 3);
		world.setBlock(x + 2, y + 7, z + 2, Blocks.water, 0, 3);
		world.setBlock(x + 3, y + 7, z + 2, Blocks.water, 0, 3);
		world.setBlock(x + 4, y + 7, z + 2, Blocks.stone, 0, 3);
		world.setBlock(x + 0, y + 7, z + 3, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 7, z + 3, Blocks.water, 0, 3);
		world.setBlock(x + 2, y + 7, z + 3, ModBlocks.block_yellowcake, 0, 3);
		world.setBlock(x + 3, y + 7, z + 3, ModBlocks.block_yellowcake, 0, 3);
		world.setBlock(x + 4, y + 7, z + 3, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 7, z + 4, Blocks.stone, 0, 3);
		world.setBlock(x + 2, y + 7, z + 4, Blocks.stone, 0, 3);
		world.setBlock(x + 3, y + 7, z + 4, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 8, z + 0, Blocks.stone, 0, 3);
		world.setBlock(x + 2, y + 8, z + 0, Blocks.stone, 0, 3);
		world.setBlock(x + 3, y + 8, z + 0, Blocks.stone, 0, 3);
		world.setBlock(x + 0, y + 8, z + 1, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 8, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 8, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 8, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 8, z + 1, Blocks.stone, 0, 3);
		world.setBlock(x + 0, y + 8, z + 2, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 8, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 8, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 8, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 8, z + 2, Blocks.stone, 0, 3);
		world.setBlock(x + 0, y + 8, z + 3, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 8, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 8, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 8, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 8, z + 3, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 8, z + 4, Blocks.stone, 0, 3);
		world.setBlock(x + 2, y + 8, z + 4, Blocks.stone, 0, 3);
		world.setBlock(x + 3, y + 8, z + 4, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 9, z + 0, Blocks.stone, 0, 3);
		world.setBlock(x + 2, y + 9, z + 0, Blocks.stone, 0, 3);
		world.setBlock(x + 3, y + 9, z + 0, Blocks.stone, 0, 3);
		world.setBlock(x + 0, y + 9, z + 1, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 9, z + 1, Blocks.stone, 0, 3);
		world.setBlock(x + 3, y + 9, z + 1, Blocks.stone, 0, 3);
		world.setBlock(x + 4, y + 9, z + 1, Blocks.stone, 0, 3);
		world.setBlock(x + 0, y + 9, z + 2, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 9, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 9, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 9, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 9, z + 2, Blocks.stone, 0, 3);
		world.setBlock(x + 0, y + 9, z + 3, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 9, z + 3, Blocks.stone, 0, 3);
		world.setBlock(x + 3, y + 9, z + 3, Blocks.stone, 0, 3);
		world.setBlock(x + 4, y + 9, z + 3, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 9, z + 4, Blocks.stone, 0, 3);
		world.setBlock(x + 2, y + 9, z + 4, Blocks.stone, 0, 3);
		world.setBlock(x + 3, y + 9, z + 4, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 10, z + 0, Blocks.grass, 0, 3);
		world.setBlock(x + 2, y + 10, z + 0, Blocks.grass, 0, 3);
		world.setBlock(x + 3, y + 10, z + 0, Blocks.grass, 0, 3);
		world.setBlock(x + 0, y + 10, z + 1, Blocks.grass, 0, 3);
		world.setBlock(x + 1, y + 10, z + 1, Blocks.gravel, 0, 3);
		world.setBlock(x + 2, y + 10, z + 1, Blocks.stone, 0, 3);
		world.setBlock(x + 3, y + 10, z + 1, Blocks.grass, 0, 3);
		world.setBlock(x + 4, y + 10, z + 1, Blocks.stone, 0, 3);
		world.setBlock(x + 0, y + 10, z + 2, Blocks.stone, 0, 3);
		world.setBlock(x + 1, y + 10, z + 2, Blocks.grass, 0, 3);
		world.setBlock(x + 2, y + 10, z + 2, ModBlocks.geysir_chlorine, 0, 3);
		world.setBlock(x + 3, y + 10, z + 2, Blocks.grass, 0, 3);
		world.setBlock(x + 4, y + 10, z + 2, Blocks.gravel, 0, 3);
		world.setBlock(x + 0, y + 10, z + 3, Blocks.grass, 0, 3);
		world.setBlock(x + 1, y + 10, z + 3, Blocks.stone, 0, 3);
		world.setBlock(x + 2, y + 10, z + 3, Blocks.grass, 0, 3);
		world.setBlock(x + 3, y + 10, z + 3, Blocks.gravel, 0, 3);
		world.setBlock(x + 4, y + 10, z + 3, Blocks.grass, 0, 3);
		world.setBlock(x + 1, y + 10, z + 4, Blocks.grass, 0, 3);
		world.setBlock(x + 2, y + 10, z + 4, Blocks.grass, 0, 3);
		world.setBlock(x + 3, y + 10, z + 4, Blocks.grass, 0, 3);
		return true;

	}

}