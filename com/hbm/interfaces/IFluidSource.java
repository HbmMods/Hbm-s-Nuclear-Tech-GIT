package com.hbm.interfaces;

import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;

public interface IFluidSource {
	
	void fillFluidInit(FluidType type);

	void fillFluid(int x, int y, int z, boolean newTact, FluidType type);

	boolean getTact();
	int getFluidFill(FluidType type);
	void setFluidFill(int i, FluidType type);
	List<IFluidAcceptor> getFluidList();
	void clearFluidList();

}
