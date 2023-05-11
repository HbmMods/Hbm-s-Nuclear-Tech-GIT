package com.hbm.tileentity.machine.storage;

import api.hbm.energy.IEnergyConductor;
import api.hbm.energy.IEnergyConnector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineFENSU extends TileEntityMachineBattery {

	public float prevRotation = 0F;
	public float rotation = 0F;
	
	public static final long maxTransfer = 10_000_000_000_000_000L;
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(worldObj.isRemote) {
			this.prevRotation = this.rotation;
			this.rotation += this.getSpeed();
			
			if(rotation >= 360) {
				rotation -= 360;
				prevRotation -= 360;
			}
		}
	}
	
	@Deprecated protected void transmitPower() {
		
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
				
				long max = maxTransfer;
				long toTransfer = Math.min(max, this.power);
				long remainder = this.power - toTransfer;
				this.power = toTransfer;
				
				long oldPower = this.power;
				long transfer = this.power - con.transferPower(this.power);
				this.power = oldPower - transfer;
				
				power += remainder;
			}
		}

		if(te instanceof IEnergyConductor) {
			IEnergyConductor con = (IEnergyConductor) te;
			
			if(con.getPowerNet() != null) {
				if(mode == 2 || mode == 3) {
					if(con.getPowerNet().isSubscribed(this)) {
						con.getPowerNet().unsubscribe(this);
					}
				} else if(!con.getPowerNet().isSubscribed(this)) {
					con.getPowerNet().subscribe(this);
				}
			}
		}
	}

	@Override
	public long getPowerRemainingScaled(long i) {
		
		double powerScaled = (double)power / (double)getMaxPower();
		
		return (long)(i * powerScaled);
	}

	@Override
	public long getMaxPower() {
		return Long.MAX_VALUE;
	}

	@Override
	public long getTransferWeight() {
		return Math.min(Math.max(this.getMaxPower() - getPower(), 0), maxTransfer);
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
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
