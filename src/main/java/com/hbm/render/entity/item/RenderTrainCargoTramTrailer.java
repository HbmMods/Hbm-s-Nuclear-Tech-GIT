package com.hbm.render.entity.item;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderTrainCargoTramTrailer extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float swing, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);

		float yaw = entity.rotationYaw;
		float prevYaw = entity.prevRotationYaw;

		if(yaw - prevYaw > 180) yaw -= 360;
		if(prevYaw - yaw > 180) prevYaw -= 360;
		
		float yawInterp = prevYaw + (yaw - prevYaw) * interp - 720;

		GL11.glRotated(-yawInterp, 0, 1, 0);
		GL11.glRotated(-entity.rotationPitch, 0, 0, 1);
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		bindTexture(ResourceManager.tram_trailer);
		ResourceManager.train_cargo_tram_trailer.renderAll();
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ResourceManager.tram_trailer;
	}
}
