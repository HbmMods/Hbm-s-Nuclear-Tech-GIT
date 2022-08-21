package com.hbm.tileentity.machine.rbmk;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityHeatex extends TileEntity implements IFluidAcceptor, IFluidSource {

	public List<IFluidAcceptor> coolantList = new ArrayList();
	public List<IFluidAcceptor> waterList = new ArrayList();
	public FluidTank coolantIn;
	public FluidTank coolantOut;
	public FluidTank waterIn;
	public FluidTank waterOut;
	public double heatBuffer;
	public static final double maxHeat = 10_000;
	
	public TileEntityHeatex() {
		coolantIn =		new FluidTank(Fluids.COOLANT_HOT,	1000, 0);
		coolantOut =	new FluidTank(Fluids.COOLANT,		1000, 1);
		waterIn =		new FluidTank(Fluids.WATER,			1000, 2);
		waterOut =		new FluidTank(Fluids.SUPERHOTSTEAM,	1000, 3);
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			/* Cool input */
			double heatCap = maxHeat - heatBuffer;
			int fillCap = coolantOut.getMaxFill() - coolantOut.getFill();
			double deltaT = coolantIn.getTankType().temperature - coolantOut.getTankType().temperature;
			double heatPot = coolantIn.getFill() * coolantIn.getTankType().heatCap * deltaT;
			double heatEff = Math.min(heatCap, heatPot);
			int convertMax = (int) (heatEff / (coolantIn.getTankType().heatCap * deltaT));
			int convertEff = Math.min(convertMax, fillCap);

			coolantIn.setFill(coolantIn.getFill() - convertEff);
			coolantOut.setFill(coolantOut.getFill() + convertEff);
			this.heatBuffer += convertEff * coolantIn.getTankType().heatCap * deltaT;
			
			double HEAT_PER_MB_WATER = RBMKDials.getBoilerHeatConsumption(worldObj);
			
			/* Heat water */
			int waterCap = waterOut.getMaxFill() - waterOut.getFill();
			int maxBoil = (int) Math.min(waterIn.getFill(), heatBuffer / HEAT_PER_MB_WATER);
			int boilEff = Math.min(maxBoil, waterCap);

			waterIn.setFill(waterIn.getFill() - boilEff);
			waterOut.setFill(waterOut.getFill() + boilEff);
			this.heatBuffer -= boilEff * HEAT_PER_MB_WATER;

			coolantIn.updateTank(this, 15);
			coolantOut.updateTank(this, 15);
			waterIn.updateTank(this, 15);
			waterOut.updateTank(this, 15);

			this.fillFluidInit(coolantOut.getTankType());
			this.fillFluidInit(waterOut.getTankType());
		}
	}
	
	public static FluidType getConversion(FluidType type) {
		if(type == Fluids.MUG_HOT)		return Fluids.MUG;
		if(type == Fluids.COOLANT_HOT)	return Fluids.COOLANT;
		return Fluids.NONE;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.coolantIn.readFromNBT(nbt, "cI");
		this.coolantOut.readFromNBT(nbt, "cO");
		this.waterIn.readFromNBT(nbt, "wI");
		this.waterOut.readFromNBT(nbt, "wO");
		this.heatBuffer = nbt.getDouble("heat");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.coolantIn.writeToNBT(nbt, "cI");
		this.coolantOut.writeToNBT(nbt, "cO");
		this.waterIn.writeToNBT(nbt, "wI");
		this.waterOut.writeToNBT(nbt, "wO");
		nbt.setDouble("heat", this.heatBuffer);
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if(index == 0) coolantIn.setFill(fill);
		if(index == 1) coolantOut.setFill(fill);
		if(index == 2) waterIn.setFill(fill);
		if(index == 3) waterOut.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		if(type == coolantIn.getTankType())		coolantIn.setFill(fill);
		if(type == coolantOut.getTankType())	coolantOut.setFill(fill);
		if(type == waterIn.getTankType())		waterIn.setFill(fill);
		if(type == waterOut.getTankType())		waterOut.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index == 0) coolantIn.setTankType(type);
		if(index == 1) coolantOut.setTankType(type);
		if(index == 2) waterIn.setTankType(type);
		if(index == 3) waterOut.setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type == coolantIn.getTankType())		return coolantIn.getFill();
		if(type == coolantOut.getTankType())	return coolantOut.getFill();
		if(type == waterIn.getTankType())		return waterIn.getFill();
		if(type == waterOut.getTankType())		return waterOut.getFill();
		return 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			fillFluid(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}
	
	@Override
	@Deprecated
	public boolean getTact() { return worldObj.getTotalWorldTime() % 2 == 0; }

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		if(type == coolantOut.getTankType())	return this.coolantList;
		if(type == waterOut.getTankType())		return this.waterList;
		return new ArrayList();
	}

	@Override
	public void clearFluidList(FluidType type) {
		if(type == coolantOut.getTankType())	this.coolantList.clear();
		if(type == waterOut.getTankType())		this.waterList.clear();
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type == coolantIn.getTankType())	return coolantIn.getMaxFill();
		if(type == waterIn.getTankType())	return waterIn.getMaxFill();
		return 0;
	}
}
