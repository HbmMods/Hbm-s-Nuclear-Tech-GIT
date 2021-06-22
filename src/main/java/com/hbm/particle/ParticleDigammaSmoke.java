package com.hbm.particle;

import com.hbm.main.ModEventHandlerClient;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleDigammaSmoke extends EntityFX {

	private int age;
	public int maxAge;

	public ParticleDigammaSmoke(TextureManager p_i1213_1_, World p_i1218_1_, double p_i1218_2_, double p_i1218_4_, double p_i1218_6_) {
		super(p_i1218_1_, p_i1218_2_, p_i1218_4_, p_i1218_6_);
		particleIcon = ModEventHandlerClient.particleBase;
		maxAge = 100 + rand.nextInt(40);
		this.noClip = true;
		
		this.particleScale = 5;
		
		this.particleRed = 0.5F + rand.nextFloat() * 0.2F;
		this.particleGreen = 0.0F;
		this.particleBlue = 0.0F;
	}
	
	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		particleAlpha = 1 - ((float) age / (float) maxAge);

		++this.age;

		if(this.age == this.maxAge) {
			this.setDead();
		}

		this.motionX *= 0.99D;
		this.motionY *= 0.99D;
		this.motionZ *= 0.99D;

		this.moveEntity(this.motionX, this.motionY, this.motionZ);
	}
	
	@Override
	public int getFXLayer() {
		return 1;
	}
	
	@Override
	public void renderParticle(Tessellator tess, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {

		tess.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
		tess.setNormal(0.0F, 1.0F, 0.0F);

		float scale = this.particleScale;
		float pX = (float) ((this.prevPosX + (this.posX - this.prevPosX) * (double) p_70539_2_ - interpPosX));
		float pY = (float) ((this.prevPosY + (this.posY - this.prevPosY) * (double) p_70539_2_ - interpPosY));
		float pZ = (float) ((this.prevPosZ + (this.posZ - this.prevPosZ) * (double) p_70539_2_ - interpPosZ));

		tess.addVertexWithUV((double) (pX - p_70539_3_ * scale - p_70539_6_ * scale), (double) (pY - p_70539_4_ * scale), (double) (pZ - p_70539_5_ * scale - p_70539_7_ * scale), particleIcon.getMaxU(), particleIcon.getMaxV());
		tess.addVertexWithUV((double) (pX - p_70539_3_ * scale + p_70539_6_ * scale), (double) (pY + p_70539_4_ * scale), (double) (pZ - p_70539_5_ * scale + p_70539_7_ * scale), particleIcon.getMaxU(), particleIcon.getMinV());
		tess.addVertexWithUV((double) (pX + p_70539_3_ * scale + p_70539_6_ * scale), (double) (pY + p_70539_4_ * scale), (double) (pZ + p_70539_5_ * scale + p_70539_7_ * scale), particleIcon.getMinU(), particleIcon.getMinV());
		tess.addVertexWithUV((double) (pX + p_70539_3_ * scale - p_70539_6_ * scale), (double) (pY - p_70539_4_ * scale), (double) (pZ + p_70539_5_ * scale - p_70539_7_ * scale), particleIcon.getMinU(), particleIcon.getMaxV());
	}
	
	@Override
	public int getBrightnessForRender(float p_70070_1_) {
		return 240;
	}
}
