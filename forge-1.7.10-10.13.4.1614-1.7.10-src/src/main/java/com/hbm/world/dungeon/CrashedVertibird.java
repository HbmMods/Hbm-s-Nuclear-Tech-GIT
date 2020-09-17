//Schematic to java Structure by jajo_11 | inspired by "MITHION'S .SCHEMATIC TO JAVA CONVERTINGTOOL"

package com.hbm.world.dungeon;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.lib.HbmChestContents;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class CrashedVertibird extends WorldGenerator
{
	Block Block1 = ModBlocks.deco_steel;
	Block Block2 = ModBlocks.deco_tungsten;
	Block Block3 = ModBlocks.reinforced_glass;
	Block Block4 = ModBlocks.deco_titanium;
	
	protected Block[] GetValidSpawnBlocks()
	{
		return new Block[]
		{
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
		int yOffset = 8 + rand.nextInt(4);
		
		if(!LocationIsValidSpawn(world, x + 9, y, z + 9))
		{
			return false;
		}

		world.setBlock(x + 4, y + 0 - yOffset, z + 1, Block1, 0, 3);
		world.setBlock(x + 5, y + 0 - yOffset, z + 1, Block1, 0, 3);
		world.setBlock(x + 6, y + 0 - yOffset, z + 1, Block1, 0, 3);
		world.setBlock(x + 5, y + 0 - yOffset, z + 2, Block2, 0, 3);
		world.setBlock(x + 4, y + 1 - yOffset, z + 0, Block1, 0, 3);
		world.setBlock(x + 5, y + 1 - yOffset, z + 0, Block1, 0, 3);
		world.setBlock(x + 6, y + 1 - yOffset, z + 0, Block1, 0, 3);
		world.setBlock(x + 3, y + 1 - yOffset, z + 1, Block1, 0, 3);
		world.setBlock(x + 7, y + 1 - yOffset, z + 1, Block1, 0, 3);
		world.setBlock(x + 4, y + 1 - yOffset, z + 2, Block1, 0, 3);
		world.setBlock(x + 5, y + 1 - yOffset, z + 2, Block1, 0, 3);
		world.setBlock(x + 6, y + 1 - yOffset, z + 2, Block1, 0, 3);
		world.setBlock(x + 3, y + 1 - yOffset, z + 3, Block2, 0, 3);
		world.setBlock(x + 4, y + 1 - yOffset, z + 3, Block1, 0, 3);
		world.setBlock(x + 5, y + 1 - yOffset, z + 3, Block1, 0, 3);
		world.setBlock(x + 6, y + 1 - yOffset, z + 3, Block1, 0, 3);
		world.setBlock(x + 7, y + 1 - yOffset, z + 3, Block2, 0, 3);
		world.setBlock(x + 4, y + 2 - yOffset, z + 0, Block3, 0, 3);
		world.setBlock(x + 5, y + 2 - yOffset, z + 0, Block3, 0, 3);
		world.setBlock(x + 3, y + 2 - yOffset, z + 1, Block3, 0, 3);
		world.setBlock(x + 7, y + 2 - yOffset, z + 1, Block3, 0, 3);
		world.setBlock(x + 3, y + 2 - yOffset, z + 2, Block1, 0, 3);
		world.setBlock(x + 4, y + 2 - yOffset, z + 2, Blocks.stone_stairs, 2, 3);
		world.setBlock(x + 6, y + 2 - yOffset, z + 2, Blocks.stone_stairs, 2, 3);
		world.setBlock(x + 7, y + 2 - yOffset, z + 2, Block1, 0, 3);
		world.setBlock(x + 7, y + 2 - yOffset, z + 3, Block4, 0, 3);
		world.setBlock(x + 3, y + 2 - yOffset, z + 4, Block2, 0, 3);
		world.setBlock(x + 4, y + 2 - yOffset, z + 4, Block1, 0, 3);
		world.setBlock(x + 5, y + 2 - yOffset, z + 4, Block1, 0, 3);
		world.setBlock(x + 6, y + 2 - yOffset, z + 4, Block1, 0, 3);
		world.setBlock(x + 7, y + 2 - yOffset, z + 4, Block2, 0, 3);
		world.setBlock(x + 3, y + 2 - yOffset, z + 5, Block2, 0, 3);
		world.setBlock(x + 4, y + 2 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 5, y + 2 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 6, y + 2 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 7, y + 2 - yOffset, z + 5, Block2, 0, 3);
		world.setBlock(x + 4, y + 2 - yOffset, z + 7, Block2, 0, 3);
		world.setBlock(x + 6, y + 2 - yOffset, z + 7, Block2, 0, 3);
		world.setBlock(x + 4, y + 3 - yOffset, z + 0, Block1, 0, 3);
		world.setBlock(x + 5, y + 3 - yOffset, z + 0, Block1, 0, 3);
		world.setBlock(x + 6, y + 3 - yOffset, z + 0, Block1, 0, 3);
		world.setBlock(x + 3, y + 3 - yOffset, z + 1, Block3, 0, 3);
		world.setBlock(x + 4, y + 3 - yOffset, z + 1, Block1, 0, 3);
		world.setBlock(x + 5, y + 3 - yOffset, z + 1, Block1, 0, 3);
		world.setBlock(x + 6, y + 3 - yOffset, z + 1, Block1, 0, 3);
		world.setBlock(x + 7, y + 3 - yOffset, z + 1, Block3, 0, 3);
		world.setBlock(x + 3, y + 3 - yOffset, z + 2, Block1, 0, 3);
		world.setBlock(x + 7, y + 3 - yOffset, z + 2, Block1, 0, 3);
		world.setBlock(x + 3, y + 3 - yOffset, z + 3, Block1, 0, 3);
		world.setBlock(x + 7, y + 3 - yOffset, z + 3, Block1, 0, 3);
		world.setBlock(x + 7, y + 3 - yOffset, z + 4, Block4, 0, 3);
		world.setBlock(x + 2, y + 3 - yOffset, z + 5, Block4, 0, 3);
		world.setBlock(x + 7, y + 3 - yOffset, z + 5, Block4, 0, 3);
		world.setBlock(x + 4, y + 3 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 5, y + 3 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 6, y + 3 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 4, y + 3 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 5, y + 3 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 6, y + 3 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 5, y + 3 - yOffset, z + 9, Block2, 0, 3);
		world.setBlock(x + 4, y + 4 - yOffset, z + 1, Block1, 0, 3);
		world.setBlock(x + 5, y + 4 - yOffset, z + 1, Block1, 0, 3);
		world.setBlock(x + 6, y + 4 - yOffset, z + 1, Block1, 0, 3);
		world.setBlock(x + 3, y + 4 - yOffset, z + 2, Block3, 0, 3);
		world.setBlock(x + 3, y + 4 - yOffset, z + 3, Block1, 0, 3);
		world.setBlock(x + 7, y + 4 - yOffset, z + 3, Block1, 0, 3);
		world.setBlock(x + 7, y + 4 - yOffset, z + 5, Block4, 0, 3);
		world.setBlock(x + 2, y + 4 - yOffset, z + 6, Block4, 0, 3);
		world.setBlock(x + 3, y + 4 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 7, y + 4 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 2, y + 4 - yOffset, z + 7, Block4, 0, 3);
		world.setBlock(x + 3, y + 4 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 6, y + 4 - yOffset, z + 7, Blocks.chest, 2, 3);
		if(world.getBlock(x + 6, y + 4 - yOffset, z + 7) == Blocks.chest)
		{
			WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.getLoot(6), (TileEntityChest)world.getTileEntity(x + 6, y + 4 - yOffset, z + 7), 8);
		}
		world.setBlock(x + 7, y + 4 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 4, y + 4 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 5, y + 4 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 6, y + 4 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 4, y + 4 - yOffset, z + 9, Block1, 0, 3);
		world.setBlock(x + 5, y + 4 - yOffset, z + 9, Block1, 0, 3);
		world.setBlock(x + 6, y + 4 - yOffset, z + 9, Block1, 0, 3);
		world.setBlock(x + 6, y + 5 - yOffset, z + 1, Block2, 0, 3);
		world.setBlock(x + 4, y + 5 - yOffset, z + 2, Block1, 0, 3);
		world.setBlock(x + 5, y + 5 - yOffset, z + 2, Block1, 0, 3);
		world.setBlock(x + 6, y + 5 - yOffset, z + 2, Block1, 0, 3);
		world.setBlock(x + 4, y + 5 - yOffset, z + 3, Block1, 0, 3);
		world.setBlock(x + 5, y + 5 - yOffset, z + 3, Block1, 0, 3);
		world.setBlock(x + 6, y + 5 - yOffset, z + 3, Block1, 0, 3);
		world.setBlock(x + 7, y + 5 - yOffset, z + 3, Block1, 0, 3);
		world.setBlock(x + 3, y + 5 - yOffset, z + 4, Block1, 0, 3);
		world.setBlock(x + 7, y + 5 - yOffset, z + 4, Block1, 0, 3);
		world.setBlock(x + 3, y + 5 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 7, y + 5 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 2, y + 5 - yOffset, z + 6, Block4, 0, 3);
		world.setBlock(x + 3, y + 5 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 7, y + 5 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 2, y + 5 - yOffset, z + 7, Block4, 0, 3);
		world.setBlock(x + 3, y + 5 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 7, y + 5 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 4, y + 5 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 5, y + 5 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 6, y + 5 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 4, y + 5 - yOffset, z + 9, Block1, 0, 3);
		world.setBlock(x + 5, y + 5 - yOffset, z + 9, Block4, 0, 3);
		world.setBlock(x + 6, y + 5 - yOffset, z + 9, Block1, 0, 3);
		world.setBlock(x + 5, y + 6 - yOffset, z + 3, Block1, 0, 3);
		world.setBlock(x + 6, y + 6 - yOffset, z + 3, Block1, 0, 3);
		world.setBlock(x + 4, y + 6 - yOffset, z + 4, Block1, 0, 3);
		world.setBlock(x + 5, y + 6 - yOffset, z + 4, Block1, 0, 3);
		world.setBlock(x + 6, y + 6 - yOffset, z + 4, Block1, 0, 3);
		world.setBlock(x + 7, y + 6 - yOffset, z + 4, Block1, 0, 3);
		world.setBlock(x + 14, y + 6 - yOffset, z + 4, Block1, 0, 3);
		world.setBlock(x + 0, y + 6 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 1, y + 6 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 2, y + 6 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 3, y + 6 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 4, y + 6 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 5, y + 6 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 6, y + 6 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 7, y + 6 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 8, y + 6 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 9, y + 6 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 10, y + 6 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 13, y + 6 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 3, y + 6 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 4, y + 6 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 6, y + 6 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 7, y + 6 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 3, y + 6 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 4, y + 6 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 5, y + 6 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 6, y + 6 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 7, y + 6 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 4, y + 6 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 5, y + 6 - yOffset, z + 8, ModBlocks.machine_battery, 2, 3);
		world.setBlock(x + 6, y + 6 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 4, y + 6 - yOffset, z + 9, Block1, 0, 3);
		world.setBlock(x + 5, y + 6 - yOffset, z + 9, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 6, y + 6 - yOffset, z + 9, Block1, 0, 3);
		world.setBlock(x + 4, y + 6 - yOffset, z + 10, Block1, 0, 3);
		world.setBlock(x + 5, y + 6 - yOffset, z + 10, Block1, 0, 3);
		world.setBlock(x + 6, y + 6 - yOffset, z + 10, Block1, 0, 3);
		world.setBlock(x + 5, y + 6 - yOffset, z + 11, Block1, 0, 3);
		world.setBlock(x + 6, y + 6 - yOffset, z + 11, Block1, 0, 3);
		world.setBlock(x + 4, y + 7 - yOffset, z + 4, Block1, 0, 3);
		world.setBlock(x + 5, y + 7 - yOffset, z + 4, Block1, 0, 3);
		world.setBlock(x + 6, y + 7 - yOffset, z + 4, Block1, 0, 3);
		world.setBlock(x + 7, y + 7 - yOffset, z + 4, Block1, 0, 3);
		world.setBlock(x + 14, y + 7 - yOffset, z + 4, Block1, 0, 3);
		world.setBlock(x + 1, y + 7 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 2, y + 7 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 3, y + 7 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 4, y + 7 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 5, y + 7 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 6, y + 7 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 7, y + 7 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 8, y + 7 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 9, y + 7 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 10, y + 7 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 11, y + 7 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 12, y + 7 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 13, y + 7 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 14, y + 7 - yOffset, z + 5, Block2, 0, 3);
		world.setBlock(x + 3, y + 7 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 4, y + 7 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 5, y + 7 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 6, y + 7 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 7, y + 7 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 14, y + 7 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 3, y + 7 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 4, y + 7 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 5, y + 7 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 6, y + 7 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 7, y + 7 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 3, y + 7 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 4, y + 7 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 5, y + 7 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 6, y + 7 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 7, y + 7 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 3, y + 7 - yOffset, z + 9, Block1, 0, 3);
		world.setBlock(x + 4, y + 7 - yOffset, z + 9, ModBlocks.machine_generator, 0, 3);
		world.setBlock(x + 5, y + 7 - yOffset, z + 9, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 6, y + 7 - yOffset, z + 9, ModBlocks.machine_generator, 0, 3);
		world.setBlock(x + 7, y + 7 - yOffset, z + 9, Block1, 0, 3);
		world.setBlock(x + 5, y + 7 - yOffset, z + 10, Block4, 0, 3);
		world.setBlock(x + 6, y + 7 - yOffset, z + 10, Block1, 0, 3);
		world.setBlock(x + 5, y + 7 - yOffset, z + 11, Block4, 0, 3);
		world.setBlock(x + 6, y + 7 - yOffset, z + 11, Block1, 0, 3);
		world.setBlock(x + 5, y + 8 - yOffset, z + 4, Block1, 0, 3);
		world.setBlock(x + 14, y + 8 - yOffset, z + 4, Block1, 0, 3);
		world.setBlock(x + 4, y + 8 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 5, y + 8 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 6, y + 8 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 13, y + 8 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 14, y + 8 - yOffset, z + 5, Block2, 0, 3);
		world.setBlock(x + 15, y + 8 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 3, y + 8 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 4, y + 8 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 5, y + 8 - yOffset, z + 6, Blocks.chest, 2, 3);
		if(world.getBlock(x + 5, y + 8 - yOffset, z + 6) == Blocks.chest)
		{
			WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.getLoot(3), (TileEntityChest)world.getTileEntity(x + 5, y + 8 - yOffset, z + 6), 8);
		}
		world.setBlock(x + 6, y + 8 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 7, y + 8 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 14, y + 8 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 3, y + 8 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 4, y + 8 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 5, y + 8 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 6, y + 8 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 7, y + 8 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 3, y + 8 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 4, y + 8 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 5, y + 8 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 6, y + 8 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 7, y + 8 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 3, y + 8 - yOffset, z + 9, Block1, 0, 3);
		world.setBlock(x + 4, y + 8 - yOffset, z + 9, ModBlocks.machine_generator, 0, 3);
		world.setBlock(x + 5, y + 8 - yOffset, z + 9, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 6, y + 8 - yOffset, z + 9, ModBlocks.machine_generator, 0, 3);
		world.setBlock(x + 7, y + 8 - yOffset, z + 9, Block1, 0, 3);
		world.setBlock(x + 4, y + 8 - yOffset, z + 10, ModBlocks.machine_generator, 0, 3);
		world.setBlock(x + 5, y + 8 - yOffset, z + 10, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 6, y + 8 - yOffset, z + 10, ModBlocks.machine_generator, 0, 3);
		world.setBlock(x + 7, y + 8 - yOffset, z + 10, Block1, 0, 3);
		world.setBlock(x + 5, y + 8 - yOffset, z + 11, Block4, 0, 3);
		world.setBlock(x + 6, y + 8 - yOffset, z + 11, Block1, 0, 3);
		world.setBlock(x + 7, y + 8 - yOffset, z + 11, Block1, 0, 3);
		world.setBlock(x + 6, y + 8 - yOffset, z + 12, Block1, 0, 3);
		world.setBlock(x + 14, y + 9 - yOffset, z + 4, Block1, 0, 3);
		world.setBlock(x + 13, y + 9 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 14, y + 9 - yOffset, z + 5, Block2, 0, 3);
		world.setBlock(x + 15, y + 9 - yOffset, z + 5, Block1, 0, 3);
		world.setBlock(x + 4, y + 9 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 5, y + 9 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 6, y + 9 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 14, y + 9 - yOffset, z + 6, Block1, 0, 3);
		world.setBlock(x + 4, y + 9 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 5, y + 9 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 6, y + 9 - yOffset, z + 7, Block1, 0, 3);
		world.setBlock(x + 3, y + 9 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 4, y + 9 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 5, y + 9 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 6, y + 9 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 7, y + 9 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 4, y + 9 - yOffset, z + 9, Block1, 0, 3);
		world.setBlock(x + 5, y + 9 - yOffset, z + 9, Block4, 0, 3);
		world.setBlock(x + 6, y + 9 - yOffset, z + 9, Block1, 0, 3);
		world.setBlock(x + 4, y + 9 - yOffset, z + 10, ModBlocks.machine_generator, 0, 3);
		world.setBlock(x + 5, y + 9 - yOffset, z + 10, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 6, y + 9 - yOffset, z + 10, ModBlocks.machine_generator, 0, 3);
		world.setBlock(x + 7, y + 9 - yOffset, z + 10, Block1, 0, 3);
		world.setBlock(x + 5, y + 9 - yOffset, z + 11, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 6, y + 9 - yOffset, z + 11, Block1, 0, 3);
		world.setBlock(x + 5, y + 9 - yOffset, z + 12, Block4, 0, 3);
		world.setBlock(x + 6, y + 9 - yOffset, z + 12, Block1, 0, 3);
		world.setBlock(x + 7, y + 9 - yOffset, z + 12, Block1, 0, 3);
		world.setBlock(x + 5, y + 9 - yOffset, z + 13, Block1, 0, 3);
		world.setBlock(x + 6, y + 9 - yOffset, z + 13, Block1, 0, 3);
		world.setBlock(x + 14, y + 10 - yOffset, z + 5, Block2, 0, 3);
		world.setBlock(x + 4, y + 10 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 5, y + 10 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 6, y + 10 - yOffset, z + 8, Block1, 0, 3);
		world.setBlock(x + 5, y + 10 - yOffset, z + 9, Block1, 0, 3);
		world.setBlock(x + 4, y + 10 - yOffset, z + 10, Block1, 0, 3);
		world.setBlock(x + 5, y + 10 - yOffset, z + 10, Block4, 0, 3);
		world.setBlock(x + 6, y + 10 - yOffset, z + 10, Block1, 0, 3);
		world.setBlock(x + 4, y + 10 - yOffset, z + 11, Block1, 0, 3);
		world.setBlock(x + 5, y + 10 - yOffset, z + 11, Block1, 0, 3);
		world.setBlock(x + 6, y + 10 - yOffset, z + 11, Block1, 0, 3);
		world.setBlock(x + 4, y + 10 - yOffset, z + 12, Block1, 0, 3);
		world.setBlock(x + 5, y + 10 - yOffset, z + 12, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 6, y + 10 - yOffset, z + 12, Block1, 0, 3);
		world.setBlock(x + 4, y + 10 - yOffset, z + 13, Block1, 0, 3);
		world.setBlock(x + 5, y + 10 - yOffset, z + 13, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 6, y + 10 - yOffset, z + 13, Block1, 0, 3);
		world.setBlock(x + 5, y + 10 - yOffset, z + 14, Block1, 0, 3);
		world.setBlock(x + 5, y + 10 - yOffset, z + 15, Block1, 0, 3);
		world.setBlock(x + 5, y + 10 - yOffset, z + 17, Block1, 0, 3);
		world.setBlock(x + 5, y + 10 - yOffset, z + 18, Block1, 0, 3);
		world.setBlock(x + 14, y + 11 - yOffset, z + 4, Block4, 0, 3);
		world.setBlock(x + 12, y + 11 - yOffset, z + 5, Block4, 0, 3);
		world.setBlock(x + 13, y + 11 - yOffset, z + 5, Block4, 0, 3);
		world.setBlock(x + 14, y + 11 - yOffset, z + 5, Block2, 0, 3);
		world.setBlock(x + 15, y + 11 - yOffset, z + 5, Block4, 0, 3);
		world.setBlock(x + 16, y + 11 - yOffset, z + 5, Block4, 0, 3);
		world.setBlock(x + 17, y + 11 - yOffset, z + 5, Block4, 0, 3);
		world.setBlock(x + 14, y + 11 - yOffset, z + 6, Block4, 0, 3);
		world.setBlock(x + 14, y + 11 - yOffset, z + 7, Block4, 0, 3);
		world.setBlock(x + 5, y + 11 - yOffset, z + 10, Block1, 0, 3);
		world.setBlock(x + 5, y + 11 - yOffset, z + 11, Block1, 0, 3);
		world.setBlock(x + 4, y + 11 - yOffset, z + 12, Block1, 0, 3);
		world.setBlock(x + 5, y + 11 - yOffset, z + 12, Block1, 0, 3);
		world.setBlock(x + 6, y + 11 - yOffset, z + 12, Block1, 0, 3);
		world.setBlock(x + 5, y + 11 - yOffset, z + 13, Block1, 0, 3);
		world.setBlock(x + 4, y + 11 - yOffset, z + 14, Block1, 0, 3);
		world.setBlock(x + 5, y + 11 - yOffset, z + 14, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 6, y + 11 - yOffset, z + 14, Block1, 0, 3);
		world.setBlock(x + 4, y + 11 - yOffset, z + 15, Block1, 0, 3);
		world.setBlock(x + 5, y + 11 - yOffset, z + 15, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 6, y + 11 - yOffset, z + 15, Block1, 0, 3);
		world.setBlock(x + 5, y + 11 - yOffset, z + 16, Block1, 0, 3);
		world.setBlock(x + 5, y + 11 - yOffset, z + 17, Block1, 0, 3);
		world.setBlock(x + 5, y + 11 - yOffset, z + 18, Block1, 0, 3);
		world.setBlock(x + 5, y + 12 - yOffset, z + 14, Block1, 0, 3);
		world.setBlock(x + 3, y + 12 - yOffset, z + 16, Block1, 0, 3);
		world.setBlock(x + 4, y + 12 - yOffset, z + 16, Block1, 0, 3);
		world.setBlock(x + 5, y + 12 - yOffset, z + 16, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 6, y + 12 - yOffset, z + 16, Block1, 0, 3);
		world.setBlock(x + 7, y + 12 - yOffset, z + 16, Block1, 0, 3);
		world.setBlock(x + 8, y + 12 - yOffset, z + 16, Block1, 0, 3);
		world.setBlock(x + 9, y + 12 - yOffset, z + 16, Block1, 0, 3);
		world.setBlock(x + 4, y + 12 - yOffset, z + 17, Block1, 0, 3);
		world.setBlock(x + 5, y + 12 - yOffset, z + 17, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 6, y + 12 - yOffset, z + 17, Block1, 0, 3);
		world.setBlock(x + 7, y + 12 - yOffset, z + 17, Block1, 0, 3);
		world.setBlock(x + 8, y + 12 - yOffset, z + 17, Block1, 0, 3);
		world.setBlock(x + 9, y + 12 - yOffset, z + 17, Block1, 0, 3);
		world.setBlock(x + 10, y + 12 - yOffset, z + 17, Block1, 0, 3);
		world.setBlock(x + 5, y + 13 - yOffset, z + 17, Block1, 0, 3);

		generate_r02_last(world, rand, x, y, z, yOffset);
		return true;

	}
	public boolean generate_r02_last(World world, Random rand, int x, int y, int z, int yOffset)
	{

		world.setBlock(x + 4, y + 1 - yOffset, z + 1, Blocks.lever, 3, 3);
		world.setBlock(x + 6, y + 1 - yOffset, z + 1, Blocks.lever, 3, 3);
		if(GeneralConfig.enableDebugMode)
			System.out.print("[Debug] Successfully spawned crashed Vertibird at " + x + " " + y +" " + z + "\n");
		return true;

	}

}