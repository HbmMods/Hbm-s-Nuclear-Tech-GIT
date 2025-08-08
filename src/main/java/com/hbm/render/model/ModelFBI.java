package com.hbm.render.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;

public class ModelFBI extends ModelBiped {

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

    	super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    }
}
