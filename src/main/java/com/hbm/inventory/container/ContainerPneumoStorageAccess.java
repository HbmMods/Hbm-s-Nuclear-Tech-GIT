package com.hbm.inventory.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.hbm.inventory.SlotNonRetarded;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoStorageAccess;
import com.hbm.util.ItemStackUtil;

import api.hbm.ntl.StackCache;
import api.hbm.ntl.StackCache.CacheSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ContainerPneumoStorageAccess extends Container {
	
	protected TileEntityPneumoStorageAccess access;
	protected InventoryPneumoStorageAccess inventory;

	public ContainerPneumoStorageAccess(InventoryPlayer invPlayer, TileEntityPneumoStorageAccess access) {
		this.access = access;
		this.inventory = new InventoryPneumoStorageAccess(access);
		
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 8; j++) {
				this.addSlotToContainer(new SlotNonRetarded(inventory, j + i * 8, 8 + j * 18, 17 + i * 18));
			}
		}
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new SlotNonRetarded(invPlayer, j + i * 9 + 9, 8 + j * 18, 169 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new SlotNonRetarded(invPlayer, i, 8 + i * 18, 227));
		}
		
		inventory.updateListing();
		this.detectAndSendChanges();
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return access.getDistanceFrom(player.posX, player.posY, player.posZ) <= 15 * 15;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		return null;
	}
	
	/** This inventory instance only exists to prepare the contents of a StackCache in such a way that we can use it in a container. */
	public static class InventoryPneumoStorageAccess implements IInventory {
		
		public StackCache cache;
		public ItemStack[] slots;
		
		public InventoryPneumoStorageAccess(TileEntityPneumoStorageAccess access) {
			this.slots = new ItemStack[getSizeInventory()];
			this.cache = access.cache;
		}
		
		public void updateListing() { // DEMO
			if(this.cache == null) return;
			List<CacheSlot> cacheSlots = new ArrayList(cache.cacheSlots.size());
			cacheSlots.addAll(cache.cacheSlots.values());
			cacheSlots.removeIf(x -> { return x.stacksize <= 0; });
			Collections.sort(cacheSlots, SORT_BY_STACK_SIZE);
			int size = cacheSlots.size();
			
			for(int i = 0; i < slots.length; i++) {
				if(i < size) {
					CacheSlot cache = cacheSlots.get(i);
					if(cache.displayStack != null) {
						slots[i] = cache.displayStack.copy();
						ItemStackUtil.addTooltipToStack(slots[i], "x" + cache.stacksize, "in " + cache.monitors.size() + " stacks");
					}
				}
			}
		}
		
		public static final Comparator<CacheSlot> SORT_BY_STACK_SIZE = new Comparator<CacheSlot>() {
			@Override
			public int compare(CacheSlot o1, CacheSlot o2) {
				if(o1.stacksize > o2.stacksize)			return 1;	if(o1.stacksize < o2.stacksize)			return -1;
				if(o1.itemId < o2.itemId)				return 1;	if(o1.itemId > o2.itemId)				return -1;
				if(o1.meta < o2.meta)					return 1;	if(o1.meta > o2.meta)					return -1;
				if(o1.nbt == null && o2.nbt != null)	return 1;	if(o1.nbt != null && o2.nbt == null)	return -1;
				return 0;
			}
		};

		@Override public int getSizeInventory() { return 6 * 9; }
		@Override public ItemStack getStackInSlot(int slot) { return slots[slot]; }
		@Override public int getInventoryStackLimit() { return 64; }

		@Override public ItemStack decrStackSize(int slot, int amount) { return null; }

		@Override
		public ItemStack getStackInSlotOnClosing(int slot) {
			if(slots[slot] != null) {
				ItemStack stack = slots[slot];
				slots[slot] = null;
				return stack;
			}
			return null;
		}

		@Override
		public void setInventorySlotContents(int slot, ItemStack stack) {
			this.slots[slot] = stack;
		}

		@Override public String getInventoryName() { return "null"; }
		@Override public boolean hasCustomInventoryName() { return false; }
		@Override public boolean isItemValidForSlot(int slot, ItemStack stack) { return false; }

		@Override
		public void markDirty() {
			
		}

		@Override public boolean isUseableByPlayer(EntityPlayer player) { return true; }

		@Override public void openInventory() { }
		@Override public void closeInventory() { }
	}
}
