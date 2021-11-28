package com.hbm.tileentity.machine;

import com.hbm.blocks.machine.BlockHadronPower;

import api.hbm.energy.IEnergyUser;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityHadronPower extends TileEntity implements IEnergyUser {

	public long power;

	@Override
	public boolean canUpdate() {
		return this.worldObj != null && this.worldObj.getTotalWorldTime() % 20 == 0;
	}
	
	@Override
	public void updateEntity() {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
		}
	}

	@Override
	public void setPower(long i) {
		power = i;
		this.markDirty();
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		
		Block b = this.getBlockType();
		
		if(b instanceof BlockHadronPower) {
			return ((BlockHadronPower)b).power;
		}
		
		return 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
	}
}
