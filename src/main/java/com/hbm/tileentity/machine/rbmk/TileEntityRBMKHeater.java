package com.hbm.tileentity.machine.rbmk;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.Library;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;

public class TileEntityRBMKHeater extends TileEntityRBMKSlottedBase implements IFluidAcceptor, IFluidSource {

	public FluidTank feed;
	public FluidTank steam;
	public List<IFluidAcceptor> list = new ArrayList();
	
	public TileEntityRBMKHeater() {
		super(1);
	}

	@Override
	public String getName() {
		return "container.rbmkHeater";
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.HEATEX;
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			feed.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			steam.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			double heatCap = this.getConversionHeat(feed.getTankType());
			double heatProvided = this.heat - heatCap;
			
			if(heatProvided > 0) {
				
				int converted = (int)Math.floor(heatProvided / RBMKDials.getBoilerHeatConsumption(worldObj));
				converted = Math.min(converted, feed.getFill());
				converted = Math.min(converted, steam.getMaxFill() - steam.getFill());
				feed.setFill(feed.getFill() - converted);
				steam.setFill(steam.getFill() + converted);
				this.heat -= converted * RBMKDials.getBoilerHeatConsumption(worldObj);
			}
			
			fillFluidInit(steam.getTankType());
		}
		
		super.updateEntity();
	}
	
	public static double getConversionHeat(FluidType type) {
		return getConversion(type).temperature;
	}
	
	public static FluidType getConversion(FluidType type) {
		if(type == Fluids.MUG)		return Fluids.MUG_HOT;
		if(type == Fluids.COOLANT)	return Fluids.COOLANT_HOT;
		return Fluids.NONE;
	}

	@Override
	public void fillFluidInit(FluidType type) {

		fillFluid(this.xCoord, this.yCoord + RBMKDials.getColumnHeight(worldObj) + 1, this.zCoord, getTact(), type);
		
		if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) == ModBlocks.rbmk_loader) {

			fillFluid(this.xCoord + 1, this.yCoord - 1, this.zCoord, getTact(), type);
			fillFluid(this.xCoord - 1, this.yCoord - 1, this.zCoord, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 1, this.zCoord + 1, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 1, this.zCoord - 1, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 2, this.zCoord, getTact(), type);
		}
		
		if(worldObj.getBlock(xCoord, yCoord - 2, zCoord) == ModBlocks.rbmk_loader) {

			fillFluid(this.xCoord + 1, this.yCoord - 2, this.zCoord, getTact(), type);
			fillFluid(this.xCoord - 1, this.yCoord - 2, this.zCoord, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 2, this.zCoord + 1, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 2, this.zCoord - 1, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 1, this.zCoord, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 3, this.zCoord, getTact(), type);
		}
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}
	
	@Override
	@Deprecated //why are we still doing this?
	public boolean getTact() { return worldObj.getTotalWorldTime() % 2 == 0; }

	@Override
	public void setFluidFill(int i, FluidType type) {
		
		if(type == feed.getTankType())
			feed.setFill(i);
		else if(type == steam.getTankType())
			steam.setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		
		if(type == feed.getTankType())
			return feed.getFill();
		else if(type == steam.getTankType())
			return steam.getFill();
		
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		
		if(type == feed.getTankType())
			return feed.getMaxFill();
		else if(type == steam.getTankType())
			return steam.getMaxFill();
		
		return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {

		if(index == 0)
			feed.setFill(fill);
		else if(index == 1)
			steam.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {

		if(index == 0)
			feed.setTankType(type);
		else if(index == 1)
			steam.setTankType(type);
	}
	
	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return list;
	}
	
	@Override
	public void clearFluidList(FluidType type) {
		list.clear();
	}
}
