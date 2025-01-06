package com.hbm.entity.mob.siege;

import com.hbm.entity.projectile.EntitySiegeLaser;
import com.hbm.items.ModItems;

import api.hbm.entity.IRadiationImmune;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntitySiegeSkeleton extends EntityMob implements IRangedAttackMob, IRadiationImmune {

	public EntitySiegeSkeleton(World world) {
		super(world);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F));
		this.tasks.addTask(3, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(5, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float damage) {
		
		if(this.isEntityInvulnerable())
			return false;
		
		SiegeTier tier = this.getTier();
		
		if(tier.fireProof && source.isFireDamage()) {
			this.extinguish();
			return false;
		}
		
		if(tier.noFall && source == DamageSource.fall)
			return false;
		
		//noFF can't be harmed by other mobs
		if(tier.noFriendlyFire && source instanceof EntityDamageSource && !(((EntityDamageSource) source).getEntity() instanceof EntityPlayer))
			return false;
		
		damage -= tier.dt;
		
		if(damage < 0) {
			worldObj.playSoundAtEntity(this, "random.break", 5F, 1.0F + rand.nextFloat() * 0.5F);
			return false;
		}
		
		damage *= (1F - tier.dr);
		
		return super.attackEntityFrom(source, damage);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.getDataWatcher().addObject(12, (int) 0);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
	}
	
	public void setTier(SiegeTier tier) {
		this.getDataWatcher().updateObject(12, tier.id);

		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).applyModifier(new AttributeModifier("Tier Speed Mod", tier.speedMod, 1));
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).applyModifier(new AttributeModifier("Tier Damage Mod", tier.damageMod, 1));
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(tier.health);
		this.setHealth(this.getMaxHealth());
	}
	
	public SiegeTier getTier() {
		SiegeTier tier = SiegeTier.tiers[this.getDataWatcher().getWatchableObjectInt(12)];
		return tier != null ? tier : SiegeTier.CLAY;
	}

	@Override
	protected void addRandomArmor() {
		super.addRandomArmor();
		this.setCurrentItemOrArmor(0, new ItemStack(ModItems.detonator_laser));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("siegeTier", this.getTier().id);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.setTier(SiegeTier.tiers[nbt.getInteger("siegeTier")]);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		this.setTier(SiegeTier.tiers[rand.nextInt(SiegeTier.getLength())]);
		this.addRandomArmor();
		return super.onSpawnWithEgg(data);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
		
		double x = posX;
		double y = posY + this.getEyeHeight();
		double z = posZ;
		
		Vec3 vec = Vec3.createVectorHelper(target.posX - x, target.posY + target.getYOffset() + target.height * 0.5 - y, target.posZ - z).normalize();
		
		SiegeTier tier = this.getTier();
		
		for(int i = 0; i < 3; i++) {
			EntitySiegeLaser laser = new EntitySiegeLaser(worldObj, this);
			laser.setPosition(x, y, z);
			laser.setThrowableHeading(vec.xCoord, vec.yCoord, vec.zCoord, 1F, i == 1 ? 0.15F : 5F);
			laser.setColor(0x808000);
			laser.setDamage(tier.damageMod);
			laser.setExplosive(tier.laserExplosive);
			laser.setBreakChance(tier.laserBreak);
			if(tier.laserIncendiary) laser.setIncendiary();
			worldObj.spawnEntityInWorld(laser);
		}
		
		this.playSound("hbm:weapon.ballsLaser", 2.0F, 0.9F + rand.nextFloat() * 0.2F);
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	protected String getLivingSound() {
		return "hbm:entity.siegeIdle";
	}

	@Override
	protected String getHurtSound() {
		return "hbm:entity.siegeHurt";
	}

	@Override
	protected String getDeathSound() {
		return "hbm:entity.siegeDeath";
	}

	@Override
	protected void dropFewItems(boolean byPlayer, int fortune) {
		
		if(byPlayer) {
			for(ItemStack drop : this.getTier().dropItem) {
				this.entityDropItem(drop.copy(), 0F);
			}
		}
	}
}
