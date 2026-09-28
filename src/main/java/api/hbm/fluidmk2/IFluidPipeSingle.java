package api.hbm.fluidmk2;

import com.hbm.inventory.fluid.FluidType;

public interface IFluidPipeSingle extends IFluidPipeMK2 {
	
	public FluidType getType();

	public void setType(FluidType type);
}
