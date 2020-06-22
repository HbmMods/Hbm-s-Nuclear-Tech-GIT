package com.hbm.inventory;

import com.hbm.handler.FluidTypeHandler.FluidType;

public class FluidStack {
	
	public int fill;
	public FluidType type;
	
	public FluidStack(int fill, FluidType type) {
		this.fill = fill;
		this.type = type;
	}

}
