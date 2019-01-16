package com.hbm.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityBullet;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelBullet;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBullet extends Render {

	private ModelBullet bullet;

	public RenderBullet() {
		bullet = new ModelBullet();
	}

	@Override
	public void doRender(Entity bullet, double x, double y, double z, float f0, float f1) {
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(bullet.prevRotationYaw + (bullet.rotationYaw - bullet.prevRotationYaw) * f1 - 90.0F,
				0.0F, 1.0F, 0.0F);
		GL11.glRotatef(bullet.prevRotationPitch + (bullet.rotationPitch - bullet.prevRotationPitch) * f1 + 180,
				0.0F, 0.0F, 1.0F);
		GL11.glScalef(1.5F, 1.5F, 1.5F);
		
		GL11.glRotatef(new Random(bullet.getEntityId()).nextInt(360), 1.0F, 0.0F, 0.0F);
		
		renderFlechette();
		
		GL11.glPopMatrix();
	}
	
	private void renderBullet(int type) {

		if (type == 2) {
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/emplacer.png"));
		} else if (type == 1) {
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/tau.png"));
		} else if (type == 0) {
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/bullet.png"));
		}
		
		bullet.renderAll(0.0625F);
	}
	
	private void renderFlechette() {
		GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		
        GL11.glScalef(1F/16F, 1F/16F, 1F/16F);
        GL11.glScalef(-1, 1, 1);
        
		Tessellator tess = Tessellator.instance;
		
		//back
		GL11.glColor3f(0.15F, 0.15F, 0.15F);
		tess.startDrawingQuads();
		tess.addVertex(0, -1, -1);
		tess.addVertex(0, 1, -1);
		tess.addVertex(0, 1, 1);
		tess.addVertex(0, -1, 1);
		tess.draw();
		
		//base
		tess.startDrawingQuads();
		tess.addVertex(0, -1, -1);
		tess.addVertex(1, -0.5, -0.5);
		tess.addVertex(1, 0.5, -0.5);
		tess.addVertex(0, 1, -1);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertex(1, -0.5, 0.5);
		tess.addVertex(0, -1, 1);
		tess.addVertex(0, 1, 1);
		tess.addVertex(1, 0.5, 0.5);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertex(1, -0.5, -0.5);
		tess.addVertex(0, -1, -1);
		tess.addVertex(0, -1, 1);
		tess.addVertex(1, -0.5, 0.5);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertex(0, 1, -1);
		tess.addVertex(1, 0.5, -0.5);
		tess.addVertex(1, 0.5, 0.5);
		tess.addVertex(0, 1, 1);
		tess.draw();

		//pin
		tess.startDrawing(4);
		tess.addVertex(1, 0.5, -0.5);
		tess.addVertex(1, -0.5, -0.5);
		tess.addVertex(6, 0, 0);
		tess.draw();

		tess.startDrawing(4);
		tess.addVertex(6, 0, 0);
		tess.addVertex(1, -0.5, 0.5);
		tess.addVertex(1, 0.5, 0.5);
		tess.draw();

		tess.startDrawing(4);
		tess.addVertex(6, 0, 0);
		tess.addVertex(1, -0.5, -0.5);
		tess.addVertex(1, -0.5, 0.5);
		tess.draw();

		tess.startDrawing(4);
		tess.addVertex(1, 0.5, 0.5);
		tess.addVertex(1, 0.5, -0.5);
		tess.addVertex(6, 0, 0);
		tess.draw();
		

        GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glPopMatrix();
	}
	
	private void renderDart(float red, float green, float blue) {
		
		GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glDepthMask(false);

        GL11.glScalef(1F/4F, 1F/8F, 1F/8F);
        GL11.glScalef(-1, 1, 1);

        GL11.glScalef(2, 2, 2);
        
		Tessellator tess = Tessellator.instance;
		
		//front
		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(6, 0, 0);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(3, -1, -1);
		tess.addVertex(3, 1, -1);
		tess.draw();

		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(3, -1, 1);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(6, 0, 0);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(3, 1, 1);
		tess.draw();

		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(3, -1, -1);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(6, 0, 0);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(3, -1, 1);
		tess.draw();

		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(6, 0, 0);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(3, 1, -1);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(3, 1, 1);
		tess.draw();
		
		//mid
		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(6, 0, 0);
		tess.addVertex(4, -0.5, -0.5);
		tess.addVertex(4, 0.5, -0.5);
		tess.draw();

		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(4, -0.5, 0.5);
		tess.addVertex(6, 0, 0);
		tess.addVertex(4, 0.5, 0.5);
		tess.draw();

		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(4, -0.5, -0.5);
		tess.addVertex(6, 0, 0);
		tess.addVertex(4, -0.5, 0.5);
		tess.draw();

		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(6, 0, 0);
		tess.addVertex(4, 0.5, -0.5);
		tess.addVertex(4, 0.5, 0.5);
		tess.draw();
		
		//tail
		tess.startDrawingQuads();
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(4, 0.5, -0.5);
		tess.addVertex(4, 0.5, 0.5);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(0, 0.5, 0.5);
		tess.addVertex(0, 0.5, -0.5);
		tess.draw();

		tess.startDrawingQuads();
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(4, -0.5, -0.5);
		tess.addVertex(4, -0.5, 0.5);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(0, -0.5, 0.5);
		tess.addVertex(0, -0.5, -0.5);
		tess.draw();

		tess.startDrawingQuads();
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(4, -0.5, 0.5);
		tess.addVertex(4, 0.5, 0.5);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(0, 0.5, 0.5);
		tess.addVertex(0, -0.5, 0.5);
		tess.draw();

		tess.startDrawingQuads();
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(4, -0.5, -0.5);
		tess.addVertex(4, 0.5, -0.5);
		tess.setColorRGBA_F(red, green, blue, 0);
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

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return new ResourceLocation(RefStrings.MODID + ":textures/models/bullet.png");
	}

}
