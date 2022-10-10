package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyUser;
import api.hbm.tile.IHeatSource;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityHeaterElectric extends TileEntityLoadedBase implements IHeatSource, IEnergyUser, INBTPacketReceiver {
	
	public long power;
	public int heatEnergy;
	protected int setting = 0;

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(worldObj.getTotalWorldTime() % 20 == 0) { //doesn't have to happen constantly
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
				this.trySubscribe(worldObj, xCoord + dir.offsetX * 3, yCoord, zCoord + dir.offsetZ * 3, dir);
			}
			
			this.heatEnergy = 0;
			
			this.tryPullHeat();
			
			if(setting > 0 && this.power >= this.getConsumption()) {
				this.power -= this.getConsumption();
				this.heatEnergy += setting * 100;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setByte("s", (byte) this.setting);
			data.setInteger("h", heatEnergy);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.setting = nbt.getByte("s");
		this.heatEnergy = nbt.getInteger("h");
	}
	
	protected void tryPullHeat() {
		TileEntity con = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		
		if(con instanceof IHeatSource) {
			IHeatSource source = (IHeatSource) con;
			this.heatEnergy += source.getHeatStored() * 0.75;
			source.useUpHeat(source.getHeatStored());
		}
	}
	
	public void toggleSetting() {
		setting++;
		
		if(setting > 10)
			setting = 0;
	}

	@Override
	public long getPower() {
		return power;
	}
	
	public long getConsumption() {
		return (long) Math.pow(setting * 200, 1.4D);
	}

	@Override
	public long getMaxPower() {
		return getConsumption() * 20;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public int getHeatStored() {
		return heatEnergy;
	}

	@Override
	public void useUpHeat(int heat) {
		this.heatEnergy = Math.max(0, this.heatEnergy - heat);
	}
}
