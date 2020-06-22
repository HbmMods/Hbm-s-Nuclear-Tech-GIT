package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelTeslaCrab extends ModelBase {

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		
		GL11.glPushMatrix();

		GL11.glRotatef(180, 0, 0, 1);
		GL11.glTranslatef(0, -1.5F, 0);

		float rot = -(MathHelper.cos(f * 0.6662F * 2.0F + 0.0F) * 0.4F) * f1 * 57.3F;
		
		ResourceManager.teslacrab.renderPart("Body");

		GL11.glPushMatrix();
		GL11.glRotatef(rot, 0, 1, 0);
		ResourceManager.teslacrab.renderPart("Front");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glRotatef(rot, 0, -1, 0);
		ResourceManager.teslacrab.renderPart("Back");
		GL11.glPopMatrix();
		
		GL11.glPopMatrix();
	}

}
