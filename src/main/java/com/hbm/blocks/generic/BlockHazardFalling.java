package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.interfaces.IItemHazard;
import com.hbm.modules.ItemHazardModule;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHazardFalling extends BlockFalling implements IItemHazard {
	
	ItemHazardModule module;
	
	private float rad = 0.0F;

	private boolean beaconable = false;

	public BlockHazardFalling() {
		this(Material.sand);
	}

	public BlockHazardFalling(Material mat) {
		super(mat);
		this.module = new ItemHazardModule();
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
	public ItemHazardModule getModule() {
		return module;
	}

	@Override
	public IItemHazard addRadiation(float radiation) {
		this.getModule().addRadiation(radiation);
		this.rad = radiation * 0.1F;
		return this;
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

		if(this.rad > 0)
			world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}
}
