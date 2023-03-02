package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.handler.radiation.ChunkRadiationManager;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockFallingRad extends BlockFalling {

	private float radIn = 0.0F;
	public BlockFallingRad(Material mat, float rad, float max) {
		super(mat);
		this.setTickRandomly(true);
		radIn = rad;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		super.updateTick(world, x, y, z, rand);
		ChunkRadiationManager.proxy.incrementRad(world, x, y, z, radIn);

		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}

	@Override
	public int tickRate(World world) {
		return 20;
	}

	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}

}
