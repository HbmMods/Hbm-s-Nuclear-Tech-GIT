package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.fluid.FluidType;

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
		return 0;
	}

	@Override
	public int getTankCapacity() {
		return 0;
	}

	@Override
	public int getTemplateIndex(int index) {
		return 0;
	}

	@Override
	public int[] getSlotIndicesFromIndex(int index) {
		return new int[] {4, 4, 4, 4}; //yeah whatever
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
}
