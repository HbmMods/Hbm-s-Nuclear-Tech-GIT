package com.hbm.particle;

import java.util.Random;

import com.hbm.main.ModEventHandlerClient;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleRocketFlame extends EntityFX {

	private TextureManager theRenderEngine;
	private int age;
	private int maxAge;

	public ParticleRocketFlame(TextureManager p_i1213_1_, World p_i1218_1_, double p_i1218_2_, double p_i1218_4_, double p_i1218_6_) {
		super(p_i1218_1_, p_i1218_2_, p_i1218_4_, p_i1218_6_);
		particleIcon = ModEventHandlerClient.particleBase;
		theRenderEngine = p_i1213_1_;
		maxAge = 300 + rand.nextInt(50);
	}

	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		this.age++;

		if (this.age == this.maxAge) {
			this.setDead();
		}

		this.motionX *= 0.9099999785423279D;
		this.motionY *= 0.9099999785423279D;
		this.motionZ *= 0.9099999785423279D;
		
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
	}

	public int getFXLayer() {
        return 1;
    }
 
	public void renderParticle(Tessellator p_70539_1_, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
	       
        Random urandom = new Random(this.getEntityId());
       
        for(int i = 0; i < 10; i++) {
           
            float add = urandom.nextFloat() * 0.3F;
            float dark = 1 - Math.min(((float)(age) / (float)(maxAge * 0.25F)), 1);
           
            this.particleRed = 1 * dark + add;
            this.particleGreen = 0.6F * dark + add;
            this.particleBlue = 0 + add;
           
            this.particleAlpha = (float) Math.pow(1 - Math.min(((float)(age) / (float)(maxAge)), 1), 0.5);
           
            p_70539_1_.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha * 0.75F);
            p_70539_1_.setNormal(0.0F, 1.0F, 0.0F);
            p_70539_1_.setBrightness(240);
           
            float spread = (float) Math.pow(((float)(age) / (float)maxAge) * 4F, 1.5) + 1F;
           
            float scale = urandom.nextFloat() * 0.5F + 0.1F + ((float)(age) / (float)maxAge) * 2F;
            float pX = (float) ((this.prevPosX + (this.posX - this.prevPosX) * (double)p_70539_2_ - interpPosX) + (urandom.nextGaussian() - 1D) * 0.2F * spread);
            float pY = (float) ((this.prevPosY + (this.posY - this.prevPosY) * (double)p_70539_2_ - interpPosY) + (urandom.nextGaussian() - 1D) * 0.5F * spread);
            float pZ = (float) ((this.prevPosZ + (this.posZ - this.prevPosZ) * (double)p_70539_2_ - interpPosZ) + (urandom.nextGaussian() - 1D) * 0.2F * spread);
           
            p_70539_1_.addVertexWithUV((double)(pX - p_70539_3_ * scale - p_70539_6_ * scale), (double)(pY - p_70539_4_ * scale), (double)(pZ - p_70539_5_ * scale - p_70539_7_ * scale), particleIcon.getMaxU(), particleIcon.getMaxV());
            p_70539_1_.addVertexWithUV((double)(pX - p_70539_3_ * scale + p_70539_6_ * scale), (double)(pY + p_70539_4_ * scale), (double)(pZ - p_70539_5_ * scale + p_70539_7_ * scale), particleIcon.getMaxU(), particleIcon.getMinV());
            p_70539_1_.addVertexWithUV((double)(pX + p_70539_3_ * scale + p_70539_6_ * scale), (double)(pY + p_70539_4_ * scale), (double)(pZ + p_70539_5_ * scale + p_70539_7_ * scale), particleIcon.getMinU(), particleIcon.getMinV());
            p_70539_1_.addVertexWithUV((double)(pX + p_70539_3_ * scale - p_70539_6_ * scale), (double)(pY - p_70539_4_ * scale), (double)(pZ + p_70539_5_ * scale - p_70539_7_ * scale), particleIcon.getMinU(), particleIcon.getMaxV());
        }
    }
}
