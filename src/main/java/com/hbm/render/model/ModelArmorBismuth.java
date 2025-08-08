package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.entity.Entity;

public class ModelArmorBismuth extends ModelArmorBase {

	public ModelArmorBismuth(int type) {
		super(type);

		this.head = new ModelRendererObj(ResourceManager.armor_bismuth, "Head");
		this.body = new ModelRendererObj(ResourceManager.armor_bismuth, "Body");
		this.leftArm = new ModelRendererObj(ResourceManager.armor_bismuth, "LeftArm").setRotationPoint(5.0F, 2.0F, 0.0F);
		this.rightArm = new ModelRendererObj(ResourceManager.armor_bismuth, "RightArm").setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.leftLeg = new ModelRendererObj(ResourceManager.armor_bismuth, "LeftLeg").setRotationPoint(1.9F, 12.0F, 0.0F);
		this.rightLeg = new ModelRendererObj(ResourceManager.armor_bismuth, "RightLeg").setRotationPoint(-1.9F, 12.0F, 0.0F);
		this.leftFoot = new ModelRendererObj(ResourceManager.armor_bismuth, "LeftFoot").setRotationPoint(1.9F, 12.0F, 0.0F);
		this.rightFoot = new ModelRendererObj(ResourceManager.armor_bismuth, "RightFoot").setRotationPoint(-1.9F, 12.0F, 0.0F);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

		GL11.glPushMatrix();
		GL11.glShadeModel(GL11.GL_SMOOTH);

		bindTexture(ResourceManager.armor_bismuth_tex);

		if(this.type == 0) {
			this.head.render(scaleFactor);
		}
		if(this.type == 1) {
			this.leftArm.render(scaleFactor);
			this.rightArm.render(scaleFactor);
			this.body.render(scaleFactor);
		}
		if(this.type == 2) {
			this.leftLeg.render(scaleFactor);
			this.rightLeg.render(scaleFactor);
		}
		if(this.type == 3) {
			this.leftFoot.render(scaleFactor);
			this.rightFoot.render(scaleFactor);
		}

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
