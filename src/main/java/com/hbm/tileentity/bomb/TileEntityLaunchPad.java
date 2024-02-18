package com.hbm.tileentity.bomb;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityLaunchPad extends TileEntityLaunchPadBase implements IEnergyUser, IFluidStandardReceiver {

	@Override public boolean isReadyForLaunch() { return delay <= 0; }
	@Override public double getLaunchOffset() { return 2D; }
	
	public int delay = 0;

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(this.delay > 0) delay--;
			
			if(!this.isMissileValid() || !this.hasFuel()) {
				this.delay = 100;
			}
		}
		
		super.updateEntity();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.delay = nbt.getInteger("delay");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("delay", delay);
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
					yCoord + 15,
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
