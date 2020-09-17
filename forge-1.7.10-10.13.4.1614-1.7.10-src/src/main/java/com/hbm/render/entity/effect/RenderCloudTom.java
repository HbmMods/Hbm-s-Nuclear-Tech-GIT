package com.hbm.render.entity.effect;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.effect.EntityCloudTom;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderCloudTom extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float f1) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		
		EntityCloudTom blast = (EntityCloudTom)entity;
		
		double scale = blast.age + f0;
		
		int segments = 16;
		float angle = (float) Math.toRadians(360D/segments);
		int height = 20;
		int depth = 20;
		
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		
		bindTexture(this.getEntityTexture(blast));
		
        GL11.glMatrixMode(GL11.GL_TEXTURE);
        GL11.glLoadIdentity();

    	float movement = -(Minecraft.getMinecraft().thePlayer.ticksExisted + f0) * 0.005F * 10;
    	GL11.glTranslatef(0, movement, 0);
        
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		for(int i = 0; i < segments; i++) {
			
			for(int j = 0; j < 5; j++) {
				
				double mod = 1 - j * 0.025;
				double h = height + j * 10;
				double off = 1D / j;
				
				Vec3 vec = Vec3.createVectorHelper(scale, 0, 0);
				vec.rotateAroundY(angle * i);
				double x0 = vec.xCoord * mod;
				double z0 = vec.zCoord * mod;

				tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, 0.0F);
				tess.addVertexWithUV(x0, h, z0, 0, 1 + off);
				tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
				tess.addVertexWithUV(x0, -depth, z0, 0, 0 + off);
				
				vec.rotateAroundY(angle);
				x0 = vec.xCoord * mod;
				z0 = vec.zCoord * mod;

				tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
				tess.addVertexWithUV(x0, -depth, z0, 1, 0 + off);
				tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, 0.0F);
				tess.addVertexWithUV(x0, h, z0, 1, 1 + off);
			}
		}
		
		tess.draw();
		
        GL11.glMatrixMode(GL11.GL_TEXTURE);
        GL11.glDepthMask(true);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glDepthMask(true);
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ResourceManager.tomblast;
	}

}
