package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;

public class ModelArmorNCRPA extends ModelArmorBase {

	ModelRendererObj eyes;

	public ModelArmorNCRPA(int type) {
		super(type);

		this.head = new ModelRendererObj(ResourceManager.armor_ncr, "Helmet");
		this.eyes = new ModelRendererObj(ResourceManager.armor_ncr, "Eyes");
		this.body = new ModelRendererObj(ResourceManager.armor_ncr, "Chest");
		this.leftArm = new ModelRendererObj(ResourceManager.armor_ncr, "LeftArm").setRotationPoint(5.0F, 2.0F, 0.0F);
		this.rightArm = new ModelRendererObj(ResourceManager.armor_ncr, "RightArm").setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.leftLeg = new ModelRendererObj(ResourceManager.armor_ncr, "LeftLeg").setRotationPoint(1.9F, 12.0F, 0.0F);
		this.rightLeg = new ModelRendererObj(ResourceManager.armor_ncr, "RightLeg").setRotationPoint(-1.9F, 12.0F, 0.0F);
		this.leftFoot = new ModelRendererObj(ResourceManager.armor_ncr, "LeftBoot").setRotationPoint(1.9F, 12.0F, 0.0F);
		this.rightFoot = new ModelRendererObj(ResourceManager.armor_ncr, "RightBoot").setRotationPoint(-1.9F, 12.0F, 0.0F);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
		this.head.copyTo(this.eyes);

		GL11.glPushMatrix();
		GL11.glShadeModel(GL11.GL_SMOOTH);

		if(this.type == 0) {
			bindTexture(ResourceManager.ncrpa_helmet);
			this.head.render(scaleFactor);

			/// START GLOW ///
			float lastX = OpenGlHelper.lastBrightnessX;
			float lastY = OpenGlHelper.lastBrightnessY;
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			GL11.glDisable(GL11.GL_LIGHTING);
			this.eyes.render(scaleFactor);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopAttrib();
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastX, lastY);
			/// END GLOW ///
		}
		if(this.type == 1) {

			bindTexture(ResourceManager.ncrpa_arm);
			this.leftArm.render(scaleFactor);
			this.rightArm.render(scaleFactor);

			bindTexture(ResourceManager.ncrpa_chest);
			this.body.render(scaleFactor);
		}
		if(this.type == 2) {
			bindTexture(ResourceManager.ncrpa_leg);
			this.leftLeg.render(scaleFactor);
			this.rightLeg.render(scaleFactor);
		}
		if(this.type == 3) {
			bindTexture(ResourceManager.ncrpa_leg);
			this.leftFoot.render(scaleFactor);
			this.rightFoot.render(scaleFactor);
		}

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
