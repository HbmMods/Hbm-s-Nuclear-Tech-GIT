package com.hbm.inventory.fluid.trait;

import java.util.List;

import com.hbm.inventory.fluid.tank.FluidTank;

import net.minecraft.world.World;

public abstract class FluidTrait {

	/** Important information that should always be displayed */
	public void addInfo(List<String> info) { }
	/* General names of simple traits which are displayed when holding shift */
	public void addInfoHidden(List<String> info) { }
	
	public void onFluidRelease(World world, int x, int y, int z, FluidTank tank, int overflowAmount) { }
}
