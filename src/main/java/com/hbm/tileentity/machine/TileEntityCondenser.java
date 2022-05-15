package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.Library;
import com.hbm.main.ModEventHandler;

import api.hbm.fluid.IFluidStandardTransceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCondenser extends TileEntity implements IFluidAcceptor, IFluidSource, IFluidStandardTransceiver {

	public int age = 0;
	public FluidTank[] tanks;
	public List<IFluidAcceptor> list = new ArrayList();
	
	public int waterTimer = 0;
	
	public TileEntityCondenser() {
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.SPENTSTEAM, 100, 0);
		tanks[1] = new FluidTank(Fluids.WATER, 100, 1);
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			age++;
			if(age >= 2) {
				age = 0;
			}

			this.tanks[0].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			this.tanks[1].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			int convert = Math.min(tanks[0].getFill(), tanks[1].getMaxFill() - tanks[1].getFill());
			tanks[0].setFill(tanks[0].getFill() - convert);
			
			int light = this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, this.xCoord, this.yCoord, this.zCoord);
			
			if(ModEventHandler.fire > 0 && light > 7) { // Make both steam and water evaporate during firestorms...
				tanks[1].setFill(tanks[1].getFill() - convert);
			} else {
				tanks[1].setFill(tanks[1].getFill() + convert);
			}
			
			this.subscribeToAllAround(tanks[0].getTankType(), this);
			this.sendFluidToAll(tanks[1].getTankType(), this);
			
			fillFluidInit(tanks[1].getTankType());
			
		} else {
			
			if(tanks[0].getFill() > 0) {
				this.waterTimer = 20;
			} else if(this.waterTimer > 0){
				this.waterTimer--;
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tanks[0].readFromNBT(nbt, "water");
		tanks[1].readFromNBT(nbt, "steam");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		tanks[0].writeToNBT(nbt, "water");
		tanks[1].writeToNBT(nbt, "steam");
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			fillFluid(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}
	
	@Override
	public boolean getTact() {
		if(age == 0)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			tanks[0].setFill(i);
		else if(type.name().equals(tanks[1].getTankType().name()))
			tanks[1].setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getFill();
		else if(type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getFill();
		
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getMaxFill();
		
		return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if(index < 2 && tanks[index] != null)
			tanks[index].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index < 2 && tanks[index] != null)
			tanks[index].setTankType(type);
	}
	
	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return list;
	}
	
	@Override
	public void clearFluidList(FluidType type) {
		list.clear();
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {tanks [1]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tanks [0]};
	}
}
