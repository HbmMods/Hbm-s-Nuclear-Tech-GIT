package com.hbm.tileentity.machine.rbmk;

import api.hbm.fluid.IFluidStandardReceiver;
import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.TileEntityLoadedBase;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRBMKInlet extends TileEntityLoadedBase implements IFluidAcceptor, IFluidStandardReceiver {
	
	public FluidTank water;
	
	public TileEntityRBMKInlet() {
		water = new FluidTank(Fluids.WATER, 32000, 0);
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.subscribeToAllAround(water.getTankType(), this);
			
			for(int i = 2; i < 6; i++) {
				ForgeDirection dir = ForgeDirection.getOrientation(i);
				Block b = worldObj.getBlock(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
				
				if(b instanceof RBMKBase) {
					int[] pos = ((RBMKBase)b).findCore(worldObj, xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
					
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
	public void setFillForSync(int fill, int index) {
		if(index == 0) water.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		if(type == Fluids.WATER) water.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index == 0) water.setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type == Fluids.WATER) return water.getFill();
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type == Fluids.WATER) return water.getMaxFill();
		return 0;
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
