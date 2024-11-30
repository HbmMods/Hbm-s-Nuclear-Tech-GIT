package com.hbm.render.entity.item;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderC130 extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float interp) {

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * interp - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(90, 0F, 0F, 1F);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * interp, 0.0F, 0.0F, 1.0F);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.c130_0_tex);
		ResourceManager.c130.renderPart("Plane");
		
		double spin = System.currentTimeMillis() * 15D % 360D;
		
		GL11.glPushMatrix();
		GL11.glTranslated(10, 4.2, -20.5);
		GL11.glRotated(spin, 1, 0, 0);
		GL11.glTranslated(-10, -4.2, 20.5);
		ResourceManager.c130.renderPart("Prop1");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(10, 4.2, -11.16);
		GL11.glRotated(spin, 1, 0, 0);
		GL11.glTranslated(-10, -4.2, 11.16);
		ResourceManager.c130.renderPart("Prop2");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(10, 4.2, 11.16);
		GL11.glRotated(spin, 1, 0, 0);
		GL11.glTranslated(-10, -4.2, -11.16);
		ResourceManager.c130.renderPart("Prop3");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(10, 4.2, 20.5);
		GL11.glRotated(spin, 1, 0, 0);
		GL11.glTranslated(-10, -4.2, -20.5);
		ResourceManager.c130.renderPart("Prop4");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ResourceManager.c130_0_tex;
	}

}
