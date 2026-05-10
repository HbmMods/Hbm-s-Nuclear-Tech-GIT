package com.hbm.render.entity.effect;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderCloudFleija extends Render {
	
	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float interp) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		EntityCloudFleija cloud = (EntityCloudFleija) entity;
		double baseScale = (cloud.age + interp) * 2;
		double ageScale = baseScale / cloud.getMaxAge();

		GL11.glPushMatrix(); {
			double scale = ageScale * 1.2;
			if(scale > 1) scale = Math.max(1 - (scale - 1) * 5, 0);
			scale *= 2 * baseScale;
			GL11.glScaled(scale, scale, scale);
			
			GL11.glColor3f(0F, 1F, 1F);
			ResourceManager.sphere_new.renderAll();
			
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			
			GL11.glColor3f(0F, 0.125F, 0.125F);
			double outerScale = 1.05;
			for(int i = 0; i < 3; i++) {
				GL11.glScaled(outerScale, outerScale, outerScale);
				ResourceManager.sphere_new.renderAll();
			}
		} GL11.glPopMatrix();

		GL11.glPushMatrix(); {
			double shockwave = 5 * baseScale;
			GL11.glScaled(shockwave, shockwave, shockwave);
			float shockTint = (1F - (float) ageScale) * 0.75F;
			GL11.glColor3f(shockTint, shockTint, shockTint);
			ResourceManager.sphere_new.renderAll();
		} GL11.glPopMatrix();

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

	@Override protected ResourceLocation getEntityTexture(Entity entity) { return null; }
}
