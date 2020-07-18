package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidTank;
import com.hbm.tileentity.TileEntityMachineBase;

public class TileEntityITER extends TileEntityMachineBase implements IConsumer, IFluidAcceptor, IFluidSource {

	public TileEntityITER() {
		super(1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setFillstate(int fill, int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setType(FluidType type, int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<FluidTank> getTanks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFluidFill(FluidType type) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getTact() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearFluidList(FluidType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPower(long i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getPower() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getMaxPower() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateEntity() {
		// TODO Auto-generated method stub
		
	}

}
