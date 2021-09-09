package com.hbm.render.model;

import com.hbm.entity.mob.siege.EntitySiegeZombie;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelSiegeZombie extends ModelBiped {

	public ModelSiegeZombie(float p_i1168_1_) {
		super(p_i1168_1_, 0.0F, 64, 32);
	}
	
	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity entity) {
		super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, entity);
		
		if(entity instanceof EntitySiegeZombie && ((EntitySiegeZombie)entity).getDataWatcher().getWatchableObjectByte(13) != 0) {
			float f6 = MathHelper.sin(this.onGround * (float) Math.PI);
			float f7 = MathHelper.sin((1.0F - (1.0F - this.onGround) * (1.0F - this.onGround)) * (float) Math.PI);
			this.bipedRightArm.rotateAngleZ = 0.0F;
			this.bipedLeftArm.rotateAngleZ = 0.0F;
			this.bipedRightArm.rotateAngleY = -(0.1F - f6 * 0.6F);
			this.bipedLeftArm.rotateAngleY = 0.1F - f6 * 0.6F;
			this.bipedRightArm.rotateAngleX = -((float) Math.PI / 2F);
			this.bipedLeftArm.rotateAngleX = -((float) Math.PI / 2F);
			this.bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
			this.bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
			this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
			this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
			this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
			this.bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
		}
	}
}
