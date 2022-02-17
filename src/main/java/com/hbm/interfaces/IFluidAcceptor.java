package com.hbm.interfaces;

import com.hbm.inventory.fluid.FluidType;

public interface IFluidAcceptor extends IFluidContainer {
	
	int getMaxFillForReceive(FluidType type);

	/*
	 * Behavior for overriding when a fluid container has matching types as in and outputs
	 * Only a temporary fix until the fluid system is rewritten
	 */
	default void setFillForTransferIncoming(int fill, FluidType type) {
		this.setFillForTransfer(fill, type);
	}
	default int getFluidFillIncoming(FluidType type) {
		return this.getFluidFill(type);
	}
}
