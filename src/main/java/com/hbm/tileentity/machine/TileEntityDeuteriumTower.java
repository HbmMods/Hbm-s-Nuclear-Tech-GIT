package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.inventory.FluidTank;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityDeuteriumTower extends TileEntityDeuteriumExtractor {

	public static final long maxPower = 1000000;

	public TileEntityDeuteriumTower() {
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(FluidType.WATER, 50000, 0);
		tanks[1] = new FluidTank(FluidType.HEAVYWATER, 5000, 0);
	}

	public void fillFluidInit(FluidType type) {
		
		int offsetX = 0;
		int offsetZ = 0;
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
		offsetX = -dir.offsetX;
		offsetZ = -rot.offsetZ;
		
		if(dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH) {
			offsetX = rot.offsetX;
			offsetZ = dir.offsetZ;
		}

        /*fillFluid(this.xCoord + rot.offsetX * 2, this.yCoord, this.zCoord - dir.offsetZ * 1, getTact(), type);
        fillFluid(this.xCoord + rot.offsetX * 2, this.yCoord, this.zCoord - dir.offsetZ * 0, getTact(), type);
        fillFluid(this.xCoord + rot.offsetX * 1, this.yCoord, this.zCoord - dir.offsetZ * 2, getTact(), type);
        fillFluid(this.xCoord + rot.offsetX * 0, this.yCoord, this.zCoord - dir.offsetZ * 2, getTact(), type);
        fillFluid(this.xCoord + rot.offsetX * 1, this.yCoord, this.zCoord + dir.offsetZ * 1, getTact(), type);
        fillFluid(this.xCoord + rot.offsetX * 0, this.yCoord, this.zCoord + dir.offsetZ * 1, getTact(), type);
        fillFluid(this.xCoord - rot.offsetX * 1, this.yCoord, this.zCoord + dir.offsetZ * 0, getTact(), type);
        fillFluid(this.xCoord - rot.offsetX * 1, this.yCoord, this.zCoord - dir.offsetZ * 1, getTact(), type);*/

        /*worldObj.setBlock(this.xCoord + rot.offsetX * 2, this.yCoord, this.zCoord - dir.offsetZ * 1, Blocks.dirt);
        worldObj.setBlock(this.xCoord + rot.offsetX * 2, this.yCoord, this.zCoord - dir.offsetZ * 0, Blocks.dirt);
        worldObj.setBlock(this.xCoord + rot.offsetX * 1, this.yCoord, this.zCoord - dir.offsetZ * 2, Blocks.dirt);
        worldObj.setBlock(this.xCoord + rot.offsetX * 0, this.yCoord, this.zCoord - dir.offsetZ * 2, Blocks.dirt);
        worldObj.setBlock(this.xCoord + rot.offsetX * 1, this.yCoord, this.zCoord + dir.offsetZ * 1, Blocks.dirt);
        worldObj.setBlock(this.xCoord + rot.offsetX * 0, this.yCoord, this.zCoord + dir.offsetZ * 1, Blocks.dirt);
        worldObj.setBlock(this.xCoord - rot.offsetX * 1, this.yCoord, this.zCoord + dir.offsetZ * 0, Blocks.dirt);
        worldObj.setBlock(this.xCoord - rot.offsetX * 1, this.yCoord, this.zCoord - dir.offsetZ * 1, Blocks.dirt);*/

        /*    I'm never doing an even sided fluid machine ever again
         * 
         *    this was pain
         *    
         *   - pheo                     */


		fillFluid(this.xCoord + offsetX * 2, this.yCoord, this.zCoord - offsetZ * 1, getTact(), type);
		fillFluid(this.xCoord + offsetX * 2, this.yCoord, this.zCoord - offsetZ * 0, getTact(), type);
		fillFluid(this.xCoord + offsetX * 1, this.yCoord, this.zCoord - offsetZ * 2, getTact(), type);
		fillFluid(this.xCoord + offsetX * 0, this.yCoord, this.zCoord - offsetZ * 2, getTact(), type);
		fillFluid(this.xCoord + offsetX * 1, this.yCoord, this.zCoord + offsetZ * 1, getTact(), type);
		fillFluid(this.xCoord + offsetX * 0, this.yCoord, this.zCoord + offsetZ * 1, getTact(), type);
		fillFluid(this.xCoord - offsetX * 1, this.yCoord, this.zCoord + offsetZ * 0, getTact(), type);
		fillFluid(this.xCoord - offsetX * 1, this.yCoord, this.zCoord - offsetZ * 1, getTact(), type);
	}
	
	protected void updateConnections() {
		
		int offsetX = 0;
		int offsetZ = 0;
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
		offsetX = -dir.offsetX;
		offsetZ = -rot.offsetZ;
		
		if(dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH) {
			offsetX = rot.offsetX;
			offsetZ = dir.offsetZ;
		}

		this.trySubscribe(worldObj, this.xCoord + offsetX * 2, this.yCoord, this.zCoord - offsetZ * 1);
		this.trySubscribe(worldObj, this.xCoord + offsetX * 2, this.yCoord, this.zCoord - offsetZ * 0);
		this.trySubscribe(worldObj, this.xCoord + offsetX * 1, this.yCoord, this.zCoord - offsetZ * 2);
		this.trySubscribe(worldObj, this.xCoord + offsetX * 0, this.yCoord, this.zCoord - offsetZ * 2);
		this.trySubscribe(worldObj, this.xCoord + offsetX * 1, this.yCoord, this.zCoord + offsetZ * 1);
		this.trySubscribe(worldObj, this.xCoord + offsetX * 0, this.yCoord, this.zCoord + offsetZ * 1);
		this.trySubscribe(worldObj, this.xCoord - offsetX * 1, this.yCoord, this.zCoord + offsetZ * 0);
		this.trySubscribe(worldObj, this.xCoord - offsetX * 1, this.yCoord, this.zCoord - offsetZ * 1);
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
					yCoord + 10,
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