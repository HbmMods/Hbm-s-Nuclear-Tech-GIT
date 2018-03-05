//Schematic to java Structure by jajo_11 | inspired by "MITHION'S .SCHEMATIC TO JAVA CONVERTINGTOOL"

package com.hbm.world;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class FactoryAdvanced extends WorldGenerator
{
	Block Block1 = ModBlocks.factory_advanced_hull;
	Block Block2 = ModBlocks.factory_advanced_conductor;
	Block Block3 = ModBlocks.factory_advanced_furnace;
	Block Block4 = ModBlocks.factory_advanced_core;
	
	@Override
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		int i = rand.nextInt(1);

		if(i == 0)
		{
		    generate_r0(world, rand, x, y, z);
		}

       return true;

	}

	public boolean generate_r0(World world, Random rand, int x, int y, int z)
	{
		x -= 1;
		z -= 1;
		
		world.setBlock(x + 0, y + 0, z + 0, Block1, 0, 3);
		world.setBlock(x + 1, y + 0, z + 0, Block1, 0, 3);
		world.setBlock(x + 2, y + 0, z + 0, Block1, 0, 3);
		world.setBlock(x + 0, y + 0, z + 1, Block1, 0, 3);
		world.setBlock(x + 1, y + 0, z + 1, Block2, 0, 3);
		world.setBlock(x + 2, y + 0, z + 1, Block1, 0, 3);
		world.setBlock(x + 0, y + 0, z + 2, Block1, 0, 3);
		world.setBlock(x + 1, y + 0, z + 2, Block1, 0, 3);
		world.setBlock(x + 2, y + 0, z + 2, Block1, 0, 3);
		world.setBlock(x + 0, y + 1, z + 0, Block1, 0, 3);
		world.setBlock(x + 1, y + 1, z + 0, Block3, 2, 3);
		world.setBlock(x + 2, y + 1, z + 0, Block1, 0, 3);
		world.setBlock(x + 0, y + 1, z + 1, Block3, 4, 3);
		world.setBlock(x + 1, y + 1, z + 1, Block4, 0, 3);
		world.setBlock(x + 2, y + 1, z + 1, Block3, 5, 3);
		world.setBlock(x + 0, y + 1, z + 2, Block1, 0, 3);
		world.setBlock(x + 1, y + 1, z + 2, Block3, 3, 3);
		world.setBlock(x + 2, y + 1, z + 2, Block1, 0, 3);
		world.setBlock(x + 0, y + 2, z + 0, Block1, 0, 3);
		world.setBlock(x + 1, y + 2, z + 0, Block1, 0, 3);
		world.setBlock(x + 2, y + 2, z + 0, Block1, 0, 3);
		world.setBlock(x + 0, y + 2, z + 1, Block1, 0, 3);
		world.setBlock(x + 1, y + 2, z + 1, Block2, 0, 3);
		world.setBlock(x + 2, y + 2, z + 1, Block1, 0, 3);
		world.setBlock(x + 0, y + 2, z + 2, Block1, 0, 3);
		world.setBlock(x + 1, y + 2, z + 2, Block1, 0, 3);
		world.setBlock(x + 2, y + 2, z + 2, Block1, 0, 3);
		return true;

	}

}