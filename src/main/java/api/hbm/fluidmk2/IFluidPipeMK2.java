package api.hbm.fluidmk2;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.lib.Library;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import net.minecraft.tileentity.TileEntity;

/**
 * IFluidConductorMK2 with added node creation method
 * @author hbm
 */
public interface IFluidPipeMK2 extends IFluidConnectorMK2 {
	
	public default FluidNode createNode(FluidType type) {
		TileEntity tile = (TileEntity) this;
		return new FluidNode(type.getNetworkProvider(), new BlockPos(tile.xCoord, tile.yCoord, tile.zCoord)).setConnections(
				new DirPos(tile.xCoord + 1, tile.yCoord, tile.zCoord, Library.POS_X),
				new DirPos(tile.xCoord - 1, tile.yCoord, tile.zCoord, Library.NEG_X),
				new DirPos(tile.xCoord, tile.yCoord + 1, tile.zCoord, Library.POS_Y),
				new DirPos(tile.xCoord, tile.yCoord - 1, tile.zCoord, Library.NEG_Y),
				new DirPos(tile.xCoord, tile.yCoord, tile.zCoord + 1, Library.POS_Z),
				new DirPos(tile.xCoord, tile.yCoord, tile.zCoord - 1, Library.NEG_Z)
				);
	}
}
