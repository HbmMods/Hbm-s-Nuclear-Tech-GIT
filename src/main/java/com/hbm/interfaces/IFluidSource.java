package com.hbm.interfaces;

import java.util.List;

import com.hbm.inventory.fluid.FluidType;

@Deprecated
public interface IFluidSource extends IFluidContainer {
	
	@Deprecated void fillFluidInit(FluidType type);

	@Deprecated void fillFluid(int x, int y, int z, boolean newTact, FluidType type);

	@Deprecated boolean getTact();
	@Deprecated List<IFluidAcceptor> getFluidList(FluidType type);
	@Deprecated void clearFluidList(FluidType type);

	@Deprecated
	public default void setFluidFillForTransfer(int fill, FluidType type) {
		this.setFluidFill(fill, type);
	}

	@Deprecated
	public default int getFluidFillForTransfer(FluidType type) {
		return this.getFluidFill(type);
	}

	@Deprecated
	public default void transferFluid(int amount, FluidType type) {
		this.setFluidFillForTransfer(this.getFluidFillForTransfer(type) - amount, type);
	}
}
