package com.hbm.interfaces;

import com.hbm.inventory.fluid.FluidType;

@Deprecated //legacy crap we can't get rid of because some ancient machines still use this for sync
public interface IFluidContainer {

	//Args: fill, what the fill should be set to; index, index for array if there are multiple tanks
	public void setFillForSync(int fill, int index);
	
	//Args: type, what the type should be set to; index, index for array if there are multiple tanks
	public void setTypeForSync(FluidType type, int index);
}
