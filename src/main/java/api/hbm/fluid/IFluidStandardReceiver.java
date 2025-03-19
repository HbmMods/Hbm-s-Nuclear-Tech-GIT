package api.hbm.fluid;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.uninos.GenNode;
import com.hbm.uninos.UniNodespace;

import api.hbm.fluidmk2.IFluidStandardReceiverMK2;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Deprecated
public interface IFluidStandardReceiver extends IFluidStandardReceiverMK2 {
	
	public default void subscribeToAllAround(FluidType type, TileEntity tile) {
		subscribeToAllAround(type, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
	}
	
	public default void subscribeToAllAround(FluidType type, World world, int x, int y, int z) {
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			trySubscribe(type, world, x, y, z, dir);
		}
	}
	
	public default void tryUnsubscribe(FluidType type, World world, int x, int y, int z) {
		GenNode node = UniNodespace.getNode(world, x, y, z, type.getNetworkProvider());
		if(node != null && node.net != null) node.net.removeReceiver(this);
	}
	
	public default void unsubscribeToAllAround(FluidType type, TileEntity tile) {
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			tryUnsubscribe(type, tile.getWorldObj(), tile.xCoord + dir.offsetX, tile.yCoord + dir.offsetY, tile.zCoord + dir.offsetZ);
		}
	}
}
