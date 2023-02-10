package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineHephaestus extends TileEntityLoadedBase implements INBTPacketReceiver, IFluidStandardTransceiver {

	public FluidTank input;
	public FluidTank output;

	public TileEntityMachineHephaestus() {
		this.input = new FluidTank(Fluids.OIL, 24_000);
		this.output = new FluidTank(Fluids.HOTOIL, 24_000);
	}
	
	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			this.updateConnections();
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		
	}
	
	private void updateConnections() {
		
		for(DirPos pos : getConPos()) {
			this.trySubscribe(input.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	private DirPos[] getConPos() {
		
		return new DirPos[] {
				new DirPos(xCoord + 1, yCoord, zCoord, Library.POS_X),
				new DirPos(xCoord - 1, yCoord, zCoord, Library.NEG_X),
				new DirPos(xCoord, yCoord, zCoord + 1, Library.POS_Z),
				new DirPos(xCoord, yCoord, zCoord - 1, Library.NEG_Z),
				new DirPos(xCoord + 1, yCoord + 11, zCoord, Library.POS_X),
				new DirPos(xCoord - 1, yCoord + 11, zCoord, Library.NEG_X),
				new DirPos(xCoord, yCoord + 11, zCoord + 1, Library.POS_Z),
				new DirPos(xCoord, yCoord + 11, zCoord - 1, Library.NEG_Z)
		};
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {input, output};
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {output};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {input};
	}
	
	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN && dir != ForgeDirection.UP && dir != ForgeDirection.DOWN;
	}
}
