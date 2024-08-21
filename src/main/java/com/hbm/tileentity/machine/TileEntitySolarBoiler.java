package com.hbm.tileentity.machine;

import java.util.HashSet;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.packet.BufPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IBufPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;

public class TileEntitySolarBoiler extends TileEntityLoadedBase implements IFluidStandardTransceiver, IBufPacketReceiver {

	private FluidTank water;
	private FluidTank steam;
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
			
			if(process < 0)
				process = 0;

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
	
	public void networkPackNT(int range) {
		if(!worldObj.isRemote) PacketDispatcher.wrapper.sendToAllAround(new BufPacket(xCoord, yCoord, zCoord, this), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, range));
	}

	@Override
	public void serialize(ByteBuf buf) {
		water.serialize(buf);
		steam.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		water.deserialize(buf);
		steam.deserialize(buf);
	}
}
