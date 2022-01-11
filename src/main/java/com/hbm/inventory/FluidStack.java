package com.hbm.inventory;

import com.hbm.handler.FluidTypeHandler.FluidTypeTheOldOne;

public class FluidStack {
	
	public int fill;
	public FluidTypeTheOldOne type;
	
	public FluidStack(int fill, FluidTypeTheOldOne type) {
		this.fill = fill;
		this.type = type;
	}

}
