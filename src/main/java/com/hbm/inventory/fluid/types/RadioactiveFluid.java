package com.hbm.inventory.fluid.types;

import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.render.util.EnumSymbol;

import net.minecraft.world.World;

public class RadioactiveFluid extends FluidType {
	
	float radPerMB = 0;

	public RadioactiveFluid(String name, int color, int p, int f, int r, EnumSymbol symbol) {
		super(name, color, p, f, r, symbol);
	}
	
	public RadioactiveFluid setRadiation(float rad) {
		this.radPerMB = rad;
		return this;
	}
	
	@Override
	public void onFluidRelease(World world, int x, int y, int z, FluidTank tank, int overflowAmount) {
		ChunkRadiationManager.proxy.incrementRad(world, x, y, z, overflowAmount * radPerMB);
	}
}
