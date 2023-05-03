package com.hbm.entity.train;

import com.hbm.blocks.rail.IRailNTM.TrackGauge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
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
		this.setSize(2F, 1F);
	}

	@Override
	public double getCurrentSpeed() {
		return this.riddenByEntity instanceof EntityPlayer ? ((EntityPlayer) this.riddenByEntity).moveForward * 0.125D : 0;
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
		return Vec3.createVectorHelper(0.75, 1.75, 0.75);
	}

	@Override
	public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
		if(!this.worldObj.isRemote && !this.isDead) {
			this.setDead();
		}
		
		return true;
	}
}
