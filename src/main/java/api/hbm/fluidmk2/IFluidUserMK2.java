package api.hbm.fluidmk2;

import com.hbm.inventory.fluid.tank.FluidTank;

public interface IFluidUserMK2 extends IFluidConnectorMK2 {
	
	public static final boolean particleDebug = false;
	
	public FluidTank[] getAllTanks();
}
