package com.hbm.entity.train;

import com.hbm.blocks.rail.IRailNTM.TrackGauge;

import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TrainCargoTram extends EntityRailCarRidable {

	/*
	 * 
	 *     _________
	 *    | |       \          <--
	 *    | |       |___
	 *    | |       |  |                             |
	 * _O\|_|_______|__|_____________________________|/O_
	 * |____|                                      |____|
	 *    \__________________________________________/
	 *        '( + )'                      '( + )'
	 * 
	 */

	public TrainCargoTram(World world) {
		super(world);
	}

	@Override
	public double getCurrentSpeed() {
		return 0;
	}

	@Override
	public TrackGauge getGauge() {
		return TrackGauge.STANDARD;
	}

	@Override
	public double getLengthSpan() {
		return 2;
	}

	@Override
	public Vec3 getRiderSeatPosition() {
		return Vec3.createVectorHelper(1, 1, 0);
	}
}
