package com.hbm.tileentity.machine.storage;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.lib.Library;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

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
	public void subscribeToAllAround(FluidType type, World world, int x, int y, int z) {
		this.trySubscribe(type, world, xCoord + 1, yCoord, zCoord + 3, Library.POS_Z);
		this.trySubscribe(type, world, xCoord - 1, yCoord, zCoord + 3, Library.POS_Z);
		this.trySubscribe(type, world, xCoord + 1, yCoord, zCoord - 3, Library.NEG_Z);
		this.trySubscribe(type, world, xCoord - 1, yCoord, zCoord - 3, Library.NEG_Z);
		this.trySubscribe(type, world, xCoord + 3, yCoord, zCoord + 1, Library.POS_X);
		this.trySubscribe(type, world, xCoord - 3, yCoord, zCoord + 1, Library.NEG_X);
		this.trySubscribe(type, world, xCoord + 3, yCoord, zCoord - 1, Library.POS_X);
		this.trySubscribe(type, world, xCoord - 3, yCoord, zCoord - 1, Library.NEG_X);
	}

	@Override
	public void unsubscribeToAllAround(FluidType type, World world, int x, int y, int z) {
		this.tryUnsubscribe(type, world, xCoord + 1, yCoord, zCoord + 3);
		this.tryUnsubscribe(type, world, xCoord - 1, yCoord, zCoord + 3);
		this.tryUnsubscribe(type, world, xCoord + 1, yCoord, zCoord - 3);
		this.tryUnsubscribe(type, world, xCoord - 1, yCoord, zCoord - 3);
		this.tryUnsubscribe(type, world, xCoord + 3, yCoord, zCoord + 1);
		this.tryUnsubscribe(type, world, xCoord - 3, yCoord, zCoord + 1);
		this.tryUnsubscribe(type, world, xCoord + 3, yCoord, zCoord - 1);
		this.tryUnsubscribe(type, world, xCoord - 3, yCoord, zCoord - 1);
	}

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
	
	public void sendFluidToAll(FluidType type, TileEntity te) {
		sendFluid(type, worldObj, xCoord + 1, yCoord, zCoord + 3, Library.POS_Z);
		sendFluid(type, worldObj, xCoord - 1, yCoord, zCoord + 3, Library.POS_Z);
		sendFluid(type, worldObj, xCoord + 1, yCoord, zCoord - 3, Library.NEG_Z);
		sendFluid(type, worldObj, xCoord - 1, yCoord, zCoord - 3, Library.NEG_Z);
		sendFluid(type, worldObj, xCoord + 3, yCoord, zCoord + 1, Library.POS_X);
		sendFluid(type, worldObj, xCoord - 3, yCoord, zCoord + 1, Library.NEG_X);
		sendFluid(type, worldObj, xCoord + 3, yCoord, zCoord - 1, Library.POS_X);
		sendFluid(type, worldObj, xCoord - 3, yCoord, zCoord - 1, Library.NEG_X);
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
