package com.hbm.blocks.bomb;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public abstract class TurretBase extends BlockContainer {
	
	Random rand = new Random();

	public TurretBase(Material mat) {
		super(mat);
	}
	
	@Override
	public int getRenderType(){
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	public abstract void executeHoldAction(World world, int i, double yaw, double pitch, int x, int y, int z);
	public abstract void executeReleaseAction(World world, int i, double yaw, double pitch, int x, int y, int z);

}
