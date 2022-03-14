package api.hbm.fluid;

import com.hbm.inventory.fluid.FluidType;

import net.minecraftforge.common.util.ForgeDirection;

public interface IFluidConnector {
	
	/**
	 * Returns the amount of fluid that remains
	 * @param power
	 * @return
	 */
	public int transferFluid(FluidType type, int fluid);
	
	/**
	 * Whether the given side can be connected to
	 * @param dir
	 * @return
	 */
	public default boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN;
	}
	
	/**
	 * Returns the amount of fluid that this machine is able to receive
	 * @param type
	 * @return
	 */
	public int getDemand(FluidType type);
}
