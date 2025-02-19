package com.hbm.items.weapon.sedna.factory;

import java.util.Locale;

import com.hbm.entity.mob.*;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.particle.helper.AshesCreator;
import com.hbm.particle.helper.SkeletonCreator;
import com.hbm.util.DamageResistanceHandler.DamageClass;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;

public class ConfettiUtil {
	
	public static void decideConfetti(EntityLivingBase entity, DamageSource source) {
		if(entity.isEntityAlive()) return;
		if(source.damageType.equals(DamageClass.LASER.name().toLowerCase(Locale.US))) pulverize(entity);
		if(source.damageType.equals(DamageClass.ELECTRIC.name().toLowerCase(Locale.US))) pulverize(entity);
		if(source.isExplosion()) gib(entity);
		if(source.isFireDamage()) cremate(entity);
	}

	public static void pulverize(EntityLivingBase entity) {
		int amount = MathHelper.clamp_int((int) (entity.width * entity.height * entity.width * 25), 5, 50);
		AshesCreator.composeEffect(entity.worldObj, entity, amount, 0.125F);
		SkeletonCreator.composeEffect(entity.worldObj, entity, 1F);
		entity.worldObj.playSoundEffect(entity.posX, entity.posY, entity.posZ, "hbm:weapon.fire.disintegration", 2.0F, 0.9F + entity.getRNG().nextFloat() * 0.2F);
	}

	public static void cremate(EntityLivingBase entity) {
		int amount = MathHelper.clamp_int((int) (entity.width * entity.height * entity.width * 25), 5, 50);
		AshesCreator.composeEffect(entity.worldObj, entity, amount, 0.125F);
		SkeletonCreator.composeEffect(entity.worldObj, entity, 0.25F);
		entity.worldObj.playSoundEffect(entity.posX, entity.posY, entity.posZ, "hbm:weapon.fire.disintegration", 2.0F, 0.9F + entity.getRNG().nextFloat() * 0.2F);
	}

	public static void gib(EntityLivingBase entity) {
		if(entity instanceof EntityCyberCrab) return;
		if(entity instanceof EntityTeslaCrab) return;
		if(entity instanceof EntityTaintCrab) return;
		if(entity instanceof EntitySkeleton) return;
		if(entity instanceof EntitySlime) return;
		
		NBTTagCompound vdat = new NBTTagCompound();
		vdat.setString("type", "giblets");
		vdat.setInteger("ent", entity.getEntityId());
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(vdat, entity.posX, entity.posY + entity.height * 0.5, entity.posZ), new TargetPoint(entity.dimension, entity.posX, entity.posY + entity.height * 0.5, entity.posZ, 150));
		entity.worldObj.playSoundEffect(entity.posX, entity.posY, entity.posZ, "mob.zombie.woodbreak", 2.0F, 0.95F + entity.getRNG().nextFloat() * 0.2F);
	}
}
