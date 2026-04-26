package com.hbm.tileentity.network.pneumatic;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.fluidmk2.IFluidStandardReceiverMK2;

public class TileEntityPneumoStorageClutter extends TileEntityMachineBase implements IFluidStandardReceiverMK2 {
	
	public FluidTank compair;

	public TileEntityPneumoStorageClutter() {
		super(6 * 9);
		this.compair = new FluidTank(Fluids.AIR, 4_000).withPressure(1);
	}

	@Override
	public String getName() {
		return "container.pneumoStorageClutter";
	}

	@Override
	public void updateEntity() {
		
	}

	@Override public FluidTank[] getAllTanks() { return new FluidTank[] {compair}; }
	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {compair}; }
}
