package com.hbm.entity.mob;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import api.hbm.entity.IRadiationImmune;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityUFO extends EntityFlying implements IMob, IBossDisplayData, IRadiationImmune {

	public int courseChangeCooldown;
	public int scanCooldown;
	/*public double waypointX;
	public double waypointY;
	public double waypointZ;*/
	public int hurtCooldown;
	public int beamTimer;
	private Entity target;
	private List<Entity> secondaries = new ArrayList();
	
	public EntityUFO(World p_i1587_1_) {
		super(p_i1587_1_);
		this.setSize(15F, 4F);
		this.isImmuneToFire = true;
		this.experienceValue = 500;
		this.ignoreFrustumCheck = true;
		this.deathTime = -30;
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		
		if(hurtCooldown > 0)
			return false;
		
		boolean hit = super.attackEntityFrom(source, amount);
		
		if(hit)
			hurtCooldown = 5;
		
		return hit;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20000.0D);
	}

	@Override
	protected void updateEntityActionState() {
		
		if(!this.worldObj.isRemote) {
			
			if(this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) {
				this.setDead();
				return;
			}
			
			if(this.hurtCooldown > 0) {
				this.hurtCooldown--;
			}
		}

		if(this.courseChangeCooldown > 0) {
			this.courseChangeCooldown--;
		}
		if(this.scanCooldown > 0) {
			this.scanCooldown--;
		}
		
		if(this.target != null && !this.target.isEntityAlive()) {
			this.target = null;
		}
		
		if(this.scanCooldown <= 0) {
			List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class, this.boundingBox.expand(100, 50, 100));
			this.secondaries.clear();
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
				
				if(entity instanceof EntityLivingBase && this.getDistanceSqToEntity(entity) < 100 * 100 && this.canEntityBeSeen(entity) && entity != this.target) {
					this.secondaries.add(entity);
				}
			}
			
			if(this.target == null && !this.secondaries.isEmpty())
				this.target = this.secondaries.get(rand.nextInt(this.secondaries.size()));
			
			this.scanCooldown = 50;
		}
		
		if(this.target != null && this.courseChangeCooldown <= 0) {
			
			Vec3 vec = Vec3.createVectorHelper(this.posX - this.target.posX, 0, this.posZ - this.target.posZ);
			
			if(rand.nextInt(3) > 0)
				vec.rotateAroundY((float)Math.PI * 2 * rand.nextFloat());
			
			double length = vec.lengthVector();
			double overshoot = 35;
			
			int wX = (int)Math.floor(this.target.posX - vec.xCoord / length * overshoot);
			int wZ = (int)Math.floor(this.target.posZ - vec.zCoord / length * overshoot);
			
			this.setWaypoint(wX, Math.max(this.worldObj.getHeightValue(wX, wZ) + 20 + rand.nextInt(15), (int) this.target.posY + 15),  wZ);
			
			this.courseChangeCooldown = 40 + rand.nextInt(20);
		}
		
		if(!worldObj.isRemote) {
			
			if(beamTimer <= 0 && this.getBeam()) {
				this.setBeam(false);
			}

			if(this.target != null) {
				double dist = Math.abs(this.target.posX - this.posX) + Math.abs(this.target.posZ - this.posZ);
				if(dist < 25)
					this.beamTimer = 30;
			}
			
			if(beamTimer > 0) {
				this.beamTimer--;
				
				if(!this.getBeam()) {
					worldObj.playSoundAtEntity(this, "hbm:entity.ufoBeam", 10.0F, 1.0F);
					this.setBeam(true);
				}

				int ix = (int)Math.floor(this.posX);
				int iz = (int)Math.floor(this.posZ);
				int iy = 0;
				
				for(int i = (int)Math.ceil(this.posY); i >= 0; i--) {
					
					if(this.worldObj.getBlock(ix, i, iz) != Blocks.air) {
						iy = i;
						break;
					}
				}
				
				if(iy < this.posY) {
					List<Entity> entities = worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(this.posX, iy, this.posZ, this.posX, this.posY, this.posZ).expand(5, 0, 5));
					
					for(Entity e : entities) {
						if(this.canAttackClass(e.getClass())) {
							e.attackEntityFrom(ModDamageSource.causeCombineDamage(this, e), 1000F);
							e.setFire(5);
							
							if(e instanceof EntityLivingBase)
								ContaminationUtil.contaminate((EntityLivingBase)e, HazardType.RADIATION, ContaminationType.CREATIVE, 5F);
						}
					}
					
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "ufo");
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, posX, iy + 0.5, posZ),  new TargetPoint(dimension, posX, iy + 0.5, posZ, 150));
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, posX + this.motionX * 0.5, iy + 0.5, posZ + this.motionZ * 0.5),  new TargetPoint(dimension, posX + this.motionX * 0.5, iy + 0.5, posZ + this.motionZ * 0.5, 150));
				}
			}
			
			if(this.ticksExisted % 300 < 200) {
				
				if(this.ticksExisted % 4 == 0) {
					
					if(!this.secondaries.isEmpty()){
						Entity e = this.secondaries.get(rand.nextInt(this.secondaries.size()));
						
						if(!e.isEntityAlive())
							this.secondaries.remove(e);
						else
							laserAttack(e);
						
					} else if(this.target != null) {
						laserAttack(this.target);
					}
					
				} else if(this.ticksExisted % 4 == 2) {
					if(this.target != null) {
						laserAttack(this.target);
					}
				}
			} else {

				if(this.ticksExisted % 20 == 0) {
					
					if(!this.secondaries.isEmpty()){
						Entity e = this.secondaries.get(rand.nextInt(this.secondaries.size()));
						
						if(!e.isEntityAlive())
							this.secondaries.remove(e);
						else
							rocketAttack(e);
						
					} else if(this.target != null) {
						rocketAttack(this.target);
					}
					
				} else if(this.ticksExisted % 20 == 10) {
					if(this.target != null) {
						rocketAttack(this.target);
					}
				}
			}
			
		}
		
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		
		if(this.courseChangeCooldown > 0) {
			
			double deltaX = this.getX() - this.posX;
			double deltaY = this.getY() - this.posY;
			double deltaZ = this.getZ() - this.posZ;
			Vec3 delta = Vec3.createVectorHelper(deltaX, deltaY, deltaZ);
			double len = delta.lengthVector();
			double speed = this.target instanceof EntityPlayer ? 5D : 2D;
			
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
	
	protected void onDeathUpdate() {
		
		if(this.getBeam())
			this.setBeam(false);
		
		this.motionY -= 0.05D;
		
		if(this.deathTime == -10) {
			worldObj.playSoundAtEntity(this, "hbm:entity.chopperDamage", 10.0F, 1.0F);
		}
		
		if(this.deathTime == 19 && !worldObj.isRemote) {
			worldObj.newExplosion(this, posX, posY, posZ, 10F, true, true);
			ExplosionNukeSmall.explode(worldObj, posX, posY, posZ, ExplosionNukeSmall.PARAMS_MEDIUM);
			
			List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(200, 200, 200));

			for(EntityPlayer player : players) {
				player.triggerAchievement(MainRegistry.bossUFO);
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.coin_ufo));
			}
		}
		
		super.onDeathUpdate();
	}
	
	private void laserAttack(Entity e) {
		
		Vec3 vec = Vec3.createVectorHelper(this.posX - e.posX, 0, this.posZ - e.posZ);
		vec.rotateAroundY((float) Math.toRadians(-80 + rand.nextInt(160)));
		vec = vec.normalize();

		double pivotX = this.posX - vec.xCoord * 10;
		double pivotY = this.posY + 0.5;
		double pivotZ = this.posZ - vec.zCoord * 10;

		Vec3 heading = Vec3.createVectorHelper(e.posX - pivotX, e.posY + e.height / 2 - pivotY, e.posZ - pivotZ);
		heading = heading.normalize();

		EntityBulletBaseNT bullet = new EntityBulletBaseNT(this.worldObj, BulletConfigSyncingUtil.WORM_LASER);
		bullet.setThrower(this);
		bullet.setPosition(pivotX, pivotY, pivotZ);
		bullet.setThrowableHeading(heading.xCoord, heading.yCoord, heading.zCoord, 2F, 0.02F);
		this.worldObj.spawnEntityInWorld(bullet);
		this.playSound("hbm:weapon.ballsLaser", 5.0F, 1.0F);
	}
	
	private void rocketAttack(Entity e) {

		Vec3 heading = Vec3.createVectorHelper(e.posX - this.posX, e.posY + e.height / 2 - posY - 0.5D, e.posZ - this.posZ);
		heading = heading.normalize();

		EntityBulletBaseNT bullet = new EntityBulletBaseNT(this.worldObj, BulletConfigSyncingUtil.UFO_ROCKET);
		bullet.setThrower(this);
		bullet.setPosition(this.posX, this.posY - 0.5D, this.posZ);
		bullet.setThrowableHeading(heading.xCoord, heading.yCoord, heading.zCoord, 2F, 0.02F);
		bullet.getEntityData().setInteger("homingTarget", e.getEntityId());
		this.worldObj.spawnEntityInWorld(bullet);
		this.playSound("hbm:turret.richard_fire", 5.0F, 1.0F);
	}
	
	@Override
	public boolean canAttackClass(Class clazz) {
		return clazz != this.getClass() && clazz != EntityBulletBaseNT.class;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));
		this.dataWatcher.addObject(17, 0);
		this.dataWatcher.addObject(18, 0);
		this.dataWatcher.addObject(19, 0);
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
	
	@Override
	protected float getSoundVolume() {
		return 10.0F;
	}

	@Override
	protected String getHurtSound() {
		return "mob.blaze.hit";
	}

	@Override
	protected String getDeathSound() {
		return null;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		super.writeEntityToNBT(p_70014_1_);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		super.readEntityFromNBT(p_70037_1_);
	}

	public void setBeam(boolean b) {
		this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b ? 1 : 0)));
	}

	public boolean getBeam() {
		return this.dataWatcher.getWatchableObjectByte(16) == 1;
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
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 500000;
	}
}
