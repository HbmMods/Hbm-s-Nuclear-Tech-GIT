package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.TileEntityMachinePolluting;

public class TileEntityMachineRotaryFurnace extends TileEntityMachinePolluting {

	public TileEntityMachineRotaryFurnace() {
		super(0, 50);
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void updateEntity() {
		
	}

	@Override
	public FluidTank[] getAllTanks() {
		return null;
	}

	@Override
	public long transferFluid(FluidType type, int pressure, long fluid) {
		return 0;
	}

	@Override
	public long getDemand(FluidType type, int pressure) {
		return 0;
	}
}
