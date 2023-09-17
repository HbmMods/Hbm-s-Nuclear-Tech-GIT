package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderDeliveryDrone extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float f1) {

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.delivery_drone_tex);
		ResourceManager.delivery_drone.renderPart("Drone");
		ResourceManager.delivery_drone.renderPart("Barrel");
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return null;
	}
}
