package com.hbm.world.gen.terrain;

import java.util.function.Predicate;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockDeadPlant.EnumDeadPlantType;
import com.hbm.world.gen.MapGenBaseMeta;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

public class MapGenBubble extends MapGenBaseMeta {

	/**
	 * Generates oil bubbles, which are generally wider than a chunk, in a safe + cascadeless manner
	 * Pretty much just an oblate sphere generator (dimensions: 3 x 1 x 3)
	 */

	private final int frequency;
	private int minSize = 8;
	private int maxSize = 64;

	public int minY = 0;
	public int rangeY = 25;

	public boolean fuzzy;

	public Block block;
	public Block replace = Blocks.stone;

	public Predicate<BiomeGenBase> canSpawn;

	public MapGenBubble(int frequency) {
		this.frequency = frequency;
	}

	public void setSize(int minSize, int maxSize) {
		this.minSize = minSize;
		this.maxSize = maxSize;

		this.range = (maxSize / 8) + 1;
	}

	@Override
	protected void func_151538_a(World world, int offsetX, int offsetZ, int chunkX, int chunkZ, Block[] blocks) {
		
		int effecFreq = frequency;
		BiomeGenBase biome = world.getBiomeGenForCoords(offsetX * 16, offsetZ * 16);
		if(biome.temperature >= 2 && biome.rainfall < 0.1) effecFreq /= 3;
		if(effecFreq <= 0) effecFreq = 1;
		
		if(rand.nextInt(effecFreq) == effecFreq - 1 && (canSpawn == null || canSpawn.test(biome))) {
			int xCoord = (chunkX - offsetX) * 16 + rand.nextInt(16);
			int zCoord = (chunkZ - offsetZ) * 16 + rand.nextInt(16);

			int yCoord = rand.nextInt(rangeY) + minY;

			double radius = rand.nextInt(maxSize - minSize) + minSize;
			double radiusSqr = (radius * radius) / 2; // original OilBubble implementation divided the square by 2 for some reason

			int yMin = Math.max(1, MathHelper.floor_double(yCoord - radius));
			int yMax = Math.min(127, MathHelper.ceiling_double_int(yCoord + radius));

			for(int bx = 15; bx >= 0; bx--) // bx, bz is the coordinate of the block we're modifying, relative to the generating chunk origin
			for(int bz = 15; bz >= 0; bz--)
			for(int by = yMin; by < yMax; by++) {
				int index = (bx * 16 + bz) * 256 + by;

				if(blocks[index] == replace) {
					// x, z are the coordinates relative to the target virtual chunk origin
					int x = xCoord + bx;
					int z = zCoord + bz;
					int y = yCoord - by;

					double rSqr = x * x + z * z + y * y * 3;
					if(fuzzy) rSqr -= rand.nextDouble() * radiusSqr / 3;
					if(rSqr < radiusSqr) {
						blocks[index] = block;
					}
				}
			}
			
			if(rand.nextInt(1) == 0) {
				addSurfaceSpot(xCoord, zCoord, blocks);
			}
		}
	}
	
	protected void addSurfaceSpot(int xCoord, int zCoord, Block[] blocks) {

		int deadMetaCount = EnumDeadPlantType.values().length;
		int spotCount = 150;
		int spotWidth = 7;

		// Add oil spot damage
		for(int i = 0; i < spotCount; i++) {
			int offX = (int)(rand.nextGaussian() * spotWidth);
			int offZ = (int)(rand.nextGaussian() * spotWidth);
			int rx = offX - xCoord;
			int rz = offZ - zCoord;

			if(rx >= 0 && rx < 16 && rz >= 0 && rz < 16) {
				// find ground level
				for(int y = 127; y >= 0; y--) {
					int index = (rx * 16 + rz) * 256 + y;

					if(blocks[index] != null && blocks[index].isOpaqueCube()) {
						for(int oy = 1; oy > -3; oy--) {
							int subIndex = index + oy;
							
							int distSq = offX * offX + offZ * offZ;
							boolean inner = distSq < (spotWidth / 2) * (spotWidth / 2);

							if(blocks[subIndex] == Blocks.grass || blocks[subIndex] == Blocks.dirt) {
								blocks[subIndex] = inner ? ModBlocks.dirt_oily : ModBlocks.dirt_dead;

								// this generation occurs BEFORE decoration, so we have no plants to modify
								// so we'll instead just add some new ones right now
								if(!inner && oy == 0 && rand.nextInt(20) == 0) {
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
		
		// and now for the hole(tm)
		for(int i = 1; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			int x = dir.offsetX - xCoord;
			int z = dir.offsetZ - zCoord;
			
			if(x >= 0 && x < 16 && z >= 0 && z < 16) {
				int solids = 0;
				
				for(int y = 127; y >= 0; y--) {
					int index = (x * 16 + z) * 256 + y;
					if(blocks[index] == null) continue;
					
					if(blocks[index].getMaterial().isLiquid()) break; 
					
					if(blocks[index].isOpaqueCube()) {
						solids++;
						
						// this approach might break a little when the surface has holes and uneveness but i don't care lmao
						if(i > 1) {
							blocks[index] = ModBlocks.stone_cracked;
							if(solids >= 4) break;
						} else {
							if(solids < 3) blocks[index] = Blocks.air;
							if(solids == 3) blocks[index] = ModBlocks.oil_spill;
							if(solids > 3 && solids < 7) blocks[index] = ModBlocks.stone_cracked;
							if(solids >= 7) break;
						}
					}
				}
			}
		}
	}
}
