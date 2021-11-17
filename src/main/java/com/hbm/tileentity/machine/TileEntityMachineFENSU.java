package com.hbm.tileentity.machine;

import com.hbm.lib.Library;

import api.hbm.energy.IEnergyConductor;
import api.hbm.energy.IEnergyConnector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineFENSU extends TileEntityMachineBattery {

	public float prevRotation = 0F;
	public float rotation = 0F;
	
	@Override
	public void updateEntity() {
		
		this.maxPower = Long.MAX_VALUE;
		
		if(!worldObj.isRemote) {
			
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			power = Library.chargeItemsFromTE(slots, 1, power, maxPower);
			
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setLong("power", power);
			nbt.setLong("maxPower", maxPower);
			nbt.setShort("redLow", redLow);
			nbt.setShort("redHigh", redHigh);
			this.networkPack(nbt, 250);
		} else {
			this.prevRotation = this.rotation;
			this.rotation += this.getSpeed();
			
			if(rotation >= 360) {
				rotation -= 360;
				prevRotation -= 360;
			}
		}
	}
	
	protected void transmitPower() {
		
		short mode = (short) this.getRelevantMode();
		
		ForgeDirection dir = ForgeDirection.DOWN;
			
		TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);

		if(te instanceof IEnergyConductor) {
			IEnergyConductor con = (IEnergyConductor) te;

			if(con.getPowerNet() != null && con.getPowerNet().isSubscribed(this))
				con.getPowerNet().unsubscribe(this);
		}

		if(mode == 1 || mode == 2) {
			if(te instanceof IEnergyConnector) {
				IEnergyConnector con = (IEnergyConnector) te;
				long oldPower = this.power;
				long transfer = this.power - con.transferPower(this.power);
				this.power = oldPower - transfer;
			}
		}

		if(te instanceof IEnergyConductor) {
			IEnergyConductor con = (IEnergyConductor) te;

			if(con.getPowerNet() != null && !con.getPowerNet().isSubscribed(this))
				con.getPowerNet().subscribe(this);
		}
	}

	@Override
	public long getPowerRemainingScaled(long i) {
		
		double powerScaled = (double)power / (double)maxPower;
		
		return (long)(i * powerScaled);
	}
	
	public float getSpeed() {
		return (float) Math.pow(Math.log(power * 0.75 + 1) * 0.05F, 5);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
