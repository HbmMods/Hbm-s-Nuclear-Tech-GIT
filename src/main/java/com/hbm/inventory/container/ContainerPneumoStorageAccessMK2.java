package com.hbm.inventory.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import com.hbm.inventory.SlotNonRetarded;
import com.hbm.inventory.container.ContainerPneumoStorageAccess.InventoryPneumoStorageAccess;
import com.hbm.inventory.container.ContainerPneumoStorageAccess.SlotPneumo;
import com.hbm.main.MainRegistry;
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
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ContainerPneumoStorageAccessMK2 extends Container implements ICustomPayloadReceiver {
	
	protected TileEntityPneumoStorageAccess access;
	protected InventoryPneumoStorageAccess inventory;
	protected EntityPlayer player;
	protected String searchString = "";
	
	public static boolean detailedSearch = false;
	
	/** On the server, this is used to find changes in the system and then send them to the client. On the client, this is just to keep track of all items. */
	protected LinkedHashMap<Long, CacheSlotDummy> cachedEntries = new LinkedHashMap();
	/** Server to client queue, contains NBT tags that represents deltas yet to be sent to the client */
	public LinkedList<NBTTagCompound> s2cQueue = new LinkedList();
	
	public void setSorter(Comparator<CacheSlotDummy> sorter) {
		this.listingStart = 0;
		this.sorter = sorter;
		this.rebuildClientIndex();
	}
	
	public void setSearchString(String search) {
		this.listingStart = 0;
		this.searchString = search.toLowerCase(Locale.US);
		this.rebuildClientIndex();
	}
	
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
	public int listingSize = 0;
	
	public int getStackCount() {
		return listingSize;
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
		
		if(player instanceof EntityPlayerMP) processDeltasAndSync((EntityPlayerMP) this.player);
	}

	public ContainerPneumoStorageAccessMK2(InventoryPlayer invPlayer, TileEntityPneumoStorageAccess access) {
		this.access = access;
		this.inventory = new InventoryPneumoStorageAccess(access);
		this.player = invPlayer.player;
		
		int hOffset = 34;
		
		for(int i = 0; i < 6; i++) for(int j = 0; j < 8; j++) {
			this.addSlotToContainer(new SlotPneumo(inventory, j + i * 8, 8 + j * 18 + hOffset, 17 + i * 18));
		}
		
		for(int i = 0; i < 3; i++) for(int j = 0; j < 9; j++) {
			this.addSlotToContainer(new SlotNonRetarded(invPlayer, j + i * 9 + 9, 8 + j * 18 + hOffset, 169 + i * 18));
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new SlotNonRetarded(invPlayer, i, 8 + i * 18 + hOffset, 227));
		}
	}

	@Override
	public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {
		if(mode == 6) return null;
		
		if(index == ContainerPneumoStorageAccess.SLOT_CLICK_ID_REFRESH) {
			this.listingStart = mode;
			this.detectAndSendChanges();
			return null;
		}

		boolean client = player.worldObj.isRemote;
		boolean leftClick = button == 0 && mode == 0;
		boolean rightClick = button == 1 && mode == 0;
		boolean shiftClick = button == 0 && mode == 1;
		
		if(index >= 0 && index < GRID_SIZE) {
			if(!client) return null;
			SlotPneumo slot = (SlotPneumo) this.getSlot(index);
			long hash = StackCache.getStackIdentity(slot.getStack());
			ItemStack held = player.inventory.getItemStack();
			long heldHash = StackCache.getStackIdentity(held);
			if(leftClick) {
				if(held != null && hash != heldHash) held = null;
				else if(held != null && hash == heldHash) {
					held.stackSize += slot.amount;
					held.stackSize = Math.min(held.stackSize, held.getMaxStackSize());
				} else if(held == null && slot.getHasStack()) {
					held = slot.getStack().copy();
					held.stackSize = (int) Math.min(slot.amount, held.getMaxStackSize());
				}
				this.player.inventory.setItemStack(held);
				sendClickToServer(ClickType.LEFT_CLICK, hash);
				return null;
			}
			if(rightClick) {
				if(held != null && hash != heldHash) held.stackSize--;
				else if(held != null && hash == heldHash) {
					held.stackSize += 1;
					held.stackSize = Math.min(held.stackSize, held.getMaxStackSize());
				} else if(held == null && slot.getHasStack()) {
					held = slot.getStack().copy();
					held.stackSize = 1;
				}
				if(held.stackSize <= 0) held = null;
				this.player.inventory.setItemStack(held);
				sendClickToServer(ClickType.RIGHT_CLICK, hash);
				return null;
			}
			if(shiftClick) { sendClickToServer(ClickType.SHIFT_CLICK, hash); return null; }
			
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
				return null; // technically not needed but we don't have to run 500,000 lines of code that ends up doing nothing
			}
		}
		
		return super.slotClick(index, button, mode, player);
	}
	
	public void sendClickToServer(ClickType type, long hash) {

		NBTTagCompound data = new NBTTagCompound();
		data.setByte("type", (byte) type.ordinal());
		data.setLong("hash", hash);
		PacketDispatcher.wrapper.sendToServer(new ContainerNBTCommsPacket(this.windowId, data));
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
	
	/** For S2C to send deltas updating the available items */
	public static enum DeltaType {
		NEW_TYPE,
		COUNT_CHANGE;
	}
	
	public static enum ClickType {
		LEFT_CLICK,
		RIGHT_CLICK,
		SHIFT_CLICK
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
			
			rebuildClientIndex();
			
		} else {
			if(this.access.cache == null || this.access.cache.hasExpired) return;
			ClickType type = EnumUtil.grabEnumSafely(ClickType.class, data.getByte("type"));
			long hash = data.getLong("hash");
			CacheSlotDummy cache = this.cachedEntries.get(hash);
			ItemStack held = this.player.inventory.getItemStack();
			long heldHash = StackCache.getStackIdentity(held);
			
			// left click
			if(type == ClickType.LEFT_CLICK || type == ClickType.RIGHT_CLICK) {
				
				// if we drop an item onto a different stack, or no stack at all, deposit
				if(hash != heldHash && held != null) {
					int toDeposit = type == ClickType.LEFT_CLICK ? held.stackSize : 1;
					held.stackSize -= toDeposit;
					int leftover = (int) this.access.cache.addItemsAndReturnQuantity(held, toDeposit);
					held.stackSize += leftover;
					this.player.inventory.setItemStack(null);
					if(held.stackSize > 0) InventoryUtil.tryAddItemToInventory(player.inventory.mainInventory, held);
					detectAndSendChanges();
					return;
				}
				
				// if our hand is empty or we have the same type, withdraw
				if(cache != null && (held == null || hash == heldHash)) {
					ItemStack stack = cache.displayStack.copy();
					int alreadyHeld = held == null ? 0 : held.stackSize;
					int capacity = stack.getMaxStackSize() - alreadyHeld;
					if(type == ClickType.RIGHT_CLICK && capacity > 1) capacity = 1;
					int toGrab = (int) Math.min(capacity, cache.stacksize);
					int grabbed = (int) this.access.cache.consumeItemsAndReturnQuantity(stack, toGrab);
					stack.stackSize = alreadyHeld + grabbed;
					this.player.inventory.setItemStack(stack);
					detectAndSendChanges();
					return;
				}
			}
			
			if(type == ClickType.SHIFT_CLICK && cache != null) {
				ItemStack stack = cache.displayStack.copy();
				int originalStacksize = (int) Math.min(stack.getMaxStackSize(), cache.stacksize);
				stack.stackSize = originalStacksize;
				ItemStack ret = InventoryUtil.tryAddItemToInventory(player.inventory.mainInventory, stack);
				int remainder = ret == null ? 0 : ret.stackSize;
				int itemsUsed = originalStacksize - remainder;
				this.access.cache.consumeItemsAndReturnQuantity(stack, itemsUsed);
				detectAndSendChanges();
				return;
			}
		}
	}
	
	public void rebuildClientIndex() {
		
		List<CacheSlotDummy> cacheSlots = new ArrayList(this.cachedEntries.size());
		cacheSlots.addAll(this.cachedEntries.values());
		cacheSlots.removeIf(x -> { return x.stacksize <= 0; });
		
		if(this.searchString != null && !this.searchString.isEmpty()) {
			if(!detailedSearch) {
				cacheSlots.removeIf(x -> {
					return !x.displayStack.getDisplayName().toLowerCase(Locale.US).contains(searchString);
				});
			} else {
				cacheSlots.removeIf(x -> {
					boolean contains = x.displayStack.getDisplayName().toLowerCase(Locale.US).contains(searchString);
					List<String> toolTip = new ArrayList();
					if(!contains) {
						x.displayStack.getItem().addInformation(x.displayStack, MainRegistry.proxy.me(), toolTip, MainRegistry.proxy.advancedTooltips());
						for(String string : toolTip) {
							if(string.toLowerCase(Locale.US).contains(searchString)) {
								contains = true;
								break;
							}
						}
					}
					return !contains;
				});
			}
		}
		
		listingSize = cacheSlots.size();
		Collections.sort(cacheSlots, this.sorter);
		int size = cacheSlots.size();
		
		int offset = listingStart * 8;
		
		for(int i = 0; i < inventory.slots.length; i++) {
			int grabIndex = offset + i;
			if(grabIndex < size) {
				CacheSlotDummy cacheSlot = cacheSlots.get(grabIndex);
				SlotPneumo slot = (SlotPneumo) this.inventorySlots.get(i);
				if(cacheSlot.displayStack != null) {
					slot.putStack(cacheSlot.displayStack.copy());
					slot.amount = cacheSlot.stacksize;
				} else {
					slot.putStack(null);
					slot.amount = 0;
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
	
	protected static Comparator<CacheSlotDummy> sorter = SORT_BY_STACK_SIZE;

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		return null;
	}
}
