package com.hbm.inventory;

import com.hbm.inventory.fluid.FluidType;

public class FluidStack {
	
	public int fill;
	public FluidType type;
	
	public FluidStack(int fill, FluidType type) {
		this.fill = fill;
		this.type = type;
	}

}
