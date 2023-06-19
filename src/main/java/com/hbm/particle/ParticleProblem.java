package com.hbm.particle;

import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;


public class ParticleProblem extends EntityFX {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/entity/theproblem.png");
	private TextureManager theRenderEngine;
	private int age;
	private int maxAge;

	public ParticleProblem(TextureManager p_i1213_1_, World p_i1218_1_, double p_i1218_2_, double p_i1218_4_, double p_i1218_6_) {
		super(p_i1218_1_, p_i1218_2_, p_i1218_4_, p_i1218_6_);
		theRenderEngine = p_i1213_1_;
		maxAge = 100 + rand.nextInt(40);
		
        this.particleRed = this.particleGreen = this.particleBlue = 0;
        this.particleScale = 4.5F;
	}

	public ParticleProblem(TextureManager p_i1213_1_, World p_i1218_1_, double p_i1218_2_, double p_i1218_4_, double p_i1218_6_, float red, float green, float blue, float scale) {
		super(p_i1218_1_, p_i1218_2_, p_i1218_4_, p_i1218_6_);
		theRenderEngine = p_i1213_1_;
		maxAge = 100 + rand.nextInt(40);

        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;
        
        this.particleScale = scale;
	}

	public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        
        if(maxAge < 400)
        {
        	maxAge = 400;
        }

        this.particleAge++;
        
        if (this.particleAge >= maxAge)
        {
            this.setDead();
        }

        this.motionX *= 0.9599999785423279D;
        this.motionY *= 0.9599999785423279D;
        this.motionZ *= 0.9599999785423279D;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }
	}	

	public int getFXLayer() {
		return 3;
	}

	public void renderParticle(Tessellator tess, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
		
		this.theRenderEngine.bindTexture(texture);
		

        GL11.glColor4f(0.85F, 0.9F, 0.5F, 1);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		RenderHelper.disableStandardItemLighting();
		
		Random rand = new Random(50);
		
		for(int i = 0; i < 1; i++) {

			double dX = (rand.nextGaussian() - 1D) * 2.5D;
			double dY = (rand.nextGaussian() - 1D) * 0.15D;
			double dZ = (rand.nextGaussian() - 1D) * 2.5D;
			double size = rand.nextDouble() * particleScale;
	        
			GL11.glTranslatef((float) dX, (float) dY, (float) dZ);
			
	        float pX = (float) ((this.prevPosX + (this.posX - this.prevPosX) * (double)p_70539_2_ - interpPosX) + rand.nextGaussian() * 0.5);
	        float pY = (float) ((this.prevPosY + (this.posY - this.prevPosY) * (double)p_70539_2_ - interpPosY) + rand.nextGaussian() * 0.5);
	        float pZ = (float) ((this.prevPosZ + (this.posZ - this.prevPosZ) * (double)p_70539_2_ - interpPosZ) + rand.nextGaussian() * 0.5);
			
			tess.startDrawingQuads();
			tess.setNormal(0.0F, 1.0F, 0.0F);
			tess.setBrightness(240);
			tess.addVertexWithUV((double)(pX - p_70539_3_ * size - p_70539_6_ * size), (double)(pY - p_70539_4_ * size), (double)(pZ - p_70539_5_ * size - p_70539_7_ * size), 1, 1);
			tess.addVertexWithUV((double)(pX - p_70539_3_ * size + p_70539_6_ * size), (double)(pY + p_70539_4_ * size), (double)(pZ - p_70539_5_ * size + p_70539_7_ * size), 1, 0);
			tess.addVertexWithUV((double)(pX + p_70539_3_ * size + p_70539_6_ * size), (double)(pY + p_70539_4_ * size), (double)(pZ + p_70539_5_ * size + p_70539_7_ * size), 0, 0);
			tess.addVertexWithUV((double)(pX + p_70539_3_ * size - p_70539_6_ * size), (double)(pY - p_70539_4_ * size), (double)(pZ + p_70539_5_ * size - p_70539_7_ * size), 0, 1);
			tess.draw();
			
			GL11.glTranslatef((float) -dX, (float) -dY, (float) -dZ);
		}
		
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthMask(true);
		
		GL11.glPolygonOffset(0.0F, 0.0F);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
}
