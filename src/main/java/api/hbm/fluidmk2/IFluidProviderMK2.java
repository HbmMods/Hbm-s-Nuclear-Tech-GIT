package api.hbm.fluidmk2;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.uninos.IGenProvider;
import com.hbm.uninos.networkproviders.FluidNetProvider;

public interface IFluidProviderMK2 extends IFluidUserMK2, IGenProvider<FluidNetProvider> {

	public void useUpFluid(FluidType type, int pressure, long amount);
	public long getProviderSpeed(FluidType type, int pressure);
	public long getFluidAvailable(FluidType type, int pressure);
}
