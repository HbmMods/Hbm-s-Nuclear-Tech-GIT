package api.hbm.conveyor;

import net.minecraftforge.common.util.ForgeDirection;

public interface IEnterableBlock {

	public boolean canEnter(IConveyorItem entity, ForgeDirection dir);
	public void onEnter(IConveyorItem entity, ForgeDirection dir);
}
