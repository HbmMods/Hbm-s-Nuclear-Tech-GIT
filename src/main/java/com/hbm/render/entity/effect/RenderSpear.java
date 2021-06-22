package com.hbm.render.entity.effect;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderSpear extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f, float interp) {
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y + 15, (float) z);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glRotated(180, 1, 0, 0);
		GL11.glScaled(2, 2, 2);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.lance_tex);
		ResourceManager.lance.renderPart("Spear");
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ResourceManager.lance_tex;
	}
}
