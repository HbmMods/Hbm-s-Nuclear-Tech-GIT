package api.hbm.ntl;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

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
	
	/** Maps an identity number to the actual cache slot */
	public LinkedHashMap<Integer, CacheSlot> cacheSlots = new LinkedHashMap();
	
	public StackCache(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * A cache slot represents multiple accessible slots combined into one by type,
	 * in essence it's a single slot with an uncapped max stack size, which references
	 * multiple slot monitor instances in order to figure out how many items it has in total.
	 * 
	 * @author hbm
	 */
	public class CacheSlot {
		
		public final ItemStack displayStack;
		public final int itemId;
		public final int meta;
		public final NBTTagCompound nbt;

		public long stacksize;
		
		public CacheSlot(ItemStack stack) {
			this.displayStack = stack.copy();
			this.displayStack.stackSize = 1;
			this.itemId = Item.getIdFromItem(stack.getItem());
			this.stacksize = stack.stackSize;
			this.meta = stack.getItemDamage();
			if(stack.hasTagCompound())
				this.nbt = (NBTTagCompound) stack.stackTagCompound.copy();
			else
				this.nbt = null;
		}
		
		public void changeAmounts(long delta) {
			this.stacksize += delta;
		}
		
		public StackCache getStackCache() {
			return StackCache.this;
		}
		
		public void reCount() {
			this.stacksize = 0;
			for(SlotMonitor monitor : monitors) {
				this.stacksize += monitor.stacksize;
			}
		}
		
		public LinkedHashSet<SlotMonitor> monitors = new LinkedHashSet();
	}
	
	public static int getStackIdentity(Item item, int meta, NBTTagCompound nbt) {
		int identity = Item.getIdFromItem(item) * 27644437;
		identity += meta * 27644437;
		if(nbt != null) identity += nbt.toString().hashCode();
		return identity;
	}
}
