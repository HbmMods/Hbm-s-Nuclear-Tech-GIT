//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: Defabricator
// Model Creator:
// Created on:05.06.2017 - 11:21:40
// Last changed on: 05.06.2017 - 11:21:40

package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDefabricator extends ModelBase
{
	int textureX = 128;
	int textureY = 64;
	ModelRenderer[] modeldefabricatorModel;

	public ModelDefabricator()
	{
	    textureWidth = 128;
	    textureHeight = 64;
		modeldefabricatorModel = new ModelRenderer[40];
		modeldefabricatorModel[0] = new ModelRenderer(this, 1, 1); // Box 0
		modeldefabricatorModel[1] = new ModelRenderer(this, 25, 1); // Box 1
		modeldefabricatorModel[2] = new ModelRenderer(this, 41, 1); // Box 2
		modeldefabricatorModel[3] = new ModelRenderer(this, 57, 1); // Box 3
		modeldefabricatorModel[4] = new ModelRenderer(this, 89, 1); // Box 4
		modeldefabricatorModel[5] = new ModelRenderer(this, 105, 1); // Box 5
		modeldefabricatorModel[6] = new ModelRenderer(this, 1, 9); // Box 6
		modeldefabricatorModel[7] = new ModelRenderer(this, 25, 9); // Box 7
		modeldefabricatorModel[8] = new ModelRenderer(this, 49, 1); // Box 8
		modeldefabricatorModel[9] = new ModelRenderer(this, 57, 9); // Box 9
		modeldefabricatorModel[10] = new ModelRenderer(this, 33, 9); // Box 10
		modeldefabricatorModel[11] = new ModelRenderer(this, 89, 9); // Box 11
		modeldefabricatorModel[12] = new ModelRenderer(this, 113, 9); // Box 12
		modeldefabricatorModel[13] = new ModelRenderer(this, 17, 1); // Box 13
		modeldefabricatorModel[14] = new ModelRenderer(this, 1, 17); // Box 14
		modeldefabricatorModel[15] = new ModelRenderer(this, 17, 17); // Box 15
		modeldefabricatorModel[16] = new ModelRenderer(this, 41, 17); // Box 16
		modeldefabricatorModel[17] = new ModelRenderer(this, 73, 17); // Box 17
		modeldefabricatorModel[18] = new ModelRenderer(this, 97, 17); // Box 18
		modeldefabricatorModel[19] = new ModelRenderer(this, 113, 17); // Box 19
		modeldefabricatorModel[20] = new ModelRenderer(this, 1, 25); // Box 20
		modeldefabricatorModel[21] = new ModelRenderer(this, 9, 25); // Box 21
		modeldefabricatorModel[22] = new ModelRenderer(this, 17, 25); // Box 22
		modeldefabricatorModel[23] = new ModelRenderer(this, 33, 25); // Box 23
		modeldefabricatorModel[24] = new ModelRenderer(this, 49, 25); // Box 24
		modeldefabricatorModel[25] = new ModelRenderer(this, 65, 25); // Box 25
		modeldefabricatorModel[26] = new ModelRenderer(this, 81, 25); // Box 26
		modeldefabricatorModel[27] = new ModelRenderer(this, 97, 1); // Box 27
		modeldefabricatorModel[28] = new ModelRenderer(this, 1, 33); // Box 28
		modeldefabricatorModel[29] = new ModelRenderer(this, 105, 17); // Box 29
		modeldefabricatorModel[30] = new ModelRenderer(this, 89, 25); // Box 30
		modeldefabricatorModel[31] = new ModelRenderer(this, 49, 33); // Box 31
		modeldefabricatorModel[32] = new ModelRenderer(this, 1, 41); // Box 32
		modeldefabricatorModel[33] = new ModelRenderer(this, 89, 25); // Box 33
		modeldefabricatorModel[34] = new ModelRenderer(this, 89, 33); // Box 34
		modeldefabricatorModel[35] = new ModelRenderer(this, 41, 41); // Box 35
		modeldefabricatorModel[36] = new ModelRenderer(this, 105, 25); // Box 36
		modeldefabricatorModel[37] = new ModelRenderer(this, 113, 25); // Box 39
		modeldefabricatorModel[38] = new ModelRenderer(this, 121, 25); // Box 40
		modeldefabricatorModel[39] = new ModelRenderer(this, 57, 41); // Box 41

		modeldefabricatorModel[0].addBox(0F, 0F, 0F, 5, 2, 3); // Box 0
		modeldefabricatorModel[0].setRotationPoint(0F, 0.5F, -1.5F);

		modeldefabricatorModel[1].addBox(0F, 0F, 0F, 5, 3, 2); // Box 1
		modeldefabricatorModel[1].setRotationPoint(0F, 0F, -1F);

		modeldefabricatorModel[2].addBox(0F, 0F, 0F, 2, 7, 3); // Box 2
		modeldefabricatorModel[2].setRotationPoint(-2F, -2F, -1.5F);

		modeldefabricatorModel[3].addBox(0F, 0F, 0F, 11, 1, 3); // Box 3
		modeldefabricatorModel[3].setRotationPoint(0F, -2F, -1.5F);

		modeldefabricatorModel[4].addBox(0F, 0F, 0F, 1, 3, 3); // Box 4
		modeldefabricatorModel[4].setRotationPoint(10F, -1F, -1.5F);

		modeldefabricatorModel[5].addBox(0F, 0F, 0F, 6, 1, 2); // Box 5
		modeldefabricatorModel[5].setRotationPoint(0F, 4F, -1F);

		modeldefabricatorModel[6].addBox(0F, -1F, 0F, 6, 1, 2); // Box 6
		modeldefabricatorModel[6].setRotationPoint(6F, 5F, -1F);
		modeldefabricatorModel[6].rotateAngleZ = -0.61086524F;

		modeldefabricatorModel[7].addBox(0F, 0F, 0F, 1, 2, 2); // Box 7
		modeldefabricatorModel[7].setRotationPoint(5F, 0.5F, -1F);

		modeldefabricatorModel[8].addBox(0F, 0F, 0F, 4, 1, 1); // Box 8
		modeldefabricatorModel[8].setRotationPoint(6F, 1F, -0.5F);

		modeldefabricatorModel[9].addBox(0F, 0F, 0F, 12, 1, 2); // Box 9
		modeldefabricatorModel[9].setRotationPoint(11F, 0F, -1F);

		modeldefabricatorModel[10].addBox(0F, 0F, 0F, 1, 6, 2); // Box 10
		modeldefabricatorModel[10].setRotationPoint(22F, -6F, -1F);

		modeldefabricatorModel[11].addBox(0F, 0F, 0F, 8, 2, 2); // Box 11
		modeldefabricatorModel[11].setRotationPoint(14F, -2.5F, -1F);

		modeldefabricatorModel[12].addBox(0F, 0F, 0F, 3, 1, 1); // Box 12
		modeldefabricatorModel[12].setRotationPoint(11F, -2F, -0.5F);

		modeldefabricatorModel[13].addBox(0F, 0F, 0F, 1, 1, 1); // Box 13
		modeldefabricatorModel[13].setRotationPoint(1F, 3.5F, -0.5F);

		modeldefabricatorModel[14].addBox(0F, 0F, 0F, 5, 1, 2); // Box 14
		modeldefabricatorModel[14].setRotationPoint(13.5F, -4.5F, -1F);

		modeldefabricatorModel[15].addBox(0F, 0F, 0F, 4, 1, 2); // Box 15
		modeldefabricatorModel[15].setRotationPoint(18F, -5F, -1F);

		modeldefabricatorModel[16].addBox(0F, 0F, 0F, 10, 1, 2); // Box 16
		modeldefabricatorModel[16].setRotationPoint(4F, -5F, -1F);

		modeldefabricatorModel[17].addBox(-6F, 0F, 0F, 6, 1, 2); // Box 17
		modeldefabricatorModel[17].setRotationPoint(4F, -5F, -1F);
		modeldefabricatorModel[17].rotateAngleZ = -0.52359878F;

		modeldefabricatorModel[18].addBox(0F, 0F, 0F, 1, 3, 3); // Box 18
		modeldefabricatorModel[18].setRotationPoint(22F, -9F, -1.5F);

		modeldefabricatorModel[19].addBox(0F, 0F, 0F, 5, 2, 2); // Box 19
		modeldefabricatorModel[19].setRotationPoint(17F, -8.5F, -1F);

		modeldefabricatorModel[20].addBox(-4F, -3F, 0F, 1, 3, 2); // Box 20
		modeldefabricatorModel[20].setRotationPoint(4F, -5F, -1F);
		modeldefabricatorModel[20].rotateAngleZ = -0.52359878F;

		modeldefabricatorModel[21].addBox(-4F, -4F, 0F, 1, 1, 2); // Box 21
		modeldefabricatorModel[21].setRotationPoint(4F, -5F, -1F);
		modeldefabricatorModel[21].rotateAngleZ = -0.52359878F;

		modeldefabricatorModel[22].addBox(0F, 0F, 0F, 4, 3, 2); // Box 22
		modeldefabricatorModel[22].setRotationPoint(-6F, 0F, -1F);

		modeldefabricatorModel[23].addBox(0F, 0F, 0F, 4, 2, 3); // Box 23
		modeldefabricatorModel[23].setRotationPoint(-6F, 0.5F, -1.5F);

		modeldefabricatorModel[24].addBox(0F, 0F, 0F, 5, 2, 2); // Box 24
		modeldefabricatorModel[24].setRotationPoint(-11F, 0.5F, -1F);

		modeldefabricatorModel[25].addBox(0F, 0F, 0F, 2, 3, 2); // Box 25
		modeldefabricatorModel[25].setRotationPoint(-10.5F, 0F, -1F);

		modeldefabricatorModel[26].addBox(0F, 0F, 0F, 2, 2, 3); // Box 26
		modeldefabricatorModel[26].setRotationPoint(-10.5F, 0.5F, -1.5F);

		modeldefabricatorModel[27].addBox(0F, -2.5F, -0.5F, 1, 1, 1); // Box 27
		modeldefabricatorModel[27].setRotationPoint(-3F, 1.5F, 0F);

		modeldefabricatorModel[28].addBox(0F, -3F, -0.5F, 20, 1, 1); // Box 28
		modeldefabricatorModel[28].setRotationPoint(-23F, 1.5F, 0F);

		modeldefabricatorModel[29].addBox(0F, -2.5F, -0.5F, 1, 1, 1); // Box 29
		modeldefabricatorModel[29].setRotationPoint(-3F, 1.5F, 0F);
		modeldefabricatorModel[29].rotateAngleX = 2.0943951F;

		modeldefabricatorModel[30].addBox(0F, -2.5F, -0.5F, 1, 1, 1); // Box 30
		modeldefabricatorModel[30].setRotationPoint(-3F, 1.5F, 0F);
		modeldefabricatorModel[30].rotateAngleX = -2.0943951F;

		modeldefabricatorModel[31].addBox(0F, -3F, -0.5F, 20, 1, 1); // Box 31
		modeldefabricatorModel[31].setRotationPoint(-23F, 1.5F, 0F);
		modeldefabricatorModel[31].rotateAngleX = -2.0943951F;

		modeldefabricatorModel[32].addBox(0F, -3F, -0.5F, 20, 1, 1); // Box 32
		modeldefabricatorModel[32].setRotationPoint(-23F, 1.5F, 0F);
		modeldefabricatorModel[32].rotateAngleX = 2.0943951F;

		modeldefabricatorModel[33].addBox(0F, -2F, -3F, 2, 1, 6); // Box 33
		modeldefabricatorModel[33].setRotationPoint(-22F, 1.5F, 0F);
		modeldefabricatorModel[33].rotateAngleX = 1.04719755F;

		modeldefabricatorModel[34].addBox(0F, -2F, -3F, 2, 1, 6); // Box 34
		modeldefabricatorModel[34].setRotationPoint(-22F, 1.5F, 0F);
		modeldefabricatorModel[34].rotateAngleX = -3.14159265F;

		modeldefabricatorModel[35].addBox(0F, -2F, -3F, 2, 1, 6); // Box 35
		modeldefabricatorModel[35].setRotationPoint(-22F, 1.5F, 0F);
		modeldefabricatorModel[35].rotateAngleX = -1.04719755F;

		modeldefabricatorModel[36].addBox(0.5F, -2.5F, -0.5F, 1, 1, 1); // Box 36
		modeldefabricatorModel[36].setRotationPoint(-24F, 1.5F, 0F);

		modeldefabricatorModel[37].addBox(0.5F, -2.5F, -0.5F, 1, 1, 1); // Box 39
		modeldefabricatorModel[37].setRotationPoint(-24F, 1.5F, 0F);
		modeldefabricatorModel[37].rotateAngleX = 2.0943951F;

		modeldefabricatorModel[38].addBox(0.5F, -2.5F, -0.5F, 1, 1, 1); // Box 40
		modeldefabricatorModel[38].setRotationPoint(-24F, 1.5F, 0F);
		modeldefabricatorModel[38].rotateAngleX = -2.0943951F;

		modeldefabricatorModel[39].addBox(0F, 0F, 0F, 13, 0, 1); // Box 41
		modeldefabricatorModel[39].setRotationPoint(5F, -5F, -0.5F);
		modeldefabricatorModel[39].rotateAngleZ = -0.17453293F;
		
		for(int i = 0; i < 40; i++)
		{
			modeldefabricatorModel[i].setTextureSize(textureX, textureY);
			modeldefabricatorModel[i].mirror = true;
		}
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		for(int i = 0; i < 40; i++)
		{
			if(i == 20)
				GL11.glDisable(GL11.GL_CULL_FACE);
			modeldefabricatorModel[i].render(f5);
			if(i == 20)
				GL11.glEnable(GL11.GL_CULL_FACE);
		}
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
	    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}