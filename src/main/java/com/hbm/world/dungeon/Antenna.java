//Schematic to java Structure by jajo_11 | inspired by "MITHION'S .SCHEMATIC TO JAVA CONVERTINGTOOL"

package com.hbm.world.dungeon;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.itempool.ItemPool;
import com.hbm.itempool.ItemPoolsLegacy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class Antenna extends WorldGenerator
{
	protected Block[] GetValidSpawnBlocks()
	{
		return new Block[]
		{
			Blocks.grass,
			Blocks.dirt,
			Blocks.stone,
			Blocks.sand,
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
		if(!LocationIsValidSpawn(world, x + 1, y, z + 1))
		{
			return false;
		}

		world.setBlock(x + 0, y + 0, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 0, z + 0, ModBlocks.steel_poles, 2, 3);
		world.setBlock(x + 2, y + 0, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 0, z + 1, ModBlocks.steel_poles, 4, 3);
		world.setBlock(x + 1, y + 0, z + 1, ModBlocks.deco_steel, 0, 3);
		world.setBlock(x + 2, y + 0, z + 1, ModBlocks.tape_recorder, 5, 3);
		world.setBlock(x + 0, y + 0, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 0, z + 2, ModBlocks.steel_poles, 3, 3);
		world.setBlock(x + 2, y + 0, z + 2, Blocks.chest, 0, 3);
		world.setBlockMetadataWithNotify(x + 2, y + 0, z + 2, 5, 3);
        WeightedRandomChestContent.generateChestContents(rand, ItemPool.getPool(ItemPoolsLegacy.POOL_ANTENNA), (TileEntityChest)world.getTileEntity(x + 2, y, z + 2), 8);
		world.setBlock(x + 0, y + 1, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 1, z + 0, ModBlocks.steel_poles, 2, 3);
		world.setBlock(x + 2, y + 1, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 1, z + 1, ModBlocks.steel_poles, 4, 3);
		world.setBlock(x + 1, y + 1, z + 1, ModBlocks.deco_steel, 0, 3);
		world.setBlock(x + 2, y + 1, z + 1, ModBlocks.tape_recorder, 5, 3);
		world.setBlock(x + 0, y + 1, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 1, z + 2, ModBlocks.steel_poles, 3, 3);
		world.setBlock(x + 2, y + 1, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 2, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 2, z + 0, ModBlocks.deco_steel, 0, 3);
		world.setBlock(x + 2, y + 2, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 2, z + 1, ModBlocks.deco_steel, 0, 3);
		world.setBlock(x + 1, y + 2, z + 1, ModBlocks.deco_steel, 0, 3);
		world.setBlock(x + 2, y + 2, z + 1, ModBlocks.deco_steel, 0, 3);
		world.setBlock(x + 0, y + 2, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 2, z + 2, ModBlocks.deco_steel, 0, 3);
		world.setBlock(x + 2, y + 2, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 3, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 3, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 3, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 3, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 3, z + 1, ModBlocks.steel_poles, 4, 3);
		world.setBlock(x + 2, y + 3, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 3, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 3, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 3, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 4, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 4, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 4, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 4, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 4, z + 1, ModBlocks.steel_poles, 4, 3);
		world.setBlock(x + 2, y + 4, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 4, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 4, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 4, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 5, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 5, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 5, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 5, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 5, z + 1, ModBlocks.steel_poles, 4, 3);
		world.setBlock(x + 2, y + 5, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 5, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 5, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 5, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 6, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 6, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 6, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 6, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 6, z + 1, ModBlocks.steel_poles, 4, 3);
		world.setBlock(x + 2, y + 6, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 6, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 6, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 6, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 7, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 7, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 7, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 7, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 7, z + 1, ModBlocks.steel_poles, 4, 3);
		world.setBlock(x + 2, y + 7, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 7, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 7, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 7, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 8, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 8, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 8, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 8, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 8, z + 1, ModBlocks.steel_poles, 4, 3);
		world.setBlock(x + 2, y + 8, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 8, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 8, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 8, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 9, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 9, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 9, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 9, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 9, z + 1, ModBlocks.steel_poles, 4, 3);
		world.setBlock(x + 2, y + 9, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 9, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 9, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 9, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 10, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 10, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 10, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 10, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 10, z + 1, ModBlocks.steel_poles, 4, 3);
		world.setBlock(x + 2, y + 10, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 10, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 10, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 10, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 11, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 11, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 11, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 11, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 11, z + 1, ModBlocks.steel_poles, 4, 3);
		world.setBlock(x + 2, y + 11, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 11, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 11, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 11, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 12, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 12, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 12, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 12, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 12, z + 1, ModBlocks.steel_poles, 4, 3);
		world.setBlock(x + 2, y + 12, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 12, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 12, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 12, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 13, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 13, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 13, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 13, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 13, z + 1, ModBlocks.pole_satellite_receiver, 3, 3);
		world.setBlock(x + 2, y + 13, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 13, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 13, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 13, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 14, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 14, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 14, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 14, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 14, z + 1, ModBlocks.steel_poles, 4, 3);
		world.setBlock(x + 2, y + 14, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 14, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 14, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 14, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 15, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 15, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 15, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 15, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 15, z + 1, ModBlocks.steel_poles, 4, 3);
		world.setBlock(x + 2, y + 15, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 15, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 15, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 15, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 16, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 16, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 16, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 16, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 16, z + 1, ModBlocks.steel_poles, 4, 3);
		world.setBlock(x + 2, y + 16, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 16, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 16, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 16, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 17, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 17, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 17, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 17, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 17, z + 1, ModBlocks.pole_satellite_receiver, 2, 3);
		world.setBlock(x + 2, y + 17, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 17, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 17, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 17, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 18, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 18, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 18, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 18, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 18, z + 1, ModBlocks.pole_satellite_receiver, 4, 3);
		world.setBlock(x + 2, y + 18, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 18, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 18, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 18, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 19, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 19, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 19, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 19, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 19, z + 1, ModBlocks.steel_poles, 4, 3);
		world.setBlock(x + 2, y + 19, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 19, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 19, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 19, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 20, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 20, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 20, z + 0, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 20, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 20, z + 1, ModBlocks.pole_top, 4, 3);
		world.setBlock(x + 2, y + 20, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 20, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 1, y + 20, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 20, z + 2, Blocks.air, 0, 3);
		if(GeneralConfig.enableDebugMode)
			System.out.print("[Debug] Successfully spawned antenna at " + x + " " + y +" " + z + "\n");
		return true;

	}

}