package com.hbm.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelShrapnel extends ModelBase {

	ModelRenderer bullet;

	public ModelShrapnel() {
		textureWidth = 16;
		textureHeight = 8;

		bullet = new ModelRenderer(this, 0, 0);
		bullet.addBox(0F, 0F, 0F, 4, 4, 4);
		bullet.setRotationPoint(1F, -0.5F, -0.5F);
		bullet.setTextureSize(16, 8);
		bullet.mirror = true;
		setRotation(bullet, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		bullet.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

	public void renderAll(float f5) {
		bullet.render(f5);
	}

}