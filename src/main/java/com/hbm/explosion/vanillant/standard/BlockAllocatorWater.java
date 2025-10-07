package com.hbm.explosion.vanillant.standard;

import java.util.HashSet;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.interfaces.IBlockAllocator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public class BlockAllocatorWater implements IBlockAllocator {

	protected int resolution;

	public BlockAllocatorWater(int resolution) {
		this.resolution = resolution;
	}

	@Override
	public HashSet<ChunkPosition> allocate(ExplosionVNT explosion, World world, double x, double y, double z, float size) {
		HashSet<ChunkPosition> affectedBlocks = new HashSet<>();

		for (int i = 0; i < this.resolution; ++i) {
			for (int j = 0; j < this.resolution; ++j) {
				for (int k = 0; k < this.resolution; ++k) {
					if (i == 0 || i == this.resolution - 1 || j == 0 || j == this.resolution - 1 || k == 0 || k == this.resolution - 1) {
						double d0 = (float) i / ((float) this.resolution - 1.0F) * 2.0F - 1.0F;
						double d1 = (float) j / ((float) this.resolution - 1.0F) * 2.0F - 1.0F;
						double d2 = (float) k / ((float) this.resolution - 1.0F) * 2.0F - 1.0F;
						double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);

						d0 /= d3;
						d1 /= d3;
						d2 /= d3;

						float powerRemaining = size * (0.7F + world.rand.nextFloat() * 0.6F);
						double currentX = x;
						double currentY = y;
						double currentZ = z;

						for (float stepSize = 0.3F; powerRemaining > 0.0F; powerRemaining -= stepSize * 0.75F) {
							int blockX = MathHelper.floor_double(currentX);
							int blockY = MathHelper.floor_double(currentY);
							int blockZ = MathHelper.floor_double(currentZ);

							Block block = world.getBlock(blockX, blockY, blockZ);
							Material material = block.getMaterial();

							// im braindead and copy code 🧃🐱‍👤
							if (material != Material.air && !material.isLiquid()) {
								float blockResistance = explosion.exploder != null ?
									explosion.exploder.func_145772_a(explosion.compat, world, blockX, blockY, blockZ, block) :
									block.getExplosionResistance(null, world, blockX, blockY, blockZ, x, y, z);
								powerRemaining -= (blockResistance + 0.3F) * stepSize;
							}


							if (powerRemaining > 0.0F &&
								(explosion.exploder == null || explosion.exploder.func_145774_a(explosion.compat, world, blockX, blockY, blockZ, block, powerRemaining)) &&
								!material.isLiquid()) {
								affectedBlocks.add(new ChunkPosition(blockX, blockY, blockZ));
							}

							currentX += d0 * (double) stepSize;
							currentY += d1 * (double) stepSize;
							currentZ += d2 * (double) stepSize;
						}
					}
				}
			}
		}

		return affectedBlocks;
	}
}
