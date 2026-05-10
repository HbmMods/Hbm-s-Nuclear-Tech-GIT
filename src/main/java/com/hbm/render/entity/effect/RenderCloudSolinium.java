package com.hbm.render.entity.effect;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.effect.EntityCloudSolinium;
import com.hbm.main.ResourceManager;
import com.hbm.util.ColorUtil;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderCloudSolinium extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		int color = 0x27FFDA;

		EntityCloudSolinium cloud = (EntityCloudSolinium) entity;
		float scale = cloud.age + interp;
		GL11.glScalef(scale, scale, scale);

		GL11.glColor3f(ColorUtil.fr(color), ColorUtil.fg(color), ColorUtil.fb(color));
		ResourceManager.sphere_new.renderAll();
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glColor4f(ColorUtil.fr(color), ColorUtil.fg(color), ColorUtil.fb(color), 0.125F);
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		double outerScale = 1.025;
		for(int i = 0; i < 3; i++) {
			GL11.glScaled(outerScale, outerScale, outerScale);
			ResourceManager.sphere_new.renderAll();
		}
		
		GL11.glColor4f(1F, 1F, 1F, 1F);

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return null;
	}
}
