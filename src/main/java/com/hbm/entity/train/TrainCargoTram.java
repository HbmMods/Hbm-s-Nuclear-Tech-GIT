package com.hbm.entity.train;

import com.hbm.blocks.rail.IRailNTM.TrackGauge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
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
	
	public double speed = 0;
	public static final double maxSpeed = 0.5;
	public static final double acceleration = 0.01;
	public static final double deceleration = 0.75;

	@Override
	public double getCurrentSpeed() { // in its current form, only call once per tick
		
		if(this.riddenByEntity instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) this.riddenByEntity;
			
			if(player.moveForward > 0) {
				speed += acceleration;
			} else if(player.moveForward < 0) {
				speed -= acceleration;
			} else {
				speed *= deceleration;
			}
			
		} else {
			speed *= deceleration;
		}
		
		speed = MathHelper.clamp_double(speed, -maxSpeed, maxSpeed);
		
		return speed;
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
		return Vec3.createVectorHelper(0.375, 1.75, 0.5);
	}

	@Override
	public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
		if(!this.worldObj.isRemote && !this.isDead) {
			this.setDead();
		}
		
		return true;
	}
}
