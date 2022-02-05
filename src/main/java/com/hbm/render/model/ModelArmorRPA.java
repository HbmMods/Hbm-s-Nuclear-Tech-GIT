package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;

public class ModelArmorRPA extends ModelArmorBase {
	
	ModelRendererObj fan;
	ModelRendererObj glow;
	
	public ModelArmorRPA(int type) {
		super(type);

		head = new ModelRendererObj(ResourceManager.armor_remnant, "Head");
		body = new ModelRendererObj(ResourceManager.armor_remnant, "Body");
		fan = new ModelRendererObj(ResourceManager.armor_remnant, "Fan");
		glow = new ModelRendererObj(ResourceManager.armor_remnant, "Glow");
		leftArm = new ModelRendererObj(ResourceManager.armor_remnant, "LeftArm").setRotationPoint(-5.0F, 2.0F, 0.0F);
		rightArm = new ModelRendererObj(ResourceManager.armor_remnant, "RightArm").setRotationPoint(5.0F, 2.0F, 0.0F);
		leftLeg = new ModelRendererObj(ResourceManager.armor_remnant, "LeftLeg").setRotationPoint(1.9F, 12.0F, 0.0F);
		rightLeg = new ModelRendererObj(ResourceManager.armor_remnant, "RightLeg").setRotationPoint(-1.9F, 12.0F, 0.0F);
		leftFoot = new ModelRendererObj(ResourceManager.armor_remnant, "LeftBoot").setRotationPoint(1.9F, 12.0F, 0.0F);
		rightFoot = new ModelRendererObj(ResourceManager.armor_remnant, "RightBoot").setRotationPoint(-1.9F, 12.0F, 0.0F);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		//body.copyTo(fan);
		body.copyTo(glow);
		
		GL11.glPushMatrix();
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		if(type == 0) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.rpa_helmet);
			head.render(par7);
		}
		if(type == 1) {
			
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.rpa_arm);
			leftArm.render(par7);
			rightArm.render(par7);
			
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.rpa_chest);
			body.render(par7);
			
			/// START GLOW ///
			float lastX = OpenGlHelper.lastBrightnessX;
			float lastY = OpenGlHelper.lastBrightnessY;
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			GL11.glDisable(GL11.GL_LIGHTING);
			glow.render(par7);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopAttrib();
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastX, lastY);
			/// END GLOW ///
			
			/// START FAN ///
			GL11.glPushMatrix();
			double px = 0.0625D;
			GL11.glTranslatef(body.offsetX * (float) px, body.offsetY * (float) px, body.offsetZ * (float) px);
			GL11.glTranslatef(body.rotationPointX * (float) px, body.rotationPointY * (float) px, body.rotationPointZ * (float) px);

			if(body.rotateAngleZ != 0.0F) {
				GL11.glRotatef(body.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
			}

			if(body.rotateAngleY != 0.0F) {
				GL11.glRotatef(body.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
			}

			if(body.rotateAngleX != 0.0F) {
				GL11.glRotatef(body.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
			}
			
			GL11.glTranslated(0, 4.875 * px, 0);
			GL11.glRotated(-System.currentTimeMillis() / 2 % 360, 0, 0, 1);
			GL11.glTranslated(0, -4.875 * px, 0);
			fan.render(par7);
			GL11.glPopMatrix();
			/// END FAN ///
		}
		if(type == 2) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.rpa_leg);
			leftLeg.render(par7);
			rightLeg.render(par7);
		}
		if(type == 3) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.rpa_leg);
			leftFoot.render(par7);
			rightFoot.render(par7);
		}
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
