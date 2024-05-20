package com.hbm.util;

public class AstronomyUtil {

	// the G in G*M1*M2/r
	public static final float GRAVITATIONAL_CONSTANT = 6.6743015e-11F;

	// Default orbital altitude, added onto planet radius to get intended orbital radius
	public static final float DEFAULT_ALTITUDE_KM = 100;

	// How many seconds in a MC day
	public static final long SECONDS_IN_DAY = 20 * 60;
	public static final long SECONDS_IN_KSP_DAY = 6 * 60 * 60;
	public static final long TICKS_IN_DAY = SECONDS_IN_DAY * 20;

	// Day length in KSP -> day length in MC
	// This conversion will make orbital mechanics run a considerable fraction faster than normal
	public static final double DAY_FACTOR = (double)SECONDS_IN_DAY / (double)SECONDS_IN_KSP_DAY;

	// Default for how fast the player character accelerates downwards due to gravity in m/s/s
	public static final float STANDARD_GRAVITY = 1.6F; // 0.08 per tick
	public static final float PLAYER_GRAVITY_MODIFIER = STANDARD_GRAVITY / 9.81F;

	public static final float KM_IN_AU = 13_599_840F; // Kerbin orbit is 1 AU

	// How quickly time moves, for testing celestial mechanics
	public static final long TIME_MULTIPLIER = 1;

	// Conversion rate from millibuckets to atmospheres
	// 1 atmosphere is 1 gigabucket
	public static final double MB_PER_ATM = 1_000_000_000D * 1_000D;

}