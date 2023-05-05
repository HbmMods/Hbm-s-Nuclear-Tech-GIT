package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.gas.BlockGasAir;
import com.hbm.config.GeneralConfig;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.INBTPacketReceiver;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardReceiver;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityAirPump extends TileEntity implements IFluidStandardReceiver, INBTPacketReceiver {
	private static final int START_CONCENTRATION_VALUE = 15;
	private int cooldownTicks = 0;
	
	public static final int flucue = 100;

	public FluidTank tank;

	public TileEntityAirPump() {
		tank = new FluidTank(Fluids.OXYGEN, 16000);
		
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote && worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
			this.subscribeToAllAround(tank.getTankType(), this);
			spread(xCoord, yCoord, zCoord, 0);

			NBTTagCompound data = new NBTTagCompound();
			tank.writeToNBT(data, "at");
			INBTPacketReceiver.networkPack(this, data, 15);
		}
	}
	

	
	/*private void releaseAir(final int xOffset, final int yOffset, final int zOffset) {
		final Block block = worldObj.getBlock(xCoord + xOffset, yCoord + yOffset, zCoord + zOffset);
		if (block.isAir(worldObj, xCoord + xOffset, yCoord + yOffset, zCoord + zOffset)) {// can be air
			//final int energy_cost = (!block.isAssociatedBlock(WarpDrive.blockAir)) ? WarpDriveConfig.BREATHING_ENERGY_PER_NEW_AIR_BLOCK[0] : WarpDriveConfig.BREATHING_ENERGY_PER_EXISTING_AIR_BLOCK[0];
			//if (isEnabled && energy_consume(energy_cost, true)) {// enough energy and enabled
				if (worldObj.setBlock(xCoord + xOffset, yCoord + yOffset, zCoord + zOffset, ModBlocks.air_block, START_CONCENTRATION_VALUE, 2)) {
					// (needs to renew air or was not maxed out)
					energy_consume(WarpDriveConfig.BREATHING_ENERGY_PER_NEW_AIR_BLOCK[0], false);
				} else {
					energy_consume(WarpDriveConfig.BREATHING_ENERGY_PER_EXISTING_AIR_BLOCK[0], false);
				}
			} else {// low energy => remove air block
				if (block.isAssociatedBlock(WarpDrive.blockAir)) {
					final int metadata = worldObj.getBlockMetadata(xCoord + xOffset, yCoord + yOffset, zCoord + zOffset);
					if (metadata > 4) {
						worldObj.setBlockMetadataWithNotify(xCoord + xOffset, yCoord + yOffset, zCoord + zOffset, metadata - 4, 2);
					} else if (metadata > 1) {
						worldObj.setBlockMetadataWithNotify(xCoord + xOffset, yCoord + yOffset, zCoord + zOffset, 1, 2);
					} else {
						// worldObj.setBlockMetadataWithNotify(xCoord + xOffset, yCoord + yOffset,  zCoord + zOffset, 0, 0, 2);
					}
				}
			//}
		}
	}*/
	
	
	private void spread(int x, int y, int z, int index) {
		if(tank.getFill() < flucue)return; 
		
		if(index > 8)
			return;
		
		if(worldObj.getBlock(x, y, z).isAir(worldObj, x, y, z) || worldObj.getBlock(x, y, z) instanceof BlockGasAir)
			worldObj.setBlock(x, y, z, ModBlocks.air_block9);
		
		if(!(worldObj.getBlock(x, y, z) instanceof BlockGasAir) && worldObj.getBlock(x, y, z) != ModBlocks.air_vent)
			return;
		
		switch(worldObj.rand.nextInt(6)) {
		case 0:
			spread(x + 1, y, z, index + 1);
			break;
		case 1:
			spread(x - 1, y, z, index + 1);
			break;
		case 2:
			spread(x, y + 1, z, index + 1);
			break;
		case 3:
			spread(x, y - 1, z, index + 1);
			break;
		case 4:
			spread(x, y, z + 1, index + 1);
			break;
		case 5:
			spread(x, y, z - 1, index + 1);
			break;
		}

		if(this.tank.getFill() >= flucue) {
			int amountToBurn = Math.min(10, this.tank.getFill());
			if(amountToBurn > 0) {
				this.tank.setFill(this.tank.getFill() - amountToBurn);
			}	
		}
		this.markDirty();
	}


	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.tank.readFromNBT(nbt, "at");

	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tank.readFromNBT(nbt, "at");

	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		tank.writeToNBT(nbt, "at");

	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {tank};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tank};
	}
}


