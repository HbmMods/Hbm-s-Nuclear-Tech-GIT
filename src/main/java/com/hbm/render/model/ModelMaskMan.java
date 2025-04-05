package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.mob.EntityMaskMan;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelMaskMan extends ModelBase {

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

		GL11.glPushMatrix();

		GL11.glRotatef(180, 1, 0, 0);
		GL11.glTranslatef(0, -1.5F, 0);
		GL11.glRotatef(-90, 0, 1, 0);

		EntityMaskMan maskManEntity = (EntityMaskMan)entity;

		//boolean target = entity.worldObj.getEntityByID(man.getDataWatcher().getWatchableObjectInt(man.dwTargetPlayer)) != null;

		//if(target)
		//	GL11.glRotated(-f3, 0, 1, 0);

        float f7 = maskManEntity.limbSwing - maskManEntity.limbSwingAmount * (1.0F - scaleFactor);
        float f6 = (maskManEntity.prevLimbSwingAmount + (maskManEntity.limbSwingAmount - maskManEntity.prevLimbSwingAmount) * scaleFactor) * 0.5F;

        double swing = Math.toDegrees(MathHelper.cos(f7 / 2F + (float)Math.PI) * 1.4F * f6);

		GL11.glRotated(swing * -0.1, 1, 0, 0);

		ResourceManager.maskman.renderPart("Torso");

		GL11.glPushMatrix();
		GL11.glTranslatef(-0.5F, 1.75F, -0.5F);
		GL11.glRotated(swing, 0, 0, 1);
		ResourceManager.maskman.renderPart("LLeg");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslatef(-0.5F, 1.75F, 0.5F);
		GL11.glRotated(swing * -1, 0, 0, 1);
		ResourceManager.maskman.renderPart("RLeg");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslatef(-0.5F, 3.75F, -1.5F);
		GL11.glRotated(swing * 0.25, 0, 0, 1);
		ResourceManager.maskman.renderPart("LArm");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslatef(-0.5F, 3.75F, 1.5F);
		GL11.glRotated(swing * -0.25, 0, 0, 1);
		ResourceManager.maskman.renderPart("RArm");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslatef(0.5F, 4F, 0);
		GL11.glRotated(-netHeadYaw, 0, 1, 0);

		if(maskManEntity.getHealth() >= maskManEntity.getMaxHealth() / 2) {
			ResourceManager.maskman.renderPart("Head");
		} else {
			ResourceManager.maskman.renderPart("Skull");
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.iou);
			ResourceManager.maskman.renderPart("IOU");
		}
		GL11.glPopMatrix();

		GL11.glPopMatrix();
	}
}
