package com.hbm.util.fauxpointtwelve;

import com.hbm.interfaces.Spaghetti;
import net.minecraftforge.common.util.ForgeDirection;

public enum Rotation {

	NONE(), CLOCKWISE_90(), CLOCKWISE_180(), COUNTERCLOCKWISE_90();

	/**
	 * Verbatim code from MC 1.12 (net.minecraft.util.Rotation)
	 * @param rotation
	 * @return
	 */
	@Spaghetti("thanks for the cool code, mojang")
	public Rotation add(Rotation rotation) {
		switch(rotation) {
		case CLOCKWISE_180:
			switch(this) {
			case NONE: return CLOCKWISE_180;
			case CLOCKWISE_90: return COUNTERCLOCKWISE_90;
			case CLOCKWISE_180: return NONE;
			case COUNTERCLOCKWISE_90: return CLOCKWISE_90;
			}

		case COUNTERCLOCKWISE_90:
			switch(this) {
			case NONE: return COUNTERCLOCKWISE_90;
			case CLOCKWISE_90: return NONE;
			case CLOCKWISE_180: return CLOCKWISE_90;
			case COUNTERCLOCKWISE_90: return CLOCKWISE_180;
			}

		case CLOCKWISE_90:
			switch(this) {
			case NONE: return CLOCKWISE_90;
			case CLOCKWISE_90: return CLOCKWISE_180;
			case CLOCKWISE_180: return COUNTERCLOCKWISE_90;
			case COUNTERCLOCKWISE_90: return NONE;
			}

		default: return this;
		}
	}

	/**
	 * Adjusted code from NTM 1.12 (com.hbm.lib.ForgeDirection)
	 * @param dir
	 * @return
	 */
	public static Rotation getBlockRotation(ForgeDirection dir){
		switch(dir) {
		case NORTH: return Rotation.NONE;
		case SOUTH: return Rotation.CLOCKWISE_180;
		case EAST: return Rotation.COUNTERCLOCKWISE_90;
		case WEST: return Rotation.CLOCKWISE_90;
		default: return Rotation.NONE;
		}
	}
}
