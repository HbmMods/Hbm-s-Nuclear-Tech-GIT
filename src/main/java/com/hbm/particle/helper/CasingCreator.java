package com.hbm.particle.helper;

import java.util.Random;

import com.hbm.particle.ParticleSpentCasing;
import com.hbm.particle.SpentCasing;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class CasingCreator implements IParticleCreator {
	
	/** Default casing without smoke */
	public static void composeEffect(World world, EntityLivingBase player, double frontOffset, double heightOffset, double sideOffset, double frontMotion, double heightMotion, double sideMotion, double motionVariance, String casing) {
		composeEffect(world, player, frontOffset, heightOffset, sideOffset, frontMotion, heightMotion, sideMotion, motionVariance, 5F, 10F, casing, false, 0, 0, 0);
	}
	
	/** Casing without smoke */
	public static void composeEffect(World world, EntityLivingBase player, double frontOffset, double heightOffset, double sideOffset, double frontMotion, double heightMotion, double sideMotion, double motionVariance, float multPitch, float multYaw, String casing) {
		composeEffect(world, player, frontOffset, heightOffset, sideOffset, frontMotion, heightMotion, sideMotion, motionVariance, multPitch, multYaw, casing, false, 0, 0, 0);
	}
	
	/** Default casing, but with smoke*/
	public static void composeEffect(World world, EntityLivingBase player, double frontOffset, double heightOffset, double sideOffset, double frontMotion, double heightMotion, double sideMotion, double motionVariance, String casing, boolean smoking, int smokeLife, double smokeLift, int nodeLife) {
		composeEffect(world, player, frontOffset, heightOffset, sideOffset, frontMotion, heightMotion, sideMotion, motionVariance, 5F, 10F, casing, false, 0, 0, 0);
	}

	public static void composeEffect(World world, EntityLivingBase player, double frontOffset, double heightOffset, double sideOffset, double frontMotion, double heightMotion, double sideMotion, double motionVariance, float mPitch, float mYaw, String casing, boolean smoking, int smokeLife, double smokeLift, int nodeLife) {
		
		if(player.isSneaking()) heightOffset -= 0.075F;
		
		Vec3 offset = Vec3.createVectorHelper(sideOffset, heightOffset, frontOffset);
		offset.rotateAroundX(-player.rotationPitch / 180F * (float) Math.PI);
		offset.rotateAroundY(-player.rotationYaw / 180F * (float) Math.PI);
		
		double x = player.posX + offset.xCoord;
		double y = player.posY + player.getEyeHeight() + offset.yCoord;
		double z = player.posZ + offset.zCoord;
		
		Vec3 motion = Vec3.createVectorHelper(sideMotion, heightMotion, frontMotion);
		motion.rotateAroundX(-player.rotationPitch / 180F * (float) Math.PI);
		motion.rotateAroundY(-player.rotationYaw / 180F * (float) Math.PI);

		double mX = player.motionX + motion.xCoord + player.getRNG().nextGaussian() * motionVariance;
		double mY = player.motionY + motion.yCoord + player.getRNG().nextGaussian() * motionVariance;
		double mZ = player.motionZ + motion.zCoord + player.getRNG().nextGaussian() * motionVariance;
		
		if(player instanceof EntityPlayer && ((EntityPlayer) player).capabilities.isFlying) mY -= 0.04D;
		
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "casingNT");
		data.setDouble("mX", mX);
		data.setDouble("mY", mY);
		data.setDouble("mZ", mZ);
		data.setFloat("yaw", player.rotationYaw);
		data.setFloat("pitch", player.rotationPitch);
		data.setFloat("mPitch", mPitch);
		data.setFloat("mYaw", mYaw);
		data.setString("name", casing);
		data.setBoolean("smoking", smoking);
		data.setInteger("smokeLife", smokeLife);
		data.setDouble("smokeLift", smokeLift);
		data.setInteger("nodeLife", nodeLife);
		
		IParticleCreator.sendPacket(world, x, y, z, 50, data);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void makeParticle(World world, EntityPlayer player, TextureManager texman, Random rand, double x, double y, double z, NBTTagCompound data) {

		String name = data.getString("name");
		SpentCasing casingConfig = SpentCasing.casingMap.get(name);
		double mX = data.getDouble("mX");
		double mY = data.getDouble("mY");
		double mZ = data.getDouble("mZ");
		float yaw = data.getFloat("yaw");
		float pitch = data.getFloat("pitch");
		float mPitch = data.getFloat("mPitch");
		float mYaw = data.getFloat("mYaw");
		boolean smoking = data.getBoolean("smoking");
		int smokeLife = data.getInteger("smokeLife");
		double smokeLift = data.getDouble("smokeLift");
		int nodeLife = data.getInteger("nodeLife");
		ParticleSpentCasing casing = new ParticleSpentCasing(texman, world, x, y, z, mX, mY, mZ, mPitch, mYaw, casingConfig, smoking, smokeLife, smokeLift, nodeLife);
		casing.prevRotationYaw = casing.rotationYaw = yaw;
		casing.prevRotationPitch = casing.rotationPitch = pitch;
		Minecraft.getMinecraft().effectRenderer.addEffect(casing);
	}
}
