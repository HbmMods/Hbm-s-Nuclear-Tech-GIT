package com.hbm.tileentity.machine;

import com.hbm.blocks.machine.BlockHadronPower;
import api.hbm.energy.IEnergyConsumer;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityHadronPower extends TileEntity implements IEnergyConsumer {

	public long power;

	public boolean canUpdate() {
		return false;
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
