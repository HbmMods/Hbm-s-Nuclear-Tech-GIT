package api.hbm.fluidmk2;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.uninos.IGenReceiver;
import com.hbm.uninos.networkproviders.FluidNetProvider;

public interface IFluidReceiverMK2 extends IGenReceiver<FluidNetProvider> {

	/** Sends fluid of the desired type and pressure to the receiver, returns the remainder */
	public long transferFluid(FluidType type, int pressure, long amount);
	public long getReceiverSpeed(FluidType type, int pressure);
}
