package com.hbm.particle;

import java.awt.Color;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.world.World;

public class ParticleGasFlame extends EntitySmokeFX {
	
	float colorMod = 1.0F;

	public ParticleGasFlame(World world, double x, double y, double z, double mX, double mY, double mZ, float scale) {
		super(world, x, y, z, mX, mY * 1.5, mZ, scale);
		updateColor();
		this.particleScale = scale;
		this.colorMod = 0.8F + rand.nextFloat() * 0.2F;
		this.noClip = true;
		this.particleMaxAge = 30 + rand.nextInt(13);
	}

	@Override
	public void onUpdate() {
		double prevMo = this.motionY;
		super.onUpdate();
		updateColor();
		//this.motionX *= 0.75;
		//this.motionZ *= 0.75;
		this.motionY = prevMo;
		
		this.motionX *= 0.75D;
		this.motionY += 0.005D;
		this.motionZ *= 0.75D;
	}
	
	protected void updateColor() {
		float time = (float) this.particleAge / (float) this.particleMaxAge;
		
		Color color = Color.getHSBColor(Math.max((60 - time * 100) / 360F, 0.0F), 1 - time * 0.25F, 1 - time * 0.5F);
		
		this.particleRed = color.getRed() / 255F;
		this.particleGreen = color.getGreen() / 255F;
		this.particleBlue = color.getBlue() / 255F;

		this.particleRed *= colorMod;
		this.particleGreen *= colorMod;
		this.particleBlue *= colorMod;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_) {
		return 15728880;
	}
}
