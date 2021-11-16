package api.hbm.energy;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
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
	public default boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN;
	}
	
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
	
	/**
	 * Basic implementation of subscribing to a nearby power grid
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 */
	public default void trySubscribe(World world, int x, int y, int z) {

		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof IEnergyConductor) {
			IEnergyConductor con = (IEnergyConductor) te;
			
			if(con.getPowerNet() != null && !con.getPowerNet().isSubscribed(this))
				con.getPowerNet().subscribe(this);
		}
	}
}
