package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.fluid.FluidType;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineChemfac extends TileEntityMachineChemplantBase {

	public TileEntityMachineChemfac() {
		super(77);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!worldObj.isRemote) {
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setIntArray("progress", this.progress);
			data.setIntArray("maxProgress", this.maxProgress);
			data.setBoolean("isProgressing", isProgressing);
			
			for(int i = 0; i < tanks.length; i++) {
				tanks[i].writeToNBT(data, "t" + i);
			}
			
			this.networkPack(data, 150);
		} else {
			
			if(this.worldObj.getTotalWorldTime() % 5 == 0) {
				
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
				ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
				Random rand = worldObj.rand;
				
				double x = xCoord + 0.5 - rot.offsetX * 0.5;
				double y = yCoord + 3;
				double z = zCoord + 0.5 - rot.offsetZ * 0.5;

				worldObj.spawnParticle("cloud", x + dir.offsetX * 1.5 + rand.nextGaussian() * 0.15, y, z + dir.offsetZ * 1.5 + rand.nextGaussian() * 0.15, 0.0, 0.15, 0.0);
				worldObj.spawnParticle("cloud", x - dir.offsetX * 0.5 + rand.nextGaussian() * 0.15, y, z - dir.offsetZ * 0.5 + rand.nextGaussian() * 0.15, 0.0, 0.15, 0.0);
			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.progress = nbt.getIntArray("progress");
		this.maxProgress = nbt.getIntArray("maxProgress");
		this.isProgressing = nbt.getBoolean("isProgressing");

		for(int i = 0; i < tanks.length; i++) {
			tanks[i].readFromNBT(nbt, "t" + i);
		}
	}

	@Override
	public long getMaxPower() {
		return 10_000_000;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		
	}

	@Override
	public boolean getTact() {
		return false;
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return new ArrayList();
	}

	@Override
	public void clearFluidList(FluidType type) {
		
	}

	@Override
	public int getRecipeCount() {
		return 8;
	}

	@Override
	public int getTankCapacity() {
		return 32_000;
	}

	@Override
	public int getTemplateIndex(int index) {
		return 13 + index * 9;
	}

	@Override
	public int[] getSlotIndicesFromIndex(int index) {
		return new int[] {5 + index * 9, 8 + index * 9, 9 + index * 9, 12 + index * 9};
	}

	@Override
	public ChunkCoordinates[] getInputPositions() {
		return new ChunkCoordinates[0];
	}

	@Override
	public ChunkCoordinates[] getOutputPositions() {
		return new ChunkCoordinates[0];
	}

	@Override
	public String getName() {
		return "container.machineChemFac";
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 5,
					yCoord,
					zCoord - 5,
					xCoord + 5,
					yCoord + 4,
					zCoord + 5
					);
		}
		
		return bb;
	}
}
