package api.hbm.ntl;

import com.hbm.uninos.networkproviders.PneumaticNetwork;

import net.minecraft.item.ItemStack;

/**
 * Interface for storage tile entities which provides the access terminals with slot monitors,
 * and slot monitors with ways of accessing the underlying stacks
 * 
 * @author hbm
 */
public interface ISlotMonitorProvider {

	/** Returns an array of available slot monitors, which should ideally mirror the available slots of that container */
	public SlotMonitor[] getMonitors();
	
	/** Returns the slot contents of that index, so that the monitors can detect changes */
	public ItemStack getSlotAt(int index);
	
	/** Returns the amount of that slot at that index. Some storages may use int64 datatypes so we have to account for those too somehow, since ItemStacks cannot handle that. */
	public long getAmountAt(int index);
	
	/** Whether this storage unit is reachable by the access point */
	public boolean isAvailableToCache(StackCache cache);
	
	/** This allows slot monitors to find the network, and by extension all cached slots */
	public PneumaticNetwork getRelevantNetwork();
	
	/** Runs whenever a new stack cache user (i.e. an access point) joins the network in order to grab all the stack monitors */
	public default void onNewCacheHasJoined(StackCache stackCache, PneumaticNetwork network) {
		for(SlotMonitor monitor : getMonitors()) {
			if(!stackCache.hasExpired && isAvailableToCache(stackCache)) {
				stackCache.addToCache(monitor);
			}
		}
	}
	
	public default void updateMonitors() {
		for(SlotMonitor monitor : getMonitors()) monitor.checkUpdate();
	}
}
