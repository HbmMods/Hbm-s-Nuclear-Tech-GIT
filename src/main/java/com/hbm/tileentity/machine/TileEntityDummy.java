package com.hbm.tileentity.machine;

import com.hbm.interfaces.IMultiblock;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityDummy extends TileEntity {

	public int targetX;
	public int targetY;
	public int targetZ;
	
    @Override
	public void updateEntity() {
    	if(!this.worldObj.isRemote) {
    		if(!(this.worldObj.getBlock(targetX, targetY, targetZ) instanceof IMultiblock)) {
    			worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
    		}
    	}
    }

    @Override
	public void readFromNBT(NBTTagCompound nbt)
    {
    	super.readFromNBT(nbt);
        this.targetX = nbt.getInteger("tx");
        this.targetY = nbt.getInteger("ty");
        this.targetZ = nbt.getInteger("tz");
    }

    @Override
	public void writeToNBT(NBTTagCompound nbt)
    {
    	super.writeToNBT(nbt);
    	nbt.setInteger("tx", this.targetX);
    	nbt.setInteger("ty", this.targetY);
    	nbt.setInteger("tz", this.targetZ);
    }
}
