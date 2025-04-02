package com.hbm.render.model;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.entity.Entity;

public class ModelArmorTailPeep extends ModelArmorBase {

	ModelRendererObj tail;

	public ModelArmorTailPeep() {
		super(0);
		this.tail = new ModelRendererObj(ResourceManager.armor_tail, "FaggyAssFuckingTailThing");
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
		this.body.copyTo(this.tail);

		bindTexture(ResourceManager.tail_peep);
		this.tail.render(scaleFactor);
	}
}
