package api.hbm.ntl;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

public class StackCache {
	
	public LinkedHashMap<Integer, CacheSlot> cacheSlots = new LinkedHashMap();
	
	public class CacheSlot {
		
		public Item item;
		public long stacksize;
		public int meta;
		public NBTTagCompound nbt;
		
		public LinkedHashSet<SlotMonitor> monitors = new LinkedHashSet();
	}
	
	public static int getStackIdentity(Item item, int meta, NBTTagCompound nbt) {
		int identity = Item.getIdFromItem(item) * 27644437;
		identity += meta * 27644437;
		if(nbt != null) identity += nbt.toString().hashCode();
		return identity;
	}
}
