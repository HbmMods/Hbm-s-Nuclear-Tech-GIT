package com.hbm.interfaces;

import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidTypeTheOldOne;

public interface IFluidSource extends IFluidContainer {
	
	void fillFluidInit(FluidTypeTheOldOne type);

	void fillFluid(int x, int y, int z, boolean newTact, FluidTypeTheOldOne type);

	boolean getTact();
	List<IFluidAcceptor> getFluidList(FluidTypeTheOldOne type);
	void clearFluidList(FluidTypeTheOldOne type);

}
