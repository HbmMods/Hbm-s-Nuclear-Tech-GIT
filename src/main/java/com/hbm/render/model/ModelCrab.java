//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: Crab
// Model Creator:
// Created on:07.06.2017 - 08:57:57
// Last changed on: 07.06.2017 - 08:57:57

package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelCrab extends ModelBase {

	public ModelRenderer[] crabModel;

	public ModelCrab() {
		this.textureWidth = 64;
		this.textureHeight = 32;

		this.crabModel = new ModelRenderer[20];
		this.crabModel[0] = new ModelRenderer(this, 1, 1); // Box 1
		this.crabModel[1] = new ModelRenderer(this, 17, 1); // Box 2
		this.crabModel[2] = new ModelRenderer(this, 33, 1); // Box 3
		this.crabModel[3] = new ModelRenderer(this, 49, 1); // Box 4
		this.crabModel[4] = new ModelRenderer(this, 1, 9); // Box 5
		this.crabModel[5] = new ModelRenderer(this, 25, 9); // Box 6
		this.crabModel[6] = new ModelRenderer(this, 41, 9); // Box 7
		this.crabModel[7] = new ModelRenderer(this, 1, 17); // Box 8
		this.crabModel[8] = new ModelRenderer(this, 17, 17); // Box 9
		this.crabModel[9] = new ModelRenderer(this, 57, 9); // Box 10
		this.crabModel[10] = new ModelRenderer(this, 33, 17); // Box 11
		this.crabModel[11] = new ModelRenderer(this, 41, 17); // Box 12
		this.crabModel[12] = new ModelRenderer(this, 49, 17); // Box 13
		this.crabModel[13] = new ModelRenderer(this, 17, 1); // Box 14
		this.crabModel[14] = new ModelRenderer(this, 33, 9); // Box 15
		this.crabModel[15] = new ModelRenderer(this, 49, 9); // Box 16
		this.crabModel[16] = new ModelRenderer(this, 9, 17); // Box 17
		this.crabModel[17] = new ModelRenderer(this, 1, 25); // Box 18
		this.crabModel[18] = new ModelRenderer(this, 17, 25); // Box 19
		this.crabModel[19] = new ModelRenderer(this, 33, 25); // Box 20

		this.crabModel[0].addBox(0F, 0F, 0F, 4, 1, 4, 0F); // Box 1
		this.crabModel[0].setRotationPoint(-2F, -3F, -2F);

		this.crabModel[1].addBox(0F, 0F, 0F, 4, 1, 6, 0F); // Box 2
		this.crabModel[1].setRotationPoint(-2F, -4F, -3F);

		this.crabModel[2].addBox(0F, 0F, 0F, 3, 1, 3, 0F); // Box 3
		this.crabModel[2].setRotationPoint(-1.5F, -5F, -1.5F);

		this.crabModel[3].addBox(0F, 0F, 0F, 4, 1, 2, 0F); // Box 4
		this.crabModel[3].setRotationPoint(-2F, -4.5F, -1F);

		this.crabModel[4].addBox(0F, 0F, 0F, 6, 1, 4, 0F); // Box 5
		this.crabModel[4].setRotationPoint(-3F, -4F, -2F);

		this.crabModel[5].addBox(-0.5F, 0F, 2F, 1, 1, 3, 0F); // Leg 6
		this.crabModel[5].setRotationPoint(0F, -3F, 0F);
		this.crabModel[5].rotateAngleX = -0.17453293F;
		this.crabModel[5].rotateAngleY = 0.78539816F;
		this.crabModel[10].addBox(-0.5F, 1F, 4F, 1, 3, 1, 0F); // Foot 11
		this.crabModel[10].setRotationPoint(0F, -3F, 0F);
		this.crabModel[10].rotateAngleX = 0.17453293F;
		this.crabModel[10].rotateAngleY = 0.78539816F;

		this.crabModel[6].addBox(-0.5F, 0F, 2F, 1, 1, 3, 0F); // Leg 7
		this.crabModel[6].setRotationPoint(0F, -3F, 0F);
		this.crabModel[6].rotateAngleX = -0.17453293F;
		this.crabModel[6].rotateAngleY = -0.78539816F;
		this.crabModel[9].addBox(-0.5F, 1F, 4F, 1, 3, 1, 0F); // Foot 10
		this.crabModel[9].setRotationPoint(0F, -3F, 0F);
		this.crabModel[9].rotateAngleX = 0.17453293F;
		this.crabModel[9].rotateAngleY = -0.78539816F;

		this.crabModel[7].addBox(-0.5F, 0F, 2F, 1, 1, 3, 0F); // Leg 8
		this.crabModel[7].setRotationPoint(0F, -3F, 0F);
		this.crabModel[7].rotateAngleX = -0.17453293F;
		this.crabModel[7].rotateAngleY = -2.35619449F;
		this.crabModel[11].addBox(-0.5F, 1F, 4F, 1, 3, 1, 0F); // Foot 12
		this.crabModel[11].setRotationPoint(0F, -3F, 0F);
		this.crabModel[11].rotateAngleX = 0.17453293F;
		this.crabModel[11].rotateAngleY = -2.35619449F;

		this.crabModel[8].addBox(-0.5F, 0F, 2F, 1, 1, 3, 0F); // Leg 9
		this.crabModel[8].setRotationPoint(0F, -3F, 0F);
		this.crabModel[8].rotateAngleX = -0.17453293F;
		this.crabModel[8].rotateAngleY = 2.35619449F;
		this.crabModel[12].addBox(-0.5F, 1F, 4F, 1, 3, 1, 0F); // Foot 13
		this.crabModel[12].setRotationPoint(0F, -3F, 0F);
		this.crabModel[12].rotateAngleX = 0.17453293F;
		this.crabModel[12].rotateAngleY = 2.35619449F;

		this.crabModel[13].addBox(-0.5F, 0F, 1.5F, 1, 1, 1, 0F); // Fang 14
		this.crabModel[13].setRotationPoint(0F, -3F, 0F);
		this.crabModel[13].rotateAngleX = -0.43633231F;
		this.crabModel[13].rotateAngleY = -0.6981317F;

		this.crabModel[14].addBox(-0.5F, 0F, 1.5F, 1, 1, 1, 0F); // Fang 15
		this.crabModel[14].setRotationPoint(0F, -3F, 0F);
		this.crabModel[14].rotateAngleX = -0.43633231F;
		this.crabModel[14].rotateAngleY = 0.87266463F;

		this.crabModel[15].addBox(-0.5F, 0F, 1.5F, 1, 1, 1, 0F); // Fang 16
		this.crabModel[15].setRotationPoint(0F, -3F, 0F);
		this.crabModel[15].rotateAngleX = -0.43633231F;
		this.crabModel[15].rotateAngleY = -2.26892803F;

		this.crabModel[16].addBox(-0.5F, 0F, 1.5F, 1, 1, 1, 0F); // Fang 17
		this.crabModel[16].setRotationPoint(0F, -3F, 0F);
		this.crabModel[16].rotateAngleX = -0.43633231F;
		this.crabModel[16].rotateAngleY = 2.44346095F;

		this.crabModel[17].addBox(0F, 0F, 0F, 2, 1, 4, 0F); // Box 18
		this.crabModel[17].setRotationPoint(-1F, -4.5F, -2F);

		this.crabModel[18].addBox(0F, 0F, 0F, 5, 1, 3, 0F); // Box 19
		this.crabModel[18].setRotationPoint(-2.5F, -3.5F, -1.5F);

		this.crabModel[19].addBox(0F, 0F, 0F, 3, 1, 5, 0F); // Box 20
		this.crabModel[19].setRotationPoint(-1.5F, -3.5F, -2.5F);

		for (int i = 0; i < 20; i++) {
			this.crabModel[i].setTextureSize(this.textureWidth, this.textureHeight);
			this.crabModel[i].mirror = true;
		}
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {

		this.crabModel[10].rotateAngleY = 0.78539816F;
		this.crabModel[9].rotateAngleY = -0.78539816F;
		this.crabModel[11].rotateAngleY = -2.35619449F;
		this.crabModel[12].rotateAngleY = 2.35619449F;
		this.crabModel[5].rotateAngleY = this.crabModel[10].rotateAngleY;
		this.crabModel[6].rotateAngleY = this.crabModel[9].rotateAngleY;
		this.crabModel[7].rotateAngleY = this.crabModel[11].rotateAngleY;
		this.crabModel[8].rotateAngleY = this.crabModel[12].rotateAngleY;
		float f9 = (-(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount) * 1.5F;
		//float f10 = -(MathHelper.cos(f * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * f1;
		//float f11 = -(MathHelper.cos(f * 0.6662F * 2.0F + ((float) Math.PI / 2F)) * 0.4F) * f1;
		//float f12 = -(MathHelper.cos(f * 0.6662F * 2.0F + ((float) Math.PI * 3F / 2F)) * 0.4F) * f1;
		//float f13 = Math.abs(MathHelper.sin(f * 0.6662F + 0.0F) * 0.4F) * f1;
		//float f14 = Math.abs(MathHelper.sin(f * 0.6662F + (float) Math.PI) * 0.4F) * f1;
		//float f15 = Math.abs(MathHelper.sin(f * 0.6662F + ((float) Math.PI / 2F)) * 0.4F) * f1;
		//float f16 = Math.abs(MathHelper.sin(f * 0.6662F + ((float) Math.PI * 3F / 2F)) * 0.4F) * f1;
		this.crabModel[10].rotateAngleY += f9;
		this.crabModel[9].rotateAngleY -= f9;
		this.crabModel[11].rotateAngleY -= f9;
		this.crabModel[12].rotateAngleY += f9;
		this.crabModel[5].rotateAngleY = this.crabModel[10].rotateAngleY;
		this.crabModel[6].rotateAngleY = this.crabModel[9].rotateAngleY;
		this.crabModel[7].rotateAngleY = this.crabModel[11].rotateAngleY;
		this.crabModel[8].rotateAngleY = this.crabModel[12].rotateAngleY;
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

		this.renderAll(scaleFactor);
	}

	public void renderAll(float scaleFactor) {

		GL11.glPushMatrix();
		GL11.glTranslatef(0, 1.5F, 0);
		GL11.glRotatef(-90, 0, 1, 0);
		for (int i = 0; i < 20; i++) {
			this.crabModel[i].render(scaleFactor);
		}
		GL11.glPopMatrix();
	}
}
