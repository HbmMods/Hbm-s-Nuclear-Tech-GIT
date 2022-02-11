package com.hbm.explosion.vanillant;

import java.util.HashSet;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public class BlockProcessorStandard implements IBlockProcessor {
	
	protected IDropChanceMutator chance;
	
	public BlockProcessorStandard() { }
	
	public BlockProcessorStandard(IDropChanceMutator chance) {
		this.chance = chance;
	}

	@Override
	public void process(ExplosionVNT explosion, World world, double x, double y, double z, HashSet<ChunkPosition> affectedBlocks) {

		Iterator iterator = affectedBlocks.iterator();
		float dropChance = 1.0F / explosion.size;

		while(iterator.hasNext()) {
			ChunkPosition chunkposition = (ChunkPosition) iterator.next();
			int blockX = chunkposition.chunkPosX;
			int blockY = chunkposition.chunkPosY;
			int blockZ = chunkposition.chunkPosZ;
			Block block = world.getBlock(blockX, blockY, blockZ);

			if(block.getMaterial() != Material.air) {
				if(block.canDropFromExplosion(null)) {

					if(chance != null) {
						dropChance = chance.mutateDropChance(explosion, block, blockX, blockY, blockZ, dropChance);
					}
					
					block.dropBlockAsItemWithChance(world, blockX, blockY, blockZ, world.getBlockMetadata(blockX, blockY, blockZ), dropChance, 0);
				}

				block.onBlockExploded(world, blockX, blockY, blockZ, null);
			}
		}
	}

	public BlockProcessorStandard setNoDrop() {
		this.chance = new DropChanceNever();
		return this;
	}
	public BlockProcessorStandard setAllDrop() {
		this.chance = new DropChanceAlways();
		return this;
	}
}
