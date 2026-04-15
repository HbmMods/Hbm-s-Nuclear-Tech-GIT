package com.hbm.tileentity.machine.rbmk;

import com.hbm.handler.CompatHandler;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.gui.GUIScreenRBMKKeyPad;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.network.RTTYSystem;
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
public class TileEntityRBMKKeyPad extends TileEntityLoadedBase implements IGUIProvider, IControlReceiver, SimpleComponent, CompatHandler.OCComponent {
	
	/*    __________
	 *   /         /|
	 *  /________ / |
	 * | __   __ |  |
	 * ||__| |__||  |
	 * | __   __ |  |
	 * ||__| |__|| /
	 * |_________|/
	 */
	
	public KeyUnit[] keys = new KeyUnit[4];
	
	public TileEntityRBMKKeyPad() {
		for(int i = 0; i < 4; i++) this.keys[i] = new KeyUnit(i);
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			for(int i = 0; i < 4; i++) this.keys[i].update();
			
			this.networkPackNT(50);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		for(int i = 0; i < 4; i++) this.keys[i].serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		for(int i = 0; i < 4; i++) this.keys[i].deserialize(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		for(int i = 0; i < 4; i++) this.keys[i].readFromNBT(nbt, i);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		for(int i = 0; i < 4; i++) this.keys[i].writeToNBT(nbt, i);
	}

	public class KeyUnit {
		
		/** If the output should be per tick, allows the "is pressed" state */
		public boolean polling;
		/** If the button is toggled, assuming polling is enabled */
		public boolean isPressed;
		/** Color of the button as rendered on the panel */
		public int color;
		/** Label on the button as rendered on the panel */
		public String label = "";
		/** What channel to send the command over */
		public String rtty = "";
		/** What to send when pressed */
		public String command = "";
		/** Whether this button is enabled and can be pressed */
		public boolean active;
		/** For non-polling buttons, the time until the button visually upresses */
		public int clickTimer;
		
		public KeyUnit(int initialIndex) {
			if(initialIndex == 0) color = 0xff0000;
			if(initialIndex == 1) color = 0xffff00;
			if(initialIndex == 2) color = 0x0080ff;
			if(initialIndex == 3) color = 0x00ff00;
			label = "Button " + (initialIndex + 1);
		}
		
		public void click() {
			if(!active) return;
			
			if(!polling) {
				if(canSend()) RTTYSystem.broadcast(worldObj, rtty, command);
				this.isPressed = true;
				this.clickTimer = 7;
			} else {
				this.isPressed = !this.isPressed;
				TileEntityRBMKKeyPad.this.markDirty();
			}
			
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "random.click", 1F, this.isPressed ? 1F : 0.75F);
		}
		
		public void update() {
			if(!active) return;
			
			if(polling && isPressed) {
				if(canSend()) RTTYSystem.broadcast(worldObj, rtty, command);
			}
			
			if(!polling && isPressed) {
				if(this.clickTimer-- <= 0) {
					this.isPressed = false;
				}
			}
		}
		
		public boolean canSend() {
			return rtty != null && !rtty.isEmpty() && command != null && !command.isEmpty();
		}

		public void serialize(ByteBuf buf) {
			buf.writeBoolean(active);
			buf.writeBoolean(polling);
			buf.writeBoolean(isPressed);
			buf.writeInt(color);
			BufferUtil.writeString(buf, label);
			BufferUtil.writeString(buf, rtty);
			BufferUtil.writeString(buf, command);
		}

		public void deserialize(ByteBuf buf) {
			active = buf.readBoolean();
			polling = buf.readBoolean();
			isPressed = buf.readBoolean();
			color = buf.readInt();
			label = BufferUtil.readString(buf);
			rtty = BufferUtil.readString(buf);
			command = BufferUtil.readString(buf);
		}

		public void readFromNBT(NBTTagCompound nbt, int index) {
			this.active = nbt.getBoolean("active" + index);
			this.polling = nbt.getBoolean("polling" + index);
			this.isPressed = nbt.getBoolean("isPressed" + index);
			this.color = nbt.getInteger("color" + index);
			this.label = nbt.getString("label" + index);
			this.rtty = nbt.getString("rtty" + index);
			this.command = nbt.getString("command" + index);
		}

		public void writeToNBT(NBTTagCompound nbt, int index) {
			nbt.setBoolean("active" + index, active);
			nbt.setBoolean("polling" + index, polling);
			nbt.setBoolean("isPressed" + index, isPressed);
			nbt.setInteger("color" + index, color);
			nbt.setString("label" + index, label);
			nbt.setString("rtty" + index, rtty);
			nbt.setString("command" + index, command);
		}
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return null; }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIScreenRBMKKeyPad(this); }

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 15 * 15;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {

		int active = data.getByte("active");
		int polling = data.getByte("polling");
		for(int i = 0; i < 4; i++) {
			this.keys[i].active = (active & (1 << i)) != 0;
			this.keys[i].polling = (polling & (1 << i)) != 0;
		}
		
		for(int i = 0; i < 4; i++) {
			KeyUnit key = this.keys[i];
			key.color = MathHelper.clamp_int(data.getInteger("color" + i), 0, 0xffffff);
			key.label = data.getString("label" + i);
			key.rtty = data.getString("rtty" + i);
			key.command = data.getString("cmd" + i);
		}
	}

	// OpenComputers methods
	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "rbmk_keypad";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getKeyInfo(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 4) return new Object[] {null, "Invalid index (1-4)"};
		java.util.LinkedHashMap<String, Object> map = new java.util.LinkedHashMap<>();
		map.put("active", keys[idx].active);
		map.put("polling", keys[idx].polling);
		map.put("pressed", keys[idx].isPressed);
		map.put("color", keys[idx].color);
		map.put("label", keys[idx].label);
		map.put("channel", keys[idx].rtty);
		map.put("command", keys[idx].command);
		return new Object[] {map};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setKeyActive(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 4) return new Object[] {false, "Invalid index (1-4)"};
		keys[idx].active = args.checkBoolean(1);
		markDirty();
		return new Object[] {true};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setKeyPolling(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 4) return new Object[] {false, "Invalid index (1-4)"};
		keys[idx].polling = args.checkBoolean(1);
		markDirty();
		return new Object[] {true};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setKeyColor(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 4) return new Object[] {false, "Invalid index (1-4)"};
		keys[idx].color = MathHelper.clamp_int(args.checkInteger(1), 0, 0xffffff);
		markDirty();
		return new Object[] {true};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setKeyLabel(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 4) return new Object[] {false, "Invalid index (1-4)"};
		keys[idx].label = args.checkString(1);
		markDirty();
		return new Object[] {true};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setKeyChannel(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 4) return new Object[] {false, "Invalid index (1-4)"};
		keys[idx].rtty = args.checkString(1);
		markDirty();
		return new Object[] {true};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setKeyCommand(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 4) return new Object[] {false, "Invalid index (1-4)"};
		keys[idx].command = args.checkString(1);
		markDirty();
		return new Object[] {true};
	}

	@Callback(direct = true, limit = 1)
	@Optional.Method(modid = "OpenComputers")
	public Object[] pressKey(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 4) return new Object[] {false, "Invalid index (1-4)"};
		if(!keys[idx].active) return new Object[] {false, "Key is not active"};
		keys[idx].click();
		markDirty();
		return new Object[] {true};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getKeyPressed(Context context, Arguments args) {
		int idx = args.checkInteger(0) - 1;
		if(idx < 0 || idx >= 4) return new Object[] {false, "Invalid index (1-4)"};
		return new Object[] {keys[idx].isPressed};
	}
}
