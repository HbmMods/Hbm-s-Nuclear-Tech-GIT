package com.hbm.dim.mapgen;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.MapGenBase;

public class MapGenTiltedSpires extends MapGenBase {
	
	private int chancePerChunk = 100;
	public int minSize = 6;
	public int maxSize = 16;
	public float minPoint = 0.5F;
	public float maxPoint = 4.0F;
	public float minTilt = 0.2F;
	public float maxTilt = 2.5F;
	public boolean curve = false;
	public int mid = 64;

	public Block regolith;
	public Block rock;

	// Note that the chance is effectively squared, so make it lower than you normally would
	public MapGenTiltedSpires(int chancePerChunk) {
		this.chancePerChunk = chancePerChunk;
		range = 4;
	}

	// This function is looped over from -this.range to +this.range on both XZ axes.
	@Override
	protected void func_151538_a(World world, int offsetX, int offsetZ, int chunkX, int chunkZ, Block[] blocks) {

		if(rand.nextInt(chancePerChunk) == Math.abs(offsetX) % chancePerChunk && rand.nextInt(chancePerChunk) == Math.abs(offsetZ) % chancePerChunk) {

			float coneRadius = rand.nextInt(maxSize - minSize) + minSize;
			float stretch = 1F / (rand.nextFloat() * (maxPoint - minPoint) + minPoint);
			float direction = rand.nextFloat() * (float)Math.PI * 2F;
			float tilt = rand.nextFloat() * (maxTilt - minTilt) + minTilt;

			if(curve) tilt *= 0.01;

			int xCoord = -offsetX + chunkX;
			int zCoord = -offsetZ + chunkZ;


			for(int bx = 15; bx >= 0; bx--) { // bx, bz is the coordinate of the block we're modifying, relative to the generating chunk origin
				for(int bz = 15; bz >= 0; bz--) {
					int d = 0;

					for(int y = mid + 63; y >= 0; y--) {
						int index = (bx * 16 + bz) * 256 + y;

						// Run until the first opaque block
						if(blocks[index] == null || !blocks[index].isOpaqueCube()) {
							// x, z are the coordinates relative to the target virtual chunk origin
							int x = xCoord * 16 + bx;
							int z = zCoord * 16 + bz;
							int oy = y - mid;

							float factor = (float) oy;
							if(curve) factor *= factor;

							x += Math.cos(direction) * factor * tilt;
							z += Math.sin(direction) * factor * tilt;

							double r = Math.sqrt(x * x + z * z);

							if(r + oy * stretch < coneRadius) {
								blocks[index] = rock;
								if(d == 1) blocks[index + 1] = regolith;
								d++;
							} else if(d > 0) {
								break;
							}
						} else {
							break;
						}
					}
				}
			}
		}
	}

}
