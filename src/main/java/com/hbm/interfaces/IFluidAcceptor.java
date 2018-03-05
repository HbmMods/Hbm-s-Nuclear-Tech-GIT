package com.hbm.interfaces;

import com.hbm.handler.FluidTypeHandler.FluidType;

public interface IFluidAcceptor {
	
	void setAFluidFill(int i, FluidType type);
	
	int getAFluidFill(FluidType type);
	
	int getMaxAFluidFill(FluidType type);

}
