package com.hbm.interfaces;

import com.hbm.handler.FluidTypeHandler.FluidType;

public interface IFluidContainer {

	//Args: fill, what the fill should be set to; index, index for array if there are multiple tanks
	public void setFillstate(int fill, int index);
	
	//Args: type, what the type should be set to; index, index for array if there are multiple tanks
	public void setType(FluidType type, int index);

}
