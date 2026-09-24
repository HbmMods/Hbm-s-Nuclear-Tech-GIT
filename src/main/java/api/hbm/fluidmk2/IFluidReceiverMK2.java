package api.hbm.fluidmk2;

import com.hbm.inventory.fluid.FluidType;

import api.hbm.energymk2.IEnergyReceiverMK2.ConnectionPriority;

public interface IFluidReceiverMK2 extends IFluidUserMK2 {

	/** Sends fluid of the desired type and pressure to the receiver, returns the remainder */
	public long transferFluid(FluidType type, int pressure, long amount);
	public default long getReceiverSpeed(FluidType type, int pressure) { return 1_000_000_000; }
	public long getDemand(FluidType type, int pressure);
	
	public default int[] getReceivingPressureRange(FluidType type) { return DEFAULT_PRESSURE_RANGE; }
	
	public default ConnectionPriority getFluidPriority() {
		return ConnectionPriority.NORMAL;
	}
}
