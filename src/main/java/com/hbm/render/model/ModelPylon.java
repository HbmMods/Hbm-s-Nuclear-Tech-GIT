//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: Pylon
// Model Creator:
// Created on:13.06.2017 - 11:17:46
// Last changed on: 13.06.2017 - 11:17:46

package com.hbm.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPylon extends ModelBase {

	public ModelRenderer[] pylonModel;

	public ModelPylon() {
		this.textureWidth = 64;
		this.textureHeight = 128;

		this.pylonModel = new ModelRenderer[4];
		this.pylonModel[0] = new ModelRenderer(this, 0, 96); // Box 0
		this.pylonModel[1] = new ModelRenderer(this, 1, 1); // Box 1
		this.pylonModel[2] = new ModelRenderer(this, 24, 1); // Box 2
		this.pylonModel[3] = new ModelRenderer(this, 25, 17); // Box 3

		this.pylonModel[0].addBox(0F, 0F, 0F, 16, 16, 16, 0F); // Box 0
		this.pylonModel[0].setRotationPoint(-8F, -6F, -8F);

		this.pylonModel[1].addBox(0F, 0F, 0F, 4, 73, 4, 0F); // Box 1
		this.pylonModel[1].setRotationPoint(-2F, -79F, -2F);

		this.pylonModel[2].addBox(0F, 0F, 0F, 6, 4, 6, 0F); // Box 2
		this.pylonModel[2].setRotationPoint(-3F, -74F, -3F);

		this.pylonModel[3].addBox(0F, 0F, 0F, 6, 2, 6, 0F); // Box 3
		this.pylonModel[3].setRotationPoint(-3F, -78F, -3F);


		for (ModelRenderer modelRenderer : this.pylonModel) {
			modelRenderer.setTextureSize(this.textureWidth, this.textureHeight);
			modelRenderer.mirror = true;
		}
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

		for(int i = 0; i < 4; i++) {
			this.pylonModel[i].render(scaleFactor);
		}
	}

	public void renderAll(float scaleFactor) {

		for(int i = 0; i < 4; i++) {
			this.pylonModel[i].render(scaleFactor);
		}
	}
}
