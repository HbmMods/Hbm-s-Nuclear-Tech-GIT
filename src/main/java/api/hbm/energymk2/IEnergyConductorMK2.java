package api.hbm.energymk2;

import com.hbm.lib.Library;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.Nodespace.PowerNode;
import net.minecraft.tileentity.TileEntity;

public interface IEnergyConductorMK2 extends IEnergyConnectorMK2 {
	
	public default PowerNode createNode() {
		TileEntity tile = (TileEntity) this;
		return new PowerNode(new BlockPos(tile.xCoord, tile.yCoord, tile.zCoord)).setConnections(
				new DirPos(tile.xCoord + 1, tile.yCoord, tile.zCoord, Library.POS_X),
				new DirPos(tile.xCoord - 1, tile.yCoord, tile.zCoord, Library.NEG_X),
				new DirPos(tile.xCoord, tile.yCoord + 1, tile.zCoord, Library.POS_Y),
				new DirPos(tile.xCoord, tile.yCoord - 1, tile.zCoord, Library.NEG_Y),
				new DirPos(tile.xCoord, tile.yCoord, tile.zCoord + 1, Library.POS_Z),
				new DirPos(tile.xCoord, tile.yCoord, tile.zCoord - 1, Library.NEG_Z)
				);
	}
}
