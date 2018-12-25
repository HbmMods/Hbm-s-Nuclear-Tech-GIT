//Schematic to java Structure by jajo_11 | inspired by "MITHION'S .SCHEMATIC TO JAVA CONVERTINGTOOL"

package com.hbm.world;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class NuclearReactor extends WorldGenerator
{
	public static String[][] array2 = new String[][] {
		{
			"     ",
			" BBB ",
			" B B ",
			" BBB ",
			"     "
		},
		{
			" BBB ",
			"BRCRB",
			"BCICB",
			"BRCRB",
			" BBB "
		},
		{
			" BAB ",
			"BRCRB",
			"AC#CA",
			"BRCRB",
			" BAB "
		},
		{
			" BBB ",
			"BRCRB",
			"BCICB",
			"BRCRB",
			" BBB "
		},
		{
			"     ",
			" BBB ",
			" B B ",
			" BBB ",
			"     "
		}
	};
	
	Block Block1 = ModBlocks.brick_concrete;
	Block Block2 = ModBlocks.reactor_element;
	Block Block3 = ModBlocks.reactor_control;
	Block Block4 = ModBlocks.reactor_conductor;
	Block Block5 = ModBlocks.reactor_hatch;
	Block Block6 = ModBlocks.reactor_computer;
	
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
		x -= 2;
		z -= 2;
		
		world.setBlock(x + 0, y + 0, z + 0, Block1, 0, 3);
		world.setBlock(x + 1, y + 0, z + 0, Block1, 0, 3);
		world.setBlock(x + 2, y + 0, z + 0, Block1, 0, 3);
		world.setBlock(x + 3, y + 0, z + 0, Block1, 0, 3);
		world.setBlock(x + 4, y + 0, z + 0, Block1, 0, 3);
		world.setBlock(x + 0, y + 0, z + 1, Block1, 0, 3);
		world.setBlock(x + 1, y + 0, z + 1, Block1, 0, 3);
		world.setBlock(x + 2, y + 0, z + 1, Block1, 0, 3);
		world.setBlock(x + 3, y + 0, z + 1, Block1, 0, 3);
		world.setBlock(x + 4, y + 0, z + 1, Block1, 0, 3);
		world.setBlock(x + 0, y + 0, z + 2, Block1, 0, 3);
		world.setBlock(x + 1, y + 0, z + 2, Block1, 0, 3);
		world.setBlock(x + 2, y + 0, z + 2, ModBlocks.fluid_duct, 0, 3);
		world.setBlock(x + 3, y + 0, z + 2, Block1, 0, 3);
		world.setBlock(x + 4, y + 0, z + 2, Block1, 0, 3);
		world.setBlock(x + 0, y + 0, z + 3, Block1, 0, 3);
		world.setBlock(x + 1, y + 0, z + 3, Block1, 0, 3);
		world.setBlock(x + 2, y + 0, z + 3, Block1, 0, 3);
		world.setBlock(x + 3, y + 0, z + 3, Block1, 0, 3);
		world.setBlock(x + 4, y + 0, z + 3, Block1, 0, 3);
		world.setBlock(x + 0, y + 0, z + 4, Block1, 0, 3);
		world.setBlock(x + 1, y + 0, z + 4, Block1, 0, 3);
		world.setBlock(x + 2, y + 0, z + 4, Block1, 0, 3);
		world.setBlock(x + 3, y + 0, z + 4, Block1, 0, 3);
		world.setBlock(x + 4, y + 0, z + 4, Block1, 0, 3);
		world.setBlock(x + 0, y + 1, z + 0, Block1, 0, 3);
		world.setBlock(x + 1, y + 1, z + 0, Block1, 0, 3);
		world.setBlock(x + 2, y + 1, z + 0, Block1, 0, 3);
		world.setBlock(x + 3, y + 1, z + 0, Block1, 0, 3);
		world.setBlock(x + 4, y + 1, z + 0, Block1, 0, 3);
		world.setBlock(x + 0, y + 1, z + 1, Block1, 0, 3);
		world.setBlock(x + 1, y + 1, z + 1, Block2, 0, 3);
		world.setBlock(x + 2, y + 1, z + 1, Block3, 0, 3);
		world.setBlock(x + 3, y + 1, z + 1, Block2, 0, 3);
		world.setBlock(x + 4, y + 1, z + 1, Block1, 0, 3);
		world.setBlock(x + 0, y + 1, z + 2, Block1, 0, 3);
		world.setBlock(x + 1, y + 1, z + 2, Block3, 0, 3);
		world.setBlock(x + 2, y + 1, z + 2, Block4, 0, 3);
		world.setBlock(x + 3, y + 1, z + 2, Block3, 0, 3);
		world.setBlock(x + 4, y + 1, z + 2, Block1, 0, 3);
		world.setBlock(x + 0, y + 1, z + 3, Block1, 0, 3);
		world.setBlock(x + 1, y + 1, z + 3, Block2, 0, 3);
		world.setBlock(x + 2, y + 1, z + 3, Block3, 0, 3);
		world.setBlock(x + 3, y + 1, z + 3, Block2, 0, 3);
		world.setBlock(x + 4, y + 1, z + 3, Block1, 0, 3);
		world.setBlock(x + 0, y + 1, z + 4, Block1, 0, 3);
		world.setBlock(x + 1, y + 1, z + 4, Block1, 0, 3);
		world.setBlock(x + 2, y + 1, z + 4, Block1, 0, 3);
		world.setBlock(x + 3, y + 1, z + 4, Block1, 0, 3);
		world.setBlock(x + 4, y + 1, z + 4, Block1, 0, 3);
		world.setBlock(x + 0, y + 2, z + 0, Block1, 0, 3);
		world.setBlock(x + 1, y + 2, z + 0, Block1, 0, 3);
		world.setBlock(x + 2, y + 2, z + 0, Block5, 2, 3);
		world.setBlock(x + 3, y + 2, z + 0, Block1, 0, 3);
		world.setBlock(x + 4, y + 2, z + 0, Block1, 0, 3);
		world.setBlock(x + 0, y + 2, z + 1, Block1, 0, 3);
		world.setBlock(x + 1, y + 2, z + 1, Block2, 0, 3);
		world.setBlock(x + 2, y + 2, z + 1, Block3, 0, 3);
		world.setBlock(x + 3, y + 2, z + 1, Block2, 0, 3);
		world.setBlock(x + 4, y + 2, z + 1, Block1, 0, 3);
		world.setBlock(x + 0, y + 2, z + 2, Block5, 4, 3);
		world.setBlock(x + 1, y + 2, z + 2, Block3, 0, 3);
		world.setBlock(x + 2, y + 2, z + 2, Block6, 0, 3);
		world.setBlock(x + 3, y + 2, z + 2, Block3, 0, 3);
		world.setBlock(x + 4, y + 2, z + 2, Block5, 5, 3);
		world.setBlock(x + 0, y + 2, z + 3, Block1, 0, 3);
		world.setBlock(x + 1, y + 2, z + 3, Block2, 0, 3);
		world.setBlock(x + 2, y + 2, z + 3, Block3, 0, 3);
		world.setBlock(x + 3, y + 2, z + 3, Block2, 0, 3);
		world.setBlock(x + 4, y + 2, z + 3, Block1, 0, 3);
		world.setBlock(x + 0, y + 2, z + 4, Block1, 0, 3);
		world.setBlock(x + 1, y + 2, z + 4, Block1, 0, 3);
		world.setBlock(x + 2, y + 2, z + 4, Block5, 3, 3);
		world.setBlock(x + 3, y + 2, z + 4, Block1, 0, 3);
		world.setBlock(x + 4, y + 2, z + 4, Block1, 0, 3);
		world.setBlock(x + 0, y + 3, z + 0, Block1, 0, 3);
		world.setBlock(x + 1, y + 3, z + 0, Block1, 0, 3);
		world.setBlock(x + 2, y + 3, z + 0, Block1, 0, 3);
		world.setBlock(x + 3, y + 3, z + 0, Block1, 0, 3);
		world.setBlock(x + 4, y + 3, z + 0, Block1, 0, 3);
		world.setBlock(x + 0, y + 3, z + 1, Block1, 0, 3);
		world.setBlock(x + 1, y + 3, z + 1, Block2, 0, 3);
		world.setBlock(x + 2, y + 3, z + 1, Block3, 0, 3);
		world.setBlock(x + 3, y + 3, z + 1, Block2, 0, 3);
		world.setBlock(x + 4, y + 3, z + 1, Block1, 0, 3);
		world.setBlock(x + 0, y + 3, z + 2, Block1, 0, 3);
		world.setBlock(x + 1, y + 3, z + 2, Block3, 0, 3);
		world.setBlock(x + 2, y + 3, z + 2, Block4, 0, 3);
		world.setBlock(x + 3, y + 3, z + 2, Block3, 0, 3);
		world.setBlock(x + 4, y + 3, z + 2, Block1, 0, 3);
		world.setBlock(x + 0, y + 3, z + 3, Block1, 0, 3);
		world.setBlock(x + 1, y + 3, z + 3, Block2, 0, 3);
		world.setBlock(x + 2, y + 3, z + 3, Block3, 0, 3);
		world.setBlock(x + 3, y + 3, z + 3, Block2, 0, 3);
		world.setBlock(x + 4, y + 3, z + 3, Block1, 0, 3);
		world.setBlock(x + 0, y + 3, z + 4, Block1, 0, 3);
		world.setBlock(x + 1, y + 3, z + 4, Block1, 0, 3);
		world.setBlock(x + 2, y + 3, z + 4, Block1, 0, 3);
		world.setBlock(x + 3, y + 3, z + 4, Block1, 0, 3);
		world.setBlock(x + 4, y + 3, z + 4, Block1, 0, 3);
		world.setBlock(x + 0, y + 4, z + 0, Block1, 0, 3);
		world.setBlock(x + 1, y + 4, z + 0, Block1, 0, 3);
		world.setBlock(x + 2, y + 4, z + 0, Block1, 0, 3);
		world.setBlock(x + 3, y + 4, z + 0, Block1, 0, 3);
		world.setBlock(x + 4, y + 4, z + 0, Block1, 0, 3);
		world.setBlock(x + 0, y + 4, z + 1, Block1, 0, 3);
		world.setBlock(x + 1, y + 4, z + 1, Block1, 0, 3);
		world.setBlock(x + 2, y + 4, z + 1, Block1, 0, 3);
		world.setBlock(x + 3, y + 4, z + 1, Block1, 0, 3);
		world.setBlock(x + 4, y + 4, z + 1, Block1, 0, 3);
		world.setBlock(x + 0, y + 4, z + 2, Block1, 0, 3);
		world.setBlock(x + 1, y + 4, z + 2, Block1, 0, 3);
		world.setBlock(x + 2, y + 4, z + 2, ModBlocks.fluid_duct, 0, 3);
		world.setBlock(x + 3, y + 4, z + 2, Block1, 0, 3);
		world.setBlock(x + 4, y + 4, z + 2, Block1, 0, 3);
		world.setBlock(x + 0, y + 4, z + 3, Block1, 0, 3);
		world.setBlock(x + 1, y + 4, z + 3, Block1, 0, 3);
		world.setBlock(x + 2, y + 4, z + 3, Block1, 0, 3);
		world.setBlock(x + 3, y + 4, z + 3, Block1, 0, 3);
		world.setBlock(x + 4, y + 4, z + 3, Block1, 0, 3);
		world.setBlock(x + 0, y + 4, z + 4, Block1, 0, 3);
		world.setBlock(x + 1, y + 4, z + 4, Block1, 0, 3);
		world.setBlock(x + 2, y + 4, z + 4, Block1, 0, 3);
		world.setBlock(x + 3, y + 4, z + 4, Block1, 0, 3);
		world.setBlock(x + 4, y + 4, z + 4, Block1, 0, 3);
		return true;

	}

}