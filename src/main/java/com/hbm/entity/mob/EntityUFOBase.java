package com.hbm.entity.mob;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public abstract class EntityUFOBase extends EntityFlying implements IMob {

	protected int scanCooldown;
	protected int courseChangeCooldown;
	protected Entity target;

	public EntityUFOBase(World world) {
		super(world);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		//XYZ
		this.getDataWatcher().addObject(17, 0);
		this.getDataWatcher().addObject(18, 0);
		this.getDataWatcher().addObject(19, 0);
	}

	@Override
	protected void updateEntityActionState() {
		
		if(!this.worldObj.isRemote) {
			
			if(this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) {
				this.setDead();
				return;
			}
		}
		
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		
		if(this.target != null && !this.target.isEntityAlive()) {
			this.target = null;
		}
		
		scanForTarget();
		
		if(this.courseChangeCooldown <= 0) {
			this.setCourse();
		}
		
		/*
		 * Make sure to invoke super.updateEntityActionState(); in the beginning of the child's override
		 * Motion is set to 0 and the targeting should optimally be handled before anything else
		 */
	}
	
	/**
	 * Standard implementation for choosing single player targets
	 * Keeps the check delay in mind and resets it too, simply call this every update
	 */
	protected void scanForTarget() {
		
		int range = getScanRange();
		
		if(this.scanCooldown <= 0) {
			List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class, this.boundingBox.expand(range, range / 2, range));
			this.target = null;
			
			for(Entity entity : entities) {
				
				if(!entity.isEntityAlive() || !canAttackClass(entity.getClass()))
					continue;
				
				if(entity instanceof EntityPlayer) {
					
					if(((EntityPlayer)entity).capabilities.isCreativeMode)
						continue;
					
					if(((EntityPlayer)entity).isPotionActive(Potion.invisibility.id))
						continue;
					
					if(this.target == null) {
						this.target = entity;
					} else {
						if(this.getDistanceSqToEntity(entity) < this.getDistanceSqToEntity(this.target)) {
							this.target = entity;
						}
					}
				}
			}
			
			this.scanCooldown = getScanDelay();
		}
	}
	
	protected int getScanRange() {
		return 50;
	}
	
	protected int getScanDelay() {
		return 100;
	}
	
	protected boolean isCourseTraversable(double p_70790_1_, double p_70790_3_, double p_70790_5_, double p_70790_7_) {
		
		double d4 = (this.getX() - this.posX) / p_70790_7_;
		double d5 = (this.getY() - this.posY) / p_70790_7_;
		double d6 = (this.getZ() - this.posZ) / p_70790_7_;
		AxisAlignedBB axisalignedbb = this.boundingBox.copy();

		for(int i = 1; i < p_70790_7_; ++i) {
			axisalignedbb.offset(d4, d5, d6);

			if(!this.worldObj.getCollidingBoundingBoxes(this, axisalignedbb).isEmpty()) {
				return false;
			}
		}

		return true;
	}
	
	protected void approachPosition(double speed) {
		
		double deltaX = this.getX() - this.posX;
		double deltaY = this.getY() - this.posY;
		double deltaZ = this.getZ() - this.posZ;
		Vec3 delta = Vec3.createVectorHelper(deltaX, deltaY, deltaZ);
		double len = delta.lengthVector();
		
		if(len > 5) {
			if(this.isCourseTraversable(this.getX(), this.getY(), this.getZ(), len)) {
				this.motionX = delta.xCoord * speed / len;
				this.motionY = delta.yCoord * speed / len;
				this.motionZ = delta.zCoord * speed / len;
			} else {
				this.courseChangeCooldown = 0;
			}
		}
	}
	
	protected void setCourse() {
		
		if(this.target != null) {
			this.setCourseForTaget();
			this.courseChangeCooldown = 20 + rand.nextInt(20);
		} else {
			this.setCourseWithoutTaget();
			this.courseChangeCooldown = 60 + rand.nextInt(20);
		}
	}
	
	protected void setCourseForTaget() {
		Vec3 vec = Vec3.createVectorHelper(this.posX - this.target.posX, 0, this.posZ - this.target.posZ);
		vec.rotateAroundY((float)Math.PI * 2 * rand.nextFloat());
		
		double length = vec.lengthVector();
		double overshoot = 10 + rand.nextDouble() * 10;
		
		int wX = (int)Math.floor(this.target.posX - vec.xCoord / length * overshoot);
		int wZ = (int)Math.floor(this.target.posZ - vec.zCoord / length * overshoot);
		
		this.setWaypoint(wX, Math.max(this.worldObj.getHeightValue(wX, wZ), (int) this.target.posY) + targetHeightOffset(),  wZ);
	}
	
	protected int targetHeightOffset() {
		return 2 + rand.nextInt(2);
	}
	
	protected int wanderHeightOffset() {
		return 2 + rand.nextInt(3);
	}
	
	protected void setCourseWithoutTaget() {
		int x = (int) Math.floor(posX + rand.nextGaussian() * 5);
		int z = (int) Math.floor(posZ + rand.nextGaussian() * 5);
		this.setWaypoint(x, this.worldObj.getHeightValue(x, z) + wanderHeightOffset(),  z);
	}

	public void setWaypoint(int x, int y, int z) {
		this.dataWatcher.updateObject(17, x);
		this.dataWatcher.updateObject(18, y);
		this.dataWatcher.updateObject(19, z);
	}

	public int getX() {
		return this.dataWatcher.getWatchableObjectInt(17);
	}

	public int getY() {
		return this.dataWatcher.getWatchableObjectInt(18);
	}

	public int getZ() {
		return this.dataWatcher.getWatchableObjectInt(19);
	}
}
