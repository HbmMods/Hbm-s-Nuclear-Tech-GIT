package com.hbm.entity.mob.siege;

import java.util.List;

import com.hbm.entity.mob.EntityUFOBase;
import com.hbm.entity.projectile.EntitySiegeLaser;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntitySiegeCraft extends EntityUFOBase implements IBossDisplayData {

	private int attackCooldown;
	private int beamCountdown;

	public EntitySiegeCraft(World world) {
		super(world);
		this.setSize(7F, 1F);
		this.isImmuneToFire = true;
		this.ignoreFrustumCheck = true;
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
	protected void onDeathUpdate() {

		this.beamCountdown = 200;
		this.setBeam(false);

		this.motionY -= 0.05D;

		if(this.deathTime == 19 && !worldObj.isRemote) {

			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "tinytot");
			PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, posX, posY + 0.5, posZ), new TargetPoint(this.dimension, posX, posY, posZ, 250));
			worldObj.playSoundEffect(posX, posY, posZ, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);
		}

		super.onDeathUpdate();
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.getDataWatcher().addObject(12, (int) 0);
		this.getDataWatcher().addObject(13, 0F);
		this.getDataWatcher().addObject(14, 0F);
		this.getDataWatcher().addObject(15, 0F);
		this.getDataWatcher().addObject(16, (byte) 0);
	}

	public void setTier(SiegeTier tier) {
		this.getDataWatcher().updateObject(12, tier.id);

		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(tier.speedMod);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(tier.health * 25);
		this.setHealth(this.getMaxHealth());
	}

	public SiegeTier getTier() {
		SiegeTier tier = SiegeTier.tiers[this.getDataWatcher().getWatchableObjectInt(12)];
		return tier != null ? tier : SiegeTier.CLAY;
	}

	public void setBeam(boolean beam) {
		this.getDataWatcher().updateObject(16, beam ? (byte) 1 : (byte) 0);
	}

	public boolean getBeam() {
		return this.getDataWatcher().getWatchableObjectByte(16) == 1;
	}

	public void setLockon(double x, double y, double z) {
		this.getDataWatcher().updateObject(13, (float) x);
		this.getDataWatcher().updateObject(14, (float) y);
		this.getDataWatcher().updateObject(15, (float) z);
	}

	public Vec3 getLockon() {
		return Vec3.createVectorHelper(
				this.getDataWatcher().getWatchableObjectFloat(13),
				this.getDataWatcher().getWatchableObjectFloat(14),
				this.getDataWatcher().getWatchableObjectFloat(15)
				);
	}

	@Override
	protected int getScanRange() {
		return 100;
	}

	@Override
	protected int targetHeightOffset() {
		return 7 + rand.nextInt(5);
	}

	@Override
	protected int wanderHeightOffset() {
		return 10 + rand.nextInt(2);
	}

	@Override
	protected void updateEntityActionState() {
		super.updateEntityActionState();

		if(this.courseChangeCooldown > 0) {
			this.courseChangeCooldown--;
		}
		if(this.scanCooldown > 0) {
			this.scanCooldown--;
		}

		if(!worldObj.isRemote) {
			if(this.attackCooldown > 0) {
				this.attackCooldown--;
			}
			if(this.beamCountdown > 0) {
				this.beamCountdown--;
			}

			if(rand.nextInt(50) == 0) {

				NBTTagCompound dPart = new NBTTagCompound();
				dPart.setString("type", "tau");
				dPart.setByte("count", (byte)(2 + rand.nextInt(3)));
				PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(dPart, posX + rand.nextGaussian() * 2, posY + rand.nextGaussian(), posZ + rand.nextGaussian() * 2), new TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 50));
			}

			boolean beam = false;

			if(this.target == null || this.beamCountdown <= 0) {
				this.beamCountdown = 300; //200 - 100: nothing, 100 - 40: update lockon, 40 - 20: fix lockon, 20 - 0: beam
			} else {

				if(this.beamCountdown >= 60 && this.beamCountdown < 120) {
					double x = this.target.posX;
					double y = this.target.posY + this.target.height * 0.5;
					double z = this.target.posZ;
					this.setLockon(x, y, z);

					if(this.beamCountdown == 110) {
						worldObj.playSoundAtEntity(this.target, "hbm:weapon.stingerLockOn", 2F, 0.75F);
					}
				}

				if(this.beamCountdown >= 40 && this.beamCountdown < 100) {

					Vec3 lockon = this.getLockon();
					NBTTagCompound fx = new NBTTagCompound();
					fx.setString("type", "vanillaburst");
					fx.setString("mode", "reddust");
					fx.setDouble("motion", 0.2D);
					fx.setInteger("count", 5);
					PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(fx, lockon.xCoord, lockon.yCoord, lockon.zCoord), new TargetPoint(this.dimension, lockon.xCoord, lockon.yCoord, lockon.zCoord, 100));
				}

				if(this.beamCountdown < 40) {

					Vec3 lockon = this.getLockon();

					if(this.beamCountdown == 39) {
						worldObj.playSoundEffect(lockon.xCoord, lockon.yCoord, lockon.zCoord, "hbm:entity.ufoBlast", 5.0F, 0.9F + worldObj.rand.nextFloat() * 0.2F);
					}

					List<Entity> entities = worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(lockon.xCoord, lockon.yCoord, lockon.zCoord, lockon.xCoord, lockon.yCoord, lockon.zCoord).expand(2, 2, 2));

					for(Entity e : entities) {
						if(this.canAttackClass(e.getClass())) {
							e.attackEntityFrom(ModDamageSource.causeCombineDamage(this, e), 1000F);
							e.setFire(5);

							if(e instanceof EntityLivingBase)
								ContaminationUtil.contaminate((EntityLivingBase)e, HazardType.RADIATION, ContaminationType.CREATIVE, 5F);
						}
					}


					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "plasmablast");
					data.setFloat("r", 0.0F);
					data.setFloat("g", 0.75F);
					data.setFloat("b", 1.0F);
					data.setFloat("pitch", -90 + rand.nextFloat() * 180);
					data.setFloat("yaw", rand.nextFloat() * 180F);
					data.setFloat("scale", 5F);
					PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, lockon.xCoord, lockon.yCoord, lockon.zCoord),  new TargetPoint(dimension, lockon.xCoord, lockon.yCoord, lockon.zCoord, 150));
					beam = true;
				}
			}

			this.setBeam(beam);

			if(this.attackCooldown == 0 && this.target != null) {
				this.attackCooldown = 30 + rand.nextInt(10);

				double x = posX;
				double y = posY;
				double z = posZ;

				Vec3 vec = Vec3.createVectorHelper(target.posX - x, target.posY + target.height * 0.5 - y, target.posZ - z).normalize();
				SiegeTier tier = this.getTier();

				float health = getHealth() / getMaxHealth();

				int r = (int)(0xff * (1 - health));
				int g = (int)(0xff * health);
				int b = 0;
				int color = (r << 16) | (g << 8) | b;

				for(int i = 0; i < 7; i++) {

					Vec3 copy = Vec3.createVectorHelper(vec.xCoord, vec.yCoord, vec.zCoord);

					copy.rotateAroundY((float)Math.PI / 180F * (i - 3) * 5F);

					EntitySiegeLaser laser = new EntitySiegeLaser(worldObj, this);
					laser.setPosition(x, y, z);
					laser.setThrowableHeading(copy.xCoord, copy.yCoord, copy.zCoord, 1F, 0.0F);
					laser.setColor(color);
					laser.setDamage(tier.damageMod);
					laser.setBreakChance(tier.laserBreak * 2);
					if(tier.laserIncendiary) laser.setIncendiary();
					worldObj.spawnEntityInWorld(laser);
				}

				this.playSound("hbm:weapon.ballsLaser", 2.0F, 1.0F);
			}
		}

		if(this.courseChangeCooldown > 0) {
			approachPosition(this.target == null ? 0.25D : 0.5D + this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue() * 1);
		}
	}

	@Override
	protected void setCourseWithoutTaget() {
		int x = (int) Math.floor(posX + rand.nextGaussian() * 15);
		int z = (int) Math.floor(posZ + rand.nextGaussian() * 15);
		this.setWaypoint(x, this.worldObj.getHeightValue(x, z) + 5 + rand.nextInt(6),  z);
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
