package com.hbm.tileentity.machine.rbmk;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.gui.GUIScreenRBMKDisplay;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.network.RTTYSystem;
import com.hbm.tileentity.network.RTTYSystem.RTTYChannel;
import com.hbm.util.BufferUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityRBMKNumitron extends TileEntityLoadedBase implements IGUIProvider, IControlReceiver {
	
	/*    __________
	 *   /         /|
	 *  /________ / |
	 * | _______ |  |
	 * ||_______||  |
	 * | _______ |  |
	 * ||_______|| /
	 * |_________|/
	 */
	
	public DisplayUnit[] displays = new DisplayUnit[2];
	
	public TileEntityRBMKNumitron() {
		for(int i = 0; i < 2; i++) this.displays[i] = new DisplayUnit(i);
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			for(int i = 0; i < 2; i++) this.displays[i].update();
			
			this.networkPackNT(50);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		for(int i = 0; i < 2; i++) this.displays[i].serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		for(int i = 0; i < 2; i++) this.displays[i].deserialize(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		for(int i = 0; i < 2; i++) this.displays[i].readFromNBT(nbt, i);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		for(int i = 0; i < 2; i++) this.displays[i].writeToNBT(nbt, i);
	}

	public class DisplayUnit {

		/** If the value should be pulled from the RTTY system every tick, otherwise only on state change */
		public boolean polling;
		/** Label on the display as rendered on the panel */
		public String label = "";
		/** What channel to read values from */
		public String rtty = "";
		/** The current read value on the display */
		public long value;
		/** Whether this display is visible on the panel */
		public boolean active;
		
		public DisplayUnit(int initialIndex) {
			label = "Display " + (initialIndex + 1);
		}
		
		public void update() {
			if(!active) return;
			if(rtty == null || rtty.isEmpty()) return;
			
			RTTYChannel chan = RTTYSystem.listen(worldObj, rtty);
			long sigVal = 0;
			
			if(chan != null && chan.timeStamp < worldObj.getTotalWorldTime() - 1) chan = null;
			
			// always accept new signals
			if(chan != null && chan.signal != null) {
				try { sigVal = Long.parseLong(chan.signal.toString()); } catch(Exception ex) { }
				this.value = sigVal;
			} else {
				// if there's no new signal and we're polling, set to 0
				if(polling) this.value = 0;
			}
		}

		public void serialize(ByteBuf buf) {
			buf.writeBoolean(active);
			buf.writeBoolean(polling);
			BufferUtil.writeString(buf, label);
			BufferUtil.writeString(buf, rtty);
			buf.writeLong(value);
		}

		public void deserialize(ByteBuf buf) {
			active = buf.readBoolean();
			polling = buf.readBoolean();
			label = BufferUtil.readString(buf);
			rtty = BufferUtil.readString(buf);
			value = buf.readLong();
		}

		public void readFromNBT(NBTTagCompound nbt, int index) {
			this.active = nbt.getBoolean("active" + index);
			this.polling = nbt.getBoolean("polling" + index);
			this.label = nbt.getString("label" + index);
			this.rtty = nbt.getString("rtty" + index);
			this.value = nbt.getLong("value" + index);
		}

		public void writeToNBT(NBTTagCompound nbt, int index) {
			nbt.setBoolean("active" + index, active);
			nbt.setBoolean("polling" + index, polling);
			nbt.setString("label" + index, label);
			nbt.setString("rtty" + index, rtty);
			nbt.setLong("value" + index, value);
		}
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return null; }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIScreenRBMKDisplay(this); }

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 15 * 15;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		int active = data.getByte("active");
		int polling = data.getByte("polling");
		for(int i = 0; i < 2; i++) {
			this.displays[i].active = (active & (1 << i)) != 0;
			this.displays[i].polling = (polling & (1 << i)) != 0;
		}
		
		for(int i = 0; i < 2; i++) {
			DisplayUnit display = this.displays[i];
			display.label = data.getString("label" + i);
			display.rtty = data.getString("rtty" + i);
		}
	}
}
