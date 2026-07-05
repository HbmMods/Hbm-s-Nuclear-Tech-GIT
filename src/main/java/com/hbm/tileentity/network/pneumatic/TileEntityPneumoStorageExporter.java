package com.hbm.tileentity.network.pneumatic;


import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerPneumoStorageExporter;
import com.hbm.inventory.gui.GUIPneumoStorageExporter;
import com.hbm.tileentity.network.RTTYSystem;
import com.hbm.util.BobMathUtil;

import api.hbm.ntl.StackCache;
import api.hbm.ntl.StackCache.CacheSlot;
import api.hbm.redstoneoverradio.IRORInteractive;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityPneumoStorageExporter extends TileEntityPneumaticMachineBase implements IRORInteractive, IControlReceiver {
	
	/** If requests should be pulled repeatedly every tick */
	public boolean continuousRequest = false;
	/** If ROR configuration has taken place, ignore manually defined filters entirely */
	public boolean rorConfiguredMode = false;
	/** What strategy to use for handling request filters */
	public int requestMode = 0;
	/** Item ID and meta pairs with amount for RoR controlled filters */
	public short[][] rorFilters = new short[9][3];
	/** Delay for non-forced (i.e. continuous request) grabs, if not successful */
	public int slotDelay[] = new int[9];
	public static final int SLOT_DELAY = 10;

	/** Each slot individually tries to pull as much as it can of the configured item */
	public static final int MODE_AS_MUCH_AS_POSSIBLE = 0;
	/** Each slot individually tries to pull the exact quantity configured */
	public static final int MODE_FULL_STACK = 1;
	/** All request slots try to pull the desired quantities simultaneously */
	public static final int MODE_FULL_REQUEST = 2;
	
	public boolean lastRedstone = false;
	
	public int[] SLOT_ACCESS = new int[] {9, 10, 11, 12, 13, 14, 15, 16, 17};

	public TileEntityPneumoStorageExporter() {
		super(18);
	}

	@Override
	public String getName() {
		return "container.pneumoStorageExporter";
	}

	@Override public boolean canExtractItem(int slot, ItemStack itemStack, int side) { return slot >= 9; }
	@Override public int[] getAccessibleSlotsFromSide(int side) { return SLOT_ACCESS; }

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!worldObj.isRemote) {
			
			for(int i = 0; i < 9; i++) {
				if(slotDelay[i] > 0) slotDelay[i]--;
			}

			boolean redstone = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
			
			if(continuousRequest) {
				this.doRequest(false);
			} else {
				if(redstone && !lastRedstone) this.doRequest(true);
			}
			
			this.lastRedstone = redstone;
			
			this.networkPackNT(15);
		}
	}
	
	public void doRequest(boolean force) {
		
		if(this.requestMode != MODE_FULL_REQUEST) {
			// handle all filters individually
			for(int i = 0; i < 9; i++) if(!requestSlot(i, force)) this.slotDelay[i] = SLOT_DELAY;
		} else {
			
			// check filter delay if forced pulls aren't active
			if(!force) for(int i = 0; i < 9; i++) {
				short[] filter = this.getFilter(i);
				if(filter != null && slotDelay[i] > 0) return;
			}
			
			// check if filter demands are met and space is free
			for(int i = 0; i < 9; i++) {
				short[] filter = this.getFilter(i);
				if(filter == null) continue;
				
				int itemId = filter[0];
				Item item = Item.getItemById(itemId);
				int meta = filter[1];
				int requestSize = filter[2];

				int existingSize = 0;
				ItemStack existingStack = slots[i + 9];
				
				if(existingStack != null) {
					if(existingStack.getItem() == item && existingStack.getItemDamage() == meta && !existingStack.hasTagCompound()) {
						existingSize = existingStack.stackSize;
					} else {
						this.slotDelay[i] = SLOT_DELAY;
						return;
					}
				}
				
				ItemStack newStack = new ItemStack(item, 1, meta);
				int capacityLeft = newStack.getMaxStackSize() - existingSize;
				
				if(capacityLeft < requestSize || getAvailability(itemId, meta) < requestSize) {
					this.slotDelay[i] = SLOT_DELAY;
					return;
				}
			}
			
			// everything is good, pull items. continues should not happen, but are in place nonetheless
			for(int i = 0; i < 9; i++) {
				short[] filter = this.getFilter(i);
				if(filter == null) continue;
				
				int itemId = filter[0];
				Item item = Item.getItemById(itemId);
				int meta = filter[1];
				int requestSize = filter[2];

				int existingSize = 0;
				ItemStack existingStack = slots[i + 9];
				if(existingStack != null) existingSize = existingStack.stackSize;
				
				ItemStack newStack = new ItemStack(item, 1, meta);
				
				long hash = StackCache.getStackIdentity(itemId, meta, null);
				if(hash == this.cache.getNullIdentity()) continue; // safeguard
				
				CacheSlot cacheSlot = this.cache.cacheSlots.get(hash);
				if(cacheSlot == null) continue; // safeguard
				
				slots[i + 9] = newStack;
				slots[i + 9].stackSize = existingSize + (int) this.cache.consumeItemsAndReturnQuantity(newStack, requestSize);
			}
			
			this.markChanged();
		}
	}
	
	/** Returns false if the slot delay should be reset (unsuccessful) or true of not (successful or delay still active) */
	public boolean requestSlot(int slot, boolean force) {
		
		if(!force && slotDelay[slot] > 0) return true;
		if(this.cache == null || this.cache.hasExpired) return false;
		
		short[] filter = this.getFilter(slot);
		if(filter == null) return false;
		
		int itemId = filter[0];
		Item item = Item.getItemById(itemId);
		int meta = filter[1];
		int requestSize = filter[2];
		
		int existingSize = 0;
		ItemStack existingStack = slots[slot + 9];
		
		if(existingStack != null) {
			if(existingStack.getItem() == item && existingStack.getItemDamage() == meta && !existingStack.hasTagCompound()) {
				existingSize = existingStack.stackSize;
			} else {
				return false;
			}
		}
		
		ItemStack newStack = new ItemStack(item, 1, meta);
		int capacityLeft = newStack.getMaxStackSize() - existingSize;
		
		// any non-AMAP mode will fail if it can't insert that much before we even check availability from the pneumo sys
		if(capacityLeft < requestSize && this.requestMode != MODE_AS_MUCH_AS_POSSIBLE) return false;
		long hash = StackCache.getStackIdentity(itemId, meta, null);
		if(hash == this.cache.getNullIdentity()) return false;
		
		CacheSlot cacheSlot = this.cache.cacheSlots.get(hash);
		if(cacheSlot == null) return false;
		
		// any non-AMAP mode will fail if the system doesn't have the requested amount
		if(cacheSlot.stacksize < requestSize && this.requestMode != MODE_AS_MUCH_AS_POSSIBLE) return false;
		if(cacheSlot.stacksize <= 0) return false;
		
		int toPull = (int) BobMathUtil.min(requestSize, cacheSlot.stacksize, capacityLeft);
		
		slots[slot + 9] = newStack;
		slots[slot + 9].stackSize = existingSize + (int) this.cache.consumeItemsAndReturnQuantity(newStack, toPull);
		this.markChanged();
		
		return true;
	}
	
	/** Returns item id, meta and request size for the given filter. Returns null if no filter is specified */
	public short[] getFilter(int slot) {
		if(rorConfiguredMode) {
			if(Item.getItemById(rorFilters[slot][0]) == null) return null;
			return rorFilters[slot];
		} else {
			if(slots[slot] != null) {
				ItemStack stack = slots[slot];
				return new short[] {(short) Item.getIdFromItem(stack.getItem()), (short) stack.getItemDamage(), (short) stack.stackSize};
			}
			return null;
		}
	}
	
	public long getAvailability(int item, int meta) {
		if(this.cache == null || this.cache.hasExpired) return 0;
		long hash = this.cache.getStackIdentity(item, meta, null);
		if(hash == this.cache.getNullIdentity()) return 0;
		CacheSlot slot = this.cache.cacheSlots.get(hash);
		if(slot == null) return 0;
		return slot.stacksize;
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeBoolean(continuousRequest);
		buf.writeBoolean(rorConfiguredMode);
		buf.writeByte((byte) requestMode);
		for(int i = 0; i < 9; i++) {
			buf.writeShort(rorFilters[i][0]);
			buf.writeShort(rorFilters[i][1]);
			buf.writeShort(rorFilters[i][2]);
		}
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.continuousRequest = buf.readBoolean();
		this.rorConfiguredMode = buf.readBoolean();
		this.requestMode = buf.readByte();
		for(int i = 0; i < 9; i++) {
			rorFilters[i][0] = buf.readShort();
			rorFilters[i][1] = buf.readShort();
			rorFilters[i][2] = buf.readShort();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.continuousRequest = nbt.getBoolean("continuousRequest");
		this.rorConfiguredMode = nbt.getBoolean("rorConfiguredMode");
		this.requestMode = nbt.getByte("requestMode");
		for(int i = 0; i < 9; i++) {
			rorFilters[i][0] = nbt.getShort("filter_" + i + "_0");
			rorFilters[i][1] = nbt.getShort("filter_" + i + "_1");
			rorFilters[i][2] = nbt.getShort("filter_" + i + "_2");
		}
		
		this.lastRedstone = nbt.getBoolean("lastRedstone");
		this.slotDelay = nbt.getIntArray("slotDelay");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("continuousRequest", continuousRequest);
		nbt.setBoolean("rorConfiguredMode", rorConfiguredMode);
		nbt.setByte("requestMode", (byte) requestMode);
		
		for(int i = 0; i < 9; i++) {
			nbt.setShort("filter_" + i + "_0", (short) rorFilters[i][0]);
			nbt.setShort("filter_" + i + "_1", (short) rorFilters[i][1]);
			nbt.setShort("filter_" + i + "_2", (short) rorFilters[i][2]);
		}

		nbt.setBoolean("lastRedstone", lastRedstone);
		nbt.setIntArray("slotDelay", slotDelay);
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerPneumoStorageExporter(player.inventory, this); }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIPneumoStorageExporter(player.inventory, this); }

	@Override public boolean hasPermission(EntityPlayer player) { return this.isUseableByPlayer(player); }

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("continuous")) {
			this.continuousRequest = !this.continuousRequest;
		}
		if(data.hasKey("request")) {
			this.requestMode++;
			if(this.requestMode >= 3) this.requestMode = 0;
		}
		if(data.hasKey("ror")) {
			this.rorConfiguredMode = !this.rorConfiguredMode;
		}
		this.markChanged();
	}

	@Override
	public String[] getFunctionInfo() {
		return new String[] {
				PREFIX_FUNCTION + "setfilter" + NAME_SEPARATOR + "slot" + PARAM_SEPARATOR + "itemid" + PARAM_SEPARATOR + "itemmeta" + PARAM_SEPARATOR + "amount",
				PREFIX_FUNCTION + "setcontinuous" + NAME_SEPARATOR + "on/off",
				PREFIX_FUNCTION + "request",
				PREFIX_FUNCTION + "requestslot" + NAME_SEPARATOR + "slot",
				PREFIX_FUNCTION + "checkavailability" + NAME_SEPARATOR + "itemid" + PARAM_SEPARATOR + "itemmeta" + PARAM_SEPARATOR + "returnchannel",
		};
	}

	@Override
	public String runRORFunction(String name, String[] params) {
		
		if((PREFIX_FUNCTION + "setfilter").equals(name) && params.length == 4) {
			int slot = IRORInteractive.parseInt(params[0], 1, 9) - 1;
			int itemId = IRORInteractive.parseInt(params[1], 0, Short.MAX_VALUE);
			int meta = IRORInteractive.parseInt(params[2], 0, Short.MAX_VALUE);
			int amount = IRORInteractive.parseInt(params[3], 1, 64);

			this.rorFilters[slot][0] = (short) itemId;
			this.rorFilters[slot][1] = (short) meta;
			this.rorFilters[slot][2] = (short) amount;
			this.markChanged();
			return null;
		}
		
		if((PREFIX_FUNCTION + "setcontinuous").equals(name) && params.length == 1) {
			if("on".equals(params[0])) this.continuousRequest = true;
			if("off".equals(params[0])) this.continuousRequest = false;
			this.markChanged();
			return null;
		}
		
		if((PREFIX_FUNCTION + "request").equals(name)) {
			this.doRequest(true);
			return null;
		}
		
		if((PREFIX_FUNCTION + "requestslot").equals(name) && params.length == 1) {
			int slot = IRORInteractive.parseInt(params[0], 1, 9) - 1;
			if(!this.requestSlot(slot, true)) this.slotDelay[slot] = SLOT_DELAY;
			return null;
		}
		
		if((PREFIX_FUNCTION + "checkavailability").equals(name) && params.length == 3) {
			int itemId = IRORInteractive.parseInt(params[0], 0, Short.MAX_VALUE);
			int meta = IRORInteractive.parseInt(params[1], 0, Short.MAX_VALUE);
			String ret = params[2];
			long availability = this.getAvailability(itemId, meta);
			RTTYSystem.broadcast(worldObj, ret, availability + "");
			return null;
		}
		
		return null;
	}
}
