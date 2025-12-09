package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.handler.radiation.ChunkRadiationManager;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockAbsorberOLD extends Block {

	float absorb = 0;

	public BlockAbsorberOLD(Material mat, float ab) {
		super(mat);
		this.setTickRandomly(true);
		absorb = ab;
	}

	@Override
	public int tickRate(World world) {

		return 10;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		ChunkRadiationManager.proxy.decrementRad(world, x, y, z, absorb);
		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}

	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);

		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}
}

