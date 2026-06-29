package com.hbm.tileentity.network.pneumatic;


import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerPneumoStorageExporter;
import com.hbm.inventory.gui.GUIPneumoStorageExporter;

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
	/** Item ID and meta pairs for RoR controlled filters */
	public short[][] rorFilters = new short[9][2];

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

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeBoolean(continuousRequest);
		buf.writeBoolean(rorConfiguredMode);
		buf.writeByte((byte) requestMode);
		for(int i = 0; i < 9; i++) {
			buf.writeShort(rorFilters[i][0]);
			buf.writeShort(rorFilters[i][1]);
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
				PREFIX_FUNCTION + "setcontinuous" + NAME_SEPARATOR + "slot" + PARAM_SEPARATOR + "itemid" + PARAM_SEPARATOR + "itemmeta" + PARAM_SEPARATOR + "amount",
				PREFIX_FUNCTION + "request",
				PREFIX_FUNCTION + "requestslot" + NAME_SEPARATOR + "slot",
				PREFIX_FUNCTION + "checkavailability" + NAME_SEPARATOR + "itemid" + PARAM_SEPARATOR + "itemmeta" + PARAM_SEPARATOR + "returnchannel",
		};
	}

	@Override
	public String runRORFunction(String name, String[] params) {
		return null;
	}
}
