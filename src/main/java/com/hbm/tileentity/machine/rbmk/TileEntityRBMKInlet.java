package com.hbm.tileentity.machine.rbmk;

import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.FluidTank;

import net.minecraft.tileentity.TileEntity;

public class TileEntityRBMKInlet extends TileEntity implements IFluidAcceptor {

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
	public int getMaxFluidFill(FluidType type) {
		// TODO Auto-generated method stub
		return 0;
	}

}
