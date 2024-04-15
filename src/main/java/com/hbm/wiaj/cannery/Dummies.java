package com.hbm.wiaj.cannery;

import com.hbm.inventory.fluid.FluidType;

import api.hbm.energymk2.IEnergyConnectorMK2;
import api.hbm.fluid.IFluidConnector;
import net.minecraft.tileentity.TileEntity;

public class Dummies {

	public static class JarDummyConnector extends TileEntity implements IEnergyConnectorMK2, IFluidConnector {

		@Override public boolean isLoaded() { return false; }
		@Override public long transferFluid(FluidType type, int pressure, long fluid) { return 0; }
		@Override public long getDemand(FluidType type, int pressure) { return 0; }
	}
}
