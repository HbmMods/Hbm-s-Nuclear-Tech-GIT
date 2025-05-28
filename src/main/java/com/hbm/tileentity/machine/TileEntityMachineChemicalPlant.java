package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.TileEntityMachineBase;

public class TileEntityMachineChemicalPlant extends TileEntityMachineBase {

	public FluidTank[] inputTanks;
	public FluidTank[] outputTanks;
	
	public long power;
	public long maxPower = 1_000_000;

	public TileEntityMachineChemicalPlant() {
		super(22);
		this.inputTanks = new FluidTank[3];
		this.outputTanks = new FluidTank[3];
		for(int i = 0; i < 3; i++) {
			this.inputTanks[i] = new FluidTank(Fluids.NONE, 24_000);
			this.outputTanks[i] = new FluidTank(Fluids.NONE, 24_000);
		}
	}

	@Override
	public String getName() {
		return "container.machineChemicalPlant";
	}

	@Override
	public void updateEntity() {
		
	}
}
