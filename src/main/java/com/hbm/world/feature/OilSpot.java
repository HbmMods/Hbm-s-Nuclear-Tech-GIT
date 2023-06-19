package com.hbm.world.feature;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockDeadPlant.EnumDeadPlantType;
import com.hbm.blocks.generic.BlockNTMFlower.EnumFlowerType;
import com.hbm.blocks.generic.BlockTallPlant;
import com.hbm.blocks.generic.BlockTallPlant.EnumTallFlower;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class OilSpot {

	public static void generateOilSpot(World world, int x, int z, int width, int count, boolean addWillows) {
		
		for(int i = 0; i < count; i++) {
			int rX = x + (int)(world.rand.nextGaussian() * width);
			int rZ = z + (int)(world.rand.nextGaussian() * width);
			int rY = world.getHeightValue(rX, rZ);
			
			for(int y = rY; y > rY - 4; y--) {

				Block below = world.getBlock(rX, y - 1, rZ);
				Block ground = world.getBlock(rX, y, rZ);
				int meta = world.getBlockMetadata(rX, y, rZ);

				/* don't affect mustard willows */
				if(ground == ModBlocks.plant_flower && (meta == EnumFlowerType.CD0.ordinal() || meta == EnumFlowerType.CD1.ordinal())) continue;
				if(ground == ModBlocks.plant_tall && (meta == EnumTallFlower.CD2.ordinal() || meta == EnumTallFlower.CD3.ordinal() || meta == EnumTallFlower.CD4.ordinal())) continue;
				
				if(below.isNormalCube() && ground != ModBlocks.plant_dead) {
					if(ground instanceof BlockTallGrass) {
						if(world.rand.nextInt(10) == 0) {
							if(world.getBlockMetadata(rX, y + 1, rZ) == 2) {
								world.setBlock(rX, y, rZ, ModBlocks.plant_dead, EnumDeadPlantType.FERN.ordinal(), 3);
							} else {
								world.setBlock(rX, y, rZ, ModBlocks.plant_dead, EnumDeadPlantType.GRASS.ordinal(), 3);
							}
						} else {
							world.setBlock(rX, y, rZ, Blocks.air);
						}
					} else if(ground instanceof BlockFlower) {
						world.setBlock(rX, y, rZ, ModBlocks.plant_dead, EnumDeadPlantType.FLOWER.ordinal(), 3);
					} else if(ground instanceof BlockDoublePlant || ground instanceof BlockTallPlant) {
						world.setBlock(rX, y, rZ, ModBlocks.plant_dead, EnumDeadPlantType.BIGFLOWER.ordinal(), 3);
					} else if(ground instanceof BlockBush) {
						world.setBlock(rX, y, rZ, ModBlocks.plant_dead, EnumDeadPlantType.GENERIC.ordinal(), 3);
					} else if(ground instanceof IPlantable) {
						world.setBlock(rX, y, rZ, ModBlocks.plant_dead, EnumDeadPlantType.GENERIC.ordinal(), 3);
					}
				}
				
				if(ground == Blocks.grass || ground == Blocks.dirt) {
					world.setBlock(rX, y, rZ, world.rand.nextInt(10) == 0 ? ModBlocks.dirt_oily : ModBlocks.dirt_dead);
					
					if(addWillows && world.rand.nextInt(50) == 0) {
						if(ModBlocks.plant_flower.canPlaceBlockAt(world, rX, y + 1, rZ)) {
							world.setBlock(rX, y + 1, rZ, ModBlocks.plant_flower, EnumFlowerType.CD0.ordinal(), 3);
						}
					}
					
					break;
					
				} else if(ground == Blocks.sand || ground == ModBlocks.ore_oil_sand) {
					
					if(world.getBlockMetadata(rX, y, rZ) == 1)
						world.setBlock(rX, y, rZ, ModBlocks.sand_dirty_red);
					else
						world.setBlock(rX, y, rZ, ModBlocks.sand_dirty);
					break;
					
				} else if(ground == Blocks.stone) {
					world.setBlock(rX, y, rZ, ModBlocks.stone_cracked);
					break;
					
				} else if(ground.getMaterial() == Material.leaves && (meta & 8) != 0 && (meta & 4) == 0) {
					world.setBlockToAir(rX, y, rZ);
					break;
				}
			}
		}
	}
}
