package com.hbm.entity.mob;

import java.util.List;

import com.hbm.entity.mob.ai.EntityAINearestAttackableTargetNT;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

/**
 * The name comes from the "swinging" movement patterns as part of its custom path-finding. Also handles rotation.
 * The end result kind of looks like dolphins jumping out of the water just to dive back in.
 * This class assumes that all implementations are indeed hostile mobs.
 * @author hbm
 *
 */
public abstract class EntityBurrowingSwingingBase extends EntityBurrowingBase {

	protected Entity targetedEntity = null;
	protected boolean wasNearGround = false;
	public int courseChangeCooldown = 0;
	public int aggroCooldown = 0;
	public double waypointX;
	public double waypointY;
	public double waypointZ;
	protected ChunkCoordinates spawnPoint = new ChunkCoordinates();
	public boolean lastInGround = false;

	public EntityBurrowingSwingingBase(World world) {
		super(world);
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTargetNT(this, EntityPlayer.class, 0, false, false, null, 128.0D));
	}
	
	@Override
	public void onUpdate() {
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;
		
		super.onUpdate();

		double dx = motionX;
		double dy = motionY;
		double dz = motionZ;
		float f3 = MathHelper.sqrt_double(dx * dx + dz * dz);
		this.rotationYaw = (float) (Math.atan2(dx, dz) * 180.0D / Math.PI);
		this.rotationPitch = (float) (Math.atan2(dy, f3) * 180.0D / Math.PI);
		
		boolean inGround = isEntityInsideOpaqueBlock();
		
		if(this.lastInGround != inGround) {
			worldObj.playSoundAtEntity(this, "hbm:block.debris", 1.0F, 1.0F);
			
			double mod = 0.25D;
			
			if(inGround)
				mod *= -1;
			
			for(int i = 0; i < 10; i++) {
				double dev = 0.05D;
				worldObj.spawnParticle("cloud", posX, posY, posZ, motionX * mod + rand.nextGaussian() * dev, motionY * mod + rand.nextGaussian() * dev, motionZ * mod + rand.nextGaussian() * dev);
			}
		}
		
		this.lastInGround = inGround;
	}

	@Override
	protected void updateAITasks() {

		this.updateEntityActionState();
		super.updateAITasks();

		updateSwingingMovement();
	}

	@Override
	protected void updateEntityActionState() {
		
		if(!this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) {
			setDead();
		}
		if((this.targetedEntity != null) && (this.targetedEntity.isDead)) {
			this.targetedEntity = null;
		}
		if(this.posY < -10.0D) {
			this.motionY = 1D;
		} else if(this.posY < 3.0D) {
			this.motionY = 0.3D;
		}
		
		if(this.ticksExisted % 2 == 0) {
			attackEntitiesInList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.5D, 0.5D, 0.5D)));
		}
	}

	protected void attackEntitiesInList(List<Entity> targets) {
		
		for(Entity target : targets) {
			if(target instanceof EntityLivingBase && canAttackClass(target.getClass())) {
				attackEntityAsMob(target);
			}
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity target) {
		
		boolean hit = target.attackEntityFrom(DamageSource.causeMobDamage(this), (float) getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
		
		if(hit) {
			this.entityAge = 0;
			double tx = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0D;
			double tz = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0D;
			double ty = (this.boundingBox.minY + this.boundingBox.maxY) / 2.0D;
			double deltaX = target.posX - tx;
			double deltaZ = target.posZ - tz;
			double deltaY = target.posY - ty;
			double knockback = 0.5 * (deltaX * deltaX + deltaZ * deltaZ + deltaY * deltaY + 0.1D);
			target.addVelocity(deltaX / knockback, deltaY / knockback, deltaZ / knockback);
		}
		
		return hit;
	}
	
	protected void updateSwingingMovement() {

		double deltaX = this.waypointX - this.posX;
		double deltaY = this.waypointY - this.posY;
		double deltaZ = this.waypointZ - this.posZ;
		double delta = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
		
		if(this.courseChangeCooldown-- <= 0) {
			
			this.courseChangeCooldown += this.getRNG().nextInt(5) + 5;
			
			double speed = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue();
			
			if(!this.canSupportMovement()) {
				speed /= 4D;
			}
			
			this.motionX += deltaX / delta * speed;
			this.motionY += deltaY / delta * speed;
			this.motionZ += deltaZ / delta * speed;
		}
		
		if(!this.canSupportMovement() && !this.wasNearGround) {
			this.motionY -= this.getGravity();
		}
		
		this.aggroCooldown--;
		
		if(this.getAttackTarget() != null) {
			
			if(this.aggroCooldown <= 0) {
				this.targetedEntity = this.getAttackTarget();
				this.aggroCooldown = getAggroCooldown();
			}
			
		} else if(this.targetedEntity == null) {
			this.waypointX = this.spawnPoint.posX - 50 + this.getRNG().nextInt(100);
			this.waypointY = this.spawnPoint.posY - 30 + this.getRNG().nextInt(60);
			this.waypointZ = this.spawnPoint.posZ - 50 + this.getRNG().nextInt(100);
		}
		
		this.rotationYaw = -(float) -(Math.atan2(this.motionX, this.motionZ) * 180.0F / Math.PI);
		this.rotationPitch = (float) -(Math.atan2(this.motionY, MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ)) * 180.0D / Math.PI);
		
		double range = 100;
		if(this.targetedEntity != null && this.targetedEntity.getDistanceSqToEntity(this) < range * range) {
			
			if(this.canSupportMovement() || this.wasNearGround) {
				
				this.waypointX = this.targetedEntity.posX;
				this.waypointY = this.targetedEntity.posY + this.targetedEntity.height * 0.5;
				this.waypointZ = this.targetedEntity.posZ;
				
				int surface = worldObj.getHeightValue(MathHelper.floor_double(this.waypointX), MathHelper.floor_double(this.waypointZ));
				
				if(this.getRNG().nextInt(80) == 0 && this.posY > surface && !this.canSupportMovement()) {
					this.wasNearGround = false;
				}
				
			} else {
				
				this.waypointX = this.targetedEntity.posX;
				this.waypointY = 10.0D;
				this.waypointZ = this.targetedEntity.posZ;
				
				if(this.posY < 15.0D) {
					this.wasNearGround = true;
				}
			}
		} else {
			this.waypointX = this.spawnPoint.posX - 20 + this.getRNG().nextInt(40);
			this.waypointY = this.spawnPoint.posY - 5 + this.getRNG().nextInt(100);
			this.waypointZ = this.spawnPoint.posZ - 20 + this.getRNG().nextInt(40);
		}
	}

	protected double getGravity() {
		return 0.01;
	}

	protected int getAggroCooldown() {
		return 20;
	}

	@Override
	public void addVelocity(double x, double y, double z) { }

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("spawnX", this.spawnPoint.posX);
		nbt.setInteger("spawnY", this.spawnPoint.posY);
		nbt.setInteger("spawnZ", this.spawnPoint.posZ);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.spawnPoint.set(nbt.getInteger("spawnX"), nbt.getInteger("spawnY"), nbt.getInteger("spawnZ"));
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {

		int x = MathHelper.floor_double(this.posX);
		int y = MathHelper.floor_double(this.posY);
		int z = MathHelper.floor_double(this.posZ);
		
		this.spawnPoint.set(x, y, z);
		
		return super.onSpawnWithEgg(data);
	}
}
