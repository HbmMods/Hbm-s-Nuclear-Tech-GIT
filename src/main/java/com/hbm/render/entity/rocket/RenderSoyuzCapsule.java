package com.hbm.render.entity.rocket;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderSoyuzCapsule extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float i, float j) {

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		double intlet = j * 0.05D;

		long worldtime = entity.worldObj.getTotalWorldTime();
		double time = (worldtime * 0.05) % (Math.PI * 2) + intlet;
		double sine = Math.sin(time) * 5;
		double sin3 = Math.sin(time + Math.PI * 0.5) * 5;
		
		int height = 7;
		
		GL11.glTranslated(0.0F, height, 0.0F);
		GL11.glRotated(sine, 0, 0, 1);
		GL11.glRotated(sin3, 1, 0, 0);
		GL11.glTranslated(0.0F, -height, 0.0F);
		
		bindTexture(ResourceManager.soyuz_lander_tex);
		ResourceManager.soyuz_lander.renderPart("Capsule");
		
		bindTexture(ResourceManager.soyuz_chute_tex);
		ResourceManager.soyuz_lander.renderPart("Chute");
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return ResourceManager.soyuz_lander_tex;
	}
}
