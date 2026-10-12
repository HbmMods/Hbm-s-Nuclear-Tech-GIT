package com.hbm.tileentity.turret;

import com.hbm.lib.Library;
import com.hbm.tileentity.IRadarCommandReceiver;
import com.hbm.util.Vec3NT;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.Vec3;

public abstract class TileEntityTurretRadarCommandBase extends TileEntityTurretBaseNT implements IRadarCommandReceiver {
	
	// if target was designated by radar
	public boolean radarDesignatedTarget = false;
	
	@Override
	public boolean sendCommandEntity(Entity target) {
		if(this.target != null || !isOn) return false;
		if(target == null || !target.isEntityAlive()) return false;

		this.radarDesignatedTarget = true;
		if(!entityAcceptableTarget(target) || !entityInLOS(target)) return false;

		Vec3 pos = this.getTurretPos();
		Vec3 ent = this.getEntityPos(target);
		double dist = pos.distanceTo(ent);

		if(dist > this.getDecetorRange() * 4) {
			turnTowards(ent);
			return true;
		}

		this.target = target;
		return true;
	}

	@Override
	public boolean sendCommandPosition(int x, int y, int z) {
		if(!isOn) return false;
		turnTowards(new Vec3NT(x, y, z));
		return true;
	}

	@Override
	public void updateEntity() {

		if(worldObj.isRemote) {
			this.lastRotationPitch = this.rotationPitch;
			this.lastRotationYaw = this.rotationYaw;
			this.rotationPitch = this.syncRotationPitch;
			this.rotationYaw = this.syncRotationYaw;
		}

		if(!worldObj.isRemote) {

			this.setupAllPorts(getPorts());
			this.updatePortPIFIFO();

			this.aligned = false;

			if(this.target != null && !target.isEntityAlive()) {
				this.target = null;
				this.radarDesignatedTarget = false;
				this.stattrak++;
			}

			if(target != null) {
				if(!this.entityInLOS(this.target)) {
					this.target = null;
					this.radarDesignatedTarget = false;
				}
			}

			if(target != null) {
				this.tPos = this.getEntityPos(target);
			} else {
				this.tPos = null;
			}

			if(isOn() && hasPower()) {

				if(tPos != null)
					this.alignTurret();
			} else {

				this.target = null;
				this.tPos = null;
			}

			if(this.target != null && !target.isEntityAlive()) {
				this.target = null;
				this.tPos = null;
				this.radarDesignatedTarget = false;
				this.stattrak++;
			}

			if(isOn() && hasPower()) {
				searchTimer--;

				this.setPower(this.getPower() - this.getConsumption());

				if(searchTimer <= 0) {
					searchTimer = this.getDecetorInterval();

					if(this.target == null)
						this.seekNewTarget();
				}
			} else {
				searchTimer = 0;
			}

			if(this.aligned) {
				this.updateFiringTick();
			}

			this.power = Library.chargeTEFromItems(slots, 10, this.power, this.getMaxPower());

			this.networkPackNT(250);

			if(usesCasings() && this.casingDelay() > 0) {
				if(casingDelay > 0) {
					casingDelay--;
				} else {
					spawnCasing();
				}
			}

		} else {

			//this will fix the interpolation error when the turret crosses the 360° point
			if(Math.abs(this.lastRotationYaw - this.rotationYaw) > Math.PI) {

				if(this.lastRotationYaw < this.rotationYaw)
					this.lastRotationYaw += Math.PI * 2;
				else
					this.lastRotationYaw -= Math.PI * 2;
			}
		}
	}

	/**
	 * Checks line of sight to the passed entity along with whether the angle falls within swivel range
	 * @return true if entity is in line of sight otherwise false
	 */
	public boolean entityInLOS(Entity e) {

		if(e.isDead || !e.isEntityAlive())
			return false;

		if(!hasThermalVision() && e instanceof EntityLivingBase && ((EntityLivingBase)e).isPotionActive(Potion.invisibility))
			return false;

		Vec3 pos = this.getTurretPos();
		Vec3 ent = this.getEntityPos(e);
		Vec3 delta = Vec3.createVectorHelper(ent.xCoord - pos.xCoord, ent.yCoord - pos.yCoord, ent.zCoord - pos.zCoord);
		double length = delta.lengthVector();

		if(length < this.getDecetorGrace() || (!this.radarDesignatedTarget && length > this.getDecetorRange() * 1.1)) //the latter statement is only relevant for entities that have already been detected
			return false;

		delta = delta.normalize();
		double pitch = Math.asin(delta.yCoord / delta.lengthVector());
		double pitchDeg = Math.toDegrees(pitch);

		//check if the entity is within swivel range
		if(pitchDeg < -this.getTurretDepression() || pitchDeg > this.getTurretElevation())
			return false;

		return !Library.isObstructedOpaque(worldObj, ent.xCoord, ent.yCoord, ent.zCoord, pos.xCoord, pos.yCoord, pos.zCoord);
	}
}
