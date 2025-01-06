package com.hbm.entity.projectile;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.hbm.blocks.bomb.BlockDetonatable;
import com.hbm.entity.grenade.EntityGrenadeTau;
import com.hbm.entity.mob.EntityCreeperNuclear;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.util.ArmorUtil;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityBullet extends Entity implements IProjectile {
	private int tileX = -1;
	private int tileY = -1;
	private int tileZ = -1;
	public double gravity = 0.0D;
	private Block field_145790_g;
	private int inData;
	private boolean inGround;
	/** 1 if the player can pick up the arrow */
	public int canBePickedUp;
	/** Seems to be some sort of timer for animating an arrow. */
	public int arrowShake;
	/** The owner of this arrow. */
	public Entity shootingEntity;
	private int ticksInGround;
	private int ticksInAir;
	public double damage;
	/** The amount of knockback an arrow applies when it hits a mob. */
	private int knockbackStrength;
	private boolean instakill = false;
	private boolean rad = false;
	public boolean antidote = false;
	public boolean pip = false;
	public boolean fire = false;

	public EntityBullet(World p_i1753_1_) {
		super(p_i1753_1_);
		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);
	}

	public EntityBullet(World p_i1754_1_, double p_i1754_2_, double p_i1754_4_, double p_i1754_6_) {
		super(p_i1754_1_);
		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);
		this.setPosition(p_i1754_2_, p_i1754_4_, p_i1754_6_);
		this.yOffset = 0.0F;
	}

	public EntityBullet(World p_i1755_1_, EntityLivingBase p_i1755_2_, EntityLivingBase p_i1755_3_, float p_i1755_4_,
			float p_i1755_5_) {
		super(p_i1755_1_);
		this.renderDistanceWeight = 10.0D;
		this.shootingEntity = p_i1755_2_;

		if (p_i1755_2_ instanceof EntityPlayer) {
			this.canBePickedUp = 1;
		}

		this.posY = p_i1755_2_.posY + p_i1755_2_.getEyeHeight() - 0.10000000149011612D;
		double d0 = p_i1755_3_.posX - p_i1755_2_.posX;
		double d1 = p_i1755_3_.boundingBox.minY + p_i1755_3_.height / 3.0F - this.posY;
		double d2 = p_i1755_3_.posZ - p_i1755_2_.posZ;
		double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);

		if (d3 >= 1.0E-7D) {
			float f2 = (float) (Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
			float f3 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
			double d4 = d0 / d3;
			double d5 = d2 / d3;
			this.setLocationAndAngles(p_i1755_2_.posX + d4, this.posY, p_i1755_2_.posZ + d5, f2, f3);
			this.yOffset = 0.0F;
			float f4 = 0;//(float) d3 * 0.2F;
			this.setThrowableHeading(d0, d1 + f4, d2, p_i1755_4_, p_i1755_5_);
		}
	}

	public EntityBullet(World p_i1756_1_, EntityLivingBase p_i1756_2_, float p_i1756_3_, int dmgMin, int dmgMax,
			boolean instakill, boolean rad) {
		super(p_i1756_1_);
		this.renderDistanceWeight = 10.0D;
		this.shootingEntity = p_i1756_2_;

		if (p_i1756_2_ instanceof EntityPlayer) {
			this.canBePickedUp = 1;
		}

		this.setSize(0.5F, 0.5F);
		this.setLocationAndAngles(p_i1756_2_.posX, p_i1756_2_.posY + p_i1756_2_.getEyeHeight(), p_i1756_2_.posZ,
				p_i1756_2_.rotationYaw, p_i1756_2_.rotationPitch);
		this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		this.posY -= 0.10000000149011612D;
		this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, p_i1756_3_ * 1.5F, 1.0F);

		// this.dmgMin = dmgMin;
		// this.dmgMax = dmgMax;
		this.instakill = instakill;
		this.rad = rad;
	}

	public EntityBullet(World p_i1756_1_, EntityLivingBase p_i1756_2_, float p_i1756_3_) {
		super(p_i1756_1_);
		this.renderDistanceWeight = 10.0D;
		this.shootingEntity = p_i1756_2_;

		if (p_i1756_2_ instanceof EntityPlayer) {
			this.canBePickedUp = 1;
		}

		this.setSize(0.5F, 0.5F);
		this.setLocationAndAngles(p_i1756_2_.posX, p_i1756_2_.posY + p_i1756_2_.getEyeHeight(), p_i1756_2_.posZ,
				p_i1756_2_.rotationYaw, p_i1756_2_.rotationPitch);
		this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		this.posY -= 0.10000000149011612D;
		this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));
		this.setThrowableHeading2(this.motionX, this.motionY, this.motionZ, p_i1756_3_ * 1.5F, 1.0F);
	}

	public EntityBullet(World p_i1756_1_, EntityLivingBase p_i1756_2_, float p_i1756_3_, int dmgMin, int dmgMax,
			boolean instakill, String isTau) {
		super(p_i1756_1_);
		this.renderDistanceWeight = 10.0D;
		this.shootingEntity = p_i1756_2_;

		if (p_i1756_2_ instanceof EntityPlayer) {
			this.canBePickedUp = 1;
		}

		this.setSize(0.5F, 0.5F);
		if(p_i1756_2_ != null) this.setLocationAndAngles(p_i1756_2_.posX, p_i1756_2_.posY + p_i1756_2_.getEyeHeight(), p_i1756_2_.posZ, p_i1756_2_.rotationYaw, p_i1756_2_.rotationPitch);
		this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		this.posY -= 0.10000000149011612D;
		this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, p_i1756_3_ * 1.5F, 1.0F);
		this.setTau(isTau == "tauDay");
		this.setChopper(isTau == "chopper");
		this.setIsCritical(isTau != "chopper");
	}
	
	//why the living shit did i make isTau a string? who knows, who cares.
	public EntityBullet(World p_i1756_1_, EntityLivingBase p_i1756_2_, float p_i1756_3_, int dmgMin, int dmgMax,
			boolean instakill, String isTau, EntityGrenadeTau grenade) {
		super(p_i1756_1_);
		this.renderDistanceWeight = 10.0D;
		this.shootingEntity = p_i1756_2_;

		this.setSize(0.5F, 0.5F);
		this.setLocationAndAngles(grenade.posX, grenade.posY + grenade.getEyeHeight(), grenade.posZ,
				grenade.rotationYaw, grenade.rotationPitch);
		this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		this.posY -= 0.10000000149011612D;
		this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, p_i1756_3_ * 1.5F, 1.0F);
		this.setTau(isTau == "tauDay");
		this.setIsCritical(true);
	}

	public EntityBullet(World world, int x, int y, int z, double mx, double my, double mz, double grav) {
		super(world);
		this.posX = x + 0.5F;
		this.posY = y + 0.5F;
		this.posZ = z + 0.5F;

		this.motionX = mx;
		this.motionY = my;
		this.motionZ = mz;

		this.gravity = grav;
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));
		this.dataWatcher.addObject(17, Byte.valueOf((byte) 0));
		this.dataWatcher.addObject(18, Byte.valueOf((byte) 0));
	}

	/**
	 * Similar to setArrowHeading, it's point the throwable entity to a x, y, z
	 * direction.
	 */
	@Override
	public void setThrowableHeading(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_,
			float p_70186_8_) {
		float f2 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
		p_70186_1_ /= f2;
		p_70186_3_ /= f2;
		p_70186_5_ /= f2;
		p_70186_1_ += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D
				* p_70186_8_;
		p_70186_3_ += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D
				* p_70186_8_;
		p_70186_5_ += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D
				* p_70186_8_;
		p_70186_1_ *= p_70186_7_;
		p_70186_3_ *= p_70186_7_;
		p_70186_5_ *= p_70186_7_;
		this.motionX = p_70186_1_;
		this.motionY = p_70186_3_;
		this.motionZ = p_70186_5_;
		float f3 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_5_ * p_70186_5_);
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(p_70186_1_, p_70186_5_) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(p_70186_3_, f3) * 180.0D / Math.PI);
		this.ticksInGround = 0;
	}
	
	public void setThrowableHeading2(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_,
			float p_70186_8_) {
		float f2 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
		p_70186_1_ /= f2;
		p_70186_3_ /= f2;
		p_70186_5_ /= f2;
		p_70186_1_ += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.042499999832361937D
				* p_70186_8_;
		p_70186_3_ += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.042499999832361937D
				* p_70186_8_;
		p_70186_5_ += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.042499999832361937D
				* p_70186_8_;
		p_70186_1_ *= p_70186_7_;
		p_70186_3_ *= p_70186_7_;
		p_70186_5_ *= p_70186_7_;
		this.motionX = p_70186_1_;
		this.motionY = p_70186_3_;
		this.motionZ = p_70186_5_;
		float f3 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_5_ * p_70186_5_);
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(p_70186_1_, p_70186_5_) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(p_70186_3_, f3) * 180.0D / Math.PI);
		this.ticksInGround = 0;
	}

	/**
	 * Sets the position and rotation. Only difference from the other one is no
	 * bounding on the rotation. Args: posX, posY, posZ, yaw, pitch
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_,
			float p_70056_8_, int p_70056_9_) {
		this.setPosition(p_70056_1_, p_70056_3_, p_70056_5_);
		this.setRotation(p_70056_7_, p_70056_8_);
	}

	/**
	 * Sets the velocity to the args. Args: x, y, z
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
		this.motionX = p_70016_1_;
		this.motionY = p_70016_3_;
		this.motionZ = p_70016_5_;

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt_double(p_70016_1_ * p_70016_1_ + p_70016_5_ * p_70016_5_);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(p_70016_1_, p_70016_5_) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(p_70016_3_, f) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			this.ticksInGround = 0;
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D
					/ Math.PI);
			// this.prevRotationPitch = this.rotationPitch =
			// (float)(Math.atan2(this.motionY, (double)f) * 180.0D / Math.PI);
		}

		Block block = this.worldObj.getBlock(this.tileX, this.tileY, this.tileZ);

		if (block.getMaterial() != Material.air) {
			block.setBlockBoundsBasedOnState(this.worldObj, this.tileX, this.tileY,
					this.tileZ);
			AxisAlignedBB axisalignedbb = block.getCollisionBoundingBoxFromPool(this.worldObj, this.tileX,
					this.tileY, this.tileZ);

			if (axisalignedbb != null
					&& axisalignedbb.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ))
					&& !this.getIsCritical()) {
				this.inGround = true;
			}
			
			if(block instanceof BlockDetonatable) {
				((BlockDetonatable) block).onShot(worldObj, this.tileX, this.tileY, this.tileZ);
			}

			if (block == Blocks.glass || block == Blocks.stained_glass || block == Blocks.glass_pane
					|| block == Blocks.stained_glass_pane) {
				this.worldObj.setBlock(this.tileX, this.tileY, this.tileZ, Blocks.air);
				this.worldObj.playSound(this.tileX, this.tileY, this.tileZ, "dig.glass",
						1.0F, 1.0F, true);
			}
		}

		if (this.arrowShake > 0) {
			--this.arrowShake;
		}

		if (this.inGround && !this.getIsCritical()) {
			this.setDead();
			
		} else {
			++this.ticksInAir;
			Vec3 vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			Vec3 vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY,
					this.posZ + this.motionZ);
			MovingObjectPosition movingobjectposition = this.worldObj.func_147447_a(vec31, vec3, false, true, false);
			vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY,
					this.posZ + this.motionZ);

			if (movingobjectposition != null) {
				vec3 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord,
						movingobjectposition.hitVec.zCoord);
			}

			Entity entity = null;
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
					this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double d0 = 0.0D;
			int i;
			float f1;

			for (i = 0; i < list.size(); ++i) {
				Entity entity1 = (Entity) list.get(i);

				if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5)) {
					f1 = 0.3F;
					AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand(f1, f1, f1);
					MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);

					if (movingobjectposition1 != null) {
						double d1 = vec31.distanceTo(movingobjectposition1.hitVec);

						if (d1 < d0 || d0 == 0.0D) {
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}

			if (entity != null) {
				movingobjectposition = new MovingObjectPosition(entity);
			}

			if (movingobjectposition != null && movingobjectposition.entityHit != null
					&& movingobjectposition.entityHit instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) movingobjectposition.entityHit;

				if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer
						&& !((EntityPlayer) this.shootingEntity).canAttackPlayer(entityplayer)) {
					movingobjectposition = null;
				}
			}

			float f2;
			float f4;

			if (movingobjectposition != null) {
				if (movingobjectposition.entityHit != null) {
					//TODO: Remove test feature in release version
					if (!(movingobjectposition.entityHit instanceof EntityItemFrame)
							|| movingobjectposition.entityHit instanceof EntityItemFrame
									&& (((EntityItemFrame) movingobjectposition.entityHit).getDisplayedItem() == null
											|| ((EntityItemFrame) movingobjectposition.entityHit)
													.getDisplayedItem() != null
													&& ((EntityItemFrame) movingobjectposition.entityHit)
															.getDisplayedItem().getItem() != ModItems.flame_pony)) {
						f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY
								+ this.motionZ * this.motionZ);
						int k = MathHelper.ceiling_double_int(f2 * this.damage);

						if (this.getIsCritical()) {
							k += this.rand.nextInt(k / 2 + 2);
						}

						DamageSource damagesource = null;

						//L: Crit
						//R: Chop
						//X: NOT
						//O: Direct
						
						//   X X   Bullet
						//    \|
						//   O-X   Tau
						//   |/ 
						//   X-O   Displacer
						
						if (!this.getIsCritical() && !this.getIsChopper()) {
							if (this.shootingEntity == null) {
								damagesource = ModDamageSource.causeBulletDamage(this, this);
							} else {
								damagesource = ModDamageSource.causeBulletDamage(this, shootingEntity);
							}
						} else if(!this.getIsChopper()) {
							if (this.shootingEntity == null) {
								damagesource = ModDamageSource.causeTauDamage(this, this);
							} else {
								damagesource = ModDamageSource.causeTauDamage(this, shootingEntity);
							}
						} else if(!this.getIsCritical()) {
							if (this.shootingEntity == null) {
								damagesource = ModDamageSource.causeDisplacementDamage(this, this);
							} else {
								damagesource = ModDamageSource.causeDisplacementDamage(this, shootingEntity);
							}
						}

						if (fire || this.isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman)) {
							movingobjectposition.entityHit.setFire(5);
						}

						if (movingobjectposition.entityHit.attackEntityFrom(damagesource, (float) damage)) {
							if (movingobjectposition.entityHit instanceof EntityLivingBase) {
								EntityLivingBase entitylivingbase = (EntityLivingBase) movingobjectposition.entityHit;

								if (rad) {
									if (entitylivingbase instanceof EntityPlayer
											&& ArmorUtil.checkForHazmat((EntityPlayer) entitylivingbase)) {
									} else if (entitylivingbase instanceof EntityCreeper) {
										EntityCreeperNuclear creep = new EntityCreeperNuclear(this.worldObj);
										creep.setLocationAndAngles(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ,
												entitylivingbase.rotationYaw, entitylivingbase.rotationPitch);
										if (!entitylivingbase.isDead)
											if (!worldObj.isRemote)
												worldObj.spawnEntityInWorld(creep);
										entitylivingbase.setDead();
									} else if (entitylivingbase instanceof EntityVillager) {
										EntityZombie creep = new EntityZombie(this.worldObj);
										creep.setLocationAndAngles(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ,
												entitylivingbase.rotationYaw, entitylivingbase.rotationPitch);
										entitylivingbase.setDead();
										if (!this.worldObj.isRemote)
											this.worldObj.spawnEntityInWorld(creep);
									} else if (entitylivingbase instanceof EntityLivingBase
											&& !(entitylivingbase instanceof EntityCreeperNuclear)
											&& !(entitylivingbase instanceof EntityMooshroom)
											&& !(entitylivingbase instanceof EntityZombie)) {
										entitylivingbase.addPotionEffect(new PotionEffect(Potion.poison.getId(), 2 * 60 * 20, 2));
										entitylivingbase.addPotionEffect(new PotionEffect(Potion.wither.getId(), 20, 4));
										entitylivingbase.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 1 * 60 * 20, 1));
									}
								}
								
								if(antidote)
									entitylivingbase.clearActivePotions();
								
								if (this.knockbackStrength > 0) {
									f4 = MathHelper
											.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

									if (f4 > 0.0F) {
										movingobjectposition.entityHit.addVelocity(
												this.motionX * this.knockbackStrength * 0.6000000238418579D / f4, 0.1D,
												this.motionZ * this.knockbackStrength * 0.6000000238418579D / f4);
									}
								}

								if (this.shootingEntity != null && this.shootingEntity instanceof EntityLivingBase) {
									EnchantmentHelper.func_151384_a(entitylivingbase, this.shootingEntity);
									EnchantmentHelper.func_151385_b((EntityLivingBase) this.shootingEntity,
											entitylivingbase);
								}

								if (this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity
										&& movingobjectposition.entityHit instanceof EntityPlayer
										&& this.shootingEntity instanceof EntityPlayerMP) {
									((EntityPlayerMP) this.shootingEntity).playerNetServerHandler
											.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
								}
							}

							if (!(movingobjectposition.entityHit instanceof EntityEnderman)) {
								if (!this.worldObj.isRemote) {
									if (!instakill || movingobjectposition.entityHit instanceof EntityPlayer) {
										// movingobjectposition.entityHit.attackEntityFrom(DamageSource.generic,
										// dmgMin + rand.nextInt(dmgMax -
										// dmgMin));
									} else if (movingobjectposition.entityHit instanceof EntityLivingBase) {
										((EntityLivingBase) movingobjectposition.entityHit).setHealth(0.0F);
									}
								}
								if (!this.getIsCritical())
									//this.setDead();
									;
							}
						} else {
							
							if(movingobjectposition.entityHit instanceof EntityLivingBase) {
								
								try {
									Field lastDamage = ReflectionHelper.findField(EntityLivingBase.class, "lastDamage", "field_110153_bc");
									
									float dmg = (float) damage + lastDamage.getFloat(movingobjectposition.entityHit);
									
									movingobjectposition.entityHit.attackEntityFrom(damagesource, dmg);
								} catch (Exception x) { }
							}
							
						}
						
						
						
						
						
						/* else {
							if (movingobjectposition.entityHit instanceof EntityLivingBase && !(movingobjectposition.entityHit instanceof EntityHunterChopper)) {
								EntityLivingBase target = (EntityLivingBase) movingobjectposition.entityHit;
								target.setHealth((float) (target.getHealth() - damage));
							}
						}*/
					} else {
						this.setDead();
					}
				} else if (!this.getIsCritical()) {
					this.tileX = movingobjectposition.blockX;
					this.tileY = movingobjectposition.blockY;
					this.tileZ = movingobjectposition.blockZ;
					this.field_145790_g = this.worldObj.getBlock(this.tileX, this.tileY,
							this.tileZ);
					this.inData = this.worldObj.getBlockMetadata(this.tileX, this.tileY,
							this.tileZ);
					this.motionX = ((float) (movingobjectposition.hitVec.xCoord - this.posX));
					this.motionY = ((float) (movingobjectposition.hitVec.yCoord - this.posY));
					this.motionZ = ((float) (movingobjectposition.hitVec.zCoord - this.posZ));
					f2 = MathHelper.sqrt_double(
							this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
					this.posX -= this.motionX / f2 * 0.05000000074505806D;
					this.posY -= this.motionY / f2 * 0.05000000074505806D;
					this.posZ -= this.motionZ / f2 * 0.05000000074505806D;
					this.inGround = true;
					this.arrowShake = 7;

					if (this.field_145790_g.getMaterial() != Material.air) {
						this.field_145790_g.onEntityCollidedWithBlock(this.worldObj, this.tileX,
								this.tileY, this.tileZ, this);
					}
				}
			}

			if (this.getIsCritical()) {
				for (i = 0; i < 8; ++i) {
					if (!this.getIsTau())
						this.worldObj.spawnParticle("fireworksSpark", this.posX + this.motionX * i / 8.0D,
								this.posY + this.motionY * i / 8.0D,
								this.posZ + this.motionZ * i / 8.0D, 0, 0,
								0/*-this.motionX, -this.motionY + 0.2D, -this.motionZ*/);
					else
						this.worldObj.spawnParticle("reddust", this.posX + this.motionX * i / 8.0D,
								this.posY + this.motionY * i / 8.0D,
								this.posZ + this.motionZ * i / 8.0D, 0, 0,
								0/*-this.motionX, -this.motionY + 0.2D, -this.motionZ*/);
				}
			}

			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

			// for (this.rotationPitch = (float)(Math.atan2(this.motionY,
			// (double)f2) * 180.0D / Math.PI); this.rotationPitch -
			// this.prevRotationPitch < -180.0F; this.prevRotationPitch -=
			// 360.0F)
			{
				;
			}

			/*
			 * while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			 * this.prevRotationPitch += 360.0F; }
			 * 
			 * while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
			 * this.prevRotationYaw -= 360.0F; }
			 * 
			 * while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			 * this.prevRotationYaw += 360.0F; }
			 */

			// this.rotationPitch = this.prevRotationPitch + (this.rotationPitch
			// - this.prevRotationPitch) * 0.2F;
			// this.rotationYaw = this.prevRotationYaw + (this.rotationYaw -
			// this.prevRotationYaw) * 0.2F;
			float f3 = 0.99F;
			f1 = 0.05F;

			if (this.isInWater()) {
				for (int l = 0; l < 4; ++l) {
					f4 = 0.25F;
					this.worldObj.spawnParticle("bubble", this.posX - this.motionX * f4, this.posY - this.motionY * f4,
							this.posZ - this.motionZ * f4, this.motionX, this.motionY, this.motionZ);
				}

				f3 = 0.8F;
			}

			if (this.isWet()) {
				this.extinguish();
			}

			this.motionX *= f3;
			this.motionY *= f3;
			this.motionZ *= f3;
			this.motionY -= gravity;
			this.setPosition(this.posX, this.posY, this.posZ);
			this.func_145775_I();
		}

		if (this.ticksExisted > 250)
			this.setDead();
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		p_70014_1_.setShort("xTile", (short) this.tileX);
		p_70014_1_.setShort("yTile", (short) this.tileY);
		p_70014_1_.setShort("zTile", (short) this.tileZ);
		p_70014_1_.setShort("life", (short) this.ticksInGround);
		p_70014_1_.setByte("inTile", (byte) Block.getIdFromBlock(this.field_145790_g));
		p_70014_1_.setByte("inData", (byte) this.inData);
		p_70014_1_.setByte("shake", (byte) this.arrowShake);
		p_70014_1_.setByte("inGround", (byte) (this.inGround ? 1 : 0));
		p_70014_1_.setByte("pickup", (byte) this.canBePickedUp);
		p_70014_1_.setDouble("damage", this.damage);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		this.tileX = p_70037_1_.getShort("xTile");
		this.tileY = p_70037_1_.getShort("yTile");
		this.tileZ = p_70037_1_.getShort("zTile");
		this.ticksInGround = p_70037_1_.getShort("life");
		this.field_145790_g = Block.getBlockById(p_70037_1_.getByte("inTile") & 255);
		this.inData = p_70037_1_.getByte("inData") & 255;
		this.arrowShake = p_70037_1_.getByte("shake") & 255;
		this.inGround = p_70037_1_.getByte("inGround") == 1;

		if (p_70037_1_.hasKey("damage", 99)) {
			this.damage = p_70037_1_.getDouble("damage");
		}

		if (p_70037_1_.hasKey("pickup", 99)) {
			this.canBePickedUp = p_70037_1_.getByte("pickup");
		} else if (p_70037_1_.hasKey("player", 99)) {
			this.canBePickedUp = p_70037_1_.getBoolean("player") ? 1 : 0;
		}
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	@Override
	public void onCollideWithPlayer(EntityPlayer p_70100_1_) {
		if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0) {
			boolean flag = this.canBePickedUp == 1 || this.canBePickedUp == 2 && p_70100_1_.capabilities.isCreativeMode;

			if (flag) {
				p_70100_1_.onItemPickup(this, 1);
				this.setDead();
			}
		}
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}

	public void setDamage(double p_70239_1_) {
		this.damage = p_70239_1_;
	}

	public double getDamage() {
		return this.damage;
	}

	/**
	 * Sets the amount of knockback the arrow applies when it hits a mob.
	 */
	public void setKnockbackStrength(int p_70240_1_) {
		this.knockbackStrength = p_70240_1_;
	}

	/**
	 * If returns false, the item will not inflict any damage against entities.
	 */
	@Override
	public boolean canAttackWithItem() {
		return false;
	}

	/**
	 * Whether the arrow has a stream of critical hit particles flying behind
	 * it.
	 */
	public void setIsCritical(boolean p_70243_1_) {
		byte b0 = this.dataWatcher.getWatchableObjectByte(16);

		if (p_70243_1_) {
			this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 | 1)));
		} else {
			this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 & -2)));
		}
	}

	public void setTau(boolean p_70243_1_) {
		byte b0 = this.dataWatcher.getWatchableObjectByte(17);

		if (p_70243_1_) {
			this.dataWatcher.updateObject(17, Byte.valueOf((byte) (b0 | 1)));
		} else {
			this.dataWatcher.updateObject(17, Byte.valueOf((byte) (b0 & -2)));
		}
	}

	public void setChopper(boolean p_70243_1_) {
		byte b0 = this.dataWatcher.getWatchableObjectByte(18);

		if (p_70243_1_) {
			this.dataWatcher.updateObject(18, Byte.valueOf((byte) (b0 | 1)));
		} else {
			this.dataWatcher.updateObject(18, Byte.valueOf((byte) (b0 & -2)));
		}
	}

	/**
	 * Whether the arrow has a stream of critical hit particles flying behind
	 * it.
	 */
	public boolean getIsCritical() {
		byte b0 = this.dataWatcher.getWatchableObjectByte(16);
		return (b0 & 1) != 0;
	}

	public boolean getIsTau() {
		byte b0 = this.dataWatcher.getWatchableObjectByte(17);
		return (b0 & 1) != 0;
	}

	public boolean getIsChopper() {
		byte b0 = this.dataWatcher.getWatchableObjectByte(18);
		return (b0 & 1) != 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_) {
		if(this.getIsCritical() || this.getIsChopper())
			return 15728880;
		else
			return super.getBrightnessForRender(p_70070_1_);
	}

	@Override
	public float getBrightness(float p_70013_1_) {
		if(this.getIsCritical() || this.getIsChopper())
			return 1.0F;
		else
			return super.getBrightness(p_70013_1_);
	}
}
