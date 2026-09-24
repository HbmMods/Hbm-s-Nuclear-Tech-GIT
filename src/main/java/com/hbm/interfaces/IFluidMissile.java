package com.hbm.interfaces;

import com.hbm.inventory.fluid.FluidType;

public interface IFluidMissile {
	void setFluid(FluidType type, int fill);
	FluidType getFluidType();
	int getFluidFill();
}
