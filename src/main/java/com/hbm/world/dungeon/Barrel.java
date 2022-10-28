//Schematic to java Structure by jajo_11 | inspired by "MITHION'S .SCHEMATIC TO JAVA CONVERTINGTOOL"

package com.hbm.world.dungeon;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.lib.HbmChestContents;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntitySellafield;
import com.hbm.tileentity.machine.storage.TileEntityCrateSteel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDoor;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class Barrel extends WorldGenerator {
	
	protected Block[] GetValidSpawnBlocks() {
		
		return new Block[] {
				Blocks.grass,
				Blocks.dirt,
				Blocks.sand,
				Blocks.stone,
				Blocks.sandstone
			};
	}

	public boolean LocationIsValidSpawn(World world, int x, int y, int z) {

		Block checkBlock = world.getBlock(x, y - 1, z);
		Block blockAbove = world.getBlock(x, y, z);
		Block blockBelow = world.getBlock(x, y - 2, z);

		for (Block i : GetValidSpawnBlocks()) {
			if (blockAbove != Blocks.air) {
				return false;
			}
			if (checkBlock == i) {
				return true;
			} else if (checkBlock == Blocks.snow_layer && blockBelow == i) {
				return true;
			} else if (checkBlock.getMaterial() == Material.plants && blockBelow == i) {
				return true;
			}
		}
		return false;
	}

	public boolean generate(World world, Random rand, int x, int y, int z) {
		int i = rand.nextInt(1);

		if (i == 0) {
			generate_r0(world, rand, x, y, z);
		}

		return true;

	}
	
	Block Block1 = ModBlocks.reinforced_brick;
	Block Block2 = ModBlocks.sellafield_slaked;
	Block Block3 = ModBlocks.brick_concrete;
	Block sellafield = ModBlocks.sellafield;
	Block Block10 = ModBlocks.deco_lead;
	Block Block11 = ModBlocks.reinforced_glass;
	Block Block12 = ModBlocks.toxic_block;

	public boolean generate_r0(World world, Random rand, int x, int y, int z) {
		if (!LocationIsValidSpawn(world, x, y, z) || !LocationIsValidSpawn(world, x + 4, y, z)
				|| !LocationIsValidSpawn(world, x + 4, y, z + 6) || !LocationIsValidSpawn(world, x, y, z + 6)) {
			return false;
		}

		world.setBlock(x + 1, y + -1, z + 0, Block1, 0, 3);
		world.setBlock(x + 2, y + -1, z + 0, Block1, 0, 3);
		world.setBlock(x + 3, y + -1, z + 0, Block1, 0, 3);
		world.setBlock(x + 0, y + -1, z + 1, Block1, 0, 3);
		world.setBlock(x + 1, y + -1, z + 1, Block1, 0, 3);
		world.setBlock(x + 2, y + -1, z + 1, Block1, 0, 3);
		world.setBlock(x + 3, y + -1, z + 1, Block1, 0, 3);
		world.setBlock(x + 4, y + -1, z + 1, Block1, 0, 3);
		world.setBlock(x + 0, y + -1, z + 2, Block1, 0, 3);
		world.setBlock(x + 1, y + -1, z + 2, Block1, 0, 3);
		world.setBlock(x + 2, y + -1, z + 2, Block1, 0, 3);
		world.setBlock(x + 3, y + -1, z + 2, Block1, 0, 3);
		world.setBlock(x + 4, y + -1, z + 2, Block1, 0, 3);
		world.setBlock(x + 0, y + -1, z + 3, Block1, 0, 3);
		world.setBlock(x + 1, y + -1, z + 3, Block1, 0, 3);
		world.setBlock(x + 2, y + -1, z + 3, Block1, 0, 3);
		world.setBlock(x + 3, y + -1, z + 3, Block1, 0, 3);
		world.setBlock(x + 4, y + -1, z + 3, Block1, 0, 3);
		world.setBlock(x + 1, y + -1, z + 4, Block1, 0, 3);
		world.setBlock(x + 2, y + -1, z + 4, Block1, 0, 3);
		world.setBlock(x + 3, y + -1, z + 4, Block1, 0, 3);
		world.setBlock(x + 1, y + -1, z + 5, Block1, 0, 3);
		world.setBlock(x + 2, y + -1, z + 5, Block1, 0, 3);
		world.setBlock(x + 3, y + -1, z + 5, Block1, 0, 3);
		world.setBlock(x + 1, y + -1, z + 6, Block1, 0, 3);
		world.setBlock(x + 2, y + -1, z + 6, Block1, 0, 3);
		world.setBlock(x + 3, y + -1, z + 6, Block1, 0, 3);
		world.setBlock(x + 1, y + 0, z + 0, Block2, 0, 3);
		world.setBlock(x + 2, y + 0, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 3, y + 0, z + 0, Block2, 0, 3);
		world.setBlock(x + 0, y + 0, z + 1, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 0, z + 1, sellafield, 3, 3);
		world.setBlock(x + 2, y + 0, z + 1, sellafield, 4, 3);
		world.setBlock(x + 3, y + 0, z + 1, sellafield, 3, 3);
		world.setBlock(x + 4, y + 0, z + 1, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 0, y + 0, z + 2, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 0, z + 2, sellafield, 4, 3);
		world.setBlock(x + 2, y + 0, z + 2, sellafield, 5, 3);
		
		if(world.getTileEntity(x + 2, y + 0, z + 2) instanceof TileEntitySellafield) {
			((TileEntitySellafield)world.getTileEntity(x + 2, y + 0, z + 2)).radius = 2.5;
		}
		
		world.setBlock(x + 3, y + 0, z + 2, sellafield, 4, 3);
		world.setBlock(x + 4, y + 0, z + 2, Block2, 0, 3);
		world.setBlock(x + 0, y + 0, z + 3, Block2, 0, 3);
		world.setBlock(x + 1, y + 0, z + 3, sellafield, 4, 3);
		world.setBlock(x + 2, y + 0, z + 3, sellafield, 3, 3);
		world.setBlock(x + 3, y + 0, z + 3, sellafield, 4, 3);
		world.setBlock(x + 4, y + 0, z + 3, Block2, 0, 3);
		world.setBlock(x + 1, y + 0, z + 4, Block2, 0, 3);
		world.setBlock(x + 2, y + 0, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 3, y + 0, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 1, z + 0, Block2, 0, 3);
		world.setBlock(x + 2, y + 1, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 3, y + 1, z + 0, Block2, 0, 3);
		world.setBlock(x + 0, y + 1, z + 1, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 1, z + 1, sellafield, 2, 3);
		world.setBlock(x + 2, y + 1, z + 1, sellafield, 3, 3);
		world.setBlock(x + 3, y + 1, z + 1, sellafield, 3, 3);
		world.setBlock(x + 4, y + 1, z + 1, Block2, 0, 3);
		world.setBlock(x + 0, y + 1, z + 2, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 1, z + 2, sellafield, 3, 3);
		
		/*world.setBlock(x + 2, y + 1, z + 2, Blocks.chest, 3, 3);

		if(world.getBlock(x + 2, y + 1, z + 2) == Blocks.chest)
		{
			WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.getLoot(3), (TileEntityChest)world.getTileEntity(x + 2, y + 1, z + 2), 16);
		}*/

		world.setBlock(x + 2, y + 1, z + 2, ModBlocks.crate_steel, 0, 3);

		if(world.getBlock(x + 2, y + 1, z + 2) == ModBlocks.crate_steel)
		{
			WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.expensive, (TileEntityCrateSteel)world.getTileEntity(x + 2, y + 1, z + 2), 16);
		}
		
		world.setBlock(x + 3, y + 1, z + 2, sellafield, 3, 3);
		world.setBlock(x + 4, y + 1, z + 2, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 0, y + 1, z + 3, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 1, z + 3, sellafield, 3, 3);
		world.setBlock(x + 2, y + 1, z + 3, sellafield, 2, 3);
		world.setBlock(x + 3, y + 1, z + 3, sellafield, 3, 3);
		world.setBlock(x + 4, y + 1, z + 3, Block2, 0, 3);
		world.setBlock(x + 1, y + 1, z + 4, Block2, 0, 3);
		world.setBlock(x + 2, y + 1, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 3, y + 1, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 2, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 2, y + 2, z + 0, Block2, 0, 3);
		world.setBlock(x + 3, y + 2, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 0, y + 2, z + 1, Block2, 0, 3);
		world.setBlock(x + 1, y + 2, z + 1, sellafield, 1, 3);
		world.setBlock(x + 2, y + 2, z + 1, sellafield, 2, 3);
		world.setBlock(x + 3, y + 2, z + 1, sellafield, 2, 3);
		world.setBlock(x + 4, y + 2, z + 1, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 0, y + 2, z + 2, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 2, z + 2, sellafield, 2, 3);
		world.setBlock(x + 2, y + 2, z + 2, sellafield, 4, 3);
		world.setBlock(x + 3, y + 2, z + 2, sellafield, 2, 3);
		world.setBlock(x + 4, y + 2, z + 2, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 0, y + 2, z + 3, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 2, z + 3, sellafield, 2, 3);
		world.setBlock(x + 2, y + 2, z + 3, sellafield, 1, 3);
		world.setBlock(x + 3, y + 2, z + 3, sellafield, 2, 3);
		world.setBlock(x + 4, y + 2, z + 3, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 2, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 2, y + 2, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 3, y + 2, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 3, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 2, y + 3, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 3, y + 3, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 0, y + 3, z + 1, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 3, z + 1, sellafield, 1, 3);
		world.setBlock(x + 2, y + 3, z + 1, sellafield, 1, 3);
		world.setBlock(x + 3, y + 3, z + 1, sellafield, 1, 3);
		world.setBlock(x + 4, y + 3, z + 1, Block2, 0, 3);
		world.setBlock(x + 0, y + 3, z + 2, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 3, z + 2, sellafield, 1, 3);
		world.setBlock(x + 2, y + 3, z + 2, sellafield, 3, 3);
		world.setBlock(x + 3, y + 3, z + 2, sellafield, 1, 3);
		world.setBlock(x + 4, y + 3, z + 2, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 0, y + 3, z + 3, Block2, 0, 3);
		world.setBlock(x + 1, y + 3, z + 3, sellafield, 1, 3);
		world.setBlock(x + 2, y + 3, z + 3, sellafield, 0, 3);
		world.setBlock(x + 3, y + 3, z + 3, sellafield, 1, 3);
		world.setBlock(x + 4, y + 3, z + 3, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 3, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 2, y + 3, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 3, y + 3, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 4, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 2, y + 4, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 3, y + 4, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 0, y + 4, z + 1, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 4, z + 1, sellafield, 0, 3);
		world.setBlock(x + 2, y + 4, z + 1, sellafield, 1, 3);
		world.setBlock(x + 3, y + 4, z + 1, sellafield, 0, 3);
		world.setBlock(x + 4, y + 4, z + 1, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 0, y + 4, z + 2, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 4, z + 2, sellafield, 0, 3);
		world.setBlock(x + 2, y + 4, z + 2, sellafield, 2, 3);
		world.setBlock(x + 3, y + 4, z + 2, sellafield, 1, 3);
		world.setBlock(x + 4, y + 4, z + 2, Block2, 0, 3);
		world.setBlock(x + 0, y + 4, z + 3, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 4, z + 3, sellafield, 1, 3);
		world.setBlock(x + 2, y + 4, z + 3, sellafield, 0, 3);
		world.setBlock(x + 3, y + 4, z + 3, sellafield, 0, 3);
		world.setBlock(x + 4, y + 4, z + 3, Block2, 0, 3);
		world.setBlock(x + 1, y + 4, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 2, y + 4, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 3, y + 4, z + 4, Block2, 0, 3);
		world.setBlock(x + 1, y + 5, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 2, y + 5, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 3, y + 5, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 0, y + 5, z + 1, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 5, z + 1, sellafield, 0, 3);
		world.setBlock(x + 2, y + 5, z + 1, sellafield, 0, 3);
		world.setBlock(x + 3, y + 5, z + 1, sellafield, 0, 3);
		world.setBlock(x + 4, y + 5, z + 1, Block2, 0, 3);
		world.setBlock(x + 0, y + 5, z + 2, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 5, z + 2, Block12, 0, 3);
		world.setBlock(x + 2, y + 5, z + 2, sellafield, 1, 3);
		world.setBlock(x + 3, y + 5, z + 2, Block12, 0, 3);
		world.setBlock(x + 4, y + 5, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 0, y + 5, z + 3, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 5, z + 3, sellafield, 0, 3);
		world.setBlock(x + 2, y + 5, z + 3, sellafield, 0, 3);
		world.setBlock(x + 3, y + 5, z + 3, Block12, 0, 3);
		world.setBlock(x + 4, y + 5, z + 3, Block2, 0, 3);
		world.setBlock(x + 1, y + 5, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 2, y + 5, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 3, y + 5, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 6, z + 0, Block10, 0, 3);
		world.setBlock(x + 2, y + 6, z + 0, Block10, 0, 3);
		world.setBlock(x + 3, y + 6, z + 0, Block10, 0, 3);
		world.setBlock(x + 0, y + 6, z + 1, Block10, 0, 3);
		world.setBlock(x + 1, y + 6, z + 1, Block12, 0, 3);
		world.setBlock(x + 2, y + 6, z + 1, Block12, 0, 3);
		world.setBlock(x + 3, y + 6, z + 1, Block12, 0, 3);
		world.setBlock(x + 4, y + 6, z + 1, Block10, 0, 3);
		world.setBlock(x + 0, y + 6, z + 2, Block10, 0, 3);
		world.setBlock(x + 1, y + 6, z + 2, Block12, 0, 3);
		world.setBlock(x + 2, y + 6, z + 2, sellafield, 0, 3);
		world.setBlock(x + 3, y + 6, z + 2, Block12, 0, 3);
		world.setBlock(x + 4, y + 6, z + 2, Block10, 0, 3);
		world.setBlock(x + 0, y + 6, z + 3, Block10, 0, 3);
		world.setBlock(x + 1, y + 6, z + 3, Block12, 0, 3);
		world.setBlock(x + 2, y + 6, z + 3, Block12, 0, 3);
		world.setBlock(x + 3, y + 6, z + 3, Block12, 0, 3);
		world.setBlock(x + 4, y + 6, z + 3, Block10, 0, 3);
		world.setBlock(x + 1, y + 6, z + 4, Block10, 0, 3);
		world.setBlock(x + 2, y + 6, z + 4, Block10, 0, 3);
		world.setBlock(x + 3, y + 6, z + 4, Block10, 0, 3);
		world.setBlock(x + 1, y + 7, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 2, y + 7, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 3, y + 7, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 0, y + 7, z + 1, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 7, z + 1, Block12, 0, 3);
		world.setBlock(x + 2, y + 7, z + 1, Block12, 0, 3);
		world.setBlock(x + 3, y + 7, z + 1, Block12, 0, 3);
		world.setBlock(x + 4, y + 7, z + 1, Block2, 0, 3);
		world.setBlock(x + 0, y + 7, z + 2, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 7, z + 2, Block12, 0, 3);
		world.setBlock(x + 2, y + 7, z + 2, Block12, 0, 3);
		world.setBlock(x + 3, y + 7, z + 2, Block12, 0, 3);
		world.setBlock(x + 4, y + 7, z + 2, Block2, 0, 3);
		world.setBlock(x + 0, y + 7, z + 3, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 7, z + 3, Block12, 0, 3);
		world.setBlock(x + 2, y + 7, z + 3, Block12, 0, 3);
		world.setBlock(x + 3, y + 7, z + 3, Block12, 0, 3);
		world.setBlock(x + 4, y + 7, z + 3, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 7, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 2, y + 7, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 3, y + 7, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 8, z + 0, Block10, 0, 3);
		world.setBlock(x + 2, y + 8, z + 0, Block10, 0, 3);
		world.setBlock(x + 3, y + 8, z + 0, Block10, 0, 3);
		world.setBlock(x + 0, y + 8, z + 1, Block10, 0, 3);
		world.setBlock(x + 1, y + 8, z + 1, Block12, 0, 3);
		world.setBlock(x + 2, y + 8, z + 1, Block12, 0, 3);
		world.setBlock(x + 3, y + 8, z + 1, Block12, 0, 3);
		world.setBlock(x + 4, y + 8, z + 1, Block10, 0, 3);
		world.setBlock(x + 0, y + 8, z + 2, Block10, 0, 3);
		world.setBlock(x + 1, y + 8, z + 2, Block12, 0, 3);
		world.setBlock(x + 2, y + 8, z + 2, Block12, 0, 3);
		world.setBlock(x + 3, y + 8, z + 2, Block12, 0, 3);
		world.setBlock(x + 4, y + 8, z + 2, Block10, 0, 3);
		world.setBlock(x + 0, y + 8, z + 3, Block10, 0, 3);
		world.setBlock(x + 1, y + 8, z + 3, Block12, 0, 3);
		world.setBlock(x + 2, y + 8, z + 3, Block12, 0, 3);
		world.setBlock(x + 3, y + 8, z + 3, Block12, 0, 3);
		world.setBlock(x + 4, y + 8, z + 3, Block10, 0, 3);
		world.setBlock(x + 1, y + 8, z + 4, Block10, 0, 3);
		world.setBlock(x + 2, y + 8, z + 4, Block10, 0, 3);
		world.setBlock(x + 3, y + 8, z + 4, Block10, 0, 3);
		world.setBlock(x + 1, y + 9, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 2, y + 9, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 3, y + 9, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 0, y + 9, z + 1, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 4, y + 9, z + 1, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 0, y + 9, z + 2, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 4, y + 9, z + 2, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 0, y + 9, z + 3, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 4, y + 9, z + 3, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 9, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 2, y + 9, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 3, y + 9, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 10, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 2, y + 10, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 3, y + 10, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 0, y + 10, z + 1, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 4, y + 10, z + 1, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 0, y + 10, z + 2, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 4, y + 10, z + 2, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 0, y + 10, z + 3, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 4, y + 10, z + 3, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 10, z + 4, Library.getRandomConcrete(), 0, 3);
		//world.setBlock(x + 2, y + 10, z + 4, Blocks.iron_door, 2, 3);
		world.setBlock(x + 3, y + 10, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 11, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 2, y + 11, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 3, y + 11, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 0, y + 11, z + 1, Block11, 0, 3);
		world.setBlock(x + 0, y + 11, z + 2, Block11, 0, 3);
		world.setBlock(x + 4, y + 11, z + 2, Block11, 0, 3);
		world.setBlock(x + 0, y + 11, z + 3, Block11, 0, 3);
		world.setBlock(x + 4, y + 11, z + 3, Block11, 0, 3);
		world.setBlock(x + 1, y + 11, z + 4, Library.getRandomConcrete(), 0, 3);
		//world.setBlock(x + 2, y + 11, z + 4, Blocks.iron_door, 8, 3);
        ItemDoor.placeDoorBlock(world, x + 2, y + 10, z + 4, 2, Blocks.iron_door);
		world.setBlock(x + 3, y + 11, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 12, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 2, y + 12, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 3, y + 12, z + 0, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 0, y + 12, z + 1, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 4, y + 12, z + 1, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 0, y + 12, z + 2, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 4, y + 12, z + 2, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 0, y + 12, z + 3, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 4, y + 12, z + 3, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 12, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 2, y + 12, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 3, y + 12, z + 4, Library.getRandomConcrete(), 0, 3);
		world.setBlock(x + 1, y + 13, z + 0, Block1, 0, 3);
		world.setBlock(x + 2, y + 13, z + 0, Block1, 0, 3);
		world.setBlock(x + 3, y + 13, z + 0, Block1, 0, 3);
		world.setBlock(x + 0, y + 13, z + 1, Block1, 0, 3);
		world.setBlock(x + 1, y + 13, z + 1, Block10, 0, 3);
		world.setBlock(x + 2, y + 13, z + 1, Block10, 0, 3);
		world.setBlock(x + 3, y + 13, z + 1, Block10, 0, 3);
		world.setBlock(x + 4, y + 13, z + 1, Block1, 0, 3);
		world.setBlock(x + 0, y + 13, z + 2, Block1, 0, 3);
		world.setBlock(x + 1, y + 13, z + 2, Block10, 0, 3);
		world.setBlock(x + 2, y + 13, z + 2, Block10, 0, 3);
		world.setBlock(x + 4, y + 13, z + 2, Block1, 0, 3);
		world.setBlock(x + 0, y + 13, z + 3, Block1, 0, 3);
		world.setBlock(x + 1, y + 13, z + 3, Block10, 0, 3);
		world.setBlock(x + 2, y + 13, z + 3, Block10, 0, 3);
		world.setBlock(x + 1, y + 13, z + 4, Block1, 0, 3);
		world.setBlock(x + 2, y + 13, z + 4, Block1, 0, 3);
		world.setBlock(x + 3, y + 13, z + 4, Block1, 0, 3);

		generate_r02_last(world, rand, x, y, z);
		return true;

	}

	public boolean generate_r02_last(World world, Random rand, int x, int y, int z) {

		world.setBlock(x + 2, y + 0, z + 5, Blocks.ladder, 3, 3);
		world.setBlock(x + 2, y + 1, z + 5, Blocks.ladder, 3, 3);
		world.setBlock(x + 2, y + 2, z + 5, Blocks.ladder, 3, 3);
		world.setBlock(x + 2, y + 3, z + 5, Blocks.ladder, 3, 3);
		world.setBlock(x + 2, y + 4, z + 5, Blocks.ladder, 3, 3);
		world.setBlock(x + 2, y + 5, z + 5, Blocks.ladder, 3, 3);
		world.setBlock(x + 2, y + 6, z + 5, Blocks.ladder, 3, 3);
		world.setBlock(x + 2, y + 7, z + 5, Blocks.ladder, 3, 3);
		world.setBlock(x + 2, y + 8, z + 5, Blocks.ladder, 3, 3);
		world.setBlock(x + 2, y + 9, z + 5, Blocks.ladder, 3, 3);
		
		if(GeneralConfig.enableDebugMode)
			System.out.print("[Debug] Successfully spawned waste tank at " + x + " " + y +" " + z + "\n");
		
		return true;

	}

}