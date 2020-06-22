package com.hbm.render.entity.effect;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.entity.particle.EntityModFX;
import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class FogRenderer extends Render {

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glDisable(GL11.GL_LIGHTING);
		//GL11.glScalef(0.5F, 0.5F, 0.5F);
		GL11.glScalef(7.5F, 7.5F, 7.5F);
		
		////
		//Random randy = new Random(p_76986_1_.hashCode());
		//double d = randy.nextInt(10) * 0.05;
		//GL11.glColor3d(1 - d, 1 - d, 1 - d);
		////
        
		
		EntityModFX particle = (EntityModFX)p_76986_1_;
		
		float alpha = 0;
		
		alpha = (float) Math.sin(particle.particleAge * Math.PI / (400F)) * 0.25F;

		GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(0.85F, 0.9F, 0.5F, alpha);
        //GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
		
		Random rand = new Random(50);
		
		for(int i = 0; i < 25; i++) {

			double dX = (rand.nextGaussian() - 1D) * 0.5D;
			double dY = (rand.nextGaussian() - 1D) * 0.15D;
			double dZ = (rand.nextGaussian() - 1D) * 0.5D;
			double size = rand.nextDouble() * 0.5D + 0.25D;
	        
			GL11.glTranslatef((float) dX, (float) dY, (float) dZ);

	        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
	        GL11.glRotatef(180 - this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
	        
			GL11.glScaled(size, size, size);

			GL11.glPushMatrix();
			this.bindEntityTexture(p_76986_1_);
			Tessellator tess = Tessellator.instance;
			
			tess.startDrawingQuads();
			tess.addVertexWithUV(-1, -1, 0, 1, 0);
			tess.addVertexWithUV(-1, 1, 0, 0, 0);
			tess.addVertexWithUV(1, 1, 0, 0, 1);
			tess.addVertexWithUV(1, -1, 0, 1, 1);
			tess.draw();
			
			GL11.glPopMatrix();

			GL11.glScaled(1/size, 1/size, 1/size);

	        GL11.glRotatef(-180 + this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
	        GL11.glRotatef(-180.0F + this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef((float) -dX, (float) -dY, (float) -dZ);
		}
		
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_LIGHTING);
        //GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return new ResourceLocation(RefStrings.MODID + ":" + "textures/particle/fog.png");
	}

}
