package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;

public class ModelArmorDigamma extends ModelArmorBase {

	ModelRendererObj cassette;

	public ModelArmorDigamma(int type) {
		super(type);

		this.head = new ModelRendererObj(ResourceManager.armor_fau, "Head");
		this.body = new ModelRendererObj(ResourceManager.armor_fau, "Body");
		this.cassette = new ModelRendererObj(ResourceManager.armor_fau, "Cassette");
		this.leftArm = new ModelRendererObj(ResourceManager.armor_fau, "LeftArm").setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.rightArm = new ModelRendererObj(ResourceManager.armor_fau, "RightArm").setRotationPoint(5.0F, 2.0F, 0.0F);
		this.leftLeg = new ModelRendererObj(ResourceManager.armor_fau, "LeftLeg").setRotationPoint(1.9F, 12.0F, 0.0F);
		this.rightLeg = new ModelRendererObj(ResourceManager.armor_fau, "RightLeg").setRotationPoint(-1.9F, 12.0F, 0.0F);
		this.leftFoot = new ModelRendererObj(ResourceManager.armor_fau, "LeftBoot").setRotationPoint(1.9F, 12.0F, 0.0F);
		this.rightFoot = new ModelRendererObj(ResourceManager.armor_fau, "RightBoot").setRotationPoint(-1.9F, 12.0F, 0.0F);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

		GL11.glPushMatrix();
		GL11.glShadeModel(GL11.GL_SMOOTH);

		this.body.copyTo(this.cassette);

		if(this.type == 0) {
			bindTexture(ResourceManager.fau_helmet);
			this.head.render(scaleFactor);
		}
		if(this.type == 1) {
			bindTexture(ResourceManager.fau_chest);
			this.body.render(scaleFactor);
	        GL11.glEnable(GL11.GL_BLEND);
	        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
			bindTexture(ResourceManager.fau_cassette);
			this.cassette.render(scaleFactor);
			bindTexture(ResourceManager.fau_arm);
			this.leftArm.render(scaleFactor);
			this.rightArm.render(scaleFactor);
		}
		if(this.type == 2) {
			bindTexture(ResourceManager.fau_leg);
			this.leftLeg.render(scaleFactor);
			this.rightLeg.render(scaleFactor);
		}
		if(this.type == 3) {
			bindTexture(ResourceManager.fau_leg);
			this.leftFoot.render(scaleFactor);
			this.rightFoot.render(scaleFactor);
		}

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
