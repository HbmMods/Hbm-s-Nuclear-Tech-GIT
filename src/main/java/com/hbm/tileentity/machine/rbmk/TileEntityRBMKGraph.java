package com.hbm.tileentity.machine.rbmk;

import com.hbm.handler.CompatHandler;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.gui.GUIScreenRBMKGraph;
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
import net.minecraft.world.World;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityRBMKGraph extends TileEntityLoadedBase implements IGUIProvider, IControlReceiver, SimpleComponent, CompatHandler.OCComponent {
	
	/*    __________
	 *   /         /|
	 *  /________ / |
	 * | _______ |  |
	 * ||_______||  |
	 * | _______ |  |
	 * ||_______|| /
	 * |_________|/
	 */
	
	public GraphUnit[] graphs = new GraphUnit[2];
	
	public TileEntityRBMKGraph() {
		for(int i = 0; i < 2; i++) this.graphs[i] = new GraphUnit(i);
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(worldObj.getTotalWorldTime() % 10 == 0) for(int i = 0; i < 2; i++) this.graphs[i].update();
			
			this.networkPackNT(50);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		for(int i = 0; i < 2; i++) this.graphs[i].serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		for(int i = 0; i < 2; i++) this.graphs[i].deserialize(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		for(int i = 0; i < 2; i++) this.graphs[i].readFromNBT(nbt, i);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		for(int i = 0; i < 2; i++) this.graphs[i].writeToNBT(nbt, i);
	}

	public class GraphUnit {

		/** If the value should be pulled from the RTTY system every tick, otherwise only on state change */
		public boolean polling;
		/** Label on the graph as rendered on the panel */
		public String label = "";
		/** What channel to read values from */
		public String rtty = "";
		/** The current read value on the display */
		public long[] values = new long[30]; // 2 values/s for 15 seconds
		/** Whether this graph is visible on the panel */
		public boolean active;
		
		public GraphUnit(int initialIndex) {
			label = "Graph " + (initialIndex + 1);
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
				pushValue(sigVal);
			} else {
				// if there's no new signal and we're polling, set to 0
				if(polling) pushValue(0);
			}
		}
		
		public void pushValue(long value) {
			
			for(int i = 1; i < values.length; i++) {
				values[i - 1] = values[i];
			}
			values[values.length - 1] = value;
		}

		public void serialize(ByteBuf buf) {
			buf.writeBoolean(active);
			buf.writeBoolean(polling);
			BufferUtil.writeString(buf, label);
			BufferUtil.writeString(buf, rtty);
			// original idea had the system send the min value, max value, and all values
			// crunched down to single bytes because the graph simply doesn't need this much resolution
			if(active) for(int i = 0; i < values.length; i++) buf.writeLong(values[i]);
			// was overkill though
		}

		public void deserialize(ByteBuf buf) {
			active = buf.readBoolean();
			polling = buf.readBoolean();
			label = BufferUtil.readString(buf);
			rtty = BufferUtil.readString(buf);
			if(active) for(int i = 0; i < values.length; i++) values[i] = buf.readLong();
		}

		public void readFromNBT(NBTTagCompound nbt, int index) {
			this.active = nbt.getBoolean("active" + index);
			this.polling = nbt.getBoolean("polling" + index);
			this.label = nbt.getString("label" + index);
			this.rtty = nbt.getString("rtty" + index);
			for(int i = 0; i < values.length; i++) this.values[i] = nbt.getLong("value" + index + "_" + i);
		}

		public void writeToNBT(NBTTagCompound nbt, int index) {
			nbt.setBoolean("active" + index, active);
			nbt.setBoolean("polling" + index, polling);
			nbt.setString("label" + index, label);
			nbt.setString("rtty" + index, rtty);
			for(int i = 0; i < values.length; i++) nbt.setLong("value" + index + "_" + i, values[i]);
		}
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return null; }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIScreenRBMKGraph(this); }

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 15 * 15;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		int active = data.getByte("active");
		int polling = data.getByte("polling");
		for(int i = 0; i < 2; i++) {
			this.graphs[i].active = (active & (1 << i)) != 0;
			this.graphs[i].polling = (polling & (1 << i)) != 0;
		}
		
		for(int i = 0; i < 2; i++) {
			GraphUnit graph = this.graphs[i];
			graph.label = data.getString("label" + i);
			graph.rtty = data.getString("rtty" + i);
		}
	}

	// OpenComputers methods
	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "rbmk_graph";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getGraphInfo(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 2) return new Object[] {null, "Invalid index (1-2)"};
		java.util.LinkedHashMap<String, Object> map = new java.util.LinkedHashMap<>();
		map.put("active", graphs[idx].active);
		map.put("polling", graphs[idx].polling);
		map.put("label", graphs[idx].label);
		map.put("channel", graphs[idx].rtty);
		map.put("values", graphs[idx].values);
		return new Object[] {map};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getGraphMin(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 2) return new Object[] {0L, "Invalid index (1-2)"};
		long min = Long.MAX_VALUE;
		for(long v : graphs[idx].values) if(v < min) min = v;
		return new Object[] {min == Long.MAX_VALUE ? 0 : min};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getGraphMax(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 2) return new Object[] {0L, "Invalid index (1-2)"};
		long max = Long.MIN_VALUE;
		for(long v : graphs[idx].values) if(v > max) max = v;
		return new Object[] {max == Long.MIN_VALUE ? 0 : max};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getGraphAvg(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 2) return new Object[] {0.0, "Invalid index (1-2)"};
		long sum = 0;
		int count = 0;
		for(long v : graphs[idx].values) { sum += v; count++; }
		return new Object[] {count > 0 ? (double)sum / count : 0.0};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setGraphActive(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 2) return new Object[] {false, "Invalid index (1-2)"};
		graphs[idx].active = args.checkBoolean(1);
		markDirty();
		return new Object[] {true};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setGraphPolling(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 2) return new Object[] {false, "Invalid index (1-2)"};
		graphs[idx].polling = args.checkBoolean(1);
		markDirty();
		return new Object[] {true};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setGraphLabel(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 2) return new Object[] {false, "Invalid index (1-2)"};
		graphs[idx].label = args.checkString(1);
		markDirty();
		return new Object[] {true};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setGraphChannel(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 2) return new Object[] {false, "Invalid index (1-2)"};
		graphs[idx].rtty = args.checkString(1);
		markDirty();
		return new Object[] {true};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] pushGraphValue(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 2) return new Object[] {false, "Invalid index (1-2)"};
		long val = (long) args.checkInteger(1);
		graphs[idx].pushValue(val);
		markDirty();
		return new Object[] {true};
	}
}
