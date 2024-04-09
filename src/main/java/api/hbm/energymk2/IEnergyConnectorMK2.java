package api.hbm.energymk2;

import net.minecraftforge.common.util.ForgeDirection;

public interface IEnergyConnectorMK2 {
	
	/**
	 * Whether the given side can be connected to
	 * dir refers to the side of this block, not the connecting block doing the check
	 * @param dir
	 * @return
	 */
	public default boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN;
	}
}
