package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCondenserPowered extends TileEntityCondenser implements IEnergyUser {
	
	public long power;
	public static final long maxPower = 10_000_000;
	public float spin;
	public float lastSpin;
	
	public TileEntityCondenserPowered() {
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.SPENTSTEAM, 100_000);
		tanks[1] = new FluidTank(Fluids.WATER, 100_000);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(worldObj.isRemote) {
			
			this.lastSpin = this.spin;
			
			if(this.waterTimer > 0) {
				this.spin += 30F;
				
				if(this.spin >= 360F) {
					this.spin -= 360F;
					this.lastSpin -= 360F;
				}
				
				if(worldObj.getTotalWorldTime() % 4 == 0) {
					ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
					worldObj.spawnParticle("cloud", xCoord + 0.5 + dir.offsetX * 1.5, yCoord + 1.5, zCoord + 0.5 + dir.offsetZ * 1.5, dir.offsetX * 0.1, 0, dir.offsetZ * 0.1);
					worldObj.spawnParticle("cloud", xCoord + 0.5 - dir.offsetX * 1.5, yCoord + 1.5, zCoord + 0.5 - dir.offsetZ * 1.5, dir.offsetX * -0.1, 0, dir.offsetZ * -0.1);
				}
			}
		}
	}

	@Override
	public void packExtra(NBTTagCompound data) {
		data.setLong("power", power);
	}
	
	@Override
	public boolean extraCondition(int convert) {
		return power >= convert * 10;
	}

	@Override
	public void postConvert(int convert) {
		this.power -= convert * 10;
		if(this.power < 0) this.power = 0;
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.tanks[0].readFromNBT(nbt, "0");
		this.tanks[1].readFromNBT(nbt, "1");
		this.waterTimer = nbt.getByte("timer");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
		tanks[0].readFromNBT(nbt, "water");
		tanks[1].readFromNBT(nbt, "steam");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		tanks[0].writeToNBT(nbt, "water");
		tanks[1].writeToNBT(nbt, "steam");
	}

	@Deprecated @Override public void fillFluidInit(FluidType type) { }

	@Override
	public void subscribeToAllAround(FluidType type, TileEntity te) {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(this.tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}

	@Override
	public void sendFluidToAll(FluidTank tank, TileEntity te) {
		for(DirPos pos : getConPos()) {
			this.sendFluid(this.tanks[1], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	public DirPos[] getConPos() {
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(xCoord + rot.offsetX * 4, yCoord + 1, zCoord + rot.offsetZ * 4, rot),
				new DirPos(xCoord - rot.offsetX * 4, yCoord + 1, zCoord - rot.offsetZ * 4, rot.getOpposite()),
				new DirPos(xCoord + dir.offsetX * 2 - rot.offsetX, yCoord + 1, zCoord + dir.offsetZ * 2 - rot.offsetZ, dir),
				new DirPos(xCoord + dir.offsetX * 2 + rot.offsetX, yCoord + 1, zCoord + dir.offsetZ * 2 + rot.offsetZ, dir),
				new DirPos(xCoord - dir.offsetX * 2 - rot.offsetX, yCoord + 1, zCoord - dir.offsetZ * 2 - rot.offsetZ, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 2 + rot.offsetX, yCoord + 1, zCoord - dir.offsetZ * 2 + rot.offsetZ, dir.getOpposite())
		};
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 3,
					yCoord,
					zCoord - 3,
					xCoord + 4,
					yCoord + 3,
					zCoord + 4
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
	public long getPower() {
		return this.power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		return this.maxPower;
	}
}
