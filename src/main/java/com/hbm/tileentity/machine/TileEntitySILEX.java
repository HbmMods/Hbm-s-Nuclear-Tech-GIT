package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.FluidTank;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;

public class TileEntitySILEX extends TileEntityMachineBase implements IFluidAcceptor {
	
	public FluidTank tank;
	public ItemStack current;
	public int currentFill;
	public int progress;
	public final int processTime = 100;

	public TileEntitySILEX() {
		super(11);
		tank = new FluidTank(FluidType.ACID, 16000, 0);
	}

	@Override
	public String getName() {
		return "container.machineSILEX";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(
				xCoord - 1,
				yCoord,
				zCoord - 1,
				xCoord + 2,
				yCoord + 3,
				zCoord + 2
			);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

	@Override
	public void setFillstate(int fill, int index) {
		
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		
	}

	@Override
	public void setType(FluidType type, int index) {
		
	}

	@Override
	public List<FluidTank> getTanks() {
		return null;
	}

	@Override
	public int getFluidFill(FluidType type) {
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		return 0;
	}
}
