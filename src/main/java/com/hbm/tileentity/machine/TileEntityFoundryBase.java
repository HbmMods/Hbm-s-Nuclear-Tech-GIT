package com.hbm.tileentity.machine;

import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;

import api.hbm.block.ICrucibleAcceptor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Base class for all foundry channel type blocks - channels, casts, basins, tanks, etc.
 * Foundry type blocks can only hold one type at a time and usually either store or move it around.
 * @author hbm
 *
 */
public abstract class TileEntityFoundryBase extends TileEntity implements ICrucibleAcceptor {
	
	public NTMMaterial type;
	protected NTMMaterial lastType;
	public int amount;
	protected int lastAmount;
	
	@Override
	public void updateEntity() {
		
		if(worldObj.isRemote) {
			
			if(shouldClientReRender() && this.lastType != this.type || this.lastAmount != this.amount) {
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				this.lastType = this.type;
				this.lastAmount = this.amount;
			}
		}
	}
	
	/** Recommended FALSE for things that update a whole lot. TRUE if updates only happen once every few ticks. */
	protected boolean shouldClientReRender() {
		return true;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.func_148857_g());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.type = Mats.matById.get(nbt.getInteger("type"));
		this.amount = nbt.getInteger("amount");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		if(this.type == null)
			nbt.setInteger("type", -1);
		else
			nbt.setInteger("type", this.type.id);
		
		nbt.setInteger("amount", this.amount);
	}
	
	public abstract int getCapacity();
}
