package com.hbm.world.feature;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class Dud extends WorldGenerator
{
	Block Block1 = ModBlocks.steel_scaffold;
	Block Block2 = ModBlocks.machine_difurnace_off;
	Block Block3 = ModBlocks.factory_titanium_core;
	Block Block4 = ModBlocks.steel_wall;
	Block Block5 = ModBlocks.reinforced_light;
	
	protected Block[] GetValidSpawnBlocks()
	{
		return new Block[]
		{
			Blocks.grass,
			Blocks.dirt,
			Blocks.stone,
			Blocks.sand,
			Blocks.sandstone,
		};
	}

	public boolean LocationIsValidSpawn(World world, int x, int y, int z)
 {

		Block checkBlock = world.getBlock(x, y - 1, z);
		Block blockAbove = world.getBlock(x, y , z);
		Block blockBelow = world.getBlock(x, y - 2, z);

		for (Block i : GetValidSpawnBlocks())
		{
			if (blockAbove != Blocks.air)
			{
				return false;
			}
			if (checkBlock == i)
			{
				return true;
			}
			else if (checkBlock == Blocks.snow_layer && blockBelow == i)
			{
				return true;
			}
			else if (checkBlock.getMaterial() == Material.plants && blockBelow == i)
			{
				return true;
			}
		}
		return false;
	}

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
		if(!LocationIsValidSpawn(world, x, y, z))
		{
			return false;
		}
		
		world.setBlock(x, y, z, ModBlocks.crashed_balefire, rand.nextInt(4) + 2, 3);
		if(MainRegistry.enableDebugMode)
			System.out.print("[Debug] Successfully spawned dud at " + x + " " + y +" " + z + "\n");
		return true;

	}
}
