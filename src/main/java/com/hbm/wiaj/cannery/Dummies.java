package com.hbm.wiaj.cannery;

import com.hbm.inventory.fluid.FluidType;

import api.hbm.energy.IEnergyConnector;
import api.hbm.fluid.IFluidConnector;
import net.minecraft.tileentity.TileEntity;

public class Dummies {

	public static class JarDummyConnector extends TileEntity implements IEnergyConnector, IFluidConnector {

		@Override public boolean isLoaded() { return false; }
		@Override public long transferFluid(FluidType type, long fluid) { return 0; }
		@Override public long getDemand(FluidType type) { return 0; }
		@Override public long transferPower(long power) { return 0; }
		@Override public long getPower() { return 0; }
		@Override public long getMaxPower() { return 0; }
	}
}
