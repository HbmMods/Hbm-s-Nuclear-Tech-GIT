package com.hbm.tileentity.machine.rbmk;

import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.network.RTTYSystem;
import com.hbm.util.BufferUtil;

import io.netty.buffer.ByteBuf;

public class TileEntityRBMKKeyPad extends TileEntityLoadedBase {
	
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
		for(int i = 0; i < 4; i++) this.keys[i] = new KeyUnit();
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
		
		public void click() {
			if(!polling) {
				if(canSend()) RTTYSystem.broadcast(worldObj, rtty, command);
			} else {
				this.isPressed = !this.isPressed;
				TileEntityRBMKKeyPad.this.markDirty();
			}
		}
		
		public void update() {
			if(polling && isPressed) {
				if(canSend()) RTTYSystem.broadcast(worldObj, rtty, command);
			}
		}
		
		public boolean canSend() {
			return rtty != null && !rtty.isEmpty() && command != null && !command.isEmpty();
		}

		public void serialize(ByteBuf buf) {
			buf.writeBoolean(polling);
			if(polling) buf.writeBoolean(isPressed);
			buf.writeInt(color);
			BufferUtil.writeString(buf, label);
			BufferUtil.writeString(buf, rtty);
			BufferUtil.writeString(buf, command);
		}

		public void deserialize(ByteBuf buf) {
			polling = buf.readBoolean();
			if(polling) isPressed = buf.readBoolean();
			color = buf.readInt();
			label = BufferUtil.readString(buf);
			rtty = BufferUtil.readString(buf);
			command = BufferUtil.readString(buf);
		}
	}
}
