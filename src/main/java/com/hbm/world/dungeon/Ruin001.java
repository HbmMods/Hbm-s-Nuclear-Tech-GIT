//Schematic to java Structure by jajo_11 | inspired by "MITHION'S .SCHEMATIC TO JAVA CONVERTINGTOOL"

package com.hbm.world.dungeon;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class Ruin001 extends WorldGenerator {

	Block Block1 = ModBlocks.brick_concrete;
	Block Block2 = ModBlocks.brick_concrete_cracked;
	Block Block3 = ModBlocks.brick_concrete_broken;
	
	protected Block[] GetValidSpawnBlocks() {
		return new Block[] {Blocks.grass};
	}

	public boolean LocationIsValidSpawn(World world, int x, int y, int z) {

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

	public boolean generate(World world, Random rand, int x, int y, int z) {
		
		int i = rand.nextInt(1);

		if(i == 0)
		{
		    generate_r0(world, rand, x, y, z);
		}

       return true;
	}

	public boolean generate_r0(World world, Random rand, int x, int y, int z) {
		
		if(!LocationIsValidSpawn(world, x, y, z) || !LocationIsValidSpawn(world, x + 12, y, z) || !LocationIsValidSpawn(world, x + 12, y, z + 14) || !LocationIsValidSpawn(world, x, y, z + 14)) {
		//	return false;
		}

		world.setBlock(x + 1, y + 0, z + 5, Block1, 0, 3);
		world.setBlock(x + 1, y + 0, z + 6, Block1, 0, 3);
		world.setBlock(x + 2, y + 0, z + 6, Block1, 0, 3);
		world.setBlock(x + 3, y + 0, z + 6, Block1, 0, 3);
		world.setBlock(x + 4, y + 0, z + 6, Block1, 0, 3);
		world.setBlock(x + 5, y + 0, z + 6, Block1, 0, 3);
		world.setBlock(x + 6, y + 0, z + 6, Block1, 0, 3);
		world.setBlock(x + 7, y + 0, z + 6, Block1, 0, 3);
		world.setBlock(x + 8, y + 0, z + 6, Block1, 0, 3);
		world.setBlock(x + 3, y + 0, z + 7, Block1, 0, 3);
		world.setBlock(x + 1, y + 0, z + 8, Block1, 0, 3);
		world.setBlock(x + 2, y + 0, z + 8, Block1, 0, 3);
		world.setBlock(x + 3, y + 0, z + 8, Block1, 0, 3);
		world.setBlock(x + 1, y + 0, z + 9, Blocks.lit_furnace, 3, 3);
		world.setBlock(x + 2, y + 0, z + 9, Block1, 0, 3);
		world.setBlock(x + 1, y + 0, z + 10, Block1, 0, 3);
		world.setBlock(x + 1, y + 1, z + 4, Block1, 0, 3);
		world.setBlock(x + 0, y + 1, z + 5, Block1, 0, 3);
		world.setBlock(x + 1, y + 1, z + 5, Blocks.vine, 0, 3);
		world.setBlock(x + 2, y + 1, z + 5, Block1, 0, 3);
		world.setBlock(x + 3, y + 1, z + 5, Block1, 0, 3);
		world.setBlock(x + 4, y + 1, z + 5, Block1, 0, 3);
		world.setBlock(x + 5, y + 1, z + 5, Block1, 0, 3);
		world.setBlock(x + 6, y + 1, z + 5, Block1, 0, 3);
		world.setBlock(x + 7, y + 1, z + 5, Block1, 0, 3);
		world.setBlock(x + 8, y + 1, z + 5, Block1, 0, 3);
		world.setBlock(x + 0, y + 1, z + 6, Block1, 0, 3);
		world.setBlock(x + 9, y + 1, z + 6, Block1, 0, 3);
		world.setBlock(x + 1, y + 1, z + 7, Block1, 0, 3);
		world.setBlock(x + 2, y + 1, z + 7, Block1, 0, 3);
		world.setBlock(x + 3, y + 1, z + 7, Blocks.vine, 0, 3);
		world.setBlock(x + 4, y + 1, z + 7, Block1, 0, 3);
		world.setBlock(x + 5, y + 1, z + 7, Block1, 0, 3);
		world.setBlock(x + 6, y + 1, z + 7, Block1, 0, 3);
		world.setBlock(x + 7, y + 1, z + 7, Block1, 0, 3);
		world.setBlock(x + 8, y + 1, z + 7, Block1, 0, 3);
		world.setBlock(x + 0, y + 1, z + 8, Block1, 0, 3);
		world.setBlock(x + 1, y + 1, z + 8, Blocks.vine, 0, 3);
		world.setBlock(x + 2, y + 1, z + 8, Blocks.vine, 0, 3);
		world.setBlock(x + 3, y + 1, z + 8, Blocks.vine, 0, 3);
		world.setBlock(x + 4, y + 1, z + 8, Block1, 0, 3);
		world.setBlock(x + 0, y + 1, z + 9, Block1, 0, 3);
		world.setBlock(x + 1, y + 1, z + 9, Blocks.vine, 0, 3);
		world.setBlock(x + 2, y + 1, z + 9, Blocks.water, 0, 3);
		world.setBlock(x + 3, y + 1, z + 9, Block1, 0, 3);
		world.setBlock(x + 0, y + 1, z + 10, Block1, 0, 3);
		world.setBlock(x + 1, y + 1, z + 10, Blocks.vine, 0, 3);
		world.setBlock(x + 2, y + 1, z + 10, Block1, 0, 3);
		world.setBlock(x + 1, y + 1, z + 11, Block1, 0, 3);
		world.setBlock(x + 1, y + 2, z + 4, Block1, 0, 3);
		world.setBlock(x + 0, y + 2, z + 5, Block1, 0, 3);
		world.setBlock(x + 1, y + 2, z + 5, Blocks.vine, 0, 3);
		world.setBlock(x + 2, y + 2, z + 5, Block1, 0, 3);
		world.setBlock(x + 3, y + 2, z + 5, Block1, 0, 3);
		world.setBlock(x + 4, y + 2, z + 5, Block1, 0, 3);
		world.setBlock(x + 5, y + 2, z + 5, Block1, 0, 3);
		world.setBlock(x + 6, y + 2, z + 5, Block1, 0, 3);
		world.setBlock(x + 7, y + 2, z + 5, Block1, 0, 3);
		world.setBlock(x + 8, y + 2, z + 5, Block1, 0, 3);
		world.setBlock(x + 9, y + 2, z + 5, Block1, 0, 3);
		world.setBlock(x + 10, y + 2, z + 5, Block1, 0, 3);
		world.setBlock(x + 11, y + 2, z + 5, Block1, 0, 3);
		world.setBlock(x + 0, y + 2, z + 6, Block1, 0, 3);
		world.setBlock(x + 1, y + 2, z + 6, Block1, 0, 3);
		world.setBlock(x + 2, y + 2, z + 6, Block1, 0, 3);
		world.setBlock(x + 3, y + 2, z + 6, Block1, 0, 3);
		world.setBlock(x + 4, y + 2, z + 6, Block1, 0, 3);
		world.setBlock(x + 5, y + 2, z + 6, Block1, 0, 3);
		world.setBlock(x + 6, y + 2, z + 6, Block1, 0, 3);
		world.setBlock(x + 7, y + 2, z + 6, Block1, 0, 3);
		world.setBlock(x + 8, y + 2, z + 6, Blocks.vine, 0, 3);
		world.setBlock(x + 9, y + 2, z + 6, Block1, 0, 3);
		world.setBlock(x + 10, y + 2, z + 6, Block1, 0, 3);
		world.setBlock(x + 11, y + 2, z + 6, Block1, 0, 3);
		world.setBlock(x + 0, y + 2, z + 7, Block1, 0, 3);
		world.setBlock(x + 1, y + 2, z + 7, Block1, 0, 3);
		world.setBlock(x + 2, y + 2, z + 7, Block1, 0, 3);
		world.setBlock(x + 3, y + 2, z + 7, Block1, 0, 3);
		world.setBlock(x + 4, y + 2, z + 7, Block1, 0, 3);
		world.setBlock(x + 5, y + 2, z + 7, Block2, 0, 3);
		world.setBlock(x + 6, y + 2, z + 7, Block3, 0, 3);
		world.setBlock(x + 7, y + 2, z + 7, Block1, 0, 3);
		world.setBlock(x + 8, y + 2, z + 7, Block1, 0, 3);
		world.setBlock(x + 9, y + 2, z + 7, Block1, 0, 3);
		world.setBlock(x + 10, y + 2, z + 7, Block3, 0, 3);
		world.setBlock(x + 11, y + 2, z + 7, Block3, 0, 3);
		world.setBlock(x + 0, y + 2, z + 8, Block1, 0, 3);
		world.setBlock(x + 1, y + 2, z + 8, Block1, 0, 3);
		world.setBlock(x + 2, y + 2, z + 8, Block1, 0, 3);
		world.setBlock(x + 3, y + 2, z + 8, Block2, 0, 3);
		world.setBlock(x + 4, y + 2, z + 8, Block3, 0, 3);
		world.setBlock(x + 5, y + 2, z + 8, Block2, 0, 3);
		world.setBlock(x + 6, y + 2, z + 8, Blocks.netherrack, 0, 3);
		world.setBlock(x + 7, y + 2, z + 8, Blocks.netherrack, 0, 3);
		world.setBlock(x + 8, y + 2, z + 8, Block3, 0, 3);
		world.setBlock(x + 9, y + 2, z + 8, Block3, 0, 3);
		world.setBlock(x + 10, y + 2, z + 8, Block2, 0, 3);
		world.setBlock(x + 11, y + 2, z + 8, Block1, 0, 3);
		world.setBlock(x + 0, y + 2, z + 9, Block1, 0, 3);
		world.setBlock(x + 1, y + 2, z + 9, Block1, 0, 3);
		world.setBlock(x + 2, y + 2, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 2, z + 9, Block1, 0, 3);
		world.setBlock(x + 4, y + 2, z + 9, Blocks.netherrack, 0, 3);
		world.setBlock(x + 5, y + 2, z + 9, Block2, 0, 3);
		world.setBlock(x + 6, y + 2, z + 9, Block3, 0, 3);
		world.setBlock(x + 7, y + 2, z + 9, Blocks.netherrack, 0, 3);
		world.setBlock(x + 8, y + 2, z + 9, Block3, 0, 3);
		world.setBlock(x + 9, y + 2, z + 9, Block3, 0, 3);
		world.setBlock(x + 10, y + 2, z + 9, Block3, 0, 3);
		world.setBlock(x + 11, y + 2, z + 9, Block1, 0, 3);
		world.setBlock(x + 0, y + 2, z + 10, Block1, 0, 3);
		world.setBlock(x + 1, y + 2, z + 10, Block1, 0, 3);
		world.setBlock(x + 2, y + 2, z + 10, Block1, 0, 3);
		world.setBlock(x + 3, y + 2, z + 10, Blocks.netherrack, 0, 3);
		world.setBlock(x + 4, y + 2, z + 10, Blocks.netherrack, 0, 3);
		world.setBlock(x + 5, y + 2, z + 10, Blocks.netherrack, 0, 3);
		world.setBlock(x + 6, y + 2, z + 10, Block2, 0, 3);
		world.setBlock(x + 7, y + 2, z + 10, Block2, 0, 3);
		world.setBlock(x + 8, y + 2, z + 10, Block3, 0, 3);
		world.setBlock(x + 9, y + 2, z + 10, Block3, 0, 3);
		world.setBlock(x + 10, y + 2, z + 10, Blocks.netherrack, 0, 3);
		world.setBlock(x + 11, y + 2, z + 10, Block1, 0, 3);
		world.setBlock(x + 0, y + 2, z + 11, Block1, 0, 3);
		world.setBlock(x + 1, y + 2, z + 11, Block1, 0, 3);
		world.setBlock(x + 2, y + 2, z + 11, Block1, 0, 3);
		world.setBlock(x + 3, y + 2, z + 11, Block1, 0, 3);
		world.setBlock(x + 4, y + 2, z + 11, Blocks.netherrack, 0, 3);
		world.setBlock(x + 5, y + 2, z + 11, Blocks.netherrack, 0, 3);
		world.setBlock(x + 6, y + 2, z + 11, Block3, 0, 3);
		world.setBlock(x + 7, y + 2, z + 11, Blocks.netherrack, 0, 3);
		world.setBlock(x + 8, y + 2, z + 11, Block3, 0, 3);
		world.setBlock(x + 9, y + 2, z + 11, Blocks.netherrack, 0, 3);
		world.setBlock(x + 10, y + 2, z + 11, Blocks.netherrack, 0, 3);
		world.setBlock(x + 11, y + 2, z + 11, Block1, 0, 3);
		world.setBlock(x + 0, y + 2, z + 12, Block1, 0, 3);
		world.setBlock(x + 1, y + 2, z + 12, Block1, 0, 3);
		world.setBlock(x + 2, y + 2, z + 12, Block1, 0, 3);
		world.setBlock(x + 3, y + 2, z + 12, Block1, 0, 3);
		world.setBlock(x + 4, y + 2, z + 12, Block1, 0, 3);
		world.setBlock(x + 5, y + 2, z + 12, Block1, 0, 3);
		world.setBlock(x + 6, y + 2, z + 12, Blocks.netherrack, 0, 3);
		world.setBlock(x + 7, y + 2, z + 12, Block1, 0, 3);
		world.setBlock(x + 8, y + 2, z + 12, Blocks.netherrack, 0, 3);
		world.setBlock(x + 9, y + 2, z + 12, Block1, 0, 3);
		world.setBlock(x + 10, y + 2, z + 12, Block1, 0, 3);
		world.setBlock(x + 11, y + 2, z + 12, Block1, 0, 3);
		world.setBlock(x + 0, y + 2, z + 13, Block1, 0, 3);
		world.setBlock(x + 1, y + 2, z + 13, Block1, 0, 3);
		world.setBlock(x + 2, y + 2, z + 13, Block1, 0, 3);
		world.setBlock(x + 3, y + 2, z + 13, Block1, 0, 3);
		world.setBlock(x + 4, y + 2, z + 13, Block1, 0, 3);
		world.setBlock(x + 5, y + 2, z + 13, Block1, 0, 3);
		world.setBlock(x + 6, y + 2, z + 13, Block1, 0, 3);
		world.setBlock(x + 7, y + 2, z + 13, Block1, 0, 3);
		world.setBlock(x + 8, y + 2, z + 13, Block1, 0, 3);
		world.setBlock(x + 9, y + 2, z + 13, Block1, 0, 3);
		world.setBlock(x + 10, y + 2, z + 13, Block1, 0, 3);
		world.setBlock(x + 11, y + 2, z + 13, Block1, 0, 3);
		world.setBlock(x + 1, y + 2, z + 14, Block1, 0, 3);
		world.setBlock(x + 2, y + 2, z + 14, Block1, 0, 3);
		world.setBlock(x + 3, y + 2, z + 14, Block1, 0, 3);
		world.setBlock(x + 4, y + 2, z + 14, Block1, 0, 3);
		world.setBlock(x + 5, y + 2, z + 14, Block1, 0, 3);
		world.setBlock(x + 6, y + 2, z + 14, Block1, 0, 3);
		world.setBlock(x + 7, y + 2, z + 14, Block1, 0, 3);
		world.setBlock(x + 8, y + 2, z + 14, Block1, 0, 3);
		world.setBlock(x + 9, y + 2, z + 14, Block1, 0, 3);
		world.setBlock(x + 10, y + 2, z + 14, Block1, 0, 3);
		world.setBlock(x + 11, y + 2, z + 14, Block1, 0, 3);
		world.setBlock(x + 0, y + 3, z + 4, Block1, 0, 3);
		world.setBlock(x + 1, y + 3, z + 4, Block1, 0, 3);
		world.setBlock(x + 2, y + 3, z + 4, Block1, 0, 3);
		world.setBlock(x + 3, y + 3, z + 4, Block1, 0, 3);
		world.setBlock(x + 4, y + 3, z + 4, Block1, 0, 3);
		world.setBlock(x + 5, y + 3, z + 4, Block1, 0, 3);
		world.setBlock(x + 6, y + 3, z + 4, Block1, 0, 3);
		world.setBlock(x + 7, y + 3, z + 4, Block1, 0, 3);
		world.setBlock(x + 8, y + 3, z + 4, Block1, 0, 3);
		world.setBlock(x + 9, y + 3, z + 4, Block1, 0, 3);
		world.setBlock(x + 10, y + 3, z + 4, Block1, 0, 3);
		world.setBlock(x + 11, y + 3, z + 4, Block1, 0, 3);
		world.setBlock(x + 12, y + 3, z + 4, Block1, 0, 3);
		world.setBlock(x + 0, y + 3, z + 5, Block1, 0, 3);
		world.setBlock(x + 1, y + 3, z + 5, Blocks.reeds, 0, 3);
		world.setBlock(x + 2, y + 3, z + 5, Blocks.waterlily, 0, 3);
		world.setBlock(x + 3, y + 3, z + 5, Blocks.waterlily, 0, 3);
		world.setBlock(x + 4, y + 3, z + 5, Blocks.waterlily, 0, 3);
		world.setBlock(x + 5, y + 3, z + 5, Blocks.chest, 3, 3);
		world.setBlock(x + 6, y + 3, z + 5, Blocks.chest, 3, 3);
		world.setBlock(x + 7, y + 3, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 3, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 12, y + 3, z + 5, Block1, 0, 3);
		world.setBlock(x + 0, y + 3, z + 6, Block1, 0, 3);
		world.setBlock(x + 1, y + 3, z + 6, ModBlocks.red_cable, 0, 3);
		world.setBlock(x + 2, y + 3, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 3, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 3, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 3, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 3, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 3, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 3, z + 6, Blocks.vine, 0, 3);
		world.setBlock(x + 9, y + 3, z + 6, Blocks.stone, 0, 3);
		world.setBlock(x + 11, y + 3, z + 6, Blocks.stone, 0, 3);
		world.setBlock(x + 12, y + 3, z + 6, Block1, 0, 3);
		world.setBlock(x + 0, y + 3, z + 7, Block1, 0, 3);
		world.setBlock(x + 1, y + 3, z + 7, Blocks.nether_wart, 5, 3);
		world.setBlock(x + 2, y + 3, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 3, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 3, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 3, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 3, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 3, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 3, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 10, y + 3, z + 7, Blocks.ender_chest, 2, 3);
		world.setBlock(x + 12, y + 3, z + 7, Block1, 0, 3);
		world.setBlock(x + 0, y + 3, z + 8, Block1, 0, 3);
		world.setBlock(x + 1, y + 3, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 3, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 3, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 3, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 3, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 3, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 3, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 3, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 9, y + 3, z + 8, Blocks.stone, 0, 3);
		world.setBlock(x + 11, y + 3, z + 8, Blocks.stone, 0, 3);
		world.setBlock(x + 12, y + 3, z + 8, Block1, 0, 3);
		world.setBlock(x + 0, y + 3, z + 9, Block1, 0, 3);
		world.setBlock(x + 1, y + 3, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 3, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 3, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 3, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 3, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 3, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 3, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 3, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 12, y + 3, z + 9, Block1, 0, 3);
		world.setBlock(x + 0, y + 3, z + 10, Block1, 0, 3);
		world.setBlock(x + 1, y + 3, z + 10, ModBlocks.machine_shredder, 0, 3);
		world.setBlock(x + 2, y + 3, z + 10, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 3, z + 10, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 3, z + 10, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 3, z + 10, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 3, z + 10, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 3, z + 10, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 3, z + 10, ModBlocks.crashed_balefire, 5, 3);
		world.setBlock(x + 9, y + 3, z + 10, Blocks.air, 0, 3);
		world.setBlock(x + 10, y + 3, z + 10, Blocks.air, 0, 3);
		world.setBlock(x + 11, y + 3, z + 10, Blocks.air, 0, 3);
		world.setBlock(x + 12, y + 3, z + 10, Block1, 0, 3);
		world.setBlock(x + 0, y + 3, z + 11, Block1, 0, 3);
		world.setBlock(x + 1, y + 3, z + 11, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 3, z + 11, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 3, z + 11, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 3, z + 11, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 3, z + 11, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 3, z + 11, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 3, z + 11, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 3, z + 11, Blocks.air, 0, 3);
		world.setBlock(x + 9, y + 3, z + 11, Blocks.air, 0, 3);
		world.setBlock(x + 10, y + 3, z + 11, Blocks.air, 0, 3);
		world.setBlock(x + 11, y + 3, z + 11, Blocks.air, 0, 3);
		world.setBlock(x + 12, y + 3, z + 11, Block1, 0, 3);
		world.setBlock(x + 0, y + 3, z + 12, Block1, 0, 3);
		world.setBlock(x + 1, y + 3, z + 12, Blocks.web, 0, 3);
		world.setBlock(x + 2, y + 3, z + 12, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 3, z + 12, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 3, z + 12, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 3, z + 12, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 3, z + 12, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 3, z + 12, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 3, z + 12, Blocks.air, 0, 3);
		world.setBlock(x + 9, y + 3, z + 12, Blocks.air, 0, 3);
		world.setBlock(x + 11, y + 3, z + 12, Block1, 0, 3);
		world.setBlock(x + 12, y + 3, z + 12, Block1, 0, 3);
		world.setBlock(x + 0, y + 3, z + 13, Block1, 0, 3);
		world.setBlock(x + 1, y + 3, z + 13, Blocks.web, 0, 3);
		world.setBlock(x + 2, y + 3, z + 13, Blocks.web, 0, 3);
		world.setBlock(x + 3, y + 3, z + 13, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 3, z + 13, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 3, z + 13, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 3, z + 13, Blocks.web, 0, 3);
		world.setBlock(x + 7, y + 3, z + 13, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 3, z + 13, Blocks.web, 0, 3);
		world.setBlock(x + 9, y + 3, z + 13, Blocks.web, 0, 3);
		world.setBlock(x + 10, y + 3, z + 13, Block1, 0, 3);
		world.setBlock(x + 11, y + 3, z + 13, Block1, 0, 3);
		world.setBlock(x + 12, y + 3, z + 13, Block1, 0, 3);
		world.setBlock(x + 1, y + 3, z + 14, Block1, 0, 3);
		world.setBlock(x + 2, y + 3, z + 14, Block1, 0, 3);
		world.setBlock(x + 3, y + 3, z + 14, Block1, 0, 3);
		world.setBlock(x + 4, y + 3, z + 14, Block1, 0, 3);
		world.setBlock(x + 5, y + 3, z + 14, Block1, 0, 3);
		world.setBlock(x + 6, y + 3, z + 14, Block1, 0, 3);
		world.setBlock(x + 7, y + 3, z + 14, Block1, 0, 3);
		world.setBlock(x + 8, y + 3, z + 14, Block1, 0, 3);
		world.setBlock(x + 9, y + 3, z + 14, Block1, 0, 3);
		world.setBlock(x + 10, y + 3, z + 14, Block1, 0, 3);
		world.setBlock(x + 11, y + 3, z + 14, Block1, 0, 3);
		world.setBlock(x + 0, y + 4, z + 4, Block1, 0, 3);
		world.setBlock(x + 1, y + 4, z + 4, Block1, 0, 3);
		world.setBlock(x + 2, y + 4, z + 4, Block1, 0, 3);
		world.setBlock(x + 3, y + 4, z + 4, Block1, 0, 3);
		world.setBlock(x + 4, y + 4, z + 4, Block1, 0, 3);
		world.setBlock(x + 5, y + 4, z + 4, Block1, 0, 3);
		world.setBlock(x + 6, y + 4, z + 4, Block1, 0, 3);
		world.setBlock(x + 7, y + 4, z + 4, Block1, 0, 3);
		world.setBlock(x + 8, y + 4, z + 4, Block1, 0, 3);
		world.setBlock(x + 9, y + 4, z + 4, Block1, 0, 3);
		world.setBlock(x + 10, y + 4, z + 4, Block1, 0, 3);
		world.setBlock(x + 11, y + 4, z + 4, Block1, 0, 3);
		world.setBlock(x + 12, y + 4, z + 4, Block1, 0, 3);
		world.setBlock(x + 0, y + 4, z + 5, Block1, 0, 3);
		world.setBlock(x + 2, y + 4, z + 5, Blocks.waterlily, 0, 3);
		world.setBlock(x + 3, y + 4, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 4, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 4, z + 5, Blocks.chest, 3, 3);
		world.setBlock(x + 6, y + 4, z + 5, Blocks.chest, 3, 3);
		world.setBlock(x + 7, y + 4, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 4, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 12, y + 4, z + 5, Block1, 0, 3);
		world.setBlock(x + 0, y + 4, z + 6, Block1, 0, 3);
		world.setBlock(x + 1, y + 4, z + 6, ModBlocks.red_cable, 0, 3);
		world.setBlock(x + 2, y + 4, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 4, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 4, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 4, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 4, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 4, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 4, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 12, y + 4, z + 6, Block1, 0, 3);
		world.setBlock(x + 0, y + 4, z + 7, Block1, 0, 3);
		world.setBlock(x + 1, y + 4, z + 7, Blocks.nether_wart, 5, 3);
		world.setBlock(x + 2, y + 4, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 4, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 4, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 4, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 4, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 4, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 4, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 12, y + 4, z + 7, Block1, 0, 3);
		world.setBlock(x + 0, y + 4, z + 8, Block1, 0, 3);
		world.setBlock(x + 1, y + 4, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 4, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 4, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 4, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 4, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 4, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 4, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 4, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 12, y + 4, z + 8, Block1, 0, 3);
		world.setBlock(x + 0, y + 4, z + 9, Block1, 0, 3);
		world.setBlock(x + 1, y + 4, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 4, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 4, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 4, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 4, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 4, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 4, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 4, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 12, y + 4, z + 9, Block1, 0, 3);
		world.setBlock(x + 0, y + 4, z + 10, Block1, 0, 3);
		world.setBlock(x + 1, y + 4, z + 10, ModBlocks.red_cable, 0, 3);
		world.setBlock(x + 2, y + 4, z + 10, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 4, z + 10, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 4, z + 10, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 4, z + 10, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 4, z + 10, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 4, z + 10, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 4, z + 10, Blocks.air, 0, 3);
		world.setBlock(x + 9, y + 4, z + 10, Blocks.air, 0, 3);
		world.setBlock(x + 10, y + 4, z + 10, Blocks.air, 0, 3);
		world.setBlock(x + 11, y + 4, z + 10, Blocks.air, 0, 3);
		world.setBlock(x + 12, y + 4, z + 10, Block1, 0, 3);
		world.setBlock(x + 0, y + 4, z + 11, Block1, 0, 3);
		world.setBlock(x + 1, y + 4, z + 11, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 4, z + 11, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 4, z + 11, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 4, z + 11, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 4, z + 11, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 4, z + 11, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 4, z + 11, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 4, z + 11, Blocks.air, 0, 3);
		world.setBlock(x + 9, y + 4, z + 11, Blocks.air, 0, 3);
		world.setBlock(x + 10, y + 4, z + 11, Blocks.air, 0, 3);
		world.setBlock(x + 11, y + 4, z + 11, Blocks.air, 0, 3);
		world.setBlock(x + 12, y + 4, z + 11, Block1, 0, 3);
		world.setBlock(x + 0, y + 4, z + 12, Block1, 0, 3);
		world.setBlock(x + 1, y + 4, z + 12, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 4, z + 12, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 4, z + 12, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 4, z + 12, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 4, z + 12, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 4, z + 12, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 4, z + 12, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 4, z + 12, Blocks.air, 0, 3);
		world.setBlock(x + 9, y + 4, z + 12, Blocks.air, 0, 3);
		world.setBlock(x + 11, y + 4, z + 12, Block1, 0, 3);
		world.setBlock(x + 12, y + 4, z + 12, Block1, 0, 3);
		world.setBlock(x + 0, y + 4, z + 13, Block1, 0, 3);
		world.setBlock(x + 1, y + 4, z + 13, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 4, z + 13, Blocks.web, 0, 3);
		world.setBlock(x + 3, y + 4, z + 13, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 4, z + 13, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 4, z + 13, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 4, z + 13, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 4, z + 13, Blocks.air, 0, 3);
		world.setBlock(x + 9, y + 4, z + 13, Blocks.web, 0, 3);
		world.setBlock(x + 10, y + 4, z + 13, Block1, 0, 3);
		world.setBlock(x + 11, y + 4, z + 13, Block1, 0, 3);
		world.setBlock(x + 12, y + 4, z + 13, Block1, 0, 3);
		world.setBlock(x + 1, y + 4, z + 14, Block1, 0, 3);
		world.setBlock(x + 2, y + 4, z + 14, Block1, 0, 3);
		world.setBlock(x + 3, y + 4, z + 14, Block1, 0, 3);
		world.setBlock(x + 4, y + 4, z + 14, Block1, 0, 3);
		world.setBlock(x + 5, y + 4, z + 14, Block1, 0, 3);
		world.setBlock(x + 6, y + 4, z + 14, Block1, 0, 3);
		world.setBlock(x + 7, y + 4, z + 14, Block1, 0, 3);
		world.setBlock(x + 8, y + 4, z + 14, Block1, 0, 3);
		world.setBlock(x + 9, y + 4, z + 14, Block1, 0, 3);
		world.setBlock(x + 10, y + 4, z + 14, Block1, 0, 3);
		world.setBlock(x + 11, y + 4, z + 14, Block1, 0, 3);

		new Ruin002().generate_r00(world, rand, x, y, z);
		return true;

	}
}