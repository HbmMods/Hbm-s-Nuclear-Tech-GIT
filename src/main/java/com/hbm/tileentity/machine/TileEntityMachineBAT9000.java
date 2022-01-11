package com.hbm.tileentity.machine;

import com.hbm.handler.FluidTypeHandler.FluidTypeTheOldOne;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineBAT9000 extends TileEntityBarrel {

	public TileEntityMachineBAT9000() {
		super(2048000);
	}
	
	@Override
	public String getName() {
		return "container.bat9000";
	}
	
	@Override
	public void checkFluidInteraction() {
		
		if(tank.getTankType().isAntimatter()) {
			worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
			worldObj.newExplosion(null, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 10, true, true);
		}
	}

	@Override
	public void fillFluidInit(FluidTypeTheOldOne type) {
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord + 3, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord + 3, getTact(), type);
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord - 3, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord - 3, getTact(), type);
		fillFluid(this.xCoord + 3, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord - 3, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord + 3, this.yCoord, this.zCoord - 1, getTact(), type);
		fillFluid(this.xCoord - 3, this.yCoord, this.zCoord - 1, getTact(), type);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 2,
					yCoord,
					zCoord - 2,
					xCoord + 3,
					yCoord + 5,
					zCoord + 3
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
