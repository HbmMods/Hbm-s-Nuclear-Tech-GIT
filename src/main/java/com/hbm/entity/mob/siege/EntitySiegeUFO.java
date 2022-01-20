package com.hbm.entity.mob.siege;

import java.util.List;

import com.hbm.entity.projectile.EntitySiegeLaser;
import com.hbm.handler.SiegeOrchestrator;

import api.hbm.entity.IRadiationImmune;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntitySiegeUFO extends EntityFlying implements IMob, IRadiationImmune {

	public int courseChangeCooldown;
	public int scanCooldown;
	private int attackCooldown;
	private Entity target;
	
	public EntitySiegeUFO(World p_i1587_1_) {
		super(p_i1587_1_);
		this.setSize(1.5F, 1F);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.getDataWatcher().addObject(12, (int) 0);
		//XYZ
		this.getDataWatcher().addObject(17, 0);
		this.getDataWatcher().addObject(18, 0);
		this.getDataWatcher().addObject(19, 0);
	}
	
	public void setTier(SiegeTier tier) {
		this.getDataWatcher().updateObject(12, tier.id);

		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(tier.speedMod);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(tier.health * 0.25);
		this.setHealth(this.getMaxHealth());
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float damage) {
		
		if(this.isEntityInvulnerable())
			return false;
		
		if(SiegeOrchestrator.isSiegeMob(source.getEntity()))
			return false;
		
		SiegeTier tier = this.getTier();
		
		if(tier.fireProof && source.isFireDamage()) {
			this.extinguish();
			return false;
		}
		
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
	
	public SiegeTier getTier() {
		SiegeTier tier = SiegeTier.tiers[this.getDataWatcher().getWatchableObjectInt(12)];
		return tier != null ? tier : SiegeTier.CLAY;
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

		if(this.courseChangeCooldown > 0) {
			this.courseChangeCooldown--;
		}
		if(this.scanCooldown > 0) {
			this.scanCooldown--;
		}
		
		if(this.target != null && !this.target.isEntityAlive()) {
			this.target = null;
		}
		
		if(!worldObj.isRemote) {
			if(this.attackCooldown > 0) {
				this.attackCooldown--;
			}
			
			if(this.attackCooldown == 0 && this.target != null) {
				this.attackCooldown = 20 + rand.nextInt(5);
				
				double x = posX;
				double y = posY;
				double z = posZ;
				
				Vec3 vec = Vec3.createVectorHelper(target.posX - x, target.posY + target.height * 0.5 - y, target.posZ - z).normalize();
				SiegeTier tier = this.getTier();
				
				EntitySiegeLaser laser = new EntitySiegeLaser(worldObj, this);
				laser.setPosition(x, y, z);
				laser.setThrowableHeading(vec.xCoord, vec.yCoord, vec.zCoord, 1F, 0.15F);
				laser.setColor(0x802000);
				laser.setDamage(tier.damageMod);
				laser.setExplosive(tier.laserExplosive);
				laser.setBreakChance(tier.laserBreak);
				if(tier.laserIncendiary) laser.setIncendiary();
				worldObj.spawnEntityInWorld(laser);
				this.playSound("hbm:weapon.ballsLaser", 2.0F, 1.0F);
			}
		}
		
		if(this.scanCooldown <= 0) {
			List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class, this.boundingBox.expand(50, 20, 50));
			this.target = null;
			
			for(Entity entity : entities) {
				
				if(!entity.isEntityAlive() || !canAttackClass(entity.getClass()))
					continue;
				
				if(entity instanceof EntityPlayer) {
					
					if(((EntityPlayer)entity).capabilities.isCreativeMode)
					//	continue;
					
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
			
			this.scanCooldown = 100;
		}
		
		if(this.courseChangeCooldown <= 0) {
			
			if(this.target != null) {
				
				Vec3 vec = Vec3.createVectorHelper(this.posX - this.target.posX, 0, this.posZ - this.target.posZ);
				vec.rotateAroundY((float)Math.PI * 2 * rand.nextFloat());
				
				double length = vec.lengthVector();
				double overshoot = 10 + rand.nextDouble() * 10;
				
				int wX = (int)Math.floor(this.target.posX - vec.xCoord / length * overshoot);
				int wZ = (int)Math.floor(this.target.posZ - vec.zCoord / length * overshoot);
				
				this.setWaypoint(wX, Math.max(this.worldObj.getHeightValue(wX, wZ) + 2 + rand.nextInt(2), (int) this.target.posY + rand.nextInt(3)),  wZ);
				
				this.courseChangeCooldown = 20 + rand.nextInt(20);
			} else {
				int x = (int) Math.floor(posX + rand.nextGaussian() * 5);
				int z = (int) Math.floor(posZ + rand.nextGaussian() * 5);
				this.setWaypoint(x, this.worldObj.getHeightValue(x, z) + 2 + rand.nextInt(3),  z);
				this.courseChangeCooldown = 60 + rand.nextInt(20);
			}
		}
		
		if(this.courseChangeCooldown > 0) {
			
			double deltaX = this.getX() - this.posX;
			double deltaY = this.getY() - this.posY;
			double deltaZ = this.getZ() - this.posZ;
			Vec3 delta = Vec3.createVectorHelper(deltaX, deltaY, deltaZ);
			double len = delta.lengthVector();
			double speed = this.target == null ? 0.25D : 0.5D + this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue() * 1;
			
			if(len > 5) {
				if(isCourseTraversable(this.getX(), this.getY(), this.getZ(), len)) {
					this.motionX = delta.xCoord * speed / len;
					this.motionY = delta.yCoord * speed / len;
					this.motionZ = delta.zCoord * speed / len;
				} else {
					this.courseChangeCooldown = 0;
				}
			}
		}
	}
	
	private boolean isCourseTraversable(double p_70790_1_, double p_70790_3_, double p_70790_5_, double p_70790_7_) {
		
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
		return super.onSpawnWithEgg(data);
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
