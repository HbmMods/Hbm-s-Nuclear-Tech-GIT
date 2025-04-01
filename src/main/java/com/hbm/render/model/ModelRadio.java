package com.hbm.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelRadio extends ModelBase {

	ModelRenderer Box;
	ModelRenderer Plate;
	ModelRenderer Lever;

	public ModelRadio() {
		this.textureWidth = 32;
		this.textureHeight = 32;

		this.Box = new ModelRenderer(this, 0, 0);
		this.Box.addBox(0F, 0F, 0F, 8, 14, 4);
		this.Box.setRotationPoint(-4F, 9F, -12F);
		this.Box.setTextureSize(32, 32);
		this.Box.mirror = true;
		setRotation(this.Box, 0F, 0F, 0F);
		this.Plate = new ModelRenderer(this, 0, 18);
		this.Plate.addBox(0F, 0F, 0F, 7, 13, 1);
		this.Plate.setRotationPoint(-3.5F, 9.5F, -12.5F);
		this.Plate.setTextureSize(32, 32);
		this.Plate.mirror = true;
		setRotation(this.Plate, 0F, 0F, 0F);
		this.Lever = new ModelRenderer(this, 16, 18);
		this.Lever.addBox(0F, -1F, -1F, 2, 8, 2);
		this.Lever.setRotationPoint(4F, 16F, -10F);
		this.Lever.setTextureSize(32, 32);
		this.Lever.mirror = true;
		setRotation(this.Lever, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

		this.Box.render(scaleFactor);
		this.Plate.render(scaleFactor);
		this.Lever.render(scaleFactor);
	}

	public void renderModel(float scaleFactor, int rotation) {

		this.Box.render(scaleFactor);
		this.Plate.render(scaleFactor);
		this.Lever.rotateAngleX = -(float)(rotation / 180F * Math.PI);
		this.Lever.render(scaleFactor);
	}

	private static void setRotation(ModelRenderer model, float x, float y, float z) {

		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
