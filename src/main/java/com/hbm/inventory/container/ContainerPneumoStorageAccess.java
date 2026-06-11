package com.hbm.inventory.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.hbm.interfaces.NotableComments;
import com.hbm.inventory.SlotNonRetarded;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.ContainerCustomPayloadPacket;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoStorageAccess;
import com.hbm.util.EnumUtil;

import api.hbm.ntl.StackCache;
import api.hbm.ntl.StackCache.CacheSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

@NotableComments // i long for the day when i never have to look at this fucking horseshit ever again
public class ContainerPneumoStorageAccess extends Container implements ICustomPayloadReceiver {
	
	protected TileEntityPneumoStorageAccess access;
	protected InventoryPneumoStorageAccess inventory;
	
	public static final int GRID_SIZE = 6 * 8;
	public static final String STACK_SIZE_KEY = "PNEUMO_STACK_SIZE";

	public ContainerPneumoStorageAccess(InventoryPlayer invPlayer, TileEntityPneumoStorageAccess access) {
		this.access = access;
		this.inventory = new InventoryPneumoStorageAccess(access);
		
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 8; j++) {
				this.addSlotToContainer(new SlotPneumo(inventory, j + i * 8, 8 + j * 18, 17 + i * 18));
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
	public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {
		
		if(index >= 0 && index < GRID_SIZE) {
			boolean client = player.worldObj.isRemote;
			SlotPneumo slot = (SlotPneumo) this.getSlot(index);
			ItemStack held = player.inventory.getItemStack();
			
			if(held == null && slot.getHasStack()) {
				ItemStack stack = slot.getStack().copy();
				
				if(button == 0) {
					int toGrab = (int) Math.min(stack.getMaxStackSize(), slot.amount);
					
					if(client) {
						stack.stackSize = toGrab;
						player.inventory.setItemStack(stack);
					} else {
						if(this.access.cache == null || this.access.cache.hasExpired) return stack;
						StackCache cache = this.access.cache;
						stack.stackSize = (int) cache.consumeItemsAndReturnQuantity(stack, toGrab); // this can't work because the stack got altered with the description NBT.....
						player.inventory.setItemStack(stack);
					}
				}
			}
			
			return slot.getHasStack() ? slot.getStack().copy() : null;
		}
		
		return super.slotClick(index, button, mode, player);
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
	}
	
	/** Used only on the client side to set the contents of a slot after a sync */
	@Override
	public void putStackInSlot(int index, ItemStack slot) {
		this.getSlot(index).putStack(slot);
	}
	
	public long[] previousStackSizes = new long[GRID_SIZE];
	
	/**
	 * All syncing is done using the ICrafting interface (which only EntityPlayerMP implements), each single slot change is one entire fucking
	 * packet that is sent like a massive pile of pricks to each client. Who cares, right. Issue is, this interface only supports sending of
	 * ItemStacks and integer values with integer keys for the progress bars. We need longs which are indexed, so at least an extra byte along
	 * with that. After some fucking about I decided, well vanilla container code is complete fucking shit anyway, why bother with any of that?
	 * Instead, we just skip all the syncing for that and use our custom crap packet. Depending on whether the stack, the count, or both has changed,
	 * we send the requested data in a single packet.
	 */
	@Override
	public void detectAndSendChanges() {
		
		// skip the first 6*8 slots, i.e. all the ones visible in the access grid
		for(int i = GRID_SIZE; i < this.inventorySlots.size(); i++) {
			ItemStack stack0 = ((Slot) this.inventorySlots.get(i)).getStack();
			ItemStack stack1 = (ItemStack) this.inventoryItemStacks.get(i);

			if(!ItemStack.areItemStacksEqual(stack1, stack0)) {
				stack1 = stack0 == null ? null : stack0.copy();
				this.inventoryItemStacks.set(i, stack1);

				for(int j = 0; j < this.crafters.size(); ++j) {
					((ICrafting) this.crafters.get(j)).sendSlotContents(this, i, stack1);
				}
			}
		}
		
		// custom horseshit scum fuck
		
		NBTTagList list = new NBTTagList();
		
		if(this.access.cache != null && !this.access.cache.hasExpired) for(int i = 0; i < GRID_SIZE; i++) {
			PneumoMesageType msg = null;
			SlotPneumo slot = (SlotPneumo) this.inventorySlots.get(i);
			ItemStack actualStack = slot.getStack();
			long cachedSize = slot.amount;
			CacheSlot cacheSlot = actualStack != null ? this.access.cache.getSlotFromStack(actualStack) : null;
			long actualSize = cacheSlot != null ? cacheSlot.stacksize : 0;
			if(actualSize <= 0) actualStack = null;
			ItemStack cachedStack = (ItemStack) this.inventoryItemStacks.get(i);
			
			if(!ItemStack.areItemStacksEqual(cachedStack, actualStack)) msg = PneumoMesageType.UPDATE_TYPE;

			if(cachedSize != actualSize || actualSize > 0 /* HACK: solve this by moving the listing change to the container class and writing the correct amounts on init */) {
				if(msg == PneumoMesageType.UPDATE_TYPE) msg = PneumoMesageType.UPDATE_ALL;
				if(msg == null) msg = PneumoMesageType.UPDATE_COUNT;
			}

			if(msg != null) {
				cachedStack = actualStack == null ? null : actualStack.copy();
				this.inventoryItemStacks.set(i, cachedStack);
				slot.amount = actualSize;
				
				NBTTagCompound listEntry = new NBTTagCompound();
				listEntry.setByte(KEY_TYPE, (byte) msg.ordinal());
				listEntry.setByte(KEY_SLOT_INDEX, (byte) i);
				if(msg == PneumoMesageType.UPDATE_ALL || msg == PneumoMesageType.UPDATE_COUNT) {
					listEntry.setLong(KEY_LONG_COUNT, actualSize);
				}
				if(msg == PneumoMesageType.UPDATE_ALL || msg == PneumoMesageType.UPDATE_TYPE) if(actualStack != null) actualStack.writeToNBT(listEntry);
				
				list.appendTag(listEntry);
			}
		}

		if(list.tagCount() > 0) {
			NBTTagCompound masterTag = new NBTTagCompound();
			masterTag.setTag("list", list);
			for(Object o : this.crafters) {
				if(o instanceof EntityPlayerMP) {
					EntityPlayerMP playerMP = (EntityPlayerMP) o;
					PacketDispatcher.wrapper.sendTo(new ContainerCustomPayloadPacket(playerMP.currentWindowId, masterTag), playerMP);
				}
			}
		}
	}

	public static final String KEY_TYPE = "t";
	public static final String KEY_SLOT_INDEX = "s";
	public static final String KEY_LONG_COUNT = "lc";
	public static enum PneumoMesageType {
		UPDATE_COUNT,
		UPDATE_TYPE,
		UPDATE_ALL;
	}
	
	@Override
	public void acceptData(int windowId, NBTTagCompound data) {
		if(windowId != this.windowId) return;
		
		NBTTagList list = data.getTagList("list", 10);
		
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound listEntry = list.getCompoundTagAt(i);
			PneumoMesageType msg = EnumUtil.grabEnumSafely(PneumoMesageType.class, listEntry.getByte(KEY_TYPE));
			int slotIndex = listEntry.getByte(KEY_SLOT_INDEX);
			SlotPneumo slot = (SlotPneumo) this.inventorySlots.get(slotIndex);
			if(msg == PneumoMesageType.UPDATE_ALL || msg == PneumoMesageType.UPDATE_COUNT) slot.amount = listEntry.getLong(KEY_LONG_COUNT);
			if(msg == PneumoMesageType.UPDATE_ALL || msg == PneumoMesageType.UPDATE_TYPE) slot.putStack(ItemStack.loadItemStackFromNBT(listEntry));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		return null;
	}
	
	public static class SlotPneumo extends SlotNonRetarded {
		
		public long amount;

		public SlotPneumo(IInventory inventory, int id, int x, int y) {
			super(inventory, id, x, y);
		}
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
					}
				}
			}
		}
		
		public static final Comparator<CacheSlot> SORT_BY_STACK_SIZE = new Comparator<CacheSlot>() {
			@Override
			public int compare(CacheSlot o1, CacheSlot o2) {
				if(o1.stacksize > o2.stacksize)			return -1;	if(o1.stacksize < o2.stacksize)			return 1;
				if(o1.itemId < o2.itemId)				return -1;	if(o1.itemId > o2.itemId)				return 1;
				if(o1.meta < o2.meta)					return -1;	if(o1.meta > o2.meta)					return 1;
				if(o1.nbt == null && o2.nbt != null)	return -1;	if(o1.nbt != null && o2.nbt == null)	return 1;
				return 0;
			}
		};

		@Override public int getSizeInventory() { return GRID_SIZE; }
		@Override public ItemStack getStackInSlot(int slot) { return slots[slot]; }
		@Override public int getInventoryStackLimit() { return 64; }
		@Override public ItemStack decrStackSize(int slot, int amount) { return null; }
		@Override public void setInventorySlotContents(int slot, ItemStack stack) { this.slots[slot] = stack; }

		@Override
		public ItemStack getStackInSlotOnClosing(int slot) {
			if(slots[slot] != null) {
				ItemStack stack = slots[slot];
				slots[slot] = null;
				return stack;
			}
			return null;
		}

		@Override public String getInventoryName() { return "null"; }
		@Override public boolean hasCustomInventoryName() { return false; }
		@Override public boolean isItemValidForSlot(int slot, ItemStack stack) { return false; }
		@Override public void markDirty() { }
		@Override public boolean isUseableByPlayer(EntityPlayer player) { return true; }
		@Override public void openInventory() { }
		@Override public void closeInventory() { }
	}
}
