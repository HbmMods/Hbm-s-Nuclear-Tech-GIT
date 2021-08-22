package com.hbm.tileentity.machine;

import com.hbm.handler.FluidTypeHandler.FluidType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineOrbus extends TileEntityBarrel {

	public TileEntityMachineOrbus() {
		super(512000);
	}
	
	@Override
	public String getName() {
		return "container.orbus";
	}
	
	@Override
	public void checkFluidInteraction() { } //NO!

	@Override
	public void fillFluidInit(FluidType type) {
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
					xCoord + 2,
					yCoord + 5,
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
