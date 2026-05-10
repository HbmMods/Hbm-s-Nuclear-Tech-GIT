package api.hbm.ntl;

import net.minecraftforge.common.util.ForgeDirection;

public interface IPneumaticConnector {
	
	/**
	 * Whether the given side can be connected to.
	 * @param dir The side of <i>this block</i>, not the one that is trying to connect.
	 * @return
	 */
	public default boolean canConnectPneumatic(ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN;
	}
}
