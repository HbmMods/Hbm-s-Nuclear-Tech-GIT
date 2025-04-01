package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.entity.Entity;

public class ModelArmorTaurun extends ModelArmorBase {

	public ModelArmorTaurun(int type) {
		super(type);

		this.head = new ModelRendererObj(ResourceManager.armor_taurun, "Helmet");
		this.body = new ModelRendererObj(ResourceManager.armor_taurun, "Chest");
		this.leftArm = new ModelRendererObj(ResourceManager.armor_taurun, "LeftArm").setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.rightArm = new ModelRendererObj(ResourceManager.armor_taurun, "RightArm").setRotationPoint(5.0F, 2.0F, 0.0F);
		this.leftLeg = new ModelRendererObj(ResourceManager.armor_taurun, "LeftLeg").setRotationPoint(1.9F, 12.0F, 0.0F);
		this.rightLeg = new ModelRendererObj(ResourceManager.armor_taurun, "RightLeg").setRotationPoint(-1.9F, 12.0F, 0.0F);
		this.leftFoot = new ModelRendererObj(ResourceManager.armor_taurun, "LeftBoot").setRotationPoint(1.9F, 12.0F, 0.0F);
		this.rightFoot = new ModelRendererObj(ResourceManager.armor_taurun, "RightBoot").setRotationPoint(-1.9F, 12.0F, 0.0F);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

		GL11.glPushMatrix();

		if(this.type == 0) {
			bindTexture(ResourceManager.taurun_helmet);
			this.head.render(scaleFactor);
		}
		if(this.type == 1) {
			bindTexture(ResourceManager.taurun_chest);
			this.body.render(scaleFactor);
			bindTexture(ResourceManager.taurun_arm);
			this.leftArm.render(scaleFactor);
			this.rightArm.render(scaleFactor);
		}
		if(this.type == 2) {
			bindTexture(ResourceManager.taurun_leg);
			GL11.glTranslated(-0.01, 0, 0);
			this.leftLeg.render(scaleFactor);
			GL11.glTranslated(0.02, 0, 0);
			this.rightLeg.render(scaleFactor);
		}
		if(this.type == 3) {
			bindTexture(ResourceManager.taurun_leg);
			GL11.glTranslated(-0.01, 0, 0);
			this.leftFoot.render(scaleFactor);
			GL11.glTranslated(0.02, 0, 0);
			this.rightFoot.render(scaleFactor);
		}

		GL11.glPopMatrix();
	}
}
