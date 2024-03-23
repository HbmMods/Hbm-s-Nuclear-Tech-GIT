package api.hbm.energymk2;

import api.hbm.energymk2.Nodespace.PowerNode;
import net.minecraft.tileentity.TileEntity;

public interface IEnergyConductorMK2 extends IEnergyConnectorMK2 {
	
	public default PowerNode getNode() {
		TileEntity tile = (TileEntity) this;
		return Nodespace.getNode(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
	}
}
