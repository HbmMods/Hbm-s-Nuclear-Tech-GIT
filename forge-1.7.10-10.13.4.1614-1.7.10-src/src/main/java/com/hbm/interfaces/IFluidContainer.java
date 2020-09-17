package com.hbm.interfaces;

import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.inventory.FluidTank;

public interface IFluidContainer {

	//Args: fill, what the fill should be set to; index, index for array if there are multiple tanks
	public void setFillstate(int fill, int index);

	//Args: fill: what the fill should be set to; type, what type the tank in question has
	void setFluidFill(int fill, FluidType type);
	
	//Args: type, what the type should be set to; index, index for array if there are multiple tanks
	public void setType(FluidType type, int index);
	
	public List<FluidTank> getTanks();
	
	//Args: type, what type the tank in question has
	int getFluidFill(FluidType type);

}
