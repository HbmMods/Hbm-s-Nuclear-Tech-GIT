package api.hbm.conveyor;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IEnterableBlock {

	public boolean canEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorItem entity);
	public void onEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorItem entity);
}
