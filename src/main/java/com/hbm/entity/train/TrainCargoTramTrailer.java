package com.hbm.entity.train;

import com.hbm.blocks.rail.IRailNTM.TrackGauge;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TrainCargoTramTrailer extends EntityRailCarCargo {

	/*
	 * 
	 *                         <--
	 *                         
	 * _O\____________________________________________/O_
	 * |____|                                      |____|
	 *    \__________________________________________/
	 *        '( + )'                      '( + )'
	 * 
	 */

	public TrainCargoTramTrailer(World world) {
		super(world);
		this.setSize(5F, 2F);
	}

	@Override public double getMaxRailSpeed() { return 1; }
	@Override public TrackGauge getGauge() { return TrackGauge.STANDARD; }
	@Override public double getLengthSpan() { return 1.5; }
	@Override public int getSizeInventory() { return 29; }
	@Override public String getInventoryName() { return this.hasCustomInventoryName() ? this.getEntityName() : "container.trainTramTrailer"; }
	@Override public AxisAlignedBB getCollisionBox() { return AxisAlignedBB.getBoundingBox(renderX, renderY, renderZ, renderX, renderY + 1, renderZ).expand(4, 0, 4); }
	@Override public double getCouplingDist(TrainCoupling coupling) { return coupling != null ? 2.75 : 0; }

	@Override
	public double getCurrentSpeed() {
		return 0; //we'll figure out how linked carts work later on - i hope
	}

	@Override
	public DummyConfig[] getDummies() {
		return new DummyConfig[] {
				new DummyConfig(2F, 1F, Vec3.createVectorHelper(0, 0, 1.5)),
				new DummyConfig(2F, 1F, Vec3.createVectorHelper(0, 0, 0)),
				new DummyConfig(2F, 1F, Vec3.createVectorHelper(0, 0, -1.5))
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
