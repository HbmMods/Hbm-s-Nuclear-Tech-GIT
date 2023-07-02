package com.hbm.entity.train;

import com.hbm.blocks.rail.IRailNTM.TrackGauge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TrainTunnelBore extends EntityRailCarRidable {

	public TrainTunnelBore(World world) {
		super(world);
		this.setSize(6F, 4F);
	}

	@Override public double getPoweredAcceleration() { return 0.01; }
	@Override public double getPassivBrake() { return 0.95; }
	@Override public boolean shouldUseEngineBrake(EntityPlayer player) { return Math.abs(this.engineSpeed) < 0.1; }
	@Override public double getMaxPoweredSpeed() { return 0.5; }
	@Override public double getMaxRailSpeed() { return 1; }

	@Override public TrackGauge getGauge() { return TrackGauge.STANDARD; }
	@Override public double getLengthSpan() { return 2.5; }
	@Override public double getCollisionSpan() { return 4.5; }
	@Override public Vec3 getRiderSeatPosition() { return Vec3.createVectorHelper(0.0, 2.375, -2.375); }
	@Override public boolean shouldRiderSit() { return true; }
	@Override public int getSizeInventory() { return 0; }
	@Override public String getInventoryName() { return this.hasCustomInventoryName() ? this.getEntityName() : "container.trainTunnelBore"; }
	@Override public double getCouplingDist(TrainCoupling coupling) { return coupling != null ? 2.75 : 0; }

	@Override public boolean canAccelerate() { return true; }
	@Override public Vec3[] getPassengerSeats() { return new Vec3[0]; }

	@Override
	public DummyConfig[] getDummies() {
		return new DummyConfig[] {
				new DummyConfig(2F, 3F, Vec3.createVectorHelper(0, 0, 2.5)),
				new DummyConfig(2F, 3F, Vec3.createVectorHelper(0, 0, 1.25)),
				new DummyConfig(2F, 3F, Vec3.createVectorHelper(0, 0, 0)),
				new DummyConfig(2F, 3F, Vec3.createVectorHelper(0, 0, -1.25)),
				new DummyConfig(2F, 3F, Vec3.createVectorHelper(0, 0, -2.5))
		};
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(!this.worldObj.isRemote && !this.isDead) {
			this.setDead();
		}
		
		return true;
	}
}
