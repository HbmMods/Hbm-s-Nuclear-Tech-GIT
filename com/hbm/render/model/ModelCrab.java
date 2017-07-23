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
	int textureX = 64;
	int textureY = 32;

	public ModelRenderer modelcrabModel[];

	public ModelCrab() {
		this.textureWidth = this.textureX;
		this.textureHeight = this.textureY;
		modelcrabModel = new ModelRenderer[20];
		modelcrabModel[0] = new ModelRenderer(this, 1, 1); // Box 1
		modelcrabModel[1] = new ModelRenderer(this, 17, 1); // Box 2
		modelcrabModel[2] = new ModelRenderer(this, 33, 1); // Box 3
		modelcrabModel[3] = new ModelRenderer(this, 49, 1); // Box 4
		modelcrabModel[4] = new ModelRenderer(this, 1, 9); // Box 5
		modelcrabModel[5] = new ModelRenderer(this, 25, 9); // Box 6
		modelcrabModel[6] = new ModelRenderer(this, 41, 9); // Box 7
		modelcrabModel[7] = new ModelRenderer(this, 1, 17); // Box 8
		modelcrabModel[8] = new ModelRenderer(this, 17, 17); // Box 9
		modelcrabModel[9] = new ModelRenderer(this, 57, 9); // Box 10
		modelcrabModel[10] = new ModelRenderer(this, 33, 17); // Box 11
		modelcrabModel[11] = new ModelRenderer(this, 41, 17); // Box 12
		modelcrabModel[12] = new ModelRenderer(this, 49, 17); // Box 13
		modelcrabModel[13] = new ModelRenderer(this, 17, 1); // Box 14
		modelcrabModel[14] = new ModelRenderer(this, 33, 9); // Box 15
		modelcrabModel[15] = new ModelRenderer(this, 49, 9); // Box 16
		modelcrabModel[16] = new ModelRenderer(this, 9, 17); // Box 17
		modelcrabModel[17] = new ModelRenderer(this, 1, 25); // Box 18
		modelcrabModel[18] = new ModelRenderer(this, 17, 25); // Box 19
		modelcrabModel[19] = new ModelRenderer(this, 33, 25); // Box 20

		modelcrabModel[0].addBox(0F, 0F, 0F, 4, 1, 4, 0F); // Box 1
		modelcrabModel[0].setRotationPoint(-2F, -3F, -2F);

		modelcrabModel[1].addBox(0F, 0F, 0F, 4, 1, 6, 0F); // Box 2
		modelcrabModel[1].setRotationPoint(-2F, -4F, -3F);

		modelcrabModel[2].addBox(0F, 0F, 0F, 3, 1, 3, 0F); // Box 3
		modelcrabModel[2].setRotationPoint(-1.5F, -5F, -1.5F);

		modelcrabModel[3].addBox(0F, 0F, 0F, 4, 1, 2, 0F); // Box 4
		modelcrabModel[3].setRotationPoint(-2F, -4.5F, -1F);

		modelcrabModel[4].addBox(0F, 0F, 0F, 6, 1, 4, 0F); // Box 5
		modelcrabModel[4].setRotationPoint(-3F, -4F, -2F);

		modelcrabModel[5].addBox(-0.5F, 0F, 2F, 1, 1, 3, 0F); // Leg 6
		modelcrabModel[5].setRotationPoint(0F, -3F, 0F);
		modelcrabModel[5].rotateAngleX = -0.17453293F;
		modelcrabModel[5].rotateAngleY = 0.78539816F;
		modelcrabModel[10].addBox(-0.5F, 1F, 4F, 1, 3, 1, 0F); // Foot 11
		modelcrabModel[10].setRotationPoint(0F, -3F, 0F);
		modelcrabModel[10].rotateAngleX = 0.17453293F;
		modelcrabModel[10].rotateAngleY = 0.78539816F;

		modelcrabModel[6].addBox(-0.5F, 0F, 2F, 1, 1, 3, 0F); // Leg 7
		modelcrabModel[6].setRotationPoint(0F, -3F, 0F);
		modelcrabModel[6].rotateAngleX = -0.17453293F;
		modelcrabModel[6].rotateAngleY = -0.78539816F;
		modelcrabModel[9].addBox(-0.5F, 1F, 4F, 1, 3, 1, 0F); // Foot 10
		modelcrabModel[9].setRotationPoint(0F, -3F, 0F);
		modelcrabModel[9].rotateAngleX = 0.17453293F;
		modelcrabModel[9].rotateAngleY = -0.78539816F;

		modelcrabModel[7].addBox(-0.5F, 0F, 2F, 1, 1, 3, 0F); // Leg 8
		modelcrabModel[7].setRotationPoint(0F, -3F, 0F);
		modelcrabModel[7].rotateAngleX = -0.17453293F;
		modelcrabModel[7].rotateAngleY = -2.35619449F;
		modelcrabModel[11].addBox(-0.5F, 1F, 4F, 1, 3, 1, 0F); // Foot 12
		modelcrabModel[11].setRotationPoint(0F, -3F, 0F);
		modelcrabModel[11].rotateAngleX = 0.17453293F;
		modelcrabModel[11].rotateAngleY = -2.35619449F;

		modelcrabModel[8].addBox(-0.5F, 0F, 2F, 1, 1, 3, 0F); // Leg 9
		modelcrabModel[8].setRotationPoint(0F, -3F, 0F);
		modelcrabModel[8].rotateAngleX = -0.17453293F;
		modelcrabModel[8].rotateAngleY = 2.35619449F;
		modelcrabModel[12].addBox(-0.5F, 1F, 4F, 1, 3, 1, 0F); // Foot 13
		modelcrabModel[12].setRotationPoint(0F, -3F, 0F);
		modelcrabModel[12].rotateAngleX = 0.17453293F;
		modelcrabModel[12].rotateAngleY = 2.35619449F;

		modelcrabModel[13].addBox(-0.5F, 0F, 1.5F, 1, 1, 1, 0F); // Fang 14
		modelcrabModel[13].setRotationPoint(0F, -3F, 0F);
		modelcrabModel[13].rotateAngleX = -0.43633231F;
		modelcrabModel[13].rotateAngleY = -0.6981317F;

		modelcrabModel[14].addBox(-0.5F, 0F, 1.5F, 1, 1, 1, 0F); // Fang 15
		modelcrabModel[14].setRotationPoint(0F, -3F, 0F);
		modelcrabModel[14].rotateAngleX = -0.43633231F;
		modelcrabModel[14].rotateAngleY = 0.87266463F;

		modelcrabModel[15].addBox(-0.5F, 0F, 1.5F, 1, 1, 1, 0F); // Fang 16
		modelcrabModel[15].setRotationPoint(0F, -3F, 0F);
		modelcrabModel[15].rotateAngleX = -0.43633231F;
		modelcrabModel[15].rotateAngleY = -2.26892803F;

		modelcrabModel[16].addBox(-0.5F, 0F, 1.5F, 1, 1, 1, 0F); // Fang 17
		modelcrabModel[16].setRotationPoint(0F, -3F, 0F);
		modelcrabModel[16].rotateAngleX = -0.43633231F;
		modelcrabModel[16].rotateAngleY = 2.44346095F;

		modelcrabModel[17].addBox(0F, 0F, 0F, 2, 1, 4, 0F); // Box 18
		modelcrabModel[17].setRotationPoint(-1F, -4.5F, -2F);

		modelcrabModel[18].addBox(0F, 0F, 0F, 5, 1, 3, 0F); // Box 19
		modelcrabModel[18].setRotationPoint(-2.5F, -3.5F, -1.5F);

		modelcrabModel[19].addBox(0F, 0F, 0F, 3, 1, 5, 0F); // Box 20
		modelcrabModel[19].setRotationPoint(-1.5F, -3.5F, -2.5F);

		for (int i = 0; i < 20; i++) {
			modelcrabModel[i].setTextureSize(textureX, textureY);
			modelcrabModel[i].mirror = true;
		}

	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		renderAll(f5);
	}

	public void renderAll(float f5) {
		GL11.glPushMatrix();
		GL11.glTranslatef(0, 1.5F, 0);
		GL11.glRotatef(-90, 0, 1, 0);
		for (int i = 0; i < 20; i++) {
			modelcrabModel[i].render(f5);
		}
		GL11.glPopMatrix();
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		
		modelcrabModel[10].rotateAngleY = 0.78539816F;
		modelcrabModel[9].rotateAngleY = -0.78539816F;
		modelcrabModel[11].rotateAngleY = -2.35619449F;
		modelcrabModel[12].rotateAngleY = 2.35619449F;
		modelcrabModel[5].rotateAngleY = modelcrabModel[10].rotateAngleY;
		modelcrabModel[6].rotateAngleY = modelcrabModel[9].rotateAngleY;
		modelcrabModel[7].rotateAngleY = modelcrabModel[11].rotateAngleY;
		modelcrabModel[8].rotateAngleY = modelcrabModel[12].rotateAngleY;
		float f9 = -(MathHelper.cos(f * 0.6662F * 2.0F + 0.0F) * 0.4F) * f1;
		//float f10 = -(MathHelper.cos(f * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * f1;
		//float f11 = -(MathHelper.cos(f * 0.6662F * 2.0F + ((float) Math.PI / 2F)) * 0.4F) * f1;
		//float f12 = -(MathHelper.cos(f * 0.6662F * 2.0F + ((float) Math.PI * 3F / 2F)) * 0.4F) * f1;
		//float f13 = Math.abs(MathHelper.sin(f * 0.6662F + 0.0F) * 0.4F) * f1;
		//float f14 = Math.abs(MathHelper.sin(f * 0.6662F + (float) Math.PI) * 0.4F) * f1;
		//float f15 = Math.abs(MathHelper.sin(f * 0.6662F + ((float) Math.PI / 2F)) * 0.4F) * f1;
		//float f16 = Math.abs(MathHelper.sin(f * 0.6662F + ((float) Math.PI * 3F / 2F)) * 0.4F) * f1;
		f9 *= 1.5;
		modelcrabModel[10].rotateAngleY += f9;
		modelcrabModel[9].rotateAngleY -= f9;
		modelcrabModel[11].rotateAngleY -= f9;
		modelcrabModel[12].rotateAngleY += f9;
		modelcrabModel[5].rotateAngleY = modelcrabModel[10].rotateAngleY;
		modelcrabModel[6].rotateAngleY = modelcrabModel[9].rotateAngleY;
		modelcrabModel[7].rotateAngleY = modelcrabModel[11].rotateAngleY;
		modelcrabModel[8].rotateAngleY = modelcrabModel[12].rotateAngleY;
	}
}