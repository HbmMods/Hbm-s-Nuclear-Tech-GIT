package com.hbm.tileentity.turret;

import java.util.List;

import com.hbm.entity.logic.EntityBomber;
import com.hbm.entity.missile.EntityMissileBase;
import com.hbm.entity.missile.EntityMissileBaseAdvanced;
import com.hbm.handler.BulletConfiguration;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.FakePlayer;

/**
 * More over-engineered than ever, but chopping this thing into the smallest possible pieces makes it easier for my demented brain to comprehend
 * @author hbm
 *
 */
public abstract class TileEntityTurretBaseNT extends TileEntityMachineBase {

	//this time we do all rotations in radians
	//what way are we facing?
	public double rotationYaw;
	public double rotationPitch;
	//only used by clients for interpolation
	public double lastRotationYaw;
	public double lastRotationPitch;
	//is the turret on?
	public boolean isOn = false;
	//is the turret aimed at the target?
	public boolean aligned = false;
	//how many ticks until the next check
	public int searchTimer;

	public boolean targetPlayers = false;
	public boolean targetAnimals = false;
	public boolean targetMobs = true;
	public boolean targetMachines = true;
	
	public Entity target;
	
	//tally marks!
	public int stattrak;
	
	/**
	 * 		 X
	 * 
	 * 		YYY
	 * 		YYY
	 * 		YYY Z
	 * 
	 * 		X -> ai slot		(0)
	 * 		Y -> ammo slots		(1 - 9)
	 * 		Z -> battery slot	(10)
	 */
	
	public TileEntityTurretBaseNT() {
		super(11);
	}
	
	@Override
	public void updateEntity() {
		
		if(target != null) {
			if(!this.entityInLOS(this.target)) {
				this.target = null;
			}
		}
		
		if(worldObj.isRemote) {
			this.lastRotationPitch = this.rotationPitch;
			this.lastRotationYaw = this.rotationYaw;
		}
		
		if(target != null) {
			this.aligned = false;
			this.alignTurret();
		} else {
			this.aligned = false;
		}
		
		if(!worldObj.isRemote) {
			
			searchTimer--;
			
			if(searchTimer <= 0) {
				searchTimer = this.getDecetorInterval();
				this.seekNewTarget();
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("target", this.target == null ? -1 : this.target.getEntityId());
			this.networkPack(data, 250);
			
		} else {
			
			Vec3 pos = this.getTurretPos();
			Vec3 vec = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
			vec.rotateAroundZ((float) -this.rotationPitch);
			vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
			
			//this will fix the interpolation error when the turret crosses the 360° point
			if(Math.abs(this.lastRotationYaw - this.rotationYaw) > Math.PI) {
				
				if(this.lastRotationYaw < this.rotationYaw)
					this.lastRotationYaw += Math.PI * 2;
				else
					this.lastRotationYaw -= Math.PI * 2;
			}

			worldObj.spawnParticle("cloud", pos.xCoord, pos.yCoord, pos.zCoord, 0.0, 0.1, 0.0);
			
			if(this.aligned)
				worldObj.spawnParticle("flame", pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord, vec.xCoord, vec.yCoord, vec.zCoord);
			if(this.target != null)
				worldObj.spawnParticle("smoke", pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord, vec.xCoord, vec.yCoord, vec.zCoord);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		
		int t = nbt.getInteger("target");
		
		if(t != -1)
			this.target = worldObj.getEntityByID(t);
	}
	
	/**
	 * Reads the namelist from the AI chip in slot 0
	 * @return null if there is either no chip to be found or if the name list is empty, otherwise it just reads the strings from the chip's NBT
	 */
	public List<String> getWhitelist() {
		return null;
	}
	
	/**
	 * Finds the nearest acceptable target within range and in line of sight
	 */
	protected void seekNewTarget() {
		
		Vec3 pos = this.getTurretPos();
		double range = this.getDecetorRange();
		List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(pos.xCoord, pos.yCoord, pos.zCoord, pos.xCoord, pos.yCoord, pos.zCoord).expand(range, range, range));
		
		Entity target = null;
		double closest = range;
		
		for(Entity entity : entities) {

			Vec3 ent = this.getEntityPos(entity);
			Vec3 delta = Vec3.createVectorHelper(ent.xCoord - pos.xCoord, ent.yCoord - pos.yCoord, ent.zCoord - pos.zCoord);
			
			double dist = delta.lengthVector();
			
			//check if it's in range
			if(dist > range)
				continue;
			
			//check if we should even fire at this entity
			if(!entityAcceptableTarget(entity))
				continue;
			
			//check for visibility
			if(!entityInLOS(entity))
				continue;
			
			//replace current target if this one is closer
			if(dist < closest) {
				closest = dist;
				target = entity;
			}
		}
		
		this.target = target;
	}
	
	/**
	 * Turns the turret by a specific amount of degrees towards the target
	 * Assumes that the target is not null
	 */
	protected void alignTurret() {
		
		double turnYaw = Math.toRadians(this.getTurretYawSpeed());
		double turnPitch = Math.toRadians(this.getTurretPitchSpeed());
		double pi2 = Math.PI * 2;

		Vec3 pos = this.getTurretPos();
		Vec3 ent = this.getEntityPos(target);
		Vec3 delta = Vec3.createVectorHelper(ent.xCoord - pos.xCoord, ent.yCoord - pos.yCoord, ent.zCoord - pos.zCoord);
		
		double targetPitch = Math.asin(delta.yCoord / delta.lengthVector());
		double targetYaw = -Math.atan2(delta.xCoord, delta.zCoord);
		
		//if we are about to overshoot the target by turning, just snap to the correct rotation
		if(Math.abs(this.rotationPitch - targetPitch) < turnPitch || Math.abs(this.rotationPitch - targetPitch) > pi2 - turnPitch) {
			this.rotationPitch = targetPitch;
		} else {
			
			if(targetPitch > this.rotationPitch)
				this.rotationPitch += turnPitch;
			else
				this.rotationPitch -= turnPitch;
		}
		
		double deltaYaw = (targetYaw - this.rotationYaw) % pi2;
		
		//determines what direction the turret should turn
		//used to prevent situations where the turret would do almost a full turn when
		//the target is only a couple degrees off while being on the other side of the 360° line
		int dir = 0;

		if(deltaYaw < -Math.PI)
			dir = 1;
		else if(deltaYaw < 0)
			dir = -1;
		else if(deltaYaw > Math.PI)
			dir = -1;
		else if(deltaYaw > 0)
			dir = 1;
		
		if(Math.abs(this.rotationYaw - targetYaw) < turnYaw || Math.abs(this.rotationYaw - targetYaw) > pi2 - turnYaw) {
			this.rotationYaw = targetYaw;
		} else {
			this.rotationYaw += turnYaw * dir;
		}
		
		double deltaPitch = targetPitch - this.rotationPitch;
		deltaYaw = targetYaw - this.rotationYaw;
		
		double deltaAngle = Math.sqrt(deltaYaw * deltaYaw + deltaPitch * deltaPitch);

		this.rotationYaw = this.rotationYaw % pi2;
		this.rotationPitch = this.rotationPitch % pi2;
		
		if(deltaAngle <= Math.toRadians(this.getAcceptableInaccuracy())) {
			this.aligned = true;
		}
	}
	
	/**
	 * Checks line of sight to the passed entity along with whether the angle falls within swivel range
	 * @return
	 */
	public boolean entityInLOS(Entity e) {
		
		Vec3 pos = this.getTurretPos();
		Vec3 ent = this.getEntityPos(e);
		Vec3 delta = Vec3.createVectorHelper(ent.xCoord - pos.xCoord, ent.yCoord - pos.yCoord, ent.zCoord - pos.zCoord);
		
		if(delta.lengthVector() < this.getDecetorGrace())
			return false;
		
		delta = delta.normalize();
		double pitch = Math.asin(delta.yCoord / delta.lengthVector());
		double pitchDeg = Math.toDegrees(pitch);
		
		//check if the entity is within swivel range
		if(pitchDeg < -this.getTurretDepression() || pitchDeg > this.getTurretElevation())
			return false;
		
		return !Library.isObstructed(worldObj, pos.xCoord, pos.yCoord, pos.zCoord, ent.xCoord, ent.yCoord, ent.zCoord);
	}
	
	/**
	 * Returns true if the entity is considered for targeting
	 * @return
	 */
	public boolean entityAcceptableTarget(Entity e) {
		
		if(e.isDead || !e.isEntityAlive())
			return false;
		
		if(targetAnimals && e instanceof EntityAnimal)
			return true;
		
		if(targetMobs && e instanceof IMob)
			return true;
		
		if(targetMachines) {

			if(e instanceof EntityMissileBaseAdvanced)
				return true;
			if(e instanceof EntityMinecart)
				return true;
			if(e instanceof EntityBomber)
				return true;
		}
		
		if(targetPlayers && e instanceof EntityPlayer) {
			
			if(e instanceof FakePlayer)
				return false;
			
			List<String> wl = getWhitelist();
			
			if(wl == null || wl.isEmpty())
				return true;
			
			return !wl.contains(((EntityPlayer)e).getDisplayName());
		}
		
		return false;
	}
	
	/**
	 * How many degrees the turret can deviate from the target to be acceptable to fire at
	 * @return
	 */
	public double getAcceptableInaccuracy() {
		return 5;
	}
	
	/**
	 * How many degrees the turret can rotate per tick (4.5°/t = 90°/s or a half turn in two seconds)
	 * @return
	 */
	public double getTurretYawSpeed() {
		return 4.5D;
	}
	
	/**
	 * How many degrees the turret can lift per tick (3°/t = 60°/s or roughly the lowest to the highest point of an average turret in one second)
	 * @return
	 */
	public double getTurretPitchSpeed() {
		return 3D;
	}

	/**
	 * Makes turrets sad :'(
	 * @return
	 */
	public double getTurretDepression() {
		return 30D;
	}

	/**
	 * Makes turrets feel privileged
	 * @return
	 */
	public double getTurretElevation() {
		return 30D;
	}
	
	/**
	 * How many ticks until a target rescan is required
	 * @return
	 */
	public int getDecetorInterval() {
		return 20;
	}
	
	/**
	 * How far away an entity can be to be picked up
	 * @return
	 */
	public double getDecetorRange() {
		return 32D;
	}
	
	/**
	 * How far away an entity needs to be to be picked up
	 * @return
	 */
	public double getDecetorGrace() {
		return 3D;
	}
	
	/**
	 * The pivot point of the turret, larger models have a default of 1.5
	 * @return
	 */
	public double getHeightOffset() {
		return 1.5D;
	}
	
	/**
	 * Horizontal offset for the spawn point of bullets
	 * @return
	 */
	public double getBarrelLength() {
		return 1.0D;
	}
	
	/**
	 * The pivot point of the turret, this position is used for LOS calculation and more
	 * @return
	 */
	public Vec3 getTurretPos() {
		//TODO: account for multiblock rotation
		return Vec3.createVectorHelper(xCoord + 1, yCoord + getHeightOffset(), zCoord + 1);
	}
	
	/**
	 * The pivot point of the turret, this position is used for LOS calculation and more
	 * @return
	 */
	public Vec3 getEntityPos(Entity e) {
		return Vec3.createVectorHelper(e.posX, e.posY + e.height * 0.5 - e.getYOffset(), e.posZ);
	}
	
	/**
	 * Yes, new turrets fire BulletNTs.
	 * @return
	 */
	protected abstract List<BulletConfiguration> getAmmoList();
	
	/*
	 * the void
	 * 
	 * 
	 * more stuff pending: the thing that makes the pew pew
	 * probably a separate method that consumes or checks ammo
	 * mayhaps some dangly bits that tie together the GUI with the AI chip's whitelist
	 */
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
