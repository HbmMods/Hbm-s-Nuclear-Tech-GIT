package com.hbm.tileentity.network;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCraneSplitter extends TileEntity {

	/* false: left belt is preferred, true: right belt is preferred */
	private boolean position;
	
	public void setPosition(boolean pos) {
		this.position = pos;
		this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
	}
	
	public boolean getPosition() {
		return this.position;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.position = nbt.getBoolean("pos");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("pos", this.position);
	}
}
