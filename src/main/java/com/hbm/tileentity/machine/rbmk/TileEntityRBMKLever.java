package com.hbm.tileentity.machine.rbmk;

import com.hbm.handler.threading.PacketThreading;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.gui.GUIScreenRBMKLever;
import com.hbm.main.NTMSounds;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.network.RTTYSystem;
import com.hbm.util.BufferUtil;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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
		} else {
			for(int i = 0; i < 2; i++) this.levers[i].updateClient();
		}
	}

	@Override public void serialize(ByteBuf buf) { super.serialize(buf); for(int i = 0; i < 2; i++) this.levers[i].serialize(buf); }
	@Override public void deserialize(ByteBuf buf) { super.deserialize(buf); for(int i = 0; i < 2; i++) this.levers[i].deserialize(buf); }
	@Override public void readFromNBT(NBTTagCompound nbt) { super.readFromNBT(nbt); for(int i = 0; i < 2; i++) this.levers[i].readFromNBT(nbt, i); }
	@Override public void writeToNBT(NBTTagCompound nbt) { super.writeToNBT(nbt); for(int i = 0; i < 2; i++) this.levers[i].writeToNBT(nbt, i); }

	public class LeverUnit {
		
		public int index;
		/** If the output should be per tick, allows the lever to lock in place */
		public boolean polling;
		/** Label on the lever as rendered on the panel */
		public String label = "";
		/** What channel to send the command over */
		public String rtty = "";
		/** What to send when flipped */
		public String commandOn = "";
		/** What to send when not flipped */
		public String commandOff = "";
		/** Whether this lever is enabled and can be pressed */
		public boolean active;
		/** If true, the lever tries to move down to the ON state, otherwise, tries to return to OFF */
		public boolean isTurningOn = false;
		public float flipProgress;
		public float prevFlipProgress;
		public float flipSync;
		public int turnProgress;
		public static final float FLIP_SPEED = 1F / 10F; // 0.5s
		
		public LeverUnit(int initialIndex) {
			this.index = initialIndex;
			label = "Lever " + (initialIndex + 1);
		}
		
		public void click() {
			if(!active) return;
			
			if(this.flipProgress <= 0 || this.flipProgress >= 1)
				worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, NTMSounds.LEVER_START, 1F, 1F);
			
			this.isTurningOn = !isTurningOn;
			TileEntityRBMKLever.this.markDirty();
		}
		
		public void update() {
			this.prevFlipProgress = this.flipProgress;
			if(!active) return;
			
			boolean arcFlash = false;
			
			if(polling) {
				if(this.flipProgress >= 1F && canSend(commandOn)) RTTYSystem.broadcast(worldObj, rtty, commandOn);
				if(this.flipProgress <= 0F && canSend(commandOff)) RTTYSystem.broadcast(worldObj, rtty, commandOff);
			}
			
			// turning on...
			if(this.isTurningOn && this.flipProgress < 1F) {
				this.flipProgress += FLIP_SPEED;
				if(this.flipProgress >= 1F) {
					this.flipProgress = 1F;
					// for non-polling levers, send one message
					if(!polling && canSend(commandOn)) RTTYSystem.broadcast(worldObj, rtty, commandOn);
					worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, NTMSounds.LEVER_STOP, 0.5F, 1F);
					arcFlash = true;
				}
			
			// turning off...
			} else if(!this.isTurningOn && this.flipProgress > 0F) {
				if(this.prevFlipProgress >= 1) arcFlash = true;
				this.flipProgress -= FLIP_SPEED;
				if(this.flipProgress <= 0F) {
					this.flipProgress = 0F;
					// for non-polling levers, send the off message once upon return
					if(!polling && canSend(commandOff)) RTTYSystem.broadcast(worldObj, rtty, commandOff);
					worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, NTMSounds.LEVER_STOP, 0.5F, 1F);
				}
			}
			
			if(arcFlash) {

				worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, NTMSounds.SPARK, 1F, 1F);
				ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata());
				ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
				
				for(int i = 0; i < 2; i++) {
					double x = xCoord + 0.5 + dir.offsetX * 0.4 - rot.offsetX * (index - 0.5) * (i == 0 ? 0.375 : 0.625);
					double y = yCoord + 0.4375 - 0.03125;
					double z = zCoord + 0.5 + dir.offsetZ * 0.4 - rot.offsetZ * (index - 0.5) * (i == 0 ? 0.375 : 0.625);
					
					NBTTagCompound dPart = new NBTTagCompound();
					dPart.setString("type", "tau");
					dPart.setByte("count", (byte)5);
					dPart.setBoolean("small", true);
					PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(dPart, x, y, z), new TargetPoint(worldObj.provider.dimensionId, x, y, z, 25));
				}
			}
		}
		
		public void updateClient() {
			this.prevFlipProgress = this.flipProgress;

			// approach-based interp
			if(this.turnProgress > 0) {
				this.flipProgress = this.flipProgress + ((this.flipSync - this.flipProgress) / (float) this.turnProgress);
				--this.turnProgress;
			} else {
				this.flipProgress = this.flipSync;
			}
		}

		public boolean canSend(String command) { return rtty != null && !rtty.isEmpty() && command != null && !command.isEmpty(); }

		public void serialize(ByteBuf buf) {
			buf.writeBoolean(active);
			buf.writeBoolean(polling);
			buf.writeFloat(flipProgress);
			BufferUtil.writeString(buf, label);
			BufferUtil.writeString(buf, rtty);
			BufferUtil.writeString(buf, commandOn);
			BufferUtil.writeString(buf, commandOff);
		}

		public void deserialize(ByteBuf buf) {
			active = buf.readBoolean();
			polling = buf.readBoolean();
			flipSync = buf.readFloat();
			label = BufferUtil.readString(buf);
			rtty = BufferUtil.readString(buf);
			commandOn = BufferUtil.readString(buf);
			commandOff = BufferUtil.readString(buf);
			turnProgress = 3; // three-ply for extra smoothness
		}

		public void readFromNBT(NBTTagCompound nbt, int index) {
			this.active = nbt.getBoolean("active" + index);
			this.polling = nbt.getBoolean("polling" + index);
			this.isTurningOn = nbt.getBoolean("isTurningOn" + index);
			this.flipProgress = nbt.getFloat("flipProgress" + index);
			this.label = nbt.getString("label" + index);
			this.rtty = nbt.getString("rtty" + index);
			this.commandOn = nbt.getString("commandOn" + index);
			this.commandOff = nbt.getString("commandOff" + index);
		}

		public void writeToNBT(NBTTagCompound nbt, int index) {
			nbt.setBoolean("active" + index, active);
			nbt.setBoolean("polling" + index, polling);
			nbt.setBoolean("isTurningOn" + index, isTurningOn);
			nbt.setFloat("flipProgress" + index, flipProgress);
			nbt.setString("label" + index, label);
			nbt.setString("rtty" + index, rtty);
			nbt.setString("commandOn" + index, commandOn);
			nbt.setString("commandOff" + index, commandOff);
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
			lever.commandOn = data.getString("cmdOn" + i);
			lever.commandOff = data.getString("cmdOff" + i);
		}
		
		this.markDirty();
	}
}
