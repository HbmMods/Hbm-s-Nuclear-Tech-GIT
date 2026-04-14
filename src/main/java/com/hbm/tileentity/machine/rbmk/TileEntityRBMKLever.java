package com.hbm.tileentity.machine.rbmk;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.gui.GUIScreenRBMKLever;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.network.RTTYSystem;
import com.hbm.util.BufferUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityRBMKLever extends TileEntityLoadedBase implements IGUIProvider, IControlReceiver {
	
	/*    __________
	 *   /         /|
	 *  /________ / |
	 * | __   __ |  |
	 * ||/\| |/\||  |
	 * ||--| |--||  |
	 * ||__| |__|| /
	 * |_________|/
	 */
	
	public LeverUnit[] levers = new LeverUnit[2];
	
	public TileEntityRBMKLever() {
		for(int i = 0; i < 2; i++) this.levers[i] = new LeverUnit(i);
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			for(int i = 0; i < 2; i++) this.levers[i].update();
			
			this.networkPackNT(50);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		for(int i = 0; i < 2; i++) this.levers[i].serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		for(int i = 0; i < 2; i++) this.levers[i].deserialize(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		for(int i = 0; i < 2; i++) this.levers[i].readFromNBT(nbt, i);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		for(int i = 0; i < 2; i++) this.levers[i].writeToNBT(nbt, i);
	}

	public class LeverUnit {
		
		/** If the output should be per tick, allows the lever to lock in place */
		public boolean polling;
		/** If the lever is flipped, assuming polling is enabled */
		public boolean isFlipped;
		/** Label on the lever as rendered on the panel */
		public String label = "";
		/** What channel to send the command over */
		public String rtty = "";
		/** What to send when pressed */
		public String command = "";
		/** Whether this lever is enabled and can be pressed */
		public boolean active;
		/** For non-polling levers, the time until the lever returns */
		public int holdTimer;
		
		public LeverUnit(int initialIndex) {
			label = "Lever " + (initialIndex + 1);
		}
		
		public void click() {
			if(!active) return;
			
			if(!polling) {
				if(canSend()) RTTYSystem.broadcast(worldObj, rtty, command);
				this.isFlipped = true;
				this.holdTimer = 10;
			} else {
				this.isFlipped = !this.isFlipped;
				TileEntityRBMKLever.this.markDirty();
			}
			
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "random.click", 1F, this.isFlipped ? 1F : 0.75F);
		}
		
		public void update() {
			if(!active) return;
			
			if(polling && isFlipped) {
				if(canSend()) RTTYSystem.broadcast(worldObj, rtty, command);
			}
			
			if(!polling && isFlipped) {
				if(this.holdTimer-- <= 0) {
					this.isFlipped = false;
				}
			}
		}
		
		public boolean canSend() {
			return rtty != null && !rtty.isEmpty() && command != null && !command.isEmpty();
		}

		public void serialize(ByteBuf buf) {
			buf.writeBoolean(active);
			buf.writeBoolean(polling);
			buf.writeBoolean(isFlipped);
			BufferUtil.writeString(buf, label);
			BufferUtil.writeString(buf, rtty);
			BufferUtil.writeString(buf, command);
		}

		public void deserialize(ByteBuf buf) {
			active = buf.readBoolean();
			polling = buf.readBoolean();
			isFlipped = buf.readBoolean();
			label = BufferUtil.readString(buf);
			rtty = BufferUtil.readString(buf);
			command = BufferUtil.readString(buf);
		}

		public void readFromNBT(NBTTagCompound nbt, int index) {
			this.active = nbt.getBoolean("active" + index);
			this.polling = nbt.getBoolean("polling" + index);
			this.isFlipped = nbt.getBoolean("isFlipped" + index);
			this.label = nbt.getString("label" + index);
			this.rtty = nbt.getString("rtty" + index);
			this.command = nbt.getString("command" + index);
		}

		public void writeToNBT(NBTTagCompound nbt, int index) {
			nbt.setBoolean("active" + index, active);
			nbt.setBoolean("polling" + index, polling);
			nbt.setBoolean("isFlipped" + index, isFlipped);
			nbt.setString("label" + index, label);
			nbt.setString("rtty" + index, rtty);
			nbt.setString("command" + index, command);
		}
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return null; }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIScreenRBMKLever(this); }

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 15 * 15;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {

		int active = data.getByte("active");
		int polling = data.getByte("polling");
		for(int i = 0; i < 2; i++) {
			this.levers[i].active = (active & (1 << i)) != 0;
			this.levers[i].polling = (polling & (1 << i)) != 0;
		}
		
		for(int i = 0; i < 2; i++) {
			LeverUnit lever = this.levers[i];
			lever.label = data.getString("label" + i);
			lever.rtty = data.getString("rtty" + i);
			lever.command = data.getString("cmd" + i);
		}
	}
}
