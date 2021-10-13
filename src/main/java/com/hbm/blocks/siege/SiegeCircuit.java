package com.hbm.blocks.siege;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class SiegeCircuit extends SiegeBase {

	public SiegeCircuit(Material material) {
		super(material, 2);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		if(!this.solidNeighbors(world, x, y, z)) {
			world.setBlock(x, y, z, ModBlocks.siege_emergency);
		}
	}
}
