package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.entity.Entity;

public class ModelArmorDNT extends ModelArmorBase {

	public ModelArmorDNT(int type) {
		super(type);

		this.head = new ModelRendererObj(ResourceManager.armor_dnt, "Head");
		this.body = new ModelRendererObj(ResourceManager.armor_dnt, "Body");
		this.leftArm = new ModelRendererObj(ResourceManager.armor_dnt, "LeftArm").setRotationPoint(5.0F, 2.0F, 0.0F);
		this.rightArm = new ModelRendererObj(ResourceManager.armor_dnt, "RightArm").setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.leftLeg = new ModelRendererObj(ResourceManager.armor_dnt, "LeftLeg").setRotationPoint(1.9F, 12.0F, 0.0F);
		this.rightLeg = new ModelRendererObj(ResourceManager.armor_dnt, "RightLeg").setRotationPoint(-1.9F, 12.0F, 0.0F);
		this.leftFoot = new ModelRendererObj(ResourceManager.armor_dnt, "LeftBoot").setRotationPoint(1.9F, 12.0F, 0.0F);
		this.rightFoot = new ModelRendererObj(ResourceManager.armor_dnt, "RightBoot").setRotationPoint(-1.9F, 12.0F, 0.0F);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

		GL11.glPushMatrix();
		GL11.glShadeModel(GL11.GL_SMOOTH);

		if(this.type == 0) {
			bindTexture(ResourceManager.dnt_helmet);
			this.head.render(scaleFactor);
		}
		if(this.type == 1) {
			bindTexture(ResourceManager.dnt_chest);
			this.body.render(scaleFactor);
			bindTexture(ResourceManager.dnt_arm);
			this.leftArm.render(scaleFactor);
			this.rightArm.render(scaleFactor);
		}
		if(this.type == 2) {
			bindTexture(ResourceManager.dnt_leg);
			this.leftLeg.render(scaleFactor);
			this.rightLeg.render(scaleFactor);
		}
		if(this.type == 3) {
			bindTexture(ResourceManager.dnt_leg);
			this.leftFoot.render(scaleFactor);
			this.rightFoot.render(scaleFactor);
		}

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
