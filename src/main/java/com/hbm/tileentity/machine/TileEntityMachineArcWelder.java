package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardReceiver;

public class TileEntityMachineArcWelder extends TileEntityMachineBase implements IEnergyUser, IFluidStandardReceiver {
	
	public long power;
	public long maxPower;
	
	public FluidTank tank;

	public TileEntityMachineArcWelder() {
		super(8);
	}

	@Override
	public String getName() {
		return "container.machineArcWelder";
	}

	@Override
	public void updateEntity() {
		
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {tank};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tank};
	}
}
