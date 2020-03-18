package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.ILaserable;
import com.hbm.inventory.FluidTank;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.block.Block;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCoreEmitter extends TileEntityMachineBase implements IConsumer, IFluidAcceptor, ILaserable {
	
	public long power;
	public static final long maxPower = 1000000000L;
	public long joules;
	public boolean isOn;
	public FluidTank tank;

	public TileEntityCoreEmitter() {
		super(2);
	}

	@Override
	public String getName() {
		return "container.dfcEmitter";
	}

	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {

			//tank.setType(0, 1, slots);
			//tank.updateTank(xCoord, yCoord, zCoord);
			
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
			int range = 50;
			
			for(int i = 1; i <= range; i++) {
				
				Block b = worldObj.getBlock(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
			}
		}
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.name().equals(tank.getTankType().name()))
			tank.setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type.name().equals(tank.getTankType().name()))
			return tank.getFill();
		else
			return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type.name().equals(tank.getTankType().name()))
			return tank.getMaxFill();
		else
			return 0;
	}

	@Override
	public void setFillstate(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		tank.setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks() {
		List<FluidTank> list = new ArrayList();
		list.add(tank);
		
		return list;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return this.maxPower;
	}

	@Override
	public void addEnergy(long energy) {
		joules += energy;
	}

}
