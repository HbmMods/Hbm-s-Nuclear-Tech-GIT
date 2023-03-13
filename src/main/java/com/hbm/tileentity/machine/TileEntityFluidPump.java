package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.INBTPacketReceiver;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardSender;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class TileEntityFluidPump extends TileEntityLoadedBase implements IEnergyUser, INBTPacketReceiver, IFluidAcceptor, IFluidStandardSender {
	
	public long power = 0;
	public FluidTank tank;
	public List<IFluidAcceptor> list = new ArrayList();

	public TileEntityFluidPump() {
		tank = new FluidTank(Fluids.WATER, 1000, 0);
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.updateConnections();
			setupTanks();
			
			if(hasPower() && canPump() && tank.getMaxFill() > tank.getFill()) {
				int amount = getPumpAmount();
				int convert = Math.min(amount, tank.getMaxFill() - tank.getFill());
				
				if (convert > 0) {
					tank.setFill(tank.getFill() + convert);
					power -= 5;
					
					Block b = worldObj.getBlock(xCoord, yCoord-1, zCoord);
		
					if(b == Blocks.lava || b == Blocks.flowing_lava || b == Blocks.water || b == Blocks.flowing_water) {
						worldObj.setBlock(xCoord, yCoord-1, zCoord, Blocks.air);
					}
				}
			}
			
			this.sendFluidToAll(tank.getTankType(), this);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			tank.writeToNBT(data, "tank");
			INBTPacketReceiver.networkPack(this, data, 25);
		}
	}
	
	private void setupTanks() {	
		if (tank.getTankType() == Fluids.WATER) {
			tank.setTankType(Fluids.WATER);
		} else if (tank.getTankType() == Fluids.LAVA) {
			tank.setTankType(Fluids.LAVA);
		} else {
			tank.setTankType(Fluids.NONE);
		}
	}
	
	protected void updateConnections() {
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
	}

	public boolean hasPower() {
		return power >= 5;
	}
	
	public boolean canPump() {
		Block b = worldObj.getBlock(xCoord, yCoord - 1, zCoord);
		
		if(b == Blocks.lava || b == Blocks.flowing_lava)	return true;
		if(b == Blocks.water || b == Blocks.flowing_water)	return true;
		
		return false;
	}
	
	public int getPumpAmount() {
		Block b = worldObj.getBlock(xCoord, yCoord - 1, zCoord);
		
		if (tank.getTankType() == Fluids.WATER) {
			if(b == Blocks.water)	return 1000;
			if(b == Blocks.flowing_water)	return 250;
		} else if (tank.getTankType() == Fluids.LAVA) {
			if(b == Blocks.lava)	return 1000;
			if(b == Blocks.flowing_lava)	return 250;
		}
		
		return 0;
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		tank.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("power");
		tank.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setLong("power", power);
		tank.writeToNBT(nbt, "tank");
	}
	
	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type == tank.getTankType())
			tank.setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type == tank.getTankType())
			return tank.getFill();

		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type == tank.getTankType())
			return tank.getMaxFill();

		return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) { }

	@Override
	public void setTypeForSync(FluidType type, int index) { }

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
		return 1000;
	}
	
	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { tank };
	}
}