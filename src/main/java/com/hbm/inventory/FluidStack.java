package com.hbm.inventory;

import com.hbm.inventory.fluid.FluidType;

public class FluidStack {

	public FluidType type;
	public int fill;
	public int pressure;
	
	@Deprecated public FluidStack(int fill, FluidType type) { // weird ass format
		this.fill = fill;
		this.type = type;
	}
	
	public FluidStack(FluidType type, int fill) {
		this(type, fill, 0);
	}
	
	public FluidStack(FluidType type, int fill, int pressure) {
		this.fill = fill;
		this.type = type;
		this.pressure = pressure;
	}
}
