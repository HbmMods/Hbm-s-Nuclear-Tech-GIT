package com.hbm.interfaces;

import net.minecraft.world.World;

public interface IReactor {
	
	boolean isStructureValid(World world);
	
	boolean isCoatingValid(World world);

	boolean hasFuse();
	
	int getWaterScaled(int i);
	
	int getCoolantScaled(int i);
	
	long getPowerScaled(long i);

	int getHeatScaled(int i);

}
