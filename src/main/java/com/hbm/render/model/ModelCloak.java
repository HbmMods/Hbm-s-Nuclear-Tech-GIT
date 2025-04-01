package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class ModelCloak extends ModelBiped {

	public ModelCloak() {
		this.textureWidth = 64;
		this.textureHeight = 32;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {

		EntityPlayer player = (EntityPlayer) entity;
		this.isSneak = player.isSneaking();
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

		if(entity instanceof AbstractClientPlayer) {
			AbstractClientPlayer player = (AbstractClientPlayer) entity;

			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 0.0F, 0.125F);
			double d3 = player.field_71091_bM + (player.field_71094_bP - player.field_71091_bM) * scaleFactor - (player.prevPosX + (player.posX - player.prevPosX) * scaleFactor);
			double d4 = player.field_71096_bN + (player.field_71095_bQ - player.field_71096_bN) * scaleFactor - (player.prevPosY + (player.posY - player.prevPosY) * scaleFactor);
			double d0 = player.field_71097_bO + (player.field_71085_bR - player.field_71097_bO) * scaleFactor - (player.prevPosZ + (player.posZ - player.prevPosZ) * scaleFactor);
			float f4 = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * scaleFactor;
			double d1 = MathHelper.sin(f4 * (float) Math.PI / 180.0F);
			double d2 = (-MathHelper.cos(f4 * (float) Math.PI / 180.0F));
			float f5 = (float) d4 * 10.0F;

			if(f5 < -6.0F) {
				f5 = -6.0F;
			}

			if(f5 > 32.0F) {
				f5 = 32.0F;
			}

			float f6 = (float) (d3 * d1 + d0 * d2) * 100.0F;
			float f7 = (float) (d3 * d2 - d0 * d1) * 100.0F;

			if(f6 < 0.0F) {
				f6 = 0.0F;
			}

			float f8 = player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * scaleFactor;
			f5 += MathHelper.sin((player.prevDistanceWalkedModified + (player.distanceWalkedModified - player.prevDistanceWalkedModified) * scaleFactor) * 6.0F) * 32.0F * f8;

			if(player.isSneaking()) {
				f5 += 25.0F;
			}

			GL11.glRotatef(6.0F + f6 / 2.0F + f5, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(f7 / 2.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-f7 / 2.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			this.bipedCloak.render(scaleFactor);
			GL11.glPopMatrix();
		}
	}
}
