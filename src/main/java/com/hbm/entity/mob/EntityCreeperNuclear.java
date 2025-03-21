package com.hbm.entity.mob;

import java.util.List;

import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityCreeperNuclear extends EntityCreeper {

	public EntityCreeperNuclear(World world) {
		super(world);
		this.fuseTime = 75;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {

		// for some reason the nuclear explosion would damage the already dead entity, reviving it and forcing it to play the death animation
		if(this.isDead) return false;

		if(source == ModDamageSource.radiation || source == ModDamageSource.mudPoisoning) {
			if(this.isEntityAlive()) this.heal(amount);
			return false;
		}

		return super.attackEntityFrom(source, amount);
	}

	@Override
	protected Item getDropItem() {
		return Item.getItemFromBlock(Blocks.tnt);
	}

	@Override
	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
		super.dropFewItems(p_70628_1_, p_70628_2_);

		if(rand.nextInt(3) == 0)
			this.dropItem(ModItems.coin_creeper, 1);
	}

	@Override
	public void onDeath(DamageSource p_70645_1_) {
		super.onDeath(p_70645_1_);

		List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(50, 50, 50));

		for(EntityPlayer player : players) {
			player.triggerAchievement(MainRegistry.bossCreeper);
		}

		if(p_70645_1_.getEntity() instanceof EntitySkeleton || (p_70645_1_.isProjectile() && p_70645_1_.getEntity() instanceof EntityArrow && ((EntityArrow) (p_70645_1_.getEntity())).shootingEntity == null)) {
			this.entityDropItem(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.NUKE_STANDARD), 1);
		}
	}

	@Override
	public void onUpdate() {

		List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(5, 5, 5));

		for(Entity e : list) {
			if(e instanceof EntityLivingBase) {
				ContaminationUtil.contaminate((EntityLivingBase) e, HazardType.RADIATION, ContaminationType.CREATIVE, 0.25F);
			}
		}

		super.onUpdate();

		if(this.isEntityAlive() && this.getHealth() < this.getMaxHealth() && this.ticksExisted % 10 == 0) {
			this.heal(1.0F);
		}
	}

	@Override
	public void func_146077_cc() {
		if(!this.worldObj.isRemote) {

			this.setDead();

			boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

			if(this.getPowered()) {

				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "muke");
				PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, posX, posY + 0.5, posZ), new TargetPoint(dimension, posX, posY, posZ, 250));
				worldObj.playSoundEffect(posX, posY + 0.5, posZ, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);

				if(flag) {
					worldObj.spawnEntityInWorld(EntityNukeExplosionMK5.statFac(worldObj, 50, posX, posY, posZ));
				} else {
					ExplosionNukeGeneric.dealDamage(worldObj, posX, posY + 0.5, posZ, 100);
				}
			} else {

				if(flag) {
					ExplosionNukeSmall.explode(worldObj, posX, posY + 0.5, posZ, ExplosionNukeSmall.PARAMS_MEDIUM);
				} else {
					ExplosionNukeSmall.explode(worldObj, posX, posY + 0.5, posZ, ExplosionNukeSmall.PARAMS_SAFE);
				}
			}
		}
	}
}
