package com.hbm.tileentity.network.pneumatic;


import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerPneumoStorageExporter;
import com.hbm.inventory.gui.GUIPneumoStorageExporter;
import com.hbm.tileentity.network.RTTYSystem;

import api.hbm.ntl.StackCache.CacheSlot;
import api.hbm.redstoneoverradio.IRORInteractive;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
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

	/** Each slot individually tries to pull as much as it can of the configured item */
	public static final int MODE_AS_MUCH_AS_POSSIBLE = 0;
	/** Each slot individually tries to pull the exact quantity configured */
	public static final int MODE_FULL_STACK = 1;
	/** All request slots try to pull the desired quantities simultaneously */
	public static final int MODE_FULL_REQUEST = 2;

	public TileEntityPneumoStorageExporter() {
		super(18);
	}

	@Override
	public String getName() {
		return "container.pneumoStorageExporter";
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!worldObj.isRemote) {
			this.networkPackNT(15);
		}
	}
	
	public void doRequest(boolean force) {
		
	}
	
	public void requestSlot(int slot, boolean force) {
		
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
		
		if("setfilter".equals(name) && params.length == 4) {
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
		
		if("setcontinuous".equals(name) && params.length == 1) {
			if("on".equals(params[0])) this.continuousRequest = true;
			if("off".equals(params[0])) this.continuousRequest = false;
			this.markChanged();
			return null;
		}
		
		if("request".equals(name)) {
			this.doRequest(true);
			return null;
		}
		
		if("requestslot".equals(name) && params.length == 1) {
			int slot = IRORInteractive.parseInt(params[0], 1, 9) - 1;
			this.requestSlot(slot, true);
			return null;
		}
		
		if("checkavailability".equals(name) && params.length == 3) {
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
