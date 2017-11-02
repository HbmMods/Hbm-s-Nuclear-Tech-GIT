//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: TwiGun
// Model Creator: 
// Created on: 01.11.2017 - 20:26:01
// Last changed on: 01.11.2017 - 20:26:01

package com.hbm.render.model; //Path where the model is located

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTwiGun extends ModelBase //Same as Filename
{
	int textureX = 128;
	int textureY = 128;
	ModelRenderer[] bodyModel;

	public ModelTwiGun() //Same as Filename
	{
	    textureWidth = 128;
	    textureHeight = 128;
		bodyModel = new ModelRenderer[65];
		bodyModel[0] = new ModelRenderer(this, 1, 1); // Rotor5
		bodyModel[1] = new ModelRenderer(this, 17, 1); // Rotor4
		bodyModel[2] = new ModelRenderer(this, 33, 1); // Rotor1
		bodyModel[3] = new ModelRenderer(this, 49, 1); // Rotor6
		bodyModel[4] = new ModelRenderer(this, 65, 1); // Rotor3
		bodyModel[5] = new ModelRenderer(this, 81, 1); // Rotor2
		bodyModel[6] = new ModelRenderer(this, 89, 1); // Box 8
		bodyModel[7] = new ModelRenderer(this, 113, 1); // Box 9
		bodyModel[8] = new ModelRenderer(this, 1, 9); // Box 10
		bodyModel[9] = new ModelRenderer(this, 17, 9); // Box 11
		bodyModel[10] = new ModelRenderer(this, 33, 9); // Box 12
		bodyModel[11] = new ModelRenderer(this, 49, 9); // Box 13
		bodyModel[12] = new ModelRenderer(this, 65, 9); // Box 14
		bodyModel[13] = new ModelRenderer(this, 1, 17); // Box 15
		bodyModel[14] = new ModelRenderer(this, 9, 17); // Box 16
		bodyModel[15] = new ModelRenderer(this, 33, 17); // Box 17
		bodyModel[16] = new ModelRenderer(this, 73, 9); // Box 18
		bodyModel[17] = new ModelRenderer(this, 105, 9); // Box 19
		bodyModel[18] = new ModelRenderer(this, 57, 17); // Box 20
		bodyModel[19] = new ModelRenderer(this, 33, 17); // Box 21
		bodyModel[20] = new ModelRenderer(this, 1, 25); // Box 22
		bodyModel[21] = new ModelRenderer(this, 73, 17); // Box 23
		bodyModel[22] = new ModelRenderer(this, 25, 33); // Box 24
		bodyModel[23] = new ModelRenderer(this, 1, 25); // Box 25
		bodyModel[24] = new ModelRenderer(this, 57, 33); // Box 26
		bodyModel[25] = new ModelRenderer(this, 89, 33); // Box 27
		bodyModel[26] = new ModelRenderer(this, 1, 49); // Box 28
		bodyModel[27] = new ModelRenderer(this, 1, 49); // Box 29
		bodyModel[28] = new ModelRenderer(this, 97, 17); // Box 30
		bodyModel[29] = new ModelRenderer(this, 25, 49); // Box 31
		bodyModel[30] = new ModelRenderer(this, 65, 49); // Box 32
		bodyModel[31] = new ModelRenderer(this, 25, 57); // Box 33
		bodyModel[32] = new ModelRenderer(this, 65, 57); // Box 34
		bodyModel[33] = new ModelRenderer(this, 33, 65); // Box 35
		bodyModel[34] = new ModelRenderer(this, 97, 49); // Box 36
		bodyModel[35] = new ModelRenderer(this, 57, 25); // Box 37
		bodyModel[36] = new ModelRenderer(this, 73, 65); // Box 38
		bodyModel[37] = new ModelRenderer(this, 1, 73); // Box 40
		bodyModel[38] = new ModelRenderer(this, 33, 73); // Box 41
		bodyModel[39] = new ModelRenderer(this, 49, 73); // Box 42
		bodyModel[40] = new ModelRenderer(this, 105, 57); // Box 43
		bodyModel[41] = new ModelRenderer(this, 113, 65); // Box 44
		bodyModel[42] = new ModelRenderer(this, 113, 25); // Box 45
		bodyModel[43] = new ModelRenderer(this, 121, 9); // Box 46
		bodyModel[44] = new ModelRenderer(this, 73, 73); // Box 47
		bodyModel[45] = new ModelRenderer(this, 25, 73); // Box 48
		bodyModel[46] = new ModelRenderer(this, 89, 73); // Box 49
		bodyModel[47] = new ModelRenderer(this, 25, 25); // Box 50
		bodyModel[48] = new ModelRenderer(this, 17, 9); // Box 51
		bodyModel[49] = new ModelRenderer(this, 49, 33); // Box 52
		bodyModel[50] = new ModelRenderer(this, 81, 33); // Box 53
		bodyModel[51] = new ModelRenderer(this, 57, 1); // Box 54
		bodyModel[52] = new ModelRenderer(this, 73, 1); // Box 55
		bodyModel[53] = new ModelRenderer(this, 105, 73); // Box 56
		bodyModel[54] = new ModelRenderer(this, 89, 73); // Box 57
		bodyModel[55] = new ModelRenderer(this, 17, 81); // Box 58
		bodyModel[56] = new ModelRenderer(this, 73, 81); // Box 59
		bodyModel[57] = new ModelRenderer(this, 1, 89); // Box 60
		bodyModel[58] = new ModelRenderer(this, 33, 89); // Box 61
		bodyModel[59] = new ModelRenderer(this, 49, 41); // Box 63
		bodyModel[60] = new ModelRenderer(this, 89, 81); // Box 64
		bodyModel[61] = new ModelRenderer(this, 113, 81); // Box 65
		bodyModel[62] = new ModelRenderer(this, 1, 89); // Box 66
		bodyModel[63] = new ModelRenderer(this, 89, 89); // Box 67
		bodyModel[64] = new ModelRenderer(this, 17, 97); // Box 68

		bodyModel[0].addBox(0F, 1F, -1F, 3, 4, 2, 0F); // Rotor5
		bodyModel[0].setRotationPoint(-12F, 0F, 0F);

		bodyModel[1].addBox(0F, 1F, -1F, 3, 4, 2, 0F); // Rotor4
		bodyModel[1].setRotationPoint(-12F, 0F, 0F);
		bodyModel[1].rotateAngleX = 2.0943951F;

		bodyModel[2].addBox(0F, 1F, -1F, 3, 4, 2, 0F); // Rotor1
		bodyModel[2].setRotationPoint(-12F, 0F, 0F);
		bodyModel[2].rotateAngleX = -2.0943951F;

		bodyModel[3].addBox(0F, 4.5F, -0.5F, 3, 1, 1, 0F); // Rotor6
		bodyModel[3].setRotationPoint(-12F, 0F, 0F);

		bodyModel[4].addBox(0F, 4.5F, -0.5F, 3, 1, 1, 0F); // Rotor3
		bodyModel[4].setRotationPoint(-12F, 0F, 0F);
		bodyModel[4].rotateAngleX = 2.0943951F;

		bodyModel[5].addBox(0F, 4.5F, -0.5F, 3, 1, 1, 0F); // Rotor2
		bodyModel[5].setRotationPoint(-12F, 0F, 0F);
		bodyModel[5].rotateAngleX = -2.0943951F;

		bodyModel[6].addBox(0F, 0F, 0F, 5, 0, 8, 0F); // Box 8
		bodyModel[6].setRotationPoint(-13F, -6F, -4F);

		bodyModel[7].addBox(0F, -1F, -1F, 5, 2, 2, 0F); // Box 9
		bodyModel[7].setRotationPoint(-13F, 0F, 0F);

		bodyModel[8].addBox(0F, -1F, -1F, 5, 2, 2, 0F); // Box 10
		bodyModel[8].setRotationPoint(-13F, 0F, 0F);
		bodyModel[8].rotateAngleX = 0.78539816F;

		bodyModel[9].addBox(0F, 0F, 0F, 5, 0, 5, 0F); // Box 11
		bodyModel[9].setRotationPoint(-13F, -6F, 4F);
		bodyModel[9].rotateAngleX = -0.78539816F;

		bodyModel[10].addBox(0F, 0F, 0F, 9, 1, 1, 0F); // Box 12
		bodyModel[10].setRotationPoint(-15F, -3F, 7F);

		bodyModel[11].addBox(0F, 0F, -5F, 5, 0, 5, 0F); // Box 13
		bodyModel[11].setRotationPoint(-13F, -6F, -4F);
		bodyModel[11].rotateAngleX = 0.78539816F;

		bodyModel[12].addBox(0F, 0F, 0F, 9, 1, 1, 0F); // Box 14
		bodyModel[12].setRotationPoint(-15F, -3F, -8F);

		bodyModel[13].addBox(0F, 0F, 0F, 5, 0, 7, 0F); // Box 15
		bodyModel[13].setRotationPoint(-13F, 6.5F, -3.5F);

		bodyModel[14].addBox(0F, 0F, 0F, 5, 0, 10, 0F); // Box 16
		bodyModel[14].setRotationPoint(-13F, 6.5F, 3.5F);
		bodyModel[14].rotateAngleX = 1.13446401F;

		bodyModel[15].addBox(0F, 0F, -10F, 5, 0, 10, 0F); // Box 17
		bodyModel[15].setRotationPoint(-13F, 6.5F, -3.5F);
		bodyModel[15].rotateAngleX = -1.13446401F;

		bodyModel[16].addBox(0F, 0F, 0F, 2, 2, 16, 0F); // Box 18
		bodyModel[16].setRotationPoint(-15F, -2F, -8F);

		bodyModel[17].addBox(0F, 0F, 0F, 3, 4, 4, 0F); // Box 19
		bodyModel[17].setRotationPoint(-16F, -2F, -2F);

		bodyModel[18].addBox(0F, 0F, 0F, 5, 1, 1, 0F); // Box 20
		bodyModel[18].setRotationPoint(-13F, 6F, -0.5F);

		bodyModel[19].addBox(0F, 0F, 0F, 2, 5, 2, 0F); // Box 21
		bodyModel[19].setRotationPoint(-15F, 2F, -1F);

		bodyModel[20].addBox(0F, 0F, 0F, 0, 10, 10, 0F); // Box 22
		bodyModel[20].setRotationPoint(-13F, -5F, -5F);

		bodyModel[21].addBox(0F, -1.5F, -1.5F, 3, 3, 3, 0F); // Box 23
		bodyModel[21].setRotationPoint(-19F, 0F, 0F);

		bodyModel[22].addBox(0F, -2F, -2F, 8, 4, 4, 0F); // Box 24
		bodyModel[22].setRotationPoint(-27F, 0F, 0F);

		bodyModel[23].addBox(0F, -1.5F, -1.5F, 3, 3, 3, 0F); // Box 25
		bodyModel[23].setRotationPoint(-19F, 0F, 0F);
		bodyModel[23].rotateAngleX = -0.78539816F;

		bodyModel[24].addBox(0F, -2F, -2F, 8, 4, 4, 0F); // Box 26
		bodyModel[24].setRotationPoint(-27F, 0F, 0F);
		bodyModel[24].rotateAngleX = -0.78539816F;

		bodyModel[25].addBox(0F, 0F, 0F, 14, 6, 4, 0F); // Box 27
		bodyModel[25].setRotationPoint(-6F, -3F, -2F);

		bodyModel[26].addBox(0F, 0F, 0F, 2, 2, 16, 0F); // Box 28
		bodyModel[26].setRotationPoint(-8F, -2F, -8F);

		bodyModel[27].addBox(0F, 0F, 0F, 3, 4, 4, 0F); // Box 29
		bodyModel[27].setRotationPoint(-9F, -2F, -2F);

		bodyModel[28].addBox(0F, 0F, 0F, 2, 5, 2, 0F); // Box 30
		bodyModel[28].setRotationPoint(-8F, 2F, -1F);

		bodyModel[29].addBox(0F, 0F, 0F, 14, 1, 5, 0F); // Box 31
		bodyModel[29].setRotationPoint(-6F, -3F, 2F);
		bodyModel[29].rotateAngleX = -0.26179939F;

		bodyModel[30].addBox(0F, 0F, 0F, 14, 1, 1, 0F); // Box 32
		bodyModel[30].setRotationPoint(-6F, -1.5F, 6F);

		bodyModel[31].addBox(0F, -1F, 0F, 14, 1, 5, 0F); // Box 33
		bodyModel[31].setRotationPoint(-6F, 1F, 2F);
		bodyModel[31].rotateAngleX = 0.26179939F;

		bodyModel[32].addBox(0F, -1F, -5F, 14, 1, 5, 0F); // Box 34
		bodyModel[32].setRotationPoint(-6F, 1F, -2F);
		bodyModel[32].rotateAngleX = -0.26179939F;

		bodyModel[33].addBox(0F, 0F, -5F, 14, 1, 5, 0F); // Box 35
		bodyModel[33].setRotationPoint(-6F, -3F, -2F);
		bodyModel[33].rotateAngleX = 0.26179939F;

		bodyModel[34].addBox(0F, 0F, 0F, 14, 1, 1, 0F); // Box 36
		bodyModel[34].setRotationPoint(-6F, -1.5F, -7F);

		bodyModel[35].addBox(0F, 0F, 0F, 5, 0, 4, 0F); // Box 37
		bodyModel[35].setRotationPoint(-8F, -6F, -2F);
		bodyModel[35].rotateAngleZ = 0.78539816F;

		bodyModel[36].addBox(0F, 0F, 0F, 16, 4, 2, 0F); // Box 38
		bodyModel[36].setRotationPoint(-6F, 3F, -1F);

		bodyModel[37].addBox(0F, 0F, 0F, 6, 6, 6, 0F); // Box 40
		bodyModel[37].setRotationPoint(8F, -3F, -3F);

		bodyModel[38].addBox(0F, 0F, 0F, 3, 2, 10, 0F); // Box 41
		bodyModel[38].setRotationPoint(8F, -2F, -5F);

		bodyModel[39].addBox(0F, 0F, 0F, 4, 1, 13, 0F); // Box 42
		bodyModel[39].setRotationPoint(8F, -1.5F, -6.5F);

		bodyModel[40].addBox(0F, 0F, 0F, 8, 3, 3, 0F); // Box 43
		bodyModel[40].setRotationPoint(14F, -1F, -1.5F);

		bodyModel[41].addBox(0F, 0F, 0F, 1, 7, 3, 0F); // Box 44
		bodyModel[41].setRotationPoint(22.5F, -1F, -1.5F);

		bodyModel[42].addBox(0F, 0F, 0F, 4, 4, 3, 0F); // Box 45
		bodyModel[42].setRotationPoint(18F, 2F, -1.5F);

		bodyModel[43].addBox(0F, 0F, 0F, 2, 4, 1, 0F); // Box 46
		bodyModel[43].setRotationPoint(13F, 2F, 0F);

		bodyModel[44].addBox(0F, -5F, 0F, 2, 5, 3, 0F); // Box 47
		bodyModel[44].setRotationPoint(18F, 6F, -1.5F);
		bodyModel[44].rotateAngleZ = -0.43633231F;

		bodyModel[45].addBox(0F, 0F, 0F, 6, 1, 2, 0F); // Box 48
		bodyModel[45].setRotationPoint(10F, 6F, -1F);

		bodyModel[46].addBox(0F, 0F, 0F, 3, 2, 2, 0F); // Box 49
		bodyModel[46].setRotationPoint(14F, -3F, -1F);
		bodyModel[46].rotateAngleZ = 0.78539816F;

		bodyModel[47].addBox(0F, 0F, 0F, 2, 1, 5, 0F); // Box 50
		bodyModel[47].setRotationPoint(14F, 0F, -2.5F);

		bodyModel[48].addBox(0F, 0F, 0F, 1, 1, 1, 0F); // Box 51
		bodyModel[48].setRotationPoint(10.5F, -3.5F, -0.5F);

		bodyModel[49].addBox(0F, 0F, 0F, 3, 1, 1, 0F); // Box 52
		bodyModel[49].setRotationPoint(9F, -3.5F, -2F);

		bodyModel[50].addBox(0F, 0F, 0F, 3, 1, 1, 0F); // Box 53
		bodyModel[50].setRotationPoint(10F, -3.5F, 1F);

		bodyModel[51].addBox(0F, 0F, 0F, 1, 1, 3, 0F); // Box 54
		bodyModel[51].setRotationPoint(9F, -3.5F, -1F);

		bodyModel[52].addBox(0F, 0F, 0F, 1, 1, 3, 0F); // Box 55
		bodyModel[52].setRotationPoint(12F, -3.5F, -2F);

		bodyModel[53].addBox(0F, 0F, 0F, 1, 6, 2, 0F); // Box 56
		bodyModel[53].setRotationPoint(22F, -0.5F, -1F);

		bodyModel[54].addBox(0F, 0F, 0F, 0, 3, 12, 0F); // Box 57
		bodyModel[54].setRotationPoint(-5F, -0.5F, -6F);

		bodyModel[55].addBox(0F, 0F, 0F, 0, 3, 12, 0F); // Box 58
		bodyModel[55].setRotationPoint(-4F, -0.5F, -6F);

		bodyModel[56].addBox(0F, 0F, 0F, 0, 3, 12, 0F); // Box 59
		bodyModel[56].setRotationPoint(-3F, -0.5F, -6F);

		bodyModel[57].addBox(0F, 0F, 0F, 0, 3, 12, 0F); // Box 60
		bodyModel[57].setRotationPoint(-2F, -0.5F, -6F);

		bodyModel[58].addBox(0F, 0F, 0F, 0, 3, 12, 0F); // Box 61
		bodyModel[58].setRotationPoint(-1F, -0.5F, -6F);

		bodyModel[59].addBox(0F, 0F, 0F, 2, 1, 2, 0F); // Box 63
		bodyModel[59].setRotationPoint(-4F, -3.5F, -1F);

		bodyModel[60].addBox(0F, 0F, 0F, 2, 1, 2, 0F); // Box 64
		bodyModel[60].setRotationPoint(-1F, -3.5F, -1F);

		bodyModel[61].addBox(0F, 0F, 0F, 2, 1, 2, 0F); // Box 65
		bodyModel[61].setRotationPoint(2F, -3.5F, -1F);

		bodyModel[62].addBox(0F, 0F, 0F, 2, 1, 2, 0F); // Box 66
		bodyModel[62].setRotationPoint(5F, -3.5F, -1F);

		bodyModel[63].addBox(0F, 0F, 0F, 0, 3, 12, 0F); // Box 67
		bodyModel[63].setRotationPoint(0F, -0.5F, -6F);

		bodyModel[64].addBox(0F, 0F, 0F, 0, 3, 12, 0F); // Box 68
		bodyModel[64].setRotationPoint(1F, -0.5F, -6F);
		
		for(int i = 0; i < 65; i++)
		{
			bodyModel[i].setTextureSize(textureX, textureY);
			bodyModel[i].mirror = true;
		}
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		for(int i = 0; i < 65; i++)
		{
			bodyModel[i].render(f5);
		}
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
	    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}