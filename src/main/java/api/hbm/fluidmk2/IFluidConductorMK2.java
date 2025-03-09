package api.hbm.fluidmk2;

import com.hbm.inventory.fluid.FluidType;

public interface IFluidConductorMK2 extends IFluidConnectorMK2 {

	public FluidNetMK2 getPipeNet(FluidType type);
	public void setPipeNet(FluidType type, FluidNetMK2 network);
}
