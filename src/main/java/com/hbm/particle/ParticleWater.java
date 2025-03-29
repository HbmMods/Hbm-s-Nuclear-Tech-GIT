package com.hbm.particle;

import com.hbm.main.ModEventHandlerClient;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.awt.*;
import java.util.Random;

@SideOnly(Side.CLIENT)
public class ParticleWater extends EntityFX {

	private int age;
	public int maxAge;
	private float baseScale = 1.0F;
	private float maxScale = 1.0F;

	public ParticleWater(TextureManager p_i1213_1_, World p_i1218_1_, double p_i1218_2_, double p_i1218_4_, double p_i1218_6_) {
		super(p_i1218_1_, p_i1218_2_, p_i1218_4_, p_i1218_6_);
		particleIcon = ModEventHandlerClient.particleBase;
		maxAge = 10 + rand.nextInt(5);
		particleGravity = 0.2F;

		double speed = 0.1 + rand.nextDouble() *2;
		double theta = rand.nextDouble() * 2 * Math.PI;
		double phi = rand.nextDouble() * Math.PI / 2;

		double motionX1 = speed * Math.cos(theta) * Math.sin(phi);

		this.motionX = motionX1;
		this.motionZ = motionX1;
	}

	public void setBaseScale(float f) { this.baseScale = f; }
	public void setMaxScale(float f) { this.maxScale = f; }

	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		particleAlpha = 1 - ((float) age / (float) maxAge);

		++this.age;

		if (this.age == this.maxAge) {
			this.setDead();
		}

		if (this.age < maxAge / 4) {
			this.motionY = 4;
		} else {
			this.motionY -= 0.2 * (1 - (float) this.age /maxAge);
		}

		this.motionY -= this.particleGravity;

		this.motionX += rand.nextGaussian() * 0.2 * 2;
		this.motionZ += rand.nextGaussian() * 0.2 * 2;

        this.moveEntity(this.motionX, motionY, this.motionZ);

		motionX *= 0.925;
		motionY *= 0.925;
		motionZ *= 0.925;

		this.particleAge++;
		if(this.onGround || this.isInWeb) this.setDead();
	}

	public int getFXLayer() {
		return 1;
	}

	public void renderParticle(Tessellator p_70539_1_, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {

		Random urandom = new Random(this.getEntityId());

		for(int i = 0; i < 6; i++) {

	        this.particleRed = this.particleGreen = this.particleBlue = urandom.nextFloat() * 0.25F + 0.25F;

			p_70539_1_.setColorRGBA_F(this.particleRed, this.particleGreen, 185F, this.particleAlpha);
			p_70539_1_.setNormal(0.0F, 1.0F, 0.0F);

			float scale = urandom.nextFloat() + 0.5F;
	        float pX = (float) ((this.prevPosX + (this.posX - this.prevPosX) * (double)p_70539_2_ - interpPosX) + (urandom.nextGaussian() - 1D) * 0.75F);
	        float pY = (float) ((this.prevPosY + (this.posY - this.prevPosY) * (double)p_70539_2_ - interpPosY) + (urandom.nextGaussian() - 1D) * 0.75F);
	        float pZ = (float) ((this.prevPosZ + (this.posZ - this.prevPosZ) * (double)p_70539_2_ - interpPosZ) + (urandom.nextGaussian() - 1D) * 0.75F);

			p_70539_1_.addVertexWithUV((double)(pX - p_70539_3_ * scale - p_70539_6_ * scale), (double)(pY - p_70539_4_ * scale), (double)(pZ - p_70539_5_ * scale - p_70539_7_ * scale), particleIcon.getMaxU(), particleIcon.getMaxV());
			p_70539_1_.addVertexWithUV((double)(pX - p_70539_3_ * scale + p_70539_6_ * scale), (double)(pY + p_70539_4_ * scale), (double)(pZ - p_70539_5_ * scale + p_70539_7_ * scale), particleIcon.getMaxU(), particleIcon.getMinV());
			p_70539_1_.addVertexWithUV((double)(pX + p_70539_3_ * scale + p_70539_6_ * scale), (double)(pY + p_70539_4_ * scale), (double)(pZ + p_70539_5_ * scale + p_70539_7_ * scale), particleIcon.getMinU(), particleIcon.getMinV());
			p_70539_1_.addVertexWithUV((double)(pX + p_70539_3_ * scale - p_70539_6_ * scale), (double)(pY - p_70539_4_ * scale), (double)(pZ + p_70539_5_ * scale - p_70539_7_ * scale), particleIcon.getMinU(), particleIcon.getMaxV());
		}
	}
}
