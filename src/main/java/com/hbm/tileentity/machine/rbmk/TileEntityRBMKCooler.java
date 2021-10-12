package com.hbm.tileentity.machine.rbmk;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.FluidTank;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityRBMKCooler extends TileEntityRBMKBase implements IFluidAcceptor {
	
	private FluidTank tank;
	
	public TileEntityRBMKCooler() {
		super();
		
		this.tank = new FluidTank(FluidType.CRYOGEL, 8000, 0);
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if((int)(this.heat) > 750) {
				
				int heatProvided = (int)(this.heat - 750D);
				int cooling = Math.min(heatProvided, tank.getFill());
				
				this.heat -= cooling;
				this.tank.setFill(this.tank.getFill() - cooling);
				
				/*
				 * spew fire here
				 */
			}
		}
		
		super.updateEntity();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		tank.readFromNBT(nbt, "cryo");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		tank.writeToNBT(nbt, "cryo");
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.COOLER;
	}

	@Override
	public void setFillstate(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		if(type == tank.getTankType())
			tank.setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		tank.setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks() {
		return new ArrayList() {{ add(tank); }};
	}

	@Override
	public int getFluidFill(FluidType type) {
		return type == tank.getTankType() ? tank.getFill() : 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		return type == tank.getTankType() ? tank.getMaxFill() : 0;
	}

}
