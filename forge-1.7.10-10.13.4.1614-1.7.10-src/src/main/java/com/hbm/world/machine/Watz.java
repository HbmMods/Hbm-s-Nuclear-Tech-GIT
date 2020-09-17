//Schematic to java Structure by jajo_11 | inspired by "MITHION'S .SCHEMATIC TO JAVA CONVERTINGTOOL"

package com.hbm.world.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class Watz extends WorldGenerator
{ 
	public static String[][] array = new String[][] {
		{
			"SSSSSSS",
			"SSSSSSS",
			"SSSSSSS",
			"SSSISSS",
			"SSSSSSS",
			"SSSSSSS",
			"SSSSSSS"
		},
		{
			"  CCC  ",
			" CWRWC ",
			"CWRKRWC",
			"CRKIKRC",
			"CWRKRWC",
			" CWRWC ",
			"  CCC  "
		},
		{
			"  CCC  ",
			" CWRWC ",
			"CWRKRWC",
			"CRKIKRC",
			"CWRKRWC",
			" CWRWC ",
			"  CCC  "
		},
		{
			"  CCC  ",
			" CWRWC ",
			"CWRKRWC",
			"CRKIKRC",
			"CWRKRWC",
			" CWRWC ",
			"  CCC  "
		},
		{
			"  CCC  ",
			" CWRWC ",
			"CWRKRWC",
			"CRKIKRC",
			"CWRKRWC",
			" CWRWC ",
			"  CCC  "
		},
		{
			"  CCC  ",
			" CWRWC ",
			"CWRKRWC",
			"CRKIKRC",
			"CWRKRWC",
			" CWRWC ",
			"  CCC  "
		},
		{
			"  CAC  ",
			" CWRWC ",
			"CWRKRWC",
			"ARK#KRA",
			"CWRKRWC",
			" CWRWC ",
			"  CAC  "
		},
		{
			"  CCC  ",
			" CWRWC ",
			"CWRKRWC",
			"CRKIKRC",
			"CWRKRWC",
			" CWRWC ",
			"  CCC  "
		},
		{
			"  CCC  ",
			" CWRWC ",
			"CWRKRWC",
			"CRKIKRC",
			"CWRKRWC",
			" CWRWC ",
			"  CCC  "
		},
		{
			"  CCC  ",
			" CWRWC ",
			"CWRKRWC",
			"CRKIKRC",
			"CWRKRWC",
			" CWRWC ",
			"  CCC  "
		},
		{
			"  CCC  ",
			" CWRWC ",
			"CWRKRWC",
			"CRKIKRC",
			"CWRKRWC",
			" CWRWC ",
			"  CCC  "
		},
		{
			"  CCC  ",
			" CWRWC ",
			"CWRKRWC",
			"CRKIKRC",
			"CWRKRWC",
			" CWRWC ",
			"  CCC  "
		},
		{
			"SSSSSSS",
			"SSSSSSS",
			"SSSSSSS",
			"SSSISSS",
			"SSSSSSS",
			"SSSSSSS",
			"SSSSSSS"
		}
	};
	
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
		x -= 3;
		z -= 3;
		
		world.setBlock(x + 0, y + 0, z + 0, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 1, y + 0, z + 0, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 2, y + 0, z + 0, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 3, y + 0, z + 0, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 4, y + 0, z + 0, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 5, y + 0, z + 0, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 6, y + 0, z + 0, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 0, y + 0, z + 1, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 1, y + 0, z + 1, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 2, y + 0, z + 1, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 3, y + 0, z + 1, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 4, y + 0, z + 1, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 5, y + 0, z + 1, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 6, y + 0, z + 1, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 0, y + 0, z + 2, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 1, y + 0, z + 2, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 2, y + 0, z + 2, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 3, y + 0, z + 2, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 4, y + 0, z + 2, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 5, y + 0, z + 2, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 6, y + 0, z + 2, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 0, y + 0, z + 3, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 1, y + 0, z + 3, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 2, y + 0, z + 3, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 3, y + 0, z + 3, ModBlocks.watz_conductor, 0, 3);
		world.setBlock(x + 4, y + 0, z + 3, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 5, y + 0, z + 3, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 6, y + 0, z + 3, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 0, y + 0, z + 4, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 1, y + 0, z + 4, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 2, y + 0, z + 4, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 3, y + 0, z + 4, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 4, y + 0, z + 4, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 5, y + 0, z + 4, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 6, y + 0, z + 4, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 0, y + 0, z + 5, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 1, y + 0, z + 5, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 2, y + 0, z + 5, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 3, y + 0, z + 5, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 4, y + 0, z + 5, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 5, y + 0, z + 5, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 6, y + 0, z + 5, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 0, y + 0, z + 6, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 1, y + 0, z + 6, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 2, y + 0, z + 6, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 3, y + 0, z + 6, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 4, y + 0, z + 6, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 5, y + 0, z + 6, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 6, y + 0, z + 6, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 0, y + 1, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 1, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 1, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 3, y + 1, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 4, y + 1, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 5, y + 1, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 1, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 1, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 1, z + 1, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 2, y + 1, z + 1, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 3, y + 1, z + 1, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 4, y + 1, z + 1, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 5, y + 1, z + 1, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 6, y + 1, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 1, z + 2, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 1, z + 2, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 2, y + 1, z + 2, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 3, y + 1, z + 2, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 4, y + 1, z + 2, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 5, y + 1, z + 2, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 6, y + 1, z + 2, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 1, z + 3, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 1, z + 3, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 2, y + 1, z + 3, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 3, y + 1, z + 3, ModBlocks.watz_conductor, 0, 3);
		world.setBlock(x + 4, y + 1, z + 3, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 5, y + 1, z + 3, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 6, y + 1, z + 3, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 1, z + 4, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 1, z + 4, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 2, y + 1, z + 4, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 3, y + 1, z + 4, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 4, y + 1, z + 4, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 5, y + 1, z + 4, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 6, y + 1, z + 4, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 1, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 1, z + 5, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 2, y + 1, z + 5, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 3, y + 1, z + 5, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 4, y + 1, z + 5, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 5, y + 1, z + 5, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 6, y + 1, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 1, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 1, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 1, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 3, y + 1, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 4, y + 1, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 5, y + 1, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 1, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 2, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 2, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 2, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 3, y + 2, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 4, y + 2, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 5, y + 2, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 2, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 2, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 2, z + 1, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 2, y + 2, z + 1, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 3, y + 2, z + 1, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 4, y + 2, z + 1, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 5, y + 2, z + 1, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 6, y + 2, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 2, z + 2, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 2, z + 2, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 2, y + 2, z + 2, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 3, y + 2, z + 2, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 4, y + 2, z + 2, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 5, y + 2, z + 2, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 6, y + 2, z + 2, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 2, z + 3, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 2, z + 3, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 2, y + 2, z + 3, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 3, y + 2, z + 3, ModBlocks.watz_conductor, 0, 3);
		world.setBlock(x + 4, y + 2, z + 3, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 5, y + 2, z + 3, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 6, y + 2, z + 3, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 2, z + 4, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 2, z + 4, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 2, y + 2, z + 4, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 3, y + 2, z + 4, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 4, y + 2, z + 4, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 5, y + 2, z + 4, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 6, y + 2, z + 4, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 2, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 2, z + 5, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 2, y + 2, z + 5, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 3, y + 2, z + 5, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 4, y + 2, z + 5, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 5, y + 2, z + 5, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 6, y + 2, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 2, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 2, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 2, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 3, y + 2, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 4, y + 2, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 5, y + 2, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 2, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 3, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 3, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 3, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 3, y + 3, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 4, y + 3, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 5, y + 3, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 3, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 3, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 3, z + 1, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 2, y + 3, z + 1, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 3, y + 3, z + 1, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 4, y + 3, z + 1, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 5, y + 3, z + 1, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 6, y + 3, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 3, z + 2, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 3, z + 2, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 2, y + 3, z + 2, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 3, y + 3, z + 2, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 4, y + 3, z + 2, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 5, y + 3, z + 2, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 6, y + 3, z + 2, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 3, z + 3, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 3, z + 3, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 2, y + 3, z + 3, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 3, y + 3, z + 3, ModBlocks.watz_conductor, 0, 3);
		world.setBlock(x + 4, y + 3, z + 3, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 5, y + 3, z + 3, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 6, y + 3, z + 3, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 3, z + 4, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 3, z + 4, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 2, y + 3, z + 4, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 3, y + 3, z + 4, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 4, y + 3, z + 4, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 5, y + 3, z + 4, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 6, y + 3, z + 4, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 3, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 3, z + 5, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 2, y + 3, z + 5, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 3, y + 3, z + 5, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 4, y + 3, z + 5, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 5, y + 3, z + 5, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 6, y + 3, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 3, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 3, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 3, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 3, y + 3, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 4, y + 3, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 5, y + 3, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 3, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 4, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 4, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 4, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 3, y + 4, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 4, y + 4, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 5, y + 4, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 4, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 4, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 4, z + 1, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 2, y + 4, z + 1, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 3, y + 4, z + 1, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 4, y + 4, z + 1, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 5, y + 4, z + 1, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 6, y + 4, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 4, z + 2, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 4, z + 2, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 2, y + 4, z + 2, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 3, y + 4, z + 2, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 4, y + 4, z + 2, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 5, y + 4, z + 2, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 6, y + 4, z + 2, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 4, z + 3, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 4, z + 3, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 2, y + 4, z + 3, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 3, y + 4, z + 3, ModBlocks.watz_conductor, 0, 3);
		world.setBlock(x + 4, y + 4, z + 3, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 5, y + 4, z + 3, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 6, y + 4, z + 3, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 4, z + 4, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 4, z + 4, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 2, y + 4, z + 4, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 3, y + 4, z + 4, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 4, y + 4, z + 4, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 5, y + 4, z + 4, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 6, y + 4, z + 4, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 4, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 4, z + 5, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 2, y + 4, z + 5, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 3, y + 4, z + 5, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 4, y + 4, z + 5, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 5, y + 4, z + 5, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 6, y + 4, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 4, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 4, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 4, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 3, y + 4, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 4, y + 4, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 5, y + 4, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 4, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 5, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 5, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 5, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 3, y + 5, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 4, y + 5, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 5, y + 5, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 5, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 5, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 5, z + 1, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 2, y + 5, z + 1, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 3, y + 5, z + 1, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 4, y + 5, z + 1, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 5, y + 5, z + 1, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 6, y + 5, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 5, z + 2, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 5, z + 2, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 2, y + 5, z + 2, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 3, y + 5, z + 2, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 4, y + 5, z + 2, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 5, y + 5, z + 2, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 6, y + 5, z + 2, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 5, z + 3, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 5, z + 3, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 2, y + 5, z + 3, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 3, y + 5, z + 3, ModBlocks.watz_conductor, 0, 3);
		world.setBlock(x + 4, y + 5, z + 3, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 5, y + 5, z + 3, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 6, y + 5, z + 3, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 5, z + 4, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 5, z + 4, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 2, y + 5, z + 4, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 3, y + 5, z + 4, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 4, y + 5, z + 4, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 5, y + 5, z + 4, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 6, y + 5, z + 4, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 5, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 5, z + 5, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 2, y + 5, z + 5, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 3, y + 5, z + 5, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 4, y + 5, z + 5, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 5, y + 5, z + 5, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 6, y + 5, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 5, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 5, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 5, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 3, y + 5, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 4, y + 5, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 5, y + 5, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 5, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 6, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 6, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 6, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 3, y + 6, z + 0, ModBlocks.watz_hatch, 2, 3);
		world.setBlock(x + 4, y + 6, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 5, y + 6, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 6, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 6, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 6, z + 1, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 2, y + 6, z + 1, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 3, y + 6, z + 1, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 4, y + 6, z + 1, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 5, y + 6, z + 1, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 6, y + 6, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 6, z + 2, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 6, z + 2, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 2, y + 6, z + 2, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 3, y + 6, z + 2, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 4, y + 6, z + 2, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 5, y + 6, z + 2, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 6, y + 6, z + 2, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 6, z + 3, ModBlocks.watz_hatch, 4, 3);
		world.setBlock(x + 1, y + 6, z + 3, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 2, y + 6, z + 3, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 3, y + 6, z + 3, ModBlocks.watz_core, 0, 3);
		world.setBlock(x + 4, y + 6, z + 3, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 5, y + 6, z + 3, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 6, y + 6, z + 3, ModBlocks.watz_hatch, 5, 3);
		world.setBlock(x + 0, y + 6, z + 4, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 6, z + 4, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 2, y + 6, z + 4, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 3, y + 6, z + 4, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 4, y + 6, z + 4, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 5, y + 6, z + 4, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 6, y + 6, z + 4, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 6, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 6, z + 5, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 2, y + 6, z + 5, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 3, y + 6, z + 5, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 4, y + 6, z + 5, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 5, y + 6, z + 5, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 6, y + 6, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 6, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 6, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 6, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 3, y + 6, z + 6, ModBlocks.watz_hatch, 3, 3);
		world.setBlock(x + 4, y + 6, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 5, y + 6, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 6, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 7, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 7, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 7, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 3, y + 7, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 4, y + 7, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 5, y + 7, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 7, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 7, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 7, z + 1, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 2, y + 7, z + 1, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 3, y + 7, z + 1, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 4, y + 7, z + 1, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 5, y + 7, z + 1, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 6, y + 7, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 7, z + 2, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 7, z + 2, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 2, y + 7, z + 2, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 3, y + 7, z + 2, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 4, y + 7, z + 2, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 5, y + 7, z + 2, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 6, y + 7, z + 2, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 7, z + 3, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 7, z + 3, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 2, y + 7, z + 3, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 3, y + 7, z + 3, ModBlocks.watz_conductor, 0, 3);
		world.setBlock(x + 4, y + 7, z + 3, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 5, y + 7, z + 3, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 6, y + 7, z + 3, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 7, z + 4, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 7, z + 4, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 2, y + 7, z + 4, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 3, y + 7, z + 4, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 4, y + 7, z + 4, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 5, y + 7, z + 4, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 6, y + 7, z + 4, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 7, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 7, z + 5, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 2, y + 7, z + 5, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 3, y + 7, z + 5, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 4, y + 7, z + 5, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 5, y + 7, z + 5, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 6, y + 7, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 7, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 7, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 7, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 3, y + 7, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 4, y + 7, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 5, y + 7, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 7, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 8, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 8, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 8, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 3, y + 8, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 4, y + 8, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 5, y + 8, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 8, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 8, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 8, z + 1, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 2, y + 8, z + 1, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 3, y + 8, z + 1, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 4, y + 8, z + 1, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 5, y + 8, z + 1, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 6, y + 8, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 8, z + 2, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 8, z + 2, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 2, y + 8, z + 2, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 3, y + 8, z + 2, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 4, y + 8, z + 2, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 5, y + 8, z + 2, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 6, y + 8, z + 2, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 8, z + 3, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 8, z + 3, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 2, y + 8, z + 3, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 3, y + 8, z + 3, ModBlocks.watz_conductor, 0, 3);
		world.setBlock(x + 4, y + 8, z + 3, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 5, y + 8, z + 3, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 6, y + 8, z + 3, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 8, z + 4, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 8, z + 4, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 2, y + 8, z + 4, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 3, y + 8, z + 4, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 4, y + 8, z + 4, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 5, y + 8, z + 4, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 6, y + 8, z + 4, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 8, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 8, z + 5, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 2, y + 8, z + 5, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 3, y + 8, z + 5, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 4, y + 8, z + 5, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 5, y + 8, z + 5, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 6, y + 8, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 8, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 8, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 8, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 3, y + 8, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 4, y + 8, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 5, y + 8, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 8, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 9, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 9, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 9, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 3, y + 9, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 4, y + 9, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 5, y + 9, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 9, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 9, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 9, z + 1, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 2, y + 9, z + 1, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 3, y + 9, z + 1, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 4, y + 9, z + 1, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 5, y + 9, z + 1, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 6, y + 9, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 9, z + 2, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 9, z + 2, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 2, y + 9, z + 2, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 3, y + 9, z + 2, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 4, y + 9, z + 2, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 5, y + 9, z + 2, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 6, y + 9, z + 2, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 9, z + 3, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 9, z + 3, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 2, y + 9, z + 3, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 3, y + 9, z + 3, ModBlocks.watz_conductor, 0, 3);
		world.setBlock(x + 4, y + 9, z + 3, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 5, y + 9, z + 3, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 6, y + 9, z + 3, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 9, z + 4, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 9, z + 4, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 2, y + 9, z + 4, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 3, y + 9, z + 4, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 4, y + 9, z + 4, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 5, y + 9, z + 4, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 6, y + 9, z + 4, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 9, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 9, z + 5, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 2, y + 9, z + 5, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 3, y + 9, z + 5, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 4, y + 9, z + 5, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 5, y + 9, z + 5, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 6, y + 9, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 9, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 9, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 9, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 3, y + 9, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 4, y + 9, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 5, y + 9, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 9, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 10, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 10, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 10, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 3, y + 10, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 4, y + 10, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 5, y + 10, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 10, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 10, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 10, z + 1, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 2, y + 10, z + 1, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 3, y + 10, z + 1, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 4, y + 10, z + 1, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 5, y + 10, z + 1, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 6, y + 10, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 10, z + 2, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 10, z + 2, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 2, y + 10, z + 2, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 3, y + 10, z + 2, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 4, y + 10, z + 2, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 5, y + 10, z + 2, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 6, y + 10, z + 2, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 10, z + 3, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 10, z + 3, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 2, y + 10, z + 3, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 3, y + 10, z + 3, ModBlocks.watz_conductor, 0, 3);
		world.setBlock(x + 4, y + 10, z + 3, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 5, y + 10, z + 3, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 6, y + 10, z + 3, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 10, z + 4, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 10, z + 4, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 2, y + 10, z + 4, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 3, y + 10, z + 4, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 4, y + 10, z + 4, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 5, y + 10, z + 4, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 6, y + 10, z + 4, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 10, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 10, z + 5, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 2, y + 10, z + 5, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 3, y + 10, z + 5, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 4, y + 10, z + 5, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 5, y + 10, z + 5, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 6, y + 10, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 10, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 10, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 10, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 3, y + 10, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 4, y + 10, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 5, y + 10, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 10, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 11, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 11, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 11, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 3, y + 11, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 4, y + 11, z + 0, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 5, y + 11, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 11, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 11, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 11, z + 1, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 2, y + 11, z + 1, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 3, y + 11, z + 1, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 4, y + 11, z + 1, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 5, y + 11, z + 1, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 6, y + 11, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 11, z + 2, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 11, z + 2, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 2, y + 11, z + 2, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 3, y + 11, z + 2, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 4, y + 11, z + 2, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 5, y + 11, z + 2, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 6, y + 11, z + 2, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 11, z + 3, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 11, z + 3, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 2, y + 11, z + 3, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 3, y + 11, z + 3, ModBlocks.watz_conductor, 0, 3);
		world.setBlock(x + 4, y + 11, z + 3, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 5, y + 11, z + 3, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 6, y + 11, z + 3, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 11, z + 4, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 1, y + 11, z + 4, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 2, y + 11, z + 4, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 3, y + 11, z + 4, ModBlocks.watz_cooler, 0, 3);
		world.setBlock(x + 4, y + 11, z + 4, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 5, y + 11, z + 4, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 6, y + 11, z + 4, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 0, y + 11, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 11, z + 5, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 2, y + 11, z + 5, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 3, y + 11, z + 5, ModBlocks.watz_control, 0, 3);
		world.setBlock(x + 4, y + 11, z + 5, ModBlocks.watz_element, 0, 3);
		world.setBlock(x + 5, y + 11, z + 5, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 6, y + 11, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 11, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 11, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 11, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 3, y + 11, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 4, y + 11, z + 6, ModBlocks.reinforced_brick, 0, 3);
		world.setBlock(x + 5, y + 11, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 11, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 12, z + 0, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 1, y + 12, z + 0, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 2, y + 12, z + 0, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 3, y + 12, z + 0, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 4, y + 12, z + 0, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 5, y + 12, z + 0, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 6, y + 12, z + 0, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 0, y + 12, z + 1, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 1, y + 12, z + 1, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 2, y + 12, z + 1, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 3, y + 12, z + 1, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 4, y + 12, z + 1, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 5, y + 12, z + 1, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 6, y + 12, z + 1, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 0, y + 12, z + 2, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 1, y + 12, z + 2, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 2, y + 12, z + 2, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 3, y + 12, z + 2, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 4, y + 12, z + 2, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 5, y + 12, z + 2, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 6, y + 12, z + 2, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 0, y + 12, z + 3, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 1, y + 12, z + 3, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 2, y + 12, z + 3, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 3, y + 12, z + 3, ModBlocks.watz_conductor, 0, 3);
		world.setBlock(x + 4, y + 12, z + 3, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 5, y + 12, z + 3, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 6, y + 12, z + 3, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 0, y + 12, z + 4, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 1, y + 12, z + 4, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 2, y + 12, z + 4, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 3, y + 12, z + 4, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 4, y + 12, z + 4, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 5, y + 12, z + 4, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 6, y + 12, z + 4, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 0, y + 12, z + 5, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 1, y + 12, z + 5, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 2, y + 12, z + 5, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 3, y + 12, z + 5, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 4, y + 12, z + 5, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 5, y + 12, z + 5, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 6, y + 12, z + 5, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 0, y + 12, z + 6, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 1, y + 12, z + 6, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 2, y + 12, z + 6, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 3, y + 12, z + 6, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 4, y + 12, z + 6, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 5, y + 12, z + 6, ModBlocks.watz_end, 0, 3);
		world.setBlock(x + 6, y + 12, z + 6, ModBlocks.watz_end, 0, 3);
		return true;

	}

}