package com.hbm.entity.projectile;

import java.lang.reflect.Field;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.RedBarrel;
import com.hbm.calc.VectorUtil;
import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.effect.EntityEMPBlast;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.entity.particle.EntityTSmokeFX;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.explosion.ExplosionParticle;
import com.hbm.explosion.ExplosionParticleB;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityBulletLaser extends Entity {
	protected BulletConfiguration config;
	protected EntityLivingBase shooter;
	
	public EntityBulletLaser(World world) {
		super(world);
	}
	
	public EntityBulletLaser(World world, EntityLivingBase entity) {
		super(world);
		shooter = entity;
		
		this.setLocationAndAngles(entity.posX,  entity.posY + entity.getEyeHeight(),  entity.posZ,  entity.rotationYaw,  entity.rotationPitch);
		
		this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
		this.posY -= 0.10000000149011612D;
		this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
		this.setPosition(this.posX, this.posY, this.posZ);
	}
	
	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(20, "");
	}
	
	@Override
	public void onUpdate() {
		if (this.ticksExisted > 1) {
			this.setDead();
		}
		
		
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		
	}
}
