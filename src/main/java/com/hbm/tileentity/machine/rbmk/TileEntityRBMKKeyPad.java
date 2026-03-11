package com.hbm.tileentity.machine.rbmk;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.gui.GUIScreenRBMKKeyPad;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.network.RTTYSystem;
import com.hbm.util.BufferUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntityRBMKKeyPad extends TileEntityLoadedBase implements IGUIProvider, IControlReceiver {
	
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

	public class KeyUnit {
		
		/** If the output should be per tick, allows the "is pressed" state */
		public boolean polling;
		/** If the button is toggled, assuming polling is enabled */
		public boolean isPressed;
		/** Color of the button as rendered on the panel */
		public int color;
		/** Label on the button as rendered on the panel */
		public String label;
		/** What channel to send the command over */
		public String rtty;
		/** What to send when pressed */
		public String command;
		/** Whether this button is enabled and can be pressed */
		public boolean active;
		
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
			} else {
				this.isPressed = !this.isPressed;
				TileEntityRBMKKeyPad.this.markDirty();
			}
		}
		
		public void update() {
			if(!active) return;
			
			if(polling && isPressed) {
				if(canSend()) RTTYSystem.broadcast(worldObj, rtty, command);
			}
		}
		
		public boolean canSend() {
			return rtty != null && !rtty.isEmpty() && command != null && !command.isEmpty();
		}

		public void serialize(ByteBuf buf) {
			buf.writeBoolean(active);
			buf.writeBoolean(polling);
			if(polling) buf.writeBoolean(isPressed);
			buf.writeInt(color);
			BufferUtil.writeString(buf, label);
			BufferUtil.writeString(buf, rtty);
			BufferUtil.writeString(buf, command);
		}

		public void deserialize(ByteBuf buf) {
			active = buf.readBoolean();
			polling = buf.readBoolean();
			if(polling) isPressed = buf.readBoolean();
			color = buf.readInt();
			label = BufferUtil.readString(buf);
			rtty = BufferUtil.readString(buf);
			command = BufferUtil.readString(buf);
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
}
