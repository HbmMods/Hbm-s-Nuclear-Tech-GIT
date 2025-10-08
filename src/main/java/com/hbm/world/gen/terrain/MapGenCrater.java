package com.hbm.world.gen.terrain;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.MapGenBase;

public class MapGenCrater extends MapGenBase {

	private int frequency = 100;
	private int minSize = 8;
	private int maxSize = 64;

	public Block regolith;
	public Block rock;

	public BiomeGenBase targetBiome;

	public MapGenCrater(int frequency) {
		this.frequency = frequency;
	}

	public void setSize(int minSize, int maxSize) {
		this.minSize = minSize;
		this.maxSize = maxSize;

		this.range = (maxSize / 8) + 1;
	}

	private double depthFunc(double x, double rad, double depth) {
		return -Math.pow(x, 2) / Math.pow(rad, 2) * depth + depth;
	}

	// This function is looped over from -this.range to +this.range on both XZ axes.
	@Override
	protected void func_151538_a(World world, int offsetX, int offsetZ, int chunkX, int chunkZ, Block[] blocks) {
		if(rand.nextInt(frequency) == 0 && (targetBiome == null || targetBiome == world.getBiomeGenForCoords(offsetX * 16, offsetZ * 16))) {
			int xCoord = -offsetX + chunkX;
			int zCoord = -offsetZ + chunkZ;

			double radius = rand.nextInt(maxSize - minSize) + minSize;
			double depth = radius * 0.35D;

			for(int bx = 15; bx >= 0; bx--) { // bx, bz is the coordinate of the block we're modifying, relative to the generating chunk origin
				for(int bz = 15; bz >= 0; bz--) {
					for(int y = 127; y >= 0; y--) {
						int index = (bx * 16 + bz) * 256 + y;

						if(blocks[index] != null && (blocks[index].isOpaqueCube() || blocks[index].getMaterial().isLiquid())) {
							// x, z are the coordinates relative to the target virtual chunk origin
							int x = xCoord * 16 + bx;
							int z = zCoord * 16 + bz;

							// y is at the current height now
							double r = Math.sqrt(x * x + z * z);

							if(r - rand.nextInt(3) <= radius) {
								// Carve out to intended depth
								int dep = (int)MathHelper.clamp_double(depthFunc(r, radius, depth), 0, y - 1);
								for(int i = 0; i < dep; i++) {
									blocks[index - i] = null;
								}

								index -= dep;
								y -= dep;

								dep = Math.min(3, y - 1);

								// Fill back in
								if(r + rand.nextInt(3) <= radius / 3D) {
									for(int i = 0; i < dep; i++) {
										blocks[index - i] = regolith;
									}
								} else {
									for(int i = 0; i < dep; i++) {
										blocks[index - i] = rock;
									}
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
