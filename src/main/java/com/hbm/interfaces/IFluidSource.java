package com.hbm.interfaces;

import java.util.List;

import com.hbm.inventory.fluid.FluidType;

public interface IFluidSource extends IFluidContainer {
	
	void fillFluidInit(FluidType type);

	void fillFluid(int x, int y, int z, boolean newTact, FluidType type);

	boolean getTact();
	List<IFluidAcceptor> getFluidList(FluidType type);
	void clearFluidList(FluidType type);

}
