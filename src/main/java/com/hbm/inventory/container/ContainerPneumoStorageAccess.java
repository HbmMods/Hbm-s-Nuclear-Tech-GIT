package com.hbm.inventory.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.hbm.interfaces.NotableComments;
import com.hbm.inventory.SlotNonRetarded;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.ContainerNBTCommsPacket;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoStorageAccess;
import com.hbm.util.EnumUtil;
import com.hbm.util.InventoryUtil;

import api.hbm.ntl.StackCache;
import api.hbm.ntl.StackCache.CacheSlot;
import cpw.mods.fml.relauncher.Side;
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
	
	/*
	 * time wasted on this fucking class: a lot
	 * 
	 * this approach was great right until it just stopped working.
	 * a proper search requires the client to decide if the item matches
	 * the criteria, and we have to process all items in the network, so
	 * this entire approach of only sending 48 items at once to the client
	 * just absolutely will not work.
	 * 
	 * i may have to rewrite absolutely everything here now, but at least i
	 * finally know what i am up against. didn't hear no bell.
	 */
	
	protected TileEntityPneumoStorageAccess access;
	protected InventoryPneumoStorageAccess inventory;
	
	public static final int SLOT_CLICK_ID_REFRESH = -666;
	public static final int GRID_SIZE = 6 * 8;
	public static final String STACK_SIZE_KEY = "PNEUMO_STACK_SIZE";
	
	public int itemCountForClient = 0;
	public int listingStart = 0;

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
		
		updateListing(0);
		this.detectAndSendChanges();
	}
	
	public void updateListing(int startingIndex) { // DEMO
		if(this.access.cache == null || this.access.cache.hasExpired) return;
		StackCache cache = this.access.cache;
		List<CacheSlot> cacheSlots = new ArrayList(cache.cacheSlots.size());
		cacheSlots.addAll(cache.cacheSlots.values());
		cacheSlots.removeIf(x -> { return x.stacksize <= 0; });
		Collections.sort(cacheSlots, SORT_BY_STACK_SIZE);
		int size = cacheSlots.size();
		
		int offset = startingIndex * 8;
		
		for(int i = 0; i < inventory.slots.length; i++) {
			int grabIndex = offset + i;
			if(grabIndex < size) {
				CacheSlot cacheSlot = cacheSlots.get(grabIndex);
				if(cacheSlot.displayStack != null) {
					inventory.slots[i] = cacheSlot.displayStack.copy();
				} else {
					inventory.slots[i] = null;
				}
			} else {
				inventory.slots[i] = null;
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

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return access.getDistanceFrom(player.posX, player.posY, player.posZ) <= 15 * 15;
	}

	@Override
	public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {
		if(mode == 6) return null;
		
		if(index == SLOT_CLICK_ID_REFRESH) {
			if(player.worldObj.isRemote) return null;
			this.listingStart = mode;
			this.detectAndSendChanges();
			return null;
		}

		boolean leftClick = button == 0 && mode == 0;
		boolean rightClick = button == 1 && mode == 0;
		boolean shiftClick = button == 0 && mode == 1;
		
		if(index >= 0 && index < GRID_SIZE) {
			boolean client = player.worldObj.isRemote;
			SlotPneumo slot = (SlotPneumo) this.getSlot(index);
			ItemStack held = player.inventory.getItemStack();
			
			if(slot.getHasStack()) {
				
				// left click, can't hold an item and provides a full stack to the held item
				if(leftClick || rightClick) {
					ItemStack stack = slot.getStack().copy();

					int alreadyHeld = held == null ? 0 : held.stackSize;
					int capacity = stack.getMaxStackSize() - alreadyHeld;
					if(rightClick && capacity > 1) capacity = 1;
					int toGrab = (int) Math.min(capacity, slot.amount);
					
					if(capacity > 0 && (held == null || StackCache.getStackIdentity(held) == StackCache.getStackIdentity(stack))) {
						if(client) {
							stack.stackSize = toGrab + alreadyHeld;
							player.inventory.setItemStack(stack);
						} else {
							if(this.access.cache == null || this.access.cache.hasExpired) return null;
							StackCache cache = this.access.cache;
							stack.stackSize = (int) cache.consumeItemsAndReturnQuantity(stack, toGrab) + alreadyHeld;
							player.inventory.setItemStack(stack);
						}
						
						return null; // for some reason we gotta terminate here and not below
					}
					
				// shift click, works even if there's a held stack, serverside only and the nwe just sync
				} else if(shiftClick && !client) {
					ItemStack stack = slot.getStack().copy();
					if(this.access.cache == null || this.access.cache.hasExpired) return null;
					StackCache cache = this.access.cache;
					int originalStacksize = (int) Math.min(stack.getMaxStackSize(), slot.amount);
					stack.stackSize = originalStacksize;
					ItemStack ret = InventoryUtil.tryAddItemToInventory(player.inventory.mainInventory, stack);
					int remainder = ret == null ? 0 : ret.stackSize;
					int itemsUsed = originalStacksize - remainder;
					cache.consumeItemsAndReturnQuantity(stack, itemsUsed);
					detectAndSendChanges();
				}
			}
			
			if(held != null) {
				int toDeposit = rightClick ? 1 : held.stackSize;
				player.inventory.getItemStack().stackSize -= toDeposit;
				if(player.inventory.getItemStack().stackSize <= 0) player.inventory.setItemStack(null);
				if(this.access.cache == null || this.access.cache.hasExpired) return null;
				StackCache cache = this.access.cache;
				int remainder = (int) cache.addItemsAndReturnQuantity(held, toDeposit);
				if(remainder > 0) {
					ItemStack copy = held.copy();
					copy.stackSize = remainder;
					InventoryUtil.tryAddItemToInventory(player.inventory.mainInventory, copy);
					detectAndSendChanges();
				}
			}
			
			return slot.getHasStack() ? slot.getStack().copy() : null;
			
		// shift clicking an item from the player inv to the storage
		} else if(index >= GRID_SIZE && index < this.inventorySlots.size()) {

			Slot slot = this.getSlot(index);
			
			if(shiftClick && slot.getHasStack()) {
				ItemStack stack = slot.getStack().copy();
				if(this.access.cache == null || this.access.cache.hasExpired) return null;
				StackCache cache = this.access.cache;
				int remainder = (int) cache.addItemsAndReturnQuantity(stack, stack.stackSize);
				slot.decrStackSize(stack.stackSize - remainder);
				if(remainder <= 0) slot.putStack(null);
				slot.onSlotChanged();
				detectAndSendChanges();
			}
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
		this.updateListing(listingStart);
		
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
			// if a null stack is indiced, skip
			boolean hasNullStack = this.access.cache.cacheSlots.containsKey(this.access.cache.getNullIdentity());
			masterTag.setInteger("itemCount", this.access.cache.cacheSlots.size() - (hasNullStack ? 1 : 0));
			masterTag.setInteger("listingStart", this.listingStart);
			masterTag.setTag("list", list);
			for(Object o : this.crafters) {
				if(o instanceof EntityPlayerMP) {
					EntityPlayerMP playerMP = (EntityPlayerMP) o;
					PacketDispatcher.wrapper.sendTo(new ContainerNBTCommsPacket(playerMP.currentWindowId, masterTag), playerMP);
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
	public void acceptData(Side side, int windowId, NBTTagCompound data) {
		if(windowId != this.windowId) return;
		
		if(side.isClient()) {
			this.itemCountForClient = data.getInteger("itemCount");
			this.listingStart = data.getInteger("listingStart");
			
			NBTTagList list = data.getTagList("list", 10);
			
			for(int i = 0; i < list.tagCount(); i++) {
				NBTTagCompound listEntry = list.getCompoundTagAt(i);
				PneumoMesageType msg = EnumUtil.grabEnumSafely(PneumoMesageType.class, listEntry.getByte(KEY_TYPE));
				int slotIndex = listEntry.getByte(KEY_SLOT_INDEX);
				SlotPneumo slot = (SlotPneumo) this.inventorySlots.get(slotIndex);
				if(msg == PneumoMesageType.UPDATE_ALL || msg == PneumoMesageType.UPDATE_COUNT) slot.amount = listEntry.getLong(KEY_LONG_COUNT);
				if(msg == PneumoMesageType.UPDATE_ALL || msg == PneumoMesageType.UPDATE_TYPE) slot.putStack(ItemStack.loadItemStackFromNBT(listEntry));
			}
		} else {
			
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

		@Override
		public boolean canTakeStack(EntityPlayer player) {
			return true;
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
