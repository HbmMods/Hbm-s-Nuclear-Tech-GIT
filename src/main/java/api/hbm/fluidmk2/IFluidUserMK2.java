package api.hbm.fluidmk2;

import com.hbm.inventory.fluid.tank.FluidTank;

import api.hbm.tile.ILoadedTile;

public interface IFluidUserMK2 extends IFluidConnectorMK2, ILoadedTile {
	
	public static final int HIGHEST_VALID_PRESSURE = 5;
	public static final int[] DEFAULT_PRESSURE_RANGE = new int[] {0, 0};
	
	public static final boolean particleDebug = false;
	
	public FluidTank[] getAllTanks();
}
