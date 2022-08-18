package com.hbm.inventory.fluid.trait;

import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.inventory.fluid.tank.FluidTank;

import net.minecraft.world.World;

public class FT_VentRadiation extends FluidTrait {
	
	float radPerMB = 0;
	
	public FT_VentRadiation(float rad) {
		this.radPerMB = rad;
	}
	
	@Override
	public void onFluidRelease(World world, int x, int y, int z, FluidTank tank, int overflowAmount) {
		ChunkRadiationManager.proxy.incrementRad(world, x, y, z, overflowAmount * radPerMB);
	}
}
