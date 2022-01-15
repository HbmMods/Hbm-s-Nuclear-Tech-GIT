package com.hbm.interfaces;

import com.hbm.inventory.fluid.FluidType;

public interface IFluidAcceptor extends IFluidContainer {
	
	int getMaxFluidFill(FluidType type);

}
