package com.hbm.render.entity.projectile;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntitySiegeLaser;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderSiegeLaser extends Render {

	@Override
	public void doRender(Entity laser, double x, double y, double z, float f0, float f1) {
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(laser.prevRotationYaw + (laser.rotationYaw - laser.prevRotationYaw) * f1 - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(laser.prevRotationPitch + (laser.rotationPitch - laser.prevRotationPitch) * f1 + 180, 0.0F, 0.0F, 1.0F);
		
		this.renderDart((EntitySiegeLaser) laser);
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ResourceManager.universal;
	}
	
	private void renderDart(EntitySiegeLaser laser) {

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glDepthMask(false);

		GL11.glScalef(1F / 4F, 1F / 8F, 1F / 8F);
		GL11.glScalef(-1, 1, 1);

		GL11.glScalef(2, 2, 2);
		
		int color = laser.getColor();

		Tessellator tess = Tessellator.instance;

		// front
		tess.startDrawing(4);
		tess.setColorOpaque_I(color);
		tess.addVertex(6, 0, 0);
		tess.setColorRGBA_I(color, 0);
		tess.addVertex(3, -1, -1);
		tess.addVertex(3, 1, -1);

		tess.setColorRGBA_I(color, 0);
		tess.addVertex(3, -1, 1);
		tess.setColorOpaque_I(color);
		tess.addVertex(6, 0, 0);
		tess.setColorRGBA_I(color, 0);
		tess.addVertex(3, 1, 1);

		tess.setColorRGBA_I(color, 0);
		tess.addVertex(3, -1, -1);
		tess.setColorOpaque_I(color);
		tess.addVertex(6, 0, 0);
		tess.setColorRGBA_I(color, 0);
		tess.addVertex(3, -1, 1);

		tess.setColorOpaque_I(color);
		tess.addVertex(6, 0, 0);
		tess.setColorRGBA_I(color, 0);
		tess.addVertex(3, 1, -1);
		tess.setColorRGBA_I(color, 0);
		tess.addVertex(3, 1, 1);

		// mid
		tess.setColorOpaque_I(color);
		tess.addVertex(6, 0, 0);
		tess.addVertex(4, -0.5, -0.5);
		tess.addVertex(4, 0.5, -0.5);

		tess.setColorOpaque_I(color);
		tess.addVertex(4, -0.5, 0.5);
		tess.addVertex(6, 0, 0);
		tess.addVertex(4, 0.5, 0.5);

		tess.setColorOpaque_I(color);
		tess.addVertex(4, -0.5, -0.5);
		tess.addVertex(6, 0, 0);
		tess.addVertex(4, -0.5, 0.5);

		tess.setColorOpaque_I(color);
		tess.addVertex(6, 0, 0);
		tess.addVertex(4, 0.5, -0.5);
		tess.addVertex(4, 0.5, 0.5);
		tess.draw();

		// tail
		tess.startDrawingQuads();
		tess.setColorOpaque_I(color);
		tess.addVertex(4, 0.5, -0.5);
		tess.addVertex(4, 0.5, 0.5);
		tess.setColorRGBA_I(color, 0);
		tess.addVertex(0, 0.5, 0.5);
		tess.addVertex(0, 0.5, -0.5);

		tess.setColorOpaque_I(color);
		tess.addVertex(4, -0.5, -0.5);
		tess.addVertex(4, -0.5, 0.5);
		tess.setColorRGBA_I(color, 0);
		tess.addVertex(0, -0.5, 0.5);
		tess.addVertex(0, -0.5, -0.5);

		tess.setColorOpaque_I(color);
		tess.addVertex(4, -0.5, 0.5);
		tess.addVertex(4, 0.5, 0.5);
		tess.setColorRGBA_I(color, 0);
		tess.addVertex(0, 0.5, 0.5);
		tess.addVertex(0, -0.5, 0.5);

		tess.setColorOpaque_I(color);
		tess.addVertex(4, -0.5, -0.5);
		tess.addVertex(4, 0.5, -0.5);
		tess.setColorRGBA_I(color, 0);
		tess.addVertex(0, 0.5, -0.5);
		tess.addVertex(0, -0.5, -0.5);
		tess.draw();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDepthMask(true);

		GL11.glPopMatrix();
	}
}
