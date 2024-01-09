package com.hbm.explosion.vanillant.standard;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.interfaces.IBlockAllocator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

import java.util.HashSet;

public class BlockAllocatorGlyphidDig implements IBlockAllocator {

	protected double maximum;
	protected int resolution;

	public BlockAllocatorGlyphidDig(double maximum) {
		this(maximum, 16);
	}

	public BlockAllocatorGlyphidDig(double maximum, int resolution) {
		this.resolution = resolution;
		this.maximum = maximum;
	}

	@Override
	public HashSet<ChunkPosition> allocate(ExplosionVNT explosion, World world, double x, double y, double z, float size) {

		HashSet<ChunkPosition> affectedBlocks = new HashSet();

		for(int i = 0; i < this.resolution; ++i) {
			for(int j = 0; j < this.resolution; ++j) {
				for(int k = 0; k < this.resolution; ++k) {
					
					if(i == 0 || i == this.resolution - 1 || j == 0 || j == this.resolution - 1 || k == 0 || k == this.resolution - 1) {
						
						double d0 = (double) ((float) i / ((float) this.resolution - 1.0F) * 2.0F - 1.0F);
						double d1 = (double) ((float) j / ((float) this.resolution - 1.0F) * 2.0F - 1.0F);
						double d2 = (double) ((float) k / ((float) this.resolution - 1.0F) * 2.0F - 1.0F);
						double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
						
						d0 /= d3;
						d1 /= d3;
						d2 /= d3;
						
						double currentX = x;
						double currentY = y;
						double currentZ = z;
						
						double dist = 0;

						for(float stepSize = 0.3F; dist <= explosion.size;) {

							double deltaX = currentX - x;
							double deltaY = currentY - y;
							double deltaZ = currentZ - z;
							dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
							
							int blockX = MathHelper.floor_double(currentX);
							int blockY = MathHelper.floor_double(currentY);
							int blockZ = MathHelper.floor_double(currentZ);
							
							Block block = world.getBlock(blockX, blockY, blockZ);

							if(block.getMaterial() != Material.air) {
								float blockResistance = explosion.exploder != null ? explosion.exploder.func_145772_a(explosion.compat, world, blockX, blockY, blockZ, block) : block.getExplosionResistance(explosion.exploder, world, blockX, blockY, blockZ, x, y, z);
								if(this.maximum < blockResistance || block == ModBlocks.glyphid_spawner) {
									break;
								}
							}

							if(explosion.exploder == null || explosion.exploder.func_145774_a(explosion.compat, world, blockX, blockY, blockZ, block, explosion.size)) {
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
