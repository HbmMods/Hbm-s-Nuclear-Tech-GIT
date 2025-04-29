package com.hbm.entity.projectile;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.bomb.BlockDetonatable;
import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.effect.EntityEMPBlast;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.BlockAllocatorStandard;
import com.hbm.explosion.vanillant.standard.BlockMutatorFire;
import com.hbm.explosion.vanillant.standard.BlockProcessorNoDamage;
import com.hbm.explosion.vanillant.standard.BlockProcessorStandard;
import com.hbm.explosion.vanillant.standard.EntityProcessorStandard;
import com.hbm.explosion.vanillant.standard.ExplosionEffectStandard;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.main.MainRegistry;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.potion.HbmPotion;
import com.hbm.util.ArmorUtil;
import com.hbm.util.BobMathUtil;
import com.hbm.util.Tuple.Pair;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

/**
 * MK2 which features several improvements:
 * - uses generic throwable code, reducing boilerplate nonsense
 * - uses approach-based interpolation, preventing desyncs and making movement silky-smooth
 * - new adjustments in the base class allow for multiple MOP impacts per frame
 * - also comes with tons of legacy code to ensure compat (sadly)
 * @author hbm
 */
public class EntityBulletBaseNT extends EntityThrowableInterp implements IBulletBase {

	@Override public double prevX() { return prevRenderX; }
	@Override public double prevY() { return prevRenderY; }
	@Override public double prevZ() { return prevRenderZ; }
	@Override public void prevX(double d) { prevRenderX = d; }
	@Override public void prevY(double d) { prevRenderY = d; }
	@Override public void prevZ(double d) { prevRenderZ = d; }
	@Override public List<Pair<Vec3, Double>> nodes() { return this.trailNodes; }

	private BulletConfiguration config;
	public float overrideDamage;

	public double prevRenderX;
	public double prevRenderY;
	public double prevRenderZ;
	public final List<Pair<Vec3, Double>> trailNodes = new ArrayList<Pair<Vec3, Double>>();

	public BulletConfiguration getConfig() {
		return config;
	}

	public EntityBulletBaseNT(World world) {
		super(world);
		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);
	}

	public EntityBulletBaseNT(World world, int config) {
		super(world);
		this.config = BulletConfigSyncingUtil.pullConfig(config);
		this.dataWatcher.updateObject(18, config);
		this.dataWatcher.updateObject(16, (byte)this.config.style);
		this.dataWatcher.updateObject(17, (byte)this.config.trail);
		this.renderDistanceWeight = 10.0D;

		if(this.config == null) {
			this.setDead();
			return;
		}

		this.setSize(0.5F, 0.5F);
	}

	public EntityBulletBaseNT(World world, int config, EntityLivingBase entity) {
		super(world);
		this.config = BulletConfigSyncingUtil.pullConfig(config);
		this.dataWatcher.updateObject(18, config);
		this.dataWatcher.updateObject(16, (byte)this.config.style);
		this.dataWatcher.updateObject(17, (byte)this.config.trail);
		thrower = entity;

		ItemStack gun = entity.getHeldItem();
		boolean offsetShot = true;
		boolean accuracyBoost = false;

		if(gun != null && gun.getItem() instanceof ItemGunBase) {
			GunConfiguration cfg = ((ItemGunBase) gun.getItem()).mainConfig;

			if(cfg != null) {
				if(cfg.hasSights && entity.isSneaking()) {
					offsetShot = false;
					accuracyBoost = true;
				}

				if(cfg.isCentered){
					offsetShot = false;
				}
			}
		}

		this.setLocationAndAngles(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ, entity.rotationYaw, entity.rotationPitch);

		if(offsetShot) {
			double sideOffset = 0.16D;

			this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * sideOffset;
			this.posY -= 0.1D;
			this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * sideOffset;
		} else {
			this.posY -= 0.1D;
		}
		this.setPosition(this.posX, this.posY, this.posZ);

		this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));

		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);

		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 1.0F, this.config.spread * (accuracyBoost ? 0.25F : 1F));
	}

	public EntityBulletBaseNT(World world, int config, EntityLivingBase entity, EntityLivingBase target, float motion, float deviation) {
		super(world);

		this.config = BulletConfigSyncingUtil.pullConfig(config);
		this.dataWatcher.updateObject(18, config);
		this.dataWatcher.updateObject(16, (byte)this.config.style);
		this.dataWatcher.updateObject(17, (byte)this.config.trail);
		this.thrower = entity;

		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);

		this.posY = entity.posY + entity.getEyeHeight() - 0.10000000149011612D;
		double d0 = target.posX - entity.posX;
		double d1 = target.boundingBox.minY + target.height / 3.0F - this.posY;
		double d2 = target.posZ - entity.posZ;
		double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);

		if (d3 >= 1.0E-7D) {
			float f2 = (float) (Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
			float f3 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
			double d4 = d0 / d3;
			double d5 = d2 / d3;
			this.setLocationAndAngles(entity.posX + d4, this.posY, entity.posZ + d5, f2, f3);
			this.yOffset = 0.0F;
			this.setThrowableHeading(d0, d1, d2, motion, deviation);
		}
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		//style
		this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));
		//trail
		this.dataWatcher.addObject(17, Byte.valueOf((byte) 0));
		//bullet config sync
		this.dataWatcher.addObject(18, Integer.valueOf((int) 0));
	}

	@Override
	public void onUpdate() {

		if(config == null) config = BulletConfigSyncingUtil.pullConfig(dataWatcher.getWatchableObjectInt(18));

		if(config == null){
			this.setDead();
			return;
		}

		if(worldObj.isRemote && config.style == BulletConfiguration.STYLE_TAU) {
			if(trailNodes.isEmpty()) {
				this.ignoreFrustumCheck = true;
				trailNodes.add(new Pair<Vec3, Double>(Vec3.createVectorHelper(-motionX * 2, -motionY * 2, -motionZ * 2), 0D));
			} else {
				trailNodes.add(new Pair<Vec3, Double>(Vec3.createVectorHelper(0, 0, 0), 1D));
			}
		}

		if(worldObj.isRemote && this.config.blackPowder && this.ticksExisted == 1) {

			for(int i = 0; i < 15; i++) {
				double mod = rand.nextDouble();
				this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ,
						(this.motionX + rand.nextGaussian() * 0.05) * mod,
						(this.motionY + rand.nextGaussian() * 0.05) * mod,
						(this.motionZ + rand.nextGaussian() * 0.05) * mod);
			}

			double mod = 0.5;
			this.worldObj.spawnParticle("flame", this.posX + this.motionX * mod, this.posY + this.motionY * mod, this.posZ + this.motionZ * mod, 0, 0, 0);
		}

		if(!worldObj.isRemote) {

			if(config.maxAge == 0) {
				if(this.config.bntUpdate != null) this.config.bntUpdate.behaveUpdate(this);
				this.setDead();
				return;
			}

			if(this.ticksExisted > config.maxAge) this.setDead();
		}

		if(this.config.bntUpdate != null) this.config.bntUpdate.behaveUpdate(this);

		this.prevPosX = posX;
		this.prevPosY = posY;
		this.prevPosZ = posZ;

		super.onUpdate();

		if(worldObj.isRemote && !config.vPFX.isEmpty()) {

			Vec3 vec = Vec3.createVectorHelper(posX - prevPosX, posY - prevPosY, posZ - prevPosZ);
			double motion = Math.max(vec.lengthVector(), 0.1);
			vec = vec.normalize();

			for(double d = 0; d < motion; d += 0.5) {

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setString("type", "vanillaExt");
				nbt.setString("mode", config.vPFX);
				nbt.setDouble("posX", this.posX - vec.xCoord * d);
				nbt.setDouble("posY", this.posY - vec.yCoord * d);
				nbt.setDouble("posZ", this.posZ - vec.zCoord * d);
				MainRegistry.proxy.effectNT(nbt);
			}
		}
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {

		if(mop.typeOfHit == MovingObjectType.BLOCK) {

			boolean hRic = rand.nextInt(100) < config.HBRC;
			boolean doesRic = config.doesRicochet && hRic;

			if(!config.isSpectral && !doesRic) {
				this.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
				this.onBlockImpact(mop.blockX, mop.blockY, mop.blockZ, mop.sideHit);
			}

			if(doesRic) {

				Vec3 face = null;

				switch(mop.sideHit) {
				case 0: face = Vec3.createVectorHelper(0, -1, 0); break;
				case 1: face = Vec3.createVectorHelper(0, 1, 0); break;
				case 2: face = Vec3.createVectorHelper(0, 0, 1); break;
				case 3: face = Vec3.createVectorHelper(0, 0, -1); break;
				case 4: face = Vec3.createVectorHelper(-1, 0, 0); break;
				case 5: face = Vec3.createVectorHelper(1, 0, 0); break;
				}

				if(face != null) {

					Vec3 vel = Vec3.createVectorHelper(motionX, motionY, motionZ);
					vel.normalize();

					boolean lRic = rand.nextInt(100) < config.LBRC;
					double angle = Math.abs(BobMathUtil.getCrossAngle(vel, face) - 90);

					if(hRic || (angle <= config.ricochetAngle && lRic)) {
						switch(mop.sideHit) {
						case 0:
						case 1: motionY *= -1; break;
						case 2:
						case 3: motionZ *= -1; break;
						case 4:
						case 5: motionX *= -1; break;
						}

						if(config.plink == 1)
							worldObj.playSoundAtEntity(this, "hbm:weapon.ricochet", 0.25F, 1.0F);
						if(config.plink == 2)
							worldObj.playSoundAtEntity(this, "hbm:weapon.gBounce", 1.0F, 1.0F);

						this.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
						onRicochet(mop.blockX, mop.blockY, mop.blockZ);

						//worldObj.setBlock((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posZ), Blocks.dirt);

					} else {
						if(!worldObj.isRemote) {
							this.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
							onBlockImpact(mop.blockX, mop.blockY, mop.blockZ, mop.sideHit);
						}
					}

					/*this.posX += (mop.hitVec.xCoord - this.posX) * 0.6;
					this.posY += (mop.hitVec.yCoord - this.posY) * 0.6;
					this.posZ += (mop.hitVec.zCoord - this.posZ) * 0.6;*/

					this.motionX *= config.bounceMod;
					this.motionY *= config.bounceMod;
					this.motionZ *= config.bounceMod;
				}
			}

		}

		if(mop.entityHit != null) {

			DamageSource damagesource = this.config.getDamage(this, this.thrower);
			Entity victim = mop.entityHit;

			if(!config.doesPenetrate) {
				this.setPosition(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
				onEntityImpact(victim);
			} else {
				onEntityHurt(victim);
			}

			float damage = rand.nextFloat() * (config.dmgMax - config.dmgMin) + config.dmgMin;

			if(overrideDamage != 0)
				damage = overrideDamage;

			boolean headshot = false;

			if(victim instanceof EntityLivingBase && this.config.headshotMult > 1F) {
				EntityLivingBase living = (EntityLivingBase) victim;
				double head = living.height - living.getEyeHeight();

				if(!!living.isEntityAlive() && mop.hitVec != null && mop.hitVec.yCoord > (living.posY + living.height - head * 2)) {
					damage *= this.config.headshotMult;
					headshot = true;
				}
			}

			if(victim != null && !victim.attackEntityFrom(damagesource, damage)) {

				try {
					Field lastDamage = ReflectionHelper.findField(EntityLivingBase.class, "lastDamage", "field_110153_bc");
					float dmg = (float) damage + lastDamage.getFloat(victim);
					if(!victim.attackEntityFrom(damagesource, dmg)) headshot = false;
				} catch (Exception x) { }

			}

			if(!worldObj.isRemote && headshot) {
				if(victim instanceof EntityLivingBase) {
					EntityLivingBase living = (EntityLivingBase) victim;
					double head = living.height - living.getEyeHeight();
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "vanillaburst");
					data.setInteger("count", 15);
					data.setDouble("motion", 0.1D);
					data.setString("mode", "blockdust");
					data.setInteger("block", Block.getIdFromBlock(Blocks.redstone_block));
					PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, living.posX, living.posY + living.height - head, living.posZ), new TargetPoint(living.dimension, living.posX, living.posY, living.posZ, 50));
					worldObj.playSoundEffect(victim.posX, victim.posY, victim.posZ, "mob.zombie.woodbreak", 1.0F, 0.95F + rand.nextFloat() * 0.2F);
				}
			}
		}
	}

	//for when a bullet dies by hitting a block
	private void onBlockImpact(int bX, int bY, int bZ, int sideHit) {
		Block block = worldObj.getBlock(bX, bY, bZ);

		if(config.bntImpact != null)
			config.bntImpact.behaveBlockHit(this, bX, bY, bZ, sideHit);

		if(!worldObj.isRemote) {
			if(!config.liveAfterImpact && !config.isSpectral && bY > -1 && !this.inGround) this.setDead();
			if(!config.doesPenetrate && bY == -1) this.setDead();
		}

		if(config.incendiary > 0 && !this.worldObj.isRemote) {
			if(worldObj.rand.nextInt(3) == 0 && worldObj.getBlock((int)posX, (int)posY, (int)posZ) == Blocks.air) worldObj.setBlock((int)posX, (int)posY, (int)posZ, Blocks.fire);
			if(worldObj.rand.nextInt(3) == 0 && worldObj.getBlock((int)posX + 1, (int)posY, (int)posZ) == Blocks.air) worldObj.setBlock((int)posX + 1, (int)posY, (int)posZ, Blocks.fire);
			if(worldObj.rand.nextInt(3) == 0 && worldObj.getBlock((int)posX - 1, (int)posY, (int)posZ) == Blocks.air) worldObj.setBlock((int)posX - 1, (int)posY, (int)posZ, Blocks.fire);
			if(worldObj.rand.nextInt(3) == 0 && worldObj.getBlock((int)posX, (int)posY + 1, (int)posZ) == Blocks.air) worldObj.setBlock((int)posX, (int)posY + 1, (int)posZ, Blocks.fire);
			if(worldObj.rand.nextInt(3) == 0 && worldObj.getBlock((int)posX, (int)posY - 1, (int)posZ) == Blocks.air) worldObj.setBlock((int)posX, (int)posY - 1, (int)posZ, Blocks.fire);
			if(worldObj.rand.nextInt(3) == 0 && worldObj.getBlock((int)posX, (int)posY, (int)posZ + 1) == Blocks.air) worldObj.setBlock((int)posX, (int)posY, (int)posZ + 1, Blocks.fire);
			if(worldObj.rand.nextInt(3) == 0 && worldObj.getBlock((int)posX, (int)posY, (int)posZ - 1) == Blocks.air) worldObj.setBlock((int)posX, (int)posY, (int)posZ - 1, Blocks.fire);
		}

		if(config.emp > 0)
			ExplosionNukeGeneric.empBlast(this.worldObj, (int)(this.posX + 0.5D), (int)(this.posY + 0.5D), (int)(this.posZ + 0.5D), config.emp);

		if(config.emp > 3) {
			if (!this.worldObj.isRemote) {

				EntityEMPBlast cloud = new EntityEMPBlast(this.worldObj, config.emp);
				cloud.posX = this.posX;
				cloud.posY = this.posY + 0.5F;
				cloud.posZ = this.posZ;

				this.worldObj.spawnEntityInWorld(cloud);
			}
		}

		if(config.jolt > 0 && !worldObj.isRemote)
			ExplosionLarge.jolt(worldObj, posX, posY, posZ, config.jolt, 150, 0.25);

		if(config.explosive > 0 && !worldObj.isRemote) {
			//worldObj.newExplosion(this.thrower, posX, posY, posZ, config.explosive, config.incendiary > 0, config.blockDamage);
			ExplosionVNT vnt = new ExplosionVNT(worldObj, posX, posY, posZ, config.explosive, this.thrower);
			vnt.setBlockAllocator(new BlockAllocatorStandard());
			if(config.blockDamage)	vnt.setBlockProcessor(new BlockProcessorStandard().withBlockEffect(config.incendiary > 0 ? new BlockMutatorFire() : null));
			else					vnt.setBlockProcessor(new BlockProcessorNoDamage().withBlockEffect(config.incendiary > 0 ? new BlockMutatorFire() : null));
			vnt.setEntityProcessor(new EntityProcessorStandard().allowSelfDamage());
			vnt.setPlayerProcessor(new PlayerProcessorStandard());
			vnt.setSFX(new ExplosionEffectStandard());
			vnt.explode();
		}

		if(config.shrapnel > 0 && !worldObj.isRemote)
			ExplosionLarge.spawnShrapnels(worldObj, posX, posY, posZ, config.shrapnel);

		if(config.chlorine > 0 && !worldObj.isRemote) {
			ExplosionChaos.spawnChlorine(worldObj, posX, posY, posZ, config.chlorine, 1.5, 0);
			worldObj.playSoundEffect((double)(posX + 0.5F), (double)(posY + 0.5F), (double)(posZ + 0.5F), "random.fizz", 5.0F, 2.6F + (rand.nextFloat() - rand.nextFloat()) * 0.8F);
		}

		if(config.rainbow > 0 && !worldObj.isRemote) {
			EntityNukeExplosionMK3 ex = EntityNukeExplosionMK3.statFacFleija(worldObj, posX, posY, posZ, config.rainbow);
			if(!ex.isDead) {
				this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 100.0f, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
				worldObj.spawnEntityInWorld(ex);

				EntityCloudFleijaRainbow cloud = new EntityCloudFleijaRainbow(this.worldObj, config.rainbow);
				cloud.posX = this.posX;
				cloud.posY = this.posY;
				cloud.posZ = this.posZ;
				this.worldObj.spawnEntityInWorld(cloud);
			}
		}

		if(config.nuke > 0 && !worldObj.isRemote) {
			worldObj.spawnEntityInWorld(EntityNukeExplosionMK5.statFac(worldObj, config.nuke, posX, posY, posZ));
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "muke");
			if(MainRegistry.polaroidID == 11 || rand.nextInt(100) == 0) data.setBoolean("balefire", true);
			PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, posX, posY + 0.5, posZ), new TargetPoint(dimension, posX, posY, posZ, 250));
			worldObj.playSoundEffect(posX, posY, posZ, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);
		}

		if(config.destroysBlocks && !worldObj.isRemote) {
			if(block.getBlockHardness(worldObj, bX, bY, bZ) <= 120)
				worldObj.func_147480_a(bX, bY, bZ, false);
		} else if(config.doesBreakGlass && !worldObj.isRemote) {
			if(block == Blocks.glass || block == Blocks.glass_pane || block == Blocks.stained_glass || block == Blocks.stained_glass_pane)
				worldObj.func_147480_a(bX, bY, bZ, false);

			if(block instanceof BlockDetonatable) {
				((BlockDetonatable) block).onShot(worldObj, bX, bY, bZ);
			}
		}
	}

	//for when a bullet dies by hitting a block
	private void onRicochet(int bX, int bY, int bZ) {

		if(config.bntRicochet != null)
			config.bntRicochet.behaveBlockRicochet(this, bX, bY, bZ);
	}

	//for when a bullet dies by hitting an entity
	private void onEntityImpact(Entity e) {
		onEntityHurt(e);
		onBlockImpact(-1, -1, -1, -1);

		if(config.bntHit != null)
			config.bntHit.behaveEntityHit(this, e);

		//this.setDead();
	}

	//for when a bullet hurts an entity, not necessarily dying
	private void onEntityHurt(Entity e) {

		if(config.bntHurt != null)
			config.bntHurt.behaveEntityHurt(this, e);

		if(config.incendiary > 0 && !worldObj.isRemote) {
			e.setFire(config.incendiary);
		}

		if(config.leadChance > 0 && !worldObj.isRemote && worldObj.rand.nextInt(100) < config.leadChance && e instanceof EntityLivingBase) {
			((EntityLivingBase)e).addPotionEffect(new PotionEffect(HbmPotion.lead.id, 10 * 20, 0));
		}

		if(e instanceof EntityLivingBase && config.effects != null && !config.effects.isEmpty() && !worldObj.isRemote) {

			for(PotionEffect effect : config.effects) {
				((EntityLivingBase)e).addPotionEffect(new PotionEffect(effect));
			}
		}

		if(config.instakill && e instanceof EntityLivingBase && !worldObj.isRemote) {

			if(!(e instanceof EntityPlayer && ((EntityPlayer)e).capabilities.isCreativeMode))
				((EntityLivingBase)e).setHealth(0.0F);
		}

		if(config.caustic > 0 && e instanceof EntityPlayer){
			ArmorUtil.damageSuit((EntityPlayer)e, 0, config.caustic);
			ArmorUtil.damageSuit((EntityPlayer)e, 1, config.caustic);
			ArmorUtil.damageSuit((EntityPlayer)e, 2, config.caustic);
			ArmorUtil.damageSuit((EntityPlayer)e, 3, config.caustic);
		}
	}

	@Override
	public boolean doesPenetrate() {
		return this.config.doesPenetrate;
	}

	@Override
	public boolean isSpectral() {
		return this.config.isSpectral;
	}

	@Override
	public int selfDamageDelay() {
		return this.config.selfDamageDelay;
	}

	@Override
	protected double headingForceMult() {
		return 1D;
	}

	@Override
	public double getGravityVelocity() {
		return this.config.gravity;
	}

	@Override
	protected double motionMult() {
		return this.config.velocity;
	}

	@Override
	protected float getAirDrag() {
		return 1F;
	}

	@Override
	protected float getWaterDrag() {
		return 1F;
	}

	@Override
	public boolean writeToNBTOptional(NBTTagCompound nbt) {
		return false;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.setDead();
	}

	public static interface IBulletHurtBehaviorNT { public void behaveEntityHurt(EntityBulletBaseNT bullet, Entity hit); }
	public static interface IBulletHitBehaviorNT { public void behaveEntityHit(EntityBulletBaseNT bullet, Entity hit); }
	public static interface IBulletRicochetBehaviorNT { public void behaveBlockRicochet(EntityBulletBaseNT bullet, int x, int y, int z); }
	public static interface IBulletImpactBehaviorNT { public void behaveBlockHit(EntityBulletBaseNT bullet, int x, int y, int z, int sideHit); }
	public static interface IBulletUpdateBehaviorNT { public void behaveUpdate(EntityBulletBaseNT bullet); }
}
