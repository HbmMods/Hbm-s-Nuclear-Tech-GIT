package com.hbm.tileentity.network;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPneumoTube extends TileEntity {

	public ForgeDirection insertionDir = ForgeDirection.UNKNOWN;
	public ForgeDirection ejectionDir = ForgeDirection.UNKNOWN;
	
	public FluidTank compair;
	
	public TileEntityPneumoTube() {
		this.compair = new FluidTank(Fluids.AIR, 4_000).withPressure(1);
	}
}
