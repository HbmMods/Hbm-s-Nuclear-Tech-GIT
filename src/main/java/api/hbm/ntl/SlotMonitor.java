package api.hbm.ntl;

import java.util.HashSet;

import api.hbm.ntl.StackCache.CacheSlot;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

public class SlotMonitor {

	/** The index of the slot in the inventory this monitor....monitors */
	public final int index;
	/** The inventory */
	public final ISlotMonitorProvider parent;
	
	/** If this monitor detects a change, all cache slots need to be notified */
	public HashSet<CacheSlot> viewedBy = new HashSet();
	
	public Item item;
	public long stacksize;
	public int meta;
	public NBTTagCompound nbt;
	
	public SlotMonitor(int index, ISlotMonitorProvider parent) {
		this.index = index;
		this.parent = parent;
	}
	
	public void checkUpdate() {
		
	}
}
