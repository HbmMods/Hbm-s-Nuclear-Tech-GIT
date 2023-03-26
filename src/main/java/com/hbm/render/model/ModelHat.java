package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class ModelHat extends ModelArmorBase {
	
	public ModelHat(int type) {
		super(type);

		head = new ModelRendererObj(ResourceManager.armor_hat);
		body = new ModelRendererObj(null);
		leftArm = new ModelRendererObj(null).setRotationPoint(-5.0F, 2.0F, 0.0F);
		rightArm = new ModelRendererObj(null).setRotationPoint(5.0F, 2.0F, 0.0F);
		leftLeg = new ModelRendererObj(null).setRotationPoint(1.9F, 12.0F, 0.0F);
		rightLeg = new ModelRendererObj(null).setRotationPoint(-1.9F, 12.0F, 0.0F);
		leftFoot = new ModelRendererObj(null).setRotationPoint(1.9F, 12.0F, 0.0F);
		rightFoot = new ModelRendererObj(null).setRotationPoint(-1.9F, 12.0F, 0.0F);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		
		GL11.glPushMatrix();
		
		if(type == 0) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.hat);
			head.render(par7);
		}
		
		GL11.glPopMatrix();
	}
}
