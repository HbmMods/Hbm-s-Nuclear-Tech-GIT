package com.hbm.render.entity.item;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.train.EntityRailCarBase;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderTrainCargoTramTrailer extends Render {

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
		
		GL11.glTranslated(x, y, z);

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
		bindTexture(ResourceManager.tram_trailer);
		ResourceManager.train_cargo_tram_trailer.renderAll();
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		int slots = entity.getDataWatcher().getWatchableObjectInt(10);
		
		if(slots > 0) {

			EntityItem dummy = new EntityItem(entity.worldObj, 0, 0, 0, new ItemStack(ModBlocks.crate));
			dummy.hoverStart = 0.0F;

			RenderItem.renderInFrame = true;
			double scale = 2;
			GL11.glScaled(scale, scale, scale);
			
			if(slots <= 5) {
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.0D, 0.375D, 0.0D, 0.0F, 0.0F);
			} else if(slots <= 10) {
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.1D, 0.375D, 0.25D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, -0.1D, 0.375D, -0.25D, 0.0F, 0.0F);
			} else if(slots <= 15) {
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.1D, 0.375D, 0.0D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, -0.1D, 0.375D, 0.375D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, -0.1D, 0.375D, -0.375D, 0.0F, 0.0F);
			} else if(slots <= 20) {
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.2D, 0.375D, 0.3D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.2D, 0.375D, -0.2D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, -0.2D, 0.375D, 0.2D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, -0.2D, 0.375D, -0.3D, 0.0F, 0.0F);
			} else if(slots <= 25) {
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.2D, 0.375D, 0.6D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.2D, 0.375D, 0.0D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.2D, 0.375D, -0.5D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, -0.2D, 0.375D, 0.2D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, -0.2D, 0.375D, -0.3D, 0.0F, 0.0F);
			} else if(slots <= 30) {
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.2D, 0.375D, 0.6D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.2D, 0.375D, 0.0D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.2D, 0.375D, -0.5D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, -0.2D, 0.375D, 0.5D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, -0.2D, 0.375D, -0.1D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, -0.2D, 0.375D, -0.6D, 0.0F, 0.0F);
			} else if(slots <= 35) {
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.2D, 0.375D, 0.4D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.2D, 0.375D, 0.0D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.2D, 0.375D, -0.4D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, -0.2D, 0.375D, 0.3D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, -0.2D, 0.375D, -0.1D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, -0.2D, 0.375D, -0.5D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.0D, 0.6875D, -0.25D, 0.0F, 0.0F);
			} else if(slots <= 40) {
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.2D, 0.375D, 0.4D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.2D, 0.375D, 0.0D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.2D, 0.375D, -0.4D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, -0.2D, 0.375D, 0.3D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, -0.2D, 0.375D, -0.1D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, -0.2D, 0.375D, -0.5D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.0D, 0.6875D, -0.25D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.0D, 0.6875D, 0.15D, 0.0F, 0.0F);
			} else {
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.2D, 0.375D, 0.4D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.2D, 0.375D, 0.0D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.2D, 0.375D, -0.4D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, -0.2D, 0.375D, 0.3D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, -0.2D, 0.375D, -0.1D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, -0.2D, 0.375D, -0.5D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.0D, 0.6875D, -0.25D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, 0.0D, 0.6875D, 0.15D, 0.0F, 0.0F);
				RenderManager.instance.renderEntityWithPosYaw(dummy, -0.1D, 0.375D, 0.8D, 0.0F, 0.0F);
			}
			
			RenderItem.renderInFrame = false;
		}
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ResourceManager.tram_trailer;
	}
}
