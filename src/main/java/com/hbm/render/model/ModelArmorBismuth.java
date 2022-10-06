package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class ModelArmorBismuth extends ModelArmorBase {
	
	public ModelArmorBismuth(int type) {
		super(type);

		head = new ModelRendererObj(ResourceManager.armor_bismuth, "Head");
		body = new ModelRendererObj(ResourceManager.armor_bismuth, "Body");
		leftArm = new ModelRendererObj(ResourceManager.armor_bismuth, "LeftArm").setRotationPoint(-5.0F, 2.0F, 0.0F);
		rightArm = new ModelRendererObj(ResourceManager.armor_bismuth, "RightArm").setRotationPoint(5.0F, 2.0F, 0.0F);
		leftLeg = new ModelRendererObj(ResourceManager.armor_bismuth, "LeftLeg").setRotationPoint(1.9F, 12.0F, 0.0F);
		rightLeg = new ModelRendererObj(ResourceManager.armor_bismuth, "RightLeg").setRotationPoint(-1.9F, 12.0F, 0.0F);
		leftFoot = new ModelRendererObj(ResourceManager.armor_bismuth, "LeftFoot").setRotationPoint(1.9F, 12.0F, 0.0F);
		rightFoot = new ModelRendererObj(ResourceManager.armor_bismuth, "RightFoot").setRotationPoint(-1.9F, 12.0F, 0.0F);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		
		GL11.glPushMatrix();
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.armor_bismuth_tex);
		
		if(type == 0) {
			head.render(par7);
		}
		if(type == 1) {
			leftArm.render(par7);
			rightArm.render(par7);
			body.render(par7);
		}
		if(type == 2) {
			leftLeg.render(par7);
			rightLeg.render(par7);
		}
		if(type == 3) {
			leftFoot.render(par7);
			rightFoot.render(par7);
		}
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
