package com.hbm.tileentity.machine.rbmk;

import com.hbm.handler.CompatHandler;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.gui.GUIScreenRBMKIndicator;
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
public class TileEntityRBMKIndicator extends TileEntityLoadedBase implements IGUIProvider, IControlReceiver, SimpleComponent, CompatHandler.OCComponent {
	
	/*    __________
	 *   /         /|
	 *  /________ / |
	 * | __   __ |  |
	 * ||__| |__||  |
	 * ||__| |__||  |
	 * ||__| |__|| /
	 * |_________|/
	 */
	
	public IndicatorUnit[] indicators = new IndicatorUnit[6];
	
	public TileEntityRBMKIndicator() {
		for(int i = 0; i < 6; i++) this.indicators[i] = new IndicatorUnit(i);
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			for(int i = 0; i < 6; i++) this.indicators[i].update();
			
			this.networkPackNT(50);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		for(int i = 0; i < 6; i++) this.indicators[i].serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		for(int i = 0; i < 6; i++) this.indicators[i].deserialize(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		for(int i = 0; i < 6; i++) this.indicators[i].readFromNBT(nbt, i);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		for(int i = 0; i < 6; i++) this.indicators[i].writeToNBT(nbt, i);
	}

	public class IndicatorUnit {
		
		/** If the value should be pulled from the RTTY system every tick, otherwise only on state change */
		public boolean polling;
		/** Color of the indicator light */
		public int color;
		/** Label on the indicator */
		public String label = "";
		/** What channel to read values from */
		public String rtty = "";
		/** Lower bound for the indicator to light up */
		public long min = 0;
		/** Upper bound for the indicator to light up */
		public long max = 100;
		/** Whether the indicator is on or off */
		public boolean light;
		/** Whether this indicator is visible on the panel */
		public boolean active;
		
		public IndicatorUnit(int initialIndex) {
			if(initialIndex % 2 == 0)
				color = 0xff0000;
			else
				color = 0xffff00;
			label = "Indicator " + (initialIndex + 1);
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
				decideLight(sigVal);
			} else {
				// if there's no new signal and we're polling, set to 0
				if(polling) decideLight(0);
			}
		}
		
		// if max is lower than min, inverse the operation, i.e. make the indicator light up outside of the bounds
		private void decideLight(long value) {
			if(min <= max) {
				light = value >= min && value <= max;
			} else {
				light = value < max || value > min;
			}
		}

		public void serialize(ByteBuf buf) {
			buf.writeBoolean(active);
			buf.writeBoolean(polling);
			buf.writeBoolean(light);
			buf.writeInt(color);
			BufferUtil.writeString(buf, label);
			BufferUtil.writeString(buf, rtty);
			buf.writeLong(min);
			buf.writeLong(max);
		}

		public void deserialize(ByteBuf buf) {
			active = buf.readBoolean();
			polling = buf.readBoolean();
			light = buf.readBoolean();
			color = buf.readInt();
			label = BufferUtil.readString(buf);
			rtty = BufferUtil.readString(buf);
			min = buf.readLong();
			max = buf.readLong();
		}

		public void readFromNBT(NBTTagCompound nbt, int index) {
			this.active = nbt.getBoolean("active" + index);
			this.polling = nbt.getBoolean("polling" + index);
			this.color = nbt.getInteger("color" + index);
			this.label = nbt.getString("label" + index);
			this.rtty = nbt.getString("rtty" + index);
			this.min = nbt.getLong("min" + index);
			this.max = nbt.getLong("max" + index);
			this.light = nbt.getBoolean("light" + index);
		}

		public void writeToNBT(NBTTagCompound nbt, int index) {
			nbt.setBoolean("active" + index, active);
			nbt.setBoolean("polling" + index, polling);
			nbt.setInteger("color" + index, color);
			nbt.setString("label" + index, label);
			nbt.setString("rtty" + index, rtty);
			nbt.setLong("min" + index, min);
			nbt.setLong("max" + index, max);
			nbt.setBoolean("light" + index, light);
		}
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return null; }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIScreenRBMKIndicator(this); }

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 15 * 15;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		int active = data.getByte("active");
		int polling = data.getByte("polling");
		for(int i = 0; i < 6; i++) {
			this.indicators[i].active = (active & (1 << i)) != 0;
			this.indicators[i].polling = (polling & (1 << i)) != 0;
		}
		
		for(int i = 0; i < 6; i++) {
			IndicatorUnit indicator = this.indicators[i];
			indicator.color = MathHelper.clamp_int(data.getInteger("color" + i), 0, 0xffffff);
			indicator.label = data.getString("label" + i);
			indicator.rtty = data.getString("rtty" + i);
			indicator.min = data.hasKey("min" + i) ? data.getInteger("min" + i) : Integer.MIN_VALUE;
			indicator.max = data.hasKey("max" + i) ? data.getInteger("max" + i) : Integer.MAX_VALUE;
		}
	}

	// OpenComputers methods
	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "rbmk_indicator";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getIndicatorInfo(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 6) return new Object[] {null, "Invalid index (1-6)"};
		java.util.LinkedHashMap<String, Object> map = new java.util.LinkedHashMap<>();
		map.put("active", indicators[idx].active);
		map.put("polling", indicators[idx].polling);
		map.put("light", indicators[idx].light);
		map.put("color", indicators[idx].color);
		map.put("label", indicators[idx].label);
		map.put("channel", indicators[idx].rtty);
		map.put("min", indicators[idx].min);
		map.put("max", indicators[idx].max);
		return new Object[] {map};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setIndicatorActive(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 6) return new Object[] {false, "Invalid index (1-6)"};
		indicators[idx].active = args.checkBoolean(1);
		markDirty();
		return new Object[] {true};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setIndicatorLight(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 6) return new Object[] {false, "Invalid index (1-6)"};
		indicators[idx].light = args.checkBoolean(1);
		markDirty();
		return new Object[] {true};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setIndicatorColor(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 6) return new Object[] {false, "Invalid index (1-6)"};
		indicators[idx].color = MathHelper.clamp_int(args.checkInteger(1), 0, 0xffffff);
		markDirty();
		return new Object[] {true};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setIndicatorLabel(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 6) return new Object[] {false, "Invalid index (1-6)"};
		indicators[idx].label = args.checkString(1);
		markDirty();
		return new Object[] {true};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setIndicatorBounds(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 6) return new Object[] {false, "Invalid index (1-6)"};
		long min = (long) args.checkInteger(1);
		long max = (long) args.checkInteger(2);
		indicators[idx].min = min;
		indicators[idx].max = max;
		markDirty();
		return new Object[] {true};
	}
}
