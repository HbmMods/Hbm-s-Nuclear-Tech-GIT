package com.hbm.render.model;

import com.hbm.entity.mob.EntityPigeon;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelPigeon extends ModelBase {

	public ModelRenderer head;
	public ModelRenderer beak;
	public ModelRenderer body;
	public ModelRenderer bodyFat;
	public ModelRenderer leftLeg;
	public ModelRenderer rightLeg;
	public ModelRenderer leftWing;
	public ModelRenderer rightWing;
	public ModelRenderer ass;
	public ModelRenderer feathers;
	
	public ModelPigeon() {
		initModel();
	}
	
	private void initModel() {
		
		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-2F, -6F, -2F, 4, 6, 4);
		this.head.setRotationPoint(0F, 16F, -2F);
		this.beak = new ModelRenderer(this, 14, 0);
		this.beak.addBox(-1F, -4F, -4F, 2, 2, 2);
		this.beak.setRotationPoint(0F, 16F, -2F);

		this.body = new ModelRenderer(this, 0, 10);
		this.body.addBox(-3F, -3F, -4F, 6, 6, 8, 0);
		this.body.setRotationPoint(0F, 17F, 0F);
		this.bodyFat = new ModelRenderer(this, 0, 10);
		this.bodyFat.addBox(-3F, -3F, -4F, 6, 6, 8, 1);
		this.bodyFat.setRotationPoint(0F, 17F, 0F);
		this.ass = new ModelRenderer(this, 0, 24);
		this.ass.addBox(-2F, -2F, -2F, 4, 4, 4);
		this.ass.setRotationPoint(0F, 20F, 4F);
		this.feathers = new ModelRenderer(this, 16, 24);
		this.feathers.addBox(-1F, -0.5F, -2F, 2, 1, 4);
		this.feathers.setRotationPoint(0F, 21.5F, 7.5F);
		
		this.leftLeg = new ModelRenderer(this, 20, 0);
		this.leftLeg.addBox(-1F, 0F, 0F, 2, 4, 2);
		this.leftLeg.setRotationPoint(1F, 20F, -1F);
		this.rightLeg = new ModelRenderer(this, 20, 0);
		this.rightLeg.addBox(-1F, 0F, 0F, 2, 4, 2);
		this.rightLeg.setRotationPoint(-1F, 20F, -1F);
		
		this.leftWing = new ModelRenderer(this, 28, 0);
		this.leftWing.addBox(0F, 0F, -3F, 1, 4, 6);
		this.leftWing.setRotationPoint(3F, -2F, 0F);
		this.rightWing = new ModelRenderer(this, 28, 10);
		this.rightWing.addBox(-1F, 0F, -3F, 1, 4, 6);
		this.rightWing.setRotationPoint(-3F, -2F, 0F);

		this.body.addChild(this.leftWing);
		this.body.addChild(this.rightWing);
		this.bodyFat.addChild(this.leftWing);
		this.bodyFat.addChild(this.rightWing);
	}

	public void render(Entity entity, float f0, float f1, float f2, float f3, float f4, float scale) {
		this.setRotationAngles(f0, f1, f2, f3, f4, scale, entity);
		this.head.render(scale);
		this.beak.render(scale);
		if(((EntityPigeon) entity).isFat()) {
			this.bodyFat.render(scale);
		} else {
			this.body.render(scale);
		}
		this.rightLeg.render(scale);
		this.leftLeg.render(scale);
		this.ass.render(scale);
		this.feathers.render(scale);
	}
	
	public void setRotationAngles(float walkLoop, float legAmplitude, float armSwing, float headYaw, float headPitch, float scale, Entity entity) {
		this.head.rotateAngleX = this.beak.rotateAngleX = headPitch / (180F / (float) Math.PI);
		this.head.rotateAngleY = this.beak.rotateAngleY = headYaw / (180F / (float) Math.PI);
		this.body.rotateAngleX = this.bodyFat.rotateAngleX = this.ass.rotateAngleX = -((float) Math.PI / 4F);
		this.feathers.rotateAngleX = -((float) Math.PI / 8F);
		this.rightLeg.rotateAngleX = MathHelper.cos(walkLoop * 0.6662F) * 1.4F * legAmplitude;
		this.leftLeg.rotateAngleX = MathHelper.cos(walkLoop * 0.6662F + (float) Math.PI) * 1.4F * legAmplitude;
		this.rightWing.rotateAngleZ = armSwing;
		this.leftWing.rotateAngleZ = -armSwing;
		
		if(((EntityPigeon) entity).isFat()) {
			this.head.rotationPointZ = -4F;
			this.beak.rotationPointZ = -4F;
			this.ass.rotationPointZ = 5F;
			this.feathers.rotationPointZ = 8.5F;
			this.leftWing.rotationPointX = 4F;
			this.rightWing.rotationPointX = -4F;
		} else {
			this.head.rotationPointZ = -2F;
			this.beak.rotationPointZ = -2F;
			this.ass.rotationPointZ = 4F;
			this.feathers.rotationPointZ = 7.5F;
			this.leftWing.rotationPointX = 3F;
			this.rightWing.rotationPointX = -3F;
		}
	}
}
