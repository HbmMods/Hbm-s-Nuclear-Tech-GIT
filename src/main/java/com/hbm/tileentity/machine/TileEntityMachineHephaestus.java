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
	
	protected void heatFluid() {
		
		FluidType type = input.getTankType();
		
		if(type.hasTrait(FT_Heatable.class)) {
			FT_Heatable trait = type.getTrait(FT_Heatable.class);
			int heat = this.getTotalHeat();
			HeatingStep step = trait.getFirstStep();
			
			int inputOps = input.getFill() / step.amountReq;
			int outputOps = (output.getMaxFill() - output.getFill()) / step.amountProduced;
			int heatOps = heat / step.heatReq;
			int ops = Math.min(Math.min(inputOps, outputOps), heatOps);

			input.setFill(input.getFill() - step.amountReq * ops);
			output.setFill(output.getFill() + step.amountProduced * ops);
			worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
		}
	}
	
	protected void setupTanks() {
		
		FluidType type = input.getTankType();
		
		if(type.hasTrait(FT_Heatable.class)) {
			FT_Heatable trait = type.getTrait(FT_Heatable.class);
			
			if(trait.getEfficiency(HeatingType.HEATEXCHANGER) > 0) {
				FluidType outType = trait.getFirstStep().typeProduced;
				output.setTankType(outType);
				return;
			}
		}

		input.setTankType(Fluids.NONE);
		output.setTankType(Fluids.NONE);
	}
	
	protected int heatFromBlock(int x, int y, int z) {
		Block b = worldObj.getBlock(x, y, z);
		
		if(b == Blocks.lava || b == Blocks.flowing_lava)	return 25;
		if(b == ModBlocks.volcanic_lava_block)				return 500;
		
		if(b == ModBlocks.ore_volcano) {
			this.fissureScanTime = worldObj.getTotalWorldTime();
			return 1_000;
		}
		
		return 0;
	}
	
	public int getTotalHeat() {
		boolean fissure = worldObj.getTotalWorldTime() - this.fissureScanTime < 20;
		int heat = 0;
		
		for(int h : this.heat) {
			heat += h;
		}
		
		if(fissure) {
			heat *= 3;
		}
		
		return heat;
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
				new DirPos(xCoord + 2, yCoord, zCoord, Library.POS_X),
				new DirPos(xCoord - 2, yCoord, zCoord, Library.NEG_X),
				new DirPos(xCoord, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord, yCoord, zCoord - 2, Library.NEG_Z),
				new DirPos(xCoord + 2, yCoord + 11, zCoord, Library.POS_X),
				new DirPos(xCoord - 2, yCoord + 11, zCoord, Library.NEG_X),
				new DirPos(xCoord, yCoord + 11, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord, yCoord + 11, zCoord - 2, Library.NEG_Z)
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
