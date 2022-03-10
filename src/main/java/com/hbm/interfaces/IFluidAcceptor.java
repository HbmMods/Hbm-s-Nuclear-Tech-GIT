package com.hbm.interfaces;

import com.hbm.inventory.fluid.FluidType;

public interface IFluidAcceptor extends IFluidContainer {
	
	int getMaxFluidFill(FluidType type);
	
	public default void setFluidFillForReceive(int fill, FluidType type) {
		this.setFluidFill(fill, type);
	}
	
	public default int getFluidFillForReceive(FluidType type) {
		return this.getFluidFill(type);
	}
	
	public default int getMaxFluidFillForReceive(FluidType type) {
		return this.getMaxFluidFill(type);
	}
	
	public default void receiveFluid(int amount, FluidType type) {
		this.setFluidFill(this.getFluidFill(type) + amount, type);
	}
}
