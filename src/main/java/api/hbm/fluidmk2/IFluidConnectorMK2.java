package api.hbm.fluidmk2;

import com.hbm.inventory.fluid.FluidType;

import net.minecraftforge.common.util.ForgeDirection;

public interface IFluidConnectorMK2 {
	
	/**
	 * Whether the given side can be connected to
	 * @param dir
	 * @return
	 */
	public default boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN;
	}
}
