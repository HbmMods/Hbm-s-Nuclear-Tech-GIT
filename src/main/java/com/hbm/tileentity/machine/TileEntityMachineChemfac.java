package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.fluid.FluidType;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;

public class TileEntityMachineChemfac extends TileEntityMachineChemplantBase {

	public TileEntityMachineChemfac() {
		super(77);
	}

	@Override
	public long getMaxPower() {
		return 0;
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
