package com.hbm.tileentity.machine;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.inventory.FluidTank;
import com.hbm.tileentity.TileEntityMachineBase;

public class TileEntityCore extends TileEntityMachineBase {
	
	public int field;
	public int heat;
	public int color;
	public FluidTank[] tanks;

	public TileEntityCore() {
		super(3);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(FluidType.DEUTERIUM, 128000, 0);
		tanks[1] = new FluidTank(FluidType.TRITIUM, 128000, 1);
	}

	@Override
	public String getName() {
		return "container.dfc_core";
	}

	@Override
	public void updateEntity() {
		
	}
	
	public long burn(long joules) {
		
		return 0;
	}
	
	public float getFuelEfficiency(FluidType type) {
		
		switch(type) {

		case HYDROGEN:
			return 1.0F;
		case DEUTERIUM:
			return 1.5F;
		case TRITIUM:
			return 1.7F;
		case OXYGEN:
			return 1.2F;
		case ACID:
			return 1.4F;
		case XENON:
			return 1.5F;
		case SAS3:
			return 2.0F;
		case BALEFIRE:
			return 2.5F;
		case AMAT:
			return 2.2F;
		case ASCHRAB:
			return 2.7F;
		default:
			return 0;
		}
	}

}
