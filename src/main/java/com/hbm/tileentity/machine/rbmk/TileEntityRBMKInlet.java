package com.hbm.tileentity.machine.rbmk;

import api.hbm.fluid.IFluidStandardReceiver;
import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.TileEntityLoadedBase;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRBMKInlet extends TileEntityLoadedBase implements IFluidStandardReceiver {
	
	public FluidTank water;
	
	public TileEntityRBMKInlet() {

	water = new FluidTank(Fluids.WATER, 50000000);

				
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
		if(RBMKDials.getReasimCoolantBoilers(worldObj)&&(Fluids.fromName("SUPERCOOLANT")!=Fluids.NONE)) 
		water.setTankType(Fluids.fromName("SUPERCOOLANT"));			
			this.subscribeToAllAround(water.getTankType(), this);
			
			for(int i = 0; i < 11; i++) {
				for(int j = 0; j <11; j++ ){
				Block b = worldObj.getBlock(xCoord + i - 5, yCoord, zCoord + j - 5);
				
				if(b instanceof RBMKBase) {
					int[] pos = ((RBMKBase)b).findCore(worldObj, xCoord + i - 5, yCoord, zCoord + j - 5);
					
/*
			for(int i = 2; i < 6; i++) {
				ForgeDirection dir = ForgeDirection.getOrientation(i);
				Block b = worldObj.getBlock(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
				
				if(b instanceof RBMKBase) {
					int[] pos = ((RBMKBase)b).findCore(worldObj, xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
*/
					if(pos != null) {
						TileEntity te = worldObj.getTileEntity(pos[0], pos[1], pos[2]);
						
						if(te instanceof TileEntityRBMKBase) {
							TileEntityRBMKBase rbmk = (TileEntityRBMKBase) te;
							
							int prov = Math.min(rbmk.maxWater - rbmk.water, water.getFill());
							rbmk.water += prov;
							water.setFill(water.getFill() - prov);
						}
					}
				}
			}
		}
	}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.water.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.water.writeToNBT(nbt, "tank");
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {water};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {water};
	}

}
