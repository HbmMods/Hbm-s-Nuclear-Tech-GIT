package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;

public class ModelArmorBJ extends ModelBiped {
	
	int type;
	
	public ModelArmorBJ(int type) {
		this.type = type;
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {

		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		GL11.glPushMatrix();
		
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.launch_table_base_tex);
		GL11.glRotated(180, 0, 0, 1);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, -1.5, 0);
		
		if(type == 0) {
			GL11.glPushMatrix();
			GL11.glTranslated(0, 1.5, 0);
			GL11.glRotated(-Math.toDegrees(this.bipedHead.rotateAngleY), 0, 1, 0);
			GL11.glRotated(-Math.toDegrees(this.bipedHead.rotateAngleX), 0, 0, 1);
			GL11.glTranslated(0, -1.5, 0);
			ResourceManager.armor_bj.renderPart("Head");
			GL11.glPopMatrix();
		}
		
		if(type == 1) {
			GL11.glPushMatrix();
			GL11.glTranslated(0, 1.5, 0);
			GL11.glRotated(-Math.toDegrees(this.bipedBody.rotateAngleY), 0, 1, 0);
			GL11.glRotated(-Math.toDegrees(this.bipedBody.rotateAngleX), 0, 0, 1);
			GL11.glTranslated(0, -1.5, 0);
			ResourceManager.armor_bj.renderPart("Body");
			GL11.glPopMatrix();
			
			double height = 22;
			
			GL11.glPushMatrix();
			GL11.glTranslated(0, 0.0625 * height, -0.25);
			GL11.glRotated(-Math.toDegrees(this.bipedLeftArm.rotateAngleZ), 1, 0, 0);
			GL11.glRotated(-Math.toDegrees(this.bipedLeftArm.rotateAngleY), 0, 1, 0);
			GL11.glRotated(-Math.toDegrees(this.bipedLeftArm.rotateAngleX), 0, 0, 1);
			GL11.glTranslated(0, -0.0625 * height, 0.25);
			ResourceManager.armor_bj.renderPart("LeftArm");
			GL11.glPopMatrix();
			
			GL11.glPushMatrix();
			GL11.glTranslated(0, 0.0625 * height, 0.25);
			GL11.glRotated(-Math.toDegrees(this.bipedRightArm.rotateAngleZ), 1, 0, 0);
			GL11.glRotated(-Math.toDegrees(this.bipedRightArm.rotateAngleY), 0, 1, 0);
			GL11.glRotated(-Math.toDegrees(this.bipedRightArm.rotateAngleX), 0, 0, 1);
			GL11.glTranslated(0, -0.0625 * height, -0.25);
			ResourceManager.armor_bj.renderPart("RightArm");
			GL11.glPopMatrix();
		}
		
		if(type == 2) {
			GL11.glPushMatrix();
			GL11.glTranslated(0, 0.75, 0);
			GL11.glRotated(-Math.toDegrees(this.bipedLeftLeg.rotateAngleX), 0, 0, 1);
			GL11.glTranslated(0, -0.75, 0);
			ResourceManager.armor_bj.renderPart("LeftLeg");
			GL11.glPopMatrix();
			
			GL11.glPushMatrix();
			GL11.glTranslated(0, 0.75, 0);
			GL11.glRotated(-Math.toDegrees(this.bipedRightLeg.rotateAngleX), 0, 0, 1);
			GL11.glTranslated(0, -0.75, 0);
			ResourceManager.armor_bj.renderPart("RightLeg");
			GL11.glPopMatrix();
		}
		
		if(type == 3) {
		}
		
		GL11.glPopMatrix();
	}

}
