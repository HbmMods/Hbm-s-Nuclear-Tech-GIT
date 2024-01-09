package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.util.fauxpointtwelve.DirPos;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityMachinePumpSteam extends TileEntityMachinePumpBase {

	public FluidTank steam;
	public FluidTank lps;
	
	public TileEntityMachinePumpSteam() {
		super();
		water = new FluidTank(Fluids.WATER, steamSpeed * 100);
		steam = new FluidTank(Fluids.STEAM, 1_000);
		lps = new FluidTank(Fluids.SPENTSTEAM, 10);
	}
	
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			for(DirPos pos : getConPos()) {
				this.trySubscribe(steam.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				if(lps.getFill() > 0) {
					this.sendFluid(lps, worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				}
			}
		}
		
		super.updateEntity();
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {water, steam, lps};
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {water, lps};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {steam};
	}
	
	protected NBTTagCompound getSync() {
		NBTTagCompound data = super.getSync();
		steam.writeToNBT(data, "s");
		lps.writeToNBT(data, "l");
		return data;
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		super.networkUnpack(nbt);
		steam.readFromNBT(nbt, "s");
		lps.readFromNBT(nbt, "l");
	}

	@Override
	protected boolean canOperate() {
		return steam.getFill() >= 100 && lps.getMaxFill() - lps.getFill() > 0 && water.getFill() < water.getMaxFill();
	}

	@Override
	protected void operate() {
		steam.setFill(steam.getFill() - 100);
		lps.setFill(lps.getFill() + 1);
		water.setFill(Math.min(water.getFill() + steamSpeed, water.getMaxFill()));
	}
}
