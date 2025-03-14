package api.hbm.fluidmk2;

import com.hbm.inventory.fluid.FluidType;

public interface IFluidProviderMK2 extends IFluidUserMK2 {

	public void useUpFluid(FluidType type, int pressure, long amount);
	public default long getProviderSpeed(FluidType type, int pressure) { return 1_000_000_000; }
	public long getFluidAvailable(FluidType type, int pressure);
	
	public default int[] getProvidingPressureRange(FluidType type) { return DEFAULT_PRESSURE_RANGE; }
}
