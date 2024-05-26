package com.hbm.entity.mob;

import com.hbm.entity.projectile.EntityBullet;
import com.hbm.lib.ModDamageSource;

import api.hbm.entity.IRadiationImmune;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityCyberCrab extends EntityMob implements IRangedAttackMob, IRadiationImmune {

	private static final IEntitySelector selector = new IEntitySelector() {
		public boolean isEntityApplicable(Entity p_82704_1_) {
			return !(p_82704_1_ instanceof EntityCyberCrab || p_82704_1_ instanceof EntityCreeper);
		}
	};

	public EntityCyberCrab(World p_i1733_1_) {
		super(p_i1733_1_);
		this.setSize(0.75F, 0.35F);
		this.getNavigator().setAvoidsWater(true);

		if(!(this instanceof EntityTaintCrab))
			this.tasks.addTask(0, new EntityAIPanic(this, 0.75D));

		this.tasks.addTask(1, new EntityAIWander(this, 0.5F));
		this.tasks.addTask(4, arrowAI());
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, true, true, selector));
	}

	protected EntityAIArrowAttack arrowAI() {
		return new EntityAIArrowAttack(this, 0.5D, 60, 80, 15.0F);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.75F);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {

		if(ModDamageSource.getIsTau(source))
			return false;

		return super.attackEntityFrom(source, amount);
	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	@Override
	public boolean isAIEnabled() {
		return true;
	}

	/**
	 * The number of iterations PathFinder.getSafePoint will execute before
	 * giving up.
	 */
	@Override
	public int getMaxSafePointTries() {
		return this.getAttackTarget() == null ? 3 : 3 + (int) (this.getHealth() - 1.0F);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		super.writeEntityToNBT(p_70014_1_);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		super.readEntityFromNBT(p_70037_1_);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		if(this.isInWater() || this.isWet() || this.isBurning())
			this.attackEntityFrom(DamageSource.generic, 10F);

		if(this.getHealth() <= 0) {
			this.setDead();

			if(this instanceof EntityTaintCrab)
				worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3F, false);
			else
				worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 0.1F, false);
		}
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "hbm:entity.cybercrab";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "hbm:entity.cybercrab";
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	@Override
	public void onDeath(DamageSource p_70645_1_) {
		super.onDeath(p_70645_1_);
	}

	@Override
	public boolean attackEntityAsMob(Entity p_70652_1_) {
		return true;
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase entity, float f) {
		EntityBullet bullet = new EntityBullet(worldObj, this, entity, 1.6F, 2);
		bullet.setIsCritical(true);
		bullet.setTau(true);
		bullet.damage = 3;
		this.worldObj.spawnEntityInWorld(bullet);
		this.playSound("hbm:weapon.sawShoot", 1.0F, 2.0F);
	}
}
