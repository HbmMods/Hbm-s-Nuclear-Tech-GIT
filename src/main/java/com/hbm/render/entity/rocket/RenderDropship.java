package com.hbm.render.entity.rocket;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.missile.EntitySatellitePod;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderDropship extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float f1) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		bindTexture(ResourceManager.dropship_tex);
		ResourceManager.dropship.renderPart("Pod");
		
		EntitySatellitePod pod = (EntitySatellitePod) entity;
		float legs = pod.prevLegs + (pod.legs - pod.prevLegs) * f1;

		for(int i = 0; i < 4; i++) {
			GL11.glPushMatrix();
			GL11.glRotated(45 + 90 * i, 0, 1, 0);
			GL11.glTranslated(0.5, 1.75, 0);
			GL11.glRotated(150 * (1F - legs), 0, 0, 1);
			GL11.glTranslated(-0.5, -1.75, 0);
			ResourceManager.dropship.renderPart("Leg");
			GL11.glPopMatrix();
		}

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ResourceManager.dropship_tex;
	}
}
