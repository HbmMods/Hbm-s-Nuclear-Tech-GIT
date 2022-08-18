package api.hbm.energy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

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
		return getIdentityFromTile((TileEntity) this);
	}
	
	public static int getIdentityFromTile(TileEntity te) {
		return getIdentityFromPos(te.xCoord, te.yCoord, te.zCoord);
	}
	
	public static int getIdentityFromPos(int x, int y, int z) {
		final int prime = 27644437; // must be this large to minimize localized collisions
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}
	
	/**
	 * Whether the link should be part of reeval when the network is changed.
	 * I.e. if this link should join any of the new networks (FALSE for switches that are turned off for example)
	 * @return
	 */
	public default boolean canReevaluate() {
		return !((TileEntity) this).isInvalid();
	}
	
	/**
	 * When a link leaves the network, the net has to manually calculate the resulting networks.
	 * Each link has to decide what other links will join the same net.
	 * @param copy
	 */
	public default void reevaluate(HashMap<Integer, IEnergyConductor> copy, HashMap<Integer, Integer> proxies) {

		for(int[] pos : getConnectionPoints()) {
			int newX = pos[0];
			int newY = pos[1];
			int newZ = pos[2];
			int id = IEnergyConductor.getIdentityFromPos(newX, newY, newZ);
			
			IEnergyConductor neighbor = copy.get(id);
			
			if(neighbor == null) {
				Integer newId = proxies.get(id);
				
				if(newId != null) {
					neighbor = copy.get(newId);
				}
			}
			
			if(neighbor != null && this.canReevaluate() && neighbor.canReevaluate()) {
				
				if(neighbor.getPowerNet() != null) {
					
					//neighbor net and no self net
					if(this.getPowerNet() == null) {
						neighbor.getPowerNet().joinLink(this);
					//neighbor net and self net
					} else {
						this.getPowerNet().joinNetworks(neighbor.getPowerNet());
					}
					
				//bidirectional re-eval, experimental and technically optional, only useful as a fallback
				} /*else {
					
					//no neighbor net and no self net
					if(this.getPowerNet() == null) {
						this.setPowerNet(new PowerNet().joinLink(this));
						neighbor.setPowerNet(this.getPowerNet().joinLink(neighbor));
					//no neighbor net and self net
					} else {
						neighbor.setPowerNet(this.getPowerNet().joinLink(neighbor));
					}
				}*/
				
				//extensive debugging has shown that bidirectional re-eval ic complete shit
			}
		}
	}
	
	/**
	 * Creates a list of positions for the reeval process. In short - what positions should be considered as connected.
	 * Also used by pylons to quickly figure out what positions to connect to.
	 * DEFAULT: Connects to all six neighboring blocks.
	 * @return
	 */
	public default List<int[]> getConnectionPoints() {

		List<int[]> pos = new ArrayList();
		TileEntity tile = (TileEntity) this;
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			int newX = tile.xCoord + dir.offsetX;
			int newY = tile.yCoord + dir.offsetY;
			int newZ = tile.zCoord + dir.offsetZ;
			
			pos.add(new int[] {newX, newY, newZ});
		}
		
		return pos;
	}
	
	/**
	 * Since isLoaded is only currently used for weeding out unwanted subscribers, and cables shouldn't (although technically can) be
	 * subscribers, we just default to true because I don't feel like wasting time implementing things that we don't actually need.
	 * Perhaps this indicates a minor flaw in the new API, but I physically lack the ability to worry about it.
	 */
	@Override
	public default boolean isLoaded() {
		return true;
	}

	//TODO: check if this standard implementation doesn't break anything (it shouldn't but right now it's a bit redundant) also: remove duplicate implementations
	@Override
	public default long transferPower(long power) {
		
		if(this.getPowerNet() == null)
			return power;
		
		return this.getPowerNet().transferPower(power);
	}
	
	/**
	 * Returns whether the conductor has mutliblock proxies which need to be taken into consideration for re-eval.
	 * @return
	 */
	public default boolean hasProxies() {
		return false;
	}
	
	/**
	 * Returns the identities (position-based) of proxies which resolve into the conductor's own identity.
	 * @return
	 */
	public default List<Integer> getProxies() {
		return new ArrayList();
	}
}
