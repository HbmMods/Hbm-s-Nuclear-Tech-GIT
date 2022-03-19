package api.hbm.energy;

import net.minecraft.tileentity.TileEntity;

/**
 * For compatible cables with no buffer, using the IPowertNet. You can make your own cables with IEnergyConnector as well, but they won't join their power network.
 * @author hbm
 */
public interface IEnergyConductor extends IEnergyConnector {

	public IPowerNet getPowerNet();
	
	public void setPowerNet(IPowerNet network);
	
	/**
	 * A unique identifier for every conductor tile. Used to prevent duplicates when loading previously persistent unloaded tiles.
	 * @return
	 */
	public default int getIdentity() {

		TileEntity te = (TileEntity) this;
		
		final int prime = 31;
		int result = 1;
		result = prime * result + te.xCoord;
		result = prime * result + te.yCoord;
		result = prime * result + te.zCoord;
		return result;
	}
}
