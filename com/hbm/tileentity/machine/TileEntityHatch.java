package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.BlockSeal;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityHatch extends TileEntity {

	public int x;
	public int y;
	public int z;

	@Override
	public void updateEntity() {
		
		Block b = worldObj.getBlock(x, y, z);
		
		if(b != ModBlocks.seal_controller && !worldObj.isRemote) {
			this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Blocks.air);
		} else {
			if(BlockSeal.getFrameSize(worldObj, x, y, z) == 0 && !worldObj.isRemote)
				this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Blocks.air);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound p_145839_1_) {
		this.x = p_145839_1_.getInteger("x1");
		this.y = p_145839_1_.getInteger("y1");
		this.z = p_145839_1_.getInteger("z1");
	}

	@Override
	public void writeToNBT(NBTTagCompound p_145841_1_) {
		p_145841_1_.setInteger("x1", this.x);
		p_145841_1_.setInteger("y1", this.y);
		p_145841_1_.setInteger("z1", this.z);
	}
	
	public void setControllerPos(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

}
