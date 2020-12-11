package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidTank;
import com.hbm.lib.Library;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntitySolarBoiler extends TileEntity implements IFluidAcceptor, IFluidSource {

	private FluidTank water;
	private FluidTank steam;
	public List<IFluidAcceptor> list = new ArrayList();
	public int heat;
	
	public TileEntitySolarBoiler() {
		water = new FluidTank(FluidType.WATER, 16000, 0);
		steam = new FluidTank(FluidType.STEAM, 1600000, 1);
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(worldObj.getTotalWorldTime() % 10 == 0) {
				fillFluidInit(FluidType.STEAM);
			}
			
			int process = heat / 10;
			process = Math.min(process, water.getFill());
			process = Math.min(process, (steam.getMaxFill() - steam.getFill()) / 100);
			
			if(process < 0)
				process = 0;

			water.setFill(water.getFill() - process);
			steam.setFill(steam.getFill() + process * 100);
			
			if(steam.getFill() > steam.getMaxFill() * 0.9)
				System.out.println("*" + steam.getFill());
			
			heat = 0;
		}
	}

	@Override
	public void setFillstate(int fill, int index) {
		if(index == 0)
			water.setFill(fill);
		if(index == 1)
			steam.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		if(type == FluidType.WATER)
			water.setFill(fill);
		if(type == FluidType.STEAM)
			steam.setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		if(index == 0)
			water.setTankType(type);
		if(index == 1)
			steam.setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks() {
		return Arrays.asList(new FluidTank[] {water, steam});
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type == FluidType.WATER)
			return water.getFill();
		if(type == FluidType.STEAM)
			return steam.getFill();
		
		return 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord, this.yCoord + 3, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord - 1, this.zCoord, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}
	
	@Override
	public boolean getTact() {
		return worldObj.getTotalWorldTime() % 20 < 10;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type == FluidType.WATER)
			return water.getMaxFill();
		if(type == FluidType.STEAM)
			return steam.getMaxFill();
		
		return 0;
	}
	
	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return list;
	}
	
	@Override
	public void clearFluidList(FluidType type) {
		list.clear();
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + 3,
					zCoord + 2
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
