package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.hazard.HazardRegistry;
import com.hbm.hazard.HazardSystem;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHazardFalling extends BlockFalling {
		
	private float rad = 0.0F;

	private boolean beaconable = false;

	public BlockHazardFalling() {
		this(Material.sand);
	}

	public BlockHazardFalling(Material mat) {
		super(mat);
	}

	public BlockHazardFalling makeBeaconable() {
		this.beaconable  = true;
		return this;
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
		return beaconable;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		if(this.rad > 0) {
			ChunkRadiationManager.proxy.incrementRad(world, x, y, z, rad);
			world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
		}
		
		super.updateTick(world, x, y, z, rand);
	}

	@Override
	public int tickRate(World world) {

		if(this.rad > 0)
			return 20;

		return super.tickRate(world);
	}

	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		
		rad = HazardSystem.getHazardLevelFromStack(new ItemStack(this), HazardRegistry.RADIATION) * 0.1F;
		
		if(this.rad > 0)
			world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}
}
