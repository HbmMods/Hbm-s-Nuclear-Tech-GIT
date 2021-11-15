package api.hbm.energy;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * For anything that connects to power and can be transferred power to, the bottom-level interface.
 * This is mean for TILE ENTITIES
 * @author hbm
 */
public interface IEnergyConnector {
	
	/**
	 * Returns the amount of power that was added
	 * @param power
	 * @return
	 */
	public long transferPower(long power);
	
	/**
	 * Whether the given side can be connected to
	 * @param dir
	 * @return
	 */
	public boolean canConnect(ForgeDirection dir);
	
	/**
	 * The current power of either the machine or an entire network
	 * @return
	 */
	public long getPower();
	
	/**
	 * The capacity of either the machine or an entire network
	 * @return
	 */
	public long getMaxPower();
}
