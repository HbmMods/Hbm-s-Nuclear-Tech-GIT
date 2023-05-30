package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityAtmoVent extends TileEntityAtmoExtractor {
	float rotSpeed;
	public float rot;
	public float prevRot;

	public TileEntityAtmoVent() {
		super();
		tanks = new FluidTank(Fluids.AIR, 50000, 0);
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

		for(DirPos pos : getConPos()) {
			this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	public void subscribeToAllAround(FluidType type, World world, int x, int y, int z) {

		for(DirPos pos : getConPos()) {
			this.trySubscribe(type, world, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	public void sendFluidToAll(FluidTank type, TileEntity te) {

		for(DirPos pos : getConPos()) {
			this.sendFluid(type, worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	private DirPos[] getConPos() {
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);

		return new DirPos[] {
				new DirPos(this.xCoord - dir.offsetX * 2, this.yCoord, this.zCoord - dir.offsetZ * 2, dir.getOpposite()),
				new DirPos(this.xCoord - dir.offsetX * 2 + rot.offsetX, this.yCoord, this.zCoord - dir.offsetZ * 2 + rot.offsetZ, dir.getOpposite()),
				
				new DirPos(this.xCoord + dir.offsetX, this.yCoord, this.zCoord + dir.offsetZ, dir),
				new DirPos(this.xCoord + dir.offsetX + rot.offsetX, this.yCoord, this.zCoord + dir.offsetZ  + rot.offsetZ, dir),
				
				new DirPos(this.xCoord - rot.offsetX, this.yCoord, this.zCoord - rot.offsetZ, rot.getOpposite()),
				new DirPos(this.xCoord - dir.offsetX - rot.offsetX, this.yCoord, this.zCoord - dir.offsetZ - rot.offsetZ, rot.getOpposite()),
				
				new DirPos(this.xCoord + rot.offsetX * 2, this.yCoord, this.zCoord + rot.offsetZ * 2, rot),
				new DirPos(this.xCoord - dir.offsetX + rot.offsetX * 2, this.yCoord, this.zCoord - dir.offsetZ + rot.offsetZ * 2, rot),
		};
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

	@Override
	public long getMaxPower() {
		return 1000000;
	}
}