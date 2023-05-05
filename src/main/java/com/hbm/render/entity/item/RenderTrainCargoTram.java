package com.hbm.render.entity.item;

import org.lwjgl.opengl.GL11;

import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderTrainCargoTram extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float swing, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);

		GL11.glRotated( -entity.rotationYaw, 0, 1, 0);
		GL11.glRotated(-entity.rotationPitch, 0, 0, 1);

		MainRegistry.proxy.displayTooltip("Render Yaw: " + entity.rotationYaw, 666);
		MainRegistry.proxy.displayTooltip("Render Pitch: " + entity.rotationPitch, 667);
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		bindTexture(ResourceManager.universal);
		ResourceManager.train_cargo_tram.renderAll();
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ResourceManager.universal;
	}
}
