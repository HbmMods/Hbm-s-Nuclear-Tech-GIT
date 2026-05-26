package api.hbm.ntl;

import java.util.Iterator;
import java.util.LinkedHashSet;

import javax.annotation.Nullable;

import com.hbm.uninos.networkproviders.PneumaticNetwork;

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
	
	@Nullable public Item item;
	public long stacksize;
	public int meta;
	public NBTTagCompound nbt;
	
	protected boolean hasAvailabilityChanged = false;
	
	public SlotMonitor(int index, ISlotMonitorProvider parent) {
		this.hasAvailabilityChanged = true;
		this.index = index;
		this.parent = parent;
	}
	
	public ItemStack toZeroStack() {
		if(item == null) return null;
		ItemStack stack = new ItemStack(item, 0, meta);
		stack.stackTagCompound = nbt;
		return stack;
	}
	
	/**
	 * Monitor providers need to keep track of whether availability has changed, i.e. compair has run out, compression setting has changed, etc
	 * This means that we don't have to check availability every single tick, which potentially saves a fuckton of iterations.
	 */
	public void availabilityHasChanged() {
		this.hasAvailabilityChanged = true;
	}
	
	public void checkUpdate() {
		
		PneumaticNetwork pneumoNet = this.parent.getRelevantNetwork();
		
		if(hasAvailabilityChanged) {
			
			if(pneumoNet != null) {
				for(StackCache cache : pneumoNet.accessors) {
					if(!cache.hasExpired && parent.isAvailableToCache(cache)) {
						cache.addToCache(this);
					}
				}
			}
			
			// if this monitor is not available to some caches, remove them
			Iterator<CacheSlot> iterator = viewedBy.iterator();
			while(iterator.hasNext()) {
				CacheSlot slot = iterator.next();
				StackCache cache = slot.getStackCache();
				if(cache.hasExpired || !parent.isAvailableToCache(cache)) {
					slot.removeMonitor(this);
					iterator.remove();
				}
			}
			
			hasAvailabilityChanged = false;
		}
		
		ItemStack stack = parent.getSlotAt(index);
		long amount = parent.getAmountAt(index);
		
		boolean hasTypeChanged = false;
		if(stack == null || item == null) {
			if(stack == null && item != null) hasTypeChanged = true;
			if(stack != null && item == null) hasTypeChanged = true;
		} else {
			if(item != stack.getItem() || meta != stack.getItemDamage()) hasTypeChanged = true;
			else if(nbt == null && stack.hasTagCompound()) hasTypeChanged = true;
			else if(nbt != null && !stack.hasTagCompound()) hasTypeChanged = true;
			else if(nbt != null && stack.hasTagCompound() && !nbt.equals(stack.stackTagCompound)) hasTypeChanged = true;
		}
		
		if(hasTypeChanged) {
			
			System.out.println("Type changed!");
			
			// remove from all existing monitors
			Iterator<CacheSlot> iterator = viewedBy.iterator();
			while(iterator.hasNext()) {
				CacheSlot slot = iterator.next();
				slot.removeMonitor(this);
				iterator.remove();
				System.out.println("Removing");
			}
			
			// set updated traits
			if(stack == null) {
				this.item = null;
				this.stacksize = 0;
				this.meta = 0;
				this.nbt = null;
			} else {
				this.item = stack.getItem();
				this.stacksize = amount;
				this.meta = stack.getItemDamage();
				this.nbt = stack.hasTagCompound() ? (NBTTagCompound) stack.stackTagCompound.copy() : null;
			}

			// find new monitors
			if(pneumoNet != null) {
				
				System.out.println("Adding to new network...");
				
				for(StackCache cache : pneumoNet.accessors) {
					System.out.println("Adding to cache...");
					if(!cache.hasExpired && parent.isAvailableToCache(cache)) {
						cache.addToCache(this);
						System.out.println("Added!");
					}
				}
			}
			
			return;
		}
		
		if(stacksize != amount) {
			long delta = amount - stacksize;
			for(CacheSlot slot : viewedBy) slot.changeAmounts(delta);

			this.stacksize = amount;
		}
	}
}
