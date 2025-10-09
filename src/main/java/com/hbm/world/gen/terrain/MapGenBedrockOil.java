package com.hbm.world.gen.terrain;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockDeadPlant.EnumDeadPlantType;
import com.hbm.blocks.generic.BlockNTMFlower.EnumFlowerType;
import com.hbm.world.gen.MapGenBaseMeta;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class MapGenBedrockOil extends MapGenBaseMeta {

	/**
	 * Similar to oil bubbles, but with a few more behaviours, like adding oily dirt
	 * no porous stone don't @ me
	 */

	private final int frequency;

	public Block block = ModBlocks.ore_bedrock_oil;
	public Block replace = Blocks.stone;

	public int spotWidth = 5;
	public int spotCount = 50;
	public boolean addWillows = true;

	public MapGenBedrockOil(int frequency) {
		this.frequency = frequency;
		this.range = 4;
	}

	@Override
	protected void func_151538_a(World world, int offsetX, int offsetZ, int chunkX, int chunkZ, Block[] blocks) {
		if(rand.nextInt(frequency) == frequency - 2) {
			int xCoord = (chunkX - offsetX) * 16 + rand.nextInt(16);
			int zCoord = (chunkZ - offsetZ) * 16 + rand.nextInt(16);

			// Add the bedrock oil spot
			for(int bx = 15; bx >= 0; bx--)
			for(int bz = 15; bz >= 0; bz--)
			for(int y = 0; y < 5; y++) {
				int index = (bx * 16 + bz) * 256 + y;

				if(blocks[index] == replace || blocks[index] == Blocks.bedrock) {
					// x, z are the coordinates relative to the target virtual chunk origin
					int x = xCoord + bx;
					int z = zCoord + bz;

					if(Math.abs(x) < 5 && Math.abs(z) < 5 && Math.abs(x) + Math.abs(y) + Math.abs(z) <= 6) {
						blocks[index] = block;
					}
				}
			}

			int deadMetaCount = EnumDeadPlantType.values().length;

			// Add oil spot damage
			for(int i = 0; i < spotCount; i++) {
				int rx = (int)(rand.nextGaussian() * spotWidth) - xCoord;
				int rz = (int)(rand.nextGaussian() * spotWidth) - zCoord;

				if(rx >= 0 && rx < 16 && rz >= 0 && rz < 16) {
					// find ground level
					for(int y = 127; y >= 0; y--) {
						int index = (rx * 16 + rz) * 256 + y;

						if(blocks[index] != null && blocks[index].isOpaqueCube()) {
							for(int oy = 1; oy > -3; oy--) {
								int subIndex = index + oy;

								if(blocks[subIndex] == Blocks.grass || blocks[subIndex] == Blocks.dirt) {
									blocks[subIndex] = rand.nextInt(10) == 0 ? ModBlocks.dirt_oily : ModBlocks.dirt_dead;

									if(addWillows && oy == 0 && rand.nextInt(50) == 0) {
										blocks[subIndex + 1] = ModBlocks.plant_flower;
										metas[subIndex + 1] = (byte)EnumFlowerType.CD0.ordinal();
									}

									// this generation occurs BEFORE decoration, so we have no plants to modify
									// so we'll instead just add some new ones right now
									if(oy == 0 && rand.nextInt(20) == 0) {
										blocks[subIndex + 1] = ModBlocks.plant_dead;
										metas[subIndex + 1] = (byte)rand.nextInt(deadMetaCount);
									}

									break;
								} else if(blocks[subIndex] == Blocks.sand || blocks[subIndex] == ModBlocks.ore_oil_sand) {
									if(metas[subIndex] == 1) {
										blocks[subIndex] = ModBlocks.sand_dirty_red;
									} else {
										blocks[subIndex] = ModBlocks.sand_dirty;
									}
									break;
								} else if(blocks[subIndex] == Blocks.stone) {
									blocks[subIndex] = ModBlocks.stone_cracked;
									break;
								}
							}

							break;
						}
					}
				}
			}
		}
	}


}
