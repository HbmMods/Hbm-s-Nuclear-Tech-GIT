package com.hbm.blocks.rail;

import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public interface IRailNTM {

	/** Returns a vector pointing to the closest snapping position given the starting position */
	public Vec3 getSnappingPos(World world, int x, int y, int z, double trainX, double trainY, double trainZ);
	
	/**
	 * Returns a location on the rail based on the train's current X/Y/Z momentum as well as the intended speed along the rail.
	 * If the train would leave the rail within that tick, the position is the last valid position on that rail.
	 * Inherently safer than simply adding the motion to the position and then snapping, since that may lead to derailing.
	 * The motion has to be calculated from the train's rotation, the scalar doesn't matter since it's only used for determining orientation in a clear way.
	 * Motion ends up being *-1 if the train is going in reverse, still pointing forwards despite the speed being negative.
	 * Also features a double[] wrapper with size 1 which holds the speed value that overshoots the rail.
	 * */
	public Vec3 getTravelLocation(World world, int x, int y, int z, double trainX, double trainY, double trainZ, double motionX, double motionY, double motionZ, double speed, double[] leftover);
	
	/** Returns that rail'S gauge. Trains will derail if the gauge does not match. */
	public TrackGauge getGauge(World world, int x, int y, int z);
	
	public static enum TrackGauge {
		STANDARD //roughly 1.5m
	}
}
