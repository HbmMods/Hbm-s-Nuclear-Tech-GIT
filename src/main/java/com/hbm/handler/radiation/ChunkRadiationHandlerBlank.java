package com.hbm.handler.radiation;

import net.minecraft.world.World;

public class ChunkRadiationHandlerBlank extends ChunkRadiationHandler {

	@Override public float getRadiation(World world, int x, int y, int z) { return 0; }
	@Override public void setRadiation(World world, int x, int y, int z, float rad) { }
	@Override public void incrementRad(World world, int x, int y, int z, float rad) { }
	@Override public void decrementRad(World world, int x, int y, int z, float rad) { }
	@Override public void updateSystem() { }
	@Override public void clearSystem(World world) { }
}
