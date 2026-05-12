package api.hbm.ntl;

import java.util.LinkedHashSet;

import api.hbm.ntl.StackCache.CacheSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Slot monitors are the access points of the system to the actual items stacks.
 * Each storage unit needs to provide all its stacks in the form of slot monitors
 * to the storage system. The slot monitor's main functionality is to detect changes
 * in the underlying stack, so that it can notify and update the system's wider
 * data structures.
 * 
 * @author hbm
 */
public class SlotMonitor {

	/** The index of the slot in the inventory this monitor....monitors */
	public final int index;
	/** The inventory */
	public final ISlotMonitorProvider parent;
	
	/** If this monitor detects a change, all cache slots need to be notified */
	public LinkedHashSet<CacheSlot> viewedBy = new LinkedHashSet();
	
	public Item item;
	public long stacksize;
	public int meta;
	public NBTTagCompound nbt;
	
	public SlotMonitor(int index, ISlotMonitorProvider parent) {
		this.index = index;
		this.parent = parent;
	}
	
	public void checkUpdate() {
		
		for(CacheSlot slot : viewedBy) { // we're gonna need an iterator for that
			if(!parent.isAvailableToCache(slot.getStackCache())) {
				// TODO: kill from that cache
			}
		}
		
		ItemStack stack = parent.getSlotAt(index);
		long amount = parent.getAmountAt(index);
		
		boolean hasTypeChanged = false;
		if(item != stack.getItem() || meta != stack.getItemDamage()) hasTypeChanged = true;
		else if(nbt == null && stack.hasTagCompound()) hasTypeChanged = true;
		else if(nbt != null && !stack.hasTagCompound()) hasTypeChanged = true;
		else if(nbt != null && stack.hasTagCompound() && !nbt.equals(stack.stackTagCompound)) hasTypeChanged = true;
		
		if(hasTypeChanged) {
			// TODO: find a bridge to the other CacheSlots, ideally over the pneumo network
			// all viewing cache slots need their reference removed and a new cache slot needs to be found/created
			return;
		}
		
		if(stacksize != amount) {
			long delta = amount - stacksize;
			for(CacheSlot slot : viewedBy) slot.changeAmounts(delta);
		}
	}
}
