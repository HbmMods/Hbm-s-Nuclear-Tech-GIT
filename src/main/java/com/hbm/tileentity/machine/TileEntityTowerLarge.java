package com.hbm.tileentity.machine;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.inventory.FluidTank;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityTowerLarge extends TileEntityCondenser {
	
	public TileEntityTowerLarge() {
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(FluidType.SPENTSTEAM, 10000, 0);
		tanks[1] = new FluidTank(FluidType.WATER, 10000, 1);
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
		for(int i = 2; i <= 6; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
			fillFluid(xCoord + dir.offsetX * 5, yCoord, zCoord + dir.offsetZ * 5, getTact(), type);
			fillFluid(xCoord + dir.offsetX * 5 + rot.offsetX * 2, yCoord, zCoord + dir.offsetZ * 5 + rot.offsetZ * 2, getTact(), type);
			fillFluid(xCoord + dir.offsetX * 5 + rot.offsetX * -2, yCoord, zCoord + dir.offsetZ * 5 + rot.offsetZ * -2, getTact(), type);
		}
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 4,
					yCoord,
					zCoord - 4,
					xCoord + 5,
					yCoord + 13,
					zCoord + 5
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
