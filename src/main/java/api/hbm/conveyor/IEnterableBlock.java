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
	public boolean canItemEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorItem entity);
	public void onItemEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorItem entity);
	
	public boolean canPackageEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorPackage entity);
	public void onPackageEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorPackage entity);
}
