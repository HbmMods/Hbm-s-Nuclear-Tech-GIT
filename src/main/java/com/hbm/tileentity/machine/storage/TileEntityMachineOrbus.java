package com.hbm.tileentity.machine.storage;

import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.fluid.FluidType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

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
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);

		for(int i = -1; i < 7; i += 7) {
			this.fillFluid(xCoord, yCoord + i, zCoord, this.getTact(), this.tank.getTankType());
			this.fillFluid(xCoord + dir.offsetX, yCoord + i, zCoord + dir.offsetZ, this.getTact(), this.tank.getTankType());
			this.fillFluid(xCoord + rot.offsetX, yCoord + i, zCoord + rot.offsetZ, this.getTact(), this.tank.getTankType());
			this.fillFluid(xCoord + dir.offsetX + rot.offsetX, yCoord + i, zCoord + dir.offsetZ + rot.offsetZ, this.getTact(), this.tank.getTankType());
		}
	}
	
	public void sendFluidToAll(FluidType type, TileEntity te) {
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);

		for(int i = -1; i < 7; i += 7) {
			ForgeDirection out = i == -1 ? ForgeDirection.DOWN : ForgeDirection.UP;
			sendFluid(type, worldObj, xCoord,								yCoord + i,	zCoord,								out);
			sendFluid(type, worldObj, xCoord + dir.offsetX,					yCoord + i,	zCoord + dir.offsetZ,				out);
			sendFluid(type, worldObj, xCoord + rot.offsetX,					yCoord + i,	zCoord + rot.offsetZ,				out);
			sendFluid(type, worldObj, xCoord + dir.offsetX + rot.offsetX,	yCoord + i,	zCoord + dir.offsetZ + rot.offsetZ,	out);
		}
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
