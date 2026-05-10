package api.hbm.ntl;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

public class SlotMonitor {

	public final int index;
	public final ISlotMonitorProvider parent;
	
	public Item item;
	public long stacksize;
	public int meta;
	public NBTTagCompound nbt;
	
	public SlotMonitor(int index, ISlotMonitorProvider parent) {
		this.index = index;
		this.parent = parent;
	}
}
