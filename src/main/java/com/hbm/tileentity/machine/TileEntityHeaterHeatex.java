package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Coolable;
import com.hbm.inventory.fluid.trait.FT_Coolable.CoolingType;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import api.hbm.tile.IHeatSource;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityHeaterHeatex extends TileEntityMachineBase implements IHeatSource, INBTPacketReceiver, IFluidStandardTransceiver {
	
	public FluidTank[] tanks;
	public int amountToCool = 1;
	public int tickDelay = 1;
	public int heatEnergy;
	
	public TileEntityHeaterHeatex() {
		super(1);
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.COOLANT_HOT, 24_000, 0);
		this.tanks[1] = new FluidTank(Fluids.COOLANT, 24_000, 1);
	}

	@Override
	public String getName() {
		return "container.heaterHeatex";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			this.setupTanks();
			this.updateConnections();
			
			NBTTagCompound data = new NBTTagCompound();
			tanks[0].writeToNBT(data, "0");
			this.tryConvert();
			tanks[1].writeToNBT(data, "1");
			data.setInteger("heat", heatEnergy);
			INBTPacketReceiver.networkPack(this, data, 25);
		}
	}
	
	protected void setupTanks() {
		
		if(tanks[0].getTankType().hasTrait(FT_Coolable.class)) {
			FT_Coolable trait = tanks[0].getTankType().getTrait(FT_Coolable.class);
			if(trait.getEfficiency(CoolingType.HEATEXCHANGER) > 0) {
				tanks[1].setTankType(trait.coolsTo);
				return;
			}
		}

		tanks[0].setTankType(Fluids.NONE);
		tanks[1].setTankType(Fluids.NONE);
	}
	
	protected void updateConnections() {
		
		for(DirPos pos : getConPos()) {
			this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	protected void tryConvert() {
		
		if(!tanks[0].getTankType().hasTrait(FT_Coolable.class)) return;
		if(tickDelay < 1) tickDelay = 1;
		if(worldObj.getTotalWorldTime() % tickDelay != 0) return;
		
		FT_Coolable trait = tanks[0].getTankType().getTrait(FT_Coolable.class);
		
		int inputOps = tanks[0].getFill() / trait.amountReq;
		int outputOps = (tanks[1].getMaxFill() - tanks[1].getFill()) / trait.amountProduced;
		int opCap = this.amountToCool;
		
		int ops = Math.min(inputOps, Math.min(outputOps, opCap));
		tanks[0].setFill(tanks[0].getFill() - trait.amountReq * ops);
		tanks[1].setFill(tanks[1].getFill() + trait.amountProduced * ops);
		this.heatEnergy += trait.heatEnergy * ops;
		this.markChanged();
	}
	
	private DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 2 + rot.offsetX, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ, dir),
				new DirPos(xCoord + dir.offsetX * 2 - rot.offsetX, yCoord, zCoord + dir.offsetZ * 2 - rot.offsetZ, dir),
				new DirPos(xCoord - dir.offsetX * 2 + rot.offsetX, yCoord, zCoord - dir.offsetZ * 2 + rot.offsetZ, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 2 - rot.offsetX, yCoord, zCoord - dir.offsetZ * 2 - rot.offsetZ, dir.getOpposite())
		};
	}

	@Override
	public int getHeatStored() {
		return heatEnergy;
	}

	@Override
	public void useUpHeat(int heat) {
		this.heatEnergy = Math.max(0, this.heatEnergy - heat);
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {tanks[1]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tanks[0]};
	}
}
