package api.hbm.ntl;

import net.minecraft.item.ItemStack;

/** Interface for storage tile entities which provides the access terminals with slot monitors, and slot monitors with ways of accessing the underlying stacks */
public interface ISlotMonitorProvider {

	/** Returns an array of available slot monitors, which should ideally mirror the available slots of that container */
	public SlotMonitor[] getMonitors();
	
	/** Returns the slot contents of that index, so that the monitors cna detect changes */
	public ItemStack getSlotAt(int index);
	
	/** Whether this storage unit is reachable by the access point on the provided location */
	public boolean isAvailableToTerminal(int termX, int termY, int termZ);
}
