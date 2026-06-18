package com.hbm.inventory.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.inventory.SlotNonRetarded;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.ContainerNBTCommsPacket;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoStorageAccess;
import com.hbm.util.EnumUtil;

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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ContainerPneumoStorageAccessMK2 extends Container implements ICustomPayloadReceiver {
	
	protected TileEntityPneumoStorageAccess access;
	protected InventoryPneumoStorageAccess inventory;
	
	/** On the server, this is used to find changes in the system and then send them to the client. On the client, this is just to keep track of all items. */
	protected LinkedHashMap<Long, CacheSlotDummy> cachedEntries = new LinkedHashMap();
	/** Server to client queue, contains NBT tags that represents deltas yet to be sent to the client */
	public LinkedList<NBTTagCompound> s2cQueue = new LinkedList();
	
	/** Used to temporarily store the previous CacheSlot contents to calculate deltas with (i.e. detect changes) */
	public static class CacheSlotDummy {
		
		public final ItemStack displayStack;
		public long stacksize;
		public final int itemId;
		public final int meta;
		public final NBTTagCompound nbt;
		
		public CacheSlotDummy(ItemStack displayStack, long stacksize) {
			this.displayStack = displayStack;
			this.stacksize = stacksize;
			this.itemId = Item.getIdFromItem(displayStack.getItem());
			this.meta = displayStack.getItemDamage();
			if(displayStack.hasTagCompound()) this.nbt = (NBTTagCompound) displayStack.stackTagCompound.copy();
			else this.nbt = null;
		}
		
		public CacheSlotDummy(CacheSlot original) {
			this(original.displayStack.copy(), original.stacksize);
		}
	}

	public static final int GRID_SIZE = 6 * 8;
	public static final int DELTAS_PER_MSG = 6 * 8;
	
	public int listingStart = 0;
	
	public int getStackCount() {
		return cachedEntries.size();
	}
	
	/** Serverside, creates a new delta for a new item stack */
	public void pushNewItem(ItemStack zeroStack, long amount) {
		NBTTagCompound data = new NBTTagCompound();
		data.setByte("type", (byte) DeltaType.NEW_TYPE.ordinal());
		zeroStack.writeToNBT(data);
		data.setLong("amount", amount);
		s2cQueue.add(data);
	}

	/** Serverside, creates a new delta for an amount update */
	public void updateCount(long hash, long amount) {
		NBTTagCompound data = new NBTTagCompound();
		data.setByte("type", (byte) DeltaType.COUNT_CHANGE.ordinal());
		data.setLong("hash", hash);
		data.setLong("amount", amount);
		s2cQueue.add(data);
	}

	/** Serverside, bunches up deltas into NBTTagLists and sends them, 48 (DELTAS_PER_MSG) at a time */
	public void processDeltasAndSync(EntityPlayerMP playerMP) {
		if(s2cQueue.isEmpty()) return;
		
		NBTTagList list = null;
		
		while(!s2cQueue.isEmpty()) {
			if(list == null) list = new NBTTagList();
			list.appendTag(s2cQueue.removeFirst());
			if(list.tagCount() >= DELTAS_PER_MSG) { bonVoyage(list, playerMP); list = null; }
		}
		
		if(list != null) bonVoyage(list, playerMP);
	}
	
	/** Wraps the NBTTagList into a master compound tag and sends it to the client */
	public void bonVoyage(NBTTagList list, EntityPlayerMP playerMP) {
		NBTTagCompound masterTag = new NBTTagCompound();
		masterTag.setTag("list", list);
		PacketDispatcher.wrapper.sendTo(new ContainerNBTCommsPacket(playerMP.currentWindowId, masterTag), playerMP);
	}
	
	/** Compares the original access stack cache with the one we have buffered to detect changes and pushes them to the s2c sending queue */
	public void checkAndSyncCache() {
		if(this.access.cache == null || this.access.cache.hasExpired) return;
		
		for(Entry<Long, CacheSlot> entry : this.access.cache.cacheSlots.entrySet()) {
			
			Long hash = entry.getKey();
			if(hash == StackCache.getNullIdentity()) continue;
			
			CacheSlotDummy existingCache = this.cachedEntries.get(hash);
			CacheSlot properCache = entry.getValue();
			
			// if the previous entries already contain this type, check for amount change
			if(existingCache != null) {
				if(existingCache.stacksize != properCache.stacksize) {
					this.updateCount(hash, properCache.stacksize);
					existingCache.stacksize = properCache.stacksize;
				}
			// if not, add that type to our index
			} else {
				existingCache = new CacheSlotDummy(properCache);
				existingCache.stacksize = properCache.stacksize;
				this.cachedEntries.put(hash, existingCache);
				this.pushNewItem(existingCache.displayStack, existingCache.stacksize);
			}
		}
	}

	public ContainerPneumoStorageAccessMK2(InventoryPlayer invPlayer, TileEntityPneumoStorageAccess access) {
		this.access = access;
		this.inventory = new InventoryPneumoStorageAccess(access);
		
		for(int i = 0; i < 6; i++) for(int j = 0; j < 8; j++) {
			this.addSlotToContainer(new SlotPneumo(inventory, j + i * 8, 8 + j * 18, 17 + i * 18));
		}
		
		for(int i = 0; i < 3; i++) for(int j = 0; j < 9; j++) {
			this.addSlotToContainer(new SlotNonRetarded(invPlayer, j + i * 9 + 9, 8 + j * 18, 169 + i * 18));
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new SlotNonRetarded(invPlayer, i, 8 + i * 18, 227));
		}
		
		this.detectAndSendChanges();
	}

	@Override
	public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {
		if(mode == 6) return null;

		boolean leftClick = button == 0 && mode == 0;
		boolean rightClick = button == 1 && mode == 0;
		boolean shiftClick = button == 0 && mode == 1;
		
		if(index >= 0 && index < GRID_SIZE) {
			
			// TODO: in this new version, the server has no fucking idea what slot we are even clicking on
			
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
	
	public long[] previousStackSizes = new long[GRID_SIZE];
	
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
		
		boolean isServer = !this.access.getWorldObj().isRemote;
		
		if(isServer) {
			checkAndSyncCache();
		} else {
			rebuildClientIndex();
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return access.getDistanceFrom(player.posX, player.posY, player.posZ) <= 15 * 15;
	}
	
	public static enum DeltaType {
		NEW_TYPE,
		COUNT_CHANGE;
	}
	
	@Override
	public void acceptData(Side side, int windowId, NBTTagCompound data) {
		if(windowId != this.windowId) return;
		
		if(side == Side.CLIENT) {
			NBTTagList list = data.getTagList("list", 10);
			
			for(int i = 0; i < list.tagCount(); i++) {
				NBTTagCompound line = list.getCompoundTagAt(i);
				DeltaType type = EnumUtil.grabEnumSafely(DeltaType.class, line.getByte("type"));
				
				if(type == DeltaType.NEW_TYPE) {
					ItemStack stack = ItemStack.loadItemStackFromNBT(line);
					long amount = line.getLong("amount");
					long hash = StackCache.getStackIdentity(stack);
					CacheSlotDummy dummy = new CacheSlotDummy(stack, amount);
					this.cachedEntries.put(hash, dummy);
					
				} else if(type == DeltaType.COUNT_CHANGE) {
					long hash = line.getLong("hash");
					CacheSlotDummy dummy = this.cachedEntries.get(hash);
					if(dummy != null) dummy.stacksize = line.getLong("amount");
				}
			}
		}
	}
	
	public int startingIndex;
	
	public void rebuildClientIndex() {
		
		List<CacheSlotDummy> cacheSlots = new ArrayList(this.cachedEntries.size());
		cacheSlots.addAll(this.cachedEntries.values());
		cacheSlots.removeIf(x -> { return x.stacksize <= 0; });
		Collections.sort(cacheSlots, SORT_BY_STACK_SIZE);
		int size = cacheSlots.size();
		
		int offset = startingIndex * 8;
		
		for(int i = 0; i < inventory.slots.length; i++) {
			int grabIndex = offset + i;
			if(grabIndex < size) {
				CacheSlotDummy cacheSlot = cacheSlots.get(grabIndex);
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
	
	public static final Comparator<CacheSlotDummy> SORT_BY_STACK_SIZE = new Comparator<CacheSlotDummy>() {
		@Override
		public int compare(CacheSlotDummy o1, CacheSlotDummy o2) {
			if(o1.stacksize > o2.stacksize)			return -1;	if(o1.stacksize < o2.stacksize)			return 1;
			if(o1.itemId < o2.itemId)				return -1;	if(o1.itemId > o2.itemId)				return 1;
			if(o1.meta < o2.meta)					return -1;	if(o1.meta > o2.meta)					return 1;
			if(o1.nbt == null && o2.nbt != null)	return -1;	if(o1.nbt != null && o2.nbt == null)	return 1;
			return 0;
		}
	};
	
	public static final Comparator<CacheSlotDummy> SORT_BY_ID = new Comparator<CacheSlotDummy>() {
		@Override
		public int compare(CacheSlotDummy o1, CacheSlotDummy o2) {
			if(o1.itemId < o2.itemId)				return -1;	if(o1.itemId > o2.itemId)				return 1;
			if(o1.meta < o2.meta)					return -1;	if(o1.meta > o2.meta)					return 1;
			if(o1.stacksize > o2.stacksize)			return -1;	if(o1.stacksize < o2.stacksize)			return 1;
			if(o1.nbt == null && o2.nbt != null)	return -1;	if(o1.nbt != null && o2.nbt == null)	return 1;
			return 0;
		}
	};
	
	public static final Comparator<CacheSlotDummy> SORT_BY_INTERNAL = new Comparator<CacheSlotDummy>() {
		@Override
		public int compare(CacheSlotDummy o1, CacheSlotDummy o2) {
			String name1 = o1.displayStack.getItem().getUnlocalizedName(o1.displayStack);
			String name2 = o2.displayStack.getItem().getUnlocalizedName(o2.displayStack);
			int compare = name1.compareToIgnoreCase(name2);
			if(compare != 0) return compare;
			return SORT_BY_ID.compare(o1, o2);
		}
	};
	
	public static final Comparator<CacheSlotDummy> SORT_BY_LOCALIZED = new Comparator<CacheSlotDummy>() {
		@Override
		public int compare(CacheSlotDummy o1, CacheSlotDummy o2) {
			String name1 = o1.displayStack.getItem().getItemStackDisplayName(o1.displayStack);
			String name2 = o2.displayStack.getItem().getItemStackDisplayName(o2.displayStack);
			int compare = name1.compareToIgnoreCase(name2);
			if(compare != 0) return compare;
			return SORT_BY_ID.compare(o1, o2);
		}
	};

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
		@Override public int getInventoryStackLimit() { return 1; }
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
