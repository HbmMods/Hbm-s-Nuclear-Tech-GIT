package com.hbm.tileentity;

import com.hbm.interfaces.IBomb;
import com.hbm.interfaces.IMultiblock;
import com.hbm.interfaces.ISource;

import net.minecraft.block.BlockContainer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityDummy extends TileEntity {

	public int targetX;
	public int targetY;
	public int targetZ;
	
    public void updateEntity() {
    	if(!this.worldObj.isRemote) {
    		if(!(this.worldObj.getBlock(targetX, targetY, targetZ) instanceof IMultiblock)) {
    			worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
    		}
    	}
    }

    public void readFromNBT(NBTTagCompound nbt)
    {
    	super.readFromNBT(nbt);
        this.targetX = nbt.getInteger("tx");
        this.targetY = nbt.getInteger("ty");
        this.targetZ = nbt.getInteger("tz");
    }

    public void writeToNBT(NBTTagCompound nbt)
    {
    	super.writeToNBT(nbt);
    	nbt.setInteger("tx", this.targetX);
    	nbt.setInteger("ty", this.targetY);
    	nbt.setInteger("tz", this.targetZ);
    }
}
