package com.hbm.world.gen.terrain;

import java.util.function.Predicate;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.MapGenBase;

public class MapGenBubble extends MapGenBase {

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
		if(rand.nextInt(frequency) == frequency - 1 && (canSpawn == null || canSpawn.test(world.getBiomeGenForCoords(offsetX * 16, offsetZ * 16)))) {
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
		}
	}

}
