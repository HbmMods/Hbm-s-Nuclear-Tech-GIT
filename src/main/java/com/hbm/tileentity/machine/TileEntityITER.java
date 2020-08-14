package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityITER extends TileEntityMachineBase implements IConsumer, IFluidAcceptor, IFluidSource {
	
	public long power;
	public static final long maxPower = 1000000000;
	public int age = 0;
	public List<IFluidAcceptor> list = new ArrayList();
	public FluidTank[] tanks;
	public FluidTank plasma;

	public TileEntityITER() {
		super(5);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(FluidType.WATER, 128000, 0);
		tanks[1] = new FluidTank(FluidType.ULTRAHOTSTEAM, 128000, 0);
		plasma = new FluidTank(FluidType.PLASMA_DT, 16000, 0);
	}

	@Override
	public String getName() {
		return "container.machineITER";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			age++;
			if (age >= 20) {
				age = 0;
			}

			if (age == 9 || age == 19)
				fillFluidInit(tanks[1].getTankType());
		}
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public void setFillstate(int fill, int index) {
		if (index < 2 && tanks[index] != null)
			tanks[index].setFill(fill);
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if (type.name().equals(tanks[0].getTankType().name()))
			tanks[0].setFill(i);
		else if (type.name().equals(tanks[1].getTankType().name()))
			tanks[1].setFill(i);
	}

	@Override
	public void setType(FluidType type, int index) {
		if (index < 2 && tanks[index] != null)
			tanks[index].setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks() {
		List<FluidTank> list = new ArrayList();
		list.add(tanks[0]);
		list.add(tanks[1]);
		
		return list;
	}

	@Override
	public int getFluidFill(FluidType type) {
		if (type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getFill();
		else if (type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getFill();
		else if (type.name().equals(tanks[2].getTankType().name()))
			return tanks[2].getFill();
		else
			return 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(xCoord, yCoord - 3, zCoord, getTact(), type);
		fillFluid(xCoord, yCoord + 3, zCoord, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}

	@Override
	public boolean getTact() {
		if (age >= 0 && age < 10) {
			return true;
		}

		return false;
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
	public int getMaxFluidFill(FluidType type) {
		if (type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getMaxFill();
		else
			return 0;
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord + 0.5 - 8,
					yCoord + 0.5 - 3,
					zCoord + 0.5 - 8,
					xCoord + 0.5 + 8,
					yCoord + 0.5 + 3,
					zCoord + 0.5 + 8
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
