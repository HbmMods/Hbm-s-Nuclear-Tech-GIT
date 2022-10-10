package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyUser;
import api.hbm.tile.IHeatSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
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
			
			this.heatEnergy *= 0.999;
			
			this.tryPullHeat();
			
			if(setting > 0 && this.power >= this.getConsumption()) {
				this.power -= this.getConsumption();
				this.heatEnergy += getHeatGen();
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setByte("s", (byte) this.setting);
			data.setInteger("h", this.heatEnergy);
			INBTPacketReceiver.networkPack(this, data, 25);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.setting = nbt.getByte("s");
		this.heatEnergy = nbt.getInteger("h");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("power");
		this.setting = nbt.getInteger("setting");
		this.heatEnergy = nbt.getInteger("heatEnergy");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setLong("power", power);
		nbt.setInteger("setting", setting);
		nbt.setInteger("heatEnergy", heatEnergy);
	}
	
	protected void tryPullHeat() {
		TileEntity con = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		
		if(con instanceof IHeatSource) {
			IHeatSource source = (IHeatSource) con;
			this.heatEnergy += source.getHeatStored() * 0.85;
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
		return (long) (Math.pow(setting, 1.4D) * 200D);
	}

	@Override
	public long getMaxPower() {
		return getConsumption() * 20;
	}
	
	public int getHeatGen() {
		return this.setting * 100;
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
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 2,
					yCoord,
					zCoord - 2,
					xCoord + 3,
					yCoord + 1,
					zCoord + 3
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
