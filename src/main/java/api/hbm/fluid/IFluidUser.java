package api.hbm.fluid;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IFluidUser extends IFluidConnector {
	
	/*public default void updateStandardPipes(World world, int x, int y, int z) {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			this.trySubscribe(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir);
	}*/
}
