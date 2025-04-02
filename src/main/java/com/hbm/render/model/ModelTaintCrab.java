package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelTaintCrab extends ModelBase {

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

		GL11.glPushMatrix();

		GL11.glRotatef(90, 0, -1, 0);
		GL11.glRotatef(180, 0, 0, 1);
		GL11.glTranslatef(0, -1.5F, 0);

		float rot = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount * 57.3F;

		ResourceManager.taintcrab.renderPart("Body");

		GL11.glPushMatrix();
		GL11.glRotatef(rot, 0, 1, 0);
		ResourceManager.taintcrab.renderPart("Legs1");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glRotatef(rot, 0, -1, 0);
		ResourceManager.taintcrab.renderPart("Legs2");
		GL11.glPopMatrix();

		GL11.glPopMatrix();
	}
}
