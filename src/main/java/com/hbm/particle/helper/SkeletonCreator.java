package com.hbm.particle.helper;

import java.util.HashMap;
import java.util.Random;
import java.util.function.Function;

import com.hbm.entity.mob.EntityDummy;
import com.hbm.main.ClientProxy;
import com.hbm.particle.ParticleSkeleton;
import com.hbm.util.Vec3NT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class SkeletonCreator implements IParticleCreator {
	
	public static HashMap<String, Function<EntityLivingBase, BoneDefinition[]>> skullanizer = new HashMap();
	
	public static void composeEffect(World world, Entity toSkeletonize, float brightness) {
		
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "skeleton");
		data.setInteger("entityID", toSkeletonize.getEntityId());
		data.setFloat("brightness", brightness);
		IParticleCreator.sendPacket(world, toSkeletonize.posX, toSkeletonize.posY, toSkeletonize.posZ, 100, data);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void makeParticle(World world, EntityPlayer player, TextureManager texman, Random rand, double x, double y, double z, NBTTagCompound data) {
		
		if(skullanizer.isEmpty()) init();
		
		int entityID = data.getInteger("entityID");
		Entity entity = world.getEntityByID(entityID);
		if(!(entity instanceof EntityLivingBase)) return;
		EntityLivingBase living = (EntityLivingBase) entity;
		
		ClientProxy.vanish(entityID);
		
		float brightness = data.getFloat("brightness");
		
		Function<EntityLivingBase, BoneDefinition[]> bonealizer = skullanizer.get(entity.getClass().getSimpleName());
		
		if(bonealizer != null) {
			BoneDefinition[] bones = bonealizer.apply(living);
			for(BoneDefinition bone : bones) {
				ParticleSkeleton skeleton = new ParticleSkeleton(Minecraft.getMinecraft().getTextureManager(), world, bone.x, bone.y, bone.z, brightness, brightness, brightness, bone.type);
				skeleton.prevRotationYaw = skeleton.rotationYaw = bone.yaw;
				skeleton.prevRotationPitch = skeleton.rotationPitch = bone.pitch;
				Minecraft.getMinecraft().effectRenderer.addEffect(skeleton);
			}
		}
	}
	
	public static class BoneDefinition {
		public EnumSkeletonType type;
		public float yaw;
		public float pitch;
		public double x;
		public double y;
		public double z;
		
		public BoneDefinition(EnumSkeletonType type, float yaw, float pitch, double x, double y, double z) {
			this.type = type;
			this.yaw = yaw;
			this.pitch = pitch;
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
	
	public static enum EnumSkeletonType {
		SKULL, TORSO, LIMB, SKULL_VILLAGER
	}
	
	public static Function<EntityLivingBase, BoneDefinition[]> BONES_BIPED = (entity) -> {
		Vec3NT leftarm = new Vec3NT(0.375, 0, 0).rotateAroundYDeg(-entity.renderYawOffset);
		Vec3NT leftleg = new Vec3NT(0.125, 0, 0).rotateAroundYDeg(-entity.renderYawOffset);
		return new BoneDefinition[] {
				new BoneDefinition(EnumSkeletonType.SKULL, -entity.rotationYawHead, entity.rotationPitch, entity.posX, entity.posY + 1.75, entity.posZ),
				new BoneDefinition(EnumSkeletonType.TORSO, -entity.renderYawOffset, 0, entity.posX, entity.posY + 1.125, entity.posZ),
				new BoneDefinition(EnumSkeletonType.LIMB, -entity.renderYawOffset, 0, entity.posX + leftarm.xCoord, entity.posY + 1.125, entity.posZ + leftarm.zCoord),
				new BoneDefinition(EnumSkeletonType.LIMB, -entity.renderYawOffset, 0, entity.posX - leftarm.xCoord, entity.posY + 1.125, entity.posZ - leftarm.zCoord),
				new BoneDefinition(EnumSkeletonType.LIMB, -entity.renderYawOffset, 0, entity.posX + leftleg.xCoord, entity.posY + 0.375, entity.posZ + leftleg.zCoord),
				new BoneDefinition(EnumSkeletonType.LIMB, -entity.renderYawOffset, 0, entity.posX - leftleg.xCoord, entity.posY + 0.375, entity.posZ - leftleg.zCoord),
		};
	};
	
	public static Function<EntityLivingBase, BoneDefinition[]> BONES_ZOMBIE = (entity) -> {
		Vec3NT leftarm = new Vec3NT(0.375, 0, 0).rotateAroundYDeg(-entity.renderYawOffset);
		Vec3NT forward = new Vec3NT(0, 0, 0.25).rotateAroundYDeg(-entity.renderYawOffset);
		Vec3NT leftleg = new Vec3NT(0.125, 0, 0).rotateAroundYDeg(-entity.renderYawOffset);
		return new BoneDefinition[] {
				new BoneDefinition(EnumSkeletonType.SKULL, -entity.rotationYawHead, entity.rotationPitch, entity.posX, entity.posY + 1.75, entity.posZ),
				new BoneDefinition(EnumSkeletonType.TORSO, -entity.renderYawOffset, 0, entity.posX, entity.posY + 1.125, entity.posZ),
				new BoneDefinition(EnumSkeletonType.LIMB, -entity.renderYawOffset, -90, entity.posX + leftarm.xCoord + forward.xCoord, entity.posY + 1.375, entity.posZ + leftarm.zCoord + forward.zCoord),
				new BoneDefinition(EnumSkeletonType.LIMB, -entity.renderYawOffset, -90, entity.posX - leftarm.xCoord + forward.xCoord, entity.posY + 1.375, entity.posZ - leftarm.zCoord + forward.zCoord),
				new BoneDefinition(EnumSkeletonType.LIMB, -entity.renderYawOffset, 0, entity.posX + leftleg.xCoord, entity.posY + 0.375, entity.posZ + leftleg.zCoord),
				new BoneDefinition(EnumSkeletonType.LIMB, -entity.renderYawOffset, 0, entity.posX - leftleg.xCoord, entity.posY + 0.375, entity.posZ - leftleg.zCoord),
		};
	};
	
	public static Function<EntityLivingBase, BoneDefinition[]> BONES_VILLAGER = (entity) -> {
		Vec3NT leftarm = new Vec3NT(0.375, 0, 0).rotateAroundYDeg(-entity.renderYawOffset);
		Vec3NT forward = new Vec3NT(0, 0, 0.25).rotateAroundYDeg(-entity.renderYawOffset);
		Vec3NT leftleg = new Vec3NT(0.125, 0, 0).rotateAroundYDeg(-entity.renderYawOffset);
		return new BoneDefinition[] {
				new BoneDefinition(EnumSkeletonType.SKULL_VILLAGER, -entity.rotationYawHead, entity.rotationPitch, entity.posX, entity.posY + 1.6875, entity.posZ),
				new BoneDefinition(EnumSkeletonType.TORSO, -entity.renderYawOffset, 0, entity.posX, entity.posY + 1, entity.posZ),
				new BoneDefinition(EnumSkeletonType.LIMB, -entity.renderYawOffset, -45, entity.posX + leftarm.xCoord + forward.xCoord, entity.posY + 1.125, entity.posZ + leftarm.zCoord + forward.zCoord),
				new BoneDefinition(EnumSkeletonType.LIMB, -entity.renderYawOffset, -45, entity.posX - leftarm.xCoord + forward.xCoord, entity.posY + 1.125, entity.posZ - leftarm.zCoord + forward.zCoord),
				new BoneDefinition(EnumSkeletonType.LIMB, -entity.renderYawOffset, 0, entity.posX + leftleg.xCoord, entity.posY + 0.375, entity.posZ + leftleg.zCoord),
				new BoneDefinition(EnumSkeletonType.LIMB, -entity.renderYawOffset, 0, entity.posX - leftleg.xCoord, entity.posY + 0.375, entity.posZ - leftleg.zCoord),
		};
	};
	
	public static Function<EntityLivingBase, BoneDefinition[]> BONES_DUMMY = (entity) -> {
		Vec3NT leftarm = new Vec3NT(0.375, 0, 0).rotateAroundYDeg(-entity.renderYawOffset);
		Vec3NT leftleg = new Vec3NT(0.125, 0, 0).rotateAroundYDeg(-entity.renderYawOffset);
		return new BoneDefinition[] {
				new BoneDefinition(EnumSkeletonType.SKULL, -entity.rotationYawHead, entity.rotationPitch, entity.posX, entity.posY + 1.75, entity.posZ),
				new BoneDefinition(EnumSkeletonType.TORSO, -entity.renderYawOffset, 0, entity.posX, entity.posY + 1.125, entity.posZ),
				new BoneDefinition(EnumSkeletonType.LIMB, -entity.renderYawOffset, 0, entity.posX + leftarm.xCoord, entity.posY + 1.125, entity.posZ + leftarm.zCoord),
				new BoneDefinition(EnumSkeletonType.LIMB, -entity.renderYawOffset, 0, entity.posX - leftarm.xCoord, entity.posY + 1.125, entity.posZ - leftarm.zCoord),
				new BoneDefinition(EnumSkeletonType.LIMB, -entity.renderYawOffset, 0, entity.posX + leftleg.xCoord, entity.posY + 0.375, entity.posZ + leftleg.zCoord),
				new BoneDefinition(EnumSkeletonType.LIMB, -entity.renderYawOffset, 0, entity.posX - leftleg.xCoord, entity.posY + 0.375, entity.posZ - leftleg.zCoord),
		};
	};
	
	public static void init() {
		skullanizer.put(EntityOtherPlayerMP.class.getSimpleName(), BONES_BIPED);
		skullanizer.put(EntityClientPlayerMP.class.getSimpleName(), BONES_BIPED);
		skullanizer.put(EntityPlayerSP.class.getSimpleName(), BONES_BIPED);

		skullanizer.put(EntityZombie.class.getSimpleName(), BONES_ZOMBIE);
		skullanizer.put(EntitySkeleton.class.getSimpleName(), BONES_ZOMBIE);
		skullanizer.put(EntityPigZombie.class.getSimpleName(), BONES_ZOMBIE);
		
		skullanizer.put(EntityVillager.class.getSimpleName(), BONES_VILLAGER);
		skullanizer.put(EntityWitch.class.getSimpleName(), BONES_VILLAGER);
		
		skullanizer.put(EntityDummy.class.getSimpleName(), BONES_DUMMY);

		//techguns compat, for some reason
		//not alwayss accurate because of variable arm position, but better than nothing
		skullanizer.put("ArmySoldier", BONES_ZOMBIE);
		skullanizer.put("PsychoSteve", BONES_ZOMBIE);
		skullanizer.put("SkeletonSoldier", BONES_ZOMBIE);
		skullanizer.put("ZombieFarmer", BONES_ZOMBIE);
		skullanizer.put("ZombieMiner", BONES_ZOMBIE);
		skullanizer.put("ZombiePigmanSoldier", BONES_ZOMBIE);
		skullanizer.put("ZombieSoldier", BONES_ZOMBIE);
	}
}
