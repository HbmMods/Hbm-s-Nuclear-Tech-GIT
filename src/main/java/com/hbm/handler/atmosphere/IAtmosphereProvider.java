package com.hbm.handler.atmosphere;

import com.hbm.handler.ThreeInts;
import com.hbm.inventory.fluid.FluidType;

import net.minecraft.world.World;

public interface IAtmosphereProvider {

	World getWorld();
	
	// the maximum distance an object can maintain a blob
	int getMaxBlobRadius();
	
	// the position to use as root, if it can't be pathed to, the bubble is popped
	ThreeInts getRootPosition();

	// Get atmospheric information
	FluidType getFluidType();
	double getFluidPressure();

	void consume(int amount);

	void onBlobCreated(AtmosphereBlob blob);

}
