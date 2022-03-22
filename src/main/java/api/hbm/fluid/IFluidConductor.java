package api.hbm.fluid;

import com.hbm.inventory.fluid.FluidType;

public interface IFluidConductor extends IFluidConnector {

	public IPipeNet getPipeNet(FluidType type);
	
	public void setPipeNet(FluidType type, IPipeNet network);
}
