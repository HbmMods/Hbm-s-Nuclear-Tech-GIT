package api.hbm.energymk2;

import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Interface for all blocks that should visually connect to cables without having an IEnergyConnector tile entity.
 * This is meant for BLOCKS
 * @author hbm
 *
 */
public interface IEnergyConnectorBlock {
	
	/**
	 * Same as IEnergyConnector's method but for regular blocks that might not even have TEs. Used for rendering only!
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param dir
	 * @return
	 */
	public boolean canConnect(IBlockAccess world, int x, int y, int z, ForgeDirection dir);
}
