package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyUser;
import net.minecraft.util.ChunkCoordinates;

public abstract class TileEntityMachineAssemblerBase extends TileEntityMachineBase implements IEnergyUser {

	public long power;
	public int[] progress;
	public int[] maxProgress;
	public boolean isProgressing;

	public TileEntityMachineAssemblerBase(int scount) {
		super(scount);
		
		int count = this.getRecipeCount();

		progress = new int[count];
		maxProgress = new int[count];
	}

	public abstract int getRecipeCount();
	public abstract int getTemplateIndex(int index);
	
	/**
	 * @param index
	 * @return A size 4 int array containing min input, max input and output indices in that order.
	 */
	public abstract int[] getSlotIndicesFromIndex(int index);
	public abstract ChunkCoordinates[] getInputPositions();
	public abstract ChunkCoordinates[] getOutputPositions();
}
