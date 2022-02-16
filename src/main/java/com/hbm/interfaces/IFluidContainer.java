package com.hbm.interfaces;

import java.util.List;

import com.hbm.inventory.FluidTank;
import com.hbm.inventory.fluid.FluidType;

public interface IFluidContainer {

	//Args: fill, what the fill should be set to; index, index for array if there are multiple tanks
	public void setFillForSync(int fill, int index);

	//Args: fill: what the fill should be set to; type, what type the tank in question has
	void setFillForTransfer(int fill, FluidType type);
	
	//Args: type, what the type should be set to; index, index for array if there are multiple tanks
	public void setTypeForSync(FluidType type, int index);
	
	//Args: type, what type the tank in question has
	int getFluidFill(FluidType type);

}
