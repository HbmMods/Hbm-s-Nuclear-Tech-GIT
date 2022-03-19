package api.hbm.fluid;

import com.hbm.inventory.fluid.FluidType;

public interface IFluidConductor {

	public IPipeNet getPipeNet(FluidType type);
	
	public void setPipeNet(FluidType type, FluidType network);
}
