package api.hbm.conveyor;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IEnterableBlock {

	/**
	 * Returns true of the moving item can enter from the given side. When this happens, the IConveyorItem will call onEnter and despawn
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param dir
	 * @param entity
	 * @return
	 */
	public boolean canEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorItem entity);
	public void onEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorItem entity);
}
