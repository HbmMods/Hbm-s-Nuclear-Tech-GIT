package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardTransceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class TileEntityFluidPump extends TileEntityMachineBase implements IFluidAcceptor, IFluidSource, IEnergyUser, IFluidStandardTransceiver {
	
	public long power = 0;
	public FluidTank[] tanks;
	public List<IFluidAcceptor> list = new ArrayList();

	public TileEntityFluidPump() {
		super(0);
		tanks = new FluidTank[1];
		tanks[0] = new FluidTank(Fluids.WATER, 1000, 0);
	}

	@Override
	public String getName() {
		return "container.fluid_pump";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.updateConnections();
			setupTanks();
			
			if(hasPower() && canPump() && tanks[0].getMaxFill() > tanks[0].getFill()) {
				int amount = getPumpAmount();
				int convert = Math.min(amount, tanks[0].getMaxFill() - tanks[0].getFill());
				
				if (convert > 0) {
					tanks[0].setFill(tanks[0].getFill() + convert);
					power -= this.getMaxPower() / 20;
					pump();
				}
			}
			
			this.sendFluidToAll(tanks[0].getTankType(), this);
			fillFluidInit(tanks[0].getTankType());

			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			tanks[0].writeToNBT(data, "water");
			
			this.networkPack(data, 50);
		}
	}
	
	private void setupTanks() {	
		if (tanks[0].getTankType() == Fluids.WATER) {
			tanks[0].setTankType(Fluids.WATER);
		} else if (tanks[0].getTankType() == Fluids.LAVA) {
			tanks[0].setTankType(Fluids.LAVA);
		} else {
			tanks[0].setTankType(Fluids.NONE);
		}
	}
	
	protected void updateConnections() {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
	}

	public void networkUnpack(NBTTagCompound data) {
		this.power = data.getLong("power");
		tanks[0].readFromNBT(data, "water");
	}

	public boolean hasPower() {
		return power >= this.getMaxPower() / 20;
	}
	
	public boolean canPump() {
		Block b = worldObj.getBlock(xCoord, yCoord - 1, zCoord);
		
		if(b == Blocks.lava || b == Blocks.flowing_lava)	return true;
		if(b == Blocks.water || b == Blocks.flowing_water)	return true;
		
		return false;
	}
	
	public int getPumpAmount() {
		Block b = worldObj.getBlock(xCoord, yCoord - 1, zCoord);
		
		if (tanks[0].getTankType() == Fluids.WATER) {
			if(b == Blocks.water)	return 1000;
			if(b == Blocks.flowing_water)	return 250;
		} else if (tanks[0].getTankType() == Fluids.LAVA) {
			if(b == Blocks.lava)	return 1000;
			if(b == Blocks.flowing_lava)	return 250;
		}
		
		return 0;
	}
	
	public void pump() {
		Block b = worldObj.getBlock(xCoord, yCoord-1, zCoord);
		
		if(b == Blocks.lava || b == Blocks.flowing_lava || b == Blocks.water || b == Blocks.flowing_water) {
			worldObj.setBlock(xCoord, yCoord-1, zCoord, Blocks.air);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
		tanks[0].readFromNBT(nbt, "water");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		tanks[0].writeToNBT(nbt, "water");
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
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type == tanks[0].getTankType())
			return tanks[0].getFill();

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
		return new FluidTank[] { tanks[0] };
	}
	
	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}
}