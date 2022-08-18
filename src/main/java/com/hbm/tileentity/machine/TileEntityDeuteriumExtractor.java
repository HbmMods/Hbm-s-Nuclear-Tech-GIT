package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityDeuteriumExtractor extends TileEntityMachineBase implements IFluidAcceptor, IFluidSource, IEnergyUser, IFluidStandardTransceiver {
	
	public long power = 0;
	public FluidTank[] tanks;
	public List<IFluidAcceptor> list = new ArrayList();

	public TileEntityDeuteriumExtractor() {
		super(0);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.WATER, 1000, 0);
		tanks[1] = new FluidTank(Fluids.HEAVYWATER, 100, 1);
	}

	@Override
	public String getName() {
		return "container.deuterium";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.updateConnections();
			
			if(worldObj.getTotalWorldTime() % 10 == 0) {
				if(hasPower() && hasEnoughWater() && tanks[1].getMaxFill() > tanks[1].getFill()) {
					int convert = Math.min(tanks[1].getMaxFill(), tanks[0].getFill()) / 50;
					convert = Math.min(convert, tanks[1].getMaxFill() - tanks[1].getFill());
					
					tanks[0].setFill(tanks[0].getFill() - convert * 50); //dividing first, then multiplying, will remove any rounding issues
					tanks[1].setFill(tanks[1].getFill() + convert);
					power -= this.getMaxPower() / 20;
				}
			}
			
			this.subscribeToAllAround(tanks[0].getTankType(), this);
			this.sendFluidToAll(tanks[1].getTankType(), this);
			fillFluidInit(tanks[1].getTankType());

			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			tanks[0].writeToNBT(data, "water");
			tanks[1].writeToNBT(data, "heavyWater");
			
			this.networkPack(data, 50);
		}
	}
	
	protected void updateConnections() {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
	}

	public void networkUnpack(NBTTagCompound data) {
		this.power = data.getLong("power");
		tanks[0].readFromNBT(data, "water");
		tanks[1].readFromNBT(data, "heavyWater");
	}

	public boolean hasPower() {
		return power >= this.getMaxPower() / 20;
	}

	public boolean hasEnoughWater() {
		return tanks[0].getFill() >= 100;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
		tanks[0].readFromNBT(nbt, "water");
		tanks[1].readFromNBT(nbt, "heavyWater");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		tanks[0].writeToNBT(nbt, "water");
		tanks[1].writeToNBT(nbt, "heavyWater");
	}


	@Override
	public void fillFluidInit(FluidType type) {

		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			fillFluid(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}
	
	@Override
	public boolean getTact() {
		return worldObj.getTotalWorldTime() % 20 < 10;
	}
	
	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type == tanks[0].getTankType())
			tanks[0].setFill(i);
		else if(type == tanks[1].getTankType())
			tanks[1].setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type == tanks[0].getTankType())
			return tanks[0].getFill();
		else if(type == tanks[1].getTankType())
			return tanks[1].getFill();

		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type == tanks[0].getTankType())
			return tanks[0].getMaxFill();

		return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) { }

	@Override
	public void setTypeForSync(FluidType type, int index) { }

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return list;
	}

	@Override
	public void clearFluidList(FluidType type) {
		list.clear();
	}

	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return 100000;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { tanks[1] };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { tanks[0] };
	}
}