package com.hbm.interfaces;

import com.hbm.handler.FluidTypeHandler.FluidType;

public interface IFluidAcceptor extends IFluidContainer {
	
	int getMaxFluidFill(FluidType type);

}
