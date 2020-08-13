package com.hbm.entity.mob.sodtekhnologiyah;

import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public abstract class EntityWormBase extends EntityBurrowing {

	public int aggroCooldown = 0;
	public int courseChangeCooldown = 0;
	public double waypointX;
	public double waypointY;
	public double waypointZ;
	protected Entity targetedEntity = null;
	protected boolean canFly = false;
	protected int dmgCooldown = 0;
	protected boolean wasNearGround;
	protected ChunkCoordinates spawnPoint = new ChunkCoordinates();
	protected double attackRange;
	protected double maxSpeed;
	protected double fallSpeed;
	protected double rangeForParts;
	protected EntityWormBase followed;
	protected int surfaceY;
	private int uniqueWormID;
	private int partID;
	protected boolean didCheck;
	protected double bodySpeed;
	protected double maxBodySpeed;
	protected double segmentDistance;
	protected double knockbackDivider;
	protected int attackTick;

	public static final IEntitySelector wormSelector = new IEntitySelector() {

		@Override
		public boolean isEntityApplicable(Entity target) {
			return target instanceof EntityWormBase;
		}
	};

	public EntityWormBase(World world) {
		super(world);
		this.setSize(1.0F, 1.0F);
		this.surfaceY = 60;
		this.renderDistanceWeight = 5.0D;
	}

	public int getPartID() {
		return this.partID;
	}

	public void setPartID(int par1) {
		this.partID = par1;
	}

	public int getUniqueWormID() {
		return this.uniqueWormID;
	}

	public void setUniqueWormID(int par1) {
		this.uniqueWormID = par1;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		
		if(this.isEntityInvulnerable() || source == DamageSource.drown || source == DamageSource.inWall ||
				((source.getEntity() instanceof EntityWormBase) && ((EntityWormBase) source.getEntity()).uniqueWormID == this.uniqueWormID)) {
			return false;
		} else {
			this.setBeenAttacked();
			return false;
		}
	}

	protected void updateEntityActionState() {
		
		if((!this.worldObj.isRemote) && (this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL)) {
			setDead();
		}
		if((this.targetedEntity != null) && (this.targetedEntity.isDead)) {
			this.targetedEntity = null;
		}
		if((getIsHead()) && (this.targetedEntity != null) && ((this.targetedEntity instanceof EntityPlayer))) {
			this.entityAge = 0;
		}
		if(this.posY < -10.0D) {
			setPositionAndUpdate(this.posX, 128.0D, this.posZ);
			this.motionY = 0.0D;
		} else if(this.posY < 3.0D) {
			this.motionY = 0.3D;
		}
		this.attackTick = Math.max(this.attackTick - 1, 0);
		if(this.attackTick == 0) {
			this.attackTick = 10;

			attackEntitiesInList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.5D, 0.5D, 0.5D)));
		}
	}

	protected void attackEntitiesInList(List<Entity> par1List) {
		
		for(Entity var3 : par1List) {
			if(((var3 instanceof EntityLivingBase)) && (canAttackClass(var3.getClass()))
					&& ((!(var3 instanceof EntityWormBase)) || (((EntityWormBase) var3).getUniqueWormID() != getUniqueWormID()))) {
				attackEntityAsMob(var3);
			}
		}
	}

	@Override
	public boolean canAttackClass(Class clazz) {
		return true;
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
		
		boolean var2 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), getAttackStrength(par1Entity));
		
		if(var2) {
			this.entityAge = 0;
			double var5 = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0D;
			double var6 = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0D;
			double var7 = (this.boundingBox.minY + this.boundingBox.maxY) / 2.0D;
			double var8 = par1Entity.posX - var5;
			double var10 = par1Entity.posZ - var6;
			double var11 = par1Entity.posY - var7;
			double var12 = this.knockbackDivider * (var8 * var8 + var10 * var10 + var11 * var11 + 0.1D);
			par1Entity.addVelocity(var8 / var12, var11 / var12, var10 / var12);
		}
		
		return var2;
	}

	public abstract float getAttackStrength(Entity paramsa);

	@Override
	public void addVelocity(double x, double y, double z) {
	}

	@Override
	public void faceEntity(Entity entity, float yaw, float pitch) {
	}

	protected boolean isCourseTraversable() {
		return (this.canFly) || (isEntityInsideOpaqueBlock());
	}

	@Override
	protected float getSoundVolume() {
		return 5.0F;
	}

	@Override
	public void setDead() {
		playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
		super.setDead();
	}

	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("wormID", this.getUniqueWormID());
	}

	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setUniqueWormID(nbt.getInteger("wormID"));
	}
}
