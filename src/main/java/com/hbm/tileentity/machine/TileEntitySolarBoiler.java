package com.hbm.tileentity.machine;

import java.util.HashSet;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.IBufPacketReceiver;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;

public class TileEntitySolarBoiler extends TileEntityLoadedBase implements IFluidStandardTransceiver, IBufPacketReceiver, IFluidCopiable {

	private FluidTank water;
	private FluidTank steam;
	public int displayHeat;
	public int heat;

	public HashSet<ChunkCoordinates> primary = new HashSet();
	public HashSet<ChunkCoordinates> secondary = new HashSet();

	public TileEntitySolarBoiler() {
		water = new FluidTank(Fluids.WATER, 100);
		steam = new FluidTank(Fluids.STEAM, 10_000);
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			this.trySubscribe(water.getTankType(), worldObj, xCoord, yCoord + 3, zCoord, Library.POS_Y);
			this.trySubscribe(water.getTankType(), worldObj, xCoord, yCoord - 1, zCoord, Library.NEG_Y);

			int process = heat / 50;
			process = Math.min(process, water.getFill());
			process = Math.min(process, (steam.getMaxFill() - steam.getFill()) / 100);
			
			this.displayHeat = this.heat;

			if(process < 0) process = 0;

			water.setFill(water.getFill() - process);
			steam.setFill(steam.getFill() + process * 100);

			this.sendFluid(steam, worldObj, xCoord, yCoord + 3, zCoord, Library.POS_Y);
			this.sendFluid(steam, worldObj, xCoord, yCoord - 1, zCoord, Library.NEG_Y);

			heat = 0;

			networkPackNT(15);
		} else {

			//a delayed queue of mirror positions because we can't expect the boiler to always tick first
			secondary.clear();
			secondary.addAll(primary);
			primary.clear();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.water.readFromNBT(nbt, "water");
		this.steam.readFromNBT(nbt, "steam");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		this.water.writeToNBT(nbt, "water");
		this.steam.writeToNBT(nbt, "steam");
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
					yCoord + 3,
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
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { steam };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { water };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { water, steam };
	}

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeInt(displayHeat);
		water.serialize(buf);
		steam.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		this.displayHeat = buf.readInt();
		water.deserialize(buf);
		steam.deserialize(buf);
	}

	@Override public FluidTank getTankToPaste() { return null; }
}
