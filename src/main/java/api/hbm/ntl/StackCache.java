package api.hbm.ntl;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * The stack cache represents all combined slots that are available to this endpoint.
 * I.e. each endpoint, like an access terminal or an automation output has one stack cache
 * which gets regularly updated so it knows what stacks it can access.
 * 
 * @author hbm
 */
public class StackCache {
	
	public int x;
	public int y;
	public int z;
	
	public boolean hasExpired = false;
	
	/** Maps an identity number to the actual cache slot */
	public LinkedHashMap<Long, CacheSlot> cacheSlots = new LinkedHashMap();
	
	public StackCache(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void addToCache(SlotMonitor monitor) {
		long monitorIdentity = getStackIdentity(monitor.item, monitor.meta, monitor.nbt);
		CacheSlot cache = cacheSlots.get(monitorIdentity);
		
		if(cache == null) {
			cache = new CacheSlot(monitor.toZeroStack());
			cacheSlots.put(monitorIdentity, cache);
		}
		
		cache.addMonitor(monitor);
	}
	
	public void dissolveCache() {
		for(Entry<Long, CacheSlot> cacheEntry : cacheSlots.entrySet()) {
			cacheEntry.getValue().destroy();
		}
		this.cacheSlots.clear();
		this.hasExpired = true;
	}
	
	/**
	 * A cache slot represents multiple accessible slots combined into one by type,
	 * in essence it's a single slot with an uncapped max stack size, which references
	 * multiple slot monitor instances in order to figure out how many items it has in total.
	 * 
	 * @author hbm
	 */
	public class CacheSlot {
		
		@Nullable public final ItemStack displayStack;
		public final int itemId;
		public final int meta;
		public final NBTTagCompound nbt;

		public long stacksize;
		
		public LinkedHashSet<SlotMonitor> monitors = new LinkedHashSet();
		
		public CacheSlot(ItemStack stack) {
			
			if(stack != null) {
				this.displayStack = stack.copy();
				this.stacksize = stack.stackSize;
				this.displayStack.stackSize = 1;
				this.itemId = Item.getIdFromItem(stack.getItem());
				this.meta = stack.getItemDamage();
				if(stack.hasTagCompound())
					this.nbt = (NBTTagCompound) stack.stackTagCompound.copy();
				else
					this.nbt = null;
			} else {
				this.displayStack = null;
				this.stacksize = 0;
				this.itemId = 0;
				this.meta = 0;
				this.nbt = null;
			}
		}
		
		public void addMonitor(SlotMonitor monitor) {
			if(this.monitors.add(monitor)) {
				monitor.viewedBy.add(this);
				this.changeAmounts(monitor.stacksize);
			}
		}
		
		public void removeMonitor(SlotMonitor monitor) {
			System.out.println("Trying to remove...");
			if(this.monitors.remove(monitor)) {
				this.changeAmounts(-monitor.stacksize);
				System.out.println("Removed!");
			}
		}
		
		public void destroy() {
			for(SlotMonitor monitor : monitors) {
				monitor.viewedBy.remove(this);
			}
			this.stacksize = 0;
		}
		
		public void changeAmounts(long delta) {
			this.stacksize += delta;
		}
		
		public StackCache getStackCache() {
			return StackCache.this;
		}
		
		// not actually used, and probably not needed. this would fix any inconsistencies with the sized,
		// however we try to ensure that sizes are always correctly updated so this should never be the case.
		public void reCount() {
			this.stacksize = 0;
			for(SlotMonitor monitor : monitors) {
				this.stacksize += monitor.stacksize;
			}
		}
	}
	
	public static long getStackIdentity(Item item, int meta, NBTTagCompound nbt) {
		long identity = Item.getIdFromItem(item) * 27644437;
		identity += meta * 27644437;
		if(nbt != null) identity += nbt.toString().hashCode();
		return identity;
	}
}
