package com.hbm.render.entity.item;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.train.EntityRailCarBase;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderTunnelBore extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float swing, float interp) {
		GL11.glPushMatrix();
		
		EntityRailCarBase train = (EntityRailCarBase) entity;
		double iX = train.prevPosX + (train.posX - train.prevPosX) * interp;
		double iY = train.prevPosY + (train.posY - train.prevPosY) * interp;
		double iZ = train.prevPosZ + (train.posZ - train.prevPosZ) * interp;
		double rX = train.lastRenderX + (train.renderX - train.lastRenderX) * interp;
		double rY = train.lastRenderY + (train.renderY - train.lastRenderY) * interp;
		double rZ = train.lastRenderZ + (train.renderZ - train.lastRenderZ) * interp;
		x -= iX - rX;
		y -= iY - rY;
		z -= iZ - rZ;
		
		GL11.glTranslated(x, y - 0.0625D, z);

		float yaw = entity.rotationYaw;
		float prevYaw = entity.prevRotationYaw;

		if(yaw - prevYaw > 180) yaw -= 360;
		if(prevYaw - yaw > 180) prevYaw -= 360;
		
		float yawInterp = prevYaw + (yaw - prevYaw) * interp - 720;

		GL11.glRotated(-yawInterp, 0, 1, 0);

		float pitch = entity.rotationPitch;
		float prevPitch = entity.prevRotationPitch;
		float pitchInterp = prevPitch + (pitch - prevPitch) * interp;
		GL11.glRotated(-pitchInterp, 1, 0, 0);
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.universal);
		ResourceManager.tunnel_bore.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ResourceManager.universal;
	}
}
