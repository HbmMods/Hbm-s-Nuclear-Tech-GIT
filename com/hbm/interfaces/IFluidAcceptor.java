package com.hbm.interfaces;

import com.hbm.handler.FluidTypeHandler.FluidType;

public interface IFluidAcceptor {
	
	void setFluidFill(int i, FluidType type);
	
	int getFluidFill(FluidType type);
	
	int getMaxFluidFill(FluidType type);

}
