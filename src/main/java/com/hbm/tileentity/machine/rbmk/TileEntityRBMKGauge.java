package com.hbm.tileentity.machine.rbmk;

import com.hbm.handler.CompatHandler;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.gui.GUIScreenRBMKGauge;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.network.RTTYSystem;
import com.hbm.tileentity.network.RTTYSystem.RTTYChannel;
import com.hbm.util.BufferUtil;

import cpw.mods.fml.common.Optional;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityRBMKGauge extends TileEntityLoadedBase implements IGUIProvider, IControlReceiver, SimpleComponent, CompatHandler.OCComponent {
	
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
		} else {

			for(int i = 0; i < 4; i++) this.gauges[i].updateClient();
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
		public long min = 0;
		/** The maximum value of the gauge, i.e. where the red area begins */
		public long max = 100;
		/** The current read value of the gauge, i.e. the needle position */
		public long value;
		/** For smoothig */
		public double renderValue;
		public double lastRenderValue;
		/** Whether this gauge is visible on the panel */
		public boolean active;
		
		public GaugeUnit(int initialIndex) {
			if(initialIndex == 0) color = 0x800000;
			if(initialIndex == 1) color = 0x804000;
			if(initialIndex == 2) color = 0x808000;
			if(initialIndex == 3) color = 0x000080;
			label = "Gauge " + (initialIndex + 1);
		}
		
		public void updateClient() {
			this.lastRenderValue = this.renderValue;
			double delta = value - renderValue;
			this.renderValue += delta * 0.1D;
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
			buf.writeLong(min);
			buf.writeLong(max);
			buf.writeLong(value);
		}

		public void deserialize(ByteBuf buf) {
			active = buf.readBoolean();
			polling = buf.readBoolean();
			color = buf.readInt();
			label = BufferUtil.readString(buf);
			rtty = BufferUtil.readString(buf);
			min = buf.readLong();
			max = buf.readLong();
			value = buf.readLong();
		}

		public void readFromNBT(NBTTagCompound nbt, int index) {
			this.active = nbt.getBoolean("active" + index);
			this.polling = nbt.getBoolean("polling" + index);
			this.color = nbt.getInteger("color" + index);
			this.label = nbt.getString("label" + index);
			this.rtty = nbt.getString("rtty" + index);
			this.min = nbt.getLong("min" + index);
			this.max = nbt.getLong("max" + index);
			this.value = nbt.getLong("value" + index);
		}

		public void writeToNBT(NBTTagCompound nbt, int index) {
			nbt.setBoolean("active" + index, active);
			nbt.setBoolean("polling" + index, polling);
			nbt.setInteger("color" + index, color);
			nbt.setString("label" + index, label);
			nbt.setString("rtty" + index, rtty);
			nbt.setLong("min" + index, min);
			nbt.setLong("max" + index, max);
			nbt.setLong("value" + index, value);
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

	// OpenComputers methods
	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "rbmk_gauge";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getGaugeInfo(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 4) return new Object[] {null, "Invalid index (1-4)"};
		java.util.LinkedHashMap<String, Object> map = new java.util.LinkedHashMap<>();
		map.put("active", gauges[idx].active);
		map.put("polling", gauges[idx].polling);
		map.put("color", gauges[idx].color);
		map.put("label", gauges[idx].label);
		map.put("channel", gauges[idx].rtty);
		map.put("min", gauges[idx].min);
		map.put("max", gauges[idx].max);
		map.put("value", gauges[idx].value);
		return new Object[] {map};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setGaugeActive(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 4) return new Object[] {false, "Invalid index (1-4)"};
		gauges[idx].active = args.checkBoolean(1);
		markDirty();
		return new Object[] {true};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setGaugePolling(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 4) return new Object[] {false, "Invalid index (1-4)"};
		gauges[idx].polling = args.checkBoolean(1);
		markDirty();
		return new Object[] {true};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setGaugeColor(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 4) return new Object[] {false, "Invalid index (1-4)"};
		gauges[idx].color = MathHelper.clamp_int(args.checkInteger(1), 0, 0xffffff);
		markDirty();
		return new Object[] {true};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setGaugeLabel(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 4) return new Object[] {false, "Invalid index (1-4)"};
		gauges[idx].label = args.checkString(1);
		markDirty();
		return new Object[] {true};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setGaugeChannel(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 4) return new Object[] {false, "Invalid index (1-4)"};
		gauges[idx].rtty = args.checkString(1);
		markDirty();
		return new Object[] {true};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setGaugeMin(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 4) return new Object[] {false, "Invalid index (1-4)"};
		gauges[idx].min = (long) args.checkInteger(1);
		markDirty();
		return new Object[] {true};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setGaugeMax(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 4) return new Object[] {false, "Invalid index (1-4)"};
		gauges[idx].max = (long) args.checkInteger(1);
		markDirty();
		return new Object[] {true};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setGaugeValue(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 4) return new Object[] {false, "Invalid index (1-4)"};
		gauges[idx].value = (long) args.checkInteger(1);
		markDirty();
		return new Object[] {true};
	}
}
