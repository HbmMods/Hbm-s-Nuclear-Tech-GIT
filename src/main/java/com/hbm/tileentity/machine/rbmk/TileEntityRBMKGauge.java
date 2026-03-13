package com.hbm.tileentity.machine.rbmk;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.gui.GUIScreenRBMKGauge;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.network.RTTYSystem;
import com.hbm.tileentity.network.RTTYSystem.RTTYChannel;
import com.hbm.util.BufferUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntityRBMKGauge extends TileEntityLoadedBase implements IGUIProvider, IControlReceiver {
	
	/*    __________
	 *   /         /|
	 *  /________ / |
	 * | __   __ |  |
	 * |(_\) (_\)|  |
	 * | __   __ |  |
	 * |(_\) (_\)| /
	 * |_________|/
	 */
	
	public GaugeUnit[] gauges = new GaugeUnit[4];
	
	public TileEntityRBMKGauge() {
		for(int i = 0; i < 4; i++) this.gauges[i] = new GaugeUnit(i);
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			for(int i = 0; i < 4; i++) this.gauges[i].update();
			
			this.networkPackNT(50);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		for(int i = 0; i < 4; i++) this.gauges[i].serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		for(int i = 0; i < 4; i++) this.gauges[i].deserialize(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		for(int i = 0; i < 4; i++) this.gauges[i].readFromNBT(nbt, i);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		for(int i = 0; i < 4; i++) this.gauges[i].writeToNBT(nbt, i);
	}

	public class GaugeUnit {
		
		/** If the value should be pulled from the RTTY system every tick, otherwise only on state change */
		public boolean polling;
		/** Color of the gauge needle */
		public int color;
		/** Label on the gauge as rendered on the panel */
		public String label = "";
		/** What channel to read values from */
		public String rtty = "";
		/** The minium value handled by the gauge */
		public int min = 0;
		/** The maximum value of the gauge, i.e. where the red area begins */
		public int max = 100;
		/** The current read value of the gauge, i.e. the needle position */
		public int value;
		/** Whether this gauge is visible on the panel */
		public boolean active;
		
		public GaugeUnit(int initialIndex) {
			if(initialIndex == 0) color = 0x800000;
			if(initialIndex == 1) color = 0x804000;
			if(initialIndex == 2) color = 0x808000;
			if(initialIndex == 3) color = 0x000080;
			label = "Gauge " + (initialIndex + 1);
		}
		
		public void update() {
			if(!active) return;
			if(rtty == null || rtty.isEmpty()) return;
			
			RTTYChannel chan = RTTYSystem.listen(worldObj, rtty);
			int sigVal = 0;
			
			if(chan != null && chan.timeStamp < worldObj.getTotalWorldTime() - 1) chan = null;
			
			// always accept new signals
			if(chan != null && chan.signal != null) {
				try { sigVal = Integer.parseInt(chan.signal.toString()); } catch(Exception ex) { }
				this.value = sigVal;
			} else {
				// if there's no new signal and we're polling, set to 0
				if(polling) this.value = 0;
			}
		}

		public void serialize(ByteBuf buf) {
			buf.writeBoolean(active);
			buf.writeBoolean(polling);
			buf.writeInt(color);
			BufferUtil.writeString(buf, label);
			BufferUtil.writeString(buf, rtty);
			buf.writeInt(min);
			buf.writeInt(max);
			buf.writeInt(value);
		}

		public void deserialize(ByteBuf buf) {
			active = buf.readBoolean();
			polling = buf.readBoolean();
			color = buf.readInt();
			label = BufferUtil.readString(buf);
			rtty = BufferUtil.readString(buf);
			min = buf.readInt();
			max = buf.readInt();
			value = buf.readInt();
		}

		public void readFromNBT(NBTTagCompound nbt, int index) {
			this.active = nbt.getBoolean("active" + index);
			this.polling = nbt.getBoolean("polling" + index);
			this.color = nbt.getInteger("color" + index);
			this.label = nbt.getString("label" + index);
			this.rtty = nbt.getString("rtty" + index);
			this.min = nbt.getInteger("min" + index);
			this.max = nbt.getInteger("max" + index);
			this.value = nbt.getInteger("value" + index);
		}

		public void writeToNBT(NBTTagCompound nbt, int index) {
			nbt.setBoolean("active" + index, active);
			nbt.setBoolean("polling" + index, polling);
			nbt.setInteger("color" + index, color);
			nbt.setString("label" + index, label);
			nbt.setString("rtty" + index, rtty);
			nbt.setInteger("min" + index, min);
			nbt.setInteger("max" + index, max);
			nbt.setInteger("value" + index, value);
		}
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return null; }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIScreenRBMKGauge(this); }

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 15 * 15;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		int active = data.getByte("active");
		int polling = data.getByte("polling");
		for(int i = 0; i < 4; i++) {
			this.gauges[i].active = (active & (1 << i)) != 0;
			this.gauges[i].polling = (polling & (1 << i)) != 0;
		}
		
		for(int i = 0; i < 4; i++) {
			GaugeUnit gauge = this.gauges[i];
			gauge.color = MathHelper.clamp_int(data.getInteger("color" + i), 0, 0xffffff);
			gauge.label = data.getString("label" + i);
			gauge.rtty = data.getString("rtty" + i);
			gauge.min = data.getInteger("min" + i);
			gauge.max = data.getInteger("max" + i);
		}
	}
}
