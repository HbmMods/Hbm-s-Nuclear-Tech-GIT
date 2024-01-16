package com.hbm.blocks.rail;

import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/** in retrospect, not the best name i could have chosen */
public interface IRailNTM {

	/** Returns a vector pointing to the closest snapping position given the starting position */
	public Vec3 getSnappingPos(World world, int x, int y, int z, double trainX, double trainY, double trainZ);
	
	/**
	 * Returns a location on the rail based on the train's current X/Y/Z momentum as well as the intended speed along the rail.
	 * If the train would leave the rail within that tick, the position is the last valid position on that rail.
	 * Inherently safer than simply adding the motion to the position and then snapping, since that may lead to derailing.
	 * The motion has to be calculated from the train's rotation (rotated 180° when going backwards), the scalar doesn't matter since it's only used for determining orientation in a clear way.
	 * Motion ends up being *-1 if the train is going in reverse, still pointing forwards despite the speed being negative.
	 * Also features RailContext which determines overshoot and the final yaw rotation
	 * */
	public Vec3 getTravelLocation(World world, int x, int y, int z, double trainX, double trainY, double trainZ, double motionX, double motionY, double motionZ, double speed, RailContext info, MoveContext context);
	
	/** Returns that rail's gauge. Trains will derail if the gauge does not match. */
	public TrackGauge getGauge(World world, int x, int y, int z);
	
	public static enum TrackGauge {
		STANDARD,	//roughly 1.5m
		NARROW		//roughly 0.75m
	}
	
	/** A wrapper for all relevant info required when leaving a rail */
	public static class RailContext {
		/** The angle at which the train ends up being on this rail */
		public float yaw;
		/** The amount of blocks still left to travel after completing the rail */
		public double overshoot;
		/** The exit position of that rail */
		public BlockPos pos;
		public RailContext yaw(float y) { this.yaw = y; return this; }
		public RailContext dist(double d) { this.overshoot = d; return this; }
		public RailContext pos(BlockPos d) { this.pos = d; return this; }
	}
	
	/** A wrapper for additional information like stopping on rails and what type of check we're doing */
	public static class MoveContext {
		public RailCheckType type;
		public double collisionBogieDistance;
		/** if a buffer stop or similar applies */
		public boolean collision = false;
		/** how much of the travel distance was cut short */
		public double overshoot;
		
		public MoveContext(RailCheckType type, double collisionBogieDistance) {
			this.type = type;
			this.collisionBogieDistance = collisionBogieDistance;
		}
	}
	
	public static enum RailCheckType {
		CORE,
		FRONT,
		BACK,
		OTHER
	}
}
