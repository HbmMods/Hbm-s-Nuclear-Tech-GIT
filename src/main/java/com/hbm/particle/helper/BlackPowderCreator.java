package com.hbm.particle.helper;

import java.util.Random;

import com.hbm.particle.ParticleBlackPowderSmoke;
import com.hbm.particle.ParticleBlackPowderSpark;
import com.hbm.util.Vec3NT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class BlackPowderCreator implements IParticleCreator {
	
	public static void composeEffect(World world, double x, double y, double z, double headingX, double headingY, double headingZ, int cloudCount, float cloudScale, float cloudSpeedMult, int sparkCount, float sparkSpeedMult) {
		
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "blackPowder");
		data.setInteger("cloudCount", cloudCount);
		data.setFloat("cloudScale", cloudScale);
		data.setFloat("cloudSpeedMult", cloudSpeedMult);
		data.setInteger("sparkCount", sparkCount);
		data.setFloat("sparkSpeedMult", sparkSpeedMult);
		data.setDouble("hX", headingX);
		data.setDouble("hY", headingY);
		data.setDouble("hZ", headingZ);
		IParticleCreator.sendPacket(world, x, y, z, 200, data);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void makeParticle(World world, EntityPlayer player, TextureManager texman, Random rand, double x, double y, double z, NBTTagCompound data) {

		double headingX = data.getDouble("hX");
		double headingY = data.getDouble("hY");
		double headingZ = data.getDouble("hZ");
		int cloudCount = data.getInteger("cloudCount");
		float cloudScale = data.getFloat("cloudScale");
		float cloudSpeedMult = data.getFloat("cloudSpeedMult");
		int sparkCount = data.getInteger("sparkCount");
		float sparkSpeedMult = data.getFloat("sparkSpeedMult");
		
		Vec3NT heading = new Vec3NT(headingX, headingY, headingZ).normalizeSelf();

		for(int i = 0; i < cloudCount; i++) {
			ParticleBlackPowderSmoke particle = new ParticleBlackPowderSmoke(world, x, y, z, cloudScale);
			double speedMult = 0.85 + rand.nextDouble() * 0.3;
			particle.motionX = heading.xCoord * cloudSpeedMult * speedMult + rand.nextGaussian() * 0.05;
			particle.motionY = heading.yCoord * cloudSpeedMult * speedMult + rand.nextGaussian() * 0.05;
			particle.motionZ = heading.zCoord * cloudSpeedMult * speedMult + rand.nextGaussian() * 0.05; 
			Minecraft.getMinecraft().effectRenderer.addEffect(particle);
		}

		for(int i = 0; i < sparkCount; i++) {
			double speedMult = 0.85 + rand.nextDouble() * 0.3;
			ParticleBlackPowderSpark particle = new ParticleBlackPowderSpark(world, x, y, z,
					heading.xCoord * sparkSpeedMult * speedMult + rand.nextGaussian() * 0.02,
					heading.yCoord * sparkSpeedMult * speedMult + rand.nextGaussian() * 0.02,
					heading.zCoord * sparkSpeedMult * speedMult + rand.nextGaussian() * 0.02);
			Minecraft.getMinecraft().effectRenderer.addEffect(particle);
		}
	}
}
