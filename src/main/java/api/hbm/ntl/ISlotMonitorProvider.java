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
	
	/** Returns the ORIGIANL ItemStack of that index, so that the monitors can detect changes */
	public ItemStack getSlotAt(int index);
	
	/** Returns the amount of that slot at that index. Some storages may use int64 datatypes so we have to account for those too somehow, since ItemStacks cannot handle that. */
	public long getAmountAt(int index);
	
	/** Removes the given number of items from that slot, returns the amount left to remove if the stack was smaller than the supplied amount */
	public long useUpItem(int index, long amount);
	
	/** Adds the given number of items to that slot, returns the amount that couldn't be added due to stack limits */
	public long addItem(int index, long amount);
	
	/** Sets the slot contents, returns the number of items that couldn't be added */
	public long setupType(int index, ItemStack zeroStack, long amount);
	
	/** Whether this container allows types to be set via the access terminal */
	public boolean allowTypeSetting();
	
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
