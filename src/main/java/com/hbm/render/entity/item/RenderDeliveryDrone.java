package com.hbm.render.entity.item;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.item.EntityDroneBase;
import com.hbm.entity.item.EntityRequestDrone;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderDeliveryDrone extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float f1) {

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		if(entity instanceof EntityRequestDrone) {
			bindTexture(ResourceManager.delivery_drone_request_tex);
		} else if(entity.getDataWatcher().getWatchableObjectByte(11) == 1)
			bindTexture(ResourceManager.delivery_drone_express_tex);
		else
			bindTexture(ResourceManager.delivery_drone_tex);
		ResourceManager.delivery_drone.renderPart("Drone");
		
		EntityDroneBase drone = (EntityDroneBase) entity;
		int style = drone.getAppearance();

		if(style == 1) ResourceManager.delivery_drone.renderPart("Crate");
		if(style == 2) ResourceManager.delivery_drone.renderPart("Barrel");
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_CULL_FACE);

		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity drone) {
		return ResourceManager.delivery_drone_tex;
	}
}
